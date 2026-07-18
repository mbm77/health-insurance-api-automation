package com.mbm.payload;

import java.util.Map;

import com.mbm.dto.customer.CustomerLoginRequest;

public class InsurancePayload {
	
	public static CustomerLoginRequest getCustomerLoginData(Map<String, String> map) {
		return 	CustomerLoginRequest.builder()
									.username(map.get("username"))
									.password(map.get("password"))
									.build();
	}
	
	
	
}
