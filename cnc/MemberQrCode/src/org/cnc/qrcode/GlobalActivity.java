package org.cnc.qrcode;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.cnc.qrcode.R.string;
import org.cnc.qrcode.activity.HowToUseActivity;
import org.cnc.qrcode.activity.LocationActivity;
import org.cnc.qrcode.asyn.API2ASyn;
import org.cnc.qrcode.benchmarkitem.BenchmarkThread;
import org.cnc.qrcode.common.Common;
import org.cnc.qrcode.common.linner.CameraOnClickListener;
import org.cnc.qrcode.common.linner.DiskOnClickListener;
import org.cnc.qrcode.common.linner.GPSOnClickListener;
import org.cnc.qrcode.common.linner.M1LocationListener;
import org.cnc.qrcode.common.linner.M2LocationListener;
import org.cnc.qrcode.common.linner.MLocationListener;
import org.cnc.qrcode.database.DBHistory2Adapter;
import org.cnc.qrcode.database.item.History2;
import org.cnc.qrcode.widget.DialogCancelSupport;
import org.com.cnc.common.adnroid.CommonDeviceId;
import org.com.cnc.common.adnroid.CommonView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.zxing.client.android.CaptureActivity;

public class GlobalActivity extends Activity implements OnClickListener {
	private ImageButton btnGPS;
	private ImageButton btnCamera;
	private ImageButton btnDisk;
	private LinearLayout llWeb;
	public static String questionContent = null;
	private static String PATH = "";
	private static String url_lib;
	public String device_id;
	private LocationManager locationManager;
	private boolean beginGps = false;
	private ProgressDialog dialog;
	private AdView adView;
	// replate MY_AD_UNIT_ID of you
	private String MY_AD_UNIT_ID = "a14da528179106a";

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Resources resources = getResources();
			if (msg.what == Common.MESSAGE_WHAT_0) {
				Intent intentggm = new Intent(getBaseContext(),
						SettingActivity.class);
				startActivityForResult(intentggm, Common.REQUEST_1);
			} else if (msg.what == Common.MESSAGE_WHAT_1) {
				if (questionContent == null) {
					Message message1 = new Message();
					message1.what = Common.MESSAGE_WHAT_0;
					handler.sendMessage(message1);
					return;
				}

				if (!Common.isOnline(GlobalActivity.this)) {
					return;
				}

				String urlGps1 = Common.API3 + "?key=" + questionContent;
				urlGps1 += "&device=" + device_id;
				urlGps1 = urlGps1 + "&lat=" + Common.latitude + "&lng="
						+ Common.longitude;

				new API2ASyn(handler, GlobalActivity.this).execute(urlGps1);
			} else if (msg.what == Common.MESSAGE_WHAT_2) {
				Intent intent = new Intent(getBaseContext(),
						CaptureActivity.class);
				startActivityForResult(intent, Common.REQUEST_0);
			} else if (msg.what == Common.MESSAGE_WHAT_3) {
				String title = resources.getString(R.string.error_trans);
				String message = resources
						.getString(R.string.Cant_decode_this_image_trans);
				message += "\n";
				message += resources
						.getString(R.string.Please_check_your_key_number_trans);
				showDialogCancelSupport(title, message);
			} else if (msg.what == Common.MESSAGE_WHAT_4) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				String message = resources
						.getString(R.string.Select_Picture_trans);
				startActivityForResult(Intent.createChooser(intent, message),
						Common.REQUEST_101);
			} else if (msg.what == Common.MESSAGE_WHAT_5) {
				String title = resources.getString(R.string.error_trans);
				String message = resources
						.getString(R.string.Cant_not_decode_this_image_trans);
				message += "\n";
				message += resources
						.getString(R.string.Please_check_your_image_trans);
				showDialogCancelSupport(title, message);
			} else if (msg.what == Common.MESSAGE_WHAT_6) {
				String message = resources
						.getString(R.string.Your_mobile_cant_not_scan_and_decode_trans);
				message += "\n";
				message += resources
						.getString(R.string.Please_check_your_key_number_or_this_DQR_code_trans);
				String title = resources.getString(R.string.error_trans);
				showDialogCancelSupport(title, message);

			} else if (msg.what == Common.MESSAGE_WHAT_7) {

				String message = msg.getData().getString("message");
				String latitude = msg.getData().getString("lat");
				String longtitude = msg.getData().getString("lng");
				String address = msg.getData().getString("address");
				String url = msg.getData().getString("url");
				redirect(message, latitude, longtitude, address, url);
			} else if (msg.what == Common.MESSAGE_WHAT_8) {
				if (API2ASyn.lAnswer.size() == 1) {
					String message = API2ASyn.lContent.get(2);
					String latitude = API2ASyn.lAnswer.get(0).getLat();
					String longtitude = API2ASyn.lAnswer.get(0).getLog();
					redirect(message, latitude, longtitude, API2ASyn.lAnswer
							.get(0).getAddress(), API2ASyn.lAnswer.get(0)
							.getUrl());
					return;
				}
				Intent iQuestionOnlyScreen = new Intent(getBaseContext(),
						QuestionOnlyActivity.class);
				iQuestionOnlyScreen.putExtra("que", "");
				iQuestionOnlyScreen.putExtra("ans", "");
				iQuestionOnlyScreen.putExtra("anstrue", "");
				iQuestionOnlyScreen.putExtra("long2", "");
				iQuestionOnlyScreen.putExtra("lat2", "");
				iQuestionOnlyScreen.putExtra("nextP", "");
				iQuestionOnlyScreen.putExtra("msgU", "");
				startActivityForResult(iQuestionOnlyScreen, Common.REQUEST_7);
			}
		}

		private void redirect(String message, String latitude,
				String longtitude, String address, String url) {
			if (!Common.isNullOrBlank(message) || !Common.isNullOrBlank(url)) {
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
				if (!Common.isOnline(GlobalActivity.this)) {
					return;
				}
				startActivity(iAnswerMapScreen);
			} else {
				message = getResources().getString(
						R.string.havent_message_and_next_point_trans);
				String title = getResources().getString(R.string.error_trans);
				showDialogCancelSupport(title, message);
			}
		}
	};

	public void onCreate(Bundle objBundleGlobal) {
		super.onCreate(objBundleGlobal);
		setContentView(R.layout.global_layout);

		device_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		String type_id = CommonDeviceId.TYPE_ID_ICOMBINED_DEVICE_ID;
		device_id = CommonDeviceId.deviceId(this, type_id);
		adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout2);

		// Add the adView to it
		layout.addView(adView);
		AdRequest request = new AdRequest();
		request.setTesting(true);
		adView.loadAd(request);

		questionContent = null;
		btnGPS = (ImageButton) findViewById(R.id.button_saterlite);
		btnCamera = (ImageButton) findViewById(R.id.button_camera);
		btnDisk = (ImageButton) findViewById(R.id.button_disk);
		findViewById(R.id.ImageButton01).setOnClickListener(this);

		GPSOnClickListener gpsOnClickListener = new GPSOnClickListener(this,
				handler);
		btnGPS.setOnClickListener(gpsOnClickListener);
		CameraOnClickListener cameraOnClickListener = new CameraOnClickListener(
				this, handler);
		btnCamera.setOnClickListener(cameraOnClickListener);

		DiskOnClickListener onClickListener = new DiskOnClickListener(this,
				handler);
		btnDisk.setOnClickListener(onClickListener);

		llWeb = (LinearLayout) findViewById(R.id.LinearLayout02);
		llWeb.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String authUrl = "http://daqiri.com/";
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_setting, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.setting_phone_number:
			Intent intent1 = new Intent(getBaseContext(),
					HowToUseActivity.class);
			startActivity(intent1);
			break;
		case R.id.about_us:
			Intent intent = new Intent(getBaseContext(), AboutUsScreen.class);
			startActivity(intent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	// get path when browser image
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == Common.REQUEST_7) {
			if (resultCode == RESULT_OK) {
				finish();
				return;
			}
		}

		if (requestCode == Common.REQUEST_0 && resultCode == RESULT_OK) {
			String contents = intent.getStringExtra("key");
			String url = contents + "?key=" + questionContent;
			url += "&device=" + device_id;
			if (!Common.isOnline(GlobalActivity.this)) {
				String title = getResources().getString(string.error_trans);
				String message = getResources().getString(
						string.havent_network_trans)
						+ "\n"
						+ getResources().getString(
								string.please_check_your_network_trans);
				Builder builder = new Builder(this);
				builder.setTitle(title);
				builder.setMessage(message);
				builder.setPositiveButton(
						getResources().getString(string.close_trans), null);
				builder.show();
				return;
			}

			// showDialogCancel("", url);
			new API2ASyn(handler, this).execute(url);
		}

		if (requestCode == Common.REQUEST_101 && intent != null) {

			request_101_1(intent);
		} else if (requestCode == Common.REQUEST_101 && intent == null) {

			String message = getResources().getString(
					R.string.No_Image_is_selected_trans);
			Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
			toast.show();
		}

		if (requestCode == Common.REQUEST_8 && resultCode == RESULT_OK) {
			// QA
			String urlGps1 = Common.API3 + "?key=" + questionContent;
			urlGps1 += "&device=" + device_id;
			urlGps1 = urlGps1 + "&lat=" + Common.latitude + "&lng="
					+ Common.longitude;
			new API2ASyn(handler, this).execute(urlGps1);
		} else if (requestCode == Common.REQUEST_8
				&& resultCode == RESULT_CANCELED) {
			String message = getString(R.string.dont_get_GPS);
			String title = getString(R.string.message_trans);

			showDialogCancelSupport(title, message);
		} else if (Common.REQUEST_1 == requestCode && resultCode == RESULT_OK) {
			if (indexOfbutton == 1) {
				startActivityForResult(
						new Intent(this, LocationActivity.class),
						Common.REQUEST_8);
			} else if (indexOfbutton == 2) {
				Intent intent1 = new Intent(getBaseContext(),
						CaptureActivity.class);
				startActivityForResult(intent1, Common.REQUEST_0);
			} else if (indexOfbutton == 3) {
				gotoHistory();
			}
		}
	}

	private void request_101_1(Intent intent) {
		try {
			Uri selectedImageUri = intent.getData();
			String selectedImagePath = getPath(selectedImageUri);
			String tokens[] = selectedImagePath.split("/");
			String message = getResources().getString(
					R.string.Image_is_selected_trans)
					+ ":";
			Toast toast = Toast.makeText(this, message
					+ tokens[tokens.length - 1], Toast.LENGTH_LONG);
			toast.show();
			PATH = selectedImagePath;

			BenchmarkThread mBenchmarkThread = new BenchmarkThread(PATH);
			mBenchmarkThread.getLink();

			url_lib = BenchmarkThread.url_scan_from_lib + "?key="
					+ questionContent;
			if (!Common.isNullOrBlank(BenchmarkThread.url_scan_from_lib)) {
				if (!Common.isOnline(GlobalActivity.this)) {
					return;
				}
				new API2ASyn(handler, this).execute(url_lib);
			}
		} catch (Exception e) {
			String title = getResources().getString(R.string.error_trans);
			String messString = getResources().getString(
					R.string.Your_mobile_cant_not_scan_this_image_trans);
			messString += "\n";
			messString += getResources().getString(
					R.string.please_check_your_image_or_wifi_trans);
			showDialogCancelSupport(title, messString);
		}
	}

	private void showDialogCancelSupport(final String title,
			final String message) {
		new DialogCancelSupport(this, title, message).show();
		// AlertDialog.Builder builder = new AlertDialog.Builder(
		// GlobalActivity.this);
		// builder.setTitle(title);
		// builder.setCancelable(true);
		// builder.setMessage(message);
		// builder.setPositiveButton("OK", null);
		// builder.show();
	}

	public void getGPS(String provider) {
		Log.i("PROVIDER", provider);
		if (locationManager == null)
			locationManager = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);

		String message = getString(R.string.get_gps_trans);
		dialog = ProgressDialog.show(this, null, message);
		beginGps = true;
		LocationListener listener = new MLocationListener(this);
		locationManager.requestLocationUpdates(provider, 0, 0, listener);

		new AsyncTask<String, String, String>() {
			protected String doInBackground(String... params) {
				int time = 0;
				while (time <= 10 && beginGps) {
					Common.sleep(1000);
					time++;
				}
				beginGps = false;
				return null;
			}

			protected void onPostExecute(String result) {
				if (beginGps) {
					upSearchGPS();
				}
			}
		}.execute("");
	}

	public void upSearchGPS() {

		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		LocationListener listener = new M1LocationListener(this);
		try {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, listener);
		} catch (Exception e) {
		}

		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, listener);

		if (beginGps) {
			String urlGps1 = Common.API3 + "?key=" + questionContent;
			urlGps1 += "&device=" + device_id;
			urlGps1 = urlGps1 + "&lat=" + Common.latitude + "&lng="
					+ Common.longitude;

			Log.i("LAT", Common.latitude + "");
			Log.i("LOG", Common.longitude + "");
			new API2ASyn(handler, this).execute(urlGps1);
			if (dialog != null) {
				dialog.dismiss();
			}
		} else if (Common.isOnline1(this)) {
			check = true;
			String message = getString(R.string.get_gps_trans);
			dialog = ProgressDialog.show(this, null, message);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0,
					new M2LocationListener(this));
		}
		beginGps = false;
	}

	boolean check = false;

	public void upSearchGPS1() {
		if (dialog != null) {
			dialog.dismiss();
		}

		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		LocationListener listener = new M1LocationListener(this);
		try {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, listener);
		} catch (Exception e) {
		}
		if (check) {
			String urlGps1 = Common.API3 + "?key=" + questionContent;
			urlGps1 += "&device=" + device_id;
			urlGps1 = urlGps1 + "&lat=" + Common.latitude + "&lng="
					+ Common.longitude;
			new API2ASyn(handler, this).execute(urlGps1);

		}
		check = false;
	}

	public int indexOfbutton = -1;

	public void onClick(View v) {
		if (v.getId() == R.id.ImageButton01) {
			indexOfbutton = 3;
			if (Common.isNullOrBlank(GlobalActivity.questionContent)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				String message = Common.getText(this,
						R.string.Your_must_input_your_key_number_trans);
				builder.setMessage(message);
				builder.setCancelable(false);
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Message message = new Message();
								message.what = Common.MESSAGE_WHAT_0;
								handler.sendMessage(message);
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
				return;
			}
			gotoHistory();

		}
	}

	private void gotoHistory() {
		History2 history = new DBHistory2Adapter(this)
				.getHistory(GlobalActivity.questionContent);
		if (!Common.isNullOrBlank(history.getMessge())) {
			Date today = new Date(System.currentTimeMillis());
			SimpleDateFormat dayFormat = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm:ss");
			String todayS = dayFormat.format(today.getTime());

			Intent iMessageScreen = new Intent(getBaseContext(),
					MessageTempActivity.class);
			iMessageScreen.putExtra("messa", history.getMessge());
			iMessageScreen.putExtra("lati", history.getLat());
			iMessageScreen.putExtra("longi", history.get_long());
			iMessageScreen.putExtra("today", todayS);
			iMessageScreen.putExtra("address", history.getAddress());
			iMessageScreen.putExtra("url", history.getUrl());
			startActivity(iMessageScreen);
		} else if (!Common.isNullOrBlank(history.getLat())) {
			Intent intent = new Intent(getBaseContext(),
					AnswerMapActivity.class);
			intent.putExtra("long5", history.get_long());
			intent.putExtra("lat5", history.getLat());
			intent.putExtra("address", history.getAddress());
			startActivity(intent);
		} else {
			String title = getResources().getString(string.error_trans);
			String message = getResources().getString(
					string.havent_history_trans);
			CommonView.viewDialog(this, title, message);
		}
	}

}