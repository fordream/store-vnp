package com.vnp.shortfirmfestival_rework.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.vnp.shortfirmfestival_rework.R;

//com.vnp.shortfirmfestival_rework.view.HeaderView
public class ShareView extends LinearLayout implements OnClickListener {
	private View list_footer;

	public ShareView(Context context) {
		super(context);

		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.share_menu, this);
		list_footer = findViewById(R.id.share);

		findViewById(R.id.share).setOnClickListener(this);
	}

	public ShareView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private ContentValues contentValues;

	public void setContentValues(ContentValues contentValues) {
		this.contentValues = contentValues;
	}

	@Override
	public void onClick(View v) {
		String _type = contentValues.getAsString("_type");
		String title = Html.fromHtml(contentValues.getAsString("title")).toString();
		String shortdesc = Html.fromHtml(contentValues.getAsString("shortdesc")).toString();
		if ("theater".equals(_type)) {
			shortdesc = contentValues.getAsString("url");
		}

		// shortdesc = contentValues.getAsString("url");

		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		intent.setType("text/plain");
		// intent.setType("image/*");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.putExtra(Intent.EXTRA_TEXT, shortdesc);

		Bundle params = new Bundle();
		params.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		params.putString("caption", "Build great social apps and get more installs.");
		intent.putExtras(params);

		getContext().startActivity(Intent.createChooser(intent, "How do you want to share?"));

		// Intent intentShareFacebook = new Intent(Intent.ACTION_VIEW,
		// Uri.parse("https://m.facebook.com/sharer.php?u=mytext&soft=composer"));
		// getContext().startActivity(intentShareFacebook);
	}
}
