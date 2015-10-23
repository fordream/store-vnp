package org.com.cnc.rosemont.views;

import org.com.cnc.common.android.CommonDeviceId;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IView;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeaderDetailView extends LinearLayout implements IView{
	private View imgBtnback;
	private TextView tvHeader;
	private AnimationSlide animationSlide=new AnimationSlide();
	public HeaderDetailView(Context context) {
		super(context);
		config(R.layout.headerdetail);
	}

	public HeaderDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.headerdetail);
	}

	public void config(int resource_layouy) {
	//	setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);

		imgBtnback = findViewById(R.id.btnBack);
		tvHeader = (TextView) findViewById(R.id.textView1);
		try {
			if (CommonDeviceId.getHeight(((Activity) getContext())) < CommonDeviceId.SIZE_HEIGHT_S) {
				tvHeader.setTextSize(12);
			} else if (CommonDeviceId.getHeight(((Activity) getContext())) < CommonDeviceId.SIZE_HEIGHT_TAB) {
				tvHeader.setTextSize(16);
			} else {
				tvHeader.setTextSize(20);
			}
		} catch (Exception e) {
		}
	}

	public void showButton(boolean isShowBack, boolean isShowNext) {
		imgBtnback.setVisibility(isShowBack ? VISIBLE : GONE);
	}

	public void setOnClick(OnClickListener onClickBack,
			OnClickListener onClickNext) {
		imgBtnback.setOnClickListener(onClickBack);
	}

	public void setData(String data) {
		// tvHeader.setTextSize(size)
		tvHeader.setText(data);
	}

	public void addOnCLick(OnClickListener nextLeft, OnClickListener nextRight) {
		findViewById(R.id.llNextLeft).setOnClickListener(nextLeft);
		findViewById(R.id.llnextRight).setOnClickListener(nextRight);

	}

	public void setButtonBacground(int index, int sizeOfRow) {
		findViewById(R.id.llNextLeft).setBackgroundResource(
				R.drawable.btnnextleft);
		findViewById(R.id.llnextRight).setBackgroundResource(
				R.drawable.btnnextright);
		if (index == 0) {
			findViewById(R.id.llNextLeft).setBackgroundResource(
					R.drawable.btnnextleft_no);
		}

		if (index == sizeOfRow - 1) {
			findViewById(R.id.llnextRight).setBackgroundResource(
					R.drawable.btnnextright_no);
		}
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
