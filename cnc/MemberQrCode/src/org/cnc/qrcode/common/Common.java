package org.cnc.qrcode.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.cnc.qrcode.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore.Images;
import android.text.format.Time;

public class Common {

	public static final String API10 = "http://daqiri.com/markers/api?key={0}";
	public static final String API3 = "http://daqiri.com/markers/api3";

	public static class Language {
		public static final int ENG = 1;
		public static final int JAPAN = 2;
		public static final int KOREA = 3;
		public static final int CHINA = 4;
		public static int LANGUAGE_ID = ENG;

		// Lang
		public static String STR_ENGLISH = "";
		public static String STR_JAPAN = "";
		public static String STR_KOREA = "";
		public static String STR_CHINA = "";

	}

	public static String readProperites(String key) {
		Properties properties = new Properties();
		try {
			properties.load(Common.class.getResourceAsStream(getLanguage()));
			return properties.getProperty("ENG");
		} catch (Exception e) {
			return "";
		}
	}

	public static String getLanguage() {
		if (Common.Language.LANGUAGE_ID == Common.Language.ENG) {
			return "eng.properties";
		} else if (Common.Language.LANGUAGE_ID == Common.Language.JAPAN) {
			return "japan.properties";
		} else if (Common.Language.LANGUAGE_ID == Common.Language.KOREA) {
			return "korea.properties";
		}
		return "china.properties";
	}

	public static String ANSWRE = "answer";

	public static final int RESULT_0 = 0;
	public static final int RESULT_1 = 1;
	public static final int RESULT_2 = 2;
	public static final int RESULT_3 = 3;
	public static final int RESULT_4 = 4;
	public static final int RESULT_5 = 5;
	public static final int RESULT_6 = 6;
	public static final int RESULT_7 = 7;
	public static final int REQUEST_0 = 0;
	public static final int REQUEST_1 = 1;
	public static final int REQUEST_2 = 2;
	public static final int REQUEST_3 = 3;
	public static final int REQUEST_4 = 4;
	public static final int REQUEST_5 = 5;
	public static final int REQUEST_6 = 6;
	public static final int REQUEST_7 = 7;
	public static final int REQUEST_8 = 8;
	public static final int REQUEST_101 = 101;

	public static final int MESSAGE_WHAT_0 = 0;
	public static final int MESSAGE_WHAT_1 = 1;
	public static final int MESSAGE_WHAT_2 = 2;
	public static final int MESSAGE_WHAT_3 = 3;
	public static final int MESSAGE_WHAT_4 = 4;
	public static final int MESSAGE_WHAT_5 = 5;
	public static final int MESSAGE_WHAT_6 = 6;
	public static final int MESSAGE_WHAT_7 = 7;
	public static final int MESSAGE_WHAT_8 = 8;
	public static final int MESSAGE_WHAT_FINISH = 1000;
	public static final int MESSAGE_WHAT_TEST = 1001;
	public static final String MESSAGE_NWTWORK_MESSAGE = "Haven't network\n Please check your network!";
	public static final String MESSGAE_NETWORK_TITLE = "Check network!";
	public static final String ARG0 = "000";
	public static final String ARG_PARCELABLE = "ARG_PARCELABLE";

	public static String latitude = null;
	public static String longitude = null;

	public static boolean isNullOrBlank(String input) {
		return input == null || (input != null && input.trim().equals(""));
	}

	public static boolean Save_to_SD(Bitmap mBitmap, Context context) {
		try {
			String filename = "QRCode_"
					+ String.valueOf(System.currentTimeMillis());
			ContentValues values = new ContentValues();
			values.put(Images.Media.TITLE, filename);
			values.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
			values.put(Images.Media.MIME_TYPE, "image/jpeg");
			// String path = Environment.getExternalStorageDirectory()
			// .toString() + File.separator + "QR_download";
			// Uri uriPath = Uri.parse(path);
			Uri uri = context.getContentResolver().insert(
					Images.Media.EXTERNAL_CONTENT_URI, values);
			// Uri uri = context.getContentResolver().insert(
			// uriPath, values);

			OutputStream outStream = context.getContentResolver()
					.openOutputStream(uri);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
			outStream.flush();
			outStream.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void showDialog(String title, String message,
			Context context, Handler handler) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});

