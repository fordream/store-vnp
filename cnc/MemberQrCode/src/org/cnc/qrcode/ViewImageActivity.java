package org.cnc.qrcode;

import java.util.List;

import org.cnc.qrcode.R.string;
import org.cnc.qrcode.common.Common;
import org.cnc.qrcode.common.ImageLoader;
import org.cnc.qrcode.common.XMLfunctions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewImageActivity extends Activity {
	private TextView tVlocation;
	private String _filename = "";
	private ImageView img;
	private String device_id;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Common.MESSAGE_WHAT_0) {
				String title = getResources().getString(R.string.error_trans);
				String messge = getResources().getString(
						R.string.download_fail_trans);
				showDialogCancel(title, messge, false);
			}
			if (msg.what == Common.MESSAGE_WHAT_1) {
				String title = getResources().getString(R.string.Success_trans);
				String message = getResources().getString(
						R.string.Image_save_to_Sdcard_trans);
				showDialogCancel(title, message, true);
			}
			if (msg.what == Common.MESSAGE_WHAT_2) {
				String title = getResources().getString(R.string.error_trans);
				String message = getResources().getString(
						R.string.Dont_Save_file_Check_your_Sdcard_trans);
				showDialogCancel(title, message, false);
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.show_web_layout1);
		device_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		img = (ImageView) findViewById(R.id.imageView1);
		tVlocation = (TextView) findViewById(R.id.tVLocation);
		tVlocation.setText("");
		String key = getIntent().getExtras().getString(Common.ARG0);
		if (!Common.isOnline(ViewImageActivity.this)) {
			return;
		}
		new Download().execute(key);
	}

	private class Download extends AsyncTask<String, String, String> {
		private Bitmap bitmap;
		private List<String> data;
		private boolean check = false;
		private ProgressDialog dialog;

		protected String doInBackground(String... params) {
			handler.post(new Runnable() {
				public void run() {
					String title = getResources().getString(
							R.string.downloading_trans);
					dialog = ProgressDialog.show(ViewImageActivity.this, null,
							title, true);
				}
			});
			data = XMLfunctions.search(params[0], device_id);
			if (data.size() > 0) {
				if ("1".equals(data.get(0))) {
					String url = data.get(1);
					bitmap = ImageLoader.loadPhotoBitmap(url, "image");
					if (bitmap != null) {
						_filename = Common.Save_to_SD(bitmap);
					}
				}
			}
			return null;
		}

		protected void onPostExecute(String result) {
			Common.rescanSdcard(ViewImageActivity.this);
			dialog.dismiss();
			Message message = new Message();
			if (data.size() == 0) {
				message.what = Common.MESSAGE_WHAT_0;
			} else if ("0".equals(data.get(0)) || data.get(0) == null) {
				message.what = Common.MESSAGE_WHAT_0;
			} else {
				if (bitmap == null) {
					message.what = Common.MESSAGE_WHAT_0;
				} else {
					img.setImageBitmap(bitmap);

					String location = getResources().getString(
							string.location_trans)
							+ " : ";
					location += _filename;
					tVlocation.setText(location);
					if (check) {
						message.what = Common.MESSAGE_WHAT_1;
					} else {
						message.what = Common.MESSAGE_WHAT_2;
					}
				}
			}

			handler.sendMessage(message);
		}
	}

	private void showDialogCancel(String title, String message,
			boolean isSuccess) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		if (!isSuccess) {
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							ViewImageActivity.this.finish();
						}
					});
		} else {
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
		}

		builder.show();
	}
}