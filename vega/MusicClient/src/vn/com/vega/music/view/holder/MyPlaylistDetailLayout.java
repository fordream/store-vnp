package vn.com.vega.music.view.holder;

import vn.com.vega.chacha.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class MyPlaylistDetailLayout extends RelativeLayout {
	public MyPlaylistDetailLayout(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.layout_activity_myplaylist_detail, this);
	}

	public interface Listener {
		public void onSoftKeyboardShown(boolean isShowing);
	}

	private Listener listener;

	public void setSoftKeyboardListener(Listener listener) {
		this.listener = listener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int proposedheight = MeasureSpec.getSize(heightMeasureSpec);
		final int actualHeight = getHeight();
		if (actualHeight > proposedheight) {
			// Keyboard is showing
			if (listener != null)
				listener.onSoftKeyboardShown(true);
		} else {
			// Keyboard is hidden
			if (listener != null)
				listener.onSoftKeyboardShown(false);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
