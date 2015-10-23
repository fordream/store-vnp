package org.com.vnp.lmhtmanager.menu;

import com.vnp.core.base.BaseItem;

public class MenuItem extends BaseItem {
	private int resImg;
	private String text;
	private String id;

	public MenuItem(String id, int resImg, String text) {
		super(null);
		this.id = id;
		this.resImg = resImg;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public int getResImg() {
		return resImg;
	}

	public String getText() {
		return text;
	}
}