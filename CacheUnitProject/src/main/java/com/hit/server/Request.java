package com.hit.server;

import com.google.gson.Gson;

public class Request<T> extends java.lang.Object implements java.io.Serializable {

	private static final long serialVersionUID = -4137284727528762260L;
	private java.util.Map<java.lang.String, java.lang.String> headers;
	T body;

	public Request(java.util.Map<java.lang.String, java.lang.String> headers, T body) {
		this.body = body;
		this.headers = headers;
	}

	public java.util.Map<java.lang.String, java.lang.String> getHeaders() {
		return headers;
	}

	public void setHeaders(java.util.Map<java.lang.String, java.lang.String> headers) {
		this.headers = headers;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public java.lang.String toString() {
		return new Gson().toJson(this).toString();
	}
}
