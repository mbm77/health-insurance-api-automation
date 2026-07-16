package com.mbm.insurance_pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerCredentials {
	private String username;
	private String password;

}
