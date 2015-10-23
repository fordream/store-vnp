package com.vnp.shortfirmfestival_rework.fragement;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vnp.core.callback.ExeCallBack;
import com.vnp.core.callback.ResClientCallBack;
import com.vnp.core.common.CommonAndroid;
import com.vnp.core.service.RestClient;
import com.vnp.shortfirmfestival_rework.R;
import com.vnp.shortfirmfestival_rework.adapter.AllAdapter;
import com.vnp.shortfirmfestival_rework.base.ShortBaseFragment;
import com.vnp.shortfirmfestival_rework.view.FooterView;

public class AllFragement extends ShortBaseFragment {
	private ListView listView;
	private AllAdapter adapter;

	private String type;

	public AllFragement(String type, ProgressBar progressBar) {
		super(progressBar);
		this.type = type;
	}

	// http://www.shortshorts.org/api/list.php?type=all&lang=en&limit=20&offset=0
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.all, null);
		listView = (ListView) view.findViewById(R.id.all_listview);
//		listView.addFooterView(new FooterView(getActivity()));
		adapter = new AllAdapter(listView, type, header_progressbar);
		// listView.setAdapter(adapter = new AllAdapter());
		return view;
	}
}
