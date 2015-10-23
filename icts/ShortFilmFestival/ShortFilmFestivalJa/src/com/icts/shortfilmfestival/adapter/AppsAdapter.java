package com.icts.shortfilmfestival.adapter;

import java.util.List;

import com.icts.shortfilmfestival.entity.AppsEntity;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.icts.shortfilmfestivalJa.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AppsAdapter extends ArrayAdapter<AppsEntity> {
	private LayoutInflater mInflater;
	private Context mContext;
	
		public AppsAdapter(Context context, List<AppsEntity> objects) {
			super(context, R.layout.app_item,objects);
			mInflater = LayoutInflater.from(context.getApplicationContext());
			mContext = context;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d("AppsAdapter", position + "-------------");
			View view = convertView;
			Wrapper wrapper;
			
			if (view == null) {
				view = mInflater.inflate(R.layout.app_item, null);
				wrapper = new Wrapper(view);
				view.setTag(wrapper);
			} else {
				wrapper = (Wrapper) view.getTag();
			}
			boolean isJp = false;
			if (Resource.localization.equals(ISettings.LANG_JP_FONT))
			{
				isJp = true;
			}
			wrapper.getAppSmartImageView().setBackgroundResource(getItem(position).getmImage());
			wrapper.getNameTextView().setText(getItem(position).getmName());
			FontUtils.setCustomFont(wrapper.getNameTextView(), true, isJp, mContext.getAssets());
			wrapper.getPriceTextView().setText(" - ¥" + getItem(position).getmPrice());
			FontUtils.setCustomFont(wrapper.getPriceTextView(), true, isJp, mContext.getAssets());
			wrapper.getDescTextView().setText(getItem(position).getmDesc());
			FontUtils.setCustomFont(wrapper.getDescTextView(), false, isJp, mContext.getAssets());
			
			return view;
		}

	

	// use an wrapper (or view holder) object to limit calling the
	// findViewById() method, which parses the entire structure of your
	// XML in search for the ID of your view
	private class Wrapper {
		private final View mRoot;
		private SmartImageView mAppSmartImageView;
		private TextView mNameTextView;
		private TextView mDescTextView;
		private TextView mPriceTextView;
		public Wrapper(View root) {
			mRoot = root;
		}

		public SmartImageView getAppSmartImageView() {
			if (mAppSmartImageView == null) {
				mAppSmartImageView = (SmartImageView) mRoot.findViewById(R.id.app_image_id);
			}
			return mAppSmartImageView;
		}
		
		public TextView getNameTextView()
		{
			if (mNameTextView == null)
			{
				mNameTextView = (TextView) mRoot.findViewById(R.id.app_name_id);
			}
			return mNameTextView;
		}
		public TextView getDescTextView()
		{
			if (mDescTextView == null)
			{
				mDescTextView = (TextView) mRoot.findViewById(R.id.app_desc_id);
			}
			return mDescTextView;
		}
		public TextView getPriceTextView()
		{
			if (mPriceTextView == null)
			{
				mPriceTextView = (TextView) mRoot.findViewById(R.id.app_price_id);
			}
			return mPriceTextView;
		}
	}



	
}
