package org.cnc.qrcode.views;

import org.cnc.qrcode.GlobalActivity;
import org.cnc.qrcode.R;
import org.cnc.qrcode.common._Return;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemHistoryView extends LinearLayout {

	public ItemHistoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public ItemHistoryView(Context context) {
		super(context);

		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.item_history, this);
	}

	public void setData(_Return return1) {
		String text = GlobalActivity.questionContent;
		((TextView) findViewById(R.id.textView1)).setText(text);
		((TextView) findViewById(R.id.textView2)).setText(return1.getTime());
	}

	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		findViewById(R.id.id_ll).setLayoutParams(params);
	}
}