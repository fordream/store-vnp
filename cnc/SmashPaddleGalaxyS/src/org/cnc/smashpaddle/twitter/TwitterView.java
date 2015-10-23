package org.cnc.smashpaddle.twitter;

import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TwitterView extends WebView implements DialogInterface {

	protected String twitterOAuthKey;

	protected String twitterOAuthSecret;

	protected String twitterCallbackURL;

	protected String twitterAccessToken;

	protected String twitterTokenSecret;

	protected String twitterOAuthURL;

	protected Dialog dialog;

	protected DefaultOAuthConsumer consumer;

	protected DefaultOAuthProvider provider;

	private String twitterMessageLoading;

	public TwitterView(Dialog dialog, Context context, String twitterOAuthKey,
			String twitterOAuthSecret, String twitterCallbackURL,
			String tiwwterAccessToken, String twitterTokenSecret) {
		super(context);
		this.dialog = dialog;
		this.twitterOAuthKey = twitterOAuthKey;
		this.twitterOAuthSecret = twitterOAuthSecret;
		this.twitterCallbackURL = twitterCallbackURL;
		this.twitterAccessToken = tiwwterAccessToken;
		this.twitterTokenSecret = twitterTokenSecret;
		setConsumer();
		setProvider();
		TwitterOAuth twitterOAuth = new TwitterOAuth(context);
		setWebViewClient(twitterOAuth);
	}

	public void setConsumer() {
		consumer = new DefaultOAuthConsumer(twitterOAuthKey, twitterOAuthSecret);
	}

	public void setProvider() {
		provider = new DefaultOAuthProvider(
				"http://twitter.com/oauth/request_token",
				"http://twitter.com/oauth/access_token",
				"http://twitter.com/oauth/authorize");
	}

	public DefaultOAuthConsumer getConsumber() {
		return this.consumer;
	}

	public DefaultOAuthProvider getProvider() {
		return this.provider;
	}

	public void doLogin() {
		new RequestToken().execute();
	}

	/**
	 * @param message
	 *            String will be display when loading login page.
	 */
	public void setMessageLoading(String message) {
		twitterMessageLoading = message;
	}

	/**
	 * @return The message will be display when loading login page.
	 */
	public String getMessageLoading() {
		return this.twitterMessageLoading;
	}

	class RequestToken extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				twitterOAuthURL = provider.retrieveRequestToken(consumer,
						twitterCallbackURL);
			} catch (OAuthMessageSignerException e) {
				Log.d(TwitterView.class.toString(), e.getMessage());
				return false;
			} catch (OAuthNotAuthorizedException e) {
				Log.d(TwitterView.class.toString(), e.getMessage());
				return false;
			} catch (OAuthExpectationFailedException e) {
				Log.d(TwitterView.class.toString(), e.getMessage());
				return false;
			} catch (OAuthCommunicationException e) {
				Log.d(TwitterView.class.toString(), e.getMessage());
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				String newString = "https://api.twitter.com/oauth/authorize?"
						+ twitterOAuthURL.substring(twitterOAuthURL
								.indexOf("?") + 1);
				loadUrl(newString);
			} else {
				((TwitterDialog) dialog).dismiss();
				return;
			}
			super.onPostExecute(result);
		}

	}

	class TwitterOAuth extends WebViewClient {
		private ProgressDialog progressDialog;

		public TwitterOAuth(Context context) {
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			if (getMessageLoading() != null)
				progressDialog.setMessage(getMessageLoading());
			else
				progressDialog.setMessage("Loading...");
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			try {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			} catch (Exception e) {
				Log.d(TwitterView.class.toString(), e.getMessage());
			}
			Log.d(TwitterView.class.toString(), url);
			if (url.startsWith(twitterCallbackURL)) {
				Uri uri = Uri.parse(url);
				String oauthVerifier = uri.getQueryParameter("oauth_verifier");
				if (oauthVerifier != null) {
					((TwitterDialog) dialog)
							.setTwitterOAuthVerifier(oauthVerifier);
					dismiss();
				}
			}
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			try {
				if (progressDialog != null) {
					progressDialog.show();
				}
			} catch (Exception e) {
				Log.d(TwitterView.class.toString(), e.getMessage());
			}
			super.onPageStarted(view, url, favicon);
		}
	}

	@Override
	public void cancel() {

	}

	@Override
	public void dismiss() {
		android.webkit.CookieManager.getInstance().removeSessionCookie();
		((TwitterDialog) dialog).dismiss();
		super.destroy();
	}

}