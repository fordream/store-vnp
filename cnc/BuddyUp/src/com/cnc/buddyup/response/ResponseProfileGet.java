package com.cnc.buddyup.response;

import org.json.JSONObject;

import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestProfileGet;

public class ResponseProfileGet extends Response {
	private String UserName;
	private String FirstName;
	private String Email;
	private String Age;
	private String Zip;
	private String Password;
	private String country;
	private String idCountry;

	public String getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(String idCountry) {
		this.idCountry = idCountry;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getAge() {
		return Age;
	}

	public void setAge(String age) {
		Age = age;
	}

	public String getZip() {
		return Zip;
	}

	public void setZip(String zip) {
		Zip = zip;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public static ResponseProfileGet getResponseProfileGet(
			RequestProfileGet request) {
		ResponseProfileGet response = new ResponseProfileGet();
		String url = request.getUrl();
		url = url.replace("{0}", request.getToken());

		JSONObject object = JSonCommon.getJSONfromURLGET(url);
		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
			response.setAge(JSonCommon.getString(object, "Age"));
			response.setCountry(JSonCommon.getString(object, "Country"));
			response.setEmail(JSonCommon.getString(object, "Email"));
			response.setFirstName(JSonCommon.getString(object, "FirstName"));
			response.setPassword(JSonCommon.getString(object, "Password"));
			response.setUserName(JSonCommon.getString(object, "UserName"));
			response.setZip(JSonCommon.getString(object, "Zip"));
			response.setIdCountry(JSonCommon.getString(object, "idCountry"));
		}
		return response;
	}
}
