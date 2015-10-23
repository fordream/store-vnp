package com.cnc.maispreco.imagescel;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class DecodeBitmap {

	static WeakReference<Bitmap> mImage = null;
	
	public static Bitmap decode(String path) {
		Bitmap bitmap = null;
		
		return bitmap;
	}

	public static Bitmap loadImageFromUrl(String path){
		Bitmap bitmap = null;
		URL url = null;
		InputStream inputStream = null;
		BufferedInputStream bufferedInputStream = null;
		ByteArrayOutputStream outputStream;
		try{
			url = new URL(path);
			inputStream = (InputStream)url.getContent();
			bufferedInputStream = new BufferedInputStream(inputStream, 1024 * 8);
			outputStream = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while((len = bufferedInputStream.read(buffer)) != -1){
				outputStream.write(buffer, 0, len);
			}
			outputStream.close();
			bufferedInputStream.close();
		}catch(Exception e){
			return bitmap;
		}
		byte[] data = outputStream.toByteArray();
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		return bitmap;
	}
	
	/**
	 * Use to resize bitmap
	 * @param bitmap: Bitmap is resized
	 * @param newHeight: new height of btmap
	 * @return: bitmap resized
	 */
	public static Bitmap reSizeBitmap(Bitmap bitmap, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scaleHeight = (float) newHeight / height;
		float ratio = (float)height / newHeight;
		float newWidth = width / ratio;
		float scaleWidth = (float)newWidth / width;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return bitmap2;
	}
	
	/**
	 * @param fileName
	 * @param maxsize
	 * @return
	 */
	public static Bitmap decodeImage(String fileName, float maxsize){
		mImage = null;
        try {
                // Decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new FileInputStream(new File(fileName)),
                                null, o);
                if(o.outWidth<(maxsize*0.8)){
                	try {
                		return BitmapFactory.decodeStream(new FileInputStream(new File(fileName)));						
					} catch (Exception e) {
						return null;
					}
                }else{
	                int scale = 1;
	                scale = (int) Math.pow(2,(int) Math.round(Math.log(maxsize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
	                // Decode with inSampleSize
	                BitmapFactory.Options o2 = new BitmapFactory.Options();
	                o2.inSampleSize = scale;
	                FileInputStream fileInput = new FileInputStream(new File(fileName));
	                mImage = new WeakReference<Bitmap>(BitmapFactory.decodeStream(fileInput, null, o2));
	                fileInput.close();
                }
        } catch (Exception e) {
        }
        return mImage.get();
	}
}
