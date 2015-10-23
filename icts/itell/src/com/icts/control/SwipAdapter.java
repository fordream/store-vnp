package com.icts.control;

import java.util.List;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.icts.utils.Constant;
import com.icts.utils.CountTime;
import com.icts.utils.Utils;


public class SwipAdapter<T> extends ArrayAdapter<T>{

	private int marginLeft;
	private int marginRight;
    //android:tag="listviewitemup"
    public static int TIME_SLIDE_ANIMATION = 500;
    public static String TAG_UP = "listviewitemup";
    public static int LEFT = -1;
    public static int RIGHT = 1;
    private boolean busy = false;
    private OnItemSlideRightListener onItemSlideRightListener;
    private OnItemSlideLeftListener onItemSlideLeftListener;
    private OnItemSlideChangedListener onItemSlideChangedListener;
    public SwipAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        marginRight= Utils.convertDip2Pixel(8.0f, context);
        marginLeft= Utils.convertDip2Pixel(8.0f, context);
    }

    public SwipAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        marginRight= Utils.convertDip2Pixel(8.0f, context);
        marginLeft= Utils.convertDip2Pixel(8.0f, context);
    }

    public SwipAdapter(Context context, int resource, int textViewResourceId,
            List<T> objects) {
        super(context, resource, textViewResourceId, objects);
        marginRight= Utils.convertDip2Pixel(8.0f, context);
        marginLeft= Utils.convertDip2Pixel(8.0f, context);
    }

    public SwipAdapter(Context context, int resource, int textViewResourceId,
            T[] objects) {
        super(context, resource, textViewResourceId, objects);
        marginRight= Utils.convertDip2Pixel(8.0f, context);
        marginLeft= Utils.convertDip2Pixel(8.0f, context);
    }

    public SwipAdapter(Context context, int textViewResourceId, List<T> objects) {
        super(context, textViewResourceId, objects);
        marginRight= Utils.convertDip2Pixel(8.0f, context);
        marginLeft= Utils.convertDip2Pixel(8.0f, context);
    }

    public SwipAdapter(Context context, int textViewResourceId, T[] objects) {
        super(context, textViewResourceId, objects);
        marginRight= Utils.convertDip2Pixel(8.0f, context);
        marginLeft= Utils.convertDip2Pixel(8.0f, context);
    }

    public void setLeftMargin(int left){
    	marginLeft = left;
    }
    public void setRightMargin(int right){
    	marginRight = right;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        if(v != null){
           handleEvent(v);
        }
        return v;
    }
    
    /**
     * create slide animation 
     * @param v
     */
    protected void handleEvent(View v){
    	v.setOnTouchListener(new OnTouchListener() {
            private float xStart;
            private int direction = 0;
            View itemUp = null;
            private boolean slideFinish = false;
            public boolean onTouch(final View v, MotionEvent event) {
                if(busy)return false;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    direction = 0;
                    if(itemUp == null) itemUp = v.findViewWithTag(SwipAdapter.TAG_UP);
                    if(itemUp == null){
                    }else{
                        xStart = event.getX();
                    }
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    float offsetX = event.getX() - xStart;
                    if(offsetX > 0){
                        if(direction <= 0 && onItemSlideRightListener != null){
                            direction = RIGHT;
                            onItemSlideRightListener.onItemSlideRight(v);
                        }
                        
                    }else if(offsetX < 0){
                        if(direction >=0 && onItemSlideLeftListener != null){
                            direction = LEFT;
                            onItemSlideLeftListener.onItemSlideLeft(v);
                        }
                    }else{
                        direction = 0;
                    }
                    RelativeLayout.LayoutParams params = (LayoutParams) itemUp.getLayoutParams();
                    params.leftMargin = (int) offsetX;
                    params.rightMargin = (int) -offsetX;
                    itemUp.setLayoutParams(params);
                }else if(event.getAction() == MotionEvent.ACTION_OUTSIDE || 
                        event.getAction() == MotionEvent.ACTION_CANCEL ||
                        event.getAction() == MotionEvent.ACTION_UP){
                    final RelativeLayout.LayoutParams params = (LayoutParams) itemUp.getLayoutParams();
                    final int leftMargin = params.leftMargin;
                    final int rightMargin = params.rightMargin;
                    Animation slideBackAnimation = new Animation(){
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            if(direction == RIGHT && Math.abs(leftMargin) > Constant.SCREEN_WIDTH/3){
                                params.leftMargin = (int) (leftMargin+(Constant.SCREEN_WIDTH - leftMargin) * interpolatedTime);
                                params.rightMargin = -params.leftMargin;
                                this.setDuration(Math.abs(Constant.SCREEN_WIDTH - leftMargin)*TIME_SLIDE_ANIMATION/Constant.SCREEN_WIDTH);
                                slideFinish = true;
                            }else if(direction == LEFT && Math.abs(leftMargin) > Constant.SCREEN_WIDTH/3){
                                params.leftMargin = (int) (leftMargin - (Constant.SCREEN_WIDTH + leftMargin) * interpolatedTime);
                                params.rightMargin = -params.leftMargin;
                                this.setDuration(Math.abs(Constant.SCREEN_WIDTH + leftMargin)*TIME_SLIDE_ANIMATION/Constant.SCREEN_WIDTH);
                                slideFinish = true;
                            }else{
                                params.leftMargin = marginLeft+(int) (leftMargin * (1 - interpolatedTime));
                                params.rightMargin = marginRight+(int) (rightMargin*(1 - interpolatedTime));
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
                            if(slideFinish && onItemSlideChangedListener != null){
                                itemUp.setVisibility(View.INVISIBLE);
                                onItemSlideChangedListener.onItemSlideChanged(v, direction);
                            }
                            else{
                            	final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) itemUp.getLayoutParams();
            	                params.leftMargin = marginLeft;
            	                params.rightMargin = marginRight;
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
    
    /**
     * @return the busy
     */
    public boolean isBusy() {
        return busy;
    }

    /**
     * @param busy the busy to set
     */
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    
    public void resetViewFirstPosition(View v){
    	View itemUp = v.findViewWithTag(SwipAdapter.TAG_UP);
    	final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) itemUp.getLayoutParams();
    	params.leftMargin = marginLeft;
        params.rightMargin = marginRight;
        itemUp.setLayoutParams(params);
        itemUp.requestLayout();
        itemUp.bringToFront();
    }
    /**
     * @param onItemSlideRightListener the onItemSlideRightListener to set
     */
    public void setOnItemSlideRightListener(OnItemSlideRightListener onItemSlideRightListener) {
        this.onItemSlideRightListener = onItemSlideRightListener;
    }

    /**
     * @param onItemSlideLeftListener the onItemSlideLeftListener to set
     */
    public void setOnItemSlideLeftListener(OnItemSlideLeftListener onItemSlideLeftListener) {
        this.onItemSlideLeftListener = onItemSlideLeftListener;
    }

    /**
     * @param onItemSlideChangedListener the onItemSlideChangedListener to set
     */
    public void setOnItemSlideChangedListener(OnItemSlideChangedListener onItemSlideChangedListener) {
        this.onItemSlideChangedListener = onItemSlideChangedListener;
    }
    /**
     * Interface that fire event when item list is touched
     */
    // Some listener from item slide and more
    
    public interface OnItemSlideRightListener{
        public abstract void onItemSlideRight(View v);
    }
    
    public interface OnItemSlideLeftListener{
        public abstract void onItemSlideLeft(View v);
    }
    
    
    public interface OnItemSlideChangedListener{
        public abstract void onItemSlideChanged(View v, int direction);
    }
}
