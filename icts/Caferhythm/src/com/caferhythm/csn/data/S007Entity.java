package com.caferhythm.csn.data;

public class S007Entity {
	private String days;
	private String memo;
	private int nextPeriod;
	private int previousPeriod;
	private int nextHairan;
	
	
	public S007Entity() {
		super();
		days = "";
		memo = "";
		nextPeriod = -1;
		nextHairan = -1;
		previousPeriod = -1;
	}
	
	
	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public int getNextPeriod() {
		return nextPeriod;
	}
	public void setNextPeriod(int nextPeriod) {
		this.nextPeriod = nextPeriod;
	}
	public int getPreviousPeriod() {
		return previousPeriod;
	}
	public void setPreviousPeriod(int previousPeriod) {
		this.previousPeriod = previousPeriod;
	}
	public int getNextHairan() {
		return nextHairan;
	}
	public void setNextHairan(int nextHairan) {
		this.nextHairan = nextHairan;
	}
	
	
}
