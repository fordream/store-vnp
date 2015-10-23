package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoadMoreView extends LinearLayout {
	@SuppressWarnings("unused")
	private Context context;
	private TextView tvLoadMore, tvLoadContent;
	private String loadMore = "";
	public static int TYPE_CATEGORY = 0;
	public static int TYPE_PRODUCT = 1;
	public static int TYPE_OFFER = 2;
	public static int TYPE_STORE = 3;
	public static int TYPE_ADDRESS = 4;
	@SuppressWarnings("unused")
	private int type = TYPE_CATEGORY;

	public LoadMoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		config();
	}

	public LoadMoreView(Context context) {
		super(context);
		this.context = context;
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.loadmore, this);
		tvLoadMore = (TextView) findViewById(R.id.textView1);
		tvLoadContent = (TextView) findViewById(R.id.textView2);
	}

	public void setType(int type) {
		this.type = type;
		Resources resources = getResources();
		String text = resources.getString(R.string.Load_More_Categories);
		if (type == TYPE_PRODUCT) {
			text = resources.getString(R.string.Load_More_Products);
		}

		if (type == TYPE_OFFER) {
			text = resources.getString(R.string.Load_More_Offers);
		}

		tvLoadMore.setText(text);

		loadMore = resources.getString(R.string.Showing);
		loadMore += " {0} ";
		loadMore += resources.getString(R.string.of);
		loadMore += " {1} ";
		if (type == TYPE_PRODUCT) {
			loadMore += resources.getString(R.string.total_products);
		}

		if (type == TYPE_OFFER) {
			loadMore += resources.getString(R.string.total_offers);
		}

		if (type == TYPE_CATEGORY) {
			loadMore += resources.getString(R.string.total_categories);
		}
		tvLoadContent.setText(loadMore.replace("{0}", "0").replace("{1}", "0"));
	}

	public void setData(String size1, String size2) {
		tvLoadContent.setText(loadMore.replace("{0}", size1).replace("{1}",
				size2));
	}

}