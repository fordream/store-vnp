package com.icts.shortfilmfestival.push;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.icts.shortfilmfestivalJa.R;

public class MessageReceivedActivity extends Activity {
	public static int numberMessage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_result);
		Message messageObj = C2DMMessageReceiver.listMessages
				.get(numberMessage++);
		if (messageObj.getMessage() != null
				&& messageObj.getMessage().length() > 0) {
			TextView view = (TextView) findViewById(R.id.result_message);
			view.setText(messageObj.getMessage() + " with newsID : "
					+ messageObj.getNewsId() + " type: "
					+ messageObj.getTypes() + " lang: "
					+ messageObj.getLanguage() + " url : "
					+ messageObj.getUrl());
			if (C2DMMessageReceiver.listMessages.size() == numberMessage) {
				C2DMMessageReceiver.listMessages.clear();
				numberMessage = 0;
				C2DMMessageReceiver.id = 0;
				Log.d("RESET", "RESET");
			}
		}

	}
}
