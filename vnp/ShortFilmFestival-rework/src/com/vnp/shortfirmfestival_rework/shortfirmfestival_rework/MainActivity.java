package com.vnp.shortfirmfestival_rework.shortfirmfestival_rework;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ProgressBar;

import com.vnp.shortfirmfestival_rework.R;
import com.vnp.shortfirmfestival_rework.adapter.MenuLeftAdapter;
import com.vnp.shortfirmfestival_rework.base.ShortBaseFragment;
import com.vnp.shortfirmfestival_rework.base.ShortFirmBaseActivity;
import com.vnp.shortfirmfestival_rework.fragement.AllFragement;
import com.vnp.shortfirmfestival_rework.fragement.AppsFragement;
import com.vnp.shortfirmfestival_rework.fragement.PhotoFragement;

public class MainActivity extends ShortFirmBaseActivity implements Runnable {
	private ProgressBar header_progressbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slide_main);
		header_progressbar = (ProgressBar) findViewById(R.id.header_progressbar);
		header_progressbar.setVisibility(View.GONE);

		View header_btn_left = findViewById(R.id.slide_main_header).findViewById(R.id.header_btn_left);
		header_btn_left.setVisibility(View.VISIBLE);
		header_btn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openMenu();
			}
		});
		new Handler().postDelayed(this, getTimeStartAnimation());
		changeFragemtn(new AllFragement("all", header_progressbar));
	}

	private void changeFragemtn(ShortBaseFragment allFragement) {
		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager.beginTransaction().replace(R.id.content_frame_main, allFragement).commit();
	}

	@Override
	public void run() {
		ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.left_drawer);
		View view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header, null);
		expandableListView.addHeaderView(view);
		final MenuLeftAdapter adapter = new MenuLeftAdapter();
		expandableListView.setAdapter(adapter);

		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

				int[] data = (int[]) adapter.getGroup(groupPosition);
				if (data[0] == R.drawable.app_active) {
					closeMenu();
					changeFragemtn(new AppsFragement(header_progressbar));
				} else if (data[0] == R.drawable.photo_active) {
					closeMenu();
					changeFragemtn(new PhotoFragement("all", header_progressbar));
				}

				return false;
			}
		});

		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				closeMenu();

				int data = (Integer) adapter.getChild(groupPosition, childPosition);
				if (data == R.string.menu_child_1_all) {
					changeFragemtn(new AllFragement("all", header_progressbar));
				} else if (data == R.string.menu_child_1_biz) {
					changeFragemtn(new AllFragement("biz", header_progressbar));
				} else if (data == R.string.menu_child_1_festival) {
					changeFragemtn(new AllFragement("ssff", header_progressbar));
				} else if (data == R.string.menu_child_1_lounge) {
					changeFragemtn(new AllFragement("lounge", header_progressbar));
				} else if (data == R.string.menu_child_1_theater) {
					changeFragemtn(new AllFragement("theater", header_progressbar));
				}

				if (data == R.string.menu_child_2_all) {
					changeFragemtn(new PhotoFragement("all", header_progressbar));
				} else if (data == R.string.menu_child_2_biz) {
					changeFragemtn(new PhotoFragement("biz", header_progressbar));
				} else if (data == R.string.menu_child_2_festival) {
					changeFragemtn(new PhotoFragement("ssff", header_progressbar));
				} else if (data == R.string.menu_child_2_lounge) {
					changeFragemtn(new PhotoFragement("lounge", header_progressbar));
				} else if (data == R.string.menu_child_2_theater) {
					changeFragemtn(new PhotoFragement("theater", header_progressbar));
				}

				return false;
			}
		});
	}

	protected void closeMenu() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
				drawer_layout.closeDrawers();
			}
		});
	}

	private void openMenu() {
		DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer_layout.openDrawer(findViewById(R.id.left_drawer));
	}
}