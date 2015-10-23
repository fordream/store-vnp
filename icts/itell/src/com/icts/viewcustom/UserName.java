package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.icts.itel.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.database.CheckUser;
import com.icts.itel.Register;
import com.icts.json.JsonAnalysis;
import com.icts.utils.Constant;
import com.icts.utils.DownloadImage;
import com.icts.utils.Utils;

public class UserName {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private TextView mTxtCoin;
	private EditText mEdtxtNickName;
	private EditText mEdtxFullname;
	private EditText mEdtxYear;
	private EditText mEdtxDate;
	private EditText mEdtxMounth;
	private Register registerAc;
	JsonAnalysis mJsonAlalysis = new JsonAnalysis(mContext);
	private DoneRegister inputNumbe;
	private String mTime;
	private CheckUser mCheckUser;
	private String mAuthe;
	private String mMobile;
	private int isp = 4;
	RelativeLayout rlProfile;
	private ImageView imgGender, imgSlideGender;
	private ImageView imgPublic, imgSlidePublic;
	private final String TAG = "toggle";
	private LinearLayout llGender, llOn;
	private boolean isMale = false;
	private TextView mTvLink;
	private boolean isOn = false;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				// if (birthday != null) {
				// StringTokenizer tokens = new StringTokenizer(birthday, "-");
				// tvYear.setText(tokens.nextToken());
				// tvMonth.setText(tokens.nextToken());
				// tvDate.setText(tokens.nextToken().subSequence(0, 2));
				// }
				// tvName.setText(userObject.getName());
				// tvNickName.setText(userObject.getUserNick());
				// tvPhone.setText(userObject.getPhone());
				isOn = true;

				if (isOn) {
					Drawable d = registerAc.getResources().getDrawable(
							R.drawable.on_button);
					if (imgPublic != null) {
						imgPublic.setImageBitmap(Utils.convert2bitmap(d));
					}
				} else {
					Drawable d = registerAc.getResources().getDrawable(
							R.drawable.off_button);
					if (imgPublic != null) {
						imgPublic.setImageBitmap(Utils.convert2bitmap(d));
					}
				}

