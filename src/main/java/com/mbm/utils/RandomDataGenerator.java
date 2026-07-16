package com.mbm.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

import net.datafaker.Faker;

public class RandomDataGenerator {

	public static Faker faker = new Faker();

	public static String getRandomDataFor(RandomDataTypeNames randomDataTypesNames) {
		switch (randomDataTypesNames) {
		case FIRSTNAME:
			return faker.name().firstName();
		case LASTNAME:
			return faker.name().lastName();
		default:
			return "No Data Matched";
		}
	}

	public static int getRandomNumber(int min, int max) {
		return faker.number().numberBetween(min, max);
	}

	@SuppressWarnings("deprecation")
	public static String getRandomAlphabets(int count) {
		return RandomStringUtils.randomAlphabetic(10);
	}

	public static boolean getRandomBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}

}
