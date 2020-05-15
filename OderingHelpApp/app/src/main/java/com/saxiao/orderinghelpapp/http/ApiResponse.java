package com.saxiao.orderinghelpapp.http;


public class ApiResponse<T> {
	/**
	 * 状态=>等同于HTTP 状态码
	 */
	private String message;
	private String description;
	private T data;

	public ApiResponse(String message, String description, T data) {
		this.message = message;
		this.description = description;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	public T getData() {
		return data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setData(T data) {
		this.data = data;
	}
}
