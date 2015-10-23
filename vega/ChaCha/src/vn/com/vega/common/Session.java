package vn.com.vega.common;

import java.util.Hashtable;

import android.os.Bundle;

public class Session {
	
	public static final String DEF_KEY_HOST = "SESSION_SHARED_KEY_HOST";
	public static final String DEF_KEY_OBJECT = "SESSION_SHARED_KEY_OBJECT";
	
	private static Hashtable<String, Hashtable<String, Object>> sharedHashes;
	
	static {
		sharedHashes = new Hashtable<String, Hashtable<String, Object>>();
	}
	
	/**
	 * Add object to host
	 * @param bundle
	 * @param host
	 * @param object
	 */
	public static void putSharedObject(Bundle bundle, Object host, Object object) {
		synchronized (sharedHashes) {
			String hostKey = "" + host;
			String objectKey = "" + object;
			Hashtable<String, Object> hash = sharedHashes.get(hostKey);
			if (hash == null) {
				hash = new Hashtable<String, Object>();
				sharedHashes.put(hostKey, hash);
			}
			hash.put(objectKey, object);
			bundle.putString(DEF_KEY_HOST, hostKey);
			bundle.putString(DEF_KEY_OBJECT, objectKey);
		}
	}
	
	/**
	 * Get object of a host
	 * @param bundle
	 * @return
	 */
	public static Object getSharedObject(Bundle bundle) {
		synchronized (sharedHashes) {
			Object obj = null;
			if (bundle != null) {
				String hostKey = bundle.getString(DEF_KEY_HOST);
				String objectKey = bundle.getString(DEF_KEY_OBJECT);
				Hashtable<String, Object> hash = sharedHashes.get(hostKey);
				if (hash != null)
					obj = hash.get(objectKey);
			}
			return obj;
		}
	}
	
	/**
	 * Clear all objects of a host
	 * Call this before 'destroyActivity' in an ActivityGroup
	 * @param host
	 */
	public static void clearSharedObjectsOf(Object host) {
		synchronized (sharedHashes) {
			String hostKey = "" + host;
			sharedHashes.remove(hostKey);
		}
	}
}

/**
 * Old implementation
 * 
public class Session {
	
	public static final String DEF_KEY = "SESSION_SHARED_KEY";
	
	private static Hashtable<String, Object> sharedObjects = new Hashtable<String, Object>();
	
	public static void putSharedObject(Bundle bundle, Object host, Object object) {
		synchronized (sharedObjects) {
			String skey = host.getClass().getName() + host; //Genera a random key
			sharedObjects.put(skey, object);
			bundle.putString(DEF_KEY, skey);
		}
	}
	
	public static Object peakAndDelSharedObject(Bundle bundle) {
		synchronized (sharedObjects) {
			Object obj = null;
			if (bundle != null) {
				String key = bundle.getString(DEF_KEY);
				obj = sharedObjects.get(key);
				sharedObjects.remove(key);
			}
			return obj;
		}
	}
}
*/
