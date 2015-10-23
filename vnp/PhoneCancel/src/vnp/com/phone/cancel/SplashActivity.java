package vnp.com.phone.cancel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ict.library.activity.CommonBaseActivity;
import com.ict.library.common.CommonAndroid;
import com.ict.library.common.CommonShortCutUtils;
import com.ict.library.database.CommonDataStore;

public class SplashActivity extends CommonBaseActivity implements Runnable {

	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		createShortcut();
		handler.postDelayed(this, 1000);
	}

	private void createShortcut() {
		CommonDataStore.getInstance().init(this);
		int version = CommonDataStore.getInstance().get("version", 0);
		int curentVersion = CommonAndroid.getVersionApp(this);

		if (version == 0 || version != curentVersion) {
			// create shortcut
			CommonShortCutUtils commonShortCutUtils = new CommonShortCutUtils(
					this);
			commonShortCutUtils.autoCreateShortCut(SplashActivity.class,
					R.string.app_name, R.drawable.ic_launcher);
		}

		// update version
		version = curentVersion;
		CommonDataStore.getInstance().save("version", version);
	}

	@Override
	protected void onUserLeaveHint() {
		finish();
		super.onUserLeaveHint();
	}

	@Override
	public void run() {
		if (!isFinishing()) {
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		}
	}
}