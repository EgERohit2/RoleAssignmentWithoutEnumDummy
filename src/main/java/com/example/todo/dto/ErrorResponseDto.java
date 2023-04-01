package com.example.todo.dto;

public class ErrorResponseDto {

	private String msgKey;

	private String errorMessage;

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorResponseDto(String msgKey, String errorMessage) {
		super();
		this.msgKey = msgKey;
		this.errorMessage = errorMessage;
	}

	public ErrorResponseDto() {
		// TODO Auto-generated constructor stub
	}

}
