package com.icts.shortfilmfestival.fragment;

import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.CacheManager.CacheResult;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.DetailNews;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.myjson.JSNews;
import com.icts.shortfilmfestival.social.FaceBookShortFilmFestival;
import com.icts.shortfilmfestival.social.TwitterShortFilmFestival;
import com.icts.shortfilmfestival.social.gplusActivity;
import com.icts.shortfilmfestival.tabgroup.NewsTab;
import com.icts.shortfilmfestivalJa.FestivalTabActivity;
import com.icts.shortfilmfestivalJa.MainTabActivity;
import com.icts.shortfilmfestivalJa.R;

public class WebViewFragmentTheater extends Fragment implements OnTouchListener{

	private static WebView mWebview;
	private static final FrameLayout.LayoutParams ZOOM_PARAMS = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
	
	private static String url = "";
	private static String title = "";
	private static String content = "";
	private int mNewsId;
	private String mParam;
	private View mRoot;
	private ProgressDialog mProgressDialog;
	private ImageView mBackImageView;
	private TextView mNumLikeFacebook;
	private TextView mNumLikeTwitter;
	private LinearLayout mBackLinearLayout;
	private Fragment mFromFragment;
	private String mLink;
	private Activity mActivity;
	private ImageView mFacebookImageView, mTweeterImageView, mGoogleImageView;
	static FaceBookShortFilmFestival mFaceBookShortFilmFestival;
	static TwitterShortFilmFestival mTwitterShortFilmFestival;
	private DetailNews mNewsDetailEntity;
	private static final String TAG = "LOG_NewsDetail";
	private boolean loadingFinished = false, redirect = false;;
	public WebViewFragmentTheater(Fragment pFromFragment,String pParam, int pNewsId,
			String pUrl, String pTitle)
	{
		url = pUrl;
		mNewsId = pNewsId;
		mParam = pParam;
		NewsTab.isDeailShow = true;
		mFromFragment = pFromFragment;
		title = pTitle.replaceAll("&amp;nbsp;", " ");
		title = pTitle.replaceAll("&amp;nbsp;", " ");
		title = pTitle.replaceAll("&lt;", "<");
		title = pTitle.replaceAll("&gt;", ">");
		title = pTitle.replaceAll("&#039;", "'");
		title = pTitle.replaceAll("&amp;quot;", "\"");
		title = pTitle.replaceAll("&quot;", "\"");
		title = pTitle.replaceAll("&amp;", "&");
		title = pTitle.replaceAll("&amp;", "&");
		title = pTitle.replaceAll("\"", "'");
		JSNews mJSNews = new JSNews();
		mNewsDetailEntity = new DetailNews();
		mNewsDetailEntity = mJSNews.getDetailNews(SSSFApi.getDetailNews(
				ISettings.NEWS_DETAIL_URL_PREFIX, this.mParam, this.mNewsId,
				ISettings.LANGUAGE));

		if (mNewsDetailEntity != null) {

			mNewsDetailEntity
					.setNumLikeFacebook(mJSNews.getNumberLikeFaceBook(SSSFApi
							.getNumLikeFaceBook(ISettings.GET_NUMBER_LIKE_FACEBOOK_URL
									+ URLEncoder.encode(this.url))));

			mNewsDetailEntity
					.setNumLikeTwitter(mJSNews.getNumberLikeTwitter(SSSFApi
							.getNumLikeFaceBook(ISettings.GET_NUMBER_LIKE_TWITER_URL
									+ URLEncoder.encode(this.url))));
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.webviewtheater, null);
		// Get data
		
		// Get WebView
		mWebview = (WebView) mRoot.findViewById(R.id.webView1);
		if (url.equals(ISettings.FESTIVAL_REPORT_EN) || url.equals(ISettings.FESTIVAL_REPORT_JA))
		{
			mWebview.getSettings().setUserAgentString("Chrome");
		}
		mWebview.getSettings().setJavaScriptEnabled(true);
		mWebview.setInitialScale(25);
		mWebview.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				if(failingUrl.equals(url)){
					Toast.makeText(getActivity().getApplicationContext(), description, Toast.LENGTH_SHORT).show();
				}
					
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		FrameLayout mContentView = (FrameLayout) getActivity().getWindow().getDecorView()
				.findViewById(android.R.id.content);
		final View zoom = mWebview.getZoomControls();
		mContentView.addView(zoom, ZOOM_PARAMS);
		zoom.setVisibility(View.GONE);
		mWebview.getSettings().setUseWideViewPort(true);
		mWebview.getSettings().setSupportZoom(true);
		mWebview.getSettings().setBuiltInZoomControls(true);
		
		
		mBackImageView = (ImageView) mRoot.findViewById(R.id.back_button_id);
		mFacebookImageView = (ImageView) mRoot
				.findViewById(R.id.facebook_button_id);
		mNumLikeFacebook = (TextView) mRoot.findViewById(R.id.numLikeFaceBook);
		mFacebookImageView.setOnTouchListener(this);
		mTweeterImageView = (ImageView) mRoot
				.findViewById(R.id.tweet_button_id);
		mNumLikeTwitter = (TextView) mRoot.findViewById(R.id.numLikeTwitter);
		mTweeterImageView.setOnTouchListener(this);
		
		if (ISettings.LANGUAGE.equals("en"))
		{
			mBackImageView.setBackgroundResource(R.drawable.back_buttn);
			mFacebookImageView.setBackgroundResource(R.drawable.face_buttn);
			mTweeterImageView.setBackgroundResource(R.drawable.tweet_buttn);
		}
		else
		{
			mBackImageView.setBackgroundResource(R.drawable.back_buttn_ja);
			mFacebookImageView.setBackgroundResource(R.drawable.face_buttn_ja);
			mTweeterImageView.setBackgroundResource(R.drawable.tweet_buttn_ja);
		}
		
		mGoogleImageView = (ImageView) mRoot.findViewById(R.id.google_button_id);
		mGoogleImageView.setOnTouchListener(this);
		mBackImageView.setOnTouchListener(this);

		mBackLinearLayout = (LinearLayout) mRoot
				.findViewById(R.id.layout_back_button);
		mBackLinearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				backPressed();

			}
		});
		 mWebview.setWebViewClient(new WebViewClient() {
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {              
	                view.loadUrl(url);
	                return true;
	            }
	            public void onLoadResource (WebView view, String url) {
	                if (mProgressDialog == null) {
	                	mProgressDialog = new ProgressDialog(mActivity);
	                	mProgressDialog.setMessage("Loading...");
	                	mProgressDialog.show();
	                }
	            }
	            public void onPageFinished(WebView view, String url) {
	                if (mProgressDialog.isShowing()) {
	                	mProgressDialog.dismiss();
	                }
	            }
	        }); 
		 mWebview.loadUrl(url);
		return mRoot;
	}
	private void backPressed() {
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		if (mFromFragment != null) {
			NewsTab.isDeailShow = false;
			ft.show(mFromFragment);
			// ft.replace(R.id.tabs_fragment, new NewsFragment());
			ft.commit();
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		
			if (mFacebookImageView.equals(v)) {
				mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
						getActivity().getApplicationContext(), mActivity,Html.fromHtml(title).toString(), url,
						"", "");
				mFaceBookShortFilmFestival.restoreSestion();
				mFaceBookShortFilmFestival.onFacebookClick();
			} else if (mTweeterImageView.equals(v)) {

				mTwitterShortFilmFestival = new TwitterShortFilmFestival(
						mActivity,Html.fromHtml(title).toString() + " "
								+ url);
				mTwitterShortFilmFestival.onTwitterClick();
			}
			else if (mGoogleImageView.equals(v))
			{
				Intent mGooglePlus = new Intent();
				mGooglePlus.setClass(getActivity(),
						gplusActivity.class);
				mGooglePlus.putExtra("URL", url);
				startActivity(mGooglePlus);
			}
			return true;
		}
		return false;
	}

	


}
