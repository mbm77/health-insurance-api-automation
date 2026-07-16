package com.mbm.filelogging;

import java.util.List;

import com.mbm.utils.DateUtils;

import io.restassured.http.Header;

public class FileLogger {

	private FileLogger() {
	}

	public static void logPassDetails(String log) {
		FileLogManager.write("[PASS] " + DateUtils.getCurrentTimeStamp() + " " + log + "\n");
	}

	public static void logFailureDetails(String message) {
		FileLogManager.write("[FAIL] " + DateUtils.getCurrentTimeStamp() + " " + message + "\n");
	}

	public static void logExceptionDetails(String details) {
		FileLogManager.write("[EXCEPTION] " + DateUtils.getCurrentTimeStamp() + " " + details + "\n");
	}

	public static void logInfoDetails(String log) {
		FileLogManager.write("[INFO] " + DateUtils.getCurrentTimeStamp() + " " + log + "\n");
	}

	public static void logWarningDetails(String log) {
		FileLogManager.write("[WARNING] " + DateUtils.getCurrentTimeStamp() + " " + log + "\n");
	}

	public static void logJson(String json) {
		FileLogManager.write("[JSON] " + DateUtils.getCurrentTimeStamp() + " " + json + "\n");
	}

	public static void logHeaders(List<Header> headerList) {
		StringBuilder sb = new StringBuilder("[HEADERS] " + DateUtils.getCurrentTimeStamp() + "\n");
		sb.append("----------------------------------------------------" + "\n");
		headerList.forEach(header -> sb.append(header.getName()).append(": ").append(header.getValue()).append("\n"));
		sb.append("----------------------------------------------------" + "\n");
		FileLogManager.write(sb.toString());
	}

	public static void pass(String message) {
		logPassDetails(message);
	}

	public static void fail(String message) {
		logFailureDetails(message);
	}

	public static void skip(String message) {
		FileLogManager.write("[SKIP] " + DateUtils.getCurrentTimeStamp() + " " + message + "\n");
	}
}
