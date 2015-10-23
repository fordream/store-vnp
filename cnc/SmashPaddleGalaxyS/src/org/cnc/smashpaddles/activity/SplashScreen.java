package org.cnc.smashpaddles.activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cnc.smashpaddle.twitter.TwitterApi;
import org.cnc.smashpaddle.twitter.TwitterDialog;
import org.cnc.smashpaddle.twitter.adapter.TwitterSession;
import org.cnc.smashpaddles.facebook.DialogError;
import org.cnc.smashpaddles.facebook.Facebook;
import org.cnc.smashpaddles.facebook.Facebook.DialogListener;
import org.cnc.smashpaddles.facebook.FacebookError;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.OAuthAuthorization;
import twitter4j.util.ImageUpload;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SplashScreen extends Activity {
	private Facebook mFacebook;
	private static final String[] PERMISSIONS = new String[] {
			"publish_stream", "read_stream" };
	String twitterOAuthKey = "jHAaFDzEkyICCpZqSZLWeA";
	String twitterOAuthSecret = "XDRgHBEBOZrfLEveMPCfDUb3nGhL1kzctkRnkCubrg";
	String twitterCallbackURL = "http://cnc.com.vn/";
	String twitterAccessToken = "579846660-RYzowZPh5nNjE3QcvhKF3vF3mJElbICaScyKfqpC";
	String twitterTokenSecret = "2Bq30a2JFyZljWsHLtiI7HEtsBJCi2ATAdPNfpPNE";
	JSONObject jArray;
	ProgressDialog pro;
	
	private ProgressDialog mProgressDialog;
	public static String url;
	@Override
	public void onBackPressed() {
		finish();
	}

	private boolean check = true;
	private Dialog loginDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		// setup value app_id facebook

		pro = new ProgressDialog(this);
		pro.setCancelable(false);
		mFacebook = new Facebook("413680098662330");
		Intent i = getIntent();
		check = i.getBooleanExtra("check", true);
		if (check)
			loginPopUp();
		Button playnowButton = (Button) findViewById(R.id.playnowButton);
		playnowButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						PreGame.class);
				startActivity(intent);
			}
		});

		Button friendsButton = (Button) findViewById(R.id.friendsButton);
		friendsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						FriendsOnline.class);
				startActivity(intent);
			}
		});

		Button boostsButton = (Button) findViewById(R.id.boostsButton);
		boostsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				boostsPopUp();
			}
		});

		Button achievementsButton = (Button) findViewById(R.id.achievementsButton);
		achievementsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						Achievements.class);
				startActivity(intent);
			}
		});

		Button optionsButton = (Button) findViewById(R.id.optionsButton);
		optionsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						Options.class);
				startActivity(intent);
			}
		});

		Button getcoinsButton = (Button) findViewById(R.id.getcoinsButton);
		getcoinsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getcoinsPopUp();
			}
		});

		Button freecoinsButton = (Button) findViewById(R.id.freecoinsButton);
		freecoinsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						FreeCoins.class);
				startActivity(intent);
			}
		});
	}

	public void boostsPopUp() {
		final Dialog dialog = new Dialog(this,
				android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.boots_popup);

		Button circleXBoosts = (Button) dialog.findViewById(R.id.circlXBoosts);
		circleXBoosts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public void getcoinsPopUp() {
		final Dialog dialog = new Dialog(this,
				android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.getcoins_popup);

		Button circleXGetcoins = (Button) dialog
				.findViewById(R.id.circleXGetCoins);
		circleXGetcoins.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	public void loginFacebookAndPostToWall() {
		mFacebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH,
				new LoginFacebookDialogListener());

	}

	class LoginFacebookDialogListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			String message = "I just logged in to Smash Paddles, download it now "
					+ "on the app store or for any Android device bbb";
			postToWall(message);
			Log.i("fffffffffffffffffffff",
					"loginmmmmmm" + mFacebook.getAccessToken());
			postOnFriendsWall();
		}

		@Override
		public void onFacebookError(FacebookError error) {
		}

		@Override
		public void onError(DialogError error) {
		}

		@Override
		public void onCancel() {
		}
	}

	public void postToWall(String message) {
		Bundle parameters = new Bundle();
		parameters.putString("message", message + " ");
		try {
			mFacebook.request("me");
			byte[] data = null;
			Bitmap bi = BitmapFactory.decodeFile("/sdcard/logo_splash.png");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bi.compress(Bitmap.CompressFormat.PNG, 20, baos);
			data = baos.toByteArray();
			parameters.putByteArray("picture", data);
			String response = mFacebook
					.request("me/photos", parameters, "POST");

			Log.i("fffffffffffffffffffff", "postToWallvvvvvvvvvvvvvvvvvvvv"
					+ mFacebook.getAccessToken());
			jArray = getFrienList(mFacebook.getAccessToken());
			if (response == null || response.equals("")
					|| response.equals("false")) {
				Log.d("Post to Facebook Wall", "Failed to post to wall");
			} else {
				Log.d("Post Facebook Wall",
						"Message posted to your facebook wall!");
				Toast.makeText(this, "posted facebook wall successful!",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e("Post Facebook Wall", "Failed to post to wall!");
		}
	}

	public void loginTwitterAndPost() {
		TwitterDialog twitterDialog = new TwitterDialog(this, twitterOAuthKey,
				twitterOAuthSecret, twitterCallbackURL, twitterAccessToken,
				twitterTokenSecret);
		twitterDialog.doLogin();
		twitterDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				
				TwitterSession twitterSession = new TwitterSession(
						SplashScreen.this);
				try {
					Log.i("oan ta la van", "ggg" +url);
					String message = "oan ta la van  " + Math.random()+ "deck  " +url;
					String oauthVerifier = ((TwitterDialog) dialog)
							.getTwitterOAuthVerifier();

					TwitterApi twitter = new TwitterApi(SplashScreen.this,
							oauthVerifier, pro);
					twitter.postStatus(message, oauthVerifier,
							((TwitterDialog) dialog).getConsumer(),
							((TwitterDialog) dialog).getProvider());
					twitterSession.storeAccessToken(new AccessToken(
							twitterAccessToken, twitterTokenSecret),
							twitterSession.getUsername());
				} catch (NullPointerException e) {
					Log.d("Twitter", e.getMessage());
				}
			}
		});
	}

	public void loginPopUp() {
		loginDialog = new Dialog(this,
				android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		loginDialog.setContentView(R.layout.login_popup);

		loginDialog.show();
		loginDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				SplashScreen.this.finish();
			}
		});

		Button loginButton = (Button) loginDialog
				.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// loginFacebookAndPostToWall();
				new ImageSender().execute();
				loginTwitterAndPost();

				// loginDialog.dismiss();

			}
		});
		Button close = (Button) loginDialog.findViewById(R.id.loginClose);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				loginDialog.dismiss();
			}
		});
	}

	public void postOnFriendsWall() {
		Bundle parameters = null;
		try {
			JSONArray contacts = null;
			contacts = jArray.getJSONArray("data");
			for (int i = 0; i < contacts.length(); i++) {
				JSONObject c = contacts.getJSONObject(i);
				String id = c.getString("id");
				byte[] data = null;
				parameters = new Bundle();
				Bitmap bi = BitmapFactory.decodeFile("/sdcard/num.png");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bi.compress(Bitmap.CompressFormat.JPEG, 20, baos);
				data = baos.toByteArray();

				// parameters.putString("to", id + "");
				parameters.putString("link", "http://cnc.com.vn");
				parameters.putString("caption",
						"{*actor*} I just logged in to Smash Paddles, download it now "
								+ "on the app store or for any Android device");
				parameters.putString("name", "CNC soft pro");
				parameters.putString("method", "photos.upload");
				// parameters.putString("privacy", "{value: 'ALL_FRIENDS'}");

				parameters.putByteArray("picture", data);
				// parameters.putString("picture",

			}
			mFacebook.dialog(this, "feed", parameters,
					new LoginFacebookDialogListener());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public JSONObject getFrienList(String token) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet httppost = new HttpGet(
					"https://graph.facebook.com/me/friends?access_token="
							+ token);
			org.apache.http.HttpResponse response = httpclient
					.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("ERROR", "Error in http connection " + e.toString());
		}
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.i("RESULT", result);
		} catch (Exception e) {
			Log.e("ERROR", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {

			jArray = new JSONObject(result);
			/*
			 * JSONArray contacts = null; contacts =
			 * jArray.getJSONArray("data"); for(int i = 0; i <
			 * contacts.length(); i++){ JSONObject c =
			 * contacts.getJSONObject(i);
			 * 
			 * // Storing each json item in variable String id =
			 * c.getString("id"); Log.i("IDDDDDDDDDDDDD",""+id);}
			 */
			Log.i("RESULT", "JSON ARRAY" + jArray.toString());

		} catch (JSONException e) {
			Log.e("ERROR", "Error parsing data " + e.toString());
		}

		return jArray;
	}

	private class ImageSender extends AsyncTask<URL, Integer, Long> {
		

		protected void onPreExecute() {
			mProgressDialog = ProgressDialog.show(SplashScreen.this, "",
					"Sending image...", true);

			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		protected Long doInBackground(URL... urls) {
			long result = 0;

			TwitterSession twitterSession = new TwitterSession(
					SplashScreen.this);
			AccessToken accessToken = twitterSession.getAccessToken();

			Configuration conf = new ConfigurationBuilder()
					.setOAuthConsumerKey("jHAaFDzEkyICCpZqSZLWeA")
					.setOAuthConsumerSecret(
							"XDRgHBEBOZrfLEveMPCfDUb3nGhL1kzctkRnkCubrg")
					.setOAuthAccessToken(accessToken.getToken())
					.setOAuthAccessTokenSecret(accessToken.getTokenSecret())
					.build();

			OAuthAuthorization auth = new OAuthAuthorization(conf,
					"jHAaFDzEkyICCpZqSZLWeA",
					"XDRgHBEBOZrfLEveMPCfDUb3nGhL1kzctkRnkCubrg",
					new AccessToken(conf.getOAuthAccessToken(),
							conf.getOAuthAccessTokenSecret()));

			ImageUpload upload = ImageUpload.getTwitpicUploader(
					"e1a74f21204c52de8c65a92d19dfb4d1", auth);

			Log.d("kkkkvvvv", "Start sending image..."+conf.getOAuthAccessToken()+"  toto  "+conf.getOAuthAccessTokenSecret());

			try {
				File file = new File("/sdcard/icon72.png");

				url = upload.upload(file);
				result = 1;

				Log.d("hhhhhh", "Image uploaded, Twitpic url is " + url);
			} catch (Exception e) {
				Log.e("kkkkkk", "Failed to send image");

				e.printStackTrace();
			}

			return result;
		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(Long result) {
			mProgressDialog.cancel();

			String text = (result == 1) ? "Image sent successfully.\n Twitpic url is: "
					+ url
					: "Failed to send image";

			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
					.show();
		}
	}

}