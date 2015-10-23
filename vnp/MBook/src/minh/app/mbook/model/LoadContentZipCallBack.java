package minh.app.mbook.model;

import java.io.File;

import minh.app.mbook.utils.MbookManager;
import android.content.Context;
import android.util.Log;

import com.vnp.core.common.UnZipExecute;
import com.vnp.core.datastore.DataStore;
import com.vnp.core.service.RequestInfo;
import com.vnp.core.service.RestClient;
import com.vnp.core.service.RestClient.IDownloadUploadFileCallBack;

public class LoadContentZipCallBack extends com.vnp.core.callback.CallBack {
	private Context context;
	private boolean isConnectting = false;

	public LoadContentZipCallBack(Context context) {
		this.context = context;
	}

	@Override
	public Object execute() {
		isConnectting = true;
		String pathContent = "/mnt/sdcard/mBook";

		if (DataStore.getInstance().get("isFirst", true)) {
			// copy 
			
			DataStore.getInstance().save("isFirst", false);
		}
		

		return null;
		// if is first, copy file from
		// RestClient restClient = new RestClient(MbookManager.SERVER_ZIP);
		//
		// RequestInfo info = new RequestInfo();
		// info.setUrl(MbookManager.SERVER_ZIP);
		// info.setFileFolderSaveFile("/mnt/sdcard/mBook");
		// info.setFileNameSave("mBook.zip");
		//
		// restClient.exeDownloadFile(info, new IDownloadUploadFileCallBack() {
		//
		// @Override
		// public void sucess() {
		// // unzip file
		// Log.e("ABC", "sucess");
		// UnZipExecute unZipExecute = new UnZipExecute();
		// unZipExecute.execute("/mnt/sdcard/mBook/mBook.zip",
		// "/mnt/sdcard/mBook/", true);
		// }
		//
		// @Override
		// public void start() {
		// Log.e("ABC", "start");
		// }
		//
		// @Override
		// public void onProcess(long arg0, long arg1) {
		// Log.e("ABC", arg0 + "onProcess" + arg1);
		// }
		//
		// @Override
		// public void error(int arg0) {
		// Log.e("ABC", "error");
		// }
		// });
		//
		// return restClient;
	}

	@Override
	public void onCallBack(Object arg0) {
		isConnectting = false;
	}

	public boolean isConnectting() {
		return isConnectting;
	}
}