package com.nullwire.trace;

import java.io.File;
import java.io.FilenameFilter;

import vn.com.vega.chacha.R;
import vn.com.vega.music.utils.Const;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ExceptionHandler implements Const {
	public static String LOG_TAG = Const.LOG_PREF + ExceptionHandler.class.getSimpleName();

	private static String[] stackTraceFileList = null;

	/**
	 * @param context
	 */
	public static void checkForTraces(final Context context) {
		new Thread(new Runnable() {
			public void run() {
				String[] stackTraces = searchForStackTraces();
				if (stackTraces != null && stackTraces.length > 0) {
					Log.d(LOG_TAG, "number of stack traces: " + stackTraces.length);
					submissionHandler.sendMessage(submissionHandler.obtainMessage(-1, context));
				}
			}
		}).start();
	}

	/**
	 * Register handler for unhandled exceptions.
	 * 
	 * @param context
	 */
	public static boolean register(Context context) {
		Log.i(LOG_TAG, "Registering default exceptions handler");
		// Get information about the Package
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo pi;
			// Version
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			// Package name
			G.APP_PACKAGE = pi.packageName;
			// Version information
			G.APP_VERSION = pi.versionName;
			G.APP_DESCRIPTION = context.getString(R.string.msg_version);
			// Files dir for storing the stack traces
			G.FILES_PATH = Environment.getExternalStorageDirectory() + "/" + pi.packageName + "/" + FOLDER_LOGS;
			// Device model
			G.PHONE_MODEL = android.os.Build.MODEL;
			// Android version
			G.ANDROID_VERSION = android.os.Build.VERSION.RELEASE;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		Log.d(LOG_TAG, "APP_VERSION: " + G.APP_VERSION);
		Log.d(LOG_TAG, "APP_PACKAGE: " + G.APP_PACKAGE);
		Log.d(LOG_TAG, "FILES_PATH: " + G.FILES_PATH);

		boolean stackTracesFound = false;

		// We'll return true if any stack traces were found
		String[] list = searchForStackTraces();
		if (list != null && list.length > 0) {
			stackTracesFound = true;
		}

		new Thread() {
			@Override
			public void run() {
				UncaughtExceptionHandler currentHandler = Thread.getDefaultUncaughtExceptionHandler();

				if (currentHandler != null) {
					Log.d(LOG_TAG, "current handler class=" + currentHandler.getClass().getName());
				}
				// don't register again if already registered
				if (!(currentHandler instanceof DefaultExceptionHandler)) {
					// Register default exceptions handler
					Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(currentHandler));
				}
			}
		}.start();

		return stackTracesFound;
	}

	/**
	 * Search for stack trace files.
	 * 
	 * @return
	 */
	private static String[] searchForStackTraces() {
		if (stackTraceFileList != null) {
			return stackTraceFileList;
		}
		File dir = new File(G.FILES_PATH + "/");
		dir.mkdir();
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		};
		return (stackTraceFileList = dir.list(filter));
	}

	private static Handler submissionHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Context context = (Context) msg.obj;
			ExceptionClickListener clickListener = new ExceptionClickListener();
			new AlertDialog.Builder(context).setMessage(String.format(context.getString(R.string.trace_msg, G.FILES_PATH))).setPositiveButton(android.R.string.yes, clickListener)
					.setNegativeButton(android.R.string.no, clickListener).create().show();
		}
	};

	public synchronized static void removeStackTraces() {
		try {
			String[] list = searchForStackTraces();
			if (list == null)
				return;
			for (int i = 0; i < list.length; i++) {
				File file = new File(G.FILES_PATH + "/" + list[i]);
				file.delete();
			}
			stackTraceFileList = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
