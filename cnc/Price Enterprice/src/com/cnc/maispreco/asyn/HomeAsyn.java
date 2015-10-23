package com.cnc.maispreco.asyn;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.maispreco.response.ProductResponse;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;
import org.com.cnc.maispreco.common.TrackerGoogle;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;

import com.cnc.maispreco.common.CommonView;
import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.soap.data.Search;
import com.cnc.maispreco.views.HomeViewControl;

public class HomeAsyn extends AsynTask {
	private List<Product> lProducts = new ArrayList<Product>();
	// DATE 9/1//2011 delete
	// private OnItemClickListener onItemClickListener;
	private Handler handler2;
	private HomeViewControl homeViewControl;

	public HomeAsyn(MaisprecoScreen maisprecoScreen, Handler handler,
			HomeViewControl homeViewControl) {
		super(maisprecoScreen);
		this.maisprecoScreen = maisprecoScreen;
		this.handler2 = handler;
		this.homeViewControl = homeViewControl;
	}

	private ProductResponse productResponse;

	protected String doInBackground(String... params) {
		showDialog();
		TrackerGoogle.homeTracker(maisprecoScreen, "/");
		
		Search search = new Search();
		search.setSearchType("8");
		search.setToken(Common.tooken);
		productResponse = Product.getLProductHome(search, maisprecoScreen);
		lProducts = productResponse.getlProducts();
		return null;
	}

	protected void onPostExecute(String result) {
		homeViewControl.addProduct(lProducts);
		homeViewControl.notifyDataSetChanged();
		if ("-1".equals(productResponse.getStatusCode())) {
			Message message = new Message();
			message.what = 3;
			message.obj = productResponse.getStatusMessage();
			handler2.sendMessage(message);
		} else if (lProducts.size() == 0) {
			Resources resources = maisprecoScreen.getResources();
			resources.getString(R.string.Sorry_there_is_no_result_available);
			CommonView.makeText(maisprecoScreen, resources
					.getString(R.string.Sorry_there_is_no_result_available));
		}
		dimiss();
	}
}
