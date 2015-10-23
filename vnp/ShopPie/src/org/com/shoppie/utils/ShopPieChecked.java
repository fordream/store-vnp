package org.com.shoppie.utils;

import android.os.Build;

public class ShopPieChecked {
	public enum StyleChecked{
		NEEDUSESUPPORTVERSION
	}
	public interface IShopPieCheckedCallback {
		public void checkedCallback(boolean can);
	}

	public void execute(IShopPieCheckedCallback checkedCallback, StyleChecked styleChecked) {
		if(checkedCallback== null){
			return;
		}
		
		if(styleChecked == StyleChecked.NEEDUSESUPPORTVERSION){
			checkedCallback.checkedCallback(needUseSupportVersion());
		}

	}

	public boolean needUseSupportVersion() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
	}
}