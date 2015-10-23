package com.cnc.maispreco.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryPara implements Parcelable {
	private OnItemClickListener clickCate;
	private OnItemClickListener clickProduct;
	private String id;
	

	public CategoryPara(OnItemClickListener clickCate,
			OnItemClickListener clickProduct, String id) {
		super();
		this.clickCate = clickCate;
		this.clickProduct = clickProduct;
		this.id = id;
	}

	public OnItemClickListener getClickCate() {
		return clickCate;
	}

	public void setClickCate(OnItemClickListener clickCate) {
		this.clickCate = clickCate;
	}

	public OnItemClickListener getClickProduct() {
		return clickProduct;
	}

	public void setClickProduct(OnItemClickListener clickProduct) {
		this.clickProduct = clickProduct;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {

	}

}
