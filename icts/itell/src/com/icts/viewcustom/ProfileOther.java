package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.itel.ChatActivity;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.object.CompanyObject;
import com.icts.object.FriendObject;
import com.icts.object.User;
import com.icts.utils.Constant;
import com.icts.utils.Constant.ViewMode;
import com.icts.utils.DownloadImage;
import com.icts.utils.DownloadImageTask;
import com.icts.utils.Utils;

public class ProfileOther {
	private final String BACKVIEW_TAG = "Back:Activity"; 
	private final int PROFILE_LOAD_DATA = 0;
	private final int PROFILE_LOAD_ERROR = 1;
	private final int PROFILE_UPDATE_ERROR = 4;
	private final int PROFILE_UPDATE_ERROR_RESSTRICT = 6;
	private final int PROFILE_UPDATE_ERROR_BLOCK = 7;
	private final int PROFILE_UPDATE_SUCCESS = 3;
	private final int PROFILE_LOAD_ALBUM = 5;
	private final int TIME_SLIDE_ANIMATION = 500;
	private final String TAG = "toggle"; 
	private Context mContext;
	private MainActivity mainActivity;
	private LayoutInflater layoutInflater;
	private RelativeLayout rlChat;
	private LinearLayout llAge;
	private boolean invite = false;
	private int inviteID = 0;
	private Button btnBack;
	private View mView = null;
	private User userObject;
	private TextView tvNick,tvGoodbadge,tvNormalBadge,tvbadbadge,tvGender,tvAge,tvDesc;
	private ImageView imgAvatar;
	private ImageView imgRequestFriend;
	private RelativeLayout llPublicOn,llPublicOff;
	private RelativeLayout llBlockOn,llBlockOff;
	private RelativeLayout llAlbumImage1,llAlbumImage2,llAlbumImage3;
	private RelativeLayout llAlbumText1,llAlbumText2,llAlbumText3;
	private ImageView imgAlbumImage1,imgAlbumImage2,imgAlbumImage3;
	private Object mObj;
	private String userID;
	private String urlPhoto1,urlPhoto2,urlPhoto3;
	private boolean isBlock = false, isRestrict = false;
	private DownloadTask down1,down2,down3;
	private ProgressDialog progress;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (progress!=null){
				progress.dismiss();
			}
			if (msg.what==PROFILE_LOAD_DATA){
				/*Utils.animation(mView, 500);*/
				tvNick.setText(userObject.getUserNick());
				tvGoodbadge.setText(String.valueOf(userObject.badge_good));
				tvbadbadge.setText(String.valueOf(userObject.badge_bad));
				tvNormalBadge.setText(String.valueOf(userObject.badge_normal));
				tvDesc.setText(userObject.desc);
				if (userObject.isMale()){
					tvGender.setText(mainActivity.getResources().getString(R.string.profile_male));
				}
				else{
					tvGender.setText(mainActivity.getResources().getString(R.string.profile_female));
				}
				if (userObject.getImageUrl() != null&&(!userObject.getImageUrl().equals(""))) {
					DownloadImageTask task = new DownloadImageTask(imgAvatar, 100, 100, 
							mContext, userObject.getImageUrl(),userObject.isMale());
					mHandler.post(task);
				}
				else {
					setDefaultAvatar(imgAvatar);
				}
				
				//age
				if (!userObject.hide_age){
					Date date = new Date(ItelApplication.timeStamp*1000 );
					Date date2 = new Date(userObject.getBirthday());
					int age = date.getYear() - date2.getYear();
					llAge.setVisibility(View.VISIBLE);
					tvAge.setText(String.valueOf(age));
				}
				else {
					llAge.setVisibility(View.GONE);
				}
				
				//Visible send request button
				if (invite||userObject.isFriend()){
					imgRequestFriend.setVisibility(View.GONE);
				}
				else {
					imgRequestFriend.setVisibility(View.VISIBLE);
				}
				
				if (isBlock){
					llBlockOn.setVisibility(View.VISIBLE);
					llBlockOff.setVisibility(View.GONE);
				}
				else {
					llBlockOn.setVisibility(View.GONE);
					llBlockOff.setVisibility(View.VISIBLE);
				}
				
				if (isRestrict){
					llPublicOn.setVisibility(View.VISIBLE);
					llPublicOff.setVisibility(View.GONE);
				}
				else {
					llPublicOn.setVisibility(View.GONE);
					llPublicOff.setVisibility(View.VISIBLE);
				}
			}
			else if (msg.what==PROFILE_LOAD_ERROR){
				//if error, move to the last screen
				/*if (progress!=null){
					progress.dismiss();
				}*/
				Toast.makeText(mContext, mContext.getString(R.string.profile_message_error), Toast.LENGTH_LONG).show();
				handleBack();
			}
			else if (msg.what==PROFILE_UPDATE_SUCCESS){
				if (progress!=null){
					progress.dismiss();
				}
				Toast.makeText(mContext, mContext.getString(R.string.message_update_success), Toast.LENGTH_LONG).show();
			}
			else if (msg.what==PROFILE_UPDATE_ERROR){
				if (progress!=null){
					progress.dismiss();
				}
				Toast.makeText(mContext,mContext.getString(R.string.message_update_error)+ItelApplication.err_message, Toast.LENGTH_LONG).show();
			}
			else if (msg.what==PROFILE_LOAD_ALBUM){
				
				//album
				@SuppressWarnings("unchecked")
				ArrayList<String> data =(ArrayList<String>) msg.obj;
				if (data!=null){
					if (data.size()==2){
						data.add(null);
					}
					else if (data.size()==1){
						data.add(null);
						data.add(null);
					}
					else if (data.size()==0){
						data.add(null);
						data.add(null);
						data.add(null);
					}
					urlPhoto1 = data.get(0);
					urlPhoto2 = data.get(1);
					urlPhoto3 = data.get(2);
				}
				else {
					urlPhoto1 = null;
					urlPhoto2 = null;
					urlPhoto3 = null;
				}
				WindowManager windowManager = mainActivity.getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                int mScreenWidth;
                int mScreenHeight;
                mScreenWidth = display.getWidth();
                mScreenHeight = display.getHeight();
				if (urlPhoto1!=null){
					//llAlbumImage1.setVisibility(View.VISIBLE);
					llAlbumText1.setVisibility(View.GONE);
					down1 = new DownloadTask(imgAlbumImage1, mScreenWidth, 
							mScreenHeight, mContext,llAlbumText1,llAlbumImage1,urlPhoto1,mHandler);
					
					mHandler.post(down1);
				}
				else {
					llAlbumImage1.setVisibility(View.GONE);
				}
				
				if (urlPhoto2!=null){
					llAlbumText2.setVisibility(View.GONE);
					down2= new DownloadTask(imgAlbumImage2,  mScreenWidth, mScreenHeight, 
									mContext,llAlbumText2,llAlbumImage2,urlPhoto2,mHandler);
					mHandler.post(down2);
					//new Thread(down2).start();
				}
				else {
					llAlbumImage2.setVisibility(View.GONE);
				}
				
				if (urlPhoto3!=null){
					//llAlbumImage3.setVisibility(View.VISIBLE);
					llAlbumText3.setVisibility(View.GONE);
					down3 = new DownloadTask(imgAlbumImage3,  mScreenWidth, 
											     mScreenHeight, mContext,llAlbumText3,llAlbumImage3,urlPhoto3,mHandler);
					//new Thread(down3).start();
					mHandler.post(down3);
				}
				else {
					llAlbumImage3.setVisibility(View.GONE);
				}
				/*if (progress!=null){
					progress.dismiss();
				}
				Utils.animation(mView, 500);*/
			}
			else if (msg.what==PROFILE_UPDATE_ERROR_RESSTRICT){
				if (progress!=null){
					progress.dismiss();
				}
				Toast.makeText(mContext, mContext.getString(R.string.message_update_error)+ItelApplication.err_message, Toast.LENGTH_LONG).show();
				isRestrict = !isRestrict;
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						if (isRestrict){
							llPublicOn.setVisibility(View.VISIBLE);
							llPublicOff.setVisibility(View.GONE);
						}
						else {
							llPublicOn.setVisibility(View.GONE);
							llPublicOff.setVisibility(View.VISIBLE);
						}
					}
				});
				
				
			}
			else if (msg.what==7){
				/*if (progress!=null){
					progress.dismiss();
				}*/
				Toast.makeText(mContext, mContext.getString(R.string.message_update_error)+ItelApplication.err_message, Toast.LENGTH_LONG).show();
				isBlock = !isBlock;
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						if (isBlock){
							llBlockOn.setVisibility(View.VISIBLE);
							llBlockOff.setVisibility(View.GONE);
						}
						else {
							llBlockOn.setVisibility(View.GONE);
							llBlockOff.setVisibility(View.VISIBLE);
						}
						
					}
				});
				
			}
			else if (msg.what==8){
				/*if (progress!=null){
					progress.dismiss();
				}*/
				imgRequestFriend.setVisibility(View.GONE);
				Toast.makeText(mContext, mContext.getString(R.string.profile_request_success), Toast.LENGTH_LONG).show();
			}
			else if (msg.what==9){
				/*if (progress!=null){
					progress.dismiss();
				}*/
				imgRequestFriend.setVisibility(View.VISIBLE);
				Toast.makeText(mContext, mContext.getString(R.string.profile_request_error)+ItelApplication.err_message, Toast.LENGTH_LONG).show();
			}
		};
	};
	
	private void showPhoto(String url,RelativeLayout rl,RelativeLayout rlText,ImageView img,int index){
		if (urlPhoto2!=null){
			//llAlbumImage2.setVisibility(View.VISIBLE);
			llAlbumText2.setVisibility(View.GONE);
			DownloadTask down2= new DownloadTask(imgAlbumImage2,  Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, 
					mContext,llAlbumText2,llAlbumImage2,urlPhoto2,mHandler);
			new Thread(down2).start();
		}
		else {
			llAlbumImage2.setVisibility(View.GONE);
		}
	}
	private final int MARGIN;
	public ProfileOther(Context context, MainActivity mActivity, Object obj) {
		ItelApplication.lastView = ItelApplication.currentView;
		ItelApplication.currentView = ViewMode.PROFILE;
		MARGIN= Utils.convertDip2Pixel(8.0f, context);
		mContext = context;
		mainActivity = mActivity;
		mObj = obj;
		progress = new ProgressDialog(mContext);
		progress.setMessage(mContext.getString(R.string.message_loading));
		
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.layout_other_profile, null);
		if (mObj!=null){
			if (mObj instanceof User) {
				 userID = ((User) mObj).getUserId();
			}
			else if(mObj instanceof FriendObject){
				userID = String.valueOf(((FriendObject) mObj).getFriendID()); 
			}
			else if(mObj instanceof CompanyObject){
				userID = String.valueOf(((CompanyObject) mObj).getmUserId()); 
			}
			else {
				userID = String.valueOf(ItelApplication.user_id);
			}
		}
		initViewComponent();
	}
	
	/**
	 * Initial view
	 */
	private void initViewComponent() {
		
		btnBack = (Button) mView.findViewById(R.id.btnProfileBack);
		//tvName = (TextView) mView.findViewById(R.id.tv1);
		tvNick = (TextView) mView.findViewById(R.id.profile_tv_name);
		tvGoodbadge = (TextView) mView.findViewById(R.id.tv_badge_good);
		tvNormalBadge = (TextView) mView.findViewById(R.id.tv_badge_normal);
		tvbadbadge = (TextView) mView.findViewById(R.id.tv_badge_bad);
		tvDesc = (TextView) mView.findViewById(R.id.profile_desc);
		tvGender = (TextView) mView.findViewById(R.id.profile_tv_gender);
		tvAge = (TextView) mView.findViewById(R.id.profile_tv_age);
		imgRequestFriend = (ImageView)mView.findViewById(R.id.profile_img_request);
		imgAvatar = (ImageView)mView.findViewById(R.id.profile_avatar);
		
		llBlockOn = (RelativeLayout) mView.findViewById(R.id.profile_ll_block_on);
		llBlockOff = (RelativeLayout) mView.findViewById(R.id.profile_ll_block_off);
		
		llPublicOn = (RelativeLayout) mView.findViewById(R.id.profile_ll_restrict_on);
		llPublicOff = (RelativeLayout) mView.findViewById(R.id.profile_ll_restrict_off);
		rlChat = (RelativeLayout)mView.findViewById(R.id.profile_rl_chat);
		
		llAge = (LinearLayout) mView.findViewById(R.id.profile_ll_age);
		if (llAge!=null){
			llAge.setVisibility(View.GONE);
		}
		
		llAlbumImage1 = (RelativeLayout) mView.findViewById(R.id.llAlbumItem1);
		imgAlbumImage1 = (ImageView) mView.findViewById(R.id.imgAlbumItem1);
		
		llAlbumImage2 = (RelativeLayout) mView.findViewById(R.id.llAlbumItem2);
		imgAlbumImage2 = (ImageView) mView.findViewById(R.id.imgAlbumItem2);
		
		llAlbumImage3 = (RelativeLayout) mView.findViewById(R.id.llAlbumItem3);
		imgAlbumImage3 = (ImageView) mView.findViewById(R.id.imgAlbumItem3);
		
		llAlbumText1 = (RelativeLayout) mView.findViewById(R.id.profile_rl_item_text_right1);
		llAlbumText2 = (RelativeLayout) mView.findViewById(R.id.profile_rl_item_text_right2);
		llAlbumText3 = (RelativeLayout) mView.findViewById(R.id.profile_rl_item_text_right3);
		
		llAlbumText1.setVisibility(View.GONE);
		llAlbumText2.setVisibility(View.GONE);
		llAlbumText3.setVisibility(View.GONE);
		
		//lvAlbum = (ListView)mView.findViewById(R.id.profile_album_lv);
		btnBack.setOnClickListener(onClickComponent);
		imgRequestFriend.setOnClickListener(onClickComponent);
		if (rlChat!=null){
			rlChat.setFocusable(true);
			rlChat.setFocusableInTouchMode(true);
			handleEvent(rlChat,false);
		}
		
		handleScroll(llBlockOff,false);
		handleScroll(llBlockOn,true);
		handleScroll(llPublicOff,false);
		handleScroll(llPublicOn,true);
		loadProfileData();
	}
	
	

	private void loadProfileData() {
		getJsonProfileData();
		getAlbumList(ItelApplication.user_id, ItelApplication.uuid, userID);
	}

	/**
	 * get data user profile
	 */
	private void getJsonProfileData() {
		progress.setMessage(mContext.getString(R.string.message_loading));
		progress.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				/*
				 * ,"hide_age":false,"name":"hieu","birth":"1983-08-08 00:00:00","desc":"","badge_good":"0",
				 * "badge_normal":"0","badge_bad":"0","restrict_pub":false,"block":false,"befriend":false,"invite":false
				 */
				JSONObject jObject;
				jObject = Constant.getJSONfromURL(String.format(Constant.SETTING_GET_PROFILE_NEW,
													ItelApplication.user_id+"",ItelApplication.uuid,userID));
				if (jObject != null) {
					userObject = new User();
					try {
						long timestamp = jObject.getLong("timestamp");
						ItelApplication.timeStamp = timestamp;
						JSONObject json2 = jObject.getJSONObject("user_data");
						userObject.setUserNick((String) json2.get("nick"));
						userObject.setName((String) json2.get("name"));
						userObject.setMale( json2.getBoolean("gender"));
						userObject.setImageUrl((String) json2.getString("avatar"));
						String birthday = (String) json2.get("birth");
						Date date = Utils.convertStringToDate(birthday, null);
						userObject.setBirthday(date.getTime());
						userObject.desc = json2.getString("desc");
						//id
						userObject.setUserId(json2.getString("id"));
						userObject.badge_good = json2.getInt("badge_good");
						userObject.badge_normal = json2.getInt("badge_normal");
						userObject.badge_bad = json2.getInt("badge_bad");
						userObject.canSearch = json2.getBoolean("can_search");
						isBlock = json2.getBoolean("block");
						userObject.hide_age = json2.getBoolean("hide_age");
						userObject.setFriend(json2.getBoolean("befriend"));
						if (!userObject.isFriend()){
							invite = json2.getBoolean("invite");
							if (invite){
								inviteID = json2.getInt("invite_id");
							}
						}
						isRestrict= json2.getBoolean("restrict_pub");
						/*urlPhoto1 = "http://img-hn.24hstatic.com:8008/upload/3-2012/images/2012-09-28/1348826326-sao-teen-khoe-bung--5-.jpg";
						urlPhoto2 = "http://img-hn.24hstatic.com:8008/upload/3-2012/images/2012-09-28/1348826326-sao-teen-khoe-bung--5-.jpg";
						urlPhoto3 = "http://img-hn.24hstatic.com:8008/upload/3-2012/images/2012-09-28/1348826326-sao-teen-khoe-bung--5-.jpg";*/
						mHandler.sendEmptyMessage(PROFILE_LOAD_DATA);
					} catch (JSONException e) {
						
						mHandler.sendEmptyMessage(PROFILE_LOAD_ERROR);
						e.printStackTrace();
					}
				}
				mHandler.sendEmptyMessage(PROFILE_LOAD_DATA);
			}
		}).start();
	}

	private OnClickListener onClickComponent = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.btnProfileBack:
				/*SettingsScreen setting = new SettingsScreen(mContext,
						mainActivity);
				mainActivity.changeViewLL(setting.getmView());*/
				handleBack();
				break;
			case R.id.profile_img_request:
				if (!userObject.isFriend()){
					if (!invite){
						//invite
						sendRequest(ItelApplication.user_id, ItelApplication.uuid, userID);
					}
					else {
						//accept friend
						//acceptFriend(ItelApplication.user_id, ItelApplication.uuid, String.valueOf(inviteID),true);
					}
				}
				else {
					//Toast.makeText(mContext, "This user are friend", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
		}
	};


	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = mView;
	}
	
	  /**
     * create slide animation 
     * @param v
     */
    protected void handleEvent(final View v,final boolean disableR){
    	v.setOnTouchListener(new OnTouchListener() {
    		final boolean disableRight = disableR;
            private float xStart;
            private int direction = 0;
            View itemUp = null;
            private boolean slideFinish = false;
            public boolean onTouch(final View v, MotionEvent event) {
            	int m = MARGIN;
        		if ((v.getId()==R.id.llAlbumItem1)||(v.getId()==R.id.llAlbumItem2)||
        				(v.getId()==R.id.llAlbumItem3)){
        			m = 0;
        		}
        		final int margin = m;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    direction = 0;
                    itemUp = v.findViewWithTag("item_move");
                    xStart = event.getX();
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    float offsetX = event.getX() - xStart;
                    if(offsetX < 0){
                        if(direction >=0 ){
                            direction = SwipAdapter.LEFT;
                            //onItemSlideLeftListener.onItemSlideLeft(v);
                        }
                    }
                    else if(offsetX > 0){
                    	if (!disableRight){
	                        if(direction <=0 ){
	                            direction = SwipAdapter.RIGHT;
	                        }
                    	}
                    	else {
                    		direction = 0;
                    	}
                    }
                    else{
                        direction = 0;
                    }
                    if (disableRight){
	                    if (offsetX<0){
		                	RelativeLayout.LayoutParams params = (LayoutParams) itemUp.getLayoutParams();
		                	params.leftMargin = (int) offsetX;
		                	params.rightMargin = (int) -offsetX;
		                	itemUp.setLayoutParams(params);
	                    }
                    }
                    else {
                    	RelativeLayout.LayoutParams params = (LayoutParams) itemUp.getLayoutParams();
	                	params.leftMargin = (int) offsetX;
	                	params.rightMargin = (int) -offsetX;
	                	itemUp.setLayoutParams(params);
                    }
                }else if(event.getAction() == MotionEvent.ACTION_OUTSIDE || 
                        event.getAction() == MotionEvent.ACTION_CANCEL ||
                        event.getAction() == MotionEvent.ACTION_UP){
                    final RelativeLayout.LayoutParams params = (LayoutParams) itemUp.getLayoutParams();
                    final int leftMargin = params.leftMargin;
                    final int rightMargin = params.rightMargin;
                    Animation slideBackAnimation = new Animation(){
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                           if(direction == SwipAdapter.LEFT && Math.abs(leftMargin) > Constant.SCREEN_WIDTH/3){
                                params.leftMargin = (int) (leftMargin - (Constant.SCREEN_WIDTH + leftMargin) * interpolatedTime);
                                params.rightMargin = -params.leftMargin;
                                this.setDuration(Math.abs(Constant.SCREEN_WIDTH + leftMargin)*TIME_SLIDE_ANIMATION/Constant.SCREEN_WIDTH);
                                slideFinish = true;
                            }
                           else if(direction == SwipAdapter.RIGHT && Math.abs(leftMargin) > Constant.SCREEN_WIDTH/3){
                        	   if (!disableRight){
	                               params.leftMargin = (int) (leftMargin+(Constant.SCREEN_WIDTH - leftMargin) * interpolatedTime);
	                               params.rightMargin = -params.leftMargin;
	                               this.setDuration(Math.abs(Constant.SCREEN_WIDTH - leftMargin)*TIME_SLIDE_ANIMATION/Constant.SCREEN_WIDTH);
	                               slideFinish = true;
                        	   }
                        	   else {
                        		   params.leftMargin = margin+(int) (leftMargin * (1 - interpolatedTime));
                                   params.rightMargin = margin+(int) (rightMargin*(1 - interpolatedTime));
                                   this.setDuration(Math.abs(leftMargin)*TIME_SLIDE_ANIMATION/Constant.SCREEN_WIDTH);
                                   slideFinish = false;
                        	   }
                           }
                           else{
                                params.leftMargin = margin+(int) (leftMargin * (1 - interpolatedTime));
                                params.rightMargin = margin+(int) (rightMargin*(1 - interpolatedTime));
                                this.setDuration(Math.abs(leftMargin)*TIME_SLIDE_ANIMATION/Constant.SCREEN_WIDTH);
                                slideFinish = false;
                            }
                            itemUp.setLayoutParams(params);
                        };
                    };
                    itemUp.startAnimation(slideBackAnimation);
                    slideBackAnimation.setAnimationListener(new AnimationListener() {
                        
                        public void onAnimationStart(Animation animation) {
                            
                        }
                        
                        public void onAnimationRepeat(Animation animation) {
                            
                        }
                        
                        public void onAnimationEnd(Animation animation) {
                            if(slideFinish){ //&& onItemSlideChangedListener != null){
                            	if (direction == SwipAdapter.LEFT){
                            		slideFinish(v,direction);
                            	}
                            	else if (direction == SwipAdapter.RIGHT){
                            		if (!disableRight){
                            			FriendObject friend = Utils.convertUser2Friend(userObject);
                                    	if (!Utils.checkValidateTime(ItelApplication.user, friend)){
                                    		//holder.vText.setVisibility(View.GONE);
                                    		String s = "itell time of friend or yours can be invalid.";
                                    		Utils.showMessage(mContext, s);
                                    		final RelativeLayout.LayoutParams params = 
                                    				(RelativeLayout.LayoutParams) itemUp.getLayoutParams();
                                    		
                                    		params.leftMargin = margin;
                                    		params.rightMargin = margin;
                                    		itemUp.setLayoutParams(params);
                                    		itemUp.clearAnimation();
                                    		itemUp.requestLayout();
                                    		itemUp.bringToFront();
                                    		slideFinish = false;
                                    		return;
                                    		
                                    	}
	                            		slideFinish(v, direction);
                            		}
                            	}
                            }
                            else{
                            	final RelativeLayout.LayoutParams params = 
                            				(RelativeLayout.LayoutParams) itemUp.getLayoutParams();
            	                params.leftMargin =margin;
            	                params.rightMargin =margin;
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
   
    private void slideFinish(View v,int direction){
    	switch (v.getId()) {
		case (R.id.profile_rl_chat):	
			if (direction==SwipAdapter.RIGHT){
				//chat
				FriendObject friend = Utils.convertUser2Friend(userObject);
				ItelApplication.friendChat = friend;
				Intent intent = new Intent(mainActivity,
										   ChatActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString(BACKVIEW_TAG, Constant.PROFILE_VIEW);
				intent.putExtras(bundle);
				removeCallback();
				mainActivity.startActivity(intent);
				mainActivity.finish();
				//previewPhoto(urlPhoto1);
			}
			else if (direction==SwipAdapter.LEFT){
				//back
				onClickComponent.onClick(btnBack);
				removeCallback();
			}
			
			break;
		case (R.id.llAlbumItem1):	
			if (direction==SwipAdapter.LEFT){
				previewPhoto(urlPhoto1);
			}
			break;
		case (R.id.llAlbumItem2):	
			if (direction==SwipAdapter.LEFT){
				previewPhoto(urlPhoto2);
			}
			break;
		case (R.id.llAlbumItem3):	
			if (direction==SwipAdapter.LEFT){
				previewPhoto(urlPhoto3);
			}
			break;
		default:
			break;
		}
    }
    
    private void handleScroll(View v,final boolean disableR){ 
		v.setOnTouchListener(new OnTouchListener() {
            private float xStart;
            private boolean disableRight = disableR;
            private int direction = 0;
            boolean isChange = false;
            private View vItem;
            public boolean onTouch(final View v, MotionEvent event) {
            	 if(event.getAction() == MotionEvent.ACTION_DOWN){
                     direction = 0;
                     vItem = v.findViewWithTag(TAG);
                     xStart = event.getX();
                 }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                     float offsetX = event.getX() - xStart;
                     if(offsetX < 0){
                    	 if (offsetX<-20){
                     		offsetX = -20;
                     	}
                    	 if (disableRight){
                    		 //right disable
                    		if(direction >=0 ){
                			 	direction = SwipAdapter.LEFT;
                    		}
                    	 }
                    	 else {
                    		 direction = 0;
                    	 }
                     }
                     else if(offsetX > 0){
                    	 if (offsetX>20){
                     		offsetX = 20;
                     	}
                    	 if (!disableRight){
                    		 //false
 	                        if(direction <=0 ){
 	                            direction = SwipAdapter.RIGHT;
 	                        }
                     	}
                     	else {
                     		direction = 0;
                     	}
                     }
                     else{
                         direction = 0;
                     }
                     if (disableRight){
	                     if (offsetX<0){
	                    	 LayoutParams params = (LayoutParams) vItem.getLayoutParams();
	                    	 params.leftMargin = (int) offsetX;
	                    	 params.rightMargin = (int) -offsetX;
	                    	 vItem.setLayoutParams(params);
	                     }
                     }
                     else {
                    	 //disable left
                    	 if (offsetX>0){
	                    	 LayoutParams params = (LayoutParams) vItem.getLayoutParams();
	                    	 params.leftMargin = (int) offsetX;
	                    	 params.rightMargin = (int) -offsetX;
	                    	 vItem.setLayoutParams(params);
	                     }
                     }
                 }else if(event.getAction() == MotionEvent.ACTION_OUTSIDE || 
                         event.getAction() == MotionEvent.ACTION_CANCEL ||
                         event.getAction() == MotionEvent.ACTION_UP){
                     final LayoutParams params = (LayoutParams) vItem.getLayoutParams();
                     final int leftMargin = params.leftMargin;
                     final int rightMargin = params.rightMargin;
                     Log.i("NDT",leftMargin+" left" );
                     Animation slideBackAnimation = new Animation(){
                         @Override
                         protected void applyTransformation(float interpolatedTime, Transformation t) {
                            if(direction == SwipAdapter.LEFT && Math.abs(leftMargin) > 5){
                            	if (disableRight){
	                                 params.leftMargin = (int) (leftMargin - (100 + leftMargin) * interpolatedTime);
	                                 params.rightMargin = -params.leftMargin;
	                                 this.setDuration(Math.abs(100 + leftMargin)*SwipAdapter.TIME_SLIDE_ANIMATION/100);
	                                 isChange = true;
                            	}
                            	 else {
                           		   params.leftMargin = (int) (leftMargin * (1 - interpolatedTime));
                                     params.rightMargin = (int) (rightMargin*(1 - interpolatedTime));
                                     this.setDuration(Math.abs(leftMargin)*TIME_SLIDE_ANIMATION/100);
                                     isChange = false;
                           	   }
                             }
                            else if(direction == SwipAdapter.RIGHT && Math.abs(leftMargin) > 5){
                         	   if (!disableRight){
 	                               params.leftMargin = (int) (leftMargin+(100 - leftMargin) * interpolatedTime);
 	                               params.rightMargin = -params.leftMargin;
 	                               this.setDuration(Math.abs(100 - leftMargin)*TIME_SLIDE_ANIMATION/100);
 	                              isChange = true;
                         	   }
                         	   else {
                         		   params.leftMargin = (int) (leftMargin * (1 - interpolatedTime));
                                   params.rightMargin = (int) (rightMargin*(1 - interpolatedTime));
                                   this.setDuration(Math.abs(leftMargin)*TIME_SLIDE_ANIMATION/100);
                                   isChange = false;
                         	   }
                            }
                            else {
                            	 params.leftMargin = (int) (leftMargin * (1 - interpolatedTime));
                                 params.rightMargin = (int) (rightMargin*(1 - interpolatedTime));
                                 this.setDuration(Math.abs(leftMargin)*TIME_SLIDE_ANIMATION/100);
                                 isChange = false;
                            }
                            vItem.setLayoutParams(params);
                         };
                     };
                     vItem.startAnimation(slideBackAnimation);
                     slideBackAnimation.setAnimationListener(new AnimationListener() {
                         
                         public void onAnimationStart(Animation animation) {
                             
                         }
                         
                         public void onAnimationRepeat(Animation animation) {
                             
                         }
                         
                         public void onAnimationEnd(Animation animation) {
                             if(isChange){ //&& onItemSlideChangedListener != null){
                            	 if(direction == SwipAdapter.LEFT){
                            		 if (disableRight){
	                            		 mHandler.post(new Runnable() {
											
											@Override
											public void run() {
												 LayoutParams params = (LayoutParams) vItem.getLayoutParams();
			                            		 params.leftMargin = 0;
			                            		 params.rightMargin = 0;
			                            		 vItem.setLayoutParams(params);
			                            		 handleToggle(v);
												
											}
										});
                            		 }
                            	 }
                            	 else if(direction == SwipAdapter.RIGHT){
                            		 if (!disableRight){
	                            		 mHandler.post(new Runnable() {
	 										
	 										@Override
	 										public void run() {
	 											 LayoutParams params = (LayoutParams) vItem.getLayoutParams();
	 		                            		 params.leftMargin = 0;
	 		                            		 params.rightMargin = 0;
	 		                            		 vItem.setLayoutParams(params);
	 		                            		 handleToggle(v);
	 											
	 										}
	 									});
                            		 }
                            		 
                            	 }
                             }
                             else{
                            	LayoutParams params = (LayoutParams) vItem.getLayoutParams();
                              	params.leftMargin = 0;
                              	params.rightMargin = 0;
                              	vItem.setLayoutParams(params);
                             }
                             direction = 0;
                         }
                     });
                 }
            	return true;
            }
		});
	}
    
    
    private void getAlbumList(final int user_id, final String uuid,final String friend_id) {
		new Thread(new Runnable() {

			@Override
			public void run() {				
				JSONObject jObject = Constant.getJSONfromURL(Constant.GET_ALBUM
						+ user_id + Constant.uuid + uuid+"&friend_id=" + friend_id);
				JSONArray jsonAr;
				int l;
				JSONObject jsonObj;
				ArrayList<String> arrAlbum = new ArrayList<String>(3);
				// Get friends
				try {
					if (jObject.has("album")) {
						jsonAr = jObject.getJSONArray("album");
						if (jsonAr != null) {
							l = jsonAr.length();
							for (int i = 0; i < l; i++) {
								jsonObj = jsonAr.optJSONObject(i);
								String link = jsonObj.getString("url");
								arrAlbum.add(link);
							}
							mHandler.sendMessage(mHandler.obtainMessage(PROFILE_LOAD_ALBUM, arrAlbum));
						}
					}
					else {
						mHandler.sendMessage(mHandler.obtainMessage(PROFILE_LOAD_ALBUM, arrAlbum));
					}
					
				} catch (JSONException e) {
					mHandler.sendEmptyMessage(PROFILE_LOAD_ERROR);
					e.printStackTrace();
				}
			}
		}).start();
	}
    
    private void acceptFriend(final int user_id, final String uuid,final String friend_id,final boolean accept){
    	new Thread(new Runnable() {

			@Override
			public void run() {				
				boolean c = Utils.acceptFriend(String.valueOf(ItelApplication.user_id), ItelApplication.uuid,
								    friend_id,accept);
				if (c){
					userObject.setFriend(true);
					inviteID = 0;
					invite = false;
					mHandler.sendEmptyMessage(8);
				}
				else {
					//error
					userObject.setFriend(false);
					inviteID = 0;
					invite = false;
					mHandler.sendEmptyMessage(9);
				}
			}
		}).start();
    }
    
    private void sendRequest(final int user_id, final String uuid,final String friend_id) {
		new Thread(new Runnable() {

			@Override
			public void run() {				
				String c = Utils.requestFriend(String.valueOf(ItelApplication.user_id), ItelApplication.uuid,
								    userID);
				if (c!=null){
					inviteID = Integer.parseInt(c);
					invite = true;
					mHandler.sendEmptyMessage(8);
				}
				else {
					invite = false;
					//error
					mHandler.sendEmptyMessage(9);
				}
			}
		}).start();
	}

    private void handleToggle(View v){
    	switch (v.getId()) {
		case (R.id.profile_ll_block_off):
			isBlock = true;
			llBlockOn.setVisibility(View.VISIBLE);
			llBlockOff.setVisibility(View.GONE);
			updateData(false);
			break;
		case (R.id.profile_ll_block_on):
			isBlock = false;
			llBlockOn.setVisibility(View.GONE);
			llBlockOff.setVisibility(View.VISIBLE);
			updateData(false);
			break;
		case (R.id.profile_ll_restrict_off):
			isRestrict = true;
			llPublicOn.setVisibility(View.VISIBLE);
			llPublicOff.setVisibility(View.GONE);
			
			updateData(true);
			break;
		case (R.id.profile_ll_restrict_on):	
			isRestrict = false;
			
			llPublicOn.setVisibility(View.GONE);
			llPublicOff.setVisibility(View.VISIBLE);
			updateData(true);
			break;
		default:
			break;
		}
    	
    	
    }
    
    private void updateData(final boolean restric){
    	progress.setMessage(mContext.getString(R.string.message_updating));
		progress.show();
		new Thread(){
			@Override
			public void run() {
				boolean c= false;
				if (restric){
					c = Utils.restrictPub(String.valueOf(ItelApplication.user_id), ItelApplication.uuid,
										  userID,isRestrict);
				}
				else {
					c = Utils.block(String.valueOf(ItelApplication.user_id), ItelApplication.uuid,
								    userID,isBlock);
				}
				if (c){
					mHandler.sendEmptyMessage(PROFILE_UPDATE_SUCCESS);
				}
				else {
					//error
					if (restric){
						mHandler.sendEmptyMessage(PROFILE_UPDATE_ERROR_RESSTRICT);
					}
					else {
						mHandler.sendEmptyMessage(PROFILE_UPDATE_ERROR_BLOCK);
					}
				}
			}
		}.start();
	}
    /**
     *  preview image
     * @param url: link to show image
     */
 	private void previewPhoto(String url) {
         final Dialog dia = new Dialog(mContext);
         dia.setContentView(R.layout.preview_dialog);
         ImageView imageView = (ImageView) dia.findViewById(R.id.profile_preview_imageview);
         Button okButton = (Button) dia.findViewById(R.id.profile_preview_ok_button);
         okButton.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 dia.cancel();
             }
         });

         if (url!=null){
             try {
                
                 WindowManager windowManager = mainActivity.getWindowManager();
                 Display display = windowManager.getDefaultDisplay();
                 int mScreenWidth;
                 int mScreenHeight;
                 mScreenWidth = display.getWidth();
                 mScreenHeight = display.getHeight();
  
                 DownloadImage down = new DownloadImage(imageView,mScreenWidth,mScreenHeight,mContext);
                 down.execute(url);
             	/*DownloadImageTask task = new DownloadImageTask(imageView, mScreenWidth, mScreenHeight, 
    					mContext, url);
    			ItelApplication.submitOther(task);*/
                 dia.show();
             } catch (OutOfMemoryError e) {
                 e.printStackTrace();
             } catch (Exception e) {}
         }
     }
 	
 	
 	private class DownloadTask extends DownloadImageTask{

 		private View v;
 		private RelativeLayout p;
 		private Handler handler;
		public DownloadTask(ImageView imageView, int width, int height,
				Context context, String url) {
			super(imageView, width, height, context, url);
		}
		
		public DownloadTask(ImageView imageView, int width, int height,
				Context context,View v,RelativeLayout p,String url,Handler handler) {
			super(imageView, width, height, context,url);
			this.v = v;
			this.p = p;
			this.handler = handler;
			this.handler.post(new Runnable() {
				
				@Override
				public void run() {
					if (DownloadTask.this.v!=null){
						DownloadTask.this.v.setVisibility(View.GONE);
					}
				}
			});
		}
		@Override
		protected void setBitmap( Bitmap bitmap) {
			super.setBitmap(bitmap);
			final Bitmap b = bitmap;
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					if (b!=null){
						if (v!=null){
							v.setVisibility(View.VISIBLE);
						}
						if (p!=null){
							p.setVisibility(View.VISIBLE);
							p.setFocusable(true);
							p.setFocusableInTouchMode(true);
							handleEvent(p,true);
						}
						
					}
					else {
						if (p!=null){
							p.setVisibility(View.GONE);
						}
					}
					
				}
			});
			
		}
 		
 	}
 	private void removeCallback(){
 		if (mHandler!=null){
 			if (down1!=null){
 				mHandler.removeCallbacks(down1);
 			}
 			if (down2!=null){
 				mHandler.removeCallbacks(down2);
 			}
 			if (down3!=null){
 				mHandler.removeCallbacks(down3);
 			}
 		}
 	}
 	private void handleBack(){
 		boolean main = false;
 		if (mainActivity instanceof MainActivity){
 			main = true;
 		}
 		switch (ItelApplication.lastView) {
		case MAP:
			mainActivity.onClickMap.onClick(null);
			break;

		case SETTING:
			mainActivity.onClickSetting.onClick(null);
			break;
			
		case FRIEND:
			mainActivity.onClickFriend.onClick(null);
			break;
		case CHAT:
			mainActivity.onClickChat.onClick(null);
			break;
		default:
			break;
		} 
 	}
 	private void setDefaultAvatar(ImageView imgViewAvatar){
		if (userObject.isMale()) {
			Drawable d = mainActivity.getResources().getDrawable(
					R.drawable.avatar_male_default);
			imgViewAvatar.setImageBitmap(null);
			imgViewAvatar.setImageBitmap(Utils.convert2bitmap(d));
		}
		else {
			Drawable d = mainActivity.getResources().getDrawable(
					R.drawable.avatar_female_default);
			imgViewAvatar.setImageBitmap(null);
			imgViewAvatar.setImageBitmap(Utils.convert2bitmap(d));
		}
	}
}
