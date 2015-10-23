package com.icts.shortfilmfestival.social;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionStore;
import com.facebook.android.Facebook.DialogListener;

public class FaceBookShortFilmFestival {
	private Facebook mFacebook;
	private Context mContext;
	private Activity mActivity;
	private Handler mRunOnUi;
	
	private String mTitle;
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getmLink() {
		return mLink;
	}
	public void setmLink(String mLink) {
		this.mLink = mLink;
	}
	public String getmDesc() {
		return mDesc;
	}
	public void setmDesc(String mDesc) {
		this.mDesc = mDesc;
	}

	private String mLink;
	private String mDesc;
	private String picture;
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Facebook getmFacebook() {
		return mFacebook;
	}
	public void setmFacebook(Facebook mFacebook) {
		this.mFacebook = mFacebook;
	}
	private ProgressDialog mProgress;
	public ProgressDialog getmProgress() {
		return mProgress;
	}
	public void setmProgress(ProgressDialog mProgress) {
		this.mProgress = mProgress;
	}
	private static final String[] PERMISSIONS = new String[] {"publish_stream", "read_stream", "offline_access"};
	private static final String APP_ID = "120097198078017";
	private boolean isSessionValid;
	public boolean isSessionValid() {
		return isSessionValid;
	}
	public void setSessionValid(boolean isSessionValid) {
		this.isSessionValid = isSessionValid;
	}
	// ===============================================================
	// Contructor
	// ===============================================================
	public FaceBookShortFilmFestival(Context pContext, Activity pActivity,
			String pTitle, String pLink, String pDesc, String pPicture)
	{
		this.mFacebook = new Facebook(APP_ID);
		this.mProgress = new ProgressDialog(pContext);
		this.mContext = pContext;
		this.mActivity = pActivity;
		this.mTitle = pTitle;
		this.mLink = pLink;
		this.mDesc = pDesc;
		this.picture = pPicture;
		this.mProgress = new ProgressDialog(pActivity);
		this.mRunOnUi = new Handler();
	}
	
	public void restoreSestion()
	{
		SessionStore.restore(mFacebook, mContext);
	}
	
	public void authorize()
	{
		mFacebook.authorize(this.mActivity, PERMISSIONS, -1, new FbLoginDialogListener());
	}

	public void postToFacebook(String title, String link, String desc, String picture) {	
		mProgress.setMessage("Posting ...");
		mProgress.show();
		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);
		Bundle params = new Bundle();
		//params.putString("access_token", mFacebook.getAccessToken());
		if (!title.equals(""))
		{
			params.putString("message", title);
		}
		params.putString("link", link);
		if (!desc.equals(""))
		{
			params.putString("description", desc);
		}
		//params.putString("picture", picture);
		
		mAsyncFbRunner.request("me/feed", params, "POST", new WallPostListener());
	}
	
	public final class WallPostListener extends BaseRequestListener {
        public void onComplete(final String response) {
        	mRunOnUi.post(new Runnable() {
        		
        		public void run() {
        			mProgress.cancel();
        			
        			Toast.makeText(mContext, "Posted to Facebook", Toast.LENGTH_SHORT).show();
        		}
        	});
        }
    }
	
	public final class FbLoginDialogListener implements DialogListener {
		
		public void onComplete(Bundle values) {
            SessionStore.save(mFacebook, mContext);
            postToFacebook(FaceBookShortFilmFestival.this.mTitle,FaceBookShortFilmFestival.this.mLink,
            		FaceBookShortFilmFestival.this.mDesc, FaceBookShortFilmFestival.this.picture);
            //getFbName();
        }
        public void onFacebookError(FacebookError error) {
          
        }
        public void onError(DialogError error) {
        	
        }
        public void onCancel() {
        	
        }
    }
    
	public void getFbName() {
		mProgress.setMessage("Finalizing ...");
		mProgress.show();
		
		new Thread() {
			
			public void run() {
		        String name = "";
		        int what = 1;
		        
		        try {
		        	String me = mFacebook.request("me");
		        	
		        	JSONObject jsonObj = (JSONObject) new JSONTokener(me).nextValue();
		        	name = jsonObj.getString("name");
		        	what = 0;
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		        
		        mFbHandler.sendMessage(mFbHandler.obtainMessage(what, name));
			}
		}.start();
	}
	
	public void fbLogout() {
		mProgress.setMessage("Disconnecting from Facebook");
		mProgress.show();
			
		new Thread() {
			
			public void run() {
				SessionStore.clear(mContext);
		        	   
				int what = 1;
					
		        try {
		        	mFacebook.logout(mContext);
		        		 
		        	what = 0;
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		        	
		        mHandler.sendMessage(mHandler.obtainMessage(what));
			}
		}.start();
	}
	
	 public void onFacebookClick() {
			if (mFacebook.isSessionValid()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
				
				builder.setMessage("Delete current Facebook connection?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   fbLogout();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				                //postToFacebook(getmTitle(), getmLink(), getmDesc(), getPicture());
				                postToFacebook(FaceBookShortFilmFestival.this.mTitle,FaceBookShortFilmFestival.this.mLink,
				                		FaceBookShortFilmFestival.this.mDesc, FaceBookShortFilmFestival.this.picture);
				           }
				       });
				
				final AlertDialog alert = builder.create();
				
				alert.show();
			} else {
				mFacebook.authorize(mActivity, PERMISSIONS, -1, new FbLoginDialogListener());
			}
		}
	
	public Handler mFbHandler = new Handler() {
		
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			
			if (msg.what == 0) {
				String username = (String) msg.obj;
		        username = (username.equals("")) ? "No Name" : username;
		            
		        SessionStore.saveName(username, mContext);
			} else {
			}
		}
	};
	
	public Handler mHandler = new Handler() {
		
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			
			if (msg.what == 1) {
				Toast.makeText(mContext, "Facebook logout failed", Toast.LENGTH_SHORT).show();
			} else {
	        	   
				Toast.makeText(mContext, "Disconnected from Facebook", Toast.LENGTH_SHORT).show();
				authorize();
			}
		}
	};
}
