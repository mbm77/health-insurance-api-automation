package com.mbm.insurance_tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.mbm.api.InsuranceAPIs;
import com.mbm.auth.JwtTokenManager;
import com.mbm.bookingtests.BaseTest;
import com.mbm.framework.request.RequestOptions;

import io.restassured.response.Response;

public class CustomerSmokeTest extends BaseTest {
	InsuranceAPIs insuranceAPIs = new InsuranceAPIs();

	@Test
	public void getProfile_Should_ReturnCustomerProfile() {
		String token = JwtTokenManager.getInstance().getAccessToken("vikram", "vikram1234");
		RequestOptions options = RequestOptions.builder().header("Authorization", "Bearer " + token).build();
		Response response = insuranceAPIs.getCustomerProfile(options);
		response.then()
				.statusCode(200)
				.time(Matchers.lessThan(6000L))
				.body("customerId", Matchers.greaterThan(0))
				.body("username", Matchers.equalTo("vikram"))
				.body("email", Matchers.notNullValue());
	}

}
