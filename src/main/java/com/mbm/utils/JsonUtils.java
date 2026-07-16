package com.mbm.utils;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbm.endpointpojos.Config;

public class JsonUtils {
	private static ObjectMapper mapper = new ObjectMapper();
	public static Config config;
	public static Config getJsonDataAsMap(String endpoinstJsonFile) {
		try {
           
            config = mapper.readValue(new File(endpoinstJsonFile), Config.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.json", e);
        }
		return config;

	}
}
