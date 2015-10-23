package com.icts.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.text.StaticLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.itel.R;
import com.icts.json.JsonAnalysis;
import com.icts.object.ObjFriendInvite;

public class InviteAdapter extends SwipAdapter<ObjFriendInvite> {
	public class ViewHolder {
		public TextView textView;
		public TextView textViewLeft;
		public TextView textViewRight;
		public ImageView imageView;
		public Button btnAccept;
		public Button btnDeny;
		public LinearLayout view;
		public RelativeLayout rlText;
	}

	private Context mContext;
	public static String idInvite;
	private LayoutInflater mInflater;
	private ArrayList<ObjFriendInvite> mListObjFriend;
	private final Integer[] mListBack = { R.drawable.list_friend_1,
			R.drawable.list_friend_2, R.drawable.list_friend_3,
			R.drawable.list_friend_4, R.drawable.list_friend_5,
			R.drawable.list_friend_6 };
	public final Integer[] imageOutResIds = new Integer[] {
			R.drawable.list_outer_tab_1, R.drawable.list_outer_tab_2,
			R.drawable.list_outer_tab_3, R.drawable.list_outer_tab_4,
			R.drawable.list_outer_tab_5, R.drawable.list_outer_tab_6 };
	public ImageLoader mImageLoader;
	private Handler mHanderAccept;
	private Handler mHanderDeny;

	/*
	 * public FriendAdapter(Context context, ArrayList<FriendObject> list) {
	 * mContext = context; mListObjFriend = list; mInflater =
	 * LayoutInflater.from(context); mImageLoader = new ImageLoader(mContext); }
	 */

	public InviteAdapter(Context context, int resource, int textViewResourceId,
			ArrayList<ObjFriendInvite> list, Handler handler1, Handler handler2) {
		super(context, resource, textViewResourceId, list);
		mContext = context;
		mListObjFriend = list;
		mHanderAccept = handler1;
		mHanderDeny = handler2;
		mInflater = LayoutInflater.from(context);
		mImageLoader = new ImageLoader(mContext);
	}

	@Override
	public int getCount() {
		return mListObjFriend.size();
	}

	@Override
	public ObjFriendInvite getItem(int position) {
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
		Log.e("", "luongdttttttttttttttt");
		if (vi == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_friend_invite, null);
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
			holder.btnAccept = (Button) convertView
					.findViewById(R.id.item_friendSearch_btnAccept);
			holder.btnDeny = (Button) convertView
					.findViewById(R.id.item_friendSearch_btnDeny);
			holder.rlText = (RelativeLayout) convertView
					.findViewById(R.id.friend_list_item_text);
			convertView.setTag(holder);
			vi = convertView;
			handleEvent(vi);
		} else {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_friend_invite, null);
			vi = convertView;
			/*
			 * convertView = LayoutInflater.from(mContext).inflate(
			 * R.layout.item_friend_search, null);
			 */
			holder = (ViewHolder) vi.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.imageView = (ImageView) vi
						.findViewById(R.id.item_friendSearch_imageView);
				holder.textView = (TextView) vi
						.findViewById(R.id.item_friendSearch_txtView);
				holder.btnAccept = (Button) convertView
						.findViewById(R.id.item_friendSearch_btnAccept);
				holder.btnDeny = (Button) convertView
						.findViewById(R.id.item_friendSearch_btnDeny);
				holder.rlText = (RelativeLayout) vi
						.findViewById(R.id.friend_list_item_text);
				holder.view = (LinearLayout) vi.findViewById(R.id.llFriend);

				holder.textViewLeft = (TextView) vi
						.findViewById(R.id.friend_list_item_text_left);
				// holder.textViewRight = (TextView) vi
				// .findViewById(R.id.friend_list_item_text_right);
				vi.setTag(holder);

			}
			handleEvent(vi);
		}

		ObjFriendInvite item = mListObjFriend.get(position);
		if (vi != null) {
			holder.view.setBackgroundDrawable(mContext.getResources()
					.getDrawable(mListBack[position % 6]));
			holder.rlText.setBackgroundDrawable(mContext.getResources()
					.getDrawable(imageOutResIds[position % 6]));
		}
		// if (i < 5) {
		// ++i;
		// } else {
		// i = i - 5;
		// }
		holder.textViewLeft.setText("PROFILE");
		holder.textView.setText(item.getmFriendNick());

		// imageView.setImageBitmap(item.getmAvatar());
		// Log.e("", "link avatar === " + item.getmFriendAvatar());
		mImageLoader.DisplayImage(item.getmFriendAvatar(), holder.imageView);
		holder.btnAccept.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JsonAnalysis mJsonAlalysis = new JsonAnalysis(v.getContext());
				String url = "http://49.212.140.145/itell/friend/accept_invite";
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("user_id", Integer
						.toString(ItelApplication.user_id)));// isp
				// authen_code
				nameValuePairs.add(new BasicNameValuePair("uuid",
						ItelApplication.uuid));
				nameValuePairs.add(new BasicNameValuePair("invite_id",
						mListObjFriend.get(position).getmInviteId()));
				mJsonAlalysis.executeLoadData(url, mHanderAccept, mContext,
						nameValuePairs);

			}
		});
		holder.btnDeny.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				JsonAnalysis mJsonAlalysis = new JsonAnalysis(v.getContext());
				String url = "http://49.212.140.145/itell/friend/deny_invite";
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("user_id", Integer
						.toString(ItelApplication.user_id)));// isp
				// authen_code
				nameValuePairs.add(new BasicNameValuePair("uuid",
						ItelApplication.uuid));
				nameValuePairs.add(new BasicNameValuePair("invite_id",
						mListObjFriend.get(position).getmInviteId()));
				idInvite = mListObjFriend.get(position).getmInviteId();
				mJsonAlalysis.executeLoadData(url, mHanderDeny, mContext,
						nameValuePairs);

			}
		});

		return vi;
	}
}
