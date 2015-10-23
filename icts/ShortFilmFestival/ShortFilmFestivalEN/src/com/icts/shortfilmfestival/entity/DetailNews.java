package com.icts.shortfilmfestival.entity;

public class DetailNews {
	private boolean error;
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	private int id;
	private String title;
	private String content;
	private String date;
	private String arrayImage[];
	private int numLikeFacebook;
	public int getNumLikeFacebook() {
		return numLikeFacebook;
	}
	public void setNumLikeFacebook(int numLikeFacebook) {
		this.numLikeFacebook = numLikeFacebook;
	}
	private int numLikeTwitter;
	public int getNumLikeTwitter() {
		return numLikeTwitter;
	}
	public void setNumLikeTwitter(int numLikeTwitter) {
		this.numLikeTwitter = numLikeTwitter;
	}
	public String[] getArrayImage() {
		return arrayImage;
	}
	public void setArrayImage(String[] arrayImage) {
		this.arrayImage = arrayImage;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public DetailNews()
	{
		setError(false);
	}
}
