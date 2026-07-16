package com.mbm.config;

import com.mbm.constants.FrameworkConstants;
import com.mbm.endpointpojos.Config;
import com.mbm.utils.JsonUtils;

public class EnvironmentConfig {

	private static Config dataFromJsonFile;

	static {
		String env = System.getProperty("env", "qa");
		switch (env) {
		case "qa":
			dataFromJsonFile = JsonUtils.getJsonDataAsMap(FrameworkConstants.getQAEndpoints());
			break;
		case "dev":
			dataFromJsonFile = JsonUtils.getJsonDataAsMap(FrameworkConstants.getDevEndpoints());
			break;
		default:
			throw new IllegalArgumentException("Unsupported environment: " + env);
		}
	}

	public static Config getConfig() {
		return dataFromJsonFile;
	}
}
