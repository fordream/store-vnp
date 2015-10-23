package com.caferhythm.csn.json;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;

public class XmlNewsPaser {
	@SuppressLint("ParserError")
	private Context context;
	private AQuery aQuery;
	private String url;

	public XmlNewsPaser() {
	}

	public XmlNewsPaser(Context context) {
		super();
		this.context = context;
		aQuery = new AQuery(context);
	}

	public void feedNews() {
		url = "http://api.newscafe.ne.jp/webapp/cafetalk/rssNews.xml";
		aQuery.ajax(url, XmlDom.class, this, "paserContent");
	}

	public void paserContent(String url, XmlDom xml, AjaxStatus status) {

		List<XmlDom> entries = xml.tags("item");
		List<String> titles = new ArrayList<String>();


		for (XmlDom entry : entries) {
			titles.add(entry.text("title"));
			Log.e("TITLE","Title: " + entry.text("title"));
		}
	}
}
