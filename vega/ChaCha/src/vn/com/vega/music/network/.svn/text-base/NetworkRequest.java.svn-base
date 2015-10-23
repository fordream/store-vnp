package vn.com.vega.music.network;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;

import vn.com.vega.music.utils.VegaLog;

public class NetworkRequest extends ContentDownloader {

	protected String responseText = null;

	protected NetworkRequest(String _url, RequestStatusListener _requestListener) {
		super(_url, _requestListener, false);
	}

	protected NetworkRequest(String _url, String _content, RequestStatusListener _requestListener) {
		super(_url, _content, _requestListener, false);
	}

	protected NetworkRequest(String _url, Bitmap _content, RequestStatusListener _requestListener) {
		super(_url, _content, _requestListener, false);
	}
	
	public String getResponseText() {
		return responseText;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public int getContentSize() {
		if (responseText != null) {
			return responseText.length();
		}
		return super.getContentSize();
	}

	@Override
	protected void onDone() {
		responseText = convertResponseToString();
		VegaLog.v(LOG_TAG, "Found response = " + responseText);
		super.onDone();
	}
}
