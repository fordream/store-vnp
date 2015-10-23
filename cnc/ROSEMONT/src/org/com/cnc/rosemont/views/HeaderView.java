package org.com.cnc.rosemont.views;

import org.com.cnc.common.android.CommonView;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.drawable;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeaderView extends LinearLayout implements IView {
	private View imgBtnback;
	private View imgBtnNext;
	private TextView tvHeader;
	public int[] header = new int[] { drawable.header_adverse_event_report,
			drawable.header_calculator, drawable.header_home_main,
			drawable.header_listproductby, drawable.headersettings };
	public static final int TYPE_ADVERSE = drawable.header_adverse_event_report;
	public static final int TYPE_CACULATOR = drawable.header_calculator;
	public static final int TYPE_HOME_MAIN = drawable.header_home_main;
	public static final int TYPE_LIST_PRODUCT_BY = drawable.header_listproductby;
	public static final int TYPE_SETTINGS = drawable.headersettings;
	public static final int TYPE_LIST_PRODUCT = drawable.header_list_product;
	public static final int TYPE_LIST_CATEGORIES = drawable.header_categories;
	public static final int TYPE_LIST_INDICATIONS = drawable.header_indications;
	public static final int TYPE_LIST_STRENGTH = drawable.header_strength;

	public HeaderView(Context context) {
		super(context);
		config(R.layout.header);
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.header);
	}

	public void config(int resource_layouy) {
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);

		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setText("");
		tvHeader.setShadowLayer(2, 0, 1, Color.BLACK);
		Typeface type = Typeface.createFromAsset(getContext().getAssets(),
				"fonts/HelveticaNeueLTCom-Bd.ttf");
		tvHeader.setTypeface(type, Typeface.NORMAL);

		imgBtnback = findViewById(R.id.btnBack);
		imgBtnNext = findViewById(R.id.btnNext);

		imgBtnback.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (getContext() instanceof IActivity) {
					((IActivity) getContext()).onBack1();
				}
			}
		});
	}

	public void setText(String string) {
		tvHeader.setText(string);

	}

	public void showButton(boolean isShowBack, boolean isShowNext) {
		imgBtnback.setVisibility(isShowBack ? VISIBLE : GONE);
		imgBtnNext.setVisibility(isShowNext ? VISIBLE : GONE);
	}

	public void setOnClick(OnClickListener onClickBack,
			OnClickListener onClickNext) {
		imgBtnback.setOnClickListener(onClickBack);
		imgBtnNext.setOnClickListener(onClickNext);
	}

	public void setParams() {
		post(new Runnable() {
			public void run() {
				int width = getWidth();
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						drawable.bgheaderdetail);
				int height = bitmap.getHeight() * width / bitmap.getWidth();
				findViewById(R.id.llheader).setLayoutParams(
						CommonView.createLayoutParams(width, height));
			}
		});
	}

	public void setType(int type) {
		findViewById(R.id.llheader).setBackgroundResource(type);
	}

	public void setText(int res) {
		tvHeader.setText(res);

	}

	public void reset() {
		// TODO Auto-generated method stub

	}
}
