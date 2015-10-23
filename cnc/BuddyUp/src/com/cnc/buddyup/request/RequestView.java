package com.cnc.buddyup.request;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class RequestView extends LinearLayout {

	public class Field {
		public ImageView fImgView1 ;
		public ImageView fImgView2 ;
		public ImageView fImgView3 ;
	}

	private Field field = new Field();

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public RequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.buddyrequestlist1);
	}

	public RequestView(Context context) {
		super(context);
		config(R.layout.buddyrequestlist1);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		field.fImgView1 = getImageView(R.id.ImageView02);
		field.fImgView2 = getImageView(R.id.ImageView01);
		field.fImgView3 = getImageView(R.id.imageView1);
	}

	public void hiddenAllKey() {
	}
}