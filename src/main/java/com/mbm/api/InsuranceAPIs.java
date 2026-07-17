package com.mbm.api;

import com.mbm.client.ApiClient;
import com.mbm.endpoints.EndPoints;
import com.mbm.enums.ConfigProperties;
import com.mbm.framework.request.RequestOptions;
import com.mbm.insurance_pojo.CustomerCredentials;
import com.mbm.utils.PropertyUtils;

import io.restassured.response.Response;

public class InsuranceAPIs {

	private final String baseUrl = PropertyUtils.get(ConfigProperties.BASEURL);

	public Response customerLogin(CustomerCredentials custmerCredentialsPayload) {

		return ApiClient.performCustomerLogin(baseUrl, EndPoints.LOGIN, custmerCredentialsPayload);

	}

	public Response getCustomerProfile(RequestOptions options) {
		return ApiClient.getCustomerData(baseUrl, EndPoints.CUSTOMER_PROFILE, options);
	}

}
