package com.icts.control;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

/**
 * Class ListView that show item to swip
 * 
 *
 */
public class SwipListView extends ListView{

    
    public static final int NUMBER_ITEM_SWIP = 5;
    private OnSetFinishSwip finishSwip;
    public SwipListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public SwipListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public SwipListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public void setFinishSwip(OnSetFinishSwip onSetFinish){
    	this.finishSwip = onSetFinish;
    }
    public void swip(int index,int direction){
        if(index < 0 || index >= this.getCount()){
            throw new IllegalArgumentException("the index of item is not in the listview");
        }
        int startSwip = 0;
        int endSwip = this.getCount() - 1;
        if(index > NUMBER_ITEM_SWIP) startSwip = index - NUMBER_ITEM_SWIP;
        if(index + NUMBER_ITEM_SWIP < endSwip) endSwip = index + NUMBER_ITEM_SWIP;
        int offsetPosition = this.getFirstVisiblePosition() - this.getHeaderViewsCount();
        int swipUp = index - offsetPosition;
        int swipDown = this.getLastVisiblePosition() - this.getHeaderViewsCount() - index;
        int swip =swipUp+swipDown; //Math.max(swipUp, swipDown);
        int time = 2000*swip/NUMBER_ITEM_SWIP;
        Log.d("hieuth", "LISTVIEW: "+this.getFirstVisiblePosition());
        TranslateAnimation animationUp = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, 
                TranslateAnimation.RELATIVE_TO_SELF, 0, 
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, -1);
                //TranslateAnimation.RELATIVE_TO_SELF, -swip);
        animationUp.setDuration(time);
        SwipListener a = new SwipListener(finishSwip,direction,index);
        boolean isSetListener = false;
        animationUp.setAnimationListener(a);
        for(int i = startSwip; i <= index; i++){
            int pos = i - offsetPosition;
            if(pos < 0 || pos>this.getCount())continue;
            View v = this.getChildAt(pos);
            if(v != null){
                v.startAnimation(animationUp);
                isSetListener = true;
            }
        }
        
        TranslateAnimation animationDown = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, 
                TranslateAnimation.RELATIVE_TO_SELF, 0, 
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, 1);
                //TranslateAnimation.RELATIVE_TO_SELF, swip);
        animationDown.setDuration(time);
        if (!isSetListener){
        	animationDown.setAnimationListener(a);
        }
        for(int i = index + 1; i <= endSwip; i++){
            int pos = i - offsetPosition;
            if(pos < 0 || pos>this.getCount())continue;
            View v = this.getChildAt(pos);
            if(v != null){
                v.startAnimation(animationDown);
            }
        }
        
    }
    
    public interface OnSetFinishSwip{
    	public abstract void onFinishSwip(int direction,int index);
    }
    public class SwipListener implements AnimationListener{
    	private int direction;
    	private int index;
    	private OnSetFinishSwip finish;
    	public SwipListener(OnSetFinishSwip finish,int direction, int index){
    		this.finish = finish;
    		this.direction = direction;
    		this.index = index;
    	}
    	public void onAnimationEnd(Animation animation) {
    		finish.onFinishSwip(direction,index);
    		/*if (this.v!=null){
    			this.v.setBackgroundResource(android.R.color.transparent);
    		}*/
    	}
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
    	
    }
}
