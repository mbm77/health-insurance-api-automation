package com.mbm.bookingtests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbm.api.BookingAPIs;
import com.mbm.bookingpojos.Booking;
import com.mbm.bookingpojos.BookingResponse;
import com.mbm.bookingpojos.TokenCredentials;
import com.mbm.client.AssertionUtil;
import com.mbm.payload.Payloads;
import com.mbm.utils.DateUtils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class BookingTests extends BaseTest {

	private Booking createdBooking;
	private Integer bookingId;

	BookingAPIs bookingAPIs = new BookingAPIs();

	@Test
	public void createBookingUsingJsonString() throws StreamReadException, DatabindException, IOException {

		List<String> bookingdates = Arrays.asList("2018-01-01", "2019-01-01");
		String payload = Payloads.getCreateBookingPayload("java", "selenium", 1000, true, bookingdates, "super bowls");

		Response response = bookingAPIs.createBooking(payload);

		response.prettyPrint();
		Assert.assertEquals(response.statusCode(), 200);

	}

	@Test
	public void createBookingUsingMap() throws StreamReadException, DatabindException, IOException {

		List<String> bookingdates = Arrays.asList(DateUtils.getCheckinDate(), DateUtils.getCheckoutDate());

		Map<String, Object> payload = Payloads.getCreateBookingPayloadFromMap("java", "selenium", 1000, true,
				bookingdates, "super bowls");
		// System.out.println(payload);System.exit(0);
		Response response = bookingAPIs.createBooking(payload);
		response.prettyPrint();
		Assert.assertEquals(response.statusCode(), 200);

	}

	@Test
	public void createBookingUsingPojWithBuilder() throws StreamReadException, DatabindException, IOException {

		Booking payloadFromBuilder = Payloads.getCreateBookingPayloadFromPojo();
		Response response = bookingAPIs.createBooking(payloadFromBuilder);

		response.prettyPrint();
		Assert.assertEquals(response.statusCode(), 200);

	}

	@Test
	public void createBookingUsingJavaObject() throws StreamReadException, DatabindException, IOException {

		Booking payload = new Booking();
		// String serializeData = new
		// ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
		Response response = bookingAPIs.createBooking(payload);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);

	}

	@Test(priority = 1, dataProvider = "BookingData", dataProviderClass = com.mbm.utils.DataProviderUtils.class)

	public void createBookingAndVerifyResponse(Booking requestPayload, Method m)
			throws JsonMappingException, JsonProcessingException {
		Response response = bookingAPIs.createBooking(requestPayload);
		bookingId = response.jsonPath().getInt("bookingid");
		createdBooking = requestPayload;
		// Payloads.booking = response.jsonPath().getObject("booking", Booking.class);
		Map<String, Object> expectedValueMap = new HashMap<>();
		expectedValueMap.put("booking.firstname", requestPayload.getFirstname());
		expectedValueMap.put("booking.lastname", requestPayload.getLastname());
		expectedValueMap.put("booking.totalprice", requestPayload.getTotalprice());
		expectedValueMap.put("booking.depositpaid", requestPayload.isDepositpaid());
		expectedValueMap.put("booking.bookingdates.checkin", requestPayload.getBookingdates().getCheckin());
		expectedValueMap.put("booking.bookingdates.checkout", requestPayload.getBookingdates().getCheckout());
		expectedValueMap.put("booking.additionalneeds", requestPayload.getAdditionalneeds());
		// AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
		AssertionUtil.assertExpectedValuesWithJsonPath(response, expectedValueMap);
		// JSONPath assertion (Direct)
		Assert.assertEquals(response.jsonPath().getString("booking.firstname"), requestPayload.getFirstname(),
				"Firstname mismatch!");
		Assert.assertEquals(response.jsonPath().getString("booking.lastname"), requestPayload.getLastname(),
				"Lastname mismatch!");

		// Deserialize into BookingResponse (which contains Booking object)
		ObjectMapper objectMapper = new ObjectMapper();
		BookingResponse createBookingResponse = objectMapper.readValue(response.asString(), BookingResponse.class);
		Booking responseBooking = createBookingResponse.getBooking(); // Extract actual booking data
		// Assertions on deserialized object
		Assert.assertEquals(responseBooking.getFirstname(), requestPayload.getFirstname(), "Firstname mismatch!");
		Assert.assertEquals(responseBooking.getLastname(), requestPayload.getLastname(), "Lastname mismatch!");
		Assert.assertEquals(responseBooking.getTotalprice(), requestPayload.getTotalprice(), "Total price mismatch!");
		Assert.assertEquals(responseBooking.isDepositpaid(), requestPayload.isDepositpaid(), "Deposit paid mismatch!");
		Assert.assertEquals(responseBooking.getAdditionalneeds(), requestPayload.getAdditionalneeds(),
				"Additional needs mismatch!");
		Assert.assertEquals(responseBooking.getBookingdates().getCheckin(),
				requestPayload.getBookingdates().getCheckin(), "Checkin date mismatch!");
		Assert.assertEquals(responseBooking.getBookingdates().getCheckout(),
				requestPayload.getBookingdates().getCheckout(), "Checkout date mismatch!");

		// Verify Booking ID is present
		Assert.assertTrue(createBookingResponse.getBookingid() > 0, "Invalid Booking ID!");

		Assert.assertEquals(responseBooking, requestPayload);

		System.out.println("✅ All Assertions Passed! in " + m.getName());

	}

	@Test(priority = 0)
	public void getBookingIdsAndVerifyResponse(Method m) {
		Response response = bookingAPIs.getBookingIds();
		/*
		String jsonStr =   "{
				"section":[
				           {"section":{"lat":"12.9716",
				        	   			"lang":"77.5946"}},
				           {"section":{"lat":"28.6139"},
				        	   			"lang":"772090"}},
							{"section":{"lat":"19.0760",
										"lang":"72.8777"}}
				           ]
		}";
		*/
		Assert.assertEquals(response.statusCode(), 200);
		System.out.println("✅ All Assertions Passed! in " + m.getName());
	}

	@Test(priority = 2)
	public void getBookingDetailsAndVerifyResponse(Method m) throws IOException {
		Response response = bookingAPIs.getBookingDetails(bookingId);
		// response.prettyPrint();
		// System.out.println(response.jsonPath().getString("firstname"));
		// String path =
		// System.getProperty("user.dir")+"/src/test/resources/schemas/getBookingSchema.json";
		// String jsonSchema = FileUtils.readFileToString(new File(path),"UTF-8");
		response.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/getBookingSchema.json"));
		// Assert.assertEquals(response.jsonPath().getString("firstname"), "tamoto");
		// Assert.assertEquals(response.jsonPath().getString("lastname"), "beto");
		System.out.println("✅ All Assertions Passed! in " + m.getName());

	}

	@Test(priority = 3)
	public void getAccessTokenAndVerifyResponse(Method m) {
		TokenCredentials tokenPayload = Payloads.getCreateTokenPayload();
		Response response = bookingAPIs.getAccessToken(tokenPayload);
		String accessToken = response.jsonPath().getString("token");
		System.out.println(accessToken);
		System.out.println("✅ All Assertions Passed! in " + m.getName());
	}

	@Test(priority = 4)
	public void getUpdateBookingAndVerifyResponse(Method m) {
		Booking updatedPayload = Payloads.getUpdateBookingPayloadFromPojo(createdBooking);
		Response response = bookingAPIs.updateBooking(bookingId, updatedPayload);
		createdBooking = updatedPayload;
		Assert.assertEquals(response.statusCode(), 200);
		System.out.println("✅ All Assertions Passed! in " + m.getName());

	}

	@Test(priority = 5)
	public void getPartialUpdateBooking(Method m) {
		Map<String, Object> partiallyUpdatedBooking = Payloads.getPartialUpdateBookingPayload();
		bookingAPIs.partiallyUpdateBooking(bookingId, partiallyUpdatedBooking);
		System.out.println("✅ All Assertions Passed! in " + m.getName());
	}

	@Test(priority = 6)
	public void getDeleteBooking(Method m) {

		Response response = bookingAPIs.deleteBooking(bookingId);
		Assert.assertEquals(response.statusCode(), 201);

		System.out.println("✅ All Assertions Passed! in " + m.getName());
	}

	// @Test
	public void verifyClaimsResponse() {
		Response response = bookingAPIs.getClaimsData();
		System.out.println(response.asString());
		// System.exit(0);
		// Address validations
		Assert.assertEquals(response.jsonPath().getList("customer.addresses").size(), 2);
		Assert.assertEquals(response.jsonPath().getString("customer.addresses[0].city"), "Hyderabad");
		Assert.assertEquals(response.jsonPath().getString("customer.addresses[1].state"), "Andhra Pradesh");

		// Nominee validations
		Assert.assertEquals(response.jsonPath().getList("policy.nominees").size(), 2);
		Assert.assertEquals(response.jsonPath().getString("policy.nominees[1].relationship"), "Son");

		// Claims validations
		Assert.assertEquals(response.jsonPath().getList("claims").size(), 2);
		Assert.assertEquals(response.jsonPath().getInt("claims[0].claimAmount"), 75000);
		Assert.assertEquals(response.jsonPath().getString("claims[1].status"), "PENDING");

		// Document validations
		Assert.assertEquals(response.jsonPath().getList("claims[0].documents").size(), 2);
		Assert.assertEquals(response.jsonPath().getString("claims[0].documents[0].documentType"), "Discharge Summary");
		Assert.assertTrue(response.jsonPath().getBoolean("claims[0].documents[1].verified"));
		Assert.assertEquals(response.jsonPath().getList("claims[1].documents").size(), 0);

		// Interview-level validations
		Assert.assertEquals(
				response.jsonPath().getString("claims.find { it.claimId == 'CLM1002' }.hospital.hospitalName"),
				"Yashoda Hospital");

		Assert.assertTrue(response.jsonPath().getList("claims.status").contains("APPROVED"));

		int totalPercentage = response.jsonPath().getList("policy.nominees.percentage", Integer.class).stream()
				.mapToInt(Integer::intValue).sum();

		Assert.assertEquals(totalPercentage, 100);

		List<Integer> claimAmounts = response.jsonPath().getList("claims.claimAmount");
		List<Integer> approvedAmounts = response.jsonPath().getList("claims.approvedAmount");

		for (int i = 0; i < claimAmounts.size(); i++) {
			Assert.assertTrue(approvedAmounts.get(i) <= claimAmounts.get(i));
		}

		Assert.assertTrue(response.jsonPath().getList("customer.addresses.country", String.class).stream()
				.allMatch(country -> country.equals("India")));

		Assert.assertTrue(response.jsonPath().getList("claims[0].documents.verified", Boolean.class).stream()
				.allMatch(Boolean::booleanValue));

		Assert.assertTrue(
				response.jsonPath().getList("claims.hospital.hospitalName", String.class).contains("Apollo Hospital"));

		long pendingCount = response.jsonPath().getList("claims.status", String.class).stream()
				.filter(status -> status.equals("PENDING")).count();

		Assert.assertEquals(pendingCount, 1);

		Assert.assertEquals(response.jsonPath().getInt("policy.nominees.find { it.name == 'Jane Doe' }.percentage"),
				60);

		Assert.assertNull(response.jsonPath().get("metadata.links.previous"));
	}

}