				// if (userObject.isMale()) {
				// Drawable d = registerAc.getResources().getDrawable(
				// R.drawable.extra_slide_boy_bg);
				// imgGender.setImageBitmap(Utils.convert2bitmap(d));
				// isMale = true;
				// } else {
				// Drawable d = registerAc.getResources().getDrawable(
				// R.drawable.extra_slide_girl_bg);
				// imgGender.setImageBitmap(Utils.convert2bitmap(d));
				// isMale = false;
				// }
				// if (userObject.getImageUrl() != null) {
				// DownloadImage down = new DownloadImage(imgViewAvatar, 60,
				// 60, mContext);
				// down.execute(userObject.getImageUrl());
				// }
				// initEventEditText();
			} else if (msg.what == 1) {
				// Toast.makeText(mContext, "Cannot find user with id " +
				// userID,
				// Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				// Toast.makeText(mContext, "Update succeed",
				// Toast.LENGTH_LONG).show();
			} else if (msg.what == 4) {
				Toast.makeText(mContext, "Error when updating",
						Toast.LENGTH_LONG).show();
			}
		};
	};
	private String mEmail;

	public UserName(Context context, Register register, String time,
			String authe, String mNumber, String email) {
		mContext = context;
		registerAc = register;
		mAuthe = authe;
		mTime = time;
		mMobile = mNumber;
		mEmail = email;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.register_username, null);

		initView();
		initData();
		setEvent();
	}

	public UserName(Context context, Register register, String time,
			String authe, String mNumber, int mIsp, String email) {
		mContext = context;
		registerAc = register;
		mAuthe = authe;
		mTime = time;
		mMobile = mNumber;
		mEmail = email;
		isp = mIsp;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.register_username, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		rlProfile = (RelativeLayout) mView.findViewById(R.id.relativeLayout20);
		mEdtxtNickName = (EditText) mView
				.findViewById(R.id.regiter_username_edittxtNick);
		mTvLink = (TextView) mView.findViewById(R.id.register_tv44);
		mEdtxFullname = (EditText) mView
				.findViewById(R.id.register_username_edtxtFullname);
		mEdtxYear = (EditText) mView.findViewById(R.id.tvYear);
		mEdtxMounth = (EditText) mView
				.findViewById(R.id.register_username_edtxMonth);
		mEdtxDate = (EditText) mView
				.findViewById(R.id.register_username_edtxDate);

		mTvLink.setClickable(true);
		mTvLink.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<a href='http://www.google.com'> 利用規約 </a>";
		mTvLink.setText(Html.fromHtml(text));

		llGender = (LinearLayout) mView.findViewById(R.id.profile_gender);
		imgGender = (ImageView) mView.findViewById(R.id.profile_img_gender);
		imgGender.setOnClickListener(onClickComponent);

		imgSlideGender = (ImageView) mView
				.findViewById(R.id.profile_img_slide_gender);
		llOn = (LinearLayout) mView.findViewById(R.id.profile_on_off);
		imgPublic = (ImageView) mView.findViewById(R.id.profile_img_on);
		imgPublic.setOnClickListener(onClickComponent);

		imgSlidePublic = (ImageView) mView
				.findViewById(R.id.profile_img_slide_on);
		imgSlidePublic.setFocusable(true);
		imgSlidePublic.setFocusableInTouchMode(true);
		handleScroll(llOn);
		handleScroll(llGender);

		rlProfile.setFocusable(true);
		rlProfile.setFocusableInTouchMode(true);
		handleEvent(rlProfile);

	}

	public void initData() {
		mCheckUser = new CheckUser(mContext);

	}

	private void handleScroll(View v) {
		v.setOnTouchListener(new OnTouchListener() {
			private float xStart;
			private int direction = 0;
			boolean isChange = false;
			private View vItem;

			public boolean onTouch(final View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					direction = 0;
					vItem = v.findViewWithTag(TAG);
					xStart = event.getX();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					float offsetX = event.getX() - xStart;
					if (offsetX < -20) {
						offsetX = -20;
					}
					if (offsetX < 0) {
						if (direction == 0) {
							direction = SwipAdapter.LEFT;
						}
					}

					else {
						direction = 0;
					}
					if (offsetX < 0) {
						LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vItem
								.getLayoutParams();
						params.leftMargin = (int) offsetX;
						params.rightMargin = (int) -offsetX;
						vItem.setLayoutParams(params);
					}
				} else if (event.getAction() == MotionEvent.ACTION_OUTSIDE
						|| event.getAction() == MotionEvent.ACTION_CANCEL
						|| event.getAction() == MotionEvent.ACTION_UP) {
					final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vItem
							.getLayoutParams();
					final int leftMargin = params.leftMargin;
					final int rightMargin = params.rightMargin;
					Log.i("NDT", leftMargin + " left");
					Animation slideBackAnimation = new Animation() {
						@Override
						protected void applyTransformation(
								float interpolatedTime, Transformation t) {
							if (direction == SwipAdapter.LEFT
									&& Math.abs(leftMargin) > 5) {
								params.leftMargin = (int) (leftMargin - (Constant.SCREEN_WIDTH + leftMargin)
										* interpolatedTime);
								params.rightMargin = -params.leftMargin;
								this.setDuration(Math.abs(Constant.SCREEN_WIDTH
										+ leftMargin)
										* SwipAdapter.TIME_SLIDE_ANIMATION
										/ Constant.SCREEN_WIDTH);
								isChange = true;
							} else {
								params.leftMargin = (int) (leftMargin * (1 - interpolatedTime)) - 3;
								params.rightMargin = (int) (rightMargin * (1 - interpolatedTime));
								this.setDuration(Math.abs(leftMargin)
										* SwipAdapter.TIME_SLIDE_ANIMATION
										/ Constant.SCREEN_WIDTH);
								isChange = false;
							}
							vItem.setLayoutParams(params);
						};
					};
					vItem.startAnimation(slideBackAnimation);
					slideBackAnimation
							.setAnimationListener(new AnimationListener() {

								public void onAnimationStart(Animation animation) {

								}

								public void onAnimationRepeat(
										Animation animation) {

								}

								public void onAnimationEnd(Animation animation) {
									if (isChange) { // &&
													// onItemSlideChangedListener
													// != null){
										if (direction == SwipAdapter.LEFT) {
											mHandler.post(new Runnable() {

												@Override
												public void run() {
													LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vItem
															.getLayoutParams();
													params.leftMargin = -3;
													params.rightMargin = 0;
													vItem.setLayoutParams(params);
													handleToggle(v);

												}
											});
										} else {
											LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vItem
													.getLayoutParams();
											params.leftMargin = 0;
											params.rightMargin = 0;
											vItem.setLayoutParams(params);
										}
										direction = 0;
									}
								}
							});

				}
				return true;
			}
		});
	}

	public void setEvent() {
		// mBtnOk.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		//
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

	private void handleToggle(View v) {
		if (v.getId() == llGender.getId()) {
			isMale = !isMale;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if (isMale) {
						Drawable d = registerAc.getResources().getDrawable(
								R.drawable.extra_slide_boy_bg);
						imgGender.setImageBitmap(Utils.convert2bitmap(d));
					} else {
						Drawable d = registerAc.getResources().getDrawable(
								R.drawable.extra_slide_girl_bg);
						imgGender.setImageBitmap(Utils.convert2bitmap(d));
					}
				}
			});
			JSONObject js = new JSONObject();

			try {
				js.put("gender", isMale ? 0 : 1);
				// updateData(js.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (v.getId() == llOn.getId()) {
			isOn = !isOn;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if (isOn) {
						Drawable d = registerAc.getResources().getDrawable(
								R.drawable.on_button);
						imgPublic.setImageBitmap(Utils.convert2bitmap(d));
					} else {
						Drawable d = registerAc.getResources().getDrawable(
								R.drawable.off_button);
						imgPublic.setImageBitmap(Utils.convert2bitmap(d));
					}

				}
			});

		}
	}

	/*
	 * 
	 * Handler doi load data tu server
	 */

	final Handler handlerRegister = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {

					try {
						if (mJsonAlalysis.getCode(data) != null) {
							JSONObject json = new JSONObject(data);
							boolean retval = json.getBoolean("retval");
							if (retval) {
								ItelApplication.user_id = json
										.getInt("user_id");
								mCheckUser.addserId(String
										.valueOf(ItelApplication.user_id));
							} else {
								// error error_code":0,"error_msg":"error
								// request","timestamp":1348939040}
								ItelApplication.timeStamp = json
										.getLong("timestamp");
								ItelApplication.err_code = json
										.getInt("error_code");
								ItelApplication.err_message = json
										.getString("error_msg");
								mCheckUser.clearData();
								Toast.makeText(
										mContext,
										"Error " + ItelApplication.err_message
												+ " when registering",
										Toast.LENGTH_LONG).show();
								// back to register
								ChoseNet chose = new ChoseNet(mContext,
										registerAc);
								registerAc.changeViewLL(chose.getmView());
								return;
							}
							Log.i("NDT", "register  " + data);
							registerAc.changeViewLL(inputNumbe.getmView());
						} else {
							registerAc.changeViewLL(inputNumbe.getmView());
						}

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

	// ///
	private OnClickListener onClickComponent = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			// switch (v.getId()) {
			// case R.id.relativeLayout5:
			// MemoSetting memo = new MemoSetting(mContext, registerAc,
			// userObject, uuid);
			// registerAc.changeViewLL(memo.getmView());
			// break;
			// case R.id.btnBack:
			// SettingsScreen setting = new SettingsScreen(mContext,
			// registerAc);
			// registerAc.changeViewLL(setting.getmView());
			// break;
			// case R.id.avatar:
			// if (userObject.getImageUrl() == null
			// || userObject.getImageUrl().equals("")) {
			// createPhoto(mContext);
			// } else
			// avatarOption(mContext);
			// // upload avatar
			// break;
			// case R.id.relativeLayout6:
			// int id = Integer.parseInt(userID);
			// AlbumSetting album = new AlbumSetting(mContext, registerAc,
			// id);
			// registerAc.changeViewLL(album.getmView());
			// break;
			// default:
			// break;
			// }
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
										// slideAnimation(v);
										// upDownAnimation(v);
										String url = registerAc
												.getString(R.string.url_register);
										List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
												2);
										nameValuePairs
												.add(new BasicNameValuePair(
														"mobile_num", mMobile));// isp
																				// authen_code
										nameValuePairs
												.add(new BasicNameValuePair(
														"email", mEmail));// isp
																			// authen_code
										nameValuePairs
												.add(new BasicNameValuePair(
														"authen_code", mAuthe));
										nameValuePairs
												.add(new BasicNameValuePair(
														"isp", String
																.valueOf(isp)));

										JSONObject j = new JSONObject();
										try {
											String uuid = Constant.md5Digest(
													mTime, registerAc);
											mCheckUser.addUuid(uuid);
											ItelApplication.uuid = uuid;
											Log.i("NDT", "uuid  " + uuid);
											j.put("uuid", uuid);
											j.put("nick", mEdtxtNickName
													.getText().toString());
											j.put("name", mEdtxFullname
													.getText().toString());
											if (isMale)
												j.put("gender", 1);
											else
												j.put("gender", 0);
											if (isOn)
												j.put("hide_age", true);
											else
												j.put("hide_age", false);
											String date = mEdtxYear.getText()
													.toString()
													+ "-"
													+ mEdtxMounth.getText()
															.toString()
													+ "-"
													+ mEdtxDate.getText()
															.toString();
											j.put("birth", date);
										} catch (JSONException exception) {

										}
										nameValuePairs
												.add(new BasicNameValuePair(
														"user_data", j
																.toString()));
										Log.e("",
												"data==="
														+ nameValuePairs
																.toString());
										mJsonAlalysis.executeLoadData(url,
												handlerRegister,
												v.getContext(), nameValuePairs);
										inputNumbe = new DoneRegister(v
												.getContext(), registerAc, 1,
												mTime);

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

	private final int TIME_SLIDE_ANIMATION = 500;
	private final int TIME_UP_DOWN_ANIMATION = 500;
}
