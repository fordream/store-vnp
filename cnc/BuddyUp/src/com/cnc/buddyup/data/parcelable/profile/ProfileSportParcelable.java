package com.cnc.buddyup.data.parcelable.profile;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileSportParcelable implements Parcelable {
	private String sportName;
	private String skillLevel;
	private String philosophy;
	private boolean[][] shcedule = new boolean[7][5];

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getPhilosophy() {
		return philosophy;
	}

	public void setPhilosophy(String philosophy) {
		this.philosophy = philosophy;
	}

	public boolean[][] getShcedule() {
		return shcedule;
	}

	public void setShcedule(boolean[][] shcedule) {
		this.shcedule = shcedule;
	}

	public ProfileSportParcelable() {
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
	}

	public static final Parcelable.Creator<ProfileSportParcelable> CREATOR = new Parcelable.Creator<ProfileSportParcelable>() {
		public ProfileSportParcelable createFromParcel(Parcel in) {
			return new ProfileSportParcelable(in);
		}

		public ProfileSportParcelable[] newArray(int size) {
			return new ProfileSportParcelable[size];
		}
	};

	public ProfileSportParcelable(Parcel in) {
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.sportName).append("\n");
		sb.append(this.skillLevel).append("\n");
		sb.append(this.philosophy).append("\n");

		for (int i = 0; i < shcedule.length; i++) {
			boolean data[] = shcedule[i];
			String content = "";
			for (int j = 0; j < data.length; j++) {
				content += data[j] ? "1 " : "0 ";
			}
			sb.append(content).append("\n");
		}
		return sb.toString();
	}
}