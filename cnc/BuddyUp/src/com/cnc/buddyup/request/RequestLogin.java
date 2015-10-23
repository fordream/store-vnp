package com.cnc.buddyup.request;

public class RequestLogin extends Request {
	public RequestLogin() {
		setUrl("http://buddyup.com/api/user/login/username/{0}/userpassword/{1}.aspx");
	}

	private String username;
	private String password;
	private String isCheck;

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
