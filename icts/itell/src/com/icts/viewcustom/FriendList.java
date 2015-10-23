package com.icts.viewcustom;

import java.util.ArrayList;

import org.json.JSONException;
import com.icts.itel.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.icts.adapter.FriendAdapter;
import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.control.SwipListAdapter;
import com.icts.control.SwipListView;
import com.icts.control.SwipAdapter.OnItemSlideChangedListener;
import com.icts.control.SwipAdapter.OnItemSlideLeftListener;
import com.icts.control.SwipAdapter.OnItemSlideRightListener;
import com.icts.control.SwipListView.OnSetFinishSwip;
import com.icts.database.CheckUser;
import com.icts.itel.MainActivity;
import com.icts.json.JsonAnalysis;
import com.icts.object.FriendObject;

public class FriendList {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private TextView mTxtCoin;
	private Button mBtnBack;
	private SwipListView mListView;
	private ArrayList<FriendObject> mListObjFriend = new ArrayList<FriendObject>();
	private MainActivity mainActivity;
	JsonAnalysis mJsonAlalysis;// = new JsonAnalysis(mContext);
	private CheckUser mCheckUser;
	private Friend mFriend;

	public FriendList(Context context, MainActivity activity, Friend friend) {
		mContext = context;
		mFriend = friend;
		mainActivity = activity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.friend_list, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		mListView = (SwipListView) mView.findViewById(R.id.friendList_listView);
		mBtnBack = (Button) mView.findViewById(R.id.friendlist_btn_back);
	}

	public void initData() {
		mCheckUser = new CheckUser(mContext);
		mJsonAlalysis = new JsonAnalysis(mContext);

		String url = String.format(
				mainActivity.getString(R.string.url_listfriend),
				mCheckUser.getUserId())
				+ mCheckUser.getUuid();

		url = String.format(mainActivity.getString(R.string.url_listfriend),
				ItelApplication.user_id) + ItelApplication.uuid;
		mJsonAlalysis.executeLoadData(url, handlerLoadData, mContext, null);

	}

	public void setEvent() {
		// mListView.setOnItemClickListener(new OnItemClickListener() {
		//
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// Profile profileView = new Profile(arg1.getContext(),
		// mainActivity);
		// mainActivity.changeViewLL(profileView.getView());
		//
		// }
		// });

		mBtnBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Friend friendView = new Friend(v.getContext(), mainActivity);
				mainActivity.changeViewLL(mFriend.getmView());

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
						mListObjFriend = mJsonAlalysis.getListFriend(data);

						FriendAdapter friendAdapter = new FriendAdapter(
								mContext, R.layout.item_friend_search,
								R.id.friend_list_item_text_left, mListObjFriend);
						friendAdapter.setRightMargin(0);
						// Disable left
						friendAdapter
								.setOnItemSlideLeftListener(new OnItemSlideLeftListener() {
									public void onItemSlideLeft(View v) {

									}

								});

						friendAdapter
								.setOnItemSlideRightListener(new OnItemSlideRightListener() {
									public void onItemSlideRight(View v) {

									}
								});

						friendAdapter
								.setOnItemSlideChangedListener(new OnItemSlideChangedListener() {
									public void onItemSlideChanged(
											final View v, int direction) {
										// SwipListAdapter adapter =
										// (SwipListAdapter) mListView
										// .getAdapter();
										FriendAdapter adapter = (FriendAdapter) mListView
												.getAdapter();
										adapter.setBusy(true);
										if (direction == SwipAdapter.LEFT) {
											mListView.swip(mListView
													.getPositionForView(v),
													direction);
										} else if (direction == SwipAdapter.RIGHT) {
											handleSlideFinish(
													v,
													direction,
													mListView
															.getPositionForView(v));
										}
									}
								});
						mListView.setAdapter(friendAdapter);
						mListView.setClipChildren(false);
						mListView.setClipToPadding(false);
						mListView.setDividerHeight(0);
						mListView.setFinishSwip(new OnSetFinishSwip() {

							@Override
							public void onFinishSwip(int direction, int index) {
								FriendAdapter adapter = (FriendAdapter) mListView
										.getAdapter();
								if (direction == SwipAdapter.RIGHT) {
									handleSwipFinish(index, direction, adapter);
								}

							}
						});

					} catch (JSONException e) {
						// Utils.showDialogServerError(json.mContext);
						e.printStackTrace();
					}
				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

	/**
	 * when finished slide
	 * 
	 * @param v
	 * @param direction
	 */
	private void handleSlideFinish(View v, int direction, int index) {
		Log.e("", "vaoaaaaaaaaaaaa");
		// Profile profileView = new Profile(mContext, mainActivity);
		// mainActivity.changeViewLL(profileView.getView());
		if (mListObjFriend.get(index).isRestrict_pub() == false) {
			ProfileOther profileOther = new ProfileOther(mContext,
					mainActivity, mListObjFriend.get(index));
			mainActivity.changeViewLL(profileOther.getmView());
		}
	}

	/**
	 * 
	 * @param index
	 *            index in array list
	 * @param direction
	 */
	private void handleSwipFinish(int index, int direction,
			FriendAdapter adapter) {
		Log.e("", "vaoaaaaaaaaaaaa111");
		// Profile profileView = new Profile(mContext, mainActivity);
		// mainActivity.changeViewLL(profileView.getView());
		ProfileOther profileOther = new ProfileOther(mContext, mainActivity,
				mListObjFriend.get(index));
		mainActivity.changeViewLL(profileOther.getmView());
	}

}
