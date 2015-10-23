/**
 * 
 */
package com.vn.icts.wendy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.ict.library.view.CustomLinearLayoutView;
import com.vn.icts.wendy.R;

/**
 * @author tvuong1pc
 * 
 */
public class TabBarView extends CustomLinearLayoutView implements
		OnClickListener {
	public interface OnCallBackTab {
		public void onCallBackTab(int position);
	}

	public int curentTab = -1;

	private ImageView imgShop;
	private ImageView imgCoupon;
	private ImageView imageViewTabScanner;
	private ImageView imgNews;
	private ImageView imgSearch;

	private OnCallBackTab onCallBackTab;

	public void setOnCallBackTab(OnCallBackTab onCallBackTab) {
		this.onCallBackTab = onCallBackTab;
	}

	/**
	 * @param context
	 */
	public TabBarView(Context context) {
		super(context);
		init(R.layout.tabbar);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TabBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.tabbar);
	}

	@Override
	public void init(int res) {
		super.init(res);
		imgShop = getView(R.id.imgShop);
		imgCoupon = getView(R.id.imgCoupon);
		imageViewTabScanner = getView(R.id.imageViewTabScanner);
		imgNews = getView(R.id.imgNews);
		imgSearch = getView(R.id.imgSearch);

		imgShop.setOnClickListener(this);
		imgCoupon.setOnClickListener(this);
		imageViewTabScanner.setOnClickListener(this);
		imgNews.setOnClickListener(this);
		imgSearch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		int newCurentTab = 0;
		if (imgShop == v) {
			newCurentTab = 0;
			// 0
		} else if (imgCoupon == v) {
			// 1
			newCurentTab = 1;
		} else if (imageViewTabScanner == v) {
			// 2
			newCurentTab = 2;
		} else if (imgNews == v) {
			// 3
			newCurentTab = 3;
		} else if (imgSearch == v) {
			// 4
			newCurentTab = 4;
		}

		if (this.curentTab != newCurentTab) {
			if (newCurentTab != 2) {
				this.curentTab = newCurentTab;
			}

			if (onCallBackTab != null) {
				onCallBackTab.onCallBackTab(this.curentTab);
			}

			config();
		}
		if (newCurentTab == 2) {
			if (onCallBackTab != null) {
				onCallBackTab.onCallBackTab(newCurentTab);
			}
		}
	}

	private void config() {
		imgShop.setBackgroundResource(curentTab == 0 ? R.drawable.tvuong_tab_click_selected
				: R.drawable.tvuong_tab_click);
		imgCoupon
				.setBackgroundResource(curentTab == 1 ? R.drawable.tvuong_tab_click_selected
						: R.drawable.tvuong_tab_click);
		imgNews.setBackgroundResource(curentTab == 3 ? R.drawable.tvuong_tab_click_selected
				: R.drawable.tvuong_tab_click);
		imgSearch
				.setBackgroundResource(curentTab == 4 ? R.drawable.tvuong_tab_click_selected
						: R.drawable.tvuong_tab_click);
	}

	public void setCurentTab(int i) {
		curentTab = i;
		config();
	}
}