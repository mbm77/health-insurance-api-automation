package com.mbm.insurance.tests.policy;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.mbm.api.PolicyApi;
import com.mbm.base.BaseTest;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class PolicyTests extends BaseTest{
	
	PolicyApi policyApi = new PolicyApi();
	
	@Test
	public void getPolicyTest() {
		Response response = policyApi.getPolicy();
		//response.prettyPrint();
		response.then().statusCode(200)
						.time(Matchers.lessThan(5000L))
						
						//Company Details
						.body("companyId", Matchers.equalTo(1))
						.body("companyName", Matchers.equalTo("ABC Health Insurance"))
						
						//Plan Details
						.body("planId", Matchers.equalTo(1))
						.body("planName", Matchers.equalTo("Family Floater Premium"))
						.body("planDescription", Matchers.equalTo("Updated comprehensive health insurance plan."))
						.body("coverageAmount", Matchers.equalTo(1500000.00f))
						.body("premiumAmount", Matchers.equalTo(18000.00f))
						.body("policyTermInMonths", Matchers.equalTo(24))
						.body("waitingPeriodInDays", Matchers.equalTo(60))
						
						//policy Details
						.body("policyId", Matchers.equalTo(1))
						.body("policyName", Matchers.equalTo("Star Family Floater Platinum"))
						.body("minAge", Matchers.equalTo(21))
						.body("maxAge", Matchers.equalTo(70))
						.body("status", Matchers.equalTo("ACTIVE"))
						
						//Coverages
						.body("coverages.size()", Matchers.equalTo(2))
						
						//verify first coverage
						.body("coverages[0].coverageId", Matchers.equalTo(1))
						.body("coverages[0].coverageName", Matchers.equalTo("ICU Hospitalization Coverage"))
						.body("coverages[0].coverageAmount", Matchers.equalTo(750000.00f))
						.body("coverages[0].status", Matchers.equalTo("ACTIVE"))
						
						//verify second coverage
						.body("coverages[1].coverageId", Matchers.equalTo(2))
						.body("coverages[1].coverageName", Matchers.equalTo("Hostel Expenses"))
						.body("coverages[1].coverageAmount", Matchers.equalTo(500000.00f))
						.body("coverages[1].status", Matchers.equalTo("ACTIVE"))
						
						//Exclusions
						.body("exclusions.size()", Matchers.equalTo(1))
						.body("exclusions[0].exclusionId", Matchers.equalTo(1))
						.body("exclusions[0].exclusionName", Matchers.equalTo("Pre-existing Diseases"))
						.body("exclusions[0].status", Matchers.equalTo("ACTIVE"))
						
						//Generic Assertions
						.body("companyId", Matchers.greaterThan(0))
						.body("companyName", Matchers.not(Matchers.emptyString()))
						
						.body("policyId", Matchers.greaterThan(0))
						.body("policyName", Matchers.not(Matchers.emptyString()))
						.body("policyCode", Matchers.startsWith("S"))
						.body("policyType", Matchers.notNullValue())
						
						.body("coverageAmount", Matchers.greaterThan(0f))
						.body("premiumAmount", Matchers.greaterThan(0f))
						
						.body("minAge", Matchers.greaterThanOrEqualTo(18))
						.body("maxAge", Matchers.greaterThan(18))
						
						
						.body("coverages", Matchers.not(Matchers.empty()))
						.body("exclusions", Matchers.notNullValue())
						
						//Collection Assertions
						.body("coverages.coverageName", Matchers.hasItems("ICU Hospitalization Coverage", "Hostel Expenses"))
						.body("coverages.status", Matchers.everyItem(Matchers.equalTo("ACTIVE")))
						.body("exclusions.status", Matchers.everyItem(Matchers.equalTo("ACTIVE")))
						
						.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/policy/policy-details-response-schema.json"));
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
	}

}
