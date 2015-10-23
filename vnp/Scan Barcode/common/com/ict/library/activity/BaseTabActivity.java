/**
 * 
 */
package com.ict.library.activity;

import android.app.TabActivity;
import android.view.View;

/**
 * class base for all activity
 * 
 * @author tvuong1pc
 * 
 */
public class BaseTabActivity extends TabActivity {
	
	/**
	 * convert view from resource
	 * @param res
	 * @return
	 */
	public <T extends View> T getView(int res) {
		@SuppressWarnings("unchecked")
		T view = (T) findViewById(res);
		return view;
	}
}