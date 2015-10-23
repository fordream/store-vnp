package org.com.vnp.vanga;

import java.util.StringTokenizer;

import android.content.Intent;

import com.ict.library.activity.BaseApplication;
import com.ict.library.asyn.BaseAsynctask;
import com.ict.library.database.CommonDataStore;
import com.ict.library.service.CommonRestClient;
import com.ict.library.service.CommonRestClient.IDownloadUploadFileCallBack;
import com.ict.library.service.CommonRestClient.RequestMethod;
import com.vnp.core.service.v2.RequestInfo;
import com.vnp.core.service.v2.TypeServer;
import com.vnp.core.util.unzip.UnZipExecute;
import com.vnp.core.util.unzip.UnZipInformation;

public class VangaApplication extends BaseApplication {

	public static final String SERVER = "https://app-server.googlecode.com/svn/trunk/app-vanga/";
	public static final String FILE_CONFIG = "vanga.txt";
	private boolean isDownload = false;

	@Override
	public void onCreate() {
		super.onCreate();
		CommonDataStore.getInstance().init(this);
		isDownload = false;
	}

	public void download() {
		if (isDownload) {
			return;
		}

		BaseAsynctask asynctask = new BaseAsynctask() {
			@Override
			protected Object doInBackground(String... params) {
				isDownload = true;
				// download content update
				CommonRestClient client = new CommonRestClient(SERVER
						+ FILE_CONFIG);
				try {
					client.execute(RequestMethod.GET);
				} catch (Exception e) {
				}

				if (client.getResponseCode() == 200) {
					String content = client.getResponse();
					StringTokenizer stringTokenizer = new StringTokenizer(
							content, ";");
					while (stringTokenizer.hasMoreElements() && isDownload) {
						final String fileName = stringTokenizer.nextElement()
								.toString();

						if (!CommonDataStore.getInstance().get(fileName, false)) {
							String serverUrl = SERVER + fileName;

							client = new CommonRestClient(serverUrl);

							RequestInfo requestInfo = new RequestInfo();
							requestInfo.setMethod(RequestMethod.GET);
							requestInfo.setUrl(serverUrl);
							requestInfo.setTypeServer(TypeServer.DOWNLOAD_FILE);
							requestInfo
									.setFileFolderSaveFile(getApplicationContext()
											.getFilesDir().getAbsolutePath());
							requestInfo.setFileNameSave(fileName);


							client.exeDownloadFile(requestInfo,
									new IDownloadUploadFileCallBack() {
										public void sucess() {
											UnZipExecute unZipExecute = new UnZipExecute();
											UnZipInformation unZipInformation = new UnZipInformation();
											unZipInformation
													.setDeleteFileZip(true);
											unZipInformation
													.setPathFolderUnZip(getApplicationContext()
															.getFilesDir()
															.getAbsolutePath());
											unZipInformation
													.setPathZip(getApplicationContext()
															.getFilesDir()
															.getAbsolutePath()
															+ "/" + fileName);
											if (!unZipExecute
													.execute(unZipInformation)) {
												isDownload = false;
											} else {
												CommonDataStore.getInstance()
														.save(fileName, true);
											}
										}

										public void start() {
										}

										public void onProcess(long arg0,
												long arg1) {
										}

										public void error(int arg0) {
											isDownload = false;
										}
									});
						}
					}
				} else {
					isDownload = false;
				}

				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				super.onPostExecute(result);
				updateScreen(isDownload);
				isDownload = false;
			}

		};

		asynctask.execute("");
	}

	private void updateScreen(boolean update) {
		if (update) {
			sendBroadcast(new Intent("screen.update"));
		}
	}

	public boolean isDownload() {
		return isDownload;
	}
}