package com.cnc.buddyup.request;

public class RequestSkillLevel extends Request {

	public RequestSkillLevel() {
		setUrl("http://buddyup.com/api/user/Skillevellist.aspx?token={0}");
	}
}