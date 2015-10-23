package com.cnc.timtalk.ui;

import com.cnc.timtalk.ui.widget.ActionBar;

import android.app.Activity;
import android.os.Bundle;

public class ChatsActivity extends Activity {

	ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chats);
		mActionBar = (ActionBar) findViewById(R.id.actionbar);
		mActionBar.setTitle("Chats");
	}

}
