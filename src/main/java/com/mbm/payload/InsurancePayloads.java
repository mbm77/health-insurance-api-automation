package com.mbm.payload;

import java.util.Map;

import com.mbm.insurance_pojo.CustomerCredentials;

public class InsurancePayloads {
	
	public static CustomerCredentials getCustomerLoginData(Map<String, String> map) {
		return CustomerCredentials.builder().username(map.get("username")).password(map.get("password")).build();
	}

}
