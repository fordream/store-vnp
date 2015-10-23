package com.icts.control;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.icts.itel.R;
import com.icts.utils.Constant;

public class iTellButton extends RelativeLayout {

    private ImageView imageColor;
    private long startTime;
    private boolean isRunning = false;
    private boolean run = true;
    private long maxTime = 60*1000, currentTime, anchorTime = 59*1000;
    Timer timer = new Timer(){
        
    };
    Activity parent = null;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(!run) return;
            currentTime = anchorTime - (System.currentTimeMillis() - startTime);
            if(currentTime < 0) {
                currentTime = 0;
                run = false;
            }
            final int alpha = (int) ((float)currentTime*255/maxTime);
            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageColor.setAlpha(alpha);
                }
            });
        }
    };

    public iTellButton(Context context) {
        this(context, null);
    }

    public iTellButton(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public iTellButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((Activity)this.getContext()).getLayoutInflater().inflate(R.layout.itellbutton, this, true);
        imageColor = (ImageView) findViewById(R.id.itellbuttoncolor);
        int resouceId = -1;
        TypedArray typeArray = context.obtainStyledAttributes(attrs,
                R.styleable.iTellButton);
        
        int N = typeArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = typeArray.getIndex(i);
            switch (attr) {
            case R.styleable.iTellButton_button_image:
                resouceId = typeArray.getResourceId(
                        R.styleable.iTellButton_button_image, 0);
                if (resouceId > 0)
                    imageColor.setImageResource(resouceId);
                
                break;
            }
        }
    }

    public void setColorOpacity(float opacity) {
        opacity = (opacity < 0) ? 0 : opacity;
        opacity = (opacity > 1) ? 1 : opacity;
        if (imageColor == null)
            return;
        imageColor.setAlpha((int) (opacity * 255));
    }
    
    public void setMaxTime(long time){
        maxTime = time;
    }
    
    public void setCurrentTime(long time){
        currentTime = time;
        anchorTime = time;
    }
    
    public long getCurrentTime(){
        return currentTime;
    }

    public void startCountDown(){
        if(parent == null){
            parent = (Activity)this.getContext();
        }
        if(maxTime <= 0 || currentTime < 0)
            throw new IllegalArgumentException("Check your max time or your current time of iTellButton is less than 0");
        if(maxTime < currentTime) currentTime = maxTime;
        startTime = System.currentTimeMillis();
        if(!isRunning){
            timer.schedule(task, 0, Constant.INTERVAL_ITELL_BUTTON);
            isRunning = true;
        }
        run = true;
    }
    
    public void stopCountDown(){
        run = false;
    }
    
    public void setButtonImageDrawable(Drawable drawable){
        imageColor.setImageDrawable(drawable);
    }
    
    public void setButtonImageResource(int resId){
        imageColor.setImageResource(resId);
    }
}
