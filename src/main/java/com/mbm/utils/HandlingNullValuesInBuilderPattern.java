package com.mbm.utils;

public class HandlingNullValuesInBuilderPattern {
	public static String handleNoData(String value) {
	    return (value == null || value.equalsIgnoreCase("NO_DATA")) ? null : value;
	}

	public static Integer handleNoDataInteger(String value) {
	    return (value == null || value.equalsIgnoreCase("NO_DATA")) ? null : Integer.parseInt(value);
	}

	public static Boolean handleNoDataBoolean(String value) {
	    return (value == null || value.equalsIgnoreCase("NO_DATA")) ? null : Boolean.parseBoolean(value);
	}


}
