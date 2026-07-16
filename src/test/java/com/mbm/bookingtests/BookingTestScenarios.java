package com.mbm.bookingtests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mbm.bookingpojos.CreateBooking;
import com.mbm.client.AssertionUtil;
import com.mbm.reports.ExtentReport;

import io.restassured.response.Response;

public class BookingTestScenarios extends BookingAPIs {

	@Test(dataProvider = "BookingDataScenarioExcel", dataProviderClass = com.mbm.utils.DataProviderUtils.class)
	public void createBookingAndVerifyResponse(CreateBooking createBooking)
			throws JsonMappingException, JsonProcessingException {
		ExtentReport
				.createTest("Test Name - " + createBooking.getScenarioId() + " " + createBooking.getScenarioDesc());

		Response response = createBooking(createBooking);
		if (createBooking.getExpectedStatusCode() != 200) {
			if (createBooking.getScenarioId().equalsIgnoreCase("CreateBookig_DuplicateID"))
				response = createBooking(createBooking);
			Assert.assertEquals(response.getStatusCode(), createBooking.getExpectedStatusCode());
			// Assert.assertEquals(response.jsonPath().getString("message"),
			// createBooking.getExpectedErrorMessage());
		} else {
			Map<String, Object> expectedValueMap = new HashMap<>();
			expectedValueMap.put("booking.firstname", createBooking.getFirstname());
			expectedValueMap.put("booking.lastname", createBooking.getLastname());
			expectedValueMap.put("booking.totalprice", createBooking.getTotalprice());
			expectedValueMap.put("booking.depositpaid", createBooking.getDepositpaid());
			expectedValueMap.put("booking.bookingdates.checkin", createBooking.getBookingdates().getCheckin());
			expectedValueMap.put("booking.bookingdates.checkout", createBooking.getBookingdates().getCheckout());
			expectedValueMap.put("booking.additionalneeds", createBooking.getAdditionalneeds());

			if (createBooking.getScenarioId().equalsIgnoreCase("CreateBooking_WithoutID")) {
				expectedValueMap.remove("firstname");
			}
			AssertionUtil.assertExpectedValuesWithJsonPath(response, expectedValueMap);
		}
	}

}
