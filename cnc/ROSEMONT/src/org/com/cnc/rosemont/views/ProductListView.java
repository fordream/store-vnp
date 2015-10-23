package org.com.cnc.rosemont.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.SortBy;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.adapter.ProductAdapter;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.items.ItemSearch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ProductListView extends LinearLayout implements
		OnItemClickListener, OnFocusChangeListener, IView {
	private ProductAdapter adapter;

	// private AnimationSlide animationSlide=new AnimationSlide();

	public ProductListView(Context context) {
		super(context);
		config(R.layout.productlist2);

	}

	public ProductListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.productlist2);
	}

	private void config(int resource_layouy) {
		// setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);

		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.setType(HeaderView.TYPE_LIST_PRODUCT);
		headerView.showButton(true, false);
		headerView.setOnClick(onClickBack, null);

		ListView listView = (ListView) findViewById(R.id.listView1);
		adapter = new ProductAdapter(getContext(), listView,
				new ArrayList<ItemSearch>());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setItemsCanFocus(true);
		adapter.setData(addDataExample());
	}

	private OnClickListener onClickBack = new OnClickListener() {
		public void onClick(View v) {
			// setAnimation(animationSlide.outToLeftAnimation());
			((IActivity) getContext()).onBack1();
		}
	};

	private List<ItemSearch> addDataExample() {
		return SortBy.sortAndAddHeader(CommonApp.ROSEMONT.search("", true));
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		ItemSearch item = adapter.getItem(position);
		if (!item.isAnphabe()) {
			String strength = item.getStrength();
			DataStore.getInstance().setConfig(Conts.STRENGTH, strength);
			DataStore.getInstance().setConfig(Conts.PRODUCTNAME,
					item.getTxtHeader());
			((IActivity) getContext()).gotoProductDetail(item.getId(),
					item.getIdTable());

		}
	}

	public void onFocusChange(View v, boolean hasFocus) {
		v.clearFocus();
	}

	public void reset() {
		CommonApp.getDataROSEMONT(getContext());
		adapter.setData(addDataExample());
		adapter.notifyDataSetChanged();
	}
}
