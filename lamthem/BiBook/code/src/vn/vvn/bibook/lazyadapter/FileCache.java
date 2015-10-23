package vn.vvn.bibook.lazyadapter;

import java.io.File;

import android.content.Context;

public class FileCache {

	private File cacheDir;
	private File saveDir;
	private long mIdFile;
	private Context mContext;

	public FileCache(Context context) {
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"LazyList");
		else
			cacheDir = context.getCacheDir();
		mContext = context;
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}


	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		String	scrambleId = String.valueOf(url.hashCode());
		File f = new File(cacheDir, scrambleId);
		return f;

	}
//
//	private String getScrambleId(String url) {
//		//http://dev.d.goo-pi.com/index.php?module=api&action=R0006&session_id=cfee41347b043457aab857cdf7b411c3&user_id=5&scramble_id=fd7bbef82aa8440eb5075ef008c68689&quality=thumb
//		try {
//			String[] params = url.split("&");
//			String scam = params[4];
//			params = scam.split("=");
//			return params[1];
//		} catch (IndexOutOfBoundsException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}


	public void clear() {
		File[] files = cacheDir.listFiles();
		for (File f : files)
			f.delete();
	}

}