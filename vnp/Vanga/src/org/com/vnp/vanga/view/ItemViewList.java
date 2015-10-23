package org.com.vnp.vanga.view;

import org.com.vnp.vanga.R;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.ict.library.common.CommonResize;
import com.ict.library.view.CustomLinearLayoutView;

public class ItemViewList extends CustomLinearLayoutView implements Runnable {

	public ItemViewList(Context context) {
		super(context);
		init(R.layout.item_listview);
		post(this);
	}

	public ItemViewList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.item_listview);
		post(this);
	}

	@Override
	public void init(int res) {
		super.init(res);
		setWillNotDraw(false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		CommonResize._20130408_resizeW960(findViewById(R.id.idmain), 960, 300);
		CommonResize._20130408_resizeW960(findViewById(R.id.imageView1), 280,
				280);
		CommonResize._20130408_resizeW960(findViewById(R.id.resizeTextView1),
				700, 280);
		
		invalidate();
	}

	public void run() {

	}

	@Override
	public void setGender() {
		post(this);
	}
}