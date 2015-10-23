package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.icts.adapter.FriendAdapter;
import com.icts.adapter.InviteAdapter;
import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.control.SwipAdapter.OnItemSlideChangedListener;
import com.icts.control.SwipAdapter.OnItemSlideRightListener;
import com.icts.control.SwipListView;
import com.icts.control.SwipListView.OnSetFinishSwip;
import com.icts.database.CheckUser;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.json.JsonAnalysis;
import com.icts.object.FriendObject;
import com.icts.object.ObjContact;
import com.icts.object.ObjFriendInvite;
import com.icts.utils.Constant;
import com.icts.utils.Utils;

public class Friend {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private TextView mTxtCoin;
	private Button mBtnFriend;
	private Button mBtnSearch;
	private Button mBtnInvite;
	private EditText mEditText;
	private SwipListView mListViewOfInvited;
	private SwipListView mListViewFriendContac;
	private LinearLayout mLLOfInvited;
	private LinearLayout mLLFriendContact;
	private LinearLayout mLLTxt1;
	private LinearLayout mLLTxt2;
	private RelativeLayout mRlL1;
	private ArrayList<FriendObject> mListObjFriend = new ArrayList<FriendObject>();
	private ArrayList<FriendObject> mListObjFriendContact = new ArrayList<FriendObject>();
	private ArrayList<ObjFriendInvite> mListObjFriendInvite = new ArrayList<ObjFriendInvite>();
	private MainActivity mainActivity;
	JsonAnalysis mJsonAlalysis;// = new JsonAnalysis(mContext);
	private CheckUser mCheckUser;
	private Context mContextBtnSearch;
	private TextView mTvLoadMore;
	private TextView mTvLoadMore2;
	private int mPage = 1;
	private int mPageFriendContact = 1;
	private int mPageFriendInvite = 1;
	private FriendAdapter mFriendAdapter;
	private InviteAdapter mInviteAdapter;
	private ArrayList<ObjContact> mListContact = new ArrayList<ObjContact>();
	private final int TIME_SLIDE_ANIMATION = 500;
	private final int TIME_UP_DOWN_ANIMATION = 1000;
	private boolean mModePage = true;

