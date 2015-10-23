package com.cnc.buddyup.request;

public class RequestUpdateOptionInformation extends Request{
	private String sex;
	private String description;
	private String comments;
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public RequestUpdateOptionInformation() {
		setUrl("http://buddyup.com/api/profile/updateoptionalinfo.aspx");
	}
}
