package com.cnc.buddyup.data.parcelable.common;

import android.os.Parcel;
import android.os.Parcelable;

public class CommonItemResquestParcelable implements Parcelable {

	public CommonItemResquestParcelable() {
		super();
	}

	private String id;
	private String txtView;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTxtView() {
		return txtView;
	}

	public void setTxtView(String txtView) {
		this.txtView = txtView;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		// out.writeString(userName);
		// out.writeString(firstName);
		// out.writeString(email);
		// out.writeString(age);
		// out.writeString(postalCode);
		// out.writeString(contry);
	}

	public static final Parcelable.Creator<CommonItemResquestParcelable> CREATOR = new Parcelable.Creator<CommonItemResquestParcelable>() {
		public CommonItemResquestParcelable createFromParcel(Parcel in) {
			return new CommonItemResquestParcelable(in);
		}

		public CommonItemResquestParcelable[] newArray(int size) {
			return new CommonItemResquestParcelable[size];
		}
	};

	public CommonItemResquestParcelable(Parcel in) {
		// userName = in.readString();
		// firstName = in.readString();
		// email = in.readString();
		// age = in.readString();
		// postalCode = in.readString();
		// contry = in.readString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id = ").append(id).append("\n");
		sb.append("txtView = ").append(txtView).append("\n");
		return sb.toString();
	}
}