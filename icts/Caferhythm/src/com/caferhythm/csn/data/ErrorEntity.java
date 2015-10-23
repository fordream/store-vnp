package com.caferhythm.csn.data;

import java.io.Serializable;

public class ErrorEntity implements Serializable{
	private int code;
	private String message;
	
	public ErrorEntity() {
		code = -1;
		message = "";
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void removeNull() {
		if(message.equals("null")){
			message = "";
		}
	}
}
