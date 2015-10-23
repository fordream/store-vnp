package com.caferhythm.csn.data;

import java.io.Serializable;

public class InfoEntity implements Serializable{
	private int cycle;
	private int stable;
	private int span;
	private int hairanbi;
	private String previous;
	
	public InfoEntity() {
		cycle = -1;
		span = -1;
		stable = -1;
		hairanbi = -1;
		previous = "";
	}
	
	public void removeNull() {
		if(previous.equals("null")){
			previous = "";
		}
	}
	
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public int getStable() {
		return stable;
	}
	public void setStable(int stable) {
		this.stable = stable;
	}
	public int getSpan() {
		return span;
	}
	public void setSpan(int span) {
		this.span = span;
	}
	public int getHairanbi() {
		return hairanbi;
	}
	public void setHairanbi(int hairanbi) {
		this.hairanbi = hairanbi;
	}
	public String getPrevious() {
		return previous;
	}
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	
	
}
