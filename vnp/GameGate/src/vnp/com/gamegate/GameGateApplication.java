package vnp.com.gamegate;

import android.app.Application;

import com.vnp.core.common.CommonAndroid;
import com.vnp.core.database.DataStore;

public class GameGateApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		int versionCode = CommonAndroid.getVersionApp(this);
		DataStore.getInstance().init(this);

		int curentVersion = DataStore.getInstance().get("versionCode", 0);

		if (curentVersion != versionCode) {
			CommonAndroid.SHORTCUT commonShortCutUtils = new CommonAndroid.SHORTCUT(
					this);
			commonShortCutUtils.autoCreateShortCut(
					SplashGamegateActivity.class, R.string.app_name,
					R.drawable.ic_launcher);
		}

		DataStore.getInstance().save("versionCode", versionCode);
	}
}