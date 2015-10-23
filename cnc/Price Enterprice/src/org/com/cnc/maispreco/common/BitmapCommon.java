package org.com.cnc.maispreco.common;

import java.io.InputStream;
import java.net.URL;

import org.com.cnc.maispreco.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapCommon {
	public static final int IO_BUFFER_SIZE = 1024;

	public static Drawable LoadImageFromWebOperations(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	public static Drawable LoadImageFromWebOperations(String url,
			String urlLogoSite, Context context) {

		if (urlLogoSite == null || urlLogoSite != null
				&& !urlLogoSite.startsWith(".")) {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.noitem);
			return new BitmapDrawable(bitmap);
		}

		try {
			InputStream is = (InputStream) new URL(url + urlLogoSite)
					.getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.noitem);
			return new BitmapDrawable(bitmap);
		}
	}

	public static Drawable LoadImageProduct(Context context) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bg1);
		return new BitmapDrawable(bitmap);
		// }
	}

	public static Drawable LoadImageProduct(String url, Context context) {

		if (url == null || url != null && url.indexOf("/") < 0) {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.noitem);
			return new BitmapDrawable(bitmap);
		}

		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.noitem);
			return new BitmapDrawable(bitmap);

		}
	}

}
