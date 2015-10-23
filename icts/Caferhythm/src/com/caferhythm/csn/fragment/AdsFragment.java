package com.caferhythm.csn.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;
import com.caferhythm.csn.R;
import com.caferhythm.csn.activity.FlashScreenActivity;
import com.caferhythm.csn.data.RssNews;

@SuppressLint("ValidFragment")
public class AdsFragment extends Fragment {
	private String screenName;
	private String appName = "caferhythm";
	private String uuid;
	private AQuery aq;
	private String url = "http://api.newscafe.ne.jp/webapp/ad/serve.xml";
	private LinearLayout ads;
	private RssNews rssNews;

	public AdsFragment(String screenName) {
		super();
		this.screenName = screenName;
		this.uuid = FlashScreenActivity.uuidString;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.ads_layout, null);

		url = url + "?uuid=" + uuid + "&app_name=" + appName + "&page="
				+ screenName;
		aq = new AQuery(v);
		ads = (LinearLayout) v.findViewById(R.id.ads);
		xml_ajax();
		return v;
	}

	public void xml_ajax() {
		Log.d("URL", url);
		aq.ajax(url, XmlDom.class, this, "cafeAds");
	}

	public void cafeAds(String url, XmlDom xml, AjaxStatus status) {
		List<XmlDom> entries = xml.tags("item");
		SimpleDateFormat sm = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss ZZZZZZ");
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd HH:mm");
		rssNews = new RssNews();
		for (XmlDom entry : entries) {
			rssNews.setTitle(entry.text("title"));
			rssNews.setLink(entry.text("link"));
			rssNews.setDescription(entry.text("description"));
			rssNews.setCategory(entry.text("category"));
			try {
				Date date = sm.parse(entry.text("pubDate"));
				rssNews.setPubDate(sf.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		buildAds();
	}

	public void buildAds() {
		if (!rssNews.getLink().equals("")) {
			Log.d("URL ADS", rssNews.getLink());
			WebView webView = new WebView(getActivity().getApplicationContext());
			
			String[] size = rssNews.getDescription().split(",");
			int height = Integer.parseInt(size[0]);
			Resources r = getResources();
			int px = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, height, r.getDisplayMetrics());
			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT, px);
			webView.setLayoutParams(layoutParams);
			webView.setPadding(0, 0, 0, 0);
		//	webView.setScrollContainer(false);
			webView.setWebViewClient(new myWebClient());
			webView.loadUrl(rssNews.getLink());
			ads.addView(webView);
		}
	}
	
	 public class myWebClient extends WebViewClient  
     {  
         @Override  
         public void onPageStarted(WebView view, String url, Bitmap favicon) {  
             super.onPageStarted(view, url, favicon);  
         }  

         @Override  
         public boolean shouldOverrideUrlLoading(WebView view, String url) {  
             return true;  
         } 
         
         @Override
        public void onPageFinished(WebView view, String url) {
        	// TODO Auto-generated method stub
        	super.onPageFinished(view, url);
        }
         
         @Override
        public void onLoadResource(WebView view, String url) {
        	// TODO Auto-generated method stub
        	super.onLoadResource(view, url);
        	Log.e("ONLOAD","ONLOAD: "+url);
        }
     } 
}
