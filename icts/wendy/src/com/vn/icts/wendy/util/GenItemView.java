package com.vn.icts.wendy.util;

import android.content.Context;

import com.ict.library.adapter.GenderView;
import com.ict.library.view.CustomLinearLayoutView;
import com.vn.icts.wendy.model.Coupon;
import com.vn.icts.wendy.model.News;
import com.vn.icts.wendy.model.Shop;
import com.vn.icts.wendy.view.ItemCouponView;
import com.vn.icts.wendy.view.ItemNewView;
import com.vn.icts.wendy.view.ItemShopView;

public class GenItemView implements GenderView {

	@Override
	public CustomLinearLayoutView getView(Context context, Object data) {
		if (data instanceof Shop) {
			return new ItemShopView(context);
		} else if (data instanceof Coupon) {
			return new ItemCouponView(context);
		}else if(data instanceof News){
			return new ItemNewView(context);
		}

		return null;
	}
}