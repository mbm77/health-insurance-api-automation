package com.mbm.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.mbm.reports.ExtentLogger;
import com.mbm.reports.ExtentManager;

import io.restassured.response.Response;

public class AssertionUtil {

	public static void assertExpectedValuesWithJsonPath(Response response, Map<String, Object> expectedValuesMap) {
		List<AssertionKeys> assertionResults = new ArrayList<>();
		assertionResults.add(new AssertionKeys("JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));

		boolean allMatched = true;

		for (Map.Entry<String, Object> entry : expectedValuesMap.entrySet()) {
			String jsonPath = entry.getKey();
			Object expectedValue = entry.getValue();
			Object actualValue = response.jsonPath().get(jsonPath);

			if (actualValue == null) {
				allMatched = false;
				assertionResults
						.add(new AssertionKeys(jsonPath, expectedValue, "Response Value Not Found", "NOT MATCHED"));
			} else if (actualValue.equals(expectedValue)) {
				assertionResults.add(new AssertionKeys(jsonPath, expectedValue, actualValue, "MATCHED"));
			} else {
				allMatched = false;
				assertionResults.add(new AssertionKeys(jsonPath, expectedValue, actualValue, "NOT MATCHED"));
			}
		}

		logAssertions(assertionResults, allMatched, response);

		// ✅ Fails the test if any assertion fails
		Assert.assertTrue(allMatched, "❌ Test failed: Some JSON path assertions did not match!");

	}

	private static void logAssertions(List<AssertionKeys> assertionResults, boolean allMatched, Response response) {
		if (allMatched) {
			ExtentLogger.logPassDetails("✅ All assertions passed successfully!");
		} else {

			String filePath = saveResponseToFile(response);

			ExtentLogger.logFailureDetails("❌ Some assertions failed! Check details below.");

			if (filePath != null) {
				try {
					ExtentManager.getExtentTest().addScreenCaptureFromPath(filePath, "Failed Response Screenshot");
				} catch (Exception e) {
					ExtentLogger.logFailureDetails("⚠️ Failed to attach response screenshot: " + e.getMessage());
				}
			}
		}

		String[][] assertionTable = assertionResults.stream()
				.map(assertion -> new String[] { assertion.getJsonPath(), String.valueOf(assertion.getExpectedValue()),
						String.valueOf(assertion.getActualValue()), assertion.getResult() })
				.toArray(String[][]::new);

		ExtentManager.getExtentTest().info(MarkupHelper.createTable(assertionTable));
	}
	
	 private static String saveResponseToFile(Response response) {
	        try {
	            String filePath = System.getProperty("user.dir")+"/test-output/API_Response.json";
	            Files.write(Paths.get(filePath), response.prettyPrint().getBytes());
	            return filePath;
	        } catch (IOException e) {
	            ExtentLogger.logFailureDetails("⚠️ Error saving response file: " + e.getMessage());
	            return null;
	        }
	    }
}
