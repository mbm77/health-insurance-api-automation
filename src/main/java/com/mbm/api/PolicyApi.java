package com.mbm.api;

import com.mbm.client.ApiClient;
import com.mbm.endpoints.EndPoints;
import com.mbm.enums.ConfigProperties;
import com.mbm.utils.PropertyUtils;

import io.restassured.response.Response;

public class PolicyApi {
	
	private static final String BASEURL = PropertyUtils.get(ConfigProperties.BASEURL);
	
	public  Response getPolicy() {
		return ApiClient.getPolicyDetails(BASEURL, EndPoints.GET_POLICY);
	
	}

}
