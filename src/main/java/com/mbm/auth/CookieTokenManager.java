package com.mbm.auth;

import com.mbm.client.ApiClient;
import com.mbm.dto.booking.TokenCredentials;
import com.mbm.payload.BookingPayload;

import io.restassured.response.Response;

public class CookieTokenManager {

	private static String cookieToken;

	public static String getCookieToken() {

		if (cookieToken == null) {
			TokenCredentials tokenPayload = BookingPayload.getCreateTokenPayload();
			Response response = ApiClient.getCookieAccessToken(tokenPayload);
			cookieToken = response.jsonPath().getString("token");
		}

		return cookieToken;
	}

}
