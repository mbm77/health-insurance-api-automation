package com.mbm.insurance_tests;

import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.mbm.bookingtests.BaseTest;
import com.mbm.insurance_pojo.CustomerCredentials;
import com.mbm.payload.InsurancePayloads;
import com.mbm.utils.DataProviderUtils;

import io.restassured.response.Response;

public class LoginSmokeTest extends BaseTest {

	InsuranceAPIs insuranceAPIs = new InsuranceAPIs();

	@Test(dataProvider = "loginData", dataProviderClass = DataProviderUtils.class)
	public void login_Should_return_JwtToken(Map<String, String> map) {
		CustomerCredentials customerCredentialsPaylad = InsurancePayloads.getCustomerLoginData(map);
		Response response = insuranceAPIs.customerLogin(customerCredentialsPaylad);
		response.then()
				.statusCode(200)
				.time(Matchers.lessThan(14000L))
				.body("accessToken", Matchers.notNullValue())
				.body("accessToken",Matchers.startsWith("eyJ"))
				.body("accessToken", Matchers.not(Matchers.emptyString()));
		
	}

}
