package com.icts.viewcustom;

import java.util.StringTokenizer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.database.CheckUser;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.utils.Constant;
import com.icts.utils.Utils;

public class PushSetting {
	private final String TAG = "item_toggle";
	private final int TIME_SLIDE_ANIMATION = 500;
	private final int TIME_UP_DOWN_ANIMATION = 1000;
	private Context mContext;
	private MainActivity mainActivity;
	private LayoutInflater layoutInflater;
	private View mView = null;
	private RelativeLayout rlAddFriend,rlFriend,rlItell;
	private TextView tvAddFriend,tvFriend,tvItell;
	private ImageView imgAddFriend,imgFriend,imgItell;
	private Button btnBack;
	private CheckUser check;
	private boolean isAdd = false,isFriend = false, isItell = false;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what==3){
				//Toast.makeText(mContext, "Update succeed", Toast.LENGTH_LONG).show();
			}
			else if (msg.what==4){
				Toast.makeText(mContext, "Error when updating", Toast.LENGTH_LONG).show();
			}
		};
	};
	public PushSetting(Context context, MainActivity mActivity) {
		mContext = context;
		mainActivity = mActivity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.layout_push_notification, null);
		check = new CheckUser(mContext.getApplicationContext());
		initViewComponent();
	}

	void initViewComponent() {
		rlAddFriend = (RelativeLayout) mView.findViewById(R.id.push_rl_add);
		rlFriend = (RelativeLayout) mView.findViewById(R.id.push_rl_friend);
		rlItell = (RelativeLayout) mView.findViewById(R.id.push_rl_iTell);
		
		tvAddFriend = (TextView) mView.findViewById(R.id.push_add_friend_text_right);
		tvFriend = (TextView) mView.findViewById(R.id.push_friend_text_right);
		tvItell = (TextView) mView.findViewById(R.id.push_itell_text_right);
		
		imgAddFriend = (ImageView) mView.findViewById(R.id.imgAddFriend);
		imgFriend = (ImageView) mView.findViewById(R.id.imgFriend);
		imgItell = (ImageView) mView.findViewById(R.id.imgItell);
		
		btnBack = (Button) mView.findViewById(R.id.btnPushBack);
		btnBack.setOnClickListener(onClickComponent);
		
		rlFriend.setFocusable(true);
		rlFriend.setFocusableInTouchMode(true);
		handleEvent(rlFriend);
		
		rlAddFriend.setFocusable(true);
		rlAddFriend.setFocusableInTouchMode(true);
		handleEvent(rlAddFriend);
		
		rlItell.setFocusable(true);
		rlItell.setFocusableInTouchMode(true);
		handleEvent(rlItell);
		
		isAdd = check.getPushAdd();
		isFriend = check.getPushFriend();
		isItell = check.getPushItell();
		showData();
	}
	
	private void showData(){
		if (isAdd){
			tvAddFriend.setText("OFF");
			imgAddFriend.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
		}
		else {
			tvAddFriend.setText("ON");
			imgAddFriend.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
		}
		
		if (isFriend){
			tvFriend.setText("OFF");
			imgFriend.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
		}
		else {
			tvFriend.setText("ON");
			imgFriend.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
		}
		
		if (isItell){
			tvItell.setText("OFF");
			imgItell.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
		}
		else {
			tvItell.setText("ON");
			imgItell.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
		}
	}

	OnClickListener onClickComponent = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnPushBack:
				SettingsScreen setting = new SettingsScreen(mContext,
						mainActivity);
				mainActivity.changeViewLL(setting.getmView());
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
            View item = null;
            private boolean slideFinish = false;
            public boolean onTouch(final View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    direction = 0;
                    item = v.findViewWithTag(TAG);
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
                	final RelativeLayout.LayoutParams params = (LayoutParams) item.getLayoutParams();
                	params.leftMargin = (int) offsetX;
                	params.rightMargin = (int) -offsetX;
                	item.setLayoutParams(params);
                }else if(event.getAction() == MotionEvent.ACTION_OUTSIDE || 
                        event.getAction() == MotionEvent.ACTION_CANCEL ||
                        event.getAction() == MotionEvent.ACTION_UP){
                    final RelativeLayout.LayoutParams params = (LayoutParams) item.getLayoutParams();
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
                            item.setLayoutParams(params);
                        };
                    };
                    item.startAnimation(slideBackAnimation);
                    slideBackAnimation.setAnimationListener(new AnimationListener() {
                        
                        public void onAnimationStart(Animation animation) {
                            
                        }
                        
                        public void onAnimationRepeat(Animation animation) {
                            
                        }
                        
                        public void onAnimationEnd(Animation animation) {
                            if(slideFinish){
                            	if (direction == SwipAdapter.RIGHT){
                            		//back
                            		SettingsScreen setting = new SettingsScreen(mContext,
                    						mainActivity);
                    				mainActivity.changeViewLL(setting.getmView());
                            	}
                            	else if (direction == SwipAdapter.LEFT){
                            		mHandler.post(new Runnable() {
										
										@Override
										public void run() {
											changeText(v);
				                    		RelativeLayout.LayoutParams params = (LayoutParams) item.getLayoutParams();
				                        	params.leftMargin = 0;
				                        	params.rightMargin = 0;
				                        	item.setLayoutParams(params);
											
										}
									});
                            	}
                        	}
                            else{
                            	final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) item.getLayoutParams();
            	                params.leftMargin = 0;
            	                params.rightMargin = 0;
            	                item.setLayoutParams(params);
            	                
            	                //Hide 
            	                
                            }
                        }
                    });
                }
                return true;
            }
        });
    }
    
   
    
    private void changeText(View v){
    	if (rlAddFriend.getId()==v.getId()){
    		isAdd=!isAdd;
    		if (isAdd){
    			tvAddFriend.setText("OFF");
    			imgAddFriend.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
    		}
    		else {
    			tvAddFriend.setText("ON");
    			imgAddFriend.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
    		}
    		check.setPushAddFriend(isAdd);
    	}
    	else if (rlFriend.getId()==v.getId()){
    		isFriend=!isFriend;
    		if (isFriend){
    			tvFriend.setText("OFF");
    			imgFriend.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
    		}
    		else {
    			tvFriend.setText("ON");
    			imgFriend.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
    		}
    		check.setPushFriend(isFriend);
    	}
    	else if (rlItell.getId()==v.getId()){
    		isItell=!isItell;
    		if (isItell){
    			tvItell.setText("OFF");
    			imgItell.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.on_button));
    		}
    		else {
    			tvItell.setText("ON");
    			imgItell.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.off_button));
    		}
    		check.setPushItell(isItell);
    	}
    }
  
   
}
