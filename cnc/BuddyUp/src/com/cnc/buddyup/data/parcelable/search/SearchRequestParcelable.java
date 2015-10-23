package com.cnc.buddyup.data.parcelable.search;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchRequestParcelable implements Parcelable {
	private String sport_activity;
	private String day;
	private String time;
	private String phylosophy;
	private String skillLevel;
	private String maximumDistance;
	private String username;
	private boolean skipDistance;
	private boolean isIndividuals;

	public SearchRequestParcelable() {
		super();
	}

	public boolean isIndividuals() {
		return isIndividuals;
	}

	public void setIndividuals(boolean isIndividuals) {
		this.isIndividuals = isIndividuals;
	}

	public String getSport_activity() {
		return sport_activity;
	}

	public void setSport_activity(String sport_activity) {
		this.sport_activity = sport_activity;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPhylosophy() {
		return phylosophy;
	}

	public void setPhylosophy(String phylosophy) {
		this.phylosophy = phylosophy;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getMaximumDistance() {
		return maximumDistance;
	}

	public void setMaximumDistance(String maximumDistance) {
		this.maximumDistance = maximumDistance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isSkipDistance() {
		return skipDistance;
	}

	public void setSkipDistance(boolean skipDistance) {
		this.skipDistance = skipDistance;
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

	public static final Parcelable.Creator<SearchRequestParcelable> CREATOR = new Parcelable.Creator<SearchRequestParcelable>() {
		public SearchRequestParcelable createFromParcel(Parcel in) {
			return new SearchRequestParcelable(in);
		}

		public SearchRequestParcelable[] newArray(int size) {
			return new SearchRequestParcelable[size];
		}
	};

	public SearchRequestParcelable(Parcel in) {
		// userName = in.readString();
		// firstName = in.readString();
		// email = in.readString();
		// age = in.readString();
		// postalCode = in.readString();
		// contry = in.readString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.sport_activity).append("\n");
		sb.append(this.day).append("\n");
		sb.append(this.time).append("\n");
		sb.append(this.phylosophy).append("\n");
		sb.append(this.skillLevel).append("\n");
		sb.append(this.maximumDistance).append("\n");
		sb.append(this.username).append("\n");
		sb.append(this.skipDistance).append("\n");
		sb.append(this.isIndividuals).append("\n");
		return sb.toString();
	}
}