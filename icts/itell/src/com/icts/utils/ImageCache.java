package com.icts.utils;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.icts.adapter.StampCategoryAdapter;
import com.icts.app.ItelApplication;
import com.icts.object.StampCatObject;
import com.icts.object.StampObject;

public class ImageCache {
	 private static final String TAG = "ImageCache";
	 private static HashMap<String, String> stampCache = new HashMap<String, String>();
	 private static HashMap<String, StampObject> stampNewCache = new HashMap<String, StampObject>();
	 private static HashMap<String, StampCatObject> stampCategoryCache = new HashMap<String, StampCatObject>();
	 private static LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(ItelApplication.CACHE_SIZE) {
		/**
		 * Measure item size in bytes rather than units which is more practical for a bitmap
		 * cache
		 */
		@Override
		protected int sizeOf(String key, Bitmap bitmap) {
			return getBitmapSize(bitmap);
		}
    };
	
	public static void addBitmapToCache(String data, Bitmap bitmap) {
	        if (data == null || bitmap == null) {
	            return;
	        }

	        // Add to memory cache
	        if (mMemoryCache != null && mMemoryCache.get(data) == null) {
	            mMemoryCache.put(data, bitmap);
	        }

    }

    /**
     * Get from memory cache.
     *
     * @param data Unique identifier for which item to get
     * @return The bitmap if found in cache, null otherwise
     */
    public static Bitmap getBitmapFromMemCache(String data) {
        if (mMemoryCache != null) {
            final Bitmap memBitmap = mMemoryCache.get(data);
            if (memBitmap != null) {
//                if (BuildConfig.DEBUG) {
//                    Log.d(TAG, "get Memory cache");
//                }
                return memBitmap;
            }
        }
        return null;
    }

	public static void clearCaches() {
		if (mMemoryCache!=null){
			mMemoryCache.evictAll();
		}
		if (stampCache!=null){
			stampCache.clear();
		}
    }
	 
	private static int getBitmapSize(Bitmap bitmap){
		/* if (Build.VERSION.SDK_INT >= 11) {
	            return bitmap.getByteCount();
	     }*/
		 // Pre HC-MR1
		 return bitmap.getRowBytes() * bitmap.getHeight();
	}
	
	public static void putStamp(StampObject stamp){
		if (stampCache!=null){
			if (!stampCache.containsKey(stamp.getCode())){
				stampCache.put(stamp.getCode(), stamp.getImageLink());
			}
		}
	}
	
	public static void putStampInNewCache(StampObject stamp){
		if (stampNewCache!=null){
			if (!stampNewCache.containsKey(stamp.getCode())){
				stampNewCache.put(stamp.getCode(), stamp);
			}
		}
	}
	
	public static void putCategoryStampInCache(StampCatObject cat){
		if (stampCategoryCache!=null){
			if (!stampCategoryCache.containsKey(cat.getId())){
				stampCategoryCache.put(cat.getId(), cat);
			}
		}
	}
	
	/**
	 * Return link of image
	 * @param key
	 * @return
	 */
	public static StampCatObject getCatStampInCache(String key){
		if (stampCategoryCache!=null){
			if (stampCategoryCache.containsKey(key)){
				return stampCategoryCache.get(key);
			}
		}
		return null;
	}
	
	/**
	 * Return link of image
	 * @param key
	 * @return
	 */
	public static StampObject getStampInNewCache(String key){
		if (stampNewCache!=null){
			if (stampNewCache.containsKey(key)){
				return stampNewCache.get(key);
			}
		}
		return null;
	}
	
	public static boolean hasStampInNewCache(String key){
		return stampNewCache.containsKey(key);
	}
	
	/**
	 * Return link of image
	 * @param key
	 * @return
	 */
	public static String getStamp(String key){
		if (stampCache!=null){
			if (stampCache.containsKey(key)){
				return stampCache.get(key);
			}
		}
		return null;
	}
	
	public static boolean hasStamp(String key){
		return stampCache.containsKey(key);
	}
	/**
	 * remove special in message
	 * @param message
	 * @return
	 */
	public static String subMessage(String message){
		if (message!=null){
			if (message.startsWith(Constant.STAMP_TAG)){
				return message.substring(Constant.STAMP_TAG.length());
			}
		}
		return null;
	}
	
	/**
	 * remove special in message
	 * @param message
	 * @return
	 */
	public static boolean isStamp(String message){
		if (message!=null){
			if (message.startsWith(Constant.STAMP_TAG)){
				return true;
			}
		}
		return false;
	}
}
