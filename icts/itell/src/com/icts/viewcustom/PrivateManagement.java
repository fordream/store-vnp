package com.icts.viewcustom;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.database.CheckUser;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.object.User;
import com.icts.utils.Constant;
import com.icts.utils.Utils;

public class PrivateManagement {
	private final int TIME_SLIDE_ANIMATION = 500;
	private final int TIME_UP_DOWN_ANIMATION = 1000;
	private Context mContext;
	private MainActivity mainActivity;
	private LayoutInflater layoutInflater;
	private View mView = null;
	private RelativeLayout rlBlock,rlContact,rlSearch;
	private TextView tvContact,tvSearch;
	private Button btnBack;
	private ImageView imgSearch,imgContact;
	private boolean isContactOn = false;
	private boolean isSearchOn = false;
	CheckUser mCheckUser;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what==0){
				if (isSearchOn){
	    			tvSearch.setText("OFF");
	    			imgSearch.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
	    		}
	    		else {
	    			imgSearch.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
	    			tvSearch.setText("ON");
	    		}
			}
			else if (msg.what==3){
				Toast.makeText(mContext, "Update succeed", Toast.LENGTH_LONG).show();
			}
			else if (msg.what==4){
				Toast.makeText(mContext, "Error when updating", Toast.LENGTH_LONG).show();
			}
		};
	};
	public PrivateManagement(Context context, MainActivity mActivity) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mainActivity = mActivity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater
				.inflate(R.layout.layout_private_management, null);
		mCheckUser = new CheckUser(mContext.getApplicationContext());
		initViewComponent();
	}

	void initViewComponent() {
		rlSearch= (RelativeLayout) mView.findViewById(R.id.relativeLayout2);
		rlContact = (RelativeLayout) mView.findViewById(R.id.relativeLayout3);
		tvSearch= (TextView) mView.findViewById(R.id.privacy_search_item_text_right);
		imgContact = (ImageView) mView.findViewById(R.id.imgContact);
		imgSearch= (ImageView) mView.findViewById(R.id.imgSearchOn);
		tvContact = (TextView) mView.findViewById(R.id.privacy_number_item_text_right);
		rlBlock = (RelativeLayout) mView.findViewById(R.id.relativeLayout4);
		rlBlock.setOnClickListener(onClickComponent);
		btnBack = (Button) mView.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(onClickComponent);
		
		isContactOn = mCheckUser.getAllowContact();
		imgSearch.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
		if (isContactOn){
			imgContact.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
			tvContact.setText("OFF");
		}
		else {
			imgContact.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
			tvContact.setText("ON");
		}
		
		getJsonProfileData();
		rlContact.setFocusable(true);
		rlContact.setFocusableInTouchMode(true);
		handleEvent(rlContact);
		
		rlSearch.setFocusable(true);
		rlSearch.setFocusableInTouchMode(true);
		handleEvent(rlSearch);
		
		rlBlock.setFocusable(true);
		rlBlock.setFocusableInTouchMode(true);
		handleEvent(rlBlock);
	}

	private void getJsonProfileData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				JSONObject jObject;
				jObject = Constant.getJSONfromURL(String.format(Constant.SETTING_GET_PROFILE_NEW,
						ItelApplication.user_id+"",ItelApplication.uuid,ItelApplication.user_id+""));
				/*jObject = Constant
						.getJSONfromURL(Constant.SETTING_GET_PROFILE + ItelApplication.user_id);*///ItelApplication.user_id);
				if (jObject != null) {
					User userObject = new User();
					try {
						/*String user_data = String.valueOf(jObject
								.getString("user_data"));*/
						long timestamp = jObject.getLong("timestamp");
						
						userObject.setmTimeCurrent(timestamp);
						ItelApplication.timeStamp = timestamp;
						JSONObject json2 = jObject.getJSONObject("user_data");
						userObject.setUserId(String.valueOf(ItelApplication.user_id));
						userObject.setUuid(ItelApplication.uuid);
						userObject.setUserNick((String) json2.get("nick"));
						userObject.setName((String) json2.get("name"));
						userObject.setMale((boolean) json2.getBoolean("gender"));
						userObject.setImageUrl((String) json2.get("avatar"));
						String birthday = (String) json2.get("birth");
						Date date = Utils.convertStringToDate(birthday, null);
						userObject.setStatusUpdateTime(date.getTime());
						ItelApplication.setTime(userObject.getStatusUpdateTime());
						userObject.desc = json2.getString("desc");
						userObject.canSearch = json2.getBoolean("can_search");
						userObject.setPhone(json2.getString("mobile_num"));
						isSearchOn = userObject.canSearch;
						ItelApplication.user = userObject;
						mHandler.sendEmptyMessage(0);
					} catch (JSONException e) {
						
						mHandler.sendEmptyMessage(1);
						e.printStackTrace();
					}
				}
				mHandler.sendEmptyMessage(0);
			}
		}).start();
	}

	OnClickListener onClickComponent = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnBack:
				SettingsScreen setting = new SettingsScreen(mContext,
						mainActivity);
				mainActivity.changeViewLL(setting.getmView());
				break;
			case R.id.relativeLayout4:
				BlogListSetting block = new BlogListSetting(mContext, mainActivity);
				mainActivity.changeViewLL(block.getmView());
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
    protected void handleEvent(final View v){
    	v.setOnTouchListener(new OnTouchListener() {
            private float xStart;
            private int direction = 0;
            View itemUp = null;
            private boolean isMove = true;
            private boolean slideFinish = false;
            public boolean onTouch(final View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    direction = 0;
                    isMove = true;
                    itemUp = v.findViewWithTag("item_move");
                    if (itemUp==null){
                    	isMove = false;
                    	itemUp = v.findViewWithTag("item_toggle");
                    }
                    xStart = event.getX();
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    float offsetX = event.getX() - xStart;
                    if(offsetX < 0){
                        if(direction >=0 ){
                            direction = SwipAdapter.LEFT;
                        }
                    }
                    else if(offsetX > 0){
                        if(direction <=0 ){
                            direction = SwipAdapter.RIGHT;
                        }
                    }
                    else{
                        direction = 0;
                    }
                    //if (offsetX<0){
                	RelativeLayout.LayoutParams params = (LayoutParams) itemUp.getLayoutParams();
                	params.leftMargin = (int) offsetX;
                	params.rightMargin = (int) -offsetX;
                	itemUp.setLayoutParams(params);
                    //}
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
                               params.leftMargin = (int) (leftMargin+(Constant.SCREEN_WIDTH - leftMargin) * interpolatedTime);
                               params.rightMargin = -params.leftMargin;
                               this.setDuration(Math.abs(Constant.SCREEN_WIDTH - leftMargin)*TIME_SLIDE_ANIMATION/Constant.SCREEN_WIDTH);
                               slideFinish = true;
                           }else{
                                params.leftMargin = (int) (leftMargin * (1 - interpolatedTime));
                                params.rightMargin = (int) (rightMargin*(1 - interpolatedTime));
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
                            	if(direction == SwipAdapter.RIGHT){
                            		onClickComponent.onClick(btnBack);
                            	}
                            	else if(direction == SwipAdapter.LEFT){
	                            	if (isMove){
	                            		itemUp.setVisibility(View.INVISIBLE);
	                            		upDownAnimation(v);
	                            	}
	                            	else {
	                            		mHandler.post(new Runnable() {
											
											@Override
											public void run() {
												//change text
			                            		changeText(v);
			                            		RelativeLayout.LayoutParams params = (LayoutParams) itemUp.getLayoutParams();
			                                	params.leftMargin = 0;
			                                	params.rightMargin = 0;
			                                	itemUp.setLayoutParams(params);
												
											}
										});
	                            	}
                            	}
                                //onItemSlideChangedListener.onItemSlideChanged(v, direction);
                                //slideAnimation(v);
                                //upDownAnimation(v);
                            }
                            else{
                            	final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) itemUp.getLayoutParams();
            	                params.leftMargin =0;
            	                params.rightMargin =0;
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
    
    private void updateData(){
		new Thread(){
			@Override
			public void run() {
				boolean c = Utils.allowSearch(String.valueOf(ItelApplication.user_id), ItelApplication.uuid,isSearchOn);
				if (c){
					//success
					mHandler.sendEmptyMessage(3);
				}
				else {
					mHandler.sendEmptyMessage(4);
				}
			}
		}.start();
	}
    
    private void changeText(View v){
    	if (rlContact.getId()==v.getId()){
    		isContactOn=!isContactOn;
    		if (isContactOn){
    			tvContact.setText("OFF");
    			imgContact.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
    		}
    		else {
    			imgContact.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
    			tvContact.setText("ON");
    		}
    		mCheckUser.updateAllowContact(isContactOn);
    	}
    	else if (rlSearch.getId()==v.getId()){
    		isSearchOn=!isSearchOn;
    		if (isSearchOn){
    			tvSearch.setText("OFF");
    			imgSearch.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
    		}
    		else {
    			imgSearch.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
    			tvSearch.setText("ON");
    		}
    		updateData();
    	}
    }
    private void upDownAnimation(View v){
    	if (v.getId()!=rlBlock.getId()){
    		return;
    	}
		v.setVisibility(View.GONE);
		TranslateAnimation animationUp = new TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0, 
            TranslateAnimation.RELATIVE_TO_SELF, 0, 
            TranslateAnimation.RELATIVE_TO_SELF, 0,
            TranslateAnimation.RELATIVE_TO_PARENT, -1);
           // TranslateAnimation.RELATIVE_TO_SELF, -1);
		ScaleAnimationListener listener = new ScaleAnimationListener(v);
		animationUp.setFillBefore(true);
		animationUp.setAnimationListener(listener);
		animationUp.setDuration(TIME_UP_DOWN_ANIMATION);
		rlContact.startAnimation(animationUp);
		
		//up
		TranslateAnimation animationUp1 = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, 
                TranslateAnimation.RELATIVE_TO_SELF, 0, 
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, -1);
                //TranslateAnimation.RELATIVE_TO_SELF, -Math.abs(tag1-tag));
		animationUp1.setDuration(TIME_UP_DOWN_ANIMATION);
		animationUp1.setFillBefore(true);
		rlSearch.startAnimation(animationUp1);
	}
    private class ScaleAnimationListener implements AnimationListener{

    	private View v;
    	public ScaleAnimationListener(View v){
    		this.v = v;
    	}
		@Override
		public void onAnimationEnd(Animation animation) {
			if (v!=null){
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
}
