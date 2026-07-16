package com.mbm.framework.request;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class RequestOptions {

	private final Map<String, Object> pathParams;
	private final Map<String, Object> queryParams;
	private final Map<String, String> headers;
	private final Map<String, String> cookies;
	private final Map<String, Object> formParams;
	private final Map<String, File> multipartFiles;
	private final Object body;

	private RequestOptions(Builder builder) {
		this.pathParams = builder.pathParams;
		this.queryParams = builder.queryParams;
		this.headers = builder.headers;
		this.cookies = builder.cookies;
		this.formParams = builder.formParams;
		this.multipartFiles = builder.multipartFiles;
		this.body = builder.body;
	}

	public Map<String, Object> getPathParams() {
		return pathParams;
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public Map<String, Object> getFormParams() {
		return formParams;
	}

	public Object getBody() {
		return body;
	}

	public Map<String, File> getMultipartFiles() {
		return multipartFiles;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Map<String, Object> pathParams;
		private Map<String, Object> queryParams;
		private Map<String, String> headers;
		private Map<String, String> cookies;
		private Map<String, Object> formParams;
		private Map<String, File> multipartFiles;
		private Object body;

		public Builder body(Object body) {
			this.body = body;
			return this;
		}

		public Builder pathParam(String key, Object value) {

			if (pathParams == null) {
				pathParams = new HashMap<>();
			}

			pathParams.put(key, value);

			return this;
		}

		public Builder headers(Map<String, String> headers) {
			if (headers != null) {
				this.headers = headers;
			}
			return this;
		}

		public Builder header(String key, String value) {

			if (headers == null) {
				headers = new HashMap<>();
			}

			headers.put(key, value);

			return this;
		}

		public Builder queryParam(String key, String value) {
			if (queryParams == null) {
				queryParams = new HashMap<>();
			}
			queryParams.put(key, value);
			return this;
		}

		public Builder queryParams(Map<String, Object> queryParams) {
			this.queryParams = queryParams;
			return this;
		}

		public Builder cookies(Map<String, String> cookies) {
			this.cookies = cookies;
			return this;
		}

		public Builder cookie(String key, String value) {
			if (cookies == null) {
				cookies = new HashMap<>();
			}
			cookies.put(key, value);
			return this;
		}

		public Builder formParams(Map<String, Object> formParams) {
			this.formParams = formParams;
			return this;
		}

		public Builder formParam(String key, Object value) {
			if (formParams == null) {
				formParams = new HashMap<>();
			}
			formParams.put(key, value);
			return this;
		}

		public Builder multipartFile(String name, File file) {
			if (multipartFiles == null) {
				multipartFiles = new HashMap<>();
			}
			multipartFiles.put(name, file);
			return this;
		}

		public RequestOptions build() {
			return new RequestOptions(this);
		}
	}
}