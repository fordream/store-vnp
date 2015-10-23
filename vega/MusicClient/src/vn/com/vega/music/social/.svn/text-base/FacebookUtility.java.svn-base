package vn.com.vega.music.social;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.utils.Const;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

public class FacebookUtility {

	private Facebook mFacebook;
	private Handler mHandler = new Handler();
	private Context mContext;
	//public static final String APP_ID = "170971199667899";
	private AsyncFacebookRunner mAsyncRunner;
	private Activity mActivity;
	
	private String mDes = "";
	private String mLink = "beta.chacha.vn";

	private static final String[] PERMISSIONS = new String[] {
			"publish_stream", "read_stream", "offline_access" };

	private DataStore datastore;

	public FacebookUtility(Context context, Activity activity) {
		mContext = context;
		mActivity = activity;
		datastore = DataStore.getInstance();
	}

	public void loginAndPostToWall(String des, String link) {

		mDes = des;
		mLink = link;
		
		// Initialize the Facebook session
		mFacebook = new Facebook(Const.FACEBOOK_APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);

		// AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(mFacebook);
		// asyncRunner.logout(context, new LogoutRequestListener());
		mFacebook.authorize(mActivity, PERMISSIONS, new LoginDialogListener());
	}

	public class WallPostRequestListener implements RequestListener {

		@Override
		public void onComplete(String response, Object state) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "Mời thành công", Toast.LENGTH_SHORT)
					.show();
			Log.d("Invite Friend Facebook response : ", response);
		}

		@Override
		public void onIOException(IOException e, Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "Lỗi kết nối với Facebook",
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * Listener for login dialog completion status
	 */
	private final class LoginDialogListener implements
			com.facebook.android.Facebook.DialogListener {

		/**
		 * Called when the dialog has completed successfully
		 */
		public void onComplete(Bundle values) {
			// Process onComplete
			Log.d("FB Sample App", "LoginDialogListener.onComplete()");
			// Dispatch on its own thread
			mHandler.post(new Runnable() {
				public void run() {

					// String access_token = mFacebook.getAccessToken();

					// datastore.setFbAccessToken(access_token);

					Bundle params = new Bundle();
					params.putString("description", mDes);
					// params.putString("picture", "");
					// params.putString("name", "Testing");
					// params.putString("message", mLink);
					params.putString("link", mLink);
					
					mFacebook.dialog(mContext, "feed", params,
							new UpdateStatusListener());

					// mAsyncRunner.request("me/feed", params, "POST",
					// new WallPostRequestListener(), null);

				}
			});
		}

		/**
         *
         */
		public void onFacebookError(FacebookError error) {
			// Process error
			Log.d("FB Sample App", "LoginDialogListener.onFacebookError()");
		}

		/**
         *
         */
		public void onError(DialogError error) {
			// Process error message
			Log.d("FB Sample App", "LoginDialogListener.onError()");
		}

		/**
         *
         */
		public void onCancel() {
			// Process cancel message
			Log.d("FB Sample App", "LoginDialogListener.onCancel()");
		}
	}

	/*
	 * callback for the feed dialog which updates the profile status
	 */
	public class UpdateStatusListener implements
			com.facebook.android.Facebook.DialogListener {
		@Override
		public void onComplete(Bundle values) {
			final String postId = values.getString("post_id");
			if (postId != null) {
				Toast toast = Toast.makeText(mContext, "Chia sẻ thành công",
						Toast.LENGTH_SHORT);
				toast.show();
			} else {
				Toast toast = Toast.makeText(mContext, "Chia sẻ không thành công",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		@Override
		public void onFacebookError(FacebookError error) {
			Toast.makeText(mContext, "Facebook Error: " + error.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel() {
			Toast toast = Toast.makeText(mContext, "Update status cancelled",
					Toast.LENGTH_SHORT);
			toast.show();
		}

		@Override
		public void onError(DialogError e) {
			// TODO Auto-generated method stub

		}
	}

}
