package com.nullwire.trace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import vn.com.vega.music.utils.Const;

import android.os.SystemClock;
import android.util.Log;

public class DefaultExceptionHandler implements UncaughtExceptionHandler {

	private UncaughtExceptionHandler defaultExceptionHandler;

	private static final String LOG_TAG = Const.LOG_PREF + DefaultExceptionHandler.class.getSimpleName();

	// constructor
	public DefaultExceptionHandler(UncaughtExceptionHandler pDefaultExceptionHandler) {
		defaultExceptionHandler = pDefaultExceptionHandler;
	}

	// Default exception handler
	public void uncaughtException(Thread t, Throwable e) {
		// Here you should have a more robust, permanent record of problems
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		try {
			long time = SystemClock.currentThreadTimeMillis();
			String filename = G.APP_VERSION + "-" + time;
			Log.d(LOG_TAG, "Writing unhandled exception to: " + G.FILES_PATH + "/" + filename + ".txt");
			BufferedWriter bos = new BufferedWriter(new FileWriter(G.FILES_PATH + "/" + filename + ".txt"));
			bos.write(G.APP_VERSION + "\n");
			bos.write(G.APP_DESCRIPTION + "\n");
			bos.write(G.ANDROID_VERSION + "\n");
			bos.write(G.PHONE_MODEL + "\n");
			bos.write(result.toString());
			bos.flush();
			bos.close();
		} catch (Exception ebos) {
			ebos.printStackTrace();
		}
		Log.d(LOG_TAG, result.toString());
		defaultExceptionHandler.uncaughtException(t, e);
	}
}
