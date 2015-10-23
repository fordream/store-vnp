package com.cnc.buddyup.request;

public class RequestPhiloshophy extends Request {
	public RequestPhiloshophy() {
		setUrl("http://buddyup.com/api/user/Philosophylist.aspx?token={0}");
	}
}