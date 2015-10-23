package com.caferhythm.csn.data;

public class UserEntity {
	private String name;
	private String mail;
	private String lastLoginAt;
	private String createdAt;
	private String updatedAt;
	private ErrorEntity error;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getLastLoginAt() {
		return lastLoginAt;
	}
	public void setLastLoginAt(String lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public ErrorEntity getError() {
		return error;
	}
	public void setError(ErrorEntity error) {
		this.error = error;
	}
	
}
