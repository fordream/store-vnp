package com.icts.shortfilmfestival.entity;

public class MovieEntity {
	private int ustreamId;
	public int getUstreamId() {
		return ustreamId;
	}
	public void setUstreamId(int ustreamId) {
		this.ustreamId = ustreamId;
	}
	private String id;
	private String title;
	private String thumbnail;
	private String author;
	private String rating;
	private String countrator;
	private String duration;
	private String viewcount;
	private String updated;
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getViewcount() {
		return viewcount;
	}
	public void setViewcount(String viewcount) {
		this.viewcount = viewcount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getCountrator() {
		return countrator;
	}
	public void setCountrator(String countrator) {
		this.countrator = countrator;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	private String link;
}
