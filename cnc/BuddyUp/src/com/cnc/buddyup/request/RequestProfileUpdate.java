package com.cnc.buddyup.request;

public class RequestProfileUpdate extends Request{
	private String Zip;
	private String Country;
	private String username;
	private String email;
	private String newsletter;
	private String firstname;
	private String userpassword;
	private String reuserpassword;
	private String age;
	private String sport1;
	private String sport2;
	private String sport3;
	public RequestProfileUpdate() {
		setUrl("http://buddyup.com/api/profile/update.aspx");
	}
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
	public String getReuserpassword() {
		return reuserpassword;
	}
	public void setReuserpassword(String reuserpassword) {
		this.reuserpassword = reuserpassword;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSport1() {
		return sport1;
	}
	public void setSport1(String sport1) {
		this.sport1 = sport1;
	}
	public String getSport2() {
		return sport2;
	}
	public void setSport2(String sport2) {
		this.sport2 = sport2;
	}
	public String getSport3() {
		return sport3;
	}
	public void setSport3(String sport3) {
		this.sport3 = sport3;
	}
}
