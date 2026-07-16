package com.mbm.client;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.mbm.bookingpojos.Booking;
import com.mbm.bookingpojos.BookingTestData;
import com.mbm.bookingpojos.CreateBooking;
import com.mbm.bookingpojos.TokenCredentials;
import com.mbm.endpoints.EndPoints;
import com.mbm.enums.ConfigProperties;
import com.mbm.filelogging.FileLogger;
import com.mbm.framework.request.RequestOptions;
import com.mbm.insurance_pojo.CustomerCredentials;
import com.mbm.reports.ExtentLogger;
import com.mbm.utils.PropertyUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

public class ApiClient {

	private static RequestSpecification getRequestSpecification(String baseUrl, String basePath,
			RequestOptions options) {

		RequestSpecification requestSpec = RestAssured.given().baseUri(baseUrl).basePath(basePath)
				.contentType(ContentType.JSON);

		if (options.getPathParams() != null) {
			requestSpec.pathParams(options.getPathParams());
		}

		if (options.getHeaders() != null) {
			requestSpec.headers(options.getHeaders());
		}

		if (options.getBody() != null) {
			requestSpec.body(options.getBody());
		}
		if (options.getQueryParams() != null) {
			requestSpec.queryParams(options.getQueryParams());
		}

		if (options.getCookies() != null) {
			requestSpec.cookies(options.getCookies());
		}

		if (options.getFormParams() != null) {
			requestSpec.formParams(options.getFormParams());
		}

		if (options.getMultipartFiles() != null) {

			for (Map.Entry<String, File> entry : options.getMultipartFiles().entrySet()) {

				requestSpec.multiPart(entry.getKey(), entry.getValue());

			}

		}

		return requestSpec;
	}

	private static void logRequestInReport(RequestSpecification requestSpecification, Integer bookingId) {
		QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);

		String baseUrl = queryableRequestSpecification.getBaseUri();
		String endpoint =  queryableRequestSpecification.getBasePath();
		if (bookingId != null) {
			endpoint = endpoint + bookingId;
		}
		ExtentLogger.logInfoDetails("Endpoint is " + baseUrl+endpoint);
		FileLogger.logInfoDetails("Endpoint is " + baseUrl+endpoint);

		String method = queryableRequestSpecification.getMethod();
		ExtentLogger.logInfoDetails("Method is " + method);
		FileLogger.logInfoDetails("Method is " + method);

		ExtentLogger.logInfoDetails("Request Headers are ");
		FileLogger.logInfoDetails("Request Headers are ");

		ExtentLogger.logHeaders(queryableRequestSpecification.getHeaders().asList());
		FileLogger.logHeaders(queryableRequestSpecification.getHeaders().asList());

