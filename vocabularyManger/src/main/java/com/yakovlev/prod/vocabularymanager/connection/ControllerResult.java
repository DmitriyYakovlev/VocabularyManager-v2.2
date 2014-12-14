package com.yakovlev.prod.vocabularymanager.connection;

public class ControllerResult {

	private int code;
	private String message;
	
	public ControllerResult(int code) {
		this.code = code;
		this.message = "";
	}
	
	public ControllerResult(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
