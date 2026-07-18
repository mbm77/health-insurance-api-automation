package com.mbm.api;

import com.mbm.client.ApiClient;
import com.mbm.dto.customer.CustomerLoginRequest;
import com.mbm.dto.customer.CustomerUpdateRequest;
import com.mbm.endpoints.EndPoints;
import com.mbm.enums.ConfigProperties;
import com.mbm.utils.PropertyUtils;

import io.restassured.response.Response;

public class CustomerApi {

	private final String baseUrl = PropertyUtils.get(ConfigProperties.BASEURL);

	public Response customerLogin(CustomerLoginRequest custmerCredentialsPayload) {

		return ApiClient.performCustomerLogin(baseUrl, EndPoints.LOGIN, custmerCredentialsPayload);

	}

	public Response getCustomerProfile() {
		return ApiClient.getCustomerData(baseUrl, EndPoints.CUSTOMER_PROFILE);
	}

	public Response updateCustomerProfile(CustomerUpdateRequest payload) {
		return ApiClient.updateCustomerProfile(baseUrl, EndPoints.UPDATE_CUSTOMER_PROFILE, payload);

	}

}
