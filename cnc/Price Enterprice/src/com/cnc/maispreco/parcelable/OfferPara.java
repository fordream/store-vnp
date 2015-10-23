package com.cnc.maispreco.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class OfferPara implements Parcelable {
	private String id;
	private String qt;
	
	public OfferPara(String id, String qt) {
		super();
		this.id = id;
		this.qt = qt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQt() {
		return qt;
	}

	public void setQt(String qt) {
		this.qt = qt;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
