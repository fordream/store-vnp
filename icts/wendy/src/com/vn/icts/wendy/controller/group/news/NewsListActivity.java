/**
 * 
 */
package com.vn.icts.wendy.controller.group.news;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ict.library.activity.BaseGroupActivity;
import com.ict.library.activity.BaseListActivity;
import com.ict.library.adapter.BaseAdapter;
import com.ict.library.database.DataStore;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.async.WendyAsyn;
import com.vn.icts.wendy.async.WendyAsyn.CallBack;
import com.vn.icts.wendy.model.News;
import com.vn.icts.wendy.util.GenItemView;
import com.vn.icts.wendy.view.TopBarView;

/**
 * @author tvuong1pc
 * 
 */
public class NewsListActivity extends BaseListActivity implements
		OnItemClickListener {
	private TopBarView topBarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// add header for shoplist
		topBarView = new TopBarView(this);
		topBarView.setTitle(R.string.news_list);
		topBarView.showRip();
		getListView().addHeaderView(topBarView);
		getListView().setBackgroundResource(R.drawable.bgpaer);
		List<Object> list = new ArrayList<Object>();
		setListAdapter(new BaseAdapter(this, list, new GenItemView()));

		getListView().setOnItemClickListener(this);

		getData();
	}

	private void getData() {
		topBarView.showProgressBar(true);
		new WendyAsyn(WendyAsyn.TYPE_NEW_LIST, new CallBack() {

			public void callBack(List<Object> lDatas) {
				topBarView.showProgressBar(false);
				if (lDatas.size() != 0) {
					((BaseAdapter) getListAdapter()).addAll(lDatas);
					((BaseAdapter) getListAdapter()).notifyDataSetChanged();
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
	protected void onResume() {
		super.onResume();
		((BaseAdapter)getListAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position,
			long arg3) {

		News a = (News) adapter.getItemAtPosition(position);

		String ids = DataStore.getInstance().get("news_id", "");
		if (!ids.contains(";" + a.getId()+ ";") ) {
			ids += ";" + a.getId() + ";";
			 DataStore.getInstance().save("news_id",ids);
		}

		Bundle extras = new Bundle();
		extras.putParcelable("NEWS", a);
		((BaseGroupActivity) getParent()).addView("NewsDetailActivity",
				NewsDetailActivity.class, extras);
		
		((BaseAdapter)getListAdapter()).notifyDataSetChanged();
	}
}
