package com.mbm.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@SuperBuilder // Ensures builder pattern includes BasePojo fields
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.ALWAYS)  	// Includes all fields, even null
//@JsonInclude(JsonInclude.Include.NON_NULL) 	//Excludes only null values
//@JsonInclude(JsonInclude.Include.NON_EMPTY)	//Excludes null and empty collections/strings
@JsonInclude(JsonInclude.Include.NON_DEFAULT) 	/* If your API expects only modified or meaningful fields, NON_DEFAULT is
													a great choice */
public class CreateBooking extends BasePojo {
	private String firstname;
	private String lastname;
	private Integer totalprice;
	private Boolean depositpaid;
	private BookingDates bookingdates;
	private String additionalneeds;

}
