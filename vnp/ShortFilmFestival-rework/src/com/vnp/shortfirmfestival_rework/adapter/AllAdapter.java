package com.vnp.shortfirmfestival_rework.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vnp.core.callback.ExeCallBack;
import com.vnp.core.callback.ResClientCallBack;
import com.vnp.core.common.ImageLoaderUtils;
import com.vnp.core.service.RestClient;
import com.vnp.shortfirmfestival_rework.R;
import com.vnp.shortfirmfestival_rework.shortfirmfestival_rework.NewsDetailActivity;

public class AllAdapter extends BaseAdapter {
	List<ContentValues> lContentValues = new ArrayList<ContentValues>();
	private ListView listView;
	private String type = "all";
	ProgressBar header_progressbar;

	public AllAdapter(ListView listView, String type, ProgressBar header_progressbar) {
		this.listView = listView;
		this.type = type;
		this.header_progressbar = header_progressbar;
		listView.setAdapter(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//				if (position == getCount()) {
//					return;
//				}
				ContentValues contentValues = (ContentValues) parent.getItemAtPosition(position);
				// theater to web
				Intent intent = new Intent(parent.getContext(), NewsDetailActivity.class);
				intent.putExtra("data", contentValues);
				parent.getContext().startActivity(intent);
				// String _type = contentValues.getAsString("_type");
				// if ("theater".equals(_type)) {
				//
				// } else {
				//
				// }
				//
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (lContentValues.size() >= 0 && firstVisibleItem + visibleItemCount >= totalItemCount) {
					execute(lContentValues.size());
				}
			}
		});

		execute(0);
	}

	@Override
	public int getCount() {
		return lContentValues.size();
	}

	@Override
	public Object getItem(int position) {
		return lContentValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.all_item, null);
		}

		ImageView all_img = (ImageView) convertView.findViewById(R.id.all_img);

		TextView all_name = (TextView) convertView.findViewById(R.id.all_name);
		TextView all_description = (TextView) convertView.findViewById(R.id.all_description);
		TextView all_description1 = (TextView) convertView.findViewById(R.id.all_description1);

		ContentValues contentValues = (ContentValues) getItem(position);

		all_name.setText(Html.fromHtml(contentValues.getAsString("date")));
		all_description.setText(Html.fromHtml(contentValues.getAsString("title")));
		all_description1.setText(Html.fromHtml(contentValues.getAsString("shortdesc")));

		if (!"0".equals(contentValues.getAsString("hasPhoto"))) {
			all_img.setVisibility(View.VISIBLE);
		} else {
			all_img.setVisibility(View.GONE);
		}

		String photoUrl = contentValues.getAsString("photoUrl");
		ImageLoaderUtils.getInstance(parent.getContext()).DisplayImage(photoUrl, all_img, null);
		return convertView;
	}

	public void addList(List<ContentValues> list) {
		lContentValues.addAll(list);
		notifyDataSetChanged();
	}

	private boolean isRun = false;

	private void execute(int offset) {
		if (isRun) {
			return;
		}
		header_progressbar.setVisibility(View.VISIBLE);
		isRun = true;
		final String api = "http://www.shortshorts.org/api/list.php?type=" + type + "&lang=en&limit=10&offset=" + offset;

		ResClientCallBack clientCallBack = new ResClientCallBack() {
			@Override
			public void onCallBack(Object arg0) {
				RestClient client = (RestClient) arg0;
				if (client.getResponseCode() == 200) {
					List<ContentValues> list = new ArrayList<ContentValues>();
					try {
						JSONObject jsonObject = new JSONObject(client.getResponse());
						JSONArray array = jsonObject.getJSONArray("data");
						String[] keys = new String[] {//
						"_type",//
								"data_id",//
								"date",//
								"title",//
								"shortdesc",//
								"url",//
								"hasPhoto",//
								"photoUrl",//
								"hasVideo"//
						};//

						for (int i = 0; i < array.length(); i++) {
							JSONObject object = array.getJSONObject(i);
							ContentValues contentValues = new ContentValues();
							for (String key : keys) {
								contentValues.put(key, object.getString(key));
							}

							list.add(contentValues);
						}

						addList(list);
					} catch (Exception e) {
					}
				} else {

				}
				header_progressbar.setVisibility(View.GONE);
				isRun = false;
			}

			@Override
			public String getUrl() {
				return api;
			}
		};

		new ExeCallBack().executeAsynCallBack(clientCallBack);
	}

}
