package com.cnc.buddyup.data;

public class SignUpRequest {
	private String postCode;
	private String country;
	private String userName;
	private String firstName;
	private String email;
	private boolean isNewsletter;

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isNewsletter() {
		return isNewsletter;
	}

	public void setNewsletter(boolean isNewsletter) {
		this.isNewsletter = isNewsletter;
	}
}