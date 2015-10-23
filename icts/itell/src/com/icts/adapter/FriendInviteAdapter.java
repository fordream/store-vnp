package com.icts.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icts.app.ItelApplication;
import com.icts.itel.R;
import com.icts.json.JsonAnalysis;
import com.icts.object.FriendObject;

public class FriendInviteAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<FriendObject> mListObjFriend;

	public ImageLoader mImageLoader;
	private Handler mhHandlerLoad;

	public FriendInviteAdapter(Context context, ArrayList<FriendObject> list,
			Handler handler) {
		mContext = context;
		mListObjFriend = list;
		mhHandlerLoad = handler;
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

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		final FriendObject item = mListObjFriend.get(position);
		if (convertView == null) {
			vi = mInflater.inflate(R.layout.item_friend_contact, null);
			vi.setBackgroundResource(R.drawable.invite_list_bg);
			ImageView imageView = (ImageView) vi
					.findViewById(R.id.item_friendInvite_ImageV);
			TextView textView = (TextView) vi
					.findViewById(R.id.item_friendInvite_tvName);
			textView.setText(item.getFriendNick());
			TextView textView2 = (TextView) vi
					.findViewById(R.id.item_friendInvite_tvMail);
			textView2.setText(item.getmMail());

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					JsonAnalysis mJsonAlalysis = new JsonAnalysis(v
							.getContext());
					String url = "http://49.212.140.145/itell/friend/send_invite_email";
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);
					nameValuePairs.add(new BasicNameValuePair("user_id",
							Integer.toString(ItelApplication.user_id)));// isp
					// authen_code
					nameValuePairs.add(new BasicNameValuePair("uuid",
							ItelApplication.uuid));
					nameValuePairs.add(new BasicNameValuePair("name", ""));
					nameValuePairs.add(new BasicNameValuePair("email", item
							.getmMail()));
					Log.e("", "userisd==" + ItelApplication.user_id + "mail = " + item.getmMail());
					mJsonAlalysis.executeLoadData(url, mhHandlerLoad, mContext,
							nameValuePairs);

				}
			});

		}

		return vi;
	}

}
