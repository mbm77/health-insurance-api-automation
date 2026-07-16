package com.mbm.reports;

import java.util.Objects;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {
	
	private ExtentManager() {
		
	}
	
	private static ThreadLocal<ExtentTest> extTest = new ThreadLocal<>();
	
	
	public static ExtentTest getExtentTest() {
		return extTest.get();
	}
	
	public static void setExtentTest(ExtentTest extentTest) {
		if(Objects.nonNull(extentTest)) {
			extTest.set(extentTest);
		}
		
	}
	
	public static void unload() {
		extTest.remove();
	}
}