		if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("PATCH")) {
			Object requestBody = queryableRequestSpecification.getBody();
			String bodyContent = (requestBody != null) ? requestBody.toString() : "No request body";
			ExtentLogger.logInfoDetails("Request Body is");
			FileLogger.logInfoDetails("Request Body is");

			ExtentLogger.logJson(bodyContent);
			FileLogger.logJson(bodyContent);
		} else {
			ExtentLogger.logInfoDetails("No Request Body for " + method + " Method");
			FileLogger.logInfoDetails("No Request Body for " + method + " Method");
		}
	}

	private static void logResponseInReport(Response response) {
		ExtentLogger.logInfoDetails("Response status is " + response.getStatusLine());
		FileLogger.logInfoDetails("Response status is " + response.getStatusLine());

		ExtentLogger.logInfoDetails("Response Headers are ");
		FileLogger.logInfoDetails("Response Headers are ");

		ExtentLogger.logHeaders(response.getHeaders().asList());
		FileLogger.logHeaders(response.getHeaders().asList());

		String responseBody = response.getBody() != null ? response.getBody().asString().trim() : "";

		if (!responseBody.isEmpty()) {
			ExtentLogger.logInfoDetails("Response Body is ");
			FileLogger.logInfoDetails("Response Body is ");
			if (responseBody.trim().startsWith("{") || responseBody.trim().startsWith("[")) {
				ExtentLogger.logJson(responseBody);
				FileLogger.logJson(responseBody);
			} else {
				ExtentLogger.logInfoDetails(responseBody);
				FileLogger.logInfoDetails(responseBody);
			}
		} else {
			ExtentLogger.logInfoDetails("No response body returned");
			FileLogger.logInfoDetails("No response body returned");
		}

	}

	public static Response performPost(String baseUrl, String basePath, String requestPayload) {

		RequestOptions options = RequestOptions.builder().body(requestPayload).build();

		RequestSpecification requestSpecification = getRequestSpecification(baseUrl, basePath, options);
		Response response = requestSpecification.post().then().extract().response();
		logRequestInReport(requestSpecification, null);
		logResponseInReport(response);
		return response;
	}

	public static Response performPost(String baseUrl, String basePath, Map<String, Object> requestPayload) {

		RequestOptions options = RequestOptions.builder().body(requestPayload).build();
		RequestSpecification requestSpecification = getRequestSpecification(baseUrl, basePath, options);

		Response response = requestSpecification.post().then().extract().response();
		logRequestInReport(requestSpecification, null);
		logResponseInReport(response);
		return response;
	}

	public static Response performPost(String baseUrl, String basePath, Booking requestPayload) {

		RequestOptions options = RequestOptions.builder().body(requestPayload).build();
		RequestSpecification requestSpecification = getRequestSpecification(baseUrl, basePath, options);

		Response response = requestSpecification.post();
		logRequestInReport(requestSpecification, null);
		logResponseInReport(response);
		return response;
	}

	public static Response performPost(String baseUrl, String basePath, CreateBooking requestPayload) {

		RequestOptions options = RequestOptions.builder().body(requestPayload).build();
		RequestSpecification requestSpecification = getRequestSpecification(baseUrl, basePath, options);

		Response response = requestSpecification.post();
		logRequestInReport(requestSpecification, null);
		logResponseInReport(response);
		return response;
	}

	public static Response performPost(String baseUrl, String basePath, BookingTestData requestPayload) {

		RequestOptions options = RequestOptions.builder().body(requestPayload).build();
		RequestSpecification requestSpecification = getRequestSpecification(baseUrl, basePath, options);

		Response response = requestSpecification.post();
		logRequestInReport(requestSpecification, null);
		logResponseInReport(response);
		return response;
	}

	public static Response performGetBookingIds(String baseUrl, String basePath) {

		RequestOptions options = RequestOptions.builder().build();

		RequestSpecification requestSpec = getRequestSpecification(baseUrl, basePath, options);

		Response response = requestSpec.get();
		logRequestInReport(requestSpec, null);
		logResponseInReport(response);
		return response;
	}

	public static Response performGetBooking(String baseUrl, String basePath, Integer bookingId) {
		// RequestSpecification requestSpecification = getRequestSpecification(baseUrl,
		// basePath, bookingId, null, null);

		RequestOptions options = RequestOptions.builder().pathParam("bookingId", bookingId).build();

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("bookingId", bookingId);

		RequestSpecification requestSpecification = getRequestSpecification(baseUrl, basePath, options);

		Response response = requestSpecification.get();

		logRequestInReport(requestSpecification, bookingId);
		logResponseInReport(response);
		return response;
	}

	public static Response performTokenPost(String baseUrl, String basePath, TokenCredentials tokenPayload) {
		RequestOptions options = RequestOptions.builder().body(tokenPayload).build();

		return getRequestSpecification(baseUrl, basePath, options).post();
	}

	public static Response performUpdate(String baseUrl, String basePath, int bookingId, Booking updatedPayload,
			Map<String, String> headers) {

		RequestOptions options = RequestOptions.builder().pathParam("bookingId", bookingId).headers(headers)
				.body(updatedPayload).build();

		RequestSpecification requestSpecification = getRequestSpecification(baseUrl, basePath, options);
		Response response = requestSpecification.put();
		logRequestInReport(requestSpecification, bookingId);
		logResponseInReport(response);
		return response;
	}

	public static Response performPartialUpdate(String baseUrl, String basePath, int bookingId,
			Map<String, Object> updatedPayload, Map<String, String> headers) {
		RequestOptions options = RequestOptions.builder().pathParam("bookingId", bookingId).body(updatedPayload)
				.headers(headers).build();
		RequestSpecification requestSpec = getRequestSpecification(baseUrl, basePath, options);
		Response response = requestSpec.put();
		logRequestInReport(requestSpec, bookingId);
		logResponseInReport(response);
		return response;

	}

	public static Response performDelete(String baseUrl, String basePath, Integer bookingId,
			Map<String, String> headers) {
		RequestOptions options = RequestOptions.builder().pathParam("bookingId", bookingId).headers(headers).build();
		RequestSpecification requestSpec = getRequestSpecification(baseUrl, basePath, options);
		Response response = requestSpec.delete();
		logRequestInReport(requestSpec, bookingId);
		logResponseInReport(response);
		return response;

	}

	public static Response performPostUsingJson(String baseUrl, String basePath, Map<String, Object> bookingData) {
		String payload = readTemplate();

		// Replace placeholders
		for (Map.Entry<String, Object> entry : bookingData.entrySet()) {
			payload = payload.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
		}
		RequestOptions options = RequestOptions.builder().body(payload).build();
		RequestSpecification requestSpec = getRequestSpecification(baseUrl, basePath, options);
		Response response = requestSpec.when().post().then().extract().response();
		logRequestInReport(requestSpec, null);
		logResponseInReport(response);
		return response;
	}

	public static Response performGetClaims(String baseUrl, String basePath) {
		RequestOptions options = RequestOptions.builder().build();
		RequestSpecification requestSpec = getRequestSpecification(baseUrl, basePath, options);
		return requestSpec.when().get();
	}

	public static Response getCookieAccessToken(TokenCredentials tokenPayload) {

		return ApiClient.performTokenPost(PropertyUtils.get(ConfigProperties.BASEURL), EndPoints.AUTH, tokenPayload);
	}

	public static Response performCustomerLogin(String baseUrl, String basePath, CustomerCredentials requestPayload) {
		RequestOptions options = RequestOptions.builder().body(requestPayload).build();
		RequestSpecification requestSpecification = getRequestSpecification(baseUrl, basePath, options);
		Response response = requestSpecification.when().post();
		logRequestInReport(requestSpecification, null);
		logResponseInReport(response);
		return response;
	}

	public static Response getCustomerData(String baseUrl, String basePath, RequestOptions options) {
		RequestSpecification requestSpecification = getRequestSpecification(baseUrl, basePath, options);
		Response response = requestSpecification.when().get();
		logRequestInReport(requestSpecification, null);
		logResponseInReport(response);
		return response;
	}

	private static String readTemplate() {
		String jsonString = "";
		try {
			jsonString = FileUtils.readFileToString(new File("src/test/resources/templates/booking_template.json"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
