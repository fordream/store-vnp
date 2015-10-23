/**
 * 
 */
package com.vn.icts.wendy.controller.scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ict.library.activity.BaseActivity;
import com.ict.library.service.LocationParacelable;
import com.ict.library.service.LocationService;
import com.ict.library.service.SensorService;
import com.ict.library.view.CameraPreview;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.view.WingView;
import com.vn.icts.wendy.view.dialog.ScannerDialog;

/**
 * @author tvuong1pc
 * 
 */
public class ScannerActivity extends BaseActivity implements OnClickListener {
	private CameraPreview shopDetailtCameraPreview;
	private Camera mCamera;
	private int numberOfCameras;
	private int cameraCurrentlyLocked;
	private Button shopDetail_btn_back;
	// The first rear facing camera
	private int defaultCameraId;
	private WingView scannerWingview;

	// location store
	// compas
	// private Rose rose;

	// broadcast for receiver sensor change
	// send from SensorService
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (isFinishing()) {
				return;
			}

			Bundle extras = intent.getExtras();
			float[] values = extras.getFloatArray(SensorService.SENSSOR_VALUE);

			// z x y
			int z = (int) values[0];
			int x = (int) values[1];
			int y = (int) values[2];

			scannerWingview.updateWing(x, y, z);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scanner);
		scannerWingview = getView(R.id.scannerWingview);
		shopDetailtCameraPreview = getView(R.id.scannerCameraPreview);
		shopDetailtCameraPreview.setRontate90(true);
		numberOfCameras = Camera.getNumberOfCameras();

		// Find the ID of the default camera
		CameraInfo cameraInfo = new CameraInfo();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
				defaultCameraId = i;
			}
		}

		//
		IntentFilter filter = new IntentFilter(SensorService.SENSSOR_ACTION);
		registerReceiver(broadcastReceiver, filter);

		shopDetail_btn_back = getView(R.id.shopDetail_btn_back);

		// set action
		shopDetail_btn_back.setOnClickListener(this);

		ScannerDialog dialog = new ScannerDialog(this,
				ScannerDialog.TYPE_YES_NO);
		dialog.show();

		dialog = new ScannerDialog(this, ScannerDialog.TYPE_PRESENT);
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		if (shopDetail_btn_back == v) {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Open the default i.e. the first rear facing camera.
		mCamera = Camera.open();

		Camera.Parameters parameters = mCamera.getParameters();
		parameters.set("orientation", "portrait");
		// parameters.setRotation(90);
		// parameters.set("orientation", "landscape");
		parameters.set("rotation", 90);
		parameters.setRotation(90);
		mCamera.setParameters(parameters);
		mCamera.setDisplayOrientation(90);

		cameraCurrentlyLocked = defaultCameraId;
		shopDetailtCameraPreview.setCamera(mCamera);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Because the Camera object is a shared resource, it's very
		// important to release it when the activity is paused.
		if (mCamera != null) {
			shopDetailtCameraPreview.setCamera(null);
			mCamera.release();
			mCamera = null;
		}
	}

}