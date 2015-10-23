package org.cnc.smashpaddle.twitter;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TwitterApi {
	private ProgressDialog pro;
	Context context;
/*	String twitterOAuthKey = "jMjiRsCKIOh8LlAHiby6g";
	String twitterOAuthSecret = "0CYrj4lIFUkhP4ncFX3STpOzivlNg9I4dTYO6XZ1Vhk";
	String twitterCallbackURL = "http://idontq.com/";
	String twitterAccessToken = "56244659-WP7TdxjcLTBbTsDS2rykwVikkdLxLWUwhRhymMBUG";
	String twitterTokenSecret = "RtZEDCuK2KPLoHG7hKFp7KfGSJDjY2eoxqhPZfHZK9M";*/
	String twitterOAuthKey = "jHAaFDzEkyICCpZqSZLWeA";
	String twitterOAuthSecret = "XDRgHBEBOZrfLEveMPCfDUb3nGhL1kzctkRnkCubrg";
	String twitterCallbackURL = "http://cnc.com.vn/";
	String twitterAccessToken = "579846660-DhwdsL2Z0ge6siJDE1PZqT697JmXQA1SPAh6TmZk";
	String twitterTokenSecret = "GYzI2wHzDoqIlvEsTNUOikOSvoDqqgHa4R1Ij6PZ8";
	String message;

	/*
	 * Variable for twitter (non-Javadoc)
	 */
	private DefaultOAuthConsumer consumer;
	private DefaultOAuthProvider provider;
	private twitter4j.Twitter twitter;

	public TwitterApi(Context context, String token, ProgressDialog pro) {
		this.context = context;
		this.pro = pro;
	}

	public void postStatus(String message, String oauthVerifier,
			DefaultOAuthConsumer consumer, DefaultOAuthProvider provider) {
		this.message = message;
		this.consumer = consumer;
		this.provider = provider;
		new ShareTwitter().execute(oauthVerifier);
	}

	class ShareTwitter extends AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			try {
				pro.dismiss();
			} catch (Exception e) {
			}
			pro = null;
			pro = new ProgressDialog(context);
			pro.setMessage("Share via twitter...");
			try {
				pro.show();
			} catch (Exception e) {

			}
			super.onPreExecute();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected Boolean doInBackground(String... params) {
			String oauthVerifier = params[0];
			try {
				provider.retrieveAccessToken(consumer, oauthVerifier);
				AccessToken a = new AccessToken(consumer.getToken(),
						consumer.getTokenSecret());
				twitter = new TwitterFactory().getInstance();
				twitter.setOAuthConsumer(twitterOAuthKey, twitterOAuthSecret);
				twitter.setOAuthAccessToken(a);
				twitter.updateStatus(message);
				return true;
			} catch (Exception e) {
				Log.d(TwitterApi.class.toString(), e.getMessage());
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			pro.dismiss();
			if (result) {
				Toast.makeText(context, "Share via Twitter successful!",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(context, "Share via Twitter unsuccessful!",
						Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

	}
}