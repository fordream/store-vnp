package com.cnc.buddyup.request;

public class RequestOptionInformation extends Request{
	public RequestOptionInformation() {
		setUrl("http://buddyup.com/api/profile/getoptionalinfo.aspx?token={0}");
	}
}
