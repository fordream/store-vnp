package org.com.vnp.lmhtmanager.view;

import org.com.vnp.lmhtmanager.R;
import org.com.vnp.lmhtmanager.menu.MenuItem;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.vnp.core.base.view.CustomLinearLayoutView;

public class MenuItemView extends CustomLinearLayoutView {
	public MenuItemView(Context context) {
		super(context);
		init(R.layout.menuitem);
	}

	@Override
	public void showHeader(boolean isShowHeader) {

	}

	@Override
	public void setGender() {

		if (getData() instanceof Cursor) {
			Cursor cursor = (Cursor) getData();
			((TextView) findViewById(R.id.textView1)).setText(cursor.getString(cursor.getColumnIndex("displayName")));
		} else if (getData() instanceof String) {
			((TextView) findViewById(R.id.textView1)).setText(getData().toString());
			findViewById(R.id.imageView1).setVisibility(View.GONE);
		} else {
			MenuItem menuItem = (MenuItem) getData();
			((TextView) findViewById(R.id.textView1)).setText(menuItem.getText());
			findViewById(R.id.imageView1).setBackgroundResource(menuItem.getResImg());
		}
	}
}