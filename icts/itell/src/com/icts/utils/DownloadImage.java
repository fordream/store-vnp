package com.icts.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;

/**
 * The actual AsyncTask that will asynchronously process the image.
 */
public class DownloadImage extends AsyncTask<Object, Void, Bitmap> {
	private static final int FADE_IN_TIME = 200;

    private Bitmap mLoadingBitmap;
    private boolean mFadeInBitmap = true;
    private Context mContext;
    private Object data;
    private int mImageWidth;
    private int mImageHeight;
    private final WeakReference<ImageView> imageViewReference;

    public DownloadImage(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }
    public DownloadImage(ImageView imageView, int width, int height,Context context) {
        this(imageView);
        mImageWidth = width;
        mImageHeight = height;
        mContext = context;
    }

    /**
     * Returns the ImageView associated with this task as long as the ImageView's task still
     * points to this task as well. Returns null otherwise.
     */
    private ImageView getAttachedImageView() {
        final ImageView imageView = imageViewReference.get();
        return imageView;
    }

	@Override
	protected Bitmap doInBackground(Object... params) {
		 data = params[0];
         final String dataString = String.valueOf(data);
         Bitmap bitmap = null;

         // If the bitmap was not found in the cache and this task has not been cancelled by
         // another thread and the ImageView that was originally bound to this task is still
         // bound back to this task and our "exit early" flag is not set, then call the main
         // process method (as implemented by a subclass)
         bitmap = ImageCache.getBitmapFromMemCache(dataString);
         if (bitmap==null){
	         bitmap = processBitmap(dataString);
	         ImageCache.addBitmapToCache(String.valueOf(params[0]), bitmap);
         }
         return bitmap;
	}
	/**
     * Once the image is processed, associates it to the imageView
     */
    @Override
    protected void onPostExecute(final Bitmap bitmap) {
        final ImageView imageView = getAttachedImageView();
        if (bitmap != null && imageView != null) {
        	imageView.post(new Runnable() {
				public void run() {
					 setImageBitmap(imageView, bitmap);
				}
			});
           
        }
    }
	

    /**
     * Called when the processing is complete and the final bitmap should be set on the ImageView.
     *
     * @param imageView
     * @param bitmap
     */
    private void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (mFadeInBitmap) {
            // Transition drawable with a transparent drwabale and the final bitmap
            final TransitionDrawable td =
                    new TransitionDrawable(new Drawable[] {
                            new ColorDrawable(android.R.color.transparent),
                            new BitmapDrawable(mContext.getResources(), bitmap)
                    });
            // Set background to loading bitmap
            imageView.setBackgroundDrawable(
                    new BitmapDrawable(mContext.getResources(), mLoadingBitmap));

            imageView.setImageDrawable(td);
            td.startTransition(FADE_IN_TIME);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }
    
    /**
     * If set to true, the image will fade-in once it has been loaded by the background thread.
     *
     * @param fadeIn
     */
    public void setImageFadeIn(boolean fadeIn) {
        mFadeInBitmap = fadeIn;
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

    protected Bitmap processBitmap(Object data) {
        return processBitmap(String.valueOf(data));
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
    public static synchronized Bitmap decodeSampledBitmapFromFile(byte[] b,
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
    
    private byte[] downloadBitmap1(String url){
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
    
    /**
     * Download a bitmap from a URL, write it to a disk and return the File pointer. This
     * implementation uses a simple disk cache.
     *
     * @param context The context to use
     * @param urlString The URL to fetch
     * @return A File pointing to the fetched bitmap
     */
    public byte[] downloadBitmap( String urlString) {
       //Check cache
    	int size;
		byte[] w = new byte[1024];
        HttpURLConnection urlConnection = null;
        ByteArrayOutputStream out = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            
            if (urlString.contains(Constant.DOMAIN)) {
    	        String authString = Constant.ITELL_USERNAME_AUTHEN+":"+Constant.ITELL_PASS_AUTHEN;
    	        byte[] authEncBytes = Base64.encode(authString.getBytes(),Base64.DEFAULT);
    	        String	authStringEnc = new String(authEncBytes);
    			urlConnection.setRequestProperty("Authorization", "basic " + authStringEnc);
            }
            final InputStream in =
                    new BufferedInputStream(urlConnection.getInputStream(), Constant.IO_BUFFER_SIZE);
            out = new ByteArrayOutputStream();
			
			while (true) {
				size = in.read(w);
				if (size <= 0) break;
				out.write(w, 0 ,size);
			}
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }

            return out.toByteArray();

        } catch (final IOException e) {
        	e.printStackTrace();
        } finally {
        	
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (final IOException e) {
                }
            }
        }

        return null;
    }




}
