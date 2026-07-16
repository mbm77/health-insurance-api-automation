package com.mbm.bookingtests;

import java.util.Arrays;

import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.mbm.filelogging.FileLogManager;

public class BaseTest {

	//@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Object[] testData, ITestResult result) {
		String testName = result.getMethod().getMethodName();
		if (testData != null && testData.length > 0) {
			testName = testName + "_" + Arrays.toString(testData);
		}
		ThreadContext.put("testName", testName);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		FileLogManager.close();
	}

}
