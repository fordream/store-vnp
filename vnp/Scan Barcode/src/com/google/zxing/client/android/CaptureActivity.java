/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.com.cnc.qrcode.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.client.android.camera.CameraManager;
import com.google.zxing.client.android.result.ResultHandler;
import com.google.zxing.client.android.result.ResultHandlerFactory;

public final class CaptureActivity extends Activity implements
		SurfaceHolder.Callback {
	private static final String TAG = CaptureActivity.class.getSimpleName();
	private static final long INTENT_RESULT_DURATION = 1500L;
	private static final long BULK_MODE_SCAN_DELAY_MS = 1000L;
	private static final float BEEP_VOLUME = 0.10f;
	private static final long VIBRATE_DURATION = 200L;

	public static final String PACKAGE_NAME = "com.google.zxing.client.android";
	private static final String PRODUCT_SEARCH_URL_PREFIX = "http://www.google";
	private static final String PRODUCT_SEARCH_URL_SUFFIX = "/m/products/scan";
	private static final String ZXING_URL = "http://zxing.appspot.com/scan";
	private static final String RETURN_CODE_PLACEHOLDER = "{CODE}";
	private static final String RETURN_URL_PARAM = "ret";
	private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES;
	static {
		DISPLAYABLE_METADATA_TYPES = new HashSet<ResultMetadataType>(5);
		DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.ISSUE_NUMBER);
		DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.SUGGESTED_PRICE);
		DISPLAYABLE_METADATA_TYPES
				.add(ResultMetadataType.ERROR_CORRECTION_LEVEL);
		DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.POSSIBLE_COUNTRY);
	}

	private enum Source {
		NATIVE_APP_INTENT, PRODUCT_SEARCH_LINK, ZXING_LINK, NONE
	}

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private TextView statusView;
	private View resultView;
	private MediaPlayer mediaPlayer;
	private Result lastResult;
	private boolean hasSurface;
	private boolean playBeep;
	private boolean vibrate;
	private boolean copyToClipboard;
	private Source source;
	private String sourceUrl;
	private String returnUrlTemplate;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	@SuppressWarnings("unused")
	private final DialogInterface.OnClickListener aboutListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialogInterface, int i) {
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.zxing_url)));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			startActivity(intent);
		}
	};

	/**
	 * 
	 * @return
	 */
	ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	/**
	 * 
	 * @return
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * 
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.capture);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		resultView = findViewById(R.id.result_view);
		statusView = (TextView) findViewById(R.id.status_view);
		handler = null;
		lastResult = null;
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	/**
	 * 
	 */
	@Override
	protected void onResume() {
		super.onResume();
		resetStatusView();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		Intent intent = getIntent();
		String action = intent == null ? null : intent.getAction();
		String dataString = intent == null ? null : intent.getDataString();
		if (intent != null && action != null) {
			if (action.equals(Intents.Scan.ACTION)) {
				source = Source.NATIVE_APP_INTENT;
				decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
			} else if (dataString != null
					&& dataString.contains(PRODUCT_SEARCH_URL_PREFIX)
					&& dataString.contains(PRODUCT_SEARCH_URL_SUFFIX)) {
				source = Source.PRODUCT_SEARCH_LINK;
				sourceUrl = dataString;
				decodeFormats = DecodeFormatManager.PRODUCT_FORMATS;
			} else if (dataString != null && dataString.startsWith(ZXING_URL)) {
				source = Source.ZXING_LINK;
				sourceUrl = dataString;
				Uri inputUri = Uri.parse(sourceUrl);
				returnUrlTemplate = inputUri
						.getQueryParameter(RETURN_URL_PARAM);
				decodeFormats = DecodeFormatManager
						.parseDecodeFormats(inputUri);
			} else {
				source = Source.NONE;
				decodeFormats = null;
			}
			characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET);
		} else {
			source = Source.NONE;
			decodeFormats = null;
			characterSet = null;
		}
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		playBeep = prefs.getBoolean(PreferencesActivity.KEY_PLAY_BEEP, true);
		if (playBeep) {
			AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
			if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
				playBeep = false;
			}
		}
		vibrate = prefs.getBoolean(PreferencesActivity.KEY_VIBRATE, false);
		copyToClipboard = prefs.getBoolean(
				PreferencesActivity.KEY_COPY_TO_CLIPBOARD, true);
		initBeepSound();
	}

	/**
	 * 
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (source == Source.NATIVE_APP_INTENT) {
				setResult(RESULT_CANCELED);
				finish();
				return true;
			} else if ((source == Source.NONE || source == Source.ZXING_LINK)
					&& lastResult != null) {
				resetStatusView();
				if (handler != null) {
					handler.sendEmptyMessage(R.id.restart_preview);
				}
				return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_FOCUS
				|| keyCode == KeyEvent.KEYCODE_CAMERA) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 
	 */
	@Override
	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	}

	/**
	 * 
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	/**
	 * 
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	/**
	 * 
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/**
	 * 
	 * @param rawResult
	 * @param barcode
	 */
	public void handleDecode(Result rawResult, Bitmap barcode) {
		inactivityTimer.onActivity();
		lastResult = rawResult;
		if (barcode == null) {
			handleDecodeInternally(rawResult, null);
		} else {
			playBeepSoundAndVibrate();
			// drawResultPoints(barcode, rawResult);
			switch (source) {
			case NATIVE_APP_INTENT:
			case PRODUCT_SEARCH_LINK:
				handleDecodeExternally(rawResult, barcode);
				break;
			case ZXING_LINK:
				if (returnUrlTemplate == null) {
					handleDecodeInternally(rawResult, barcode);
				} else {
					handleDecodeExternally(rawResult, barcode);
				}
				break;
			case NONE:
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(this);
				if (prefs.getBoolean(PreferencesActivity.KEY_BULK_MODE, false)) {
					Toast.makeText(this, R.string.msg_bulk_mode_scanned,
							Toast.LENGTH_SHORT).show();
					if (handler != null) {
						handler.sendEmptyMessageDelayed(R.id.restart_preview,
								BULK_MODE_SCAN_DELAY_MS);
					}
					resetStatusView();
				} else {
					handleDecodeInternally(rawResult, barcode);
				}
				break;
			}
		}
	}

	/**
	 * 
	 * @param rawResult
	 * @param barcode
	 */
	private void handleDecodeInternally(Result rawResult, Bitmap barcode) {
		String result = ResultHandlerFactory.makeResultHandler(this, rawResult)
				.getDisplayContents().toString();
		Intent data = new Intent();
		data.putExtra("key", result);
		setResult(Activity.RESULT_OK, data);
		this.finish();
	}

	/**
	 * 
	 * @param rawResult
	 * @param barcode
	 */
	private void handleDecodeExternally(Result rawResult, Bitmap barcode) {
		viewfinderView.drawResultBitmap(barcode);
		ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(
				this, rawResult);
		statusView.setText(getString(resultHandler.getDisplayTitle()));
		if (copyToClipboard) {
			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			clipboard.setText(resultHandler.getDisplayContents());
		}
		if (source == Source.NATIVE_APP_INTENT) {
			Intent intent = new Intent(getIntent().getAction());
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			intent.putExtra(Intents.Scan.RESULT, rawResult.toString());
			intent.putExtra(Intents.Scan.RESULT_FORMAT, rawResult
					.getBarcodeFormat().toString());
			Message message = Message.obtain(handler, R.id.return_scan_result);
			message.obj = intent;
			handler.sendMessageDelayed(message, INTENT_RESULT_DURATION);
		} else if (source == Source.PRODUCT_SEARCH_LINK) {
			Message message = Message
					.obtain(handler, R.id.launch_product_query);
			int end = sourceUrl.lastIndexOf("/scan");
			message.obj = sourceUrl.substring(0, end) + "?q="
					+ resultHandler.getDisplayContents().toString()
					+ "&source=zxing";
			handler.sendMessageDelayed(message, INTENT_RESULT_DURATION);
		} else if (source == Source.ZXING_LINK) {
			Message message = Message
					.obtain(handler, R.id.launch_product_query);
			message.obj = returnUrlTemplate.replace(RETURN_CODE_PLACEHOLDER,
					resultHandler.getDisplayContents().toString());
			handler.sendMessageDelayed(message, INTENT_RESULT_DURATION);
		}
	}

	/**
	 * 
	 */
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);
			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	/**
	 * 
	 */
	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
			displayFrameworkBugMessageAndExit();
			return;
		} catch (RuntimeException e) {
			Log.w(TAG, "Unexpected error initializating camera", e);
			displayFrameworkBugMessageAndExit();
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	/**
	 * 
	 */
	private void displayFrameworkBugMessageAndExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage(getString(R.string.msg_camera_framework_bug));
		builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
		builder.setOnCancelListener(new FinishListener(this));
		builder.show();
	}

	/**
	 * 
	 */
	private void resetStatusView() {
		resultView.setVisibility(View.GONE);
		statusView.setText(R.string.msg_default_status);
		statusView.setVisibility(View.VISIBLE);
		viewfinderView.setVisibility(View.VISIBLE);
		lastResult = null;
	}

	/**
	 * 
	 */
	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}
}