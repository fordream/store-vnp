package org.cnc.qrcode.common;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageLoader {

	private static final String TAG = ImageLoader.class.getSimpleName();

	public static Bitmap loadPhotoBitmap(final String imageUrl,
			final String type) {

		InputStream imageStream = null;

		try {
			HttpGet httpRequest = new HttpGet(new URL(imageUrl).toURI());
			HttpResponse response = (HttpResponse) new DefaultHttpClient()
					.execute(httpRequest);
			httpRequest = null;
			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(
					response.getEntity());
			response = null;
			imageStream = bufHttpEntity.getContent();
			bufHttpEntity = null;

			if (imageStream != null) {
				final BitmapFactory.Options options = new BitmapFactory.Options();
				if (type.equals("image")) {
					options.inSampleSize = 2;
				}

				return BitmapFactory.decodeStream(imageStream, null, options);
			} else {
			}
		} catch (IOException e) {
		} catch (URISyntaxException e) {
		} finally {
			if (imageStream != null)
				closeStream(imageStream);
		}

		return null;
	}

	private static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				Log.e(TAG, "Could not close stream", e);
			}
		}
	}
}