package com.vnp.shortfirmfestival_rework.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.vnp.core.callback.ExeCallBack;
import com.vnp.core.callback.ResClientCallBack;
import com.vnp.core.common.ImageLoaderUtils;
import com.vnp.core.service.RestClient;
import com.vnp.core.service.RestClient.RequestMethod;
import com.vnp.shortfirmfestival_rework.R;

public class PhotosAdapter extends BaseAdapter {
	List<ContentValues> lContentValues = new ArrayList<ContentValues>();
	private GridView listView;
	private String type = "all";
	ProgressBar header_progressbar;

	public PhotosAdapter(GridView listView, String type, ProgressBar header_progressbar) {
		this.listView = listView;
		this.type = type;
		this.header_progressbar = header_progressbar;
		listView.setAdapter(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// if (position == getCount()) {
				// return;
				// }
				// ContentValues contentValues = (ContentValues)
				// parent.getItemAtPosition(position);
				// Intent intent = new Intent(parent.getContext(),
				// NewsDetailActivity.class);
				// intent.putExtra("data", contentValues);
				// parent.getContext().startActivity(intent);

			}
		});
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (lContentValues.size() >= 0 && firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
					//execute(lContentValues.size());
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
			convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.photo_item, null);
		}

		ImageView photo_img = (ImageView) convertView.findViewById(R.id.photo_img);

		ContentValues contentValues = (ContentValues) getItem(position);

		String photoUrl = contentValues.getAsString("img_big");
		if (photoUrl != null) {
			photoUrl = photoUrl.replace("https", "http");
		}

		ImageLoaderUtils.getInstance(parent.getContext()).DisplayImage(photoUrl, photo_img);
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
		final String api = "http://www.shortshorts.org/api/photos.php?type=" + type + "&lang=en&limit=10&offset=" + offset;
		ResClientCallBack clientCallBack = new ResClientCallBack() {
			@Override
			public RequestMethod getMedthod() {
				return RequestMethod.POST;
			}

			@Override
			public void onCallBack(Object arg0) {
				RestClient client = (RestClient) arg0;
				if (client.getResponseCode() == 200) {
					List<ContentValues> list = new ArrayList<ContentValues>();
					try {
						JSONObject jsonObject = new JSONObject(client.getResponse());
						JSONArray array = jsonObject.getJSONArray("data");
						String[] keys = new String[] {//
						"type",//
								"img_small",//
								"img_medium",//
								"img_big",//
								"comment_count",//
								"listComment",//
								"caption"//
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
