package com.icts.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icts.itel.R;
import com.icts.object.FriendObject;

public class FriendSearchAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<FriendObject> mListObjFriend;
	private Integer[] mListBack = { R.drawable.list_friend_1,
			R.drawable.list_friend_2, R.drawable.list_friend_3,
			R.drawable.list_friend_4, R.drawable.list_friend_5,
			R.drawable.list_friend_6 };
	public ImageLoader mImageLoader;

	public FriendSearchAdapter(Context context, ArrayList<FriendObject> list) {
		mContext = context;
		mListObjFriend = list;
		mInflater = LayoutInflater.from(context);
		mImageLoader = new ImageLoader(mContext);
	}

	public int getCount() {
		return mListObjFriend.size();
	}

	public Object getItem(int position) {
		return mListObjFriend.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	int i = 0;

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		FriendObject item = mListObjFriend.get(position);
		if (convertView == null) {
			vi = mInflater.inflate(R.layout.item_friend_search, null);
			vi.setBackgroundResource(mListBack[i]);
			if (i < 5)
				++i;
			else {
				i = i - 5;
			}
			ImageView imageView = (ImageView) vi
					.findViewById(R.id.item_friendSearch_imageView);
			TextView textView = (TextView) vi
					.findViewById(R.id.item_friendSearch_txtView);
			textView.setText(item.getFriendNick());
			ImageView iView = (ImageView) vi
					.findViewById(R.id.item_friendSearch_txtLock);
			if (item.isRestrict_pub())
				iView.setVisibility(View.VISIBLE);
			// imageView.setImageBitmap(item.getmAvatar());
			Log.e("", "link avatar === " + item.getFriendAva());
			mImageLoader.DisplayImage(item.getFriendAva(), imageView);

		}

		return vi;
	}
}
