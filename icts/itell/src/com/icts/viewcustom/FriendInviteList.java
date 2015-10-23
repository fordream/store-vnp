package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import com.icts.itel.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icts.adapter.FriendInviteAdapter;
import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.database.CheckUser;
import com.icts.itel.MainActivity;
import com.icts.json.JsonAnalysis;
import com.icts.object.FriendObject;
import com.icts.utils.Constant;

public class FriendInviteList {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private TextView mTxtCoin;
	private Button mBtnBack;
	private Button mBtnInviteMail;
	private ListView mListView;
	private ArrayList<FriendObject> mListObjFriend = new ArrayList<FriendObject>();
	private MainActivity mainActivity;
	JsonAnalysis mJsonAlalysis;// = new JsonAnalysis(mContext);
	private CheckUser mCheckUser;
	private int mPage = 1;
	private Friend mFriend;
	private RelativeLayout mRlL1;
	private final int TIME_SLIDE_ANIMATION = 500;
	private final int TIME_UP_DOWN_ANIMATION = 1000;
	FriendInviteAdapter friendAdapter;

	public FriendInviteList(Context context, MainActivity activity,
			Friend friend) {
		mContext = context;
		mainActivity = activity;
		mFriend = friend;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.friend_listivite, null);

		initView();
		initData();
		setEvent();
	}

	TextView mTvLoadMore2;

	public void initView() {
		mListView = (ListView) mView.findViewById(R.id.friendList_listView);
		mBtnBack = (Button) mView.findViewById(R.id.friendlist_btn_back);
		mRlL1 = (RelativeLayout) mView
				.findViewById(R.id.relativeLayoutInviteMail);
		mRlL1.setFocusable(true);
		mRlL1.setFocusableInTouchMode(true);
		handleEvent(mRlL1);
		mTvLoadMore2 = new TextView(mContext);
		mTvLoadMore2.setText("Load More");
		mTvLoadMore2.setGravity(Gravity.CENTER);
		mTvLoadMore2.setHeight(90);
		mTvLoadMore2.setVisibility(View.INVISIBLE);
		mListView.addFooterView(mTvLoadMore2);
		// mBtnInviteMail = (Button) mView
		// .findViewById(R.id.friendInvited_BtnMail);
	}

	public void load(Handler handler) {
		String url = mainActivity.getString(R.string.url_contactNoFriend);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("user_id",
				ItelApplication.user.getUserId()));// isp
		// authen_code
		nameValuePairs.add(new BasicNameValuePair("uuid", ItelApplication.user
				.getUuid()));
		nameValuePairs.add(new BasicNameValuePair("page", Integer
				.toString(mPage)));
		mJsonAlalysis.executeLoadData(url, handler, mContext,
				nameValuePairs);
	}

	public void initData() {
		mCheckUser = new CheckUser(mContext);
		mJsonAlalysis = new JsonAnalysis(mContext);
		load(handlerLoadData);

	}

	public void setEvent() {
		mTvLoadMore2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				load(handlerLoadDataMore);

			}
		});

		mBtnBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Friend friendView = new Friend(v.getContext(), mainActivity);
				mainActivity.changeViewLL(friendView.getmView());

			}
		});
		// mBtnInviteMail.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// FriendInviteMail friendView = new FriendInviteMail(v
		// .getContext(), mainActivity, FriendInviteList.this);
		// mainActivity.changeViewLL(friendView.getmView());
		//
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
						mPage++;
						mTvLoadMore2.setVisibility(View.VISIBLE);
						mListObjFriend = mJsonAlalysis.getListIsFriendContact(
								data, mListObjFriend);

						friendAdapter = new FriendInviteAdapter(mContext,
								mListObjFriend, handlerSendmail);
						mListView.setAdapter(friendAdapter);

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

	final Handler handlerLoadDataMore = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {

					try {
						mPage++;
						if (mListObjFriend.size() == 0)
							mTvLoadMore2.setVisibility(View.INVISIBLE);
						else
							mTvLoadMore2.setVisibility(View.VISIBLE);
						mListObjFriend = mJsonAlalysis.getListIsFriendContact(
								data, mListObjFriend);

						// friendAdapter = new FriendInviteAdapter(mContext,
						// mListObjFriend, handlerSendmail);
						// mListView.setAdapter(friendAdapter);
						friendAdapter.notifyDataSetChanged();

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
										Log.e("", "finishhhhhhhhhh");
										// FriendInviteList friendListInvite =
										// new FriendInviteList(
										// mContext, mainActivity,
										// Friend.this);
										FriendInviteMail friendView = new FriendInviteMail(
												mContext, mainActivity,
												FriendInviteList.this);
										mainActivity.changeViewLL(friendView
												.getmView());
										// slideAnimation(v);
										// upDownAnimation(v);
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

	/*
	 * 
	 * Handler doi load data tu server
	 */

	final Handler handlerSendmail = new Handler() {
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

							for (int i = 0; i < mListObjFriend.size(); ++i) {
								if (mListObjFriend.get(i).getmMail()
										.equals(flag)) {
									mListObjFriend.remove(i);
									break;
								}

							}

							friendAdapter.notifyDataSetChanged();
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.e("", "dopneeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
					//
					// try {
					//
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
