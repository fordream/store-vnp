package com.vnp.shortfirmfestival_rework.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

public class ShortBaseFragment extends Fragment {
	protected ProgressBar header_progressbar;

	public ShortBaseFragment(ProgressBar header_progressbar) {
		super();
		this.header_progressbar = header_progressbar;
		header_progressbar.setVisibility(View.GONE);
	}

}
