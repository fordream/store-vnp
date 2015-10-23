package vn.vvn.bibook.encrypter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class EncrypterActivity extends Activity {
	/** Called when the activity is first created. */
	String pathSdcard = Environment.getExternalStorageDirectory().toString();
	ThreadDecodeBook decode;
	long t;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		t = System.currentTimeMillis();
		decode = new ThreadDecodeBook(this, "/mnt/sdcard/SmartBook/1/240/",bookHandler );
		decode.start();
		// WifiManager wimanager = (WifiManager)
		// getSystemService(Context.WIFI_SERVICE);
		// String address = wimanager.getConnectionInfo().getMacAddress();
		// address = "01:23:45:67:89:ab";
		// Log.e("MAC", "Mac address " + address);
		// Encrypter encrypter = new Encrypter();
		// Log.e("MAC", "MD5 " + encrypter.md5(address).length());
		// try {
		// // Encrypt
		// long t = System.currentTimeMillis();
		// encrypter.encrypt(pathSdcard + "/Sleep Away.mp3", pathSdcard
		// + "/away.mp3", encrypter.md5(address));
		// Log.d("mahoa", "time " + (System.currentTimeMillis() - t));
		// // Decrypt
		// t = System.currentTimeMillis();
		// Log.e("File", "Filesdir " + getFilesDir());
		// if (!encrypter.decrypt(pathSdcard + "/away.mp3", getFilesDir()
		// + "/awaydecode.mp3", encrypter.md5(address))) {
		// Log.e("MES", "Khong the giai ma");
		// }
		// Log.d("mahoa", "time 2 " + (System.currentTimeMillis() - t));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	final Handler bookHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int total = msg.arg1;
			if (total > 0) {
				if (total == 1001) {
					// view sach
					Log.d("time decode", "time "
							+ (System.currentTimeMillis() - t));
				} else {
					// show dialog sach ko co ban quyen
				}
			}
		};
	};
}