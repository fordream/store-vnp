package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.icts.app.ItelApplication;
import com.icts.database.CheckUser;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.json.JsonAnalysis;

public class FriendInviteMail {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Button mBtnSendMail;
	private Button mBtnBack;
	private EditText mEditTextMail;
	private MainActivity mainActivity;
	JsonAnalysis mJsonAlalysis;// = new JsonAnalysis(mContext);
	private FriendInviteList mFriendInviteList;
	private Context mContext;
	private Context viewContext;

	public FriendInviteMail(Context context, MainActivity activity,
			FriendInviteList friendInviteList) {
		mainActivity = activity;
		mFriendInviteList = friendInviteList;
		mContext = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.friend_ivitemail, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		mBtnSendMail = (Button) mView.findViewById(R.id.friend_btnSendMail);
		mEditTextMail = (EditText) mView
				.findViewById(R.id.friend_EdittextInvitemail);
		mBtnBack = (Button) mView.findViewById(R.id.friendInvite_btn_back);
	}

	public void initData() {
		// mCheckUser = new CheckUser(mContext);
		// mJsonAlalysis = new JsonAnalysis(mContext);
		// String url = mainActivity.getString(R.string.url_contactNoFriend);
		// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		// nameValuePairs.add(new BasicNameValuePair("user_id", "11"));// isp
		// // authen_code
		// nameValuePairs.add(new BasicNameValuePair("uuid",
		// "ccf4766507a1b47bbc321fa995687d6a"));
		// nameValuePairs.add(new BasicNameValuePair("page", Integer
		// .toString(mPage)));
		// mJsonAlalysis.executeLoadData(url, handlerLoadData, mContext,
		// nameValuePairs);

	}

	public void setEvent() {

		mBtnSendMail.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mJsonAlalysis = new JsonAnalysis(mContext);
				String url = mainActivity.getString(R.string.url_inviteMail);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("user_id", Integer
						.toString(ItelApplication.user_id)));// isp
				// authen_code
				nameValuePairs.add(new BasicNameValuePair("uuid",
						ItelApplication.uuid));
				nameValuePairs.add(new BasicNameValuePair("name", ""));
				nameValuePairs.add(new BasicNameValuePair("email",
						mEditTextMail.getText().toString()));
				mJsonAlalysis.executeLoadData(url, handlerLoadData, mContext,
						nameValuePairs);
				viewContext = v.getContext();

			}
		});

		mBtnBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				FriendInviteList friendView = new FriendInviteList(v
						.getContext(), mainActivity, null);
				mainActivity.changeViewLL(friendView.getmView());

			}
		});
	}

	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = mView;
	}

	/*
	 * 
	 * Handler doi load data tu server
	 */

	final Handler handlerLoadData = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {
					try {
						String flag = mJsonAlalysis.getCodeReturn(data);
						if (flag.equals("")) {
							Toast.makeText(mContext, "Send not sucessful",
									Toast.LENGTH_LONG).show();
						} else {
							FriendInviteList friendView = new FriendInviteList(
									viewContext, mainActivity, null);
							mainActivity.changeViewLL(friendView.getmView());

						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// try {
					// mListObjFriend = mJsonAlalysis.getListFriend(data);
					//
					// FriendAdapter friendAdapter = new FriendAdapter(
					// mContext, mListObjFriend);
					//
					// } catch (JSONException e) {
					// // Utils.showDialogServerError(json.mContext);
					// e.printStackTrace();
					// }
				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

}
