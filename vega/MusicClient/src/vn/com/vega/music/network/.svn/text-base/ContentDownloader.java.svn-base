/***********************************************************************
 * Module:  ContentDownloader.java
 * Author:  SonNH
 * Purpose: Defines the Class ContentDownloader
 ***********************************************************************/

package vn.com.vega.music.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import vn.com.vega.music.utils.VegaLog;

/*import org.apache.http.entity.mime.HttpMultipartMode;
 import org.apache.http.entity.mime.MultipartEntity;
 import org.apache.http.entity.mime.content.ByteArrayBody;
 import org.apache.http.entity.mime.content.StringBody;*/

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class ContentDownloader implements Runnable {

	protected static final String LOG_TAG = ContentDownloader.class
			.getSimpleName();
	protected static final String USER_AGENT = "Mozilla/5.0 (Linux; U; Android 2.1-update1; de-de; HTC Desire 1.19.161.5 Build/ERE27) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17";
	protected static final int TIMEOUT = 10000; // Http request time out: 10s

	protected Object lock = new Object(); // Lock object
	protected String url; // Address of server
	protected boolean aborted = false; // Cancel request
	protected boolean completed = false; // Request completed, data is received
	protected boolean requestIsSuccess = false; // To check request success or
												// not
	HttpResponse response = null;
	private HttpGet httpGet = null;
	private HttpPost httpPost = null;
	private URLConnection urlConnection = null;
	private String submit = null; // Submit content for method POST
	private InputStream instream; // InputStream for response data from server
	private int mDeclaredContentLength = 0;
	private boolean wantDownloader = false;

	// tuanhd
	private boolean isImage = false;
	private Bitmap mBitmap;

	RequestStatusListener requestListener = null;
	private static HttpClient shareHttpClient = null;

	/*-------------------------------------- LOCAL METHOD ----------------- */

	protected static HttpClient getHttpClientInstance() {
		if (shareHttpClient == null) {

			HttpParams my_httpParams = new BasicHttpParams();
			my_httpParams.setParameter(CoreProtocolPNames.USER_AGENT,
					USER_AGENT);
			HttpConnectionParams.setConnectionTimeout(my_httpParams, TIMEOUT);
			HttpConnectionParams.setSoTimeout(my_httpParams, TIMEOUT);
			shareHttpClient = new DefaultHttpClient();
		}
		return shareHttpClient;
	}

	protected ContentDownloader(String _url,
			RequestStatusListener _requestListener, boolean _wantDownloader) {
		isImage = false;
		submit = null;
		url = _url;
		requestListener = _requestListener;
		wantDownloader = _wantDownloader;
	}

	protected ContentDownloader(String _url, String _content,
			RequestStatusListener _requestListener, boolean _wantDownloader) {
		isImage = false;
		submit = _content; // Hoai Ngo: 2011-10-18 fix bug NullPointerException
		url = _url;
		requestListener = _requestListener;
		wantDownloader = _wantDownloader;
	}

	protected ContentDownloader(String _url, Bitmap _content,
			RequestStatusListener _requestListener, boolean _wantDownloader) {
		isImage = true;
		mBitmap = _content;
		url = _url;
		requestListener = _requestListener;
		wantDownloader = _wantDownloader;
	}

	/*-------------------------------------- PUBLIC METHOD ----------------- */
	public boolean isFinished() {
		synchronized (lock) {
			return completed;
		}
	}

	public boolean isSuccess() {
		synchronized (lock) {
			return requestIsSuccess;
		}
	}

	public void abort() {
		synchronized (lock) {
			aborted = true;
		}
	}

	public InputStream getInputStream() throws IOException {
		synchronized (lock) {
			if (!completed) {
				return null;
			}
		}

		if (instream != null) {
			return instream;
		}

		try {
			if (response != null) {
				HttpEntity entity = response.getEntity();
				mDeclaredContentLength = (int) entity.getContentLength();
				instream = entity.getContent();
			} else {
				instream = urlConnection.getInputStream();
				mDeclaredContentLength = urlConnection.getContentLength();
			}
		} catch (IOException ioe) {
			throw ioe;
		} catch (Throwable t) {
		}
		return instream;
	}

	public int getContentSize() {
		synchronized (lock) {
			if (!completed) {
				return -1;
			}
		}

		if ((instream == null) && (mDeclaredContentLength == 0)) {
			if (response != null) {
				mDeclaredContentLength = (int) response.getEntity()
						.getContentLength();
			} else {
				mDeclaredContentLength = urlConnection.getContentLength();
			}
		}
		return mDeclaredContentLength;
	}

	public void close() {
		synchronized (lock) {
			requestListener = null;

			if (completed) {
				if (instream != null) {
					try {
						instream.close();
					} catch (IOException ioe) {
					}
				}

				response = null;
				httpGet = null;
				httpPost = null;
				urlConnection = null;
			} else {
				aborted = true;
			}
		}
	}

	protected void onDone() {
		if (requestListener != null) {
			requestListener.onRequestDone();
		}
	}

	protected String convertResponseToString() {
		try {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Throwable t) {
		}
		return "";
	}

	@Override
	public void run() {
		try {
			synchronized (lock) {
				requestIsSuccess = false;
				completed = false;
			}

			VegaLog.d(LOG_TAG, "Send request to : " + url);

			HttpClient httpclient = getHttpClientInstance();
			if (wantDownloader) {
				URL objUrl = new URL(url);
				urlConnection = objUrl.openConnection();
			} else {
				if (isImage) {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					mBitmap.compress(CompressFormat.JPEG, 75, bos);
					byte[] data = bos.toByteArray();
					ByteArrayBody bab = new ByteArrayBody(data,
							"user_avatar.jpg");
					// File file= new File("/mnt/sdcard/forest.png");
					// FileBody bin = new FileBody(file);
					
					
					MultipartEntity reqEntity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);
					
					
					
					reqEntity.addPart("uploaded", bab);
					reqEntity.addPart("photoCaption", new StringBody(
							"userAvatar"));
					httpPost = new HttpPost(url);
					httpPost.setEntity(reqEntity);
					response = httpclient.execute(httpPost);
					
					HttpEntity entity = response.getEntity();
					String str = EntityUtils.toString(entity);
					int x = 2;
					
				} else {
					if (submit == null) {
						httpGet = new HttpGet(url);
						response = httpclient.execute(httpGet);
						

					} else {
						httpPost = new HttpPost(url);
						StringEntity se = new StringEntity(submit);

						se.setContentEncoding(new BasicHeader(
								HTTP.CONTENT_TYPE, "application/json"));
						
						httpPost.setEntity(se);
						response = httpclient.execute(httpPost);
					}
				}
			}

			synchronized (lock) {
				if (aborted) {

					// Clean up
					response = null;
					httpGet = null;
					httpPost = null;
					urlConnection = null;

					return;
				}

				requestIsSuccess = true;
				onDone();
				completed = true;
			}
		} catch (ClientProtocolException e) {
			requestIsSuccess = false;

			VegaLog.e(LOG_TAG, e.getClass().getName() + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			requestIsSuccess = false;

			VegaLog.e(LOG_TAG, e.getClass().getName() + e.getMessage());
			e.printStackTrace();
		} catch (Throwable t) {
			requestIsSuccess = false;

			t.printStackTrace();
		} finally {
			synchronized (lock) {
				completed = true;
			}
		}
	}
}