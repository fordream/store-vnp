package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.TrackerGoogle;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ContactUsViewControl extends LinearLayout {
	private EditText etTo;
	private EditText etCC;
	private EditText etSB;

	private EditText etContact;

	private Button btnClearContact;
	private Button btnSendContact;
Context context;
	public ContactUsViewControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		config();
	}

	public ContactUsViewControl(Context context) {
		super(context);
		this.context = context;
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.contactus, this);
		new AsyncTask<String, String, String>() {
			protected String doInBackground(String... params) {
				TrackerGoogle.homeTracker(context, "/faleconosco");
				return null;
			}
		}.execute("");
		etCC = (EditText) findViewById(R.id.etCC);
		etContact = (EditText) findViewById(R.id.etContact);
		etSB = (EditText) findViewById(R.id.etSB);
		etTo = (EditText) findViewById(R.id.etTo);

		btnClearContact = (Button) findViewById(R.id.btnClearContact);
		btnSendContact = (Button) findViewById(R.id.btnSendContact);

		btnClearContact.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				etContact.setText("I have feedback on your app:");
			}
		});

		btnSendContact.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);

				String[] recipients = new String[] { etTo.getText().toString(),
						etCC.getText().toString(), };

				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						recipients);

				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, etSB
						.getText().toString());

				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						etContact.getText().toString());

				emailIntent.setType("text/plain");

				getContext().startActivity(
						Intent.createChooser(emailIntent, "Send mail..."));

			}
		});
	}
}