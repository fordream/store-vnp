package org.com.shoppie;

import org.com.shoppie.service.SipService;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.vnp.core.datastore.DataStore;

public abstract class MBaseActivity extends ActionBarActivity {
	protected static final int ID_DIALOG_LOADING_DATA = 1;
	protected static final int ID_DIALOG_NOT_NETWORK = 1;
	protected DataStore dataStore = DataStore.getInstance();

	/**
	 * convert view from resource
	 * 
	 * @param res
	 * @return
	 */
	public <T extends View> T getView(int res) {
		@SuppressWarnings("unchecked")
		T view = (T) findViewById(res);
		return view;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataStore.init(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (ID_DIALOG_LOADING_DATA == id) {
			return ProgressDialog.show(this, null, "loading");
		}
		return super.onCreateDialog(id);
	}

	protected void execute() {
		showDialog(ID_DIALOG_LOADING_DATA);

		new AsyncTask<String, String, String>() {
			Object data;

			@Override
			protected String doInBackground(String... params) {
				data = _doInBackground();
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				_onPostExecute(data);
				dismissDialog(ID_DIALOG_LOADING_DATA);

			}

		}.execute("");
	}

	protected abstract Object _doInBackground();

	protected abstract void _onPostExecute(Object data);

	/***
	 * service
	 */
	private boolean useService = false;

	public void setUseService(boolean useService) {
		this.useService = useService;
	}

	private SipService mService;

	public SipService getSipService() {
		return mService;
	}


}