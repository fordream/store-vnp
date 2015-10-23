package com.cnc.maispreco.common;

import android.view.View;

import com.cnc.maispreco.views.CategoryViewControl;
import com.cnc.maispreco.views.GenericProductsViewControl;
import com.cnc.maispreco.views.HomeViewControl;
import com.cnc.maispreco.views.OffersViewControl;
import com.cnc.maispreco.views.SearchViewControl;
import com.cnc.maispreco.views.SimilarProductsViewControl;

public class ActivityCommon {
	public static void notifyDataSetChanged(View view) {
		if (view == null) {
			return;
		} else if (view instanceof CategoryViewControl) {
			((CategoryViewControl) view).notifyDataSetChanged();
		} else if (view instanceof OffersViewControl) {
			((OffersViewControl) view).notifyDataSetChangedBesprice();
			((OffersViewControl) view).notifyDataSetChangedNearyBy();
		} else if (view instanceof HomeViewControl) {
			((HomeViewControl) view).notifyDataSetChanged();
		} else if (view instanceof GenericProductsViewControl) {
			((GenericProductsViewControl) view).notifyDataSetChanged();
		} else if (view instanceof SearchViewControl) {
			((SearchViewControl) view).notifyDataSetChanged();
		} else if (view instanceof SimilarProductsViewControl) {
			((SimilarProductsViewControl) view).notifyDataSetChanged();
		}
	}
}
