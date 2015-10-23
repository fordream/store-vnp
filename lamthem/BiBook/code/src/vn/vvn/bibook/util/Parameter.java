/**
 * 
 */
package vn.vvn.bibook.util;

import vn.vvn.bibook.encrypter.Encrypter;
import android.content.Context;
import android.provider.Settings.Secure;
import android.view.animation.AlphaAnimation;

/**
 * @author haipn
 * 
 */
public class Parameter {
	public static AlphaAnimation getAlphaDown() {
		AlphaAnimation alphaDown = new AlphaAnimation(1.0f, .3f);
		alphaDown.setDuration(500);
		alphaDown.setFillAfter(true);
		return alphaDown;
	}

	public static AlphaAnimation getAlphaUp() {

		AlphaAnimation alphaUp = new AlphaAnimation(0.3f, 1.0f);
		alphaUp.setDuration(500);
		alphaUp.setFillAfter(true);
		// alphaUp.setFillBefore(true);
		return alphaUp;
	}
	public static String getPass(Context context){
		String android_id = Secure.getString(context
				.getContentResolver(), Secure.ANDROID_ID);
		Encrypter encrypter = new Encrypter();
//		address = "00:11:22:33:44:55";
		return encrypter.md5(android_id);
	}
	public static ThreadDownload threaddownload = null;
}
