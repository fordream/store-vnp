package com.csn.caferhythm.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.caferhythm.csn.R;
import com.caferhythm.csn.activity.TemperatureInputActivity;
import com.caferhythm.csn.data.NewsRow;

public class NewAdapter extends ArrayAdapter<NewsRow> {
	private LayoutInflater mInflater;
	private ArrayList<NewsRow> data;
	int layoutResourceId;
	private Context mContext;
	SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat s2;

	public NewAdapter(Context context, int textViewResourceId,
			ArrayList<NewsRow> d) {
		super(context, textViewResourceId);
		mInflater = LayoutInflater.from(context);
		data = d;
		mContext = context;
		Resources r = mContext.getResources();
		s2 = new SimpleDateFormat("yyyy" + r.getString(R.string.year) + "MM"
				+ r.getString(R.string.month) + "dd"
				+ r.getString(R.string.day));
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder2 holder;
		if (convertView == null) {
			holder = new ViewHolder2();
			convertView = mInflater.inflate(R.layout.news_row, null);
			holder.newsContent = (TextView) convertView
					.findViewById(R.id.tv_new_1);
			holder.newsTime = (TextView) convertView
					.findViewById(R.id.tv_time_new_1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder2) convertView.getTag();
		}
		holder.newsContent.setText(data.get(position).getNewsContent());
		holder.newsTime.setText(data.get(position).getNewsTime());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(mContext, TemperatureInputActivity.class);
				// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				mContext.startActivity(i);
			}
		});
		return convertView;
	}
}

class ViewHolder2 {
	TextView newsTime;
	TextView newsContent;
}
