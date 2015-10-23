package com.csn.caferhythm.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.caferhythm.csn.R;
import com.caferhythm.csn.data.RssNews;
import com.caferhythm.csn.data.S0032Entity;
import com.caferhythm.csn.utils.StringUtils;

public class S05PagerAdapter extends PagerAdapter {
	
	private ArrayList<S0032Entity> listEntities;
	private FragmentActivity activity;
	private LinearLayout ads;
	private ArrayList<RssNews> listRssAds;
	private ArrayList<RssNews> listRssNews;
	private View view;
	private AQuery aq;
	private TextView error; 
	private LinearLayout areas;
	
	
	public S05PagerAdapter(FragmentActivity activity,ArrayList<S0032Entity> listEntities,
			ArrayList<RssNews> listRssAds,ArrayList<RssNews> listRssNews) {
		super();
		this.listEntities = listEntities;
		this.activity = activity;
		this.listRssAds = listRssAds;
		this.listRssNews = listRssNews;
	}
	
	@Override
	public int getCount() {
		return listEntities.size();
	}
	
	@Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
 
    }

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		 return arg0 == ((View) arg1);
	}
	
	

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.s05_pager_item, null);
		aq = new AQuery(view);
		error = (TextView) view.findViewById(R.id.error);
		areas = (LinearLayout) view.findViewById(R.id.newsarea);
		((TextView)view.findViewById(R.id.sp05_tv_content)).setText(listEntities.get(position).getMessage());
		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
		ratingBar.setRating((float) listEntities.get(position).getStar());
		
		TextView t = (TextView)view.findViewById(R.id.sp05_tv_title);
		t.setText(listEntities.get(position).getTitle());
		
		BitmapDrawable bitmapDrawable = new BitmapDrawable(listEntities.get(position).getLeft());
		t.setCompoundDrawablesWithIntrinsicBounds(bitmapDrawable, null, null, null);
		
		buildNews();
		if(listRssAds.size() >= 4){
			buildAds(view, listRssAds.get(position));
		}
		((ViewPager) container).addView(view, 0);
		return view;
	}
	

	public void buildAds(View view,RssNews rssAds) {
		if (!rssAds.getLink().equals("")) {
			WebView webView = (WebView) view.findViewById(R.id.adsWebview);
			String[] size = rssAds.getDescription().split(",");
			int height = Integer.parseInt(size[0]);
			Resources r = activity.getResources();
			int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, r.getDisplayMetrics());
			LinearLayout.LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, px);
			webView.setLayoutParams(layoutParams);
			webView.setPadding(0, 0, 0, 0);
			webView.setScrollContainer(false);
			webView.loadUrl(rssAds.getLink());
		}
	}

	private void buildNews() {
		if (listRssNews != null && listRssNews.size() >= 3) {
			aq.id(R.id.tv_new_1).text(StringUtils.validString(listRssNews.get(0).getTitle(), 11));
			aq.id(R.id.tv_time_new_1).text(listRssNews.get(0).getPubDate());
			final String link1 = listRssNews.get(0).getLink();
			aq.id(R.id.lo_new_1).clicked(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent httpIntent = new Intent(Intent.ACTION_VIEW);
					httpIntent.setData(Uri.parse(link1));

					activity.startActivity(httpIntent);
				}
			});
			aq.id(R.id.tv_new_2).text(StringUtils.validString(listRssNews.get(1).getTitle(), 11));
			aq.id(R.id.tv_time_new_2).text(listRssNews.get(1).getPubDate());
			final String link2 = listRssNews.get(1).getLink();
			aq.id(R.id.lo_new_2).clicked(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent httpIntent = new Intent(Intent.ACTION_VIEW);
					httpIntent.setData(Uri.parse(link2));

					activity.startActivity(httpIntent);
				}
			});
			
			
			aq.id(R.id.tv_new_3).text(StringUtils.validString(listRssNews.get(2).getTitle(), 11));
			aq.id(R.id.tv_time_new_3).text(listRssNews.get(2).getPubDate());
			final String link3 = listRssNews.get(0).getLink();
			aq.id(R.id.lo_new_3).clicked(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent httpIntent = new Intent(Intent.ACTION_VIEW);
					httpIntent.setData(Uri.parse(link3));

					activity.startActivity(httpIntent);
				}
			});
		}else{
			error.setVisibility(View.VISIBLE);
			areas.setVisibility(View.GONE);
		}
	}
}
