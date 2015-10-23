package org.com.vnp.lmhtmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LMHTStaticChampion extends dto.Static.Champion implements
		Parcelable {

	public LMHTStaticChampion(dto.Static.Champion champion) {
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		// out.writeInt(mData);
	}

//	public static final Parcelable.Creator<LMHTStaticChampion> CREATOR = new Parcelable.Creator<LMHTStaticChampion>() {
//		public LMHTStaticChampion createFromParcel(Parcel in) {
//			return new LMHTStaticChampion(in);
//		}
//
//		public LMHTStaticChampion[] newArray(int size) {
//			return new LMHTStaticChampion[size];
//		}
//	};

	private LMHTStaticChampion(Parcel in) {
		// mData = in.readInt();
	}
}