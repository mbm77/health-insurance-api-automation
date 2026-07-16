package com.mbm.bookingtests;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class JsonDataDrivenTests {

	private BookingAPIs bookingAPIs = new BookingAPIs();

	@Test(dataProvider = "bookingDataFromJsonFile", dataProviderClass = com.mbm.utils.DataProviderUtils.class)
	public void createBookingTest(Map<String, Object> bookingData, Method m) throws Exception {
		// ExtentReportManager
		// .createTest("Test Name "+m.getName());
		Response response = bookingAPIs.createBookingByJson(bookingData);
		response.prettyPrint();

	}

}
