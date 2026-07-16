package com.mbm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mbm.auth.CookieTokenManager;
import com.mbm.auth.OAuthTokenManager;

public class HeaderUtils {

	private Map<String, String> headers = new HashMap<>();

	public HeaderUtils addAuthorization() {
		headers.put("Authorization", "Bearer " + OAuthTokenManager.getInstance().getAccessToken());
		return this;
	}

	public HeaderUtils addCookieToken() {
		headers.put("Cookie", "token=" + CookieTokenManager.getCookieToken());
		return this;
	}

	public HeaderUtils addTraceId() {
		headers.put("Trace-Id", UUID.randomUUID().toString());
		return this;
	}

	public HeaderUtils addProjectName(String project) {
		headers.put("Project-Name", project);
		return this;
	}

	public Map<String, String> build() {
		return headers;
	}
}