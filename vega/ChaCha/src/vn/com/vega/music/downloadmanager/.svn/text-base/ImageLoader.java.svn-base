package vn.com.vega.music.downloadmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.SyncUpstreamPackage;
import vn.com.vega.music.utils.Const;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

/**
 * 
 * @author khainv
 * 
 */
public class ImageLoader {
	
	private static final String LOG_TAG = Const.LOG_PREF + ImageLoader.class.getSimpleName();
	
	private static ImageLoader shareInstance;
	private final int image_id = R.drawable.ic_playlist_default;
	
	private final int album_image_id = R.drawable.ic_album_default;
	
	private final int artist_image_id = R.drawable.ic_artist_default;
	
	private final int video_image_id = R.drawable.ic_video_default;
	
	private final int playlist_image_id = R.drawable.ic_playlist_default;
	
	private final int song_image_id = R.drawable.ic_song_default;
	
	private String mType = TYPE_SONG;
	
	public static final String TYPE_SONG = "SONG";
	public static final String TYPE_ARTIST = "ARTIST";
	public static final String TYPE_ALBUM = "ALBUM";
	public static final String TYPE_PLAYLIST = "PLAYLIST";
	public static final String TYPE_VIDEO = "VIDEO";
	
	private HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();
	private File cacheDir;
	private Context context;
	//private Bitmap defaultImage;

	public static ImageLoader getInstance(Context context) {
		if (shareInstance == null) {
			shareInstance = new ImageLoader(context);
		}
		return shareInstance;
	}

	private ImageLoader(Context ctx) {
		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
		context = ctx;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(Environment.getExternalStorageDirectory(), context.getPackageName() + "/images/");
		} else {
			cacheDir = context.getCacheDir();
		}

