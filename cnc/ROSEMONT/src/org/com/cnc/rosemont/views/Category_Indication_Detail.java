package org.com.cnc.rosemont.views;

import java.util.ArrayList;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.adapter.SearchAdapter;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.items.ItemSearch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Category_Indication_Detail extends LinearLayout implements
		OnItemClickListener, IView {
	public static final int TYPE_CATEGORY = 1;
	public static final int TYPE_INDICATION = 2;
	HeaderView headerView;
	ListView listView;
	private SearchAdapter adapter;

	public Category_Indication_Detail(Context context) {
		super(context);
		config(R.layout.productlist2);
	}

	public Category_Indication_Detail(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.productlist2);
	}

	private void config(int resource_layouy) {
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);

		headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.showButton(true, false);
		headerView.setOnClick(onClickBack, null);

		listView = (ListView) findViewById(R.id.listView1);

		// add Header
		// ImageView imageView = new ImageView(getContext());
		// imageView.setBackgroundResource(R.drawable.img_list_product_by);
		// listView.addHeaderView(imageView);

		adapter = new SearchAdapter(getContext(), listView,
				new ArrayList<ItemSearch>());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	private OnClickListener onClickBack = new OnClickListener() {
		public void onClick(View v) {
			((IActivity) getContext()).onBack1();
		}
	};

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		ItemSearch item = (ItemSearch) arg0.getItemAtPosition(position);

		if (item != null && !item.isAnphabe()) {
			String strength = item.getStrength();
			DataStore.getInstance().setConfig(Conts.STRENGTH, strength);
			DataStore.getInstance().setConfig(Conts.PRODUCTNAME,item.getTxtHeader());
			((IActivity) getContext()).gotoProductDetail(item.getId(),
					item.getIdTable());


		}
	}

	int type = TYPE_CATEGORY;
	String data = "";

	public void setData(int type, String data) {
		this.type = type;
		this.data = data;
		if (type == TYPE_CATEGORY) {
			type = TYPE_CATEGORY;
			adapter = new SearchAdapter(getContext(), listView,
					CommonApp.ROSEMONT.getCategoryList(data));
		} else {
			type = TYPE_INDICATION;
			adapter = new SearchAdapter(getContext(), listView,
					CommonApp.ROSEMONT.getIndicationsList(data));
		}

		headerView.setText(data);

		listView.setAdapter(adapter);
	}

	public void reset() {
		setData(type, data);

	}
}