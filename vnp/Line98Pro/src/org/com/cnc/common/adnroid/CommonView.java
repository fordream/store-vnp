package org.com.cnc.common.adnroid;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class CommonView {
	public static void viewDialog(Context context, String title, String message) {
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setNegativeButton("Close", null);
		builder.show();
	}
	
	 static Bitmap convertBitmap(Bitmap img, int width1, int h) {
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

	public static void makeText(Activity activity, String string) {
		Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
	}

	public static void makeText(Context context, String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
		
	}
}
