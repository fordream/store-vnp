package org.com.vnp.lmhtmanager.view;

import org.com.vnp.lmhtmanager.R;
import org.com.vnp.lmhtmanager.menu.MenuItem;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.vnp.core.base.view.CustomLinearLayoutView;

public class MenuMoreItemView extends CustomLinearLayoutView {
	public MenuMoreItemView(Context context) {
		super(context);
		init(R.layout.menu_more_item);
	}

	@Override
	public void showHeader(boolean isShowHeader) {

	}

	@Override
	public void setGender() {
		((TextView) findViewById(R.id.textView1)).setText(getData().toString());
	}
}