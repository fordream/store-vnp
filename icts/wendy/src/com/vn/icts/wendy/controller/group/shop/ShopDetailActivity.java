/**
 * 
 */
package com.vn.icts.wendy.controller.group.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fedorvlasov.lazylist.ImageLoader;
import com.ict.library.activity.BaseActivity;
import com.ict.library.service.SensorService;
import com.ict.library.view.CameraPreview;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.model.Shop;

/**
 * @author tvuong1pc
 * 
 */
public class ShopDetailActivity extends BaseActivity implements OnClickListener {
	private CameraPreview shopDetailtCameraPreview;
	private Camera mCamera;
	private int numberOfCameras;
	private int cameraCurrentlyLocked;
	private Button shopDetail_btn_back;
	// The first rear facing camera
	private int defaultCameraId;
	private TextView tvName;
	private TextView tvComment;
	private TextView tvAddress;
	private TextView tvWebsite;
	private TextView tvnearby;
	private TextView tvPhone;
	private TextView tvPrice;
	private ImageView imgAvatar;

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

			int orientation = (int) values[0];
			// rose.setDirection(orientation);
		}
	};

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.shop_detail);
		shopDetailtCameraPreview = getView(R.id.shopDetailtCameraPreview);
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

		// /
		IntentFilter filter = new IntentFilter(SensorService.SENSSOR_ACTION);
		registerReceiver(broadcastReceiver, filter);

		shopDetail_btn_back = getView(R.id.shopDetail_btn_back);

		// set action
		shopDetail_btn_back.setOnClickListener(this);

		getView(R.id.button1).setOnClickListener(this);

		tvName = getView(R.id.textView1);
		tvComment = getView(R.id.textView2);
		tvAddress = getView(R.id.TextView06);
		tvWebsite = getView(R.id.TextView05);
		tvnearby = getView(R.id.TextView04);
		tvPhone = getView(R.id.TextView03);
		tvPrice = getView(R.id.textView5);
		imgAvatar = getView(R.id.imageView2);
		// show data
		showData();
	}

	private void showData() {
		Shop shop = getIntent().getExtras().getParcelable("SHOP");
		tvName.setText(shop.getName());
		tvAddress.setText("   " + shop.getAddress());
		tvComment.setText(shop.getComment());
		tvPrice.setText("$" + shop.getPrice());
		tvWebsite.setText("   " + shop.getWeb());
		tvPhone.setText("   " + shop.getPhone());
		// nearby

		// imgAvatar
		ImageLoader downloader = new ImageLoader(getParent(),
				R.drawable.transfer);
		downloader.DisplayImage(shop.getUrlImage(), getParent(), imgAvatar);

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

	@Override
	public void onClick(View v) {
		if (v == shopDetail_btn_back) {
			// finish
			finish();
		} else if (v.getId() == R.id.button1) {
			Shop shop = getIntent().getExtras().getParcelable("SHOP");

			if (shop.getPhone() != null && !TextUtils.isEmpty(shop.getPhone())) {
				String number = "tel:" + shop.getPhone();
				Intent callIntent = new Intent(Intent.ACTION_CALL,
						Uri.parse(number));
				startActivity(callIntent);
			}
		}
	}
}
