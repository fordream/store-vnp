package com.cnc.buddyup.sign.paracelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(name);
	}

	public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
		public Country createFromParcel(Parcel in) {
			return new Country(in);
		}

		public Country[] newArray(int size) {
			return new Country[size];
		}
	};

	public Country(Parcel in) {
		id = in.readString();
		name = in.readString();
	}
}