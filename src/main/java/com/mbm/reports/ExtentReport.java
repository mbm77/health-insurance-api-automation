package com.mbm.reports;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {

	public static ExtentReports extentReports;

	public static void createInstance(String filename, String documentTitle, String reportName) {
		if (Objects.isNull(extentReports)) { // ✅ Avoid re-initialization
			extentReports = new ExtentReports();
			ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(filename);
			extentSparkReporter.config().setTheme(Theme.DARK);
			extentSparkReporter.config().setDocumentTitle(documentTitle);
			extentSparkReporter.config().setReportName(reportName);
			extentSparkReporter.config().setEncoding("UTF-8");
			extentReports.attachReporter(extentSparkReporter);
		}
	}

	public static String getReportNameWithTimeStamp() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		String formattedTime = dateTimeFormatter.format(localDateTime);
		//String reportName = "TestReport" + formattedTime + ".html";
		String reportName = "ExtentReport.html";
		return reportName;
	}

	public static void flushReport() {
		if (Objects.nonNull(extentReports)) {
			extentReports.flush();
		}
		ExtentManager.unload();

	}

	public static void createTest(String testCaseName) {
		ExtentTest test = extentReports.createTest(testCaseName);
		ExtentManager.setExtentTest(test);
	}

}
