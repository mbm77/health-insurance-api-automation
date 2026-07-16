package com.mbm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import com.mbm.constants.FrameworkConstants;
import com.mbm.enums.ConfigProperties;

public class PropertyUtils {
	private static Properties property;

	static {
		try (FileInputStream fis = new FileInputStream(FrameworkConstants.getConfigFilePath());) {

			property = new Properties();
			property.load(fis);
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public static String get(ConfigProperties key) {
		if (Objects.isNull(key) || Objects.isNull(property.getProperty(key.name().toLowerCase()))) {
			throw new RuntimeException("Property name " + key + " is not found. Please check conig.properies file");
		}
		return property.getProperty(key.name().toLowerCase());
	}

}
