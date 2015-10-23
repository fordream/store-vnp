package com.cnc.buddyup.request;

public class RequestProfileDelete extends Request{
	public RequestProfileDelete() {
		setUrl("http://buddyup.com/api/profile/delete/token/{0}.aspx");
	}
}
