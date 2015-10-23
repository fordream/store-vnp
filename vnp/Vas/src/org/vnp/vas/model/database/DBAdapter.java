/**
 * 
 */
package org.vnp.vas.model.database;

import android.content.Context;

import com.ict.library.database.CommonDBAdapter;

/**
 * @author tvuong1pc
 * 
 */
public class DBAdapter extends CommonDBAdapter {

	/**
	 * @param ct
	 * @param dbName
	 * @param packagename
	 */
	public DBAdapter(Context ct) {
		super(ct, "database.sqlite", "org.vnp.vas");
	}
}