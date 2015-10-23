package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TabView extends LinearLayout {

	private int _id;
	private LinearLayout llTab;
	private ImageView imgTab;

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		register();
	}

	public TabView(Context context) {
		super(context);
		register();

	}

	private void register() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		li.inflate(R.layout.tab, this, true);

		llTab = (LinearLayout) findViewById(R.id.llTab);

		imgTab = (ImageView) findViewById(R.id.imgTab);
	}

	public int get_Id() {
		return _id;
	}

	public void set_Id(int id) {
		this._id = id;
	}

	public LinearLayout getLlTab() {
		return llTab;
	}

	public void setLlTab(LinearLayout llTab) {
		this.llTab = llTab;
	}

	public ImageView getImgTab() {
		return imgTab;
	}

	public void setImgTab(ImageView imgTab) {
		this.imgTab = imgTab;
	}

	public void setImgTab(int resource) {
		if (this.imgTab == null) {
			//imgTab = new ImageView(getContext());
		}
		this.imgTab.setImageResource(resource);
	}

	public void setBgSelected(Boolean isSelected) {
		llTab.setBackgroundResource(isSelected ? R.drawable.bgmenuselect
				: R.drawable.bgmenu);
	}
}
