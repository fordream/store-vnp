package org.com.cnc.common.maispreco.response;

public class Response {
	public static final String STATUSCODE = "statusCode";
	public static final String TATUSMESSAGE = "statusMessage";
	private String statusCode;
	private String statusMessage;
	private String resultTotal;
	
	public String getResultTotal() {
		return resultTotal;
	}

	public void setResultTotal(String resultTotal) {
		this.resultTotal = resultTotal;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
