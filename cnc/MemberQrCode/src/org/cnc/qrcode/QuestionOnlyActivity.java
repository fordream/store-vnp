package org.cnc.qrcode;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.cnc.qrcode.adapter.ApaterRow;
import org.cnc.qrcode.asyn.API2ASyn;
import org.cnc.qrcode.builder.AlertCustom;
import org.cnc.qrcode.common.Answer;
import org.cnc.qrcode.common.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionOnlyActivity extends Activity {
	private ListView listView;
	private ApaterRow adapter;
	private List<String> lData = new ArrayList<String>();

	int position = 0;

	private TextView question;
	private ImageButton btn_answer;
	String testAnswer = "2";
	String idButton = "";
	String testAns = "";
	String nextPoint2 = "";
	String msU;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Common.MESSAGE_WHAT_0) {
				finish();
			}
		}

	};

	public void onCreate(Bundle objQandAValid) {
		super.onCreate(objQandAValid);
		setContentView(R.layout.question_layout);
		question = (TextView) findViewById(R.id.show_question);
		try {
			question.setText(API2ASyn.lContent.get(3));
		} catch (Exception e) {
		}
		btn_answer = (ImageButton) findViewById(R.id.btn_answer_question);

		listView = (ListView) findViewById(R.id.listView1);
		adapter = new ApaterRow(this, lData);
		listView.setAdapter(adapter);

		for (Answer answer : API2ASyn.lAnswer) {
			String name = answer.getText();
			if (name == null) {
				name = "";
			}

			lData.add(name);
		}

		adapter.setPosition(0);
		adapter.notifyDataSetChanged();

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				QuestionOnlyActivity.this.position = position;
				adapter.setPosition(position);
				adapter.notifyDataSetChanged();
			}
		});

		btn_answer.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				int position = adapter.getPosition();
				if (API2ASyn.lAnswer.size() >= 1) {
					if (position >= 0) {
						String message = "";
						try {
							message = API2ASyn.lContent.get(2);
						} catch (Exception e) {
						}
						String latitude = API2ASyn.lAnswer.get(position)
								.getLat();
						String longtitude = API2ASyn.lAnswer.get(position)
								.getLog();
						String address = API2ASyn.lAnswer.get(position)
								.getAddress();
						String url = API2ASyn.lAnswer.get(position).getUrl();
						redirect(message, latitude, longtitude, address, url);
						return;
					}
				}
				String title = Common.getText(QuestionOnlyActivity.this,
						R.string.error_trans);
				showDialog(title, "Your answer is incorrect!");
			}

			private void redirect(String message, String latitude,
					String longtitude, String address, String url) {
				if (!Common.isNullOrBlank(message)) {
					Date today = new Date(System.currentTimeMillis());

					SimpleDateFormat dayFormat = new SimpleDateFormat(
							"dd/MM/yyyy hh:mm:ss");
					String todayS = dayFormat.format(today.getTime());

					Intent iMessageScreen = new Intent(getBaseContext(),
							MessageTempActivity.class);
					iMessageScreen.putExtra("messa", message);
					iMessageScreen.putExtra("lati", latitude);
					iMessageScreen.putExtra("longi", longtitude);
					iMessageScreen.putExtra("today", todayS);
					iMessageScreen.putExtra("address", address);
					iMessageScreen.putExtra("url", url);
					startActivity(iMessageScreen);
				} else if (!Common.isNullOrBlank(longtitude)
						&& !Common.isNullOrBlank(latitude)) {
					Intent iAnswerMapScreen = new Intent(getBaseContext(),
							AnswerMapActivity.class);
					iAnswerMapScreen.putExtra("long5", longtitude);
					iAnswerMapScreen.putExtra("lat5", latitude);
					iAnswerMapScreen.putExtra("address", address);
					if (Common.isOnline(QuestionOnlyActivity.this)) {
						startActivity(iAnswerMapScreen);
					}
				} else {
					String title = Common.getText(QuestionOnlyActivity.this,
							R.string.go_to_main_screen_trans);
					String message1 = Common.getText(QuestionOnlyActivity.this,
							R.string.havent_message_and_next_point_trans);
					showDialogCancel(title, message1);
				}
			}
		});

	}

	private void showDialogCancel(String title, String message) {
		AlertCustom builder = new AlertCustom(this, title, message, handler);
		builder.show();
	}

	private void showDialog(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent iIncorrectScreen = new Intent(getBaseContext(),
						GlobalActivity.class);
				startActivity(iIncorrectScreen);
				finish();
			}
		});

		builder.show();
	}
}