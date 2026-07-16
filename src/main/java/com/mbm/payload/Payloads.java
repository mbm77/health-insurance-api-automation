package com.mbm.payload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mbm.bookingpojos.Booking;
import com.mbm.bookingpojos.BookingDates;
import com.mbm.bookingpojos.TokenCredentials;
import com.mbm.enums.ConfigProperties;
import com.mbm.insurance_pojo.CustomerCredentials;
import com.mbm.utils.DateUtils;
import com.mbm.utils.PropertyUtils;
import com.mbm.utils.RandomDataGenerator;
import com.mbm.utils.RandomDataTypeNames;

public class Payloads {

	private Payloads() {
	}

	public static String getCreateBookingPayload(String firstname, String lastname, int totalprice, boolean depositpaid,
			List<String> bookingdates, String additionalneeds) {
		String payload = "{\r\n" + "    \"firstname\": \"" + firstname + "\",\r\n" + "    \"lastname\": \"" + lastname
				+ "\",\r\n" + "    \"totalprice\": " + totalprice + ",\r\n" + "    \"depositpaid\": " + depositpaid
				+ ",\r\n" + "    \"bookingdates\": {\r\n" + "        \"checkin\": \"" + bookingdates.get(0) + "\",\r\n"
				+ "        \"checkout\": \"" + bookingdates.get(1) + "\"\r\n" + "    },\r\n"
				+ "    \"additionalneeds\": \"" + additionalneeds + " bowls\"\r\n" + "}";
		return payload;
	}

	public static Map<String, Object> getCreateBookingPayloadFromMap(String firstname, String lastname, int totalprice,
			boolean depositpaid, List<String> bookingdates, String additionalneeds) {
		Map<String, Object> payload = new HashMap<>();
		Map<String, Object> bookingdatesMap = new HashMap<>();
		bookingdatesMap.put("checkin", bookingdates.get(0));
		bookingdatesMap.put("checkout", bookingdates.get(1));
		payload.put("firstname", firstname);
		payload.put("lastname", lastname);
		payload.put("totalprice", totalprice);
		payload.put("depositpaid", depositpaid);
		payload.put("bookingdates", bookingdatesMap);
		payload.put("additionalneeds", additionalneeds);
		return payload;

	}

	public static Map<String, Object> getCreateBookingPayloadFromMap() {

		Map<String, Object> payload = new HashMap<>();
		Map<String, Object> bookingdatesMap = new HashMap<>();
		bookingdatesMap.put("checkin", DateUtils.getCheckinDate());
		bookingdatesMap.put("checkout", DateUtils.getCheckoutDate());
		payload.put("firstname", RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME));
		payload.put("lastname", RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.LASTNAME));
		payload.put("totalprice", RandomDataGenerator.getRandomNumber(2000, 4000));
		payload.put("depositpaid", true);
		payload.put("bookingdates", bookingdatesMap);
		payload.put("additionalneeds", RandomDataGenerator.getRandomAlphabets(10));
		return payload;

	}

	public static Booking getCreateBookingPayloadFromPojo() {
		return Booking.builder().firstname(RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME))
				.lastname(RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.LASTNAME))
				.totalprice(RandomDataGenerator.getRandomNumber(2000, 4000))
				.depositpaid(RandomDataGenerator.getRandomBoolean())
				.bookingdates(BookingDates.builder().checkin(DateUtils.getCheckinDate())
						.checkout(DateUtils.getCheckoutDate()).build())
				.additionalneeds(RandomDataGenerator.getRandomAlphabets(10)).build();
	}

	public static TokenCredentials getCreateTokenPayload() {
		return TokenCredentials.builder().username(PropertyUtils.get(ConfigProperties.USERNAME))
				.password(PropertyUtils.get(ConfigProperties.PASSWORD)).build();
	}

	public static Booking getUpdateBookingPayloadFromPojo(Booking createBooking) {
		return createBooking.toBuilder().firstname(RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME))
				.lastname(RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.LASTNAME))
				.totalprice(RandomDataGenerator.getRandomNumber(2000, 4000))
				.depositpaid(RandomDataGenerator.getRandomBoolean())
				.bookingdates(BookingDates.builder().checkin(DateUtils.getCheckinDate())
						.checkout(DateUtils.getCheckoutDate()).build())
				.additionalneeds(RandomDataGenerator.getRandomAlphabets(10)).build();
	}

	public static Map<String, Object> getPartialUpdateBookingPayload() {
		Map<String, Object> payload = new HashMap<>();
		payload.put("additionalneeds", RandomDataGenerator.getRandomAlphabets(10));
		return payload;
	}

	
}
