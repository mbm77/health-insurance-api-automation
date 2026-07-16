package com.mbm.bookingpojos;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mbm.utils.DateUtils;
import com.mbm.utils.RandomDataGenerator;
import com.mbm.utils.RandomDataTypeNames;

import groovy.transform.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
public class Booking {
	@Builder.Default
	@JsonProperty("firstname")
	private String firstname = RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME);

	@Builder.Default
	@JsonProperty("lastname")
	private String lastname = RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.LASTNAME);

	@Builder.Default
	@JsonProperty("totalprice")
	private int totalprice = RandomDataGenerator.getRandomNumber(2000, 4000);

	@Builder.Default
	@JsonProperty("depositpaid")
	private boolean depositpaid = RandomDataGenerator.getRandomBoolean();

	@Builder.Default
	@JsonProperty("bookingdates")
	// private BookingDates bookingdates = new
	// BookingDates(DateUtils.getCheckinDate(), DateUtils.getCheckoutDate());
	private BookingDates bookingdates = BookingDates.builder().build();

	@Builder.Default
	@JsonProperty("additionalneeds")
	private String additionalneeds = RandomDataGenerator.getRandomAlphabets(10);
}