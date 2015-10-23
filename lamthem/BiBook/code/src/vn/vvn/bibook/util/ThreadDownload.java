package vn.vvn.bibook.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;

import vn.vvn.bibook.R;
import vn.vvn.bibook.encrypter.Encrypter;
import vn.vvn.bibook.item.Book;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ThreadDownload extends Thread {

	Handler mHandler;
	public final static int STATE_DONE = 0;
	public final static int STATE_RUNNING = 1;
	int mState;

	public int getmState() {
		return mState;
	}

	public int total = 0;
	public String quality;
	public String url;
	public static int TIMEOUT = 60000;
	private String mFilename;
	private Book mBook;
	
	private Context mContext;

	// private ArrayList<Bitmap> listImage = new ArrayList<Bitmap>();
	public ThreadDownload(Context context, Handler h, Book book) {
		mHandler = h;
		mBook = book;
		this.url = context.getString(R.string.url_download_book,
				book.getBookId());
		mFilename = Environment.getExternalStorageDirectory().toString() + "/"
				+ book.getZipFolderName() + ".zip";
		mContext = context;
	}
	

	public void run() {
		Looper.prepare();
		mState = STATE_RUNNING;
		if (mState == STATE_RUNNING) {
			try {
				// ClientGZipContentCompression.main(url);
				downloadFile();
				// downloadFromUrl(url, mFilename);
				// unzip(mFilename);
				extractFile();
				// encryptFile();

			} catch (MalformedURLException e) {
				total = -1;
				e.printStackTrace();
			} catch (IOException e) {
				total = -1;
				e.printStackTrace();
			} catch (URISyntaxException e) {
				total = -1;
				e.printStackTrace();
			} catch (ZipException e) {
				total = -1;
				e.printStackTrace();
			} catch (JSONException e) {
				total = -1;
				e.printStackTrace();
			}
			if (total == -1) {
				File del = new File(mFilename);
				del.delete();
			}
			Message msg = mHandler.obtainMessage();
			msg.arg1 = total;
			mHandler.sendMessage(msg);
		}
	}

	// private void encryptFile() {
	// String sdcard = Environment.getExternalStorageDirectory().toString();
	// for (int i = 0; i < mBook.getListCategoryId().size(); i++) {
	// String path = sdcard
	// + mContext.getString(R.string.path_to_category, mBook
	// .getListCategoryId().get(i));
	// createFolder(path + "/" + mBook.getBookId());
	// createFolder(path + "/" + mBook.getBookId() + "/Pictures");
	// createFolder(path + "/" + mBook.getBookId() + "/Sounds");
	// createFolder(path + "/" + mBook.getBookId() + "/Texts");
	// String folderExtra = sdcard + "/" + mBook.getZipFolderName();
	// String folderExtraPic = sdcard + "/" + mBook.getZipFolderName()
	// + "/Pictures";
	// String folderExtraSou = sdcard + "/" + mBook.getZipFolderName()
	// + "/Sounds";
	//
	// String folderExtraText = sdcard + "/" + mBook.getZipFolderName()
	// + "/Texts";
	// Log.e("path", "paht " + folderExtraPic);
	// Log.e("path", "paht " + folderExtraSou);
	// Log.e("path", "paht " + folderExtraText);
	//
	// encryptFolder(folderExtraPic, path + "/" + mBook.getBookId()
	// + "/Pictures");
	//
	// encryptFolder(folderExtraSou, path + "/" + mBook.getBookId()
	// + "/Sounds");
	// encryptFolder(folderExtraText, path + "/" + mBook.getBookId()
	// + "/Texts");
	// File info = new File(folderExtra + "/info.xml");
	// info.renameTo(new File(path + "/" + mBook.getBookId()));
	// }
	// }

	private void extractFile() throws ZipException {
		String sdcard = Environment.getExternalStorageDirectory().toString();
		for (int i = 0; i < mBook.getListCategoryId().size(); i++) {
			String path = sdcard
					+ mContext.getString(R.string.path_to_category, mBook
							.getListCategoryId().get(i));
			// Log.d("haipn", "path:" + path);
			File pathCat = new File(path);
			if (!pathCat.exists())
				pathCat.mkdir();
			ZipFile zip = new ZipFile(mFilename);
			zip.extractAll(sdcard);
			// Log.d("test", "extract finish");

			createFolder(path + "/" + mBook.getBookId());
			createFolder(path + "/" + mBook.getBookId() + "/Pictures");
			createFolder(path + "/" + mBook.getBookId() + "/Sounds");
			createFolder(path + "/" + mBook.getBookId() + "/Texts");
			String folderExtra = sdcard + "/" + mBook.getZipFolderName();
			encryptFolder(folderExtra, path + "/" + mBook.getBookId());
			File info = new File(folderExtra + "/info.xml");
			info.renameTo(new File(path + "/" + mBook.getBookId() + "/info.xml"));
			new File(mFilename).delete();
			delFolder(new File(folderExtra));

		}
		//

	}

	private void createFolder(String name) {
		File bookPath = new File(name);
		if (!bookPath.exists()) {
			bookPath.mkdir();
		}
	}

	private void encryptFolder(String path, String dst) {
		// Log.e("Test", "path folder " + path);
		File folderExtra = new File(path);
		if (folderExtra.isDirectory() || folderExtra.exists()) {
			File[] fol = folderExtra.listFiles();
			for (File fo : fol) {

				File[] files = fo.listFiles();
				// Log.e("Test", "files " + files);
				try {
					// Log.d("encode file", fo.getName() + "++++++++++++++++");
					if (!fo.getName().equals("Texts")) {
						for (File f : files) {
							// Log.d("encode file",
							// f.getName() + " ----------------" + dst
							// + "/" + fo.getName() + "/"
							// + f.getName());
							// String name[] = f.getName().split(".")
							if (!f.getName().contains("Cover")) {
								Encrypter encrypter = new Encrypter();
								encrypter.encrypt(path + "/" + fo.getName()
										+ "/" + f.getName(),
										dst + "/" + fo.getName() + "/"
												+ f.getName()
												+ ".vvn",
										Parameter.getPass(mContext));
							} else {
								File info = new File(path + "/" + fo.getName()
										+ "/" + f.getName());
								info.renameTo(new File(dst + "/" + fo.getName()
										+ "/" + f.getName()));
							}
						}
					} else {
						for (File f : files) {
							File info = new File(path + "/" + fo.getName()
									+ "/" + f.getName());
							info.renameTo(new File(dst + "/" + fo.getName()
									+ "/" + f.getName()));
						}
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void delFolder(File pathCat) {

		if (pathCat.isDirectory() || pathCat.exists()) {
			File[] fol = pathCat.listFiles();
			for (File fo : fol) {

				File[] files = fo.listFiles();
				try {
					for (File f : files) {
						// Log.d("DEL file", f.getName() + " ----------------");
						f.delete();
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				fo.delete();
			}
			pathCat.delete();
		}
	}

	public void downloadFile() throws MalformedURLException, IOException,
			URISyntaxException, JSONException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(new URL(url).toURI());

		// // Add your data
		// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		// nameValuePairs.add(new BasicNameValuePair("device", "android"));
		// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		//
		// // Execute HTTP Post Request
		// HttpResponse responseGetUrl = httpclient.execute(httppost);
		// InputStream is = responseGetUrl.getEntity().getContent();
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(is));
		// StringBuilder sb = new StringBuilder();
		// String line = null;
		// line = reader.readLine();
		// int i = line.indexOf('{');
		// line = line.substring(i);
		// Log.d("haipn", "json download:" + line);
		// JSONObject urlDownload = new JSONObject(line);
		// String link = urlDownload.getString("url");
		// Log.d("haipn", "link download: " + link);

		// is.close();
		HttpGet httpRequest = null;
		httpRequest = new HttpGet(new URL(url).toURI());
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, TIMEOUT);
		// HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);

		HttpEntity entity = response.getEntity();
		InputStream instream = entity.getContent();
		FileOutputStream fos = new FileOutputStream(mFilename);
		Log.e("test", "mFile name " + mFilename);
		BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
		byte[] data = new byte[1024];
		int x = 0;
		while ((x = instream.read(data, 0, 1024)) >= 0) {
			bout.write(data, 0, x);
		}
		bout.close();
		instream.close();
	}

	// public void unzip(String zipFile) throws ZipException, IOException {
	//
	// System.out.println(zipFile);
	// ;
	// int BUFFER = 2048;
	// File file = new File(zipFile);
	//
	// ZipFile zip = new ZipFile(file);
	// String newPath = zipFile.substring(0, zipFile.length() - 4);
	//
	// new File(newPath).mkdir();
	// Enumeration zipFileEntries = zip.entries();
	//
	// // Process each entry
	// while (zipFileEntries.hasMoreElements()) {
	// // grab a zip file entry
	// ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	//
	// String currentEntry = entry.getName();
	//
	// File destFile = new File(newPath, currentEntry);
	// destFile = new File(newPath, destFile.getName());
	// File destinationParent = destFile.getParentFile();
	//
	// // create the parent directory structure if needed
	// destinationParent.mkdirs();
	// if (!entry.isDirectory()) {
	// BufferedInputStream is = new BufferedInputStream(
	// zip.getInputStream(entry));
	// int currentByte;
	// // establish buffer for writing file
	// byte data[] = new byte[BUFFER];
	//
	// // write the current file to disk
	// FileOutputStream fos = new FileOutputStream(destFile);
	// BufferedOutputStream dest = new BufferedOutputStream(fos,
	// BUFFER);
	//
	// // read and write until last byte is encountered
	// while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
	// dest.write(data, 0, currentByte);
	// }
	// dest.flush();
	// dest.close();
	// is.close();
	// }
	// if (currentEntry.endsWith(".zip")) {
	// // found a zip file, try to open
	// unzip(destFile.getAbsolutePath());
	// }
	// }
	// }

	public void setState(int state) {
		mState = state;
	}
}
