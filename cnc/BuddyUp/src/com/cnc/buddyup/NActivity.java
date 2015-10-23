package com.cnc.buddyup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NActivity extends Activity {
	protected LinearLayout llContent;
	protected Button commonbtnBack;
	protected Button commonBtnAdd;
	protected TextView commonETHearder;
	protected LinearLayout common_llSubmit;
	protected ImageView commonImage;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common);
	}

	private void config() {
		llContent = getLinearLayout(R.id.commonLLContent);
		commonbtnBack = getButton(R.id.commonbtnBack);
		commonBtnAdd = getButton(R.id.commonBtnAdd);
		commonETHearder = getTextView(R.id.commonETHearder);
		common_llSubmit = getLinearLayout(R.id.common_llSubmit);
		commonImage = getImageView(R.id.imageView1);
		commonImage.setVisibility(View.GONE);
	}

	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		config();
	}

	protected void addView(View view) {
		if (view != null) {
			llContent.removeAllViews();
			llContent.addView(view);
		}
	}

	protected void addView(View view, boolean isClear) {
		if (view != null) {
			if (isClear) {
				llContent.removeAllViews();
			}
			llContent.addView(view);
		}
	}

	protected void config(boolean showBack, boolean showAdd, String text,
			boolean showfooter) {
		commonbtnBack.setVisibility(showBack ? View.VISIBLE : View.GONE);
		commonBtnAdd.setVisibility(showAdd ? View.VISIBLE : View.GONE);
		common_llSubmit.setVisibility(showfooter ? View.VISIBLE : View.GONE);
		commonETHearder.setText(text);
	}

	protected boolean addView(View viewCompareTo, View viewComapreFrom,
			View view) {
		if (viewComapreFrom == viewCompareTo) {
			addView(view);
			return true;
		}
		return false;
	}

	public void showBtnSubmit(boolean isSHow) {
		int visible = isSHow ? View.VISIBLE : View.GONE;
		common_llSubmit.setVisibility(visible);
	}

	public void setHeader(String text) {
		commonETHearder.setText(text);
	}

	public void changleBackground(int res) {
		llContent.setBackgroundResource(res);
	}
	public void changleBackground(boolean res) {
		llContent.setBackgroundResource(res?R.drawable.buddy_main_bg1 : R.drawable.profile_bg);
	}
}