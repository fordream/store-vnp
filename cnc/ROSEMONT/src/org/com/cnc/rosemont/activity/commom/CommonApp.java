package org.com.cnc.rosemont.activity.commom;

import java.io.File;
import java.util.List;

import org.com.cnc.common.android.CommonDeviceId;
import org.com.cnc.rosemont.database.DBAdapterData;
import org.com.cnc.rosemont.database.table.RosemontTable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

public class CommonApp {
	public static class DATA{
		public static String id = null;
		public static String id_row = null;
		
		public static void reset(){
			id = null;
			id_row = null;
		}
	}
	public static final int REQUESTCODE_FINISH = 1000;
	public static final String URL_MEDIA = "http://rosemontdev.pslink.org.uk/components/com_product/media/";

	public static boolean isReload = false;

	public static boolean isMoveProductList = false;

	public static boolean isGotoProductDetail = false;

	public static RosemontTable ROSEMONT = new RosemontTable();

	public static String PATH = Environment.getExternalStorageDirectory()
			+ File.separator + "download" + File.separator + "rosemont"
			+ File.separator;

	public static String PATH_SDCARD = Environment
			.getExternalStorageDirectory() + File.separator;

	public static String PATH_ASSET = "pdf";

	public static Handler hlHome;

	public static Handler hlSearch;

	public static Handler hlCalculator;

	public static int SIZE_OF_TEXT = 10;

	public static void configSizeOftext(Activity activity) {
		if (CommonDeviceId.getHeight(activity) < CommonDeviceId.SIZE_HEIGHT_S) {
			SIZE_OF_TEXT = 10;
		} else if (CommonDeviceId.getHeight(activity) < CommonDeviceId.SIZE_HEIGHT_TAB) {
			SIZE_OF_TEXT = 12;
		} else {
			SIZE_OF_TEXT = 14;
		}
	}

	public static void getDataROSEMONT(final Context context) {
		ROSEMONT = new RosemontTable();
		new DBAdapterData(context).selectAll(ROSEMONT);
	}

	public static float calculator(String strength, String Z) {
		try {
			String X = strength.substring(0, strength.indexOf("mg"));
			String Y = strength.substring(strength.indexOf("/") + 1,
					strength.indexOf("ml"));
			float x = Float.valueOf(X);
			float y = Float.valueOf(Y);
			float z = Float.valueOf(Z);

			return z / x * y;
		} catch (Exception e) {
			return 0f;
		}
	}

	public static float calculator(String strength, String Z, String W) {
		float w = 0;

		try {
			w = Float.valueOf(W);
		} catch (Exception e) {
			w = 0;
		}

		return calculator(strength, Z) * w;
	}

	public static float per(String string, String string2) {
		try {
			float retail = Float.valueOf(string);
			float tariff = Float.valueOf(string2);
	

			float result = (((tariff-retail) / tariff)*100);
//			Margin = ((T-R)/ T)*100%
			return Math.round(result*100)/100f;
		} catch (Exception e) {
		}
		return 0;
	}

	public static void startPDF(String path, Context context) {
		File file = new File(path);
		PackageManager packageManager = context.getPackageManager();
		Intent testIntent = new Intent(Intent.ACTION_VIEW);
		testIntent.setType("application/pdf");
		@SuppressWarnings("rawtypes")
		List list = packageManager.queryIntentActivities(testIntent,
				PackageManager.MATCH_DEFAULT_ONLY);
		if (list.size() > 0 && file.isFile()) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			Uri uri = Uri.fromFile(file);
			intent.setDataAndType(uri, "application/pdf");
			context.startActivity(intent);
		}
	}

	public static void createFolder() {
		String PATH = Environment.getExternalStorageDirectory()
				+ File.separator + "download" + File.separator;
		File file = new File(PATH);
		if (!file.exists()) {
			file.mkdirs();
		}

		PATH = Environment.getExternalStorageDirectory() + File.separator
				+ "download" + File.separator + "rosemont" + File.separator;

		file = new File(PATH);

		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static String changleName(String fileName) {
		if (fileName != null && fileName.contains(".pdf")) {
			int index = fileName.indexOf("|") + 1;
			if (index >= 0) {
				fileName = fileName.substring(index, fileName.length());
			}
		}

		return fileName;
	}

	public static String convertDate(String date1) {
		try {
			String date = date1.substring(0, date1.indexOf(" "));
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			String day = date.substring(8, 10);
			return day + "/" + month + "/" + year;
		} catch (Exception e) {
			return date1;
		}
	}

	public static CharSequence getString(Resources resources, int resource) {
		return resources.getString(resource);
	}

	public static void reset() {
		CommonApp.hlHome = null;
		CommonApp.hlSearch = null;
		CommonApp.hlCalculator = null;
		
		isShowFirst = false;
	}
	public static boolean isShowFirst = true;

	public static String upper(String txtHeader) {
		if (txtHeader != null) {
			char[] stringArray = txtHeader.toCharArray();
			stringArray[0] = Character.toUpperCase(stringArray[0]);
			txtHeader = new String(stringArray);
			return txtHeader;
		}
		return null;

	}

	public static boolean checkStatusWifi(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifi != null) {
			return wifi.isConnected();
		} else
			return false;
	}

	public static boolean checkStatus3G(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile3G = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobile3G != null) {
			return mobile3G.isConnected();
		} else
			return false;
	}

}