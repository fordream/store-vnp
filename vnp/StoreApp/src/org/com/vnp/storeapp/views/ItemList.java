package org.com.vnp.storeapp.views;

import org.com.cnc.common.android.database.DataStore;
import org.com.vnp.storeapp.MyListData;
import org.com.vnp.storeapp.R;
import org.com.vnp.storeapp.adapter.items.Item;
import org.com.vnp.storeapp.common.Conts;
import org.com.vnp.storeapp.database.DBAdapter;
import org.com.vnp.storeapp.database.Plan;
import org.com.vnp.storeapp.database.QuanAn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemList extends LinearLayout implements OnClickListener,
		AlertDialog.OnClickListener {

	private Item item;

	private Context mContext;

	public ItemList(Context context) {
		super(context);
		mContext = context;
		config();
	}

	public ItemList(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.item, this);
		findViewById(R.id.buttonX1).setOnClickListener(this);
	}

	public View addData(Item item) {
		this.item = item;

		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setText(this.item.getTitle());

		TextView textView2 = (TextView) findViewById(R.id.textView2);
		textView2.setText(this.item.getContent());

		return this;
	}

	public void onClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.delete);
		builder.setMessage(R.string.comfirm);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.ok, this);
		builder.setNegativeButton(R.string.cancel, null);
		builder.show();
	}

	public void onClick(DialogInterface dialog, int which) {
		if ("true".equals(DataStore.getInstance().getConfig(Conts.DIADIEM))) {
			DBAdapter.getInstance().delete(new Plan(), item);
		} else {
			DBAdapter.getInstance().delete(new QuanAn(), item);
		}

		((MyListData) mContext).onReload();
	}

}