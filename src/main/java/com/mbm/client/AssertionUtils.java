package com.mbm.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.mbm.reports.ExtentLogger;
import com.mbm.reports.ExtentManager;

import io.restassured.response.Response;

public class AssertionUtils {
	public static void assertExpectedValuesWithJsonPath(Response response, Map<String, Object> expectedValuesMap) {
		List<AssertionKeys> actualValuesMap = new ArrayList<>();
		actualValuesMap.add(new AssertionKeys("JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
		boolean allMatched = true;
		Set<String> jsonPaths = expectedValuesMap.keySet();
		for (String jsonPath : jsonPaths) {
			Optional<Object> actualValue = Optional.ofNullable(response.jsonPath().get(jsonPath));
			if (actualValue.isPresent()) {
				Object value = actualValue.get();
				if (value.equals(expectedValuesMap.get(jsonPath))) {
					actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "MATCHED"));
				} else {
					allMatched = false;
					actualValuesMap
							.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "NOT MATCHED"));
				}
			} else {
				allMatched = false;
				actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath),
						"Response Value Not Found", "NOT MATCHED"));
			}

		}
		if (allMatched) {
			ExtentLogger.logPassDetails("All assertions are passed. 😊😊😊😊😊");
		} else {
			ExtentLogger.logFailureDetails("Some Assertions are failed 😒😒😒😒😒");
		}

		String[][] arrayOfAssertions = actualValuesMap.stream()
				.map(assertions -> new String[] { assertions.getJsonPath(),
						String.valueOf(assertions.getExpectedValue()), String.valueOf(assertions.getActualValue()),
						assertions.getResult() })
				.toArray(String[][]::new);
		ExtentManager.getExtentTest().info(MarkupHelper.createTable(arrayOfAssertions));

	}

}
