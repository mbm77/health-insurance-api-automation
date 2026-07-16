package com.mbm.auth;

import com.mbm.bookingpojos.TokenCredentials;
import com.mbm.client.ApiClient;
import com.mbm.payload.Payloads;

import io.restassured.response.Response;

public class CookieTokenManager {

	private static String cookieToken;

	public static String getCookieToken() {

		if (cookieToken == null) {
			TokenCredentials tokenPayload = Payloads.getCreateTokenPayload();
			Response response = ApiClient.getCookieAccessToken(tokenPayload);
			cookieToken = response.jsonPath().getString("token");
		}

		return cookieToken;
	}

}
