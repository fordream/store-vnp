package vn.vvn.bibook.activity;

import java.io.File;

import vn.vvn.bibook.R;
import vn.vvn.bibook.pagecurlgallery.ViewBook2;
import vn.vvn.bibook.util.CheckWifi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class TopScreen extends Activity {

	protected static final int DIALOG_NO_INTERNET = 0;

	private static final String TAG = "topscreen";

	LinearLayout mLlBookshelf;

	LinearLayout mLlReport;
	LinearLayout mLlCreateBook;
	LinearLayout mLlDownload;
	LinearLayout mLlUpload;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d(TAG, "oncreate");

		initComponent();
		initContent();
		setEvent();
	}

	private void initComponent() {
		mLlBookshelf = (LinearLayout) findViewById(R.id.llBookshelf);

		mLlReport = (LinearLayout) findViewById(R.id.llReport);
		mLlCreateBook = (LinearLayout) findViewById(R.id.llCreateBook);
		mLlDownload = (LinearLayout) findViewById(R.id.llDownload);
		mLlUpload = (LinearLayout) findViewById(R.id.llUpload);
	}

	private void initContent() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();
	}
	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		deleteTempBook();
		super.onDestroy();
	}
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_NO_INTERNET:
			AlertDialog.Builder builderMessage = new AlertDialog.Builder(this);
			builderMessage.setMessage(getString(R.string.message_no_internet))
					.setPositiveButton(getString(R.string.OK),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});
			AlertDialog messageDialogWifi = builderMessage.create();
			return messageDialogWifi;
		}
		return super.onCreateDialog(id);
	}

	private void setEvent() {

		mLlBookshelf.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(TopScreen.this, ListBookInCategory.class);
				i.putExtra(ListBookInCategory.CATEGORY_ID, 1);
				startActivity(i);
			}
		});

		// mLlReport = (LinearLayout) findViewById(R.id.llReport);
		mLlCreateBook.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				Intent i = new Intent(TopScreen.this, SetNameBook.class);
//				startActivity(i);
			}
		});
		mLlDownload.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				CheckWifi check = new CheckWifi();
				if (check.check(TopScreen.this)) {
					Intent i = new Intent(TopScreen.this, ListDownload.class);
					startActivity(i);
				} else {
					showDialog(DIALOG_NO_INTERNET);
				}
			}
		});
		mLlUpload.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

			}
		});
	}
	
	
	private void deleteTempBook() {
		File rootImage = new File(this.getFilesDir() + "/Pictures");
		File rootSound = new File(this.getFilesDir() + "/Sounds");
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
