package com.mbm.listeners;

import java.util.Arrays;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.mbm.auth.TokenManager;
import com.mbm.enums.ConfigProperties;
import com.mbm.filelogging.FileLogManager;
import com.mbm.filelogging.FileLogger;
import com.mbm.filelogutils.CreateDirForFileLogUtils;
import com.mbm.reports.ExtentLogger;
import com.mbm.reports.ExtentManager;
import com.mbm.reports.ExtentReport;
import com.mbm.utils.PropertyUtils;

public class ListenerClass implements ITestListener, ISuiteListener {

	private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

	@Override
	public void onStart(ISuite suite) {
		
		

		        TokenManager.getInstance()
		                    .userLogin(
		                    	PropertyUtils.get(ConfigProperties.USERNAME), 
		                    	PropertyUtils.get(ConfigProperties.PASSWORD)
		                    );
		 
		String reportFileName = ExtentReport.getReportNameWithTimeStamp();
		String fullReportPath = System.getProperty("user.dir") + "/reports/" + reportFileName;
		ExtentReport.createInstance(fullReportPath, "Test API Automation Report", "Test Execution Report");
		CreateDirForFileLogUtils.cleanPreviousLogs("logs");
	}

	@Override
	public void onFinish(ISuite suite) {
		ExtentReport.flushReport();
	}

	@Override
	public void onTestStart(ITestResult result) {
		startTime.set(System.currentTimeMillis()); // record start time
		String testName = result.getTestClass().getName() + " - " + result.getMethod().getMethodName();
		ExtentReport.createTest(testName);
		String testNameForFileLog = testName + "_H" + Arrays.deepHashCode(result.getParameters()) + "_T"
				+ Thread.currentThread().getId() + "_" + System.nanoTime(); // more precise than millis

		FileLogManager.init(testNameForFileLog);
		FileLogger.logInfoDetails("Test Started: " + testName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		long duration = System.currentTimeMillis() - startTime.get();
		FileLogger
				.pass("Test Passed: " + result.getMethod().getMethodName() + " | Duration: " + duration / 1000.0 + "s");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		long duration = System.currentTimeMillis() - startTime.get();
		ensureExtentTestCreated(result);

		ExtentLogger.logFailureDetails(result.getThrowable().getMessage());

		String stackTrace = Arrays.toString(result.getThrowable().getStackTrace());
		stackTrace = stackTrace.replace(",", "<br>");
		String formattedTrace = "<details><summary>Click Here To See Exception Logs</summary>" + stackTrace
				+ "</details>";

		ExtentLogger.logExceptionDetails(formattedTrace);
		FileLogger
				.fail("Test Failed: " + result.getMethod().getMethodName() + " | Duration: " + duration / 1000.0 + "s");
		FileLogger.logExceptionDetails(result.getThrowable().toString());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		long duration = System.currentTimeMillis() - startTime.get();
		ensureExtentTestCreated(result);
		ExtentLogger.logFailureDetails("Test skipped: " + result.getSkipCausedBy().toString());
		FileLogger.skip(
				"Test Skipped: " + result.getMethod().getMethodName() + " | Duration: " + duration / 1000.0 + "s");
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}

	/**
	 * Ensures an ExtentTest is created if DataProvider or setup failures prevent
	 * onTestStart() from running.
	 */
	private void ensureExtentTestCreated(ITestResult result) {
		if (ExtentManager.getExtentTest() == null) {
			String testName = result.getTestClass().getName() + " - " + result.getMethod().getMethodName();
			ExtentReport.createTest(testName);
		}
	}
}
