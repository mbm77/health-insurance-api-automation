package com.mbm.bookingtests;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.Test;

import com.mbm.api.BookingApi;

import io.restassured.response.Response;

public class JsonDataDrivenTests {

	private BookingApi bookingAPIs = new BookingApi();

	@Test(dataProvider = "bookingDataFromJsonFile", dataProviderClass = com.mbm.utils.DataProviderUtils.class)
	public void createBookingTest(Map<String, Object> bookingData, Method m) throws Exception {
		// ExtentReportManager
		// .createTest("Test Name "+m.getName());
		Response response = bookingAPIs.createBookingByJson(bookingData);
		response.prettyPrint();

	}

}
