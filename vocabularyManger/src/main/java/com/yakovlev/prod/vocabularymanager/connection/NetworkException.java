package com.yakovlev.prod.vocabularymanager.connection;

/**
 * 
 * @author codescriptor
 *
 */

public class NetworkException extends Exception{

	private static final long serialVersionUID = -861795027703009287L;

	private int statusCode;
	private int networkStatusCode;
	private String msg;
	
	public NetworkException(String msg, Throwable cause, int statusCode) {
		this(msg, cause);
		this.statusCode = statusCode;
		this.msg = msg;
	}
	
	public NetworkException(int networkStatusCode, String msg) {
		this(msg);
		this.networkStatusCode = networkStatusCode;
		this.msg = msg;
	}
	
	public NetworkException(String msg, int statusCode) {
		this(msg);
		this.statusCode = statusCode;
		this.msg = msg;
	}
	
	public NetworkException(String msg, Throwable cause) {
		super(msg);
		this.msg = msg;
	}
	
	public NetworkException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public int getNetworkStatusCode() {
		return networkStatusCode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
}
