package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemProductListByView extends LinearLayout implements IView{
	private AnimationSlide animationSlide=new AnimationSlide();
	public ItemProductListByView(Context context, int index) {
		super(context);
		config(index);
	}

	public ItemProductListByView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(0);
	}

	private void config(int index) {
		//setAnimation(animationSlide.inFromRightAnimation());
		
		int resource_layouy = R.layout.item_list_product_by2;
		if (index == 0) {
			resource_layouy = R.layout.item_list_product_by0;
		} else if (index == 1) {
			resource_layouy = R.layout.item_list_product_by1;
		} else if (index == 2) {
			resource_layouy = R.layout.item_list_product_by2;
		} else if (index == 3) {
			resource_layouy = R.layout.item_list_product_by3;
		}

		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
	}

	public void setData(String string) {
		((TextView) findViewById(R.id.tvProductItemListBy)).setText(string);
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
