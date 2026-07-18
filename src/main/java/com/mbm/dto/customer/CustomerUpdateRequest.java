package com.mbm.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerUpdateRequest {
	//@JsonProperty("first_name")
	private String firstName;

	//@JsonProperty("last_name")
	private String lastName;

	//@JsonProperty("date_of_birth")
	private String dateOfBirth;

	//@JsonProperty("gender")
	private String gender;

	//@JsonProperty("aadhaar_number")
	private String aadhaarNumber;

	//@JsonProperty("pan_number")
	private String panNumber;

	//@JsonProperty("blood_group")
	private String bloodGroup;

	//@JsonProperty("marital_status")
	private String maritalStatus;

	//@JsonProperty("occupation")
	private String occupation;

	//@JsonProperty("annual_income")
	private String annualIncome;

	//@JsonProperty("address")
	private String address;

	//@JsonProperty("city")
	private String city;

	//@JsonProperty("state")
	private String state;

	//@JsonProperty("country")
	private String country;

	//@JsonProperty("pincode")
	private String pincode;

}
