package com.caferhythm.csn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.caferhythm.csn.R;
import com.caferhythm.csn.calender.CalenderListViewActivity;
import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;

public class BaseActivityWithHeadtab extends FragmentActivity implements
		OnSlideMenuItemClickListener {
	private String mTabName;
	private Button menuButton;
	private int layoutResource;
	private TextView headtabTextView;
	private PopupWindow popupWindow;
	private View popupView;
	private SlideMenu slidemenu;

	private Button menuHomeButton;

	public void setContentTab(String tabName, int layout) {
		mTabName = tabName;
		layoutResource = layout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(layoutResource);
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.slide, this, 333);
		menuButton = (Button) findViewById(R.id.bt_head_menu);
		headtabTextView = (TextView) findViewById(R.id.tv_head_name);
		headtabTextView.setText(mTabName);

		menuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (popupWindow != null && popupWindow.isShowing()) {
				// popupWindow.dismiss();
				// return;
				// }
				// LayoutInflater layoutInflater = (LayoutInflater)
				// getBaseContext()
				// .getSystemService(LAYOUT_INFLATER_SERVICE);
				// popupView = layoutInflater.inflate(R.layout.menu_popup,
				// null);
				// popupWindow = new PopupWindow(popupView,
				// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				// setActionForButtoMenu();
				// popupWindow.setAnimationStyle(R.style.PopupAnimation);
				// popupWindow.showAtLocation(menuButton, Gravity.LEFT, 0, 0);
				slidemenu.show();
			}
		});
	}

	private void setActionForButtoMenu() {
		menuHomeButton = (Button) popupView.findViewById(R.id.bt_menu_home);
		menuHomeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("test", "home clicked");
			}
		});
	}

	@SuppressWarnings("static-access")
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (slidemenu != null && slidemenu.isMenuShown()) {
			slidemenu.hide();
		} else
			super.onBackPressed();

	}

	@Override
	public void onSlideMenuItemClick(int itemId) {
		// TODO Auto-generated method stub
		switch (itemId) {
		case R.id.item_one:
			if (!getClass().toString().equals(MyPageActivity.class.toString())) {
				Intent intent = new Intent(getApplicationContext(),
						MyPageActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				slidemenu.hide();
			}
			break;
		case R.id.item_two:
			if (!getClass().toString().equals(SP01Activity.class.toString())) {
				Intent intent = new Intent(getApplicationContext(),
						SP01Activity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				slidemenu.hide();
			}
			break;
		case R.id.item_three:
			if (!getClass().toString().equals(Seiri01Activity.class.toString())) {
				Intent intent = new Intent(getApplicationContext(),
						Seiri01Activity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				slidemenu.hide();
			}
			break;
		case R.id.item_four:
			if (!getClass().toString().equals(
					BuildAlarmActivity.class.toString())) {
				Intent intent = new Intent(getApplicationContext(),
						BuildAlarmActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				slidemenu.hide();
			}
			break;
		case R.id.item_five:
			if (!getClass().toString().equals(
					BuildAlarmActivity2.class.toString())) {
				Intent intent = new Intent(getApplicationContext(),
						BuildAlarmActivity2.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				slidemenu.hide();
			}
			break;
		case R.id.item_six:
			if (!getClass().toString().equals(
					CalenderListViewActivity.class.toString())) {
				Intent intent = new Intent(getApplicationContext(),
						CalenderListViewActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				slidemenu.hide();
			}
			break;
		case R.id.item_seven:
			if (!getClass().toString().equals(
					MailAdressSettingActivity.class.toString())) {
				Intent intent = new Intent(getApplicationContext(),
						MailAdressSettingActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				slidemenu.hide();
			}
			break;
		case R.id.item_eight:
			if (!getClass().toString().equals(HelpActivity.class.toString())) {
				Intent intent = new Intent(getApplicationContext(),
						HelpActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				slidemenu.hide();
			}
			break;
		}
	}
}
