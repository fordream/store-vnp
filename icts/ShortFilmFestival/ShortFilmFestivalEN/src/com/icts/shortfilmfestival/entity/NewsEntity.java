package com.icts.shortfilmfestival.entity;

public class NewsEntity {
	private int id;
	private String desc;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String datetime;
	private String content;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private int hasPhoto;
	private int hasMovie;
	public int isHasPhoto() {
		return hasPhoto;
	}
	public void setHasPhoto(int hasPhoto) {
		this.hasPhoto = hasPhoto;
	}
	public int isHasMovie() {
		return hasMovie;
	}
	public void setHasMovie(int hasMovie) {
		this.hasMovie = hasMovie;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private String title;
	
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	private String thumbnail;
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
}
