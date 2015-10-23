package org.com.vnp.xathubongbong2.model;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class CommonView {
	public static void viewDialog(Context context, String title, String message) {
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setCancelable(false);
		builder.setNegativeButton("Close", null);
		builder.show();
	}

	public static void makeText(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void showMarketPublish(Context context, String publish) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://search?q=pub:" + publish));
		context.startActivity(intent);
	}

	public static void viewDialog(Context context, String title,
			String message, DialogInterface.OnClickListener onclick) {
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setCancelable(false);
		builder.setNegativeButton("Close", onclick);
		builder.show();
	}

	public static void viewDialog(Context context, String yes, String close,
			String title, String message,
			DialogInterface.OnClickListener onclickNegativeButton,
			DialogInterface.OnClickListener onclickPositiveButton) {
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setCancelable(false);
		builder.setNegativeButton(close, onclickNegativeButton);
		builder.setPositiveButton(yes, onclickPositiveButton);
		builder.show();
	}

	public static String getString(Context context, int res) {
		return context.getResources().getString(res);
	}

	public static boolean callPhone(Context context, String phone) {
		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + phone));
			context.startActivity(callIntent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

	public static boolean callWeb(Context context, String url) {
		try {
			Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			context.startActivity(myIntent);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static LayoutParams createLayoutParams(Point point) {
		return new LayoutParams(point.x, point.y);
	}

	public static LayoutParams createLayoutParams(int x, int y) {
		return new LayoutParams(x, y);
	}

	public static ProgressDialog showProgressDialog(Context context,
			String message) {
		ProgressDialog dialog = ProgressDialog.show(context, "", message);
		dialog.setCancelable(true);
		return dialog;

	}

	public static void hiddenKeyBoard(Activity activity) {
		try {
			String service = Context.INPUT_METHOD_SERVICE;
			InputMethodManager imm = null;
			imm = (InputMethodManager) activity.getSystemService(service);
			IBinder binder = activity.getCurrentFocus().getWindowToken();
			imm.hideSoftInputFromWindow(binder, 0);
		} catch (Exception e) {
		}
	}

	public static void hiddenTitleBarAndFullScreen(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		activity.getWindow().setFlags(flag, flag);

	}
}