package com.vnp.shortfirmfestival_rework.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.vnp.shortfirmfestival_rework.R;
import com.vnp.shortfirmfestival_rework.adapter.PhotosAdapter;
import com.vnp.shortfirmfestival_rework.base.ShortBaseFragment;
import com.vnp.shortfirmfestival_rework.view.FooterView;

public class PhotoFragement extends ShortBaseFragment {
	private GridView listView;
	private PhotosAdapter adapter;

	private String type;

	public PhotoFragement(String type, ProgressBar progressBar) {
		super(progressBar);
		this.type = type;
	}

	// http://www.shortshorts.org/api/photos.php?type=all&limit=20&offset=0
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.photo, null);
		listView = (GridView) view.findViewById(R.id.all_listview);
		// listView.addFooterView(new FooterView(getActivity()));
		adapter = new PhotosAdapter(listView, type, header_progressbar);
		// listView.setAdapter(adapter = new AllAdapter());
		return view;
	}
}
