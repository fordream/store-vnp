package org.com.cnc.qrcode;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class SMSScreen extends Activity implements OnClickListener {
	private AutoCompleteTextView autoCompleteTextView;
	private EditText editText;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms);
		findViewById(R.id.button1).setOnClickListener(this);
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		editText = (EditText) findViewById(R.id.editText1);

		String msg = getIntent().getStringExtra("key");
		msg = getResources().getString(R.string.sms) + msg;
		editText.setText(msg);

		getPhone();
	}

	private void getPhone() {
//		List<Phone> lPhones = Common.getLPhone(this);
//		if(lPhones.size() > 0){
//			Phone phone = lPhones.get(0);
//			Common.builder(this, "Test", phone.toString()).show();
//		}
		// String[] projection = new String[] { Phones.NUMBER };
		// Cursor c = managedQuery( Phones.CONTENT_URI, projection, null, null,
		// null );
		// int colIndex = -1;
		// try {
		// colIndex = c.getColumnIndexOrThrow( Phones.NUMBER );
		//
		// //count is equal to 3
		// for( int i = 0; i < count; i++ ){
		// try {
		//
		// } catch ( Exception e ) {
		// }
		// }
		// } catch( Exception e ) {
		// }

	}

	public void onClick(View arg0) {
		String phone = autoCompleteTextView.getText().toString();
		String content = editText.getText().toString();
		try {
			sendSMS(phone, content);
		} catch (Exception e) {
			Resources resources = getResources();
			String title = resources.getString(R.string.title);
			String message = resources.getString(R.string.message1);
			String close = resources.getString(R.string.close);
			Builder builder = new Builder(this);
			builder.setTitle(title);
			builder.setMessage(message);
			builder.setPositiveButton(close, null);
			builder.show();
		}
	}

	private void sendSMS(String phoneNumber, String message) {
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				SMSScreen.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, pi, null);
	}
}
