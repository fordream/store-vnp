package org.com.cnc.rosemont.views;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonDeviceId;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.id;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.items.ItemSearch;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemProductDetailView extends LinearLayout implements IView {
	public static final int TYPE_CHECK = 1;
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_VISICOSITY = 2;
	int type = TYPE_CHECK;
	//private AnimationSlide animationSlide=new AnimationSlide();
	public ItemProductDetailView(Context context, int index) {
		super(context);
		config(index);
	}

	public ItemProductDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(TYPE_CHECK);
	}

	public void config(int index) {
	//	setAnimation(animationSlide.inFromRightAnimation());
		type = index;

		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		int resource_layouy = R.layout.itemproductdetailcheck;

		if (index == TYPE_TEXT) {
			resource_layouy = R.layout.itemproductdetailtext;
		} else if (index == TYPE_VISICOSITY) {
			resource_layouy = R.layout.itemproductdetailviscosity;
		}

		li.inflate(resource_layouy, this);
	}

	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		findViewById(R.id.ll).setLayoutParams(params);
	}

	public void updateData(ItemSearch item) {
		TextView textView = ((TextView) findViewById(R.id.textView1));
		textView.setText(item.getTxtHeader());
		int heght = CommonDeviceId.getHeight((Activity) getContext());

		// size for S, TAB
		int size = heght * 20 / 960;

		// if is Galaxy Y
		if (heght < CommonDeviceId.SIZE_HEIGHT_S) {
			size = heght * 40 / 960;
		}

		textView.setTextSize(size);
		if (TYPE_CHECK == type) {
			((CheckBox) findViewById(id.checkBox1)).setChecked("1"
					.endsWith(item.getTxtContent()));
			((CheckBox) findViewById(id.checkBox1)).setEnabled(false);
			((CheckBox) findViewById(id.checkBox1)).setFocusable(false);
		} else if (type == TYPE_TEXT) {
			TextView textView2 = ((TextView) findViewById(R.id.textView2));
			textView2.setTextSize(size);
			textView2.setText(item.getTxtContent());
		} else if (type == TYPE_VISICOSITY) {
			View view = findViewById(R.id.linearLayout2);
			String value = item.getTxtContent();
			int water[] = new int[] { R.drawable.water1, R.drawable.water2,
					R.drawable.water3 };
			if (!Common.isNullOrBlank(value)) {
				if ("1".equals(value)) {
					view.setBackgroundResource(water[0]);
				} else if ("2".equals(value)) {
					view.setBackgroundResource(water[1]);
				} else if ("3".equals(value)) {
					view.setBackgroundResource(water[2]);
				}
			} else {
				// view.setBackgroundResource(water[0]);
			}
		}
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}