/**
 * 
 */
package com.vn.icts.wendy.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ict.library.view.CustomLinearLayoutView;
import com.vn.icts.wendy.R;

/**
 * @author tvuong1pc
 * 
 */
public class TopBarView extends CustomLinearLayoutView implements
		OnClickListener {
	private RelativeLayout menu_home;
	private ProgressBar progressBar;
	private ImageView topBarRip;
	private ImageView topbar_button_right;

	/**
	 * @param context
	 */
	public TopBarView(Context context) {
		super(context);
		init(R.layout.topbar);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TopBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.topbar);
	}

	@Override
	public void init(int res) {
		super.init(res);
		menu_home = getView(R.id.menu_home);
		// menu_home.setOnClickListener(this);

		progressBar = getView(R.id.progressBar1);
		showProgressBar(false);

		topBarRip = getView(R.id.topBar_rip);
		topbar_button_right = getView(R.id.topbar_button_right);
		this.setOnClickListener(this);
	}

	public void showProgressBar(final boolean isShow) {
		post(new Runnable() {
			public void run() {
				progressBar.setVisibility(isShow ? VISIBLE : GONE);
			}
		});
	}

	public boolean isShowProgressbar() {
		return progressBar.getVisibility() == VISIBLE;
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 == this) {
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://wendys.co.jp/wendys/index.php"));
			getContext().startActivity(intent);
		}
	}

	public void setTitle(int res) {
		TextView textView = getView(R.id.textView1);
		textView.setText(res);
	}

	public void showRip() {
		topBarRip.setVisibility(VISIBLE);
	}

	public void setOnClickRight(OnClickListener clickListener) {
		topbar_button_right.setVisibility(View.VISIBLE);
		topbar_button_right.setOnClickListener(clickListener);
	}

	public void setTitle(String name) {
		TextView textView = getView(R.id.textView1);
		textView.setText(name);
	}
}