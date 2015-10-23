package org.com.vnp.storeapp.views;

import org.com.cnc.common.android.database.DataStore;
import org.com.vnp.storeapp.MyListData;
import org.com.vnp.storeapp.R;
import org.com.vnp.storeapp.common.Conts;
import org.com.vnp.storeapp.views.wigets.DialogPlan;
import org.com.vnp.storeapp.views.wigets.DialogQuanAn;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class HeaderList extends LinearLayout implements OnClickListener {
	private MyListData myListData;

	public HeaderList(Context context) {
		super(context);
		myListData = (MyListData) context;
		config();
	}

	public HeaderList(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.headerlist, this);
		findViewById(R.id.buttonAdd1).setOnClickListener(this);
		setOnClickListener(this);
	}

	public void onClick(View v) {
		if ("true".equals(DataStore.getInstance().getConfig(Conts.DIADIEM))) {
			DialogPlan dialogQuanAn = new DialogPlan(myListData);
			dialogQuanAn.show();
		} else {
			DialogQuanAn dialogQuanAn = new DialogQuanAn(myListData);
			dialogQuanAn.show();
		}
	}
}
