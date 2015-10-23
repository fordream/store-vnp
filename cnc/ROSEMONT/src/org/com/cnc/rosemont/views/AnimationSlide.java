package org.com.cnc.rosemont.views;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationSlide {
	public Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
		Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
		Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		outtoLeft.setDuration(400);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
		}
	public  Animation inFromRightAnimation() {
		 
		Animation inFromRight = new TranslateAnimation(
		Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
		Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		inFromRight.setDuration(400);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
		}
}
