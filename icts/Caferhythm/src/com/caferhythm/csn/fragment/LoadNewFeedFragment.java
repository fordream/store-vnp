package com.caferhythm.csn.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;
import com.caferhythm.csn.R;
import com.caferhythm.csn.data.RssNews;
import com.caferhythm.csn.utils.StringUtils;

@SuppressLint("ParserError")
public class LoadNewFeedFragment extends Fragment {
	private Context mContext;
	private String url1 = "http://api.newscafe.ne.jp/webapp/news/category_topics.xml?c=ren";
	private String url2 = "http://api.newscafe.ne.jp/webapp/news/latest.xml?exclude=ren";
	private AQuery aq;
	private ArrayList<RssNews> listRssNews;
	private LinearLayout listnews;
	private TextView error;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.news_area, null);
		error = (TextView) v.findViewById(R.id.error);
		listnews = (LinearLayout) v.findViewById(R.id.listnews);
		listRssNews = new ArrayList<RssNews>();
		aq = new AQuery(v);
		xml_ajax();
		return v;
	}

	private void buildNews() {
		if (listRssNews != null && listRssNews.size() > 0) {
			for (int i = 0; i < listRssNews.size(); i++) {
				View view = LayoutInflater.from(getActivity()).inflate(
						R.layout.newsitem, null);
				((TextView) view.findViewById(R.id.tv_new_1))
						.setText(StringUtils.validString(listRssNews.get(i)
								.getTitle(), 11));
				((TextView) view.findViewById(R.id.tv_time_new_1))
						.setText(listRssNews.get(i).getPubDate());
				listnews.addView(view);
				final String link = listRssNews.get(i).getLink();
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent httpIntent = new Intent(Intent.ACTION_VIEW);
						httpIntent.setData(Uri.parse(link));

						startActivity(httpIntent);
					}
				});

				if (i >= 2) {
					break;
				}
			}
		} else {
			error.setVisibility(View.VISIBLE);
		}
	}

	public void xml_ajax() {
		aq.ajax(url1, XmlDom.class, this, "cafeNews1");
		aq.ajax(url2, XmlDom.class, this, "cafeNews2");
	}

	public void cafeNews1(String url, XmlDom xml, AjaxStatus status) {
		if (status.getCode() == 200) {
			List<XmlDom> entries = xml.tags("item");
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat sf = new SimpleDateFormat("MM/dd HH:mm");

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
		} else {
			error.setVisibility(View.VISIBLE);
		}
	}

	public void cafeNews2(String url, XmlDom xml, AjaxStatus status) {
		if (status.getCode() == 200) {
			List<XmlDom> entries = xml.tags("item");
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat sf = new SimpleDateFormat("MM/dd HH:mm");

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
				if (listRssNews.size() > 2) {
					break;
				}
			}

			buildNews();
		} else {
			error.setVisibility(View.VISIBLE);
		}
	}
}
