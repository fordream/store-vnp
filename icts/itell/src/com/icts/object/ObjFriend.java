package com.icts.object;


public class ObjFriend extends User {
	private int mIdFriend;
	private boolean isFriend;
	private boolean isRestricPublic;
	private int mBadge;

	// ////////mBlock == false thi bi lock
	private boolean mBlock = true;

	public int getmIdFriend() {
		return mIdFriend;
	}

	public void setmIdFriend(int mIdFriend) {
		this.mIdFriend = mIdFriend;
	}

	public boolean ismBlock() {
		return mBlock;
	}

	public void setmBlock(boolean mBlock) {
		this.mBlock = mBlock;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

	public boolean isRestricPublic() {
		return isRestricPublic;
	}

	public void setRestricPublic(boolean isRestricPublic) {
		this.isRestricPublic = isRestricPublic;
	}

	public int getmBadge() {
		return mBadge;
	}

	public void setmBadge(int mBadge) {
		this.mBadge = mBadge;
	}

}
