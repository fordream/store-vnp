package com.cnc.maispreco.parcelable;

import org.com.cnc.maispreco.MaisprecoScreen;

import com.cnc.maispreco.soap.data.Product;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.AdapterView.OnItemClickListener;

public class ProductParacel implements Parcelable {
	private MaisprecoScreen maisprecoScreen;
	private Handler handler;
	private Product product;
	private String searchType;
	private OnItemClickListener ClickListenerHome;
	private OnItemClickListener ClickListenerOffers;

	public MaisprecoScreen getMaisprecoScreen() {
		return maisprecoScreen;
	}

	public void setMaisprecoScreen(MaisprecoScreen maisprecoScreen) {
		this.maisprecoScreen = maisprecoScreen;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public OnItemClickListener getClickListenerHome() {
		return ClickListenerHome;
	}

	public void setClickListenerHome(OnItemClickListener clickListenerHome) {
		ClickListenerHome = clickListenerHome;
	}

	public OnItemClickListener getClickListenerOffers() {
		return ClickListenerOffers;
	}

	public void setClickListenerOffers(OnItemClickListener clickListenerOffers) {
		ClickListenerOffers = clickListenerOffers;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {

	}

}
