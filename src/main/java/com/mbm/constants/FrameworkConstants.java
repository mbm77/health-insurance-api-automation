package com.mbm.constants;

public class FrameworkConstants {
	private FrameworkConstants() {
	}

	private static final String RESOURCEPATH = System.getProperty("user.dir") + "/src/test/resources/";
	private static final String QAENDPOINTS = RESOURCEPATH + "bookings/qa/bookingAPIData.json";
	private static final String DEVENDPOINTS = RESOURCEPATH + "bookings/dev/bookingAPIData.json";
	private static final String CONFIGFILEPATH = RESOURCEPATH + "config/config.properties";
	private static final String EXCELPATH = RESOURCEPATH + "test-data/BookingData.xlsx";
	private static final String JSONBOOKINGDATA = RESOURCEPATH + "test-data/booking_data.json";
	private static final String LOGINDATASHEET = "login_data";
	private static final String CUSTOMER_PROFILE_DATA_SHEET = "customer_profile_data"; 

	public static String getResourcePath() {
		return RESOURCEPATH;
	}

	public static String getQAEndpoints() {
		return QAENDPOINTS;
	}

	public static String getDevEndpoints() {
		return DEVENDPOINTS;
	}

	public static String getConfigFilePath() {
		return CONFIGFILEPATH;
	}

	public static String getExcelPath() {
		return EXCELPATH;
	}

	public static String getJsonBookingData() {
		return JSONBOOKINGDATA;
	}
	
	public static String getLoginDataSheet() {
		return LOGINDATASHEET;
	}
	
	public static String getCustomerProfileData() {
		return CUSTOMER_PROFILE_DATA_SHEET;
	}

}
