package com.mbm.auth;

import com.mbm.enums.AuthType;
import com.mbm.enums.ConfigProperties;
import com.mbm.exceptions.TokenGenerationException;
import com.mbm.utils.PropertyUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OAuthTokenManager {
	private String accessToken;
	private long tokenExpiryTime = 0;

	private static final OAuthTokenManager INSTANCE = new OAuthTokenManager();

	private OAuthTokenManager() {
	}

	public static OAuthTokenManager getInstance() {
		return INSTANCE;
	}

	public synchronized String getAccessToken() {
		if (accessToken == null || isTokenExpired()) {
			generateToken();
		}
		return accessToken;
	}

	public String generateToken() {
		Response response;
		AuthType authType = AuthType.valueOf(PropertyUtils.get(ConfigProperties.AUTHTYPE).toUpperCase());

		switch (authType) {

		case BASIC:
			response = RestAssured.given().auth().preemptive()
					.basic(PropertyUtils.get(ConfigProperties.CLIENTID),
							PropertyUtils.get(ConfigProperties.CLIENTSECRET))
					.contentType("application/x-www-form-urlencoded").formParam("grant_type", "client_credentials")
					.when().post(PropertyUtils.get(ConfigProperties.AUTHURL));

			break;

		case FORM:
			response = RestAssured.given().formParam("grant_type", "client_credentials")
					.formParam("client_id", PropertyUtils.get(ConfigProperties.CLIENTID))
					.formParam("client_secret", PropertyUtils.get(ConfigProperties.CLIENTSECRET))
					.post(PropertyUtils.get(ConfigProperties.AUTHURL));
			break;

		default:
			throw new IllegalArgumentException("Unsupported auth type");
		}
		validateResponse(response);
		accessToken = response.jsonPath().getString("access_token");
		int expiresIn = response.jsonPath().getInt("expires_in"); // seconds

		tokenExpiryTime = System.currentTimeMillis() + (expiresIn - bufferSeconds()) * 1000L;
		return accessToken;
	}

	private void validateResponse(Response response) {

		if (response.getStatusCode() != 200) {

			throw new TokenGenerationException(
				    "Unable to generate OAuth token.\n"
				    + "Status Code: " + response.getStatusCode()
				    + "\nResponse: " + response.asPrettyString());

		}

	}

	private boolean isTokenExpired() {
		return System.currentTimeMillis() >= tokenExpiryTime;
	}

	private int bufferSeconds() {

		return Integer.parseInt(PropertyUtils.get(ConfigProperties.TOKEN_EXPIRY_BUFFER));
	}

}