		builder.show();
	}

	public static boolean isOnline(Context context) {
		if (!CheckStatusNetwork.isOnline(context)) {
			return false;
		}
		return true;
	}

	public static boolean isOnline1(Context context) {
		if (!CheckStatusNetwork.isOnline(context)) {
			return false;
		}
		return true;
	}

	public static Message createMessage(int What, Parcelable parcelable) {
		Message message = new Message();
		message.what = What;
		Bundle data = new Bundle();
		data.putParcelable(ARG_PARCELABLE, parcelable);
		message.setData(data);
		return message;
	}

	public static String getText(Activity showWebActivity, int loadingTrans) {
		Resources resources = showWebActivity.getResources();
		return resources.getString(loadingTrans);
	}

	public static boolean isSupportGPS(Context context) {
		LocationManager manager = (LocationManager) context
				.getSystemService(Activity.LOCATION_SERVICE);
		List<String> lAllProviders = manager.getAllProviders();
		for (int i = 0; i < lAllProviders.size(); i++) {
			if (LocationManager.GPS_PROVIDER.equals(lAllProviders.get(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOpenGPS(Context context) {
		LocationManager manager = (LocationManager) context
				.getSystemService(Activity.LOCATION_SERVICE);
		return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	// / addd
	private static int getKeyResource(String text) {
		String pattern[] = new String[] { "check_key", "device_fail",
				"not_paid", "check_location", "check_time" };
		int resource[] = new int[] { R.string.check_key, R.string.device_fail,
				R.string.not_paid, R.string.check_location, R.string.check_time };
		for (int i = 0; i < pattern.length; i++) {
			if (pattern[i].equals(text)) {
				return resource[i];
			}
		}
		return -1;
	}

	public static String getStringKeyFromResource(Context ct, String key,
			String timeStart, String timeEnd) {
		int resource = getKeyResource(key);

		if (resource == -1) {
			return null;
		}

		String text = getString(ct, resource);

		text = text.replace("{0}", timeStart + "");
		text = text.replace("{1}", timeEnd + "");

		return text;
	}

	public static String getString(Context context, int res) {
		return context.getResources().getString(res);
	}

	public static String createDate() {
		Time currentTime = new Time();
		currentTime.setToNow();
		String text = (currentTime.monthDay < 10 ? "0" : "")
				+ currentTime.monthDay;
		text += "/" + (currentTime.month < 10 ? "0" : "") + currentTime.month;
		text += "/";
		text += (currentTime.year);

		text += " " + currentTime.hour;
		text += ":" + currentTime.minute;
		text += ":" + currentTime.second;
		return text;
	}

	public static void rescanSdcard(Context context) {
		new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
				+ Environment.getExternalStorageDirectory()));
		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentFilter.addDataScheme("file");
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
				.parse("file://" + Environment.getExternalStorageDirectory())));
	}

	private static boolean createDirectory(String string) {
		File file = new File(string);

		if (!file.exists()) {
			file.mkdir();
			return true;
		}

		return false;
	}

	public static String Save_to_SD(Bitmap bm) {
		try {
			String extStorageDirectory = Environment
					.getExternalStorageDirectory().toString();
			String meteoDirectory_path = extStorageDirectory;
			meteoDirectory_path += File.separator + "dcim";
			createDirectory(meteoDirectory_path);
			meteoDirectory_path += File.separator + "QR_Download";
			createDirectory(meteoDirectory_path);
			OutputStream outStream = null;

			Date todayD = new Date(System.currentTimeMillis());
			SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
			String todayS = dayFormat.format(todayD.getTime());
			String arrDate[] = todayS.split("/");

			String d = arrDate[0];
			String m = arrDate[1];
			String n = arrDate[2];
			String filename = "QRCode_" + n + m + d + "_";
			int index = 1;

			boolean check = true;
			while (check) {
				File file = new File(meteoDirectory_path, File.separator
						+ filename + index + ".jpg");
				if (file.exists()) {
					index++;
				} else {

					break;
				}
			}

			File file = new File(meteoDirectory_path, File.separator + filename
					+ index + ".jpg");
			outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
			return meteoDirectory_path + File.separator + filename + index
					+ ".jpg";
		} catch (Exception e) {
			return null;
		}
	}

}
