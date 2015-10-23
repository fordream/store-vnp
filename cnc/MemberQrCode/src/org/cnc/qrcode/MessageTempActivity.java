package org.cnc.qrcode;

import org.cnc.qrcode.asyn.SaveHistory;
import org.cnc.qrcode.common.Common;
import org.cnc.qrcode.config.Config;
import org.cnc.qrcode.database.item.History2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MessageTempActivity extends Activity implements OnClickListener {
	private Holder holder;
	// private static ImageButton btn_show_message_temp;
	// private static TextView show_message_temp;
	private String longti6;
	private String lati6;
	private String tempMessage;
	private String address;
	private String url;

	private boolean isBlankOrNull(String arg) {
		return arg == null || (arg != null && "".equals(arg));
	}

	public void onCreate(Bundle objQandAValid) {
		super.onCreate(objQandAValid);
		setContentView(R.layout.message_temp_layout);
		holder = new Holder();
		// btn_show_message_temp = (ImageButton)
		// findViewById(R.id.btn_show_message_temp);
		// show_message_temp = (TextView) findViewById(R.id.show_message_temp);
		// // Text Url
		// TextView textView = (TextView) findViewById(R.id.textView1);

		Bundle extras = getIntent().getExtras();
		longti6 = extras.getString("longi");
		lati6 = extras.getString("lati");
		tempMessage = extras.getString("messa");
		address = extras.getString("address");
		url = extras.getString("url");

		if (url != null) {
			String text = "URL : " + url;
			SpannableString content1 = new SpannableString(text);
			content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
			holder.textView.setText(content1);
			holder.textView.setOnClickListener(this);
		} else {
			holder.textView.setText("");
		}

		if (!Common.isNullOrBlank(Config.getInstance().errorkey)) {
			String key = Config.getInstance().errorkey;
			String timeStart = Config.getInstance().start;
			String timeEnd = Config.getInstance().end;

			tempMessage = Common.getStringKeyFromResource(this, key, timeStart,
					timeEnd);
		}

		int resouce = R.string.message_trans;
		String meString = Common.getText(this, resouce);
		holder.show_message_temp.setText(meString + " :" + tempMessage);

		if (isBlankOrNull(longti6) || isBlankOrNull(lati6)) {
			holder.btn_show_message_temp.setVisibility(View.GONE);
			History2 history = new History2();
			history.setKey(GlobalActivity.questionContent);
			history.setMessge(tempMessage);
			history.setUrl(url);
			if (Common.isNullOrBlank(Config.getInstance().errorkey)) {
				// if (!isBlankOrNull(tempMessage)
				// && !tempMessage.contains("Incorrect")) {
				// if (!tempMessage.contains("Device")
				// && !tempMessage.contains("Please"))
				new SaveHistory(this, history).execute("");
				// }
			}
		}

		holder.btn_show_message_temp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (tempMessage.contains("http://")) {
					Intent iShowWebScreen = new Intent(getBaseContext(),
							ShowWebActivity.class);
					iShowWebScreen.putExtra("msgAns", tempMessage);
					startActivity(iShowWebScreen);
					finish();
				} else {
					Intent iAnswerMapScreen = new Intent(getBaseContext(),
							AnswerMapActivity.class);
					iAnswerMapScreen.putExtra("long5", longti6);
					iAnswerMapScreen.putExtra("lat5", lati6);
					iAnswerMapScreen.putExtra("address", address);
					if (Common.isOnline(MessageTempActivity.this))
						startActivity(iAnswerMapScreen);
				}
			}
		});
	}

	public void onClick(View arg0) {
		if (R.id.textView1 == arg0.getId()) {
			Intent iShowWebScreen = new Intent(getBaseContext(),
					ShowWebActivity.class);
			iShowWebScreen.putExtra("msgAns", url);
			startActivity(iShowWebScreen);
		}
	}

	private class Holder {
		View btn_show_message_temp;
		TextView show_message_temp;
		// Text Url
		TextView textView;

		public Holder() {
			btn_show_message_temp = (ImageButton) findViewById(R.id.btn_show_message_temp);
			show_message_temp = (TextView) findViewById(R.id.show_message_temp);
			// Text Url
			textView = (TextView) findViewById(R.id.textView1);
		}
	}
}
