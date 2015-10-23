package com.caferhythm.csn.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;
import com.caferhythm.csn.R;
import com.caferhythm.csn.data.Fluctuation;
import com.caferhythm.csn.data.RssNews;
import com.caferhythm.csn.data.S0032Entity;
import com.caferhythm.csn.json.JsonPaser;
import com.csn.caferhythm.adapter.S05PagerAdapter;
import com.csn.caferhythm.adapter.Sp05FragmentPagerAdapter;

public class Sp05Activity extends FragmentActivity {
	private ViewPager viewPager;
	private ArrayList<S0032Entity> listUrl;
	private Fluctuation f;
	private int page = 0;
	private String[] screen = { "diet_factor", "phys_factor", "skin_factor",
			"pms_factor" };
	private FragmentActivity mFragmentActivity;

	private String appName = "caferhythm";
	private String uuid;
	private AQuery aq;
	private String url = "http://api.newscafe.ne.jp/webapp/ad/serve.xml";
	private String url1 = "http://api.newscafe.ne.jp/webapp/news/category_topics.xml?c=ren";
	private String url2 = "http://api.newscafe.ne.jp/webapp/news/latest.xml?exclude=ren";
	private int count = 0;

	private ArrayList<RssNews> listRssNews;
	private ArrayList<RssNews> listAdsUrl;
	SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	SimpleDateFormat sf = new SimpleDateFormat("MM/dd HH:mm");
	private ProgressDialog progressDialog;
	private S05PagerAdapter s05PagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// setContentTab(getString(R.string.sp05_screen_title),
		// R.layout.sp05layout);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sp05layout);
		mFragmentActivity = this;
		uuid = FlashScreenActivity.uuidString;
		aq = new AQuery(Sp05Activity.this);
		listRssNews = new ArrayList<RssNews>();
		listAdsUrl = new ArrayList<RssNews>();
		progressDialog = new ProgressDialog(Sp05Activity.this);
		progressDialog.setTitle("Loading...");
		progressDialog.setCancelable(false);
		viewPager = (ViewPager) findViewById(R.id.titlePage);
		findViewById(R.id.bt_sp05_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		gotoNext();
		/*
		xml_ajax();
		*/
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	}
	
	
	public void reload() {
		aq.ajax(url1, XmlDom.class, this, "cafeNews3");
	}
	

	public void gotoNext() {
		//progressDialog.dismiss();
		listUrl = new ArrayList<S0032Entity>();
		f = (Fluctuation) getIntent().getSerializableExtra("fluctuation");
		page = getIntent().getIntExtra("page", 0);
		if (f != null) {
			listUrl = JsonPaser.getListFluctuation(getResources(), f);
			//Sp05PagerAdapter sp05PagerAdapter = new Sp05PagerAdapter(Sp05Activity.this, listUrl);
			Sp05FragmentPagerAdapter sp05PagerAdapter = new Sp05FragmentPagerAdapter(getSupportFragmentManager(), listUrl, Sp05Activity.this);
			viewPager.setAdapter(sp05PagerAdapter);
			viewPager.setCurrentItem(page - 1);
			/*
			s05PagerAdapter = new S05PagerAdapter(
					Sp05Activity.this, listUrl, listAdsUrl, listRssNews);
			viewPager.setAdapter(s05PagerAdapter);
			viewPager.setCurrentItem(page - 1);
			*/
		}
	}

	public void xml_ajax() {
		progressDialog.show();
		url = "http://api.newscafe.ne.jp/webapp/ad/serve.xml";
		url = url + "?uuid=" + uuid + "&app_name=" + appName + "&page="
				+ screen[0];
		aq.ajax(url, XmlDom.class, this, "cafeAds");
	}
	
	

	public void cafeAds(String url, XmlDom xml, AjaxStatus status) {
		if (status.getCode() == 200) {
			count++;
			// Log.e("URL",url+ " COUNT: "+count);
			List<XmlDom> entries = xml.tags("item");
			for (XmlDom entry : entries) {
				RssNews rssAds = new RssNews();
				rssAds.setTitle(entry.text("title"));
				rssAds.setLink(entry.text("link"));
				rssAds.setDescription(entry.text("description"));
				listAdsUrl.add(rssAds);
			}

			if (count < 4) {
				url = "http://api.newscafe.ne.jp/webapp/ad/serve.xml";
				url = url + "?uuid=" + uuid + "&app_name=" + appName + "&page="
						+ screen[count];
				aq.ajax(url, XmlDom.class, this, "cafeAds");
			} else {
				// Log.e("DONE","DONE COUTN: "+count);
				aq.ajax(url1, XmlDom.class, this, "cafeNews1");
			}
		} else {
			aq.ajax(url1, XmlDom.class, this, "cafeNews1");
		}

	}

	public void cafeNews1(String url, XmlDom xml, AjaxStatus status) {
		// Log.e("URL",url);
		if (status.getCode() == 200) {
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
					e.printStackTrace();
				}

				rssNews.setAuthor(entry.text("author"));
				listRssNews.add(rssNews);
				break;
			}
			aq.ajax(url2, XmlDom.class, this, "cafeNews2");
		} else {
			gotoNext();
		}
	}

	public void cafeNews2(String url, XmlDom xml, AjaxStatus status) {
		if (status.getCode() == 200) {
			List<XmlDom> entries = xml.tags("item");
			for (int i = 0; i < entries.size(); i++) {
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
				if (i == 2) {
					break;
				}
			}
			gotoNext();
		} else {
			gotoNext();
		}
	}

	public void cafeNews3(String url, XmlDom xml, AjaxStatus status) {
		if (status.getCode() == 200) {
			listRssNews.clear();
			Log.e("HERE","HERE: "+ this.getClass().toString());
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
					e.printStackTrace();
				}

				rssNews.setAuthor(entry.text("author"));
				listRssNews.add(rssNews);
				break;
			}
			aq.ajax(url2, XmlDom.class, this, "cafeNews4");
		}
	}

	public void cafeNews4(String url, XmlDom xml, AjaxStatus status) {
		if (status.getCode() == 200) {
			List<XmlDom> entries = xml.tags("item");
			for (int i = 0; i < entries.size(); i++) {
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
				if (i == 2) {
					break;
				}
			}
			if(s05PagerAdapter == null){
				s05PagerAdapter = new S05PagerAdapter(
						Sp05Activity.this, listUrl, listAdsUrl, listRssNews);
				viewPager.setAdapter(s05PagerAdapter);
			}else{
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						s05PagerAdapter.notifyDataSetChanged();
					}
				});
				
			}
		}
		//progressDialog.dismiss();
	}

}