package com.vnp.camerakorea;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.vnp.core.view.CommonCameraPreview;

public class MainActivity extends Activity {

	// private Camera cameraObject;
		// private ShowCamera showCamera;
		private ImageView pic;
		CommonCameraPreview cameraPreview;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			pic = (ImageView) findViewById(R.id.imageView1);
			// showCamera = new ShowCamera(this);
			FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
			// preview.addView(showCamera);
			cameraPreview = (CommonCameraPreview) findViewById(R.id.camera);
			// preview.addView(cameraPreview);

		}

		public void snapIt(View view) {
			CommonCameraPreview.TakePictureListener listener = new CommonCameraPreview.TakePictureListener() {

				@Override
				public void takeSucess(Bitmap bitmap) {
					pic.setImageBitmap(bitmap);
				}

				@Override
				public void takeFail() {

				}
			};
			cameraPreview.takePicture(listener);
		}

}
