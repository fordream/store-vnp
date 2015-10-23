package vn.vvn.bibook.item;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class NewScrollView extends ScrollView {

	private boolean mIsBottom;

	private ScrollViewListener scrollViewListener = null;

	public NewScrollView(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		mIsBottom = false;
	}

	public NewScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mIsBottom = false;
	}

	public NewScrollView(Context context) {
		super(context);
		mIsBottom = false;
	}

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (!mIsBottom) {
			View view = (View) getChildAt(getChildCount() - 1);
			int diff = (view.getBottom() - (getHeight() + getScrollY()));// Calculate
																			// the
																			// scrolldiff
			if (diff == 0) {
				mIsBottom = true;
			}
		}
		super.onScrollChanged(l, t, oldl, oldt);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(t);
		}
	}

	public boolean ismIsBottom() {
		return mIsBottom;
	}

	public void setmIsBottom(boolean mIsBottom) {
		this.mIsBottom = mIsBottom;
	}

}
