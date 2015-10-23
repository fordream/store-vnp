package com.cnc.android.timtalk.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnc.android.timtalk.ui.R;

public class ChatsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View fragView = inflater.inflate(R.layout.layout_chats, container,
				false);
		return fragView;
	}

}
