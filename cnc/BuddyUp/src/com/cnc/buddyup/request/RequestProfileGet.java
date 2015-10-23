package com.cnc.buddyup.request;

public class RequestProfileGet extends Request{
	public RequestProfileGet() {
		setUrl("http://buddyup.com/api/profile/get/token/{0}.aspx");
	}
}
