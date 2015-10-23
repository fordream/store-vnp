package com.cnc.buddyup.request;

public class RequestSportName extends Request {

	public RequestSportName() {
		setUrl("http://buddyup.com/api/profile/listsport.aspx?token={0}");
	}
}