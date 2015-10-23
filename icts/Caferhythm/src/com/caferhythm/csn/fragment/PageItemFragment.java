package com.caferhythm.csn.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;
import com.caferhythm.csn.R;
import com.caferhythm.csn.activity.FlashScreenActivity;
import com.caferhythm.csn.data.RssNews;
import com.caferhythm.csn.data.S0032Entity;
import com.caferhythm.csn.utils.StringUtils;

public class PageItemFragment extends Fragment {
	int mNum;
	private ArrayList<S0032Entity> listEntities;
	private static String[] screen = { "diet_factor", "phys_factor","skin_factor", "pms_factor" };
	private Activity activity;
	private String appName = "caferhythm";
	private String uuid;
	private AQuery aq;
	private String url = "http://api.newscafe.ne.jp/webapp/ad/serve.xml";
	private LinearLayout ads;
	private RssNews rssAds;
	private String url1 = "http://api.newscafe.ne.jp/webapp/news/category_topics.xml?c=ren";
	private String url2 = "http://api.newscafe.ne.jp/webapp/news/latest.xml?exclude=ren";
	private ArrayList<RssNews> listRssNews;
	private LinearLayout listnews;
	private SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private SimpleDateFormat sf = new SimpleDateFormat("MM/dd HH:mm");
	private TextView error;

	public PageItemFragment(int num,ArrayList<S0032Entity> listEntities,Activity activity) {
		mNum = num;
		this.listEntities = listEntities;
		this.activity = activity;
	}

	@SuppressLint("ValidFragment")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sp05_pager_item, container,false);
		((TextView) view.findViewById(R.id.sp05_tv_content)).setText(listEntities.get(mNum).getMessage());
		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
		ratingBar.setRating((float) listEntities.get(mNum).getStar());
		TextView t = (TextView) view.findViewById(R.id.sp05_tv_title);
		t.setText(listEntities.get(mNum).getTitle());
		BitmapDrawable bitmapDrawable = new BitmapDrawable(listEntities.get(mNum).getLeft());
		t.setCompoundDrawablesWithIntrinsicBounds(bitmapDrawable, null,null, null);
		
		aq = new AQuery(view);
		error = (TextView) view.findViewById(R.id.sp05_error);
		uuid = FlashScreenActivity.uuidString;
		url = "http://api.newscafe.ne.jp/webapp/ad/serve.xml";
		url = url + "?uuid=" + uuid + "&app_name=" + appName + "&page="+screen[mNum];
		ads = (LinearLayout) view.findViewById(R.id.adsarea);
		listnews = (LinearLayout) view.findViewById(R.id.newsarea);
		listRssNews = new ArrayList<RssNews>();
		rssAds = new RssNews();
		xml_ajax();
		return view;
	}
	
	
	public void xml_ajax() {
		aq.ajax(url, XmlDom.class, this, "cafeAds");
		aq.ajax(url1, XmlDom.class, this, "cafeNews1");
	}

	public void buildAds() {
		if (!rssAds.getLink().equals("")) {
			WebView webView = new WebView(activity);
			String[] size = rssAds.getDescription().split(",");
			int height = Integer.parseInt(size[0]);
			Resources r = activity.getResources();
			int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, r.getDisplayMetrics());
			LinearLayout.LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, px);
			webView.setLayoutParams(layoutParams);
			webView.setPadding(0, 0, 0, 0);
			webView.setScrollContainer(false);
			webView.loadUrl(rssAds.getLink());
			ads.addView(webView);
		}
	}

	private void buildNews() {
		if (listRssNews != null) {
			for (int i = 0; i < listRssNews.size(); i++) {
				View view = LayoutInflater.from(activity).inflate(R.layout.newsitem, null);
				((TextView) view.findViewById(R.id.tv_new_1)).setText(StringUtils.validString(listRssNews.get(i).getTitle(), 11));
				((TextView) view.findViewById(R.id.tv_time_new_1)).setText(listRssNews.get(i).getPubDate());
				listnews.addView(view);
				final String link = listRssNews.get(i).getLink();
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent httpIntent = new Intent(Intent.ACTION_VIEW);
						httpIntent.setData(Uri.parse(link));
						activity.startActivity(httpIntent);
					}
				});
			}
		}
	}

	public void cafeAds(String url, XmlDom xml, AjaxStatus status) {
		List<XmlDom> entries = xml.tags("item");
		for (XmlDom entry : entries) {
			rssAds.setTitle(entry.text("title"));
			rssAds.setLink(entry.text("link"));
			rssAds.setDescription(entry.text("description"));
			rssAds.setCategory(entry.text("category"));
			try {
				Date date = sm.parse(entry.text("pubDate"));
				rssAds.setPubDate(sf.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		buildAds();
	}

	public void cafeNews1(String url, XmlDom xml, AjaxStatus status) {
		if(status.getCode() == 200){
		listRssNews.clear();
		List<XmlDom> entries = xml.tags("item");
		for (XmlDom entry : entries) {
			RssNews rssNews = new RssNews();
			rssNews.setTitle(entry.text("title"));
			rssNews.setLink(entry.text("link"));
			rssNews.setDescription(entry.text("description"));
			rssNews.setCategory(entry.text("category"));
			try {
				Date date = sm.parse(entry.text("pubDate"));
				rssNews.setPubDate(sf.format(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			rssNews.setAuthor(entry.text("author"));
			listRssNews.add(rssNews);
			break;
		}
		aq.ajax(url2, XmlDom.class, this, "cafeNews2");
		}else{
			error.setVisibility(View.VISIBLE);
		}
	}

	public void cafeNews2(String url, XmlDom xml, AjaxStatus status) {
		if(status.getCode() == 200){
		List<XmlDom> entries = xml.tags("item");
		for (int i=0;i<entries.size();i++) {
			XmlDom entry = entries.get(i);
			RssNews rssNews = new RssNews();
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

			rssNews.setAuthor(entry.text("author"));
			listRssNews.add(rssNews);
			if(i == 1){
				break;
			}
		}
		buildNews();
		}else{
			error.setVisibility(View.VISIBLE);
		}
	}
}
