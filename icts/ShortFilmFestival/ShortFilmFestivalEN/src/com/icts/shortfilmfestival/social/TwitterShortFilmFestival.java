package com.icts.shortfilmfestival.social;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.SessionStore;
import com.twitter.android.TwitterApp;
import com.twitter.android.TwitterApp.TwDialogListener;


public class TwitterShortFilmFestival {
	private static final String twitter_consumer_key = "qQZqxAzQHDnZPocGwLn9A";
	private static final String twitter_secret_key = "FoVXlAvaXqwH30nuK1bDv7CS14GROdxnWaZnV8sY5U";
	private TwitterApp mTwitter;
	private String mContent;
	private Activity mContext;
	private ProgressDialog mProgress;
	public TwitterShortFilmFestival(Activity pContext,String pContent)
	{
		this.mContent = pContent;
		this.mContext = pContext;
		this.mProgress  = new ProgressDialog(pContext);
		this.mTwitter = new TwitterApp(mContext, twitter_consumer_key,twitter_secret_key);
		this.mTwitter.setListener(mTwLoginDialogListener);
	}
	
	public void resetAccessToken()
	{
		this.mTwitter.resetAccessToken();
	}
	
	public boolean hasAccessToken()
	{
		return this.mTwitter.hasAccessToken();
	}
	public void authorize()
	{
		this.mTwitter.authorize();
	}
	
	public void postToTwitter() {
		new Thread() {
			
			public void run() {
				int what = 0;
				
				try {
					mTwitter.updateStatus(mContent);
				} catch (Exception e) {
					Log.d("Exception", e.getMessage());
					what = 1;
				}
				
				mHandler.sendMessage(mHandler.obtainMessage(what));
			}
		}.start();
	}
	
	public void tiwtterLogout() {
		mProgress.setMessage("Disconnecting from Twitter");
		mProgress.show();
			
		new Thread() {
			
			public void run() {
				SessionStore.clear(mContext);
		        	   
				int what = 1;
					
		        try {
		        	mTwitter.resetAccessToken();
		        		 
		        	what = 0;
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		        	
		        mHandlerLogout.sendMessage(mHandlerLogout.obtainMessage(what));
			}
		}.start();
	}
	
	public void onTwitterClick() {
		if (mTwitter.hasAccessToken()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			
			builder.setMessage("Delete current Twitter connection?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   tiwtterLogout();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                postToTwitter();
			           }
			       });
			final AlertDialog alert = builder.create();
			
			alert.show();
		} else {			
			
			mTwitter.authorize();
		}
	}
	
	private Handler mHandler = new Handler() {
		
		public void handleMessage(Message msg) {
			String text = (msg.what == 0) ? "Posted to Twitter" : "Post to Twitter failed";
			
			Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
		}
	};
	
	public Handler mHandlerLogout = new Handler() {
		
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			
			if (msg.what == 1) {
				Toast.makeText(mContext, "Twitter logout failed", Toast.LENGTH_SHORT).show();
			} else {
	        	   
				Toast.makeText(mContext, "Disconnected from Twitter", Toast.LENGTH_SHORT).show();
				mTwitter.authorize();
			}
		}
	};
	
	private final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {
		
		public void onComplete(String value) {
			postToTwitter();
		}
		
		
		public void onError(String value) {
			
		}
	};
	
}
