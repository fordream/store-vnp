package com.vn.icts.wendy.model;

import com.ict.library.database.DataStore;

public class Setting {
	private String userName;
	private boolean isMale;
	private String dateOfBirth;
	private boolean isPush;
	private boolean isConnectFace;
	private boolean isConnectTwiiter;

	public Setting() {
		userName = DataStore.getInstance().get("userName", "");
		isMale = DataStore.getInstance().get("isMale", false);
		dateOfBirth = DataStore.getInstance().get("dateOfBirth", "");
		isPush = DataStore.getInstance().get("isPush", false);
		isConnectFace = DataStore.getInstance().get("isConnectFace", false);
		isConnectTwiiter = DataStore.getInstance().get("isConnectTwiiter",
				false);
	}

	/**
	 * save
	 */
	public void save() {
		DataStore.getInstance().save("userName", userName);
		DataStore.getInstance().save("isMale", isMale);
		DataStore.getInstance().save("dateOfBirth", dateOfBirth);
		DataStore.getInstance().save("isPush", isPush);
		DataStore.getInstance().save("isConnectFace", isConnectFace);
		DataStore.getInstance().save("isConnectTwiiter", isConnectTwiiter);
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the isMale
	 */
	public boolean isMale() {
		return isMale;
	}

	/**
	 * @param isMale
	 *            the isMale to set
	 */
	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the isPush
	 */
	public boolean isPush() {
		return isPush;
	}

	/**
	 * @param isPush
	 *            the isPush to set
	 */
	public void setPush(boolean isPush) {
		this.isPush = isPush;
	}

	/**
	 * @return the isConnectFace
	 */
	public boolean isConnectFace() {
		return isConnectFace;
	}

	/**
	 * @param isConnectFace
	 *            the isConnectFace to set
	 */
	public void setConnectFace(boolean isConnectFace) {
		this.isConnectFace = isConnectFace;
	}

	/**
	 * @return the isConnectTwiiter
	 */
	public boolean isConnectTwiiter() {
		return isConnectTwiiter;
	}

	/**
	 * @param isConnectTwiiter
	 *            the isConnectTwiiter to set
	 */
	public void setConnectTwiiter(boolean isConnectTwiiter) {
		this.isConnectTwiiter = isConnectTwiiter;
	}
}
