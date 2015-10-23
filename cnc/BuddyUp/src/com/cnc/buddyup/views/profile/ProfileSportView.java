package com.cnc.buddyup.views.profile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.cnc.buddyup.R;
@SuppressWarnings("unused")
public class ProfileSportView extends LinearLayout {
	private ProfileSportItemView[] sportItems = new ProfileSportItemView[3];

	public ProfileSportView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.profile_sport);
	}

	public ProfileSportView(Context context) {
		super(context);
		config(R.layout.profile_sport);
	}

	public void config(final int resLayout) {
//		new AsyncTask<String, String, String>() {
//
//			protected String doInBackground(String... params) {
//				LayoutInflater li = (LayoutInflater) getContext().getSystemService(
//						Activity.LAYOUT_INFLATER_SERVICE);
//				li.inflate(resLayout, ProfileSportView.this);
//				return null;
//			}
//		}.execute("");

		// sportItems[0] = (ProfileSportItemView)
		// findViewById(R.id.profileSportItemView1);
		// sportItems[1] = (ProfileSportItemView)
		// findViewById(R.id.profileSportItemView1);
		// sportItems[2] = (ProfileSportItemView)
		// findViewById(R.id.profileSportItemView1);
	}

}