package com.mbm.bookingpojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BookingResponse {
	@JsonProperty("bookingid")
	private int bookingid;

	@JsonProperty("booking")
	private Booking booking;
}
