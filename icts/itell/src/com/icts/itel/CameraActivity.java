package com.icts.itel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class CameraActivity extends Activity {
	private OrientationEventListener mOrientationEventListener;
	private int mOrientation = -1;

	private static final int ORIENTATION_PORTRAIT_NORMAL = 1;
	private static final int ORIENTATION_PORTRAIT_INVERTED = 2;
	private static final int ORIENTATION_LANDSCAPE_NORMAL = 3;
	private static final int ORIENTATION_LANDSCAPE_INVERTED = 4;
	public static final String PHOTO_SAVE_FILE_PATH = "/sdcard/DCIM/Camera/";

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new CameraViewTest(this));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mOrientationEventListener == null) {
			mOrientationEventListener = new OrientationEventListener(this,
					SensorManager.SENSOR_DELAY_NORMAL) {

				@Override
				public void onOrientationChanged(int orientation) {
					if (orientation >= 315 || orientation < 45) {
						if (mOrientation != ORIENTATION_PORTRAIT_NORMAL) {
							mOrientation = ORIENTATION_PORTRAIT_NORMAL;
						}
					} else if (orientation < 315 && orientation >= 225) {
						if (mOrientation != ORIENTATION_LANDSCAPE_NORMAL) {
							mOrientation = ORIENTATION_LANDSCAPE_NORMAL;
						}
					} else if (orientation < 225 && orientation >= 135) {
						if (mOrientation != ORIENTATION_PORTRAIT_INVERTED) {
							mOrientation = ORIENTATION_PORTRAIT_INVERTED;
						}
					} else { // orientation <135 && orientation > 45
						if (mOrientation != ORIENTATION_LANDSCAPE_INVERTED) {
							mOrientation = ORIENTATION_LANDSCAPE_INVERTED;
						}
					}
				}
			};
		}
		if (mOrientationEventListener.canDetectOrientation()) {
			mOrientationEventListener.enable();
		}
	}

	public class CameraViewTest extends SurfaceView implements
			SurfaceHolder.Callback {

		public CameraViewTest(Context context) {
			super(context);
			_context = context;

			_holder = getHolder();
			if (_holder != null) {
				_holder.addCallback(this);
				_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			}

			this._photofile = makePhotoFileName();
		}

		private String makePhotoFileName() {
			Calendar cal = Calendar.getInstance();
			String filename = String.valueOf(cal.getTimeInMillis());
			return filename;
		}

		public void surfaceCreated(SurfaceHolder holder) {
			try {
				_camera = Camera.open();
				if (_camera != null) {
					// The SD card is not inserted
					if (_context != null) {
						Toast.makeText(_context, "Camare take picture",
								Toast.LENGTH_LONG).show();
					}
					Parameters params = _camera.getParameters();
					if (params != null) {
						final int IMAGEFORMAT_JPEG = 256;
						params.setPictureFormat(IMAGEFORMAT_JPEG);
						// params.setFlashMode(Parameters.FLASH_MODE_ON);
						// params.setFocusMode(Parameters.FOCUS_MODE_AUTO);
						_camera.setParameters(params);
					}
					_camera.setPreviewDisplay(holder);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
			if (_camera != null) {
				Camera.Parameters p = _camera.getParameters();
				List<Camera.Size> previewSize = p.getSupportedPreviewSizes();
				Camera.Size size = previewSize.get(0);
				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1) {
					_camera.setDisplayOrientation(90);
				} else {
					p.set("orientation", "portrait");
					p.set("rotation", 90);
				}
				p.setPreviewSize(size.width, size.height);
				_camera.setParameters(p);

				_camera.startPreview();
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			if (_camera != null) {
				_camera.setPreviewCallback(null);
				_camera.stopPreview();
				_camera.release();
			}
			_camera = null;
			//
			if (_context != null) {
				((Activity) this._context).finish();
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if ((event != null) && (_camera != null)) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					try {
						_camera.takePicture(null, null, mPictureListenerJpeg);
					} catch (Exception e) {
					}
				}
			}
			return true;
		}

		private Camera.PictureCallback mPictureListenerJpeg = new Camera.PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {
				if (data != null) {
					FileOutputStream myFOS = null;
					try {
						File file = new File(PHOTO_SAVE_FILE_PATH);
						if (!file.exists()) {
							file.mkdir();
						}

						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 4;
						Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
								data.length, options);
						Matrix matrix = new Matrix();
						switch (mOrientation) {
						case ORIENTATION_PORTRAIT_NORMAL:
							matrix.postRotate(90);
							break;
						case ORIENTATION_LANDSCAPE_NORMAL:
							matrix.postRotate(0);
							break;
						case ORIENTATION_PORTRAIT_INVERTED:
							matrix.postRotate(270);
							break;
						case ORIENTATION_LANDSCAPE_INVERTED:
							matrix.postRotate(180);
							break;
						}
						int width = getWindowManager().getDefaultDisplay()
								.getWidth();
						int height = 2 * width / 3;
						bmp = scaleImage(bmp, width, height);
						bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
								bmp.getHeight(), matrix, true);
						// save data
						myFOS = new FileOutputStream(PHOTO_SAVE_FILE_PATH
								+ _photofile + ".jpg");
						BufferedOutputStream bos = new BufferedOutputStream(
								myFOS);

						if (bmp != null) {
							bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
						}
						bos.close();
						bos = null;
						myFOS.flush();
						myFOS.close();
						insertGallary(data);
						Toast.makeText(_context,
								"Photo saved " + PHOTO_SAVE_FILE_PATH,
								Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				((Activity) _context).finish();
			}
		};

		private boolean insertGallary(byte[] data) {
			boolean ret = false;
			Uri imageUri = null;
			String path = null;
			if ((data == null) || (data.length == 0)) {
				return false;
			}
			try {
				if (_context != null) {
					ContentResolver cr = _context.getContentResolver();
					if ((cr != null) && (_photofile != null)) {
						path = PHOTO_SAVE_FILE_PATH + _photofile + ".jpg";
						Uri uri = Uri.fromFile(new File(path));
						ContentValues values = new ContentValues();
						values.put(MediaStore.Images.Media.TITLE,
								uri.getLastPathSegment());
						values.put(MediaStore.Images.Media.DISPLAY_NAME,
								uri.getLastPathSegment());
						values.put(MediaStore.Images.Media.MIME_TYPE,
								"image/jpeg");
						values.put(MediaStore.Images.Media.DATA, uri.getPath());
						values.put(MediaStore.Images.Media.DATE_TAKEN,
								System.currentTimeMillis());
						imageUri = cr.insert(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								values);
					}
				}
				setIntentPicture(path);

			} catch (OutOfMemoryError e) {
				Toast.makeText(_context, "OutOfMemoryError", Toast.LENGTH_LONG)
						.show();
			} catch (Exception e) {
			}
			return ret;
		}

		private void setIntentPicture(String path) {
			Intent intent = null;
			Activity act = (Activity) this._context;
			if (act != null) {
				intent = act.getIntent();
			}
			Bundle bundle = null;
			if (intent != null) {
				bundle = intent.getExtras();
			}
			if ((bundle != null)
					&& (bundle.containsKey("INTENT_CAPTURE_AVATAR"))) {
				intent.putExtra("INTENT_CAPTURE_AVATAR", path);
				act.setResult(Activity.RESULT_OK, intent);
			}

		}

		private SurfaceHolder _holder;
		private Camera _camera;
		private Context _context;
		private String _photofile;
	}

	public static Bitmap scaleImage(Bitmap bmp, int width, int height) {
		int w = bmp.getWidth();
		int h = bmp.getHeight();

		double ratioW = (double) width / w;
		double ratioH = (double) height / h;
		double min = ratioH;
		if (ratioW < ratioH) {
			min = ratioH;
		}
		bmp = Bitmap.createScaledBitmap(bmp, (int) (w * min), (int) (h * min),
				true);

		return bmp;
	}
}
