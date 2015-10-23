package org.com.vnp.vanga.view;

import org.com.vnp.vanga.VangaApplication;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

public class DownloadProgessBar extends ProgressBar {

	public DownloadProgessBar(Context context) {
		super(context);
		init();
	}

	public DownloadProgessBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DownloadProgessBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setWillNotDraw(false);
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (getContext().getApplicationContext() instanceof VangaApplication) {
			VangaApplication application = (VangaApplication) getContext()
					.getApplicationContext();

			if (application.isDownload()) {
				setVisibility(View.VISIBLE);
			} else {
				setVisibility(View.GONE);
			}
		}

		invalidate();
	}
}