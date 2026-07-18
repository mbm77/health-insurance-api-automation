package com.mbm.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");

	public static String getCheckinDate() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public static String getCheckoutDate() {
		return LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static String getDateFormat(String date) {

	    String[] patterns = {
	            "yyyy-MM-dd",
	            "M/d/yy",
	            "MM/dd/yy",
	            "M/d/yyyy",
	            "MM/dd/yyyy"
	    };

	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    for (String pattern : patterns) {
	        try {
	            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(pattern);
	            LocalDate localDate = LocalDate.parse(date, inputFormatter);
	            return localDate.format(outputFormatter);
	        } catch (DateTimeParseException e) {
	            // Try the next pattern
	        }
	    }

	    throw new IllegalArgumentException("Unsupported date format: " + date);
	}

	public static String getCurrentTimeStamp() {
		return LocalDateTime.now().format(dateTimeFormatter);
	}
}
