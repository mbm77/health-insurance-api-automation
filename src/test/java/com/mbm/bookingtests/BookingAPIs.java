package com.mbm.bookingtests;

import java.util.Map;

import com.mbm.bookingpojos.Booking;
import com.mbm.bookingpojos.BookingTestData;
import com.mbm.bookingpojos.CreateBooking;
import com.mbm.bookingpojos.TokenCredentials;
import com.mbm.client.ApiClient;
import com.mbm.endpoints.EndPoints;
import com.mbm.enums.ConfigProperties;
import com.mbm.utils.HeaderUtils;
import com.mbm.utils.PropertyUtils;

import io.restassured.response.Response;

public class BookingAPIs {

	private final String baseUrl = PropertyUtils.get(ConfigProperties.BASEURL);
	Map<String, String> headers;

	public Response createBooking(String createBookingAPIPayload) {

		return ApiClient.performPost(baseUrl, EndPoints.CREATE_BOOKING, createBookingAPIPayload);
	}

	public Response createBooking(Map<String, Object> createBookingAPIPayload) {

		return ApiClient.performPost(baseUrl, EndPoints.CREATE_BOOKING, createBookingAPIPayload);
	}

	public Response createBooking(Booking createBookingAPIPayload) {

		return ApiClient.performPost(baseUrl, EndPoints.CREATE_BOOKING, createBookingAPIPayload);
	}

	public Response createBooking(CreateBooking createBookingAPIPayload) {

		return ApiClient.performPost(baseUrl, EndPoints.CREATE_BOOKING, createBookingAPIPayload);
	}

	public Response createBooking(BookingTestData createBookingAPIPayload) {

		return ApiClient.performPost(baseUrl, EndPoints.CREATE_BOOKING, createBookingAPIPayload);
	}

	public Response getBookingIds() {

		return ApiClient.performGetBookingIds(baseUrl, EndPoints.GET_BOOKING_IDS);
	}

	public Response getBookingDetails(Integer bookingId) {

		return ApiClient.performGetBooking(baseUrl, EndPoints.GET_BOOKING, bookingId);
	}

	public Response getAccessToken(TokenCredentials tokenPayload) {

		return ApiClient.performTokenPost(baseUrl, EndPoints.AUTH, tokenPayload);
	}

	public Response updateBooking(Integer bookingId, Booking updatedPayload) {

		headers = new HeaderUtils().addCookieToken().build();

		return ApiClient.performUpdate(baseUrl, EndPoints.UPDATE_BOOKING, bookingId, updatedPayload, headers);
	}

	public Response partiallyUpdateBooking(Integer bookingId, Map<String, Object> updatedPayload) {

		headers = new HeaderUtils().addCookieToken().build();
		return ApiClient.performPartialUpdate(baseUrl, EndPoints.PARTIAL_UPDATE_BOOKING, bookingId, updatedPayload,
				headers);
	}

	public Response deleteBooking(Integer bookingId) {

		return ApiClient.performDelete(baseUrl, EndPoints.DELETE_BOOKING, bookingId, headers);
	}

	public Response createBookingByJson(Map<String, Object> createBokingByJsondata) {

		return ApiClient.performPostUsingJson(baseUrl, EndPoints.CREATE_BOOKING, createBokingByJsondata);
	}

	public Response getClaimsData() {
		return ApiClient.performGetClaims(baseUrl, EndPoints.CLAIMS);
	}

}
