package vn.com.vega.music.device;

import java.io.File;
import java.util.Stack;

import vn.com.vega.music.database.DatabaseManager;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * 
 * @author khainv
 * @since 09/2011
 */
public class FileStorage implements Const {
	private static final String APP_PACKAGE = "ChaCha";

	private static final String LOG_TAG = Const.LOG_PREF + FileStorage.class.getSimpleName();

	private static final String STRING_VALUE = "";
	private static final long DEFAULT_VALUE = 1;
	public static final long MBYTE = 1024 * 1024;

	private static File mCacheDir;
	private static File mAvatarDir;
	protected boolean isExternalStorageMounted = false;

	public FileStorage() {
		// TODO Auto-generated constructor stub
		if (isExtMounted()) {
			File externalStorage = Environment.getExternalStorageDirectory();
			File mRootDir = new File(externalStorage, APP_PACKAGE);
			if (!mRootDir.exists()) {
				if (!mRootDir.mkdir()) {
					Log.e(LOG_TAG, "Can not create data directory... SD Card is read-only");
				}
			}
			mCacheDir = new File(externalStorage, APP_PACKAGE + "/" + FOLDER_MUSIC);
			if (!mCacheDir.exists()) {
				if (!mCacheDir.mkdir()) {
					Log.e(LOG_TAG, "Can not create data directory... SD Card is read-only");
				}
			}
			mAvatarDir = new File(externalStorage, APP_PACKAGE + "/" + FOLDER_AVATAR);
			if (!mAvatarDir.exists()) {
				if (!mAvatarDir.mkdir()) {
					Log.e(LOG_TAG, "Can not create avatar directory... SD Card is read-only");
				}
			}
		} else {
			Log.w(LOG_TAG, "Mount sdcard to continute use this app");
		}
	}

	public String getSongCachePath(int songid) {
		// TODO: Get song cached path
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return null;
			}
			File file = new File(mCacheDir, songid + MUSIC_EXT);
			if (file.exists()) {
				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return STRING_VALUE;
	}

	public String getSongCachePath(Song song) {
		// TODO: Get song cached path
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return null;
			}
			File file = new File(mCacheDir, song.title + MUSIC_EXT);
			if (file.exists()) {
				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return STRING_VALUE;
	}

	public String createSongCachePath(int songid) {
		// TODO: Get song cached path
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return null;
			}
			File file = new File(mCacheDir, songid + MUSIC_EXT);
			if (file.exists()) {
				return file.getAbsolutePath();
			} else
				return mCacheDir + "/" + songid + MUSIC_EXT;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return STRING_VALUE;
	}
	
	public String getAvatarCachePath() {
		// TODO: Get song cached path
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return null;
			}
			File file = new File(mAvatarDir, "me.png");
			if (file.exists()) {
				return file.getAbsolutePath();
			} else
				return mAvatarDir + "/" + "me.png";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return STRING_VALUE;
	}

	public String getCacheDirRoot() {
		return mCacheDir.getAbsolutePath();
	}

	public String createSongCachePath(Song song) {
		// TODO: Get song cached path
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return null;
			}
			File file = new File(mCacheDir, song.title + MUSIC_EXT);
			if (file.exists()) {
				return file.getAbsolutePath();
			} else
				return mCacheDir + "/" + song.title + MUSIC_EXT;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return STRING_VALUE;
	}

	public static long getUsedSize() {
		long result = 0L;
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return DEFAULT_VALUE;
			}

			mCacheDir = new File(Environment.getExternalStorageDirectory(), APP_PACKAGE);

			if (!mCacheDir.exists())
				return 0;

			Stack<File> dirlist = new Stack<File>();
			dirlist.clear();
			dirlist.push(mCacheDir);
			while (!dirlist.isEmpty()) {
				File dirCurrent = dirlist.pop();
				File[] fileList = dirCurrent.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					if (fileList[i].isDirectory())
						dirlist.push(fileList[i]);
					else
						result += fileList[i].length();
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DEFAULT_VALUE;
	}

	public static long getVolumeSize() {
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return DEFAULT_VALUE;
			}
			StatFs stat = new StatFs(Environment.getExternalStorageDirectory().toString());
			long totalBytes = (long) stat.getBlockSize() * (long) stat.getBlockCount();
			return totalBytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DEFAULT_VALUE;
	}

	public static long getAvaiableVolume() {
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return DEFAULT_VALUE;
			}
			StatFs stat = new StatFs(Environment.getExternalStorageDirectory().toString());
			long bytesAvaiable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
			return bytesAvaiable;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DEFAULT_VALUE;
	}

	public static boolean removeSongCache(int songid) {
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return false;
			}
			File file = new File(mCacheDir, songid + ".vega");
			if (file.exists()) {
				return file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int removeAllSongCache() {
		// Remove all song cached
		int numbFileDeleted = 0;
		try {
			if (!isExtMounted()) {
				Log.w(LOG_TAG, "SDcard is ejected");
				return -1;
			}

			mCacheDir = new File(Environment.getExternalStorageDirectory(), APP_PACKAGE);

			if (mCacheDir.exists()) {
				Stack<File> dirlist = new Stack<File>();
				dirlist.clear();
				dirlist.push(mCacheDir);
				while (!dirlist.isEmpty()) {
					File dirCurrent = dirlist.pop();
					File[] fileList = dirCurrent.listFiles();
					for (File file : fileList) {
						if (file.isDirectory()) {
							dirlist.push(file);
						} else {
							if (file.delete()) {
								numbFileDeleted++;
							}
						}
					}
				}
				return numbFileDeleted;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String getDatabasePath(Context ctx) {
		// Get database path
		return ctx.getFilesDir().getAbsolutePath() + "/" + DatabaseManager.DATABASE_NAME;
	}

	public static boolean isExtMounted() {
		// Check if external storage is mounted
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
			return true;
		else
			return false;
	}

	public static boolean canWriteToExt() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
			if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				return false;
			} else
				return true;
		else
			return false;
	}
}