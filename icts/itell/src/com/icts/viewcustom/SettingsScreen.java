package com.icts.viewcustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.icts.control.SwipAdapter;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.utils.Constant;

public class SettingsScreen {
	private final int TIME_SLIDE_ANIMATION = 500;
	private final int TIME_UP_DOWN_ANIMATION = 500;
	private Context mContext;
	private MainActivity mainActivity;
	private LayoutInflater layoutInflater;
	private View mView = null;
	//private LinearLayout rlProfile;
	private RelativeLayout rlProfile, rlPush, rlPrivate,rlHelp,rlAbout,rlRegister;
	public SettingsScreen(Context context, MainActivity mActivity) {
		mContext = context;
		mainActivity = mActivity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.layout_setting, null);
		initViewComponent();
	}

	private void initViewComponent() {
		rlProfile = (RelativeLayout) mView.findViewById(R.id.relativeLayout2);
		rlPrivate = (RelativeLayout) mView.findViewById(R.id.relativeLayout3);
		rlPush = (RelativeLayout) mView.findViewById(R.id.relativeLayout4);
		rlHelp = (RelativeLayout) mView.findViewById(R.id.relativeLayout5);
		rlAbout = (RelativeLayout) mView.findViewById(R.id.relativeLayout6);
		rlRegister = (RelativeLayout) mView.findViewById(R.id.relativeLayout7);
		
		rlRegister.setFocusable(true);
		rlRegister.setFocusableInTouchMode(true);
		handleEvent(rlRegister);
		
		rlPush.setFocusable(true);
		rlPush.setFocusableInTouchMode(true);
		handleEvent(rlPush);
		
		rlPrivate.setFocusable(true);
		rlPrivate.setFocusableInTouchMode(true);
		handleEvent(rlPrivate);
		
		rlProfile.setFocusable(true);
		rlProfile.setFocusableInTouchMode(true);
		handleEvent(rlProfile);
		
		rlHelp.setFocusable(true);
		rlHelp.setFocusableInTouchMode(true);
		handleEvent(rlHelp);
		
		rlAbout.setFocusable(true);
		rlAbout.setFocusableInTouchMode(true);
		handleEvent(rlAbout);
	}

	private OnClickListener onClickComponent = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.relativeLayout2:
				// test upload avatar
				ProfileSetting profile = new ProfileSetting(mContext,
						mainActivity);
				mainActivity.changeViewLL(profile.getmView());
				//Utils.animation(profile.getmView(),TIME_SLIDE_ANIMATION);
				break;
			case R.id.relativeLayout3:
				PrivateManagement pri = new PrivateManagement(mContext,
						mainActivity);
				mainActivity.changeViewLL(pri.getmView());
				//Utils.animation(pri.getmView(),TIME_SLIDE_ANIMATION);
				break;
			case R.id.relativeLayout4:
				PushSetting push = new PushSetting(mContext, mainActivity);
				mainActivity.changeViewLL(push.getmView());
				//Utils.animation(push.getmView(),TIME_SLIDE_ANIMATION);
				break;
			case R.id.relativeLayout5:
				HelpSetting help = new HelpSetting(mContext, mainActivity);
				mainActivity.changeViewLL(help.getmView());
				//Utils.animation(push.getmView(),TIME_SLIDE_ANIMATION);
				break;
			case R.id.relativeLayout6:
				AboutSetting about = new AboutSetting(mContext, mainActivity);
				mainActivity.changeViewLL(about.getmView());
				//Utils.animation(push.getmView(),TIME_SLIDE_ANIMATION);
				break;
			case R.id.relativeLayout7:
				RegisterSetting register = new RegisterSetting(mContext, mainActivity);
				mainActivity.changeViewLL(register.getmView());
				//Utils.animation(push.getmView(),TIME_SLIDE_ANIMATION);
				break;
			default:
				break;
			}
		}
	};

	private View nextView = null;
	private void upDownAnimation(View v){
		v.setVisibility(View.GONE);
		switch (v.getId()) {
		case R.id.relativeLayout2:
			ProfileSetting profile = new ProfileSetting(mContext,
					mainActivity);
			nextView = profile.getmView();
			break;
		case R.id.relativeLayout3:
			PrivateManagement pri = new PrivateManagement(mContext,
					mainActivity);
			nextView =pri.getmView(); 
			break;
		case R.id.relativeLayout4:
			PushSetting push = new PushSetting(mContext, mainActivity);
			nextView =push.getmView();
			break;
		case R.id.relativeLayout5:
			HelpSetting help = new HelpSetting(mContext, mainActivity);
			nextView = help.getmView();
			break;
		case R.id.relativeLayout6:
			AboutSetting about = new AboutSetting(mContext, mainActivity);
			nextView = about.getmView();
			break;
		case R.id.relativeLayout7:
			RegisterSetting reg = new RegisterSetting(mContext, mainActivity);
			nextView = reg.getmView();
			break;
		}
		
		/*if (nextView!=null){
			int h = v.getLayoutParams().height;
			RelativeLayout rl1 =(RelativeLayout) v.findViewWithTag("setting_back");
			LinearLayout rl2 =(LinearLayout) v.findViewWithTag("item_move");
			((RelativeLayout)v).removeView(rl1);
			((RelativeLayout)v).removeView(rl2);
			((RelativeLayout)v).requestLayout();
			LinearLayout ll =(LinearLayout) v.findViewWithTag("next_view");
			RelativeLayout.LayoutParams para = new LayoutParams(LayoutParams.FILL_PARENT, h);
			ll.addView(nextView,para);
		}*/
		
		int tag =Integer.parseInt(v.getTag().toString());
		boolean c= false;
		if (rlProfile.getId()!=v.getId()){
			c = true;
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
			rlProfile.startAnimation(animationUp);
		}
		if (rlRegister.getId()!=v.getId()){
			int tag1 =Integer.parseInt(rlRegister.getTag().toString());
			if (tag1>tag){
				//up
				TranslateAnimation animationDown = new TranslateAnimation(
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0,
		                TranslateAnimation.RELATIVE_TO_PARENT, 1);
				animationDown.setDuration(TIME_UP_DOWN_ANIMATION);
				if (!c){
					ScaleAnimationListener listener = new ScaleAnimationListener(v);
					animationDown.setFillBefore(true);
					animationDown.setAnimationListener(listener);
				}
				c = true;
				rlRegister.startAnimation(animationDown);
				
			}
			else {
				//up
				TranslateAnimation animationUp = new TranslateAnimation(
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0,
		                TranslateAnimation.RELATIVE_TO_PARENT, -1);
				animationUp.setDuration(TIME_UP_DOWN_ANIMATION);
				if (!c){
					ScaleAnimationListener listener = new ScaleAnimationListener(v);
					animationUp.setFillBefore(true);
					animationUp.setAnimationListener(listener);
				}
				rlRegister.startAnimation(animationUp);
				c = true;
				
			}
		}
		if (rlPrivate.getId()!=v.getId()){
			int tag1 =Integer.parseInt(rlPrivate.getTag().toString());
			if (tag1>tag){
				//up
				TranslateAnimation animationDown = new TranslateAnimation(
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0,
		                TranslateAnimation.RELATIVE_TO_PARENT, 1);
		                //TranslateAnimation.RELATIVE_TO_SELF, Math.abs(tag1-tag));
				animationDown.setDuration(TIME_UP_DOWN_ANIMATION);
				if (!c){
					ScaleAnimationListener listener = new ScaleAnimationListener(v);
					animationDown.setFillBefore(true);
					animationDown.setAnimationListener(listener);
				}
				c = true;
				rlPrivate.startAnimation(animationDown);
				
			}
			else {
				//up
				TranslateAnimation animationUp = new TranslateAnimation(
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0,
		                TranslateAnimation.RELATIVE_TO_PARENT, -1);
		                //TranslateAnimation.RELATIVE_TO_SELF, -Math.abs(tag1-tag));
				animationUp.setDuration(TIME_UP_DOWN_ANIMATION);
				if (!c){
					ScaleAnimationListener listener = new ScaleAnimationListener(v);
					animationUp.setFillBefore(true);
					animationUp.setAnimationListener(listener);
				}
				rlPrivate.startAnimation(animationUp);
				c = true;
				
			}
		}
		if (rlPush.getId()!=v.getId()){
			int tag1 =Integer.parseInt(rlPrivate.getTag().toString());
			if (tag1>tag){
				//down
				TranslateAnimation animationDown = new TranslateAnimation(
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0,
		                TranslateAnimation.RELATIVE_TO_PARENT, 1);
		                //TranslateAnimation.RELATIVE_TO_SELF, Math.abs(tag1-tag));
				animationDown.setDuration(TIME_UP_DOWN_ANIMATION);
				if (!c){
					ScaleAnimationListener listener = new ScaleAnimationListener(v);
					animationDown.setFillBefore(true);
					animationDown.setAnimationListener(listener);
				}
				c = true;
				rlPush.startAnimation(animationDown);
			}
			else {
				//up
				TranslateAnimation animationUp = new TranslateAnimation(
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0,
		                TranslateAnimation.RELATIVE_TO_PARENT, -1);
		                //TranslateAnimation.RELATIVE_TO_SELF, -Math.abs(tag1-tag));
				animationUp.setDuration(TIME_UP_DOWN_ANIMATION);
				if (!c){
					ScaleAnimationListener listener = new ScaleAnimationListener(v);
					animationUp.setFillBefore(true);
					animationUp.setAnimationListener(listener);
				}
				c = true;
				rlPush.startAnimation(animationUp);
				
			}
		}
		
		if (rlHelp.getId()!=v.getId()){
			int tag1 =Integer.parseInt(rlHelp.getTag().toString());
			if (tag1>tag){
				//down
				TranslateAnimation animationDown = new TranslateAnimation(
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0,
		                TranslateAnimation.RELATIVE_TO_PARENT, 1);
		                //TranslateAnimation.RELATIVE_TO_SELF, Math.abs(tag1-tag));
				animationDown.setDuration(TIME_UP_DOWN_ANIMATION);
				if (!c){
					ScaleAnimationListener listener = new ScaleAnimationListener(v);
					animationDown.setFillBefore(true);
					animationDown.setAnimationListener(listener);
				}
				c = true;
				rlHelp.startAnimation(animationDown);
			}
			else {
				//up
				TranslateAnimation animationUp = new TranslateAnimation(
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_SELF, 0, 
		                TranslateAnimation.RELATIVE_TO_PARENT, -1);
		                //TranslateAnimation.RELATIVE_TO_SELF, -Math.abs(tag1-tag));
				animationUp.setDuration(TIME_UP_DOWN_ANIMATION);
				if (!c){
					ScaleAnimationListener listener = new ScaleAnimationListener(v);
					animationUp.setFillBefore(true);
					animationUp.setAnimationListener(listener);
				}
				c = true;
				rlHelp.startAnimation(animationUp);
				
			}
		}
		
		if (rlAbout.getId()!=v.getId()){
			int tag1 =Integer.parseInt(rlPrivate.getTag().toString());
			//down
			TranslateAnimation animationDown = new TranslateAnimation(
	                TranslateAnimation.RELATIVE_TO_SELF, 0, 
	                TranslateAnimation.RELATIVE_TO_SELF, 0, 
	                TranslateAnimation.RELATIVE_TO_SELF, 0, 
	                TranslateAnimation.RELATIVE_TO_PARENT, 1);
	                //TranslateAnimation.RELATIVE_TO_SELF, Math.abs(tag1-tag));
			animationDown.setDuration(TIME_UP_DOWN_ANIMATION);
			if (!c){
				ScaleAnimationListener listener = new ScaleAnimationListener(v);
				animationDown.setFillBefore(true);
				animationDown.setAnimationListener(listener);
			}
			c = true;
			rlAbout.startAnimation(animationDown);
		}
		
		/*if (nextView!=null){
			nextView.setDrawingCacheEnabled(true);
			ImageView img =(ImageView) v.findViewWithTag("next_view");
			Bitmap bitmap = nextView.getDrawingCache();
			img.setImageBitmap(bitmap);
			ScaleAnimation animationScale = new ScaleAnimation(1,1,0.1f,1);
			animationScale.setDuration(TIME_UP_DOWN_ANIMATION);
			img.setAnimation(animationScale);
		}*/
	}
	private void slideAnimation(View v){
		Animation mAnimation = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
	            TranslateAnimation.RELATIVE_TO_PARENT, -1.0f,
	            TranslateAnimation.ABSOLUTE, 0f,
	            TranslateAnimation.ABSOLUTE, 0f);
		ScaleAnimationListener listener = new ScaleAnimationListener(v);
		mAnimation.setDuration(TIME_SLIDE_ANIMATION);
		mAnimation.setFillBefore(true);
		mAnimation.setAnimationListener(listener);
		if (rlProfile.getId()!=v.getId()){
			rlProfile.startAnimation(mAnimation);
		}
		if (rlPrivate.getId()!=v.getId()){
			rlPrivate.startAnimation(mAnimation);
		}
		if (rlPush.getId()!=v.getId()){
			rlPush.startAnimation(mAnimation);
		}
		
		if (rlHelp.getId()!=v.getId()){
			rlHelp.startAnimation(mAnimation);
		}
		
		if (rlAbout.getId()!=v.getId()){
			rlAbout.startAnimation(mAnimation);
		}
	}
	
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
            private boolean slideFinish = false;
            public boolean onTouch(final View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    direction = 0;
                    itemUp = v.findViewWithTag("item_move");
                    xStart = event.getX();
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    float offsetX = event.getX() - xStart;
                    if(offsetX < 0){
                        if(direction ==0 ){
                            direction = SwipAdapter.LEFT;
                            //onItemSlideLeftListener.onItemSlideLeft(v);
                        }
                    }
                    else{
                        direction = 0;
                    }
                    if (offsetX<0){
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
                                itemUp.setVisibility(View.INVISIBLE);
                                //onItemSlideChangedListener.onItemSlideChanged(v, direction);
                                //slideAnimation(v);
                                upDownAnimation(v);
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
    private class ScaleAnimationListener implements AnimationListener{

    	private View v;
    	public ScaleAnimationListener(View v){
    		this.v = v;
    	}
		@Override
		public void onAnimationEnd(Animation animation) {
			if (v!=null){
				//onClickComponent.onClick(v);
				mainActivity.changeViewLL(nextView);
				//Utils.animation(nextView, TIME_SLIDE_ANIMATION);
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
