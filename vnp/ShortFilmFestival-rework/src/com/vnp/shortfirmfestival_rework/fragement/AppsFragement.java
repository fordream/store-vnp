package com.vnp.shortfirmfestival_rework.fragement;

import java.util.StringTokenizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vnp.core.common.CommonAndroid;
import com.vnp.shortfirmfestival_rework.R;
import com.vnp.shortfirmfestival_rework.adapter.AppsAdapter;
import com.vnp.shortfirmfestival_rework.base.ShortBaseFragment;

public class AppsFragement extends ShortBaseFragment {
	private ListView listView;

	// Data For Apps

	// public static final int LIST_LOGO_APP[]=
	// {
	// R.drawable.express,
	// R.drawable.world,
	// R.drawable.top_app,
	// R.drawable.secret,
	// R.drawable.yukkina,
	// R.drawable.rococo,
	// R.drawable.eclipse
	//
	// };
	public AppsFragement(ProgressBar header_progressbar) {
		super(header_progressbar);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.all, null);
		listView = (ListView) view.findViewById(R.id.all_listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String data = parent.getItemAtPosition(position).toString();
				// name_icon_description_description1_price_linkGoogle
				StringTokenizer stringTokenizer = new StringTokenizer(data, "_");

				String name = stringTokenizer.nextToken();
				String icon = stringTokenizer.nextToken();
				String description = stringTokenizer.nextToken();
				String description1 = stringTokenizer.nextToken();
				String price = stringTokenizer.nextToken();
				String linkGoogle = stringTokenizer.nextToken();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkGoogle));
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_nothing);
			}
		});
		view.findViewById(R.id.all_progressbar).setVisibility(View.GONE);
		view.postDelayed(new Runnable() {

			@Override
			public void run() {
				listView.setAdapter(new AppsAdapter());
			}
		}, 500);
		return view;
	}
}
