/**
 * 
 */
package com.vn.icts.wendy.controller.group.shop;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ict.library.activity.BaseListActivity;
import com.ict.library.adapter.BaseAdapter;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.async.WendyAsyn;
import com.vn.icts.wendy.async.WendyAsyn.CallBack;
import com.vn.icts.wendy.controller.group.ShopGroupActivity;
import com.vn.icts.wendy.model.Shop;
import com.vn.icts.wendy.util.GenItemView;
import com.vn.icts.wendy.view.TopBarView;

/**
 * @author tvuong1pc
 * 
 */
public class ShopListActivity extends BaseListActivity implements
		OnItemClickListener {
	private TopBarView topBarView;
	private boolean isNearShop = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {
			isNearShop = getIntent().getExtras().getBoolean(
					ShopGroupActivity.LOCATION, false);
		}

		// add header for shoplist
		topBarView = new TopBarView(this);
		topBarView.setTitle(R.string.shop_list);
		topBarView.showRip();
		getListView().setBackgroundResource(R.drawable.main_bg);
		getListView().addHeaderView(topBarView);

		List<Object> list = new ArrayList<Object>();
		setListAdapter(new BaseAdapter(this, list, new GenItemView()));
		getListView().setOnItemClickListener(this);
		getData();
	}
	
	private void getData() {
		topBarView.showProgressBar(true);
		new WendyAsyn(WendyAsyn.TYPE_SHOP_LIST, new CallBack() {
			
			public void callBack(List<Object> lDatas) {
				topBarView.showProgressBar(false);
				if(lDatas.size() != 0){
					((BaseAdapter)getListAdapter()).addAll(lDatas);
					((BaseAdapter)getListAdapter()).notifyDataSetChanged();
				}
			}
		}, null).execute("");
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && !isNearShop) {
			getParent().onBackPressed();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}

	

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position,
			long arg3) {
		Shop a = (Shop) adapter.getItemAtPosition(position);
		Bundle extras = new Bundle();
		Intent intent = new Intent(getParent(), ShopDetailActivity.class);
		extras.putParcelable("SHOP", a);
		getParent().startActivity(intent.putExtras(extras));
	}
}