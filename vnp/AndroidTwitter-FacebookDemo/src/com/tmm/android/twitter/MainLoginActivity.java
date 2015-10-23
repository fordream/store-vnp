package com.tmm.android.twitter;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.gmail.GmailGender;
import com.gmail.GmailGender.CallBack;
import com.vnp.core.facebook.DialogError;
import com.vnp.core.facebook.Facebook;
import com.vnp.core.facebook.Facebook.DialogListener;
import com.vnp.core.facebook.FacebookError;
import com.vnp.core.twitter.TwitterApp;
import com.vnp.core.twitter.TwitterApp.TwDialogListener;

public class MainLoginActivity extends Activity implements OnClickListener {

	TwitterApp twitterApp;
	Facebook facebook;
	Button btnFacebook;
	Button btnGmail;
	Button btnTwiter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.setProperty("http.keepAlive", "false");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_login);

		btnFacebook = (Button) findViewById(R.id.button1);
		btnGmail = (Button) findViewById(R.id.Button01);
		btnTwiter = (Button) findViewById(R.id.Button02);

		init();

		setUp(btnFacebook);
		setUp(btnGmail);
		setUp(btnTwiter);
		btnFacebook.setOnClickListener(this);
		btnGmail.setOnClickListener(this);
		btnTwiter.setOnClickListener(this);

		WebView webView = (WebView) findViewById(R.id.web);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			
			@Override
			public void onPageFinished(WebView view, String url) {

				super.onPageFinished(view, url);
			}
		});
		webView.loadUrl("http://mail.google.com");

	}

	GmailGender gmailGender;

	private void setUp(Button btnFacebook2) {
		if (btnGmail == btnFacebook2) {
			if (gmailGender.getGoogleToken().equals("")) {
				btnGmail.setText("Login Gmail");
			} else {
				btnGmail.setText("Logout Gmail");
			}
		} else if (btnFacebook == btnFacebook2) {

			btnFacebook.setText("Login facebook");
			if (facebook.getAccessToken() != null) {
				btnFacebook.setText("Logout Facebook");
			}
		} else if (btnTwiter == btnFacebook2) {
			btnTwiter.setText("Login Twiter");
			if (twitterApp.hasAccessToken()) {
				btnTwiter.setText("Logout TWiter");
			}
		}

	}

	private void init() {
		twitterApp = new TwitterApp(this, "fnubma6onRcwbdjPW51A",
				"m9ZRaibyeTvyMSHFREDNtqg3dOHMRjQuLkVHaTwiYpo");
		twitterApp.setListener(new TwDialogListener() {
			@Override
			public void onError(String arg0) {
				setUp(btnTwiter);
			}

			@Override
			public void onComplete(String arg0) {
				setUp(btnTwiter);
			}
		});

		facebook = new Facebook("429606567129217");
		gmailGender = new GmailGender(this);
		gmailGender.setCallBack(new CallBack() {

			@Override
			public void sucess() {
				setUp(btnGmail);
				Builder builder = new Builder(MainLoginActivity.this);
				builder.setMessage(gmailGender.getGoogleToken());
				builder.show();
			}

			@Override
			public void error(String message) {
				Builder builder = new Builder(MainLoginActivity.this);
				builder.setMessage(message);
				builder.show();
				setUp(btnGmail);
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == btnGmail) {
			if (gmailGender.getGoogleToken().equals("")) {
				gmailGender.authent("vnp.mr.truong@gmail.com", "");
			} else {
				gmailGender.clear();
				setUp(btnGmail);
			}
		} else if (v == btnFacebook) {
			if (facebook.getAccessToken() == null) {
				facebook.authorize(MainLoginActivity.this,
						new DialogListener() {

							@Override
							public void onFacebookError(FacebookError arg0) {

							}

							@Override
							public void onError(DialogError arg0) {

							}

							@Override
							public void onComplete(Bundle arg0) {
								setUp(btnFacebook);
							}

							@Override
							public void onCancel() {

							}
						});
			} else {
				try {
					facebook.logout(this);
				} catch (MalformedURLException e) {
				} catch (IOException e) {
				}
			}
		} else if (v == btnTwiter) {
			if (!twitterApp.hasAccessToken()) {
				twitterApp.authorize();
			} else {
				twitterApp.logout();
				setUp(btnTwiter);
			}
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}
}
