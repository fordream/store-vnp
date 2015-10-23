package com.icts.itel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.icts.app.ItelApplication;
import com.icts.database.CheckUser;
import com.icts.object.User;
import com.icts.utils.Constant;
import com.icts.utils.Utils;
import com.icts.viewcustom.ChoseNet;
import com.icts.viewcustom.InputMailToServer;

public class Register extends Activity {
	public LinearLayout llMain;
	private CheckUser mCheckUser;
	private ProgressDialog mProgress;
	private Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == Constant.CHECK_SUCCESS) {
				Intent in = new Intent(Register.this, MainActivity.class);
				startActivity(in);
				finish();
			} else {
				// ChoseNet chose = new ChoseNet(llMain.getContext(),
				// Register.this);
				// changeViewLL(chose.getmView());
				InputMailToServer inputMailToServer = new InputMailToServer(
						getBaseContext(), Register.this);
				changeViewLL(inputMailToServer.getmView());
			}
			if (mProgress != null) {
				mProgress.dismiss();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		Constant.SCREEN_WIDTH = displaymetrics.widthPixels;
		Constant.SCREEN_HEIGHT = displaymetrics.heightPixels;
		ItelApplication.numMessages = 0;
		setContentView(R.layout.register);
		// JsonAnalysis.getFriendsList("11",
		// "ccf4766507a1b47bbc321fa995687d6a");
		initView();
		initData();
		// test2();
	}

	public void initView() {
		llMain = (LinearLayout) findViewById(R.id.register_llmain);
	}

	// //
	// //add View
	public void changeViewLL(View view) {
		llMain.removeAllViews();
		LayoutParams pParrams = new LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		llMain.addView(view, pParrams);

	}

	public void initData() {
		mCheckUser = new CheckUser(getApplicationContext());
		// if (BuildConfig.DEBUG){
		// Log.i("NDT",
		// "user_id "+mCheckUser.getUserId()+" uuid "+mCheckUser.getUuid());
		// }
		if ((mCheckUser.getUuid() == null) || (mCheckUser.getUserId() == null)
				|| mCheckUser.getUuid().equals("")
				|| mCheckUser.getUserId().equals("")) {
			InputMailToServer inputMailToServer = new InputMailToServer(
					getBaseContext(), Register.this);
			changeViewLL(inputMailToServer.getmView());
		} else {
			// Check user
			mProgress = new ProgressDialog(this);
			mProgress.setMessage("Checking user...");
			mProgress.show();
			Thread check = new Thread(checkUser);
			check.start();

		}

	}

	private Runnable checkUser = new Runnable() {
		@Override
		public void run() {
			mProgress.show();
			if (Utils.hasNetwork(Register.this)) {
				User user = Utils.getCheckUser(mCheckUser.getUuid());
				if (user != null) {
					if (!mCheckUser.getUserId().equalsIgnoreCase(
							user.getUserId())) {
						mCheckUser.addserId(user.getUserId());
						ItelApplication.user_id = Integer.parseInt(user
								.getUserId());
					}
					ItelApplication.user = user;
					ItelApplication.uuid = mCheckUser.getUuid();
					ItelApplication.setTime(user.getStatusUpdateTime());
					mhandler.sendEmptyMessage(Constant.CHECK_SUCCESS);
				} else {
					// mCheckUser.clearData();
					ItelApplication.user = null;
					mhandler.sendEmptyMessage(Constant.CHECK_DATA_INVALID);
				}
			} else {
				mhandler.sendEmptyMessage(Constant.CHECK_NETWORK_ERROR);
			}
		}
	};

	// /////////////////////////////
	public void showExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
		builder.setMessage("Are you sure you want to exit?")
				.setCancelable(false)
				.setTitle("EXIT")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								Register.this.finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.setIcon(R.drawable.ic_launcher);
		alert.show();
	}

	@Override
	public boolean onKeyDown(int pKeyCode, KeyEvent pEvent) {
		if (pKeyCode == KeyEvent.KEYCODE_HOME
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {

		} else if (pKeyCode == KeyEvent.KEYCODE_BACK
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			showExitDialog();

			return true;
		}
		return super.onKeyDown(pKeyCode, pEvent);
	}

}
