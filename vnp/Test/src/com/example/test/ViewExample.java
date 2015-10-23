package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.vnp.core.service.v2.CommonV2RestClientServiceObserver;
import com.vnp.core.service.v2.ResponseInfor;

public class ViewExample extends com.ict.library.view.CustomLinearLayoutView {

	public ViewExample(Context context) {
		super(context);
		setWillNotDraw(false);
	}

	public ViewExample(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
	}

	long ind = 0;
	Paint paint = new Paint();

	private ResponseInfor responseInfor;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		ind++;

		if (ind > 10000)
			ind = 0;

		if (responseInfor == null)
			responseInfor = CommonV2RestClientServiceObserver.getInStance()
					.getResponseInfor(idCallBack);
		if (responseInfor != null) {
			if (responseInfor.getTotalSize() > 0)
				canvas.drawText(((float) responseInfor.getCurentSize()
						/ (float) (responseInfor.getTotalSize()) * 100)
						+ "%", 100, 100, paint);
			else{
				canvas.drawText("wait", 100, 100, paint);
			}
		}else{
			canvas.drawText("null", 100, 100, paint);
		}

		invalidate();
	}

	@Override
	public void setGender() {

	}

	String idCallBack;

	public void setIdData(String id) {
		idCallBack = id;
	}

}
