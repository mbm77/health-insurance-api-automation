package com.mbm.payload;

import java.util.Map;

import com.mbm.dto.customer.CustomerLoginRequest;
import com.mbm.dto.customer.CustomerUpdateRequest;
import com.mbm.utils.DateUtils;

public class CustomerPayload {

	public static CustomerUpdateRequest getCustomerUpdateRequestPayload(Map<String, String> customerData) {
		return CustomerUpdateRequest.builder()
				.firstName(customerData.get("firstName"))
				.lastName(customerData.get("lastName"))
				.dateOfBirth(DateUtils.getDateFormat(customerData.get("dateOfBirth")))
				.gender(customerData.get("gender"))
				.aadhaarNumber(customerData.get("aadhaarNumber"))
				.panNumber(customerData.get("panNumber"))
				.bloodGroup(customerData.get("bloodGroup"))
				.maritalStatus(customerData.get("maritalStatus"))
				.occupation(customerData.get("occupation"))
				.annualIncome(customerData.get("annualIncome"))
				.address(customerData.get("address"))
				.city(customerData.get("city")).state(customerData.get("state"))
				.country(customerData.get("country"))
				.pincode(customerData.get("pincode"))
				.build();

	}

	public static CustomerLoginRequest getCustomerLoginData(Map<String, String> map) {
		return CustomerLoginRequest.builder()
				.username(map.get("username"))
				.password(map.get("password"))
				.build();
	}

}