		if (!cacheDir.exists())
			cacheDir.mkdirs();
		
//		if(mType.equals(TYPE_VIDEO))
//			defaultImage = ((BitmapDrawable) context.getResources().getDrawable(video_image_id)).getBitmap();
//		else if(mType.equals(TYPE_ALBUM))
//			defaultImage = ((BitmapDrawable) context.getResources().getDrawable(album_image_id)).getBitmap();
//		else if(mType.equals(TYPE_ARTIST))
//			defaultImage = ((BitmapDrawable) context.getResources().getDrawable(artist_image_id)).getBitmap();
//		else if(mType.equals(TYPE_PLAYLIST))
//			defaultImage = ((BitmapDrawable) context.getResources().getDrawable(playlist_image_id)).getBitmap();
//		else
//			defaultImage = ((BitmapDrawable) context.getResources().getDrawable(song_image_id)).getBitmap();
		
	}

	public void DisplayImage(String url, Activity activity, ImageView imageView, String type) {
		
		mType = type;
		
		if(type.equals(TYPE_VIDEO))
			DisplayImage(url, activity, imageView, video_image_id);
		else if(type.equals(TYPE_ALBUM))
			DisplayImage(url, activity, imageView, album_image_id);
		else if(type.equals(TYPE_ARTIST))
			DisplayImage(url, activity, imageView, artist_image_id);
		else if(type.equals(TYPE_PLAYLIST))
			DisplayImage(url, activity, imageView, playlist_image_id);
		else
			DisplayImage(url, activity, imageView, song_image_id);
	}

	public void DisplayImage(String url, Activity activity, ImageView imageView, int defaultImageResID) {
		imageView.setTag(url);
		String relativeURL = relativeURLof(url);
		if (cache.containsKey(relativeURL)) {
			imageView.setImageBitmap(cache.get(relativeURL));
		}

		else {
			queuePhoto(url, activity, imageView);
			imageView.setImageResource(defaultImageResID);
		}
	}

	private String relativeURLof(String url) {
		if (url == null || url.trim().length() == 0)
			return "";

		String relativeURL = url.replaceFirst("http://[^/]+/", "");
		return relativeURL;
	}

	private void queuePhoto(String url, Activity activity, ImageView imageView) {
		photosQueue.Clean(imageView);
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		synchronized (photosQueue.photosToLoad) {
			photosQueue.photosToLoad.push(p);
			photosQueue.photosToLoad.notifyAll();
		}

		if (photoLoaderThread.getState() == Thread.State.NEW)
			photoLoaderThread.start();
	}

	public Bitmap getBitmap(String url) {
		String filename = String.valueOf(url.hashCode());
		File f = new File(cacheDir, filename);
		Bitmap bitmap;
		bitmap = decodeFile(f);
		if (bitmap != null)
			return bitmap;
		try {
			URL imgUrl = new URL(url);
			InputStream is = imgUrl.openStream();
			if (is != null) {
				OutputStream os = new FileOutputStream(f);
				copyStream(is, os);
				os.close();
				bitmap = decodeFile(f);
			}
		} catch (MalformedURLException mue) {
			// TODO: handle exception
			//bitmap = defaultImage;
		} catch (IOException ioe) {
			// TODO: handle exception
			 //bitmap = defaultImage;
		}
		return bitmap;
	}

	private Bitmap decodeFile(File f) {
		try {
			return BitmapFactory.decodeStream(new FileInputStream(f));

			// @Phuc removed, for using ImageLoader with many image size
			/*
			 * BitmapFactory.Options o = new BitmapFactory.Options();
			 * o.inJustDecodeBounds = true; BitmapFactory.decodeStream(new
			 * FileInputStream(f), null, o); final int REQUIRED_SIZE = 70; int
			 * width_tmp = o.outWidth, height_tmp = o.outHeight; int scale = 1;
			 * while (true) { if (width_tmp / 2 < REQUIRED_SIZE || height_tmp /
			 * 2 < REQUIRED_SIZE) break; width_tmp /= 2; height_tmp /= 2;
			 * scale++; }
			 * 
			 * BitmapFactory.Options o2 = new BitmapFactory.Options();
			 * o2.inSampleSize = scale; return BitmapFactory.decodeStream(new
			 * FileInputStream(f), null, o2);
			 */
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	PhotosQueue photosQueue = new PhotosQueue();

	public void stopThread() {
		photoLoaderThread.interrupt();
	}

	// stores list of photos to download
	class PhotosQueue {
		private Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

		// removes all instances of this ImageView
		public void Clean(ImageView image) {
			for (int j = 0; j < photosToLoad.size();) {
				if (photosToLoad.get(j).imageView == image)
					photosToLoad.remove(j);
				else
					++j;
			}
		}
	}

	class PhotosLoader extends Thread {
		public void run() {
			try {
				while (true) {
					// thread waits until there are any images to load in the
					// queue
					if (photosQueue.photosToLoad.size() == 0)
						synchronized (photosQueue.photosToLoad) {
							photosQueue.photosToLoad.wait();
						}
					if (photosQueue.photosToLoad.size() != 0) {
						PhotoToLoad photoToLoad;
						synchronized (photosQueue.photosToLoad) {
							photoToLoad = photosQueue.photosToLoad.pop();
						}
						Bitmap bmp = getBitmap(photoToLoad.url);
						if(bmp != null){
							cache.put(relativeURLof(photoToLoad.url), bmp);

							if (((String) photoToLoad.imageView.getTag()).equals(photoToLoad.url)) {
								BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad.imageView);
								Activity a = (Activity) photoToLoad.imageView.getContext();
								a.runOnUiThread(bd);
							}
						}
						
					}
					if (Thread.interrupted())
						break;
				}
			} catch (InterruptedException e) {
				Log.e(LOG_TAG, "What? ~~~> " + e.getMessage());
			}
		}
	}

	PhotosLoader photoLoaderThread = new PhotosLoader();

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		ImageView imageView;

		public BitmapDisplayer(Bitmap b, ImageView i) {
			bitmap = b;
			imageView = i;
		}

		public void run() {
			if (bitmap != null)
				imageView.setImageBitmap(bitmap);
			else
				imageView.setImageResource(image_id);
		}
	}

	public void clearCache() {
		// clear memory cache
		cache.clear();

		// clear SD cache
		File[] files = cacheDir.listFiles();
		for (File f : files)
			f.delete();
	}

	public HashMap<String, Bitmap> getHashmap() {
		return this.cache;
	}

	public boolean isAlive() {
		return photoLoaderThread.isAlive();
	}

	public static void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
}
