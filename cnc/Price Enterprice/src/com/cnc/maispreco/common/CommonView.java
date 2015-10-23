package com.cnc.maispreco.common;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class CommonView {
	public static int HEIGHT = 0;

	public static void makeText(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();

	}

	public static void builder(Context context, String title, String message) {
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setNegativeButton("Close", null);
		builder.show();
	}

	public static void builder(Context context) {
		String title = "Message";
		String message = "Sorry, there is no result available.";
		CommonView.builder(context, title, message);
	}

	public static Bitmap convertBitmap(Bitmap img) {
		int width = img.getWidth();
		int height = img.getHeight();
		int newWidth = 40;
		int newHeight = 60;
		newWidth = (int) (((float) newHeight) / ((float) height) * ((float) width));
		float scaleWidth = (float) newWidth / width;
		float scaleHeight = (float) newHeight / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(img, 0, 0, width, height,
				matrix, true);
		return resizedBitmap;
	}
	
	

	public static Drawable scaleDraable(Drawable drawable) {
		Drawable d = null;
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		Bitmap bitmap2 = convertBitmap(bitmap);
		d = new BitmapDrawable(bitmap2);
		return d;
	}

	
	public static Bitmap convertBitmap(Bitmap img, int h) {
		int width = img.getWidth();
		int height = img.getHeight();
		int newWidth = 40;
		int newHeight = h;
		newWidth = (int) (((float) newHeight) / ((float) height) * ((float) width));
		float scaleWidth = (float) newWidth / width;
		float scaleHeight = (float) newHeight / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(img, 0, 0, width, height,
				matrix, true);
		return resizedBitmap;
	}

	public static Drawable scaleDraable(Drawable drawable, int height) {
		Drawable d = null;
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		Bitmap bitmap2 = convertBitmap(bitmap, height);
		d = new BitmapDrawable(bitmap2);
		return d;
	}

	public static Bitmap convertBitmap(Bitmap img, int width1, int h) {
		int width = img.getWidth();
		int height = img.getHeight();
		int newWidth = 40;
		int newHeight = h;
		newWidth = (int) (((float) newHeight) / ((float) height) * ((float) width));
		if (((float) width1) / ((float) h) > ((float) width) / ((float) height)) {
			newHeight = h;
			newWidth = (int) (((float) newHeight) / ((float) height) * ((float) width));
		} else {
			newWidth = width1;
			newHeight = (int) (((float) newWidth) / ((float) width) * ((float) height));
		}

		float scaleWidth = (float) newWidth / width;
		float scaleHeight = (float) newHeight / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(img, 0, 0, width, height,
				matrix, true);
		return resizedBitmap;
	}

	public static Drawable scaleDraable(Drawable drawable, int width, int height) {
		Drawable d = null;
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		Bitmap bitmap2 = convertBitmap(bitmap, width, height);
		d = new BitmapDrawable(bitmap2);
		return d;
	}
}
