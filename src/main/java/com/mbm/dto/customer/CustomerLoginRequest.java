package com.mbm.dto.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerLoginRequest {
	private String username;
	private String password;

}
