/**
 * 
 */
package com.vn.icts.wendy.controller.group.coupon;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ict.library.activity.BaseGroupActivity;
import com.ict.library.activity.BaseListActivity;
import com.ict.library.adapter.BaseAdapter;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.async.WendyAsyn;
import com.vn.icts.wendy.async.WendyAsyn.CallBack;
import com.vn.icts.wendy.model.Coupon;
import com.vn.icts.wendy.util.GenItemView;
import com.vn.icts.wendy.view.TopBarView;

/**
 * @author tvuong1pc
 * 
 */
public class CouponListActivity extends BaseListActivity implements
		OnItemClickListener {
	private TopBarView topBarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// add header for shoplist
		topBarView = new TopBarView(this);
		topBarView.setTitle(R.string.coupon_list);
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
		new WendyAsyn(WendyAsyn.TYPE_ALL_COUPON_LIST, new CallBack() {
			
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
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			getParent().onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position,
			long arg3) {
		Coupon a = (Coupon) adapter.getItemAtPosition(position);
		Bundle extras = new Bundle();
		extras.putParcelable("COUPON", a);
		((BaseGroupActivity) getParent()).addView("CouponDetailActivity",
				CouponDetailActivity.class, extras);
	}
}