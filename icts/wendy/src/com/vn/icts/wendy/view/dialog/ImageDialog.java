package com.vn.icts.wendy.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.vn.icts.wendy.R;
import com.vn.icts.wendy.view.TopBarView;

public class ImageDialog extends Dialog implements android.view.View.OnClickListener {

	public ImageDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		setCancelable(false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.img_dialog_layout);
		TopBarView barView = (TopBarView)findViewById(R.id.dialog_img_topbar);
		barView.showRip();
		barView.setOnClickRight(this);
		barView.setTitle(R.string.picture);
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}
}