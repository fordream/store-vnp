package com.icts.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import com.icts.itel.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * The actual AsyncTask that will asynchronously process the image.
 */
public class DownloadImageTask implements Runnable {
    private int mImageWidth;
    private int mImageHeight;
    private ImageView imageView;
	private String path;
	private Bitmap bitmap = null;
	private boolean isAvatar = false;
	private boolean isMale = false;
	private Context context;
    public DownloadImageTask(ImageView imageView, int width, int height,Context context,String url) {
    	this.context = context;
        mImageWidth = width;
        mImageHeight = height;
        this.imageView = imageView;
        this.path = url;
    }
    public DownloadImageTask(ImageView imageView, int width, int height,Context context,String url,boolean isMale) {
    	this.context = context;
        mImageWidth = width;
        mImageHeight = height;
        this.imageView = imageView;
        this.path = url;
        this.isMale = isMale;
        this.imageView.post(new Runnable() {
			
			@Override
			public void run() {
				Bitmap b = Utils.getAvatarImage(DownloadImageTask.this.isMale,
												DownloadImageTask.this.context);
				DownloadImageTask.this.imageView.setImageBitmap(b);
			}
		});
    }
    
    public void setIsAvatar(boolean c){
    	isAvatar = c;
    }
    
    public void setMale(boolean b){
    	isMale = b;
    }
    
    /**
     * The main process method, which will be called by the ImageWorker in the AsyncTask background
     * thread.
     *
     * @param data The data to load the bitmap, in this case, a regular http URL
     * @return The downloaded and resized bitmap
     */
    private Bitmap processBitmap(String data) {
        // Download a bitmap, write it to a file
        final byte[] b = downloadBitmap( data);

        if (b != null) {
            // Return a sampled down version
            return decodeSampledBitmapFromFile(b, mImageWidth, mImageHeight);
        }

        return null;
    }

    /**
     * Decode and sample down a bitmap from a file to the requested width and height.
     *
     * @param filename The full path of the file to decode
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions
     *         that are equal to or greater than the requested width and height
     */
    private synchronized Bitmap decodeSampledBitmapFromFile(byte[] b,
            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //BitmapFactory.decodeFile(filename, options);
        BitmapFactory.decodeByteArray(b,0,b.length, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(b,0,b.length, options);
    }
    
    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}.
     * @param options An options object with out* params already populated (run through a decode*
     *            method with inJustDecodeBounds==true
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further.
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
    
	private byte[] downloadBitmap(String url){
		int size;
		byte[] w = new byte[1024];
        ByteArrayOutputStream out = null;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(new URI(url));
			httpGet.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(Constant.ITELL_USERNAME_AUTHEN,Constant.ITELL_PASS_AUTHEN),
					"UTF-8", false));
			HttpResponse response = httpclient.execute(httpGet);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				out = new ByteArrayOutputStream();
					
				while (true) {
					size = is.read(w);
					if (size <= 0) break;
					out.write(w, 0 ,size);
				}
	            int b;
	            while ((b = is.read()) != -1) {
	                out.write(b);
	            }

	            return out.toByteArray();
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public void run() {
		
		if (path==null){
			setBitmap(null);
			return;
		}
		if (path.equalsIgnoreCase("")){
			setBitmap(null);
			return;
		}
		 bitmap = ImageCache.getBitmapFromMemCache(path);
		 if (bitmap==null){
	         bitmap = processBitmap(path);
	         ImageCache.addBitmapToCache(path, bitmap);
         }
		 setBitmap(bitmap);
	
	}
	
	protected void setBitmap(Bitmap bitmap){
		  
		 if (imageView!=null){
			 if (bitmap==null){
				 if (!isAvatar){
					Drawable draw = context.getResources().getDrawable(R.drawable.icon);
					bitmap = Utils.convert2bitmap(draw);
				 }
				 else {
					 bitmap = Utils.getAvatarImage(isMale, context);
				 }
			 }
			 final Bitmap b = bitmap; 
        	 imageView.post(new Runnable() {
				
				@Override
				public void run() {
					imageView.setImageBitmap(null);
					imageView.setImageBitmap(b);
				}
			});
         }
	}


}
