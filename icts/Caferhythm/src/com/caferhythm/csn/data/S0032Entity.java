package com.caferhythm.csn.data;

import android.graphics.Bitmap;

public class S0032Entity {
	private int pt;
	private String message;
	private int star;
	private String title;
	private Bitmap left;

	public S0032Entity() {
		// TODO Auto-generated constructor stub
		pt = -1;
		message = "";
		star = 0;
		title = "";
		left = null;
	}

	public Bitmap getLeft() {
		return left;
	}

	public void setLeft(Bitmap left) {
		this.left = left;
	}

	public int getPt() {
		return pt;
	}

	public void setPt(int pt) {
		this.pt = pt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
