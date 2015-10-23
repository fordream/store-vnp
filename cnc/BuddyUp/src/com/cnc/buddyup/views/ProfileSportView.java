package com.cnc.buddyup.views;


import org.com.cnc.common.android.runable.LoadingViewRunable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cnc.buddyup.ProfileScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.asyn.AsynProfileWeekend;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.common.views.LoadingView;
import com.cnc.buddyup.response.ResponseProfileWeekend;
import com.cnc.buddyup.views.profile.ProfileSportItemView;

public class ProfileSportView extends LinearLayout {
	private ProfileSportItemView sport1;
	private ProfileSportItemView sport2;
	private ProfileSportItemView sport3;
	private LoadingView loadingView;

	public ProfileSportView(Context context) {
		super(context);
		config();
	}

	public ProfileSportView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public void showLoadingView(boolean check) {
		loadingView.post(new LoadingViewRunable(loadingView, check));
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.profile_sport_information, this);
		loadingView = (LoadingView) findViewById(R.id.loadingView1);

		sport1 = (ProfileSportItemView) findViewById(R.id.profileSportItemView1);
		sport1.setInDex(1);

		sport2 = (ProfileSportItemView) findViewById(R.id.ProfileSportItemView02);
		sport2.setInDex(2);

		sport3 = (ProfileSportItemView) findViewById(R.id.ProfileSportItemView01);
		sport3.setInDex(3);

		findViewById(R.id.commonbtnBack).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						((ProfileScreen) getContext()).addBack();
					}
				});

		new AsynProfileWeekend(this).execute("");
	}

	public void updateSkillLevel(String id, String name, String idView) {
		if (idView.equals(sport1.getIndex() + "")) {
			sport1.updateSkillLevel(id, name);
		} else if (idView.equals(sport2.getIndex() + "")) {
			sport2.updateSkillLevel(id, name);
		} else if (idView.equals(sport3.getIndex() + "")) {
			sport3.updateSkillLevel(id, name);
		}
	}

	public void updateSportName(String id, String name, String idView) {
		if (idView.equals(sport1.getIndex() + "")) {
			sport1.updateSportName(id, name);
		} else if (idView.equals(sport2.getIndex() + "")) {
			sport2.updateSportName(id, name);
		} else if (idView.equals(sport3.getIndex() + "")) {
			sport3.updateSportName(id, name);
		}
	}

	public void updatePhiloshophy(String id, String name, String idView) {
		if (idView.equals(sport1.getIndex() + "")) {
			sport1.updatePhiloshophy(id, name);
		} else if (idView.equals(sport2.getIndex() + "")) {
			sport2.updatePhiloshophy(id, name);
		} else if (idView.equals(sport3.getIndex() + "")) {
			sport3.updatePhiloshophy(id, name);
		}
	}

	public void update(ResponseProfileWeekend response) {
		if ("0".equals(response.getStatus())) {
			sport1.update(response.getSport1());
			sport2.update(response.getSport2());
			sport3.update(response.getSport3());
		} else {
			Common.builder(getContext(), getString(R.string.message1),
					response.getMessage());
		}
	}

	private String getString(int res) {
		return getResources().getString(res);
	}
}
