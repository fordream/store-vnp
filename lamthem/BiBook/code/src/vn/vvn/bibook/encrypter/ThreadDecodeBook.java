package vn.vvn.bibook.encrypter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vn.vvn.bibook.pagecurlgallery.ViewBook2;
import vn.vvn.bibook.util.Parameter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


public class ThreadDecodeBook extends Thread {

	Handler mHandler;
	public final static int STATE_DONE = 0;
	public final static int STATE_RUNNING = 1;
	int mState;
	private Context mContext;
	public int total = 0;
	private String mPath;

	public ThreadDecodeBook(Context context, String path, Handler handler) {
		this.mContext = context;
		mPath = path;
		mHandler = handler;
	}

	public void run() {
		Looper.prepare();
		mState = STATE_RUNNING;
		if (mState == STATE_RUNNING) {
			createFolder(mContext.getFilesDir() + "/Pictures");
			createFolder(mContext.getFilesDir() + "/Sounds");
			// createFolder("/mnt/sdcard/Test/Pictures");
			// createFolder("/mnt/sdcard/Test/Sounds");
			if (decryptFolder(mPath)) {
				total = 1001;
			} else {
				total = 1003;
			}
			Message msg = mHandler.obtainMessage();
			msg.arg1 = total;
			mHandler.sendMessage(msg);
		}
	}

	private void createFolder(String name) {
		File bookPath = new File(name);
		if (!bookPath.exists()) {
			bookPath.mkdir();
		}
	}

	private boolean decryptFolder(String path) {
		// dst /data/data/packagename/files/
		String dst = mContext.getFilesDir().toString();
		// String dst = "/mnt/sdcard/Test";
		Log.e("haipn", "path folder " + dst);
		deleteTempBook();
		File folderExtra = new File(path);
		if (folderExtra.isDirectory() || folderExtra.exists()) {
			File[] fol = folderExtra.listFiles();
			for (File fo : fol) {
				File[] files = fo.listFiles();
				// Log.e("Test", "files " + files);
				try {
					// Log.d("DEL file", fo.getName() + "++++++++++++++++");
					
					if (!fo.getName().equals("Texts")) {

						for (File f : files) {
							Encrypter encrypter = new Encrypter();
							String nameDst = "";
							if (fo.getName().equals("Pictures")) {
								
								if (!f.getName().contains("Cover")) {
									String[] name = f.getName().split("\\.");
									nameDst = dst + "/" + fo.getName() + "/"
											+ name[0] + "." + name[1];
									if (encrypter.decrypt(
											path + "/" + fo.getName() + "/"
													+ f.getName(), nameDst,
											Parameter.getPass(mContext))) {

									} else {
										return false;
									}
									Log.d("haipn", "name dst:" + nameDst);
								} else {
									nameDst = dst + "/" + fo.getName() + "/"
											+ f.getName();
									String cover = path + "/" + fo.getName()
											+ "/" + f.getName();
									byte[] buf = new byte[1024];

									try {
										InputStream in = new FileInputStream(
												cover);

										OutputStream out = new FileOutputStream(
												nameDst);
										int numRead = 0;
										// ghi file
										while ((numRead = in.read(buf)) >= 0) {
											out.write(buf, 0, numRead);
										}
										out.close();
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							} else if (fo.getName().equals("Sounds")) {
								String[] name = f.getName().split("\\.");
								nameDst = dst + "/" + fo.getName() + "/"
										+ name[0] + "." + name[1];
								Log.d("haipn", "name dst:" + nameDst);
								if (encrypter.decrypt(path + "/" + fo.getName()
										+ "/" + f.getName(), nameDst,
										Parameter.getPass(mContext))) {

								} else {
									return false;
								}
							}

						}
					}

				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public int getmState() {
		return mState;
	}

	public void setState(int state) {
		mState = state;
	}
	private void deleteTempBook() {
		File rootImage = new File(mContext.getFilesDir() + "/Pictures");
		File rootSound = new File(mContext.getFilesDir() + "/Sounds");
		try {
			File[] images = rootImage.listFiles();
			for (File file : images) {
				Log.d("haipn", "del file: " + file.getAbsolutePath());
				file.delete();
			}
			images = rootSound.listFiles();
			for (File file : images) {
				Log.d("haipn", "del file: " + file.getAbsolutePath());
				file.delete();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