	public Friend(Context context, MainActivity activity) {
		mContext = context;
		mainActivity = activity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.friend_search, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		mListViewOfInvited = (SwipListView) mView
				.findViewById(R.id.friendSearch_listView1);
		mListViewFriendContac = (SwipListView) mView
				.findViewById(R.id.friendSearch_listView2);
		mRlL1 = (RelativeLayout) mView.findViewById(R.id.relativeLayoutInvite);
		mBtnFriend = (Button) mView.findViewById(R.id.friendSearch_btnFriend);
		mBtnSearch = (Button) mView.findViewById(R.id.friendSearch_btnSearch);
		mEditText = (EditText) mView.findViewById(R.id.friendSearch_editText);
		mLLTxt1 = (LinearLayout) mView.findViewById(R.id.friendSearch_llTxt1);
		mLLTxt2 = (LinearLayout) mView.findViewById(R.id.friendSearch_llTxt2);
		// mBtnInvite = (Button)
		// mView.findViewById(R.id.friendSearch_btnInvite);
		mLLOfInvited = (LinearLayout) mView.findViewById(R.id.friendSearch_ll1);
		// mTvLoadMore = new TextView(mContext);
		// mTvLoadMore.setText("Load More");
		// mTvLoadMore.setGravity(Gravity.CENTER);
		// mTvLoadMore.setHeight(90);
		// mListViewOfInvited.addFooterView(mTvLoadMore);
		mTvLoadMore2 = new TextView(mContext);
		mTvLoadMore2.setText("Load More");
		mTvLoadMore2.setGravity(Gravity.CENTER);
		mTvLoadMore2.setHeight(90);
		mTvLoadMore2.setVisibility(View.INVISIBLE);
		mListViewFriendContac.addFooterView(mTvLoadMore2);

		// //
		mRlL1.setFocusable(true);
		mRlL1.setFocusableInTouchMode(true);
		handleEvent(mRlL1);

		mEditText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mEditText.setHint("");

					break;

				default:
					break;
				}
				return false;
			}
		});

	}

	public void getContact(List<ObjContact> list) {

		// ContentResolver cr = mainActivity.getContentResolver();
		// Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
		// null, null, null, null);
		// if (cur.getCount() > 0) {
		// while (cur.moveToNext()) {
		// String id = cur.getString(
		// cur.getColumnIndex(ContactsContract.Contacts._ID));
		// String name = cur.getString(
		// cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		// // if
		// (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))
		// > 0) {
		// //Query phone here. Covered next
		// // }
		//
		// if (Integer.parseInt(cur.getString(
		// cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
		// {
		// Cursor pCur = cr.query(
		// ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
		// null,
		// ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
		// new String[]{id}, null);
		// while (pCur.moveToNext()) {
		// // Do something with phones
		// }
		// pCur.close();
		// }
		//
		// }
		// }

		// //////////////////////////////////////////

		// //
		Cursor phones = mainActivity.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		mainActivity.startManagingCursor(phones);
		if (phones != null) {
			int count = 0;
			while (phones.moveToNext()) {
				count++;
				String name = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String phoneNumber = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				String numberId = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

				ObjContact objContact = new ObjContact();
				objContact.setmNumber(phoneNumber);
				objContact.setmName(name);
				objContact.setmID(numberId);
				// objContact.setmMail(mail);
				list.add(objContact);
			}
		}
		phones.close();

		// ///////////////
		Cursor mail = mainActivity.getContentResolver().query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null,
				null, null);
		mainActivity.startManagingCursor(mail);
		if (mail != null) {
			int count1 = 0;
			while (mail.moveToNext()) {
				count1++;
				String mailUser = mail
						.getString(mail
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				String numberId1 = mail
						.getString(mail
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));
				int size = list.size();
				for (int i = 0; i < size; ++i) {
					if (list.get(i).getmID().equals(numberId1)) {
						list.get(i).setmMail(mailUser);
					}
				}

			}
		}
		mail.close();
	}

	public void initData() {

		mCheckUser = new CheckUser(mContext);
		if (mCheckUser.getContact()) {
			showDialog();

		} else {
			loadData(handlerLoadFriendCantact);
		}

	}

	public void loadData(Handler handler) {
		mJsonAlalysis = new JsonAnalysis(mContext);

		String url = mainActivity.getString(R.string.url_contactFriend);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("user_id",
				ItelApplication.user.getUserId()));// isp
		// authen_code
		nameValuePairs.add(new BasicNameValuePair("uuid", ItelApplication.user
				.getUuid()));
		// nameValuePairs.add(new BasicNameValuePair("keyword", "mau"));
		nameValuePairs.add(new BasicNameValuePair("page", Integer
				.toString(mPageFriendContact)));

		// ////
		// String urlInvite = mainActivity.getString(R.string.url_listInvite);
		// List<NameValuePair> nameValuePairs2 = new
		// ArrayList<NameValuePair>(2);
		// nameValuePairs2.add(new BasicNameValuePair("user_id", "14"));// isp
		// // authen_code
		// nameValuePairs2.add(new BasicNameValuePair("uuid",
		// "ccf4766507a1b47bbc321fa995687d6a"));
		// nameValuePairs.add(new BasicNameValuePair("keyword", "mau"));
		// nameValuePairs2.add(new BasicNameValuePair("page", Integer
		// .toString(mPageFriendContact)));
		mJsonAlalysis.executeLoadData(url, handler, mContext, nameValuePairs);

	}

	public void setEvent() {
		mTvLoadMore2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				mContextBtnSearch = arg0.getContext();
				mJsonAlalysis = new JsonAnalysis(mContext);
				if (!mModePage) {

					String url = mainActivity
							.getString(R.string.url_listfriend_search);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);
					nameValuePairs.add(new BasicNameValuePair("user_id",
							ItelApplication.user.getUserId()));// isp
					// authen_code
					nameValuePairs.add(new BasicNameValuePair("uuid",
							ItelApplication.user.getUuid()));
					nameValuePairs.add(new BasicNameValuePair("keyword",
							mEditText.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("page", Integer
							.toString(mPage)));
					mJsonAlalysis.executeLoadData(url, handlerLoadMore,
							mContext, nameValuePairs);
				} else {
					loadData(handlerLoadFriendCantactMore);
				}

			}
		});
		mBtnFriend.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				FriendList friendListView = new FriendList(v.getContext(),
						mainActivity, Friend.this);
				mainActivity.changeViewLL(friendListView.getmView());

			}
		});
		// mListViewOfInvited.setOnItemClickListener(new OnItemClickListener() {
		//
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// Profile profileView = new Profile(arg1.getContext(),
		// mainActivity);
		// mainActivity.changeViewLL(profileView.getView());
		//
		// }
		// });

		mBtnSearch.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				mPage = 0;
				mContextBtnSearch = arg0.getContext();
				mCheckUser = new CheckUser(mContext);
				mJsonAlalysis = new JsonAnalysis(mContext);

				String url = mainActivity
						.getString(R.string.url_listfriend_search);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("user_id",
						ItelApplication.user.getUserId()));// isp
				// authen_code
				nameValuePairs.add(new BasicNameValuePair("uuid",
						ItelApplication.user.getUuid()));
				nameValuePairs.add(new BasicNameValuePair("keyword", mEditText
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("page", Integer
						.toString(mPage)));
				mJsonAlalysis.executeLoadData(url, handlerLoadData, mContext,
						nameValuePairs);
				++mPage;

			}
		});

		// mBtnInvite.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// FriendInviteList friendListInvite = new FriendInviteList(v
		// .getContext(), mainActivity, Friend.this);
		// mainActivity.changeViewLL(friendListInvite.getmView());
		// }
		// });

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
						mListObjFriend = mJsonAlalysis
								.getListFriendSearch(data);
						mFriendAdapter = new FriendAdapter(mContext,
								R.layout.item_friend_search,
								R.id.friend_list_item_text_left, mListObjFriend);
						if (mListObjFriend.size() == 0)
							mTvLoadMore2.setVisibility(View.INVISIBLE);
						else
							mTvLoadMore2.setVisibility(View.VISIBLE);

						// TODO disable left
						mFriendAdapter.setOnItemSlideLeftListener(null);
						mFriendAdapter
								.setOnItemSlideRightListener(new OnItemSlideRightListener() {

									public void onItemSlideRight(View v) {

									}
								});

						mFriendAdapter
								.setOnItemSlideChangedListener(new OnItemSlideChangedListener() {
									public void onItemSlideChanged(
											final View v, int direction) {
										/*
										 * FriendAdapter adapter =
										 * (FriendAdapter) mListViewFriendContac
										 * .getAdapter();
										 */
										mFriendAdapter.setBusy(true);
										if (direction == SwipAdapter.RIGHT) {
											mListViewFriendContac.swip(
													mListViewFriendContac
															.getPositionForView(v),
													direction);
										}
									}
								});
						mListViewFriendContac.setAdapter(mFriendAdapter);
						mListViewFriendContac.setClipChildren(false);
						mListViewFriendContac.setClipToPadding(false);
						mListViewFriendContac.setDividerHeight(0);
						mListViewFriendContac
								.setFinishSwip(new OnSetFinishSwip() {

									@Override
									public void onFinishSwip(int direction,
											int index) {
										if (direction == SwipAdapter.RIGHT) {
											/*
											 * FriendAdapter adapter =
											 * (FriendAdapter)
											 * mListViewFriendContac
											 * .getAdapter();
											 */
											handleSwipFinish(index, direction,
													mFriendAdapter);
										}
									}
								});
						mListViewFriendContac.setAdapter(mFriendAdapter);
						Log.e("", "size===" + mListObjFriend.size());
						if(mListObjFriend.size() > 0)
							mLLTxt2.setVisibility(View.VISIBLE);

					} catch (JSONException e) {
						Utils.showDialogErorr(mContextBtnSearch,
								"NETWORK ERORR");
						e.printStackTrace();
					}
				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

	/**
	 * 
	 * @param index
	 *            index in array list
	 * @param direction
	 */
	private void handleSwipFinish(int index, int direction,
			FriendAdapter adapter) {
		FriendObject friend = adapter.getItem(index);
		if (direction == SwipAdapter.RIGHT) {
			ProfileOther profile = new ProfileOther(mContext, mainActivity,
					friend);
			mainActivity.changeViewLL(profile.getmView());
		}

	}

	private void handleSwipFinishInvite(int index, int direction,
			InviteAdapter adapter) {
		ObjFriendInvite friend = adapter.getItem(index);
		if (direction == SwipAdapter.RIGHT) {
			ProfileOther profile = new ProfileOther(mContext, mainActivity,
					friend);
			mainActivity.changeViewLL(profile.getmView());
		}

	}

	/*
	 * 
	 * Handler doi load data tu server
	 */

	final Handler handlerLoadMore = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {

					try {
						ArrayList<FriendObject> mListObjFriend2 = mJsonAlalysis
								.getListFriendSearch(data);
						for (int i = 0; i < mListObjFriend2.size(); ++i)
							mListObjFriend.add(mListObjFriend2.get(i));
						// FriendAdapter friendAdapter = new FriendAdapter(
						// mContext, mListObjFriend);
						// mListView.setAdapter(friendAdapter);
						mFriendAdapter.notifyDataSetChanged();
						Log.e("", "size===" + mListObjFriend.size());

					} catch (JSONException e) {
						Utils.showDialogErorr(mContextBtnSearch,
								"NETWORK ERORR");
						e.printStackTrace();
					}
				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

	/*
	 * 
	 * Handler get list friend in contact from server
	 */

	final Handler handlerLoadFriendCantact = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {
					String urlInvite = String.format(
							mainActivity.getString(R.string.url_listInvite),
							ItelApplication.user_id) + ItelApplication.uuid;
					;

					mJsonAlalysis.executeLoadData(urlInvite,
							handlerLoadFriendInvite, mContext, null);

					try {
						++mPageFriendContact;
						mListObjFriendContact = mJsonAlalysis
								.getListIsFriendContact(data, mListObjFriend);
						if (mListObjFriendContact.size() == 0)
							mTvLoadMore2.setVisibility(View.INVISIBLE);
						else
							mTvLoadMore2.setVisibility(View.VISIBLE);
						mFriendAdapter = new FriendAdapter(mContext,
								R.layout.item_friend_search,
								R.id.friend_list_item_text_left, mListObjFriend);

						// Disable left
						mFriendAdapter.setOnItemSlideLeftListener(null);

						mFriendAdapter
								.setOnItemSlideRightListener(new OnItemSlideRightListener() {
									public void onItemSlideRight(View v) {

									}
								});

						mFriendAdapter
								.setOnItemSlideChangedListener(new OnItemSlideChangedListener() {
									public void onItemSlideChanged(
											final View v, int direction) {
										FriendAdapter adapter = (FriendAdapter) mListViewFriendContac
												.getAdapter();
										adapter.setBusy(true);
										if (direction == SwipAdapter.RIGHT) {
											mListViewFriendContac.swip(
													mListViewFriendContac
															.getPositionForView(v),
													direction);
										}
									}
								});
						mListViewFriendContac.setClipChildren(false);
						mListViewFriendContac.setClipToPadding(false);
						mListViewFriendContac.setDividerHeight(0);
						mListViewFriendContac
								.setFinishSwip(new OnSetFinishSwip() {

									@Override
									public void onFinishSwip(int direction,
											int index) {
										if (direction == SwipAdapter.RIGHT) {
											FriendAdapter adapter = (FriendAdapter) mListViewFriendContac
													.getAdapter();
											handleSwipFinish(index, direction,
													adapter);
										}

									}
								});
						mListViewFriendContac.setAdapter(mFriendAdapter);
						Log.e("", "size===" + mListObjFriendContact.size());
						if (mListObjFriendContact.size() == 0) {
							mLLTxt2.setVisibility(View.GONE);
						}

					} catch (JSONException e) {
						Utils.showDialogErorr(mContext, "NETWORK ERORR");
						e.printStackTrace();
					}
				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

	final Handler handlerLoadFriendCantactMore = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {

					try {
						mListObjFriendContact = mJsonAlalysis
								.getListIsFriendContact(data, mListObjFriend);
						mFriendAdapter.notifyDataSetChanged();

					} catch (JSONException e) {
						Utils.showDialogErorr(mContext, "NETWORK ERORR");
						e.printStackTrace();
					}
				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

	final Handler handlerPostContact = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {

					mCheckUser.addContact(false);
					loadData(handlerLoadFriendCantact);

				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

	final Handler handlerLoadFriendInvite = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			// mListViewOfInvited.setVisibility(View.GONE);
			// mLLOfInvited.setVisibility(View.GONE);
			if (total >= 0) {
				if (exeption) {

					try {
						mListObjFriendInvite = mJsonAlalysis
								.getListFriendInviteUser(data);
						mInviteAdapter = new InviteAdapter(mContext,
								R.layout.item_friend_search,
								R.id.friend_list_item_text_left,
								mListObjFriendInvite, handlerSendAccept,
								handlerSendDeny);

						// Disable left
						mInviteAdapter.setOnItemSlideLeftListener(null);

						mInviteAdapter
								.setOnItemSlideRightListener(new OnItemSlideRightListener() {
									public void onItemSlideRight(View v) {

									}
								});

						mInviteAdapter
								.setOnItemSlideChangedListener(new OnItemSlideChangedListener() {
									public void onItemSlideChanged(
											final View v, int direction) {
										InviteAdapter adapter = (InviteAdapter) mListViewOfInvited
												.getAdapter();
										adapter.setBusy(true);
										if (direction == SwipAdapter.RIGHT) {
											mListViewOfInvited.swip(
													mListViewOfInvited
															.getPositionForView(v),
													direction);
										}
									}
								});
						mListViewOfInvited.setClipChildren(false);
						mListViewOfInvited.setClipToPadding(false);
						mListViewOfInvited.setDividerHeight(0);
						mListViewOfInvited.setFinishSwip(new OnSetFinishSwip() {

							@Override
							public void onFinishSwip(int direction, int index) {
								if (direction == SwipAdapter.RIGHT) {
									InviteAdapter adapter = (InviteAdapter) mListViewOfInvited
											.getAdapter();
									handleSwipFinishInvite(index, direction,
											adapter);
								}

							}
						});
						mListViewOfInvited.setAdapter(mInviteAdapter);
						Log.e("", "size===" + mListObjFriendInvite.size());
						if (mListObjFriendInvite.size() == 0) {
							mListViewOfInvited.setVisibility(View.GONE);
							mLLOfInvited.setVisibility(View.GONE);
							mLLTxt1.setVisibility(View.GONE);
						}

					} catch (JSONException e) {
						// <<<<<<< .mine
						// mListViewOfInvited.setVisibility(View.GONE);
						// mLLOfInvited.setVisibility(View.GONE);
						// // Utils.showDialogErorr(mContext, "NETWORK ERORR");
						// =======
						if (mListObjFriendInvite.size() == 0) {
							mListViewOfInvited.setVisibility(View.GONE);
							mLLOfInvited.setVisibility(View.GONE);
							mLLTxt1.setVisibility(View.GONE);
						}
						// Utils.showDialogErorr(mContext, "NETWORK ERORR");
						// >>>>>>> .r815
						e.printStackTrace();
					}
				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

	// //////////
	/*
	 * 
	 * Handler doi load data tu server
	 */

	final Handler handlerSendAccept = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {
					try {
						String flag = mJsonAlalysis.getCodeReturnInvite(data);
						if (flag.equals("")) {
							Toast.makeText(mContext, "Send not sucessful",
									Toast.LENGTH_LONG).show();
						} else {

							for (int i = 0; i < mListObjFriendInvite.size(); ++i) {
								if (mListObjFriendInvite.get(i).getmFriendID()
										.equals(flag)) {
									mListObjFriendInvite.remove(i);
									break;
								}

							}
							mInviteAdapter.notifyDataSetChanged();

						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

	final Handler handlerSendDeny = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {
					try {
						String flag = mJsonAlalysis.getCodeReturnInvite(data);
						if (flag.equals("")) {
							Toast.makeText(mContext, "Send not sucessful",
									Toast.LENGTH_LONG).show();
						} else {

							for (int i = 0; i < mListObjFriendInvite.size(); ++i) {
								if (mListObjFriendInvite.get(i).getmFriendID()
										.equals(InviteAdapter.idInvite)) {
									mListObjFriendInvite.remove(i);
									break;
								}

							}
							mInviteAdapter.notifyDataSetChanged();

						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

	// /////////////////////////////
	public void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Are you sure you want sysn contact?")
				.setCancelable(false)
				.setTitle("EXIT")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								getContact(mListContact);
								int count = mListContact.size();
								mJsonAlalysis = new JsonAnalysis(mContext);
								String url = mainActivity
										.getString(R.string.url_postContact);
								List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
										2);
								nameValuePairs.add(new BasicNameValuePair(
										"user_id", ItelApplication.user
												.getUserId()));// isp
								// authen_code
								nameValuePairs.add(new BasicNameValuePair(
										"uuid", ItelApplication.user.getUuid()));
								JSONArray listName = new JSONArray();
								JSONArray listPhone = new JSONArray();
								JSONArray listMail = new JSONArray();

								for (int i = 0; i < count; ++i) {
									listName.put(mListContact.get(i).getmName());
									listPhone.put(mListContact.get(i)
											.getmNumber());
									listMail.put(mListContact.get(i).getmMail());

								}
								// name = URLEncoder.encode(userName, "UTF-8");

								// try {
								// listName.add(URLEncoder.encode("aaaaaaaa",
								// "UTF-8"));
								// listPhone.add(URLEncoder.encode(
								// "34324324234", "UTF-8"));
								// listMail.add(URLEncoder.encode(
								// "adnajd@gmail.com", "UTF-8"));
								// } catch (UnsupportedEncodingException e) {
								// // TODO Auto-generated catch block
								// e.printStackTrace();
								// }

								// Log.e("", "contact ===" + j.toString());
								// Log.e("", "contact222 ===" + j1.toString());

								nameValuePairs.add(new BasicNameValuePair(

								"names", listName.toString()));
								nameValuePairs.add(new BasicNameValuePair(

								"phones", listPhone.toString()));
								nameValuePairs.add(new BasicNameValuePair(
										"emails", listMail.toString()));
								mJsonAlalysis.executeLoadData(url,
										handlerPostContact, mContext,
										nameValuePairs);

							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.setIcon(R.drawable.ic_launcher);
		alert.show();
	}

	// ///
	protected void handleEvent(final View v) {
		v.setOnTouchListener(new OnTouchListener() {
			private float xStart;
			private int direction = 0;
			View itemUp = null;
			private boolean slideFinish = false;

			public boolean onTouch(final View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					direction = 0;
					itemUp = v.findViewWithTag("item_move");
					xStart = event.getX();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					float offsetX = event.getX() - xStart;
					if (offsetX < 0) {
						if (direction == 0) {
							direction = SwipAdapter.LEFT;
							// onItemSlideLeftListener.onItemSlideLeft(v);
						}
					} else {
						direction = 0;
					}
					if (offsetX < 0) {
						RelativeLayout.LayoutParams params = (LayoutParams) itemUp
								.getLayoutParams();
						params.leftMargin = (int) offsetX;
						params.rightMargin = (int) -offsetX;
						itemUp.setLayoutParams(params);
					}
				} else if (event.getAction() == MotionEvent.ACTION_OUTSIDE
						|| event.getAction() == MotionEvent.ACTION_CANCEL
						|| event.getAction() == MotionEvent.ACTION_UP) {
					final RelativeLayout.LayoutParams params = (LayoutParams) itemUp
							.getLayoutParams();
					final int leftMargin = params.leftMargin;
					final int rightMargin = params.rightMargin;
					Animation slideBackAnimation = new Animation() {
						@Override
						protected void applyTransformation(
								float interpolatedTime, Transformation t) {
							if (direction == SwipAdapter.LEFT
									&& Math.abs(leftMargin) > Constant.SCREEN_WIDTH / 3) {
								params.leftMargin = (int) (leftMargin - (Constant.SCREEN_WIDTH + leftMargin)
										* interpolatedTime);
								params.rightMargin = -params.leftMargin;
								this.setDuration(Math.abs(Constant.SCREEN_WIDTH
										+ leftMargin)
										* TIME_SLIDE_ANIMATION
										/ Constant.SCREEN_WIDTH);
								slideFinish = true;
							} else {
								params.leftMargin = (int) (leftMargin * (1 - interpolatedTime));
								params.rightMargin = (int) (rightMargin * (1 - interpolatedTime));
								this.setDuration(Math.abs(leftMargin)
										* TIME_SLIDE_ANIMATION
										/ Constant.SCREEN_WIDTH);
								slideFinish = false;
							}
							itemUp.setLayoutParams(params);
						};
					};
					itemUp.startAnimation(slideBackAnimation);
					slideBackAnimation
							.setAnimationListener(new AnimationListener() {

								public void onAnimationStart(Animation animation) {

								}

								public void onAnimationRepeat(
										Animation animation) {

								}

								public void onAnimationEnd(Animation animation) {
									if (slideFinish) { // &&
														// onItemSlideChangedListener
														// != null){
										itemUp.setVisibility(View.INVISIBLE);
										// onItemSlideChangedListener.onItemSlideChanged(v,
										// direction);
										// slideAnimation(v);
										// upDownAnimation(v);
										FriendInviteList friendListInvite = new FriendInviteList(
												mContext, mainActivity,
												Friend.this);
										mainActivity
												.changeViewLL(friendListInvite
														.getmView());

									} else {
										final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) itemUp
												.getLayoutParams();
										params.leftMargin = 50;
										params.rightMargin = 50;
										itemUp.setLayoutParams(params);
									}
									direction = 0;
								}
							});
				}
				return true;
			}
		});
	}

	private void slideAnimation(View v) {
		Animation mAnimation = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, -1.0f,
				TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE,
				0f);
		ScaleAnimationListener listener = new ScaleAnimationListener(v);
		mAnimation.setDuration(TIME_SLIDE_ANIMATION);
		mAnimation.setFillBefore(true);
		mAnimation.setAnimationListener(listener);
		if (mRlL1.getId() != v.getId()) {
			mRlL1.startAnimation(mAnimation);
		}
		// if (rlPrivate.getId() != v.getId()) {
		// rlPrivate.startAnimation(mAnimation);
		// }
		// if (rlPush.getId() != v.getId()) {
		// rlPush.startAnimation(mAnimation);
		// }
		//
		// if (rlHelp.getId() != v.getId()) {
		// rlHelp.startAnimation(mAnimation);
		// }
		//
		// if (rlAbout.getId() != v.getId()) {
		// rlAbout.startAnimation(mAnimation);
		// }
	}

	private class ScaleAnimationListener implements AnimationListener {

		private View v;

		public ScaleAnimationListener(View v) {
			this.v = v;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (v != null) {
				Log.e("", "endddddddddddddddddd");
				onClickComponent.onClick(v);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationStart(Animation animation) {

		}
	}

	private OnClickListener onClickComponent = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.relativeLayoutInvite:
				FriendInviteList friendListInvite = new FriendInviteList(
						v.getContext(), mainActivity, Friend.this);
				mainActivity.changeViewLL(friendListInvite.getmView());
				break;
			// case R.id.relativeLayout3:
			// PrivateManagement pri = new PrivateManagement(mContext,
			// mainActivity);
			// mainActivity.changeViewLL(pri.getmView());
			// //Utils.animation(pri.getmView(),TIME_SLIDE_ANIMATION);
			// break;
			// case R.id.relativeLayout4:
			// PushSetting push = new PushSetting(mContext, mainActivity);
			// mainActivity.changeViewLL(push.getmView());
			// //Utils.animation(push.getmView(),TIME_SLIDE_ANIMATION);
			// break;
			// case R.id.relativeLayout5:
			// HelpSetting help = new HelpSetting(mContext, mainActivity);
			// mainActivity.changeViewLL(help.getmView());
			// //Utils.animation(push.getmView(),TIME_SLIDE_ANIMATION);
			// break;
			// case R.id.relativeLayout6:
			// AboutSetting about = new AboutSetting(mContext, mainActivity);
			// mainActivity.changeViewLL(about.getmView());
			// //Utils.animation(push.getmView(),TIME_SLIDE_ANIMATION);
			// break;
			default:
				break;
			}
		}
	};

}
