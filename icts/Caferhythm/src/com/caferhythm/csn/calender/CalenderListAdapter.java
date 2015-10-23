package com.caferhythm.csn.calender;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caferhythm.csn.R;

public class CalenderListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<String> dates;
	private int width;
	
	
	public CalenderListAdapter(Context context, ArrayList<String> dates) {
		super();
		this.context = context;
		this.dates = dates;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return dates.size();
	}

	@Override
	public Object getItem(int position) {
		return dates.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ArrayList<ViewHold> listViewHold;
		View view = convertView;
		if(view == null){
			view = inflater.inflate(R.layout.calender_list_item, null);
			listViewHold = new ArrayList<ViewHold>();
			for(int i=0;i<7;i++){
				final ViewHold v = new ViewHold();
				v.itemPosition = (position *7)+i;
				//v.calender = (LinearLayout) view.findViewById(R.id.LinearLayout06);
				v.calender.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View vs) {
						Toast.makeText(context, "item: " + v.itemPosition, Toast.LENGTH_LONG).show();
						Log.e("Alo","Alo Anh day");
					}
				});
				listViewHold.add(v);
				
			}
			view.setTag(listViewHold);
		}else{
				listViewHold = (ArrayList<ViewHold>) view.getTag();
			}
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "item: test", Toast.LENGTH_LONG).show();
				Log.e("Alo","Alo Anh day");
			}
		});
		return view;
	}
	
	class ViewHold{
		private LinearLayout calender;
		private TextView days;
		private ImageView icon;
		private int itemPosition;
		
		
		public int getItemPosition() {
			return itemPosition;
		}
		public void setItemPosition(int itemPosition) {
			this.itemPosition = itemPosition;
		}
		public LinearLayout getCalender() {
			return calender;
		}
		public void setCalender(LinearLayout calender) {
			this.calender = calender;
		}
		public TextView getDays() {
			return days;
		}
		public void setDays(TextView days) {
			this.days = days;
		}
		public ImageView getIcon() {
			return icon;
		}
		public void setIcon(ImageView icon) {
			this.icon = icon;
		}
		
	}

}
