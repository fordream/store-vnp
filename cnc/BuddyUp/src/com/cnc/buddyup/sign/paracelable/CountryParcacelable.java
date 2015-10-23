package com.cnc.buddyup.sign.paracelable;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class CountryParcacelable implements Parcelable {
	private List<Country> lCountries = new ArrayList<Country>();

	public CountryParcacelable() {
	}

	public void add(String idCountry, String nameCountry) {
		if (idCountry != null) {
			Country country = new Country(idCountry, nameCountry);
			lCountries.add(country);
		}
	}

	public List<Country> getLCountries() {
		return lCountries;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(lCountries.size());
		for (int i = 0; i < lCountries.size(); i++) {
			Country country = lCountries.get(i);
			out.writeString(country.getId());
			out.writeString(country.getName());
		}
	}

	public static final Parcelable.Creator<CountryParcacelable> CREATOR = new Parcelable.Creator<CountryParcacelable>() {
		public CountryParcacelable createFromParcel(Parcel in) {
			return new CountryParcacelable(in);
		}

		public CountryParcacelable[] newArray(int size) {
			return new CountryParcacelable[size];
		}
	};

	public CountryParcacelable(Parcel in) {
		int count = in.readInt();
		lCountries = new ArrayList<Country>();
		for (int i = 0; i < count; i++) {
			String id = in.readString();
			String name = in.readString();
			lCountries.add(new Country(id, name));
		}
	}
}
