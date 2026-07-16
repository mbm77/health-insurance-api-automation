package com.mbm.bookingpojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mbm.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookingDates {
	@Builder.Default
	@JsonProperty("checkin")
	private String checkin = DateUtils.getCheckinDate();

	@Builder.Default
	@JsonProperty("checkout")
	private String checkout = DateUtils.getCheckoutDate();
}
