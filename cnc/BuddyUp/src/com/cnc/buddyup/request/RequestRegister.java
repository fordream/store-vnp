package com.cnc.buddyup.request;

public class RequestRegister extends Request {
	public RequestRegister() {
		setUrl("http://buddyup.com/api/user/register.aspx");
	}

	String Zip;
	String Country;
	String username;
	String email;
	String emailcheck;
	String newsletter;
	String firstname;
	String userpassword;

	public String getZip() {
		return Zip;
	}

	public void setZip(String zip) {
		Zip = zip;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getUsername() {
		return username;
	}
	

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailcheck() {
		return emailcheck;
	}

	public void setEmailcheck(String emailcheck) {
		this.emailcheck = emailcheck;
	}

	public String getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(String newsletter) {
		this.newsletter = newsletter;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
}