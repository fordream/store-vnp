package com.caferhythm.csn.configure;

public class ErrorJSON {
	public static final String ERROR1002 = "1002";
	public static final String ERROR1001 = "1001";
	public static final String NO_ERROR = "200";
	
	private String code;
	private String mesage;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMesage() {
		return mesage;
	}
	public void setMesage(String mesage) {
		this.mesage = mesage;
	}
	public boolean isNoError(){
		return code.equals(NO_ERROR);
	}
}
