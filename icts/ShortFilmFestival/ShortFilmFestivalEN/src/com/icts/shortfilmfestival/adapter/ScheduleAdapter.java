package com.icts.shortfilmfestival.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icts.shortfilmfestival.entity.ScheduleEntity;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.vnp.shortfilmfestival.R;

public class ScheduleAdapter extends ArrayAdapter<ScheduleEntity> {

	private LayoutInflater mInflater;
	private Context mContext;

	public ScheduleAdapter(Context context, List<ScheduleEntity> objects) {
		super(context, 0, objects);
		mInflater = LayoutInflater.from(context.getApplicationContext());
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("NewsListFragment", "----------ScheduleAdapter------------:" + position);
		final ViewHolder mViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_event, parent, false);
			mViewHolder = new ViewHolder();
			
			mViewHolder.title = (TextView) convertView.findViewById(R.id.txt_even_title);
			mViewHolder.date = (TextView) convertView.findViewById(R.id.txt_even_date);
			mViewHolder.time = (TextView) convertView.findViewById(R.id.txt_even_time);
			mViewHolder.imgFree = (ImageView) convertView.findViewById(R.id.imgFree);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		mViewHolder.title.setText(getItem(position).getTitle());
		mViewHolder.date.setText(getItem(position).getDate());
		mViewHolder.time.setText(getItem(position).getTime());
		boolean isJp = false;
		if (Resource.localization.equals(ISettings.LANG_JP_FONT))
		{
			isJp = true;
		}
		FontUtils.setCustomFont(mViewHolder.title, false, isJp, mContext.getAssets());
		FontUtils.setCustomFont(mViewHolder.date, false, isJp, mContext.getAssets());
		FontUtils.setCustomFont(mViewHolder.time, false, false, mContext.getAssets());
		if(position%2 == 0){
			convertView.setBackgroundResource(R.drawable.table_content_bg);
		}else{
			convertView.setBackgroundDrawable(null);
		}
		
		if(getItem(position).getFree().equals("")){
			mViewHolder.imgFree .setVisibility(View.INVISIBLE);
		}else{
			mViewHolder.imgFree .setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}

	static class ViewHolder {
			private TextView title;
			private TextView date;
			private TextView time;
			private ImageView imgFree;
	}

}
