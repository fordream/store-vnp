package org.cnc.smashpaddle.twitter;

import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.LinearLayout;

/**
 * 
 * @author locnx <br>
 *         Class is used for user login to Twitter using OAuth.<br>
 * 
 *         <b>How to use?</b><br>
 *         Create a new Activity with code example below:<br>
 *         <code>
 * <pre>
 * public class Twitter extends Activity{
 * protected void onCreate(Bundle savedInstanceState) {
 * 		super.onCreate(savedInstanceState);
 * 		TwitterDialog twitterDialog = new TwitterDialog(this, twitterOAuthKey, twitterOAuthSecret, 
 *    		twitterCallbackURL, twitterAccessToken, twitterTokenSecret);
 * 		    twitterDialog.doLogin();
 * 		    twitterDialog.setOnDismissListener(new OnDismissListener() {
 * @Override public void onDismiss(DialogInterface dialog) { try{ String
 *           oauthVerifier = ((TwitterDialog)dialog).getTwitterOAuthVerifier());
 *           DefaultOAuthConsumer consumer =
 *           ((TwitterDialog)dialog).getConsumer()); DefaultOAuthProvider
 *           provider = ((TwitterDialog)dialog).getProvider());
 *           }catch(NullpoiterException e){ Debug.LogD(e.getMessage); } } }); }
 *           </pre> </code>
 * 
 */

public class TwitterDialog extends Dialog implements DialogInterface {
	private String twitterOAuthVerifier = null;
	private TwitterView twiterView = null;

	public TwitterDialog(Context context, String twitterOAuthKey,
			String twitterOAuthSecret, String twitterCallbackURL,
			String tiwwterAccessToken, String twitterTokenSecret) {
		super(context, android.R.style.Theme_Black_NoTitleBar);
		LinearLayout ln = new LinearLayout(context);
		ln.setBackgroundColor(Color.WHITE);
		twiterView = new TwitterView(this, context, twitterOAuthKey,
				twitterOAuthSecret, twitterCallbackURL, tiwwterAccessToken,
				twitterTokenSecret);
		ln.addView(twiterView);
		setContentView(ln);
	}

	public TwitterDialog(Context context, String twitterOAuthKey,
			String twitterOAuthSecret, String twitterCallbackURL,
			String tiwwterAccessToken, String twitterTokenSecret,
			String messageLoading) {
		super(context, android.R.style.Theme_Black_NoTitleBar);
		LinearLayout ln = new LinearLayout(context);
		ln.setBackgroundColor(Color.WHITE);
		twiterView = new TwitterView(this, context, twitterOAuthKey,
				twitterOAuthSecret, twitterCallbackURL, tiwwterAccessToken,
				twitterTokenSecret);
		twiterView.setMessageLoading(messageLoading);
		ln.addView(twiterView);
		setContentView(ln);
	}

	public void setMessageLoading(String messageLoading) {
		twiterView.setMessageLoading(messageLoading);
	}

	public void doLogin() {
		twiterView.doLogin();
		this.show();
	}

	public void setTwitterOAuthVerifier(String twitterOAuthVerifier) {
		this.twitterOAuthVerifier = twitterOAuthVerifier;
	}

	public String getTwitterOAuthVerifier() {
		return this.twitterOAuthVerifier;
	}

	public DefaultOAuthConsumer getConsumer() {
		return twiterView.getConsumber();
	}

	public DefaultOAuthProvider getProvider() {
		return twiterView.getProvider();
	}

}