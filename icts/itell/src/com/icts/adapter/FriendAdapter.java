package com.icts.adapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.icts.control.SwipAdapter;
import com.icts.itel.R;
import com.icts.object.FriendObject;
import com.icts.utils.Constant;
import com.icts.utils.DownloadImageTask;

public class FriendAdapter extends SwipAdapter<FriendObject> {
	public class ViewHolder {
		public TextView textView;
		public TextView textViewLeft;
		public TextView textViewRight;
		public ImageView imageView;
		public ImageView iView;
		public LinearLayout view;
		public RelativeLayout rlText;
	}

	private Context mContext;
	private LayoutInflater mInflater;
	private ExecutorService executor;
	private ArrayList<FriendObject> mListObjFriend;
	private final Integer[] mListBack = { R.drawable.list_friend_1,
			R.drawable.list_friend_2, R.drawable.list_friend_3,
			R.drawable.list_friend_4, R.drawable.list_friend_5,
			R.drawable.list_friend_6 };
	public final Integer[] imageOutResIds = new Integer[] {
			R.drawable.list_outer_tab_1, R.drawable.list_outer_tab_2,
			R.drawable.list_outer_tab_3, R.drawable.list_outer_tab_4,
			R.drawable.list_outer_tab_5, R.drawable.list_outer_tab_6 };
	public ImageLoader mImageLoader;

	/*
	 * public FriendAdapter(Context context, ArrayList<FriendObject> list) {
	 * mContext = context; mListObjFriend = list; mInflater =
	 * LayoutInflater.from(context); mImageLoader = new ImageLoader(mContext); }
	 */

	public FriendAdapter(Context context, int resource, int textViewResourceId,
			ArrayList<FriendObject> list) {
		super(context, resource, textViewResourceId, list);
		mContext = context;
		mListObjFriend = list;
		mInflater = LayoutInflater.from(context);
		mImageLoader = new ImageLoader(mContext);
		executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
	}

	 @Override
	public int getCount() {
		return mListObjFriend.size();
	}

	 @Override
	public FriendObject getItem(int position) {
		return mListObjFriend.get(position);
	}

	 @Override
	public long getItemId(int position) {
		return position;
	}

	int i = 0;

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = super.getView(position, convertView, parent);
		ViewHolder holder;
		if (vi == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_friend_search, null);
			holder = new ViewHolder();

			holder.imageView = (ImageView) convertView
					.findViewById(R.id.item_friendSearch_imageView);
			holder.textView = (TextView) convertView
					.findViewById(R.id.item_friendSearch_txtView);
			holder.view = (LinearLayout) convertView
					.findViewById(R.id.llFriend);
			holder.textViewLeft = (TextView) convertView
			.findViewById(R.id.friend_list_item_text_left);
			// holder.textViewRight = (TextView) convertView
			// .findViewById(R.id.friend_list_item_text_right);
			holder.iView = (ImageView) convertView
					.findViewById(R.id.item_friendSearch_txtLock);
			holder.rlText = (RelativeLayout) convertView
					.findViewById(R.id.friend_list_item_text);
			convertView.setTag(holder);
			vi = convertView;
			handleEvent(vi);
		} else {
			/*convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_friend_search, null);*/
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_friend_search, null);
			vi = convertView;
			holder = (ViewHolder) vi.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.imageView = (ImageView) vi
						.findViewById(R.id.item_friendSearch_imageView);
				holder.textView = (TextView) vi
						.findViewById(R.id.item_friendSearch_txtView);
				holder.iView = (ImageView) vi
						.findViewById(R.id.item_friendSearch_txtLock);
				holder.rlText = (RelativeLayout) vi
						.findViewById(R.id.friend_list_item_text);
				holder.view = (LinearLayout) vi
						.findViewById(R.id.llFriend);

				holder.textViewLeft = (TextView) vi
				.findViewById(R.id.friend_list_item_text_left);
				// holder.textViewRight = (TextView) vi
				// .findViewById(R.id.friend_list_item_text_right);
				vi.setTag(holder);
				
			}
			handleEvent(vi);
		}

		FriendObject item = mListObjFriend.get(position);
		if (vi != null) {
			holder.view.setBackgroundDrawable(mContext.getResources().getDrawable(mListBack[position%6]));
			holder.rlText.setBackgroundDrawable(mContext.getResources().getDrawable(imageOutResIds[position%6]));
		}
//		if (i < 5) {
//			++i;
//		} else {
//			i = i - 5;
//		}
		holder.textViewLeft.setText("PROFILE");
		holder.textView.setText(item.getFriendNick());

		if (item.isRestrict_pub())
			holder.iView.setVisibility(View.VISIBLE);
		// imageView.setImageBitmap(item.getmAvatar());
		Log.e("", "link avatar === " + item.getFriendAva());
		//mImageLoader.DisplayImage(item.getFriendAva(), holder.imageView);
		DownloadImageTask task = new DownloadImageTask(holder.imageView, 40, 40, 
				mContext, item.getFriendAva(),item.isMale());
		executor.submit(task);

		return vi;
	}
}
