package org.cnc.qrcode.common;

import android.os.Parcel;
import android.os.Parcelable;

public class DataSend implements Parcelable {
	private int mData;

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(mData);
	}

	public static final Parcelable.Creator<DataSend> CREATOR = new Parcelable.Creator<DataSend>() {
		public DataSend createFromParcel(Parcel in) {
			return new DataSend(in);
		}

		public DataSend[] newArray(int size) {
			return new DataSend[size];
		}
	};

	private DataSend(Parcel in) {
		mData = in.readInt();
	}

}
