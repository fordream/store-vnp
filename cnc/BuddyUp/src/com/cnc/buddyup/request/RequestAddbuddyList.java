package com.cnc.buddyup.request;


public class RequestAddbuddyList extends Request {
	private String username;
	private String description;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RequestAddbuddyList() {
		setUrl("http://buddyup.com/api/buddies/Add/token/{0}.aspx");
	}
}