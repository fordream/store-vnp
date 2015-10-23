package com.cnc.buddyup.data;

public class LoginResquest {
	private boolean status = false;
	private String user;
	private String pass;

	public LoginResquest(boolean status, String user, String pass) {
		super();
		this.status = status;
		this.user = user;
		this.pass = pass;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}