package com.mbm.dto.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder // This makes sure fields are inherited in subclasses
public class BasePojo {
	@JsonIgnore // exclude a field or method from JSON serialization and deserialization.
	private String scenarioId;
	@JsonIgnore
	private String scenarioDesc;
	@JsonIgnore
	private int expectedStatusCode;
	@JsonIgnore
	private String expectedErrorMessage;

	// ✅ Explicit no-args constructor
	public BasePojo() {
	}
}
