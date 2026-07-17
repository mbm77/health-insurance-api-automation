package com.mbm.auth;

import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbm.endpoints.EndPoints;
import com.mbm.enums.AuthType;
import com.mbm.enums.ConfigProperties;
import com.mbm.exceptions.TokenGenerationException;
import com.mbm.insurance_pojo.CustomerCredentials;
import com.mbm.utils.PropertyUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class JwtTokenManager {
	private String accessToken;
	private long tokenExpiryTime = 0;
	Response response;

	private static final JwtTokenManager INSTANCE = new JwtTokenManager();

	private JwtTokenManager() {
	}

	public static JwtTokenManager getInstance() {
		return INSTANCE;
	}

	public synchronized String getAccessToken(String username, String password) {
		if (accessToken == null || isTokenExpired()) {
			return generateToken(username, password);
		}
		return accessToken;
	}

	public String generateToken(String username, String password) {

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

		case JWT:
			response = RestAssured.given().baseUri(PropertyUtils.get(ConfigProperties.BASEURL))
					.basePath(EndPoints.LOGIN).contentType(ContentType.JSON)
					.body(CustomerCredentials.builder().username(username).password(password).build()).when().post();
			break;

		default:
			throw new IllegalArgumentException("Unsupported auth type");
		}
		validateResponse(response);
		accessToken = response.jsonPath().getString("accessToken");

		String[] parts = accessToken.split("\\.");

		String payload = new String(Base64.getUrlDecoder().decode(parts[1]));//{"sub":"vikram","iat":1784281647,"exp":1784285247}

		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode jsonNode = mapper.readTree(payload);

			if (jsonNode.has("exp")) {
				long exp = jsonNode.get("exp").asLong();
				tokenExpiryTime = (exp * 1000L) - (bufferSeconds() * 1000L);
			} else {
				throw new TokenGenerationException("Expiry claim not found in JWT");
			}

		} catch (JsonProcessingException e) {
			throw new TokenGenerationException("Invalid JWT token format", e);
		}

		return accessToken;
	}

	private void validateResponse(Response response) {

		if (response.getStatusCode() != 200) {

			throw new TokenGenerationException("Unable to generate Jwt token.\n" + "Status Code: "
					+ response.getStatusCode() + "\nResponse: " + response.asPrettyString());

		}

	}

	private boolean isTokenExpired() {
		return System.currentTimeMillis() >= tokenExpiryTime;
	}

	private int bufferSeconds() {

		return Integer.parseInt(PropertyUtils.get(ConfigProperties.TOKEN_EXPIRY_BUFFER));
	}

}
