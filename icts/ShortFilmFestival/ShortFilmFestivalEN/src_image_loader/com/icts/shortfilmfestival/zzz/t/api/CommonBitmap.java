package com.icts.shortfilmfestival.zzz.t.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

public class CommonBitmap {

	public static Bitmap reSizeBitmap(Bitmap bitmap, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scaleHeight = (float) newHeight / height;
		float ratio = (float) height / newHeight;
		float newWidth = width / ratio;
		float scaleWidth = (float) newWidth / width;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return bitmap2;
	}

	public static Drawable createDrawableTranfer() {
		Drawable drawable = new Drawable() {

			public void setColorFilter(ColorFilter cf) {

			}

			public void setAlpha(int alpha) {

			}

			public int getOpacity() {
				return 0;
			}

			public void draw(Canvas canvas) {

			}
		};
		return drawable;
	}

	public static void downloadFromUrl(String url1, String fileName) {
		try {
			URL url = new URL(url1);
			File file = new File(fileName);
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;

			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
		} catch (IOException e) {
		}
	}

	public static Bitmap loadBitmapFromUrl(String url1, int maxWidthAndHeight) {
		try {
			URL url = new URL(url1);
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();

			// BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inJustDecodeBounds = true;
			//
			// BitmapFactory.decodeStream(is, null, options);
			//
			// options.inSampleSize = checkSampleSize(maxWidthAndHeight,
			// options.outWidth, options.outHeight);
			//
			// options.inJustDecodeBounds = false;
			return BitmapFactory.decodeStream(is, null, null);
		} catch (Exception e) {
			return null;

		}
	}

	public static int checkSampleSize(int widCompare, int widthBitmap,
			int heightBitmap) {
		if (Math.max(widthBitmap, heightBitmap) > widCompare) {
			int inSampleSize = Math.max(widthBitmap, heightBitmap) / widCompare;

			if (Math.max(widthBitmap, heightBitmap) % widCompare != 0) {
				inSampleSize += 1;
			}

			return inSampleSize;
		}

		return 1;
	}
}