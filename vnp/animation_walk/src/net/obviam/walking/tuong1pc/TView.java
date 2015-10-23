package net.obviam.walking.tuong1pc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class TView extends View {
	Wing wing;

	public TView(Context context) {
		super(context);
		Wing.init(getContext());
		wing = new Wing(getContext(), null);
		setWillNotDraw(false);
	}

	protected void onDraw(android.graphics.Canvas canvas) {
		canvas.drawBitmap(wing.getBitmap(), 10, 10, new Paint());
		invalidate();
	}

}