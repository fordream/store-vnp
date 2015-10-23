package vn.com.vega.music.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class SegmentedControlButton extends RadioButton {

	private float mX;

	public SegmentedControlButton(Context context) {
		super(context);
	}

	public SegmentedControlButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SegmentedControlButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		String text = this.getText().toString();
		Paint textPaint = new Paint();
		textPaint.setAntiAlias(true);

		float currentWidth = textPaint.measureText(text);
		float currentHeight = textPaint.measureText("A");

		textPaint.setTextSize(this.getTextSize());
		textPaint.setTextAlign(Paint.Align.CENTER);

		if (isChecked()) {
			textPaint.setColor(Color.WHITE);
		} else {
			textPaint.setColor(Color.LTGRAY);
		}

		float w = (this.getWidth() / 2) - currentWidth;
		float h = (this.getHeight() / 2) + currentHeight;

		canvas.drawText(text, mX, h, textPaint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int ow, int oh) {
		super.onSizeChanged(w, h, ow, oh);
		mX = w * 0.5f; // remember the center of the screen
	}
}
