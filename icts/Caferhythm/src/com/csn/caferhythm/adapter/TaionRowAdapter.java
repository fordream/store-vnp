package com.csn.caferhythm.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.caferhythm.csn.R;
import com.caferhythm.csn.activity.TemperatureInputActivity;
import com.caferhythm.csn.data.TaionRow;

public class TaionRowAdapter extends ArrayAdapter<TaionRow> {
	private LayoutInflater mInflater;
	private ArrayList<TaionRow> data;
	int layoutResourceId;
	private Context mContext;
	SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat s2;
	private String headName="";

	public TaionRowAdapter(Context context, int textViewResourceId,
			ArrayList<TaionRow> d) {
		super(context, textViewResourceId);
		mInflater = LayoutInflater.from(context);
		data = d;
		mContext = context;
		Resources r = mContext.getResources();
		s2 = new SimpleDateFormat("yyyy" + r.getString(R.string.year) + "MM"
				+ r.getString(R.string.month)+"dd"+r.getString(R.string.day));
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.taion_row, null);
			holder.taionTV = (TextView) convertView
					.findViewById(R.id.tv_taion_row_taion);
			holder.dateTV = (TextView) convertView
					.findViewById(R.id.tv_taion_row_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			headName = s2.format(s1.parse(data.get(position)
					.getDate()));
			holder.dateTV.setText(headName);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (data.get(position).getTaion().length() > 1)
			holder.taionTV.setText(data.get(position).getTaion()+"\u2103");
		else {
			holder.taionTV.setText("--"+"\u2103");
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(mContext,TemperatureInputActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				if(data.get(position).getTaion().length()>0)
					i.putExtra("taion", data.get(position).getTaion());
				i.putExtra("headname", data.get(position).getDate());
				mContext.startActivity(i);
			}
		});
		return convertView;
	}
}

class ViewHolder {
	TextView dateTV;
	TextView taionTV;
}
