package com.caferhythm.csn.data;

import java.io.Serializable;

public class TimeEntity implements Serializable {
	protected String start;
	protected String end;
	
	public TimeEntity() {
		this.start = "";
		this.end = "";
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public void removeNull() {
		if(this.start.equals("null")){
			this.start = "";
		}
		
		if(this.end.equals("null")){
			this.end = "";
		}
	}
}
