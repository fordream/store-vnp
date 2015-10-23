package wyf.ytl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class WelcomeView extends LinearLayout {
	private ChessActivity activity;

	public WelcomeView(Context context, ChessActivity activity) {
		super(context);
		this.activity = activity;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.wellcomview, this);
		activity.myHandler.sendEmptyMessage(1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			activity.myHandler.sendEmptyMessage(1);
		}

		return super.onTouchEvent(event);
	}
}