package com.mbm.insurance.tests.customer;

import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.mbm.api.CustomerApi;
import com.mbm.base.BaseTest;
import com.mbm.dto.customer.CustomerLoginRequest;
import com.mbm.dto.customer.CustomerUpdateRequest;
import com.mbm.payload.CustomerPayload;
import com.mbm.utils.DataProviderUtils;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class CustomerTests extends BaseTest {

	CustomerApi customerApi = new CustomerApi();

	@Test(dataProvider = "loginData", dataProviderClass = DataProviderUtils.class)
	public void customerLoginTest(Map<String, String> map) {
		CustomerLoginRequest customerCredentialsPaylad = CustomerPayload.getCustomerLoginData(map);
		Response response = customerApi.customerLogin(customerCredentialsPaylad);

		response.then().statusCode(200).time(Matchers.lessThan(20000L))
				.body("accessToken", Matchers.notNullValue())
				.body("accessToken", Matchers.startsWith("eyJ"))
				.body("accessToken", Matchers.not(Matchers.emptyString()));

	}

	@Test(groups = { "smoke" })
	public void getCustomerProfileTest() {

		Response response = customerApi.getCustomerProfile();
		response.then().statusCode(200)
				.time(Matchers.lessThan(6000L))
				.body("customerId", Matchers.greaterThan(0))
				.body("username", Matchers.equalTo("vikram"))
				.body("email", Matchers.notNullValue());
	}

	@Test(dataProvider = "customerProfileData", dataProviderClass = DataProviderUtils.class, groups = { "regression" })
	public void updateCustomerProfileTest(Map<String, String> customerData) {
		CustomerUpdateRequest payload = CustomerPayload.getCustomerUpdateRequestPayload(customerData);
		Response response = customerApi.updateCustomerProfile(payload);
		response.then().statusCode(200)
				.time(Matchers.lessThan(5000L))
				.contentType(ContentType.JSON)
				.body("annualIncome", Matchers.equalTo(5000000.0f))
				.body(JsonSchemaValidator
						.matchesJsonSchemaInClasspath("schemas/customer-profile-response-schema.json"));

	}

}
