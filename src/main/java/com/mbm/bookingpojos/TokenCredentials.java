package com.mbm.bookingpojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(toBuilder=true)
public class TokenCredentials {
	private String username;
	private String password;
}
