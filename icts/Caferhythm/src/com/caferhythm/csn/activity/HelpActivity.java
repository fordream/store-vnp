package com.caferhythm.csn.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.caferhythm.csn.R;

public class HelpActivity extends BaseActivityWithHeadtab {
	// view on screen
	private TextView help01TV;
	private TextView help02TV;
	private TextView help03TV;
	private TextView help04TV;
	private TextView help05TV;
	private TextView help06TV;
	private TextView help07TV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentTab(getResources().getString(R.string.help),
				R.layout.help_screen);
		super.onCreate(savedInstanceState);
		genView();
	}

	private void genView() {
		help01TV = (TextView) findViewById(R.id.tv_help_01);
		help02TV = (TextView) findViewById(R.id.tv_help_02);
		help03TV = (TextView) findViewById(R.id.tv_help_03);
		help04TV = (TextView) findViewById(R.id.tv_help_04);
		help05TV = (TextView) findViewById(R.id.tv_help_05);
		help06TV = (TextView) findViewById(R.id.tv_help_06);
		help07TV = (TextView) findViewById(R.id.tv_help_07);

		help01TV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						HelpContent01Activity.class));
			}
		});
		help02TV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						HelpContent02Activity.class));
			}
		});
		help03TV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						HelpContent03Activity.class));
			}
		});
		help04TV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						UseInformationActivity.class));
			}
		});
		help05TV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						PrivacyPolicyActivity.class));
			}
		});
		help06TV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						HelpContent06Activity.class));
			}
		});
		help07TV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uriUrl = Uri.parse("http://smph.newscafe.ne.jp/"); 
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);  
				startActivity(launchBrowser);  
			}
		});
	}
}
