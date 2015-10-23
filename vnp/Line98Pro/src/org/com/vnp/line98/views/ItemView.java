package org.com.vnp.line98.views;

import org.com.cnc.common.adnroid.views.CommonLinearLayout;
import org.com.vnp.line98.activity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ItemView extends CommonLinearLayout {
	private ImageView imageView;
	private LinearLayout llContent;

	public ItemView(Context context) {
		super(context);
		config(R.layout.item);
	}

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.item);
	}

	public void config(int resource_layouy) {
		super.config(resource_layouy);
		imageView = getImageView(R.id.imageView1);
		llContent = (LinearLayout) findViewById(R.id.llContent);
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImage(int res) {
		imageView.setBackgroundResource(res);
		llContent.setBackgroundResource(R.drawable.bg2);
	}

	public void setImageRounte(boolean b, Animation animation) {
		if (b) {
			llContent.setBackgroundResource(R.drawable.bg3);
		} else {
			llContent.setBackgroundResource(R.drawable.bg2);
		}
	}
}