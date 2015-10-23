package com.icts.shortfilmfestival.entity;

public class ScheduleEntity {
	private int id;
	private String date;
	private String time;
	private String title;
	private String free;
	private String link;
	private boolean click;

	public boolean isClick() {
		return click;
	}
	public void setClick(boolean click) {
		this.click = click;
	}
	public ScheduleEntity() {

	}
	public ScheduleEntity(int id, String date, String time, String title,
			String free, String link) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.title = title;
		this.free = free;
		this.link = link;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
