package com.caferhythm.csn.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.data.SP04Entity;
import com.caferhythm.csn.fragment.AdsFragment;
import com.caferhythm.csn.fragment.LoadNewFeedFragment;
import com.caferhythm.csn.json.JsonPaser;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Sp04Activity extends FragmentActivity {
	private TextView s04_top1_right;
	private TextView s04_top2_right;
	private SP04Entity sp04Entity;
	private LinearLayout sp04_list_day;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		// setContentTab(getString(R.string.sp_04_title), R.layout.sp04layout);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sp04layout);
		getUIComponent();
		pd = new ProgressDialog(this);
		pd.setMessage("Loading data from server...");
		pd.show();
		getServerData();
		findViewById(R.id.bt_sp04_chosing_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		LoadNewFeedFragment loadNewFeedFragment =  new LoadNewFeedFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.newsarea, loadNewFeedFragment, "News");
		AdsFragment adsFragment = new AdsFragment("preg_list");
		ft.add(R.id.adsarea, adsFragment, "Ads");
		ft.commit();
	}

	public void getServerData() {
		Connection.get(API.API_S003 + FlashScreenActivity.token, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String res) {
						super.onSuccess(res);
						Log.e("RESSP04", res);
						if(pd!=null && pd.isShowing())
							pd.dismiss();
						sp04Entity = JsonPaser.getSp04Entity(res);
						if (sp04Entity.getErrorEntity().getCode() == 200) {
							sp04Entity.dateFormat();
							buildUIComponent();
						} else {
							showError(sp04Entity.getErrorEntity().getMessage());
						}
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						super.onFailure(arg0, arg1);
						showError(getResources().getString(R.string.ms_cannot_get_data));
					}
				});
	}

	private void showError(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}

	public void setUIComponent() {

	}

	public void buildUIComponent() {
		//SimpleDateFormat s1 = new SimpleDateFormat("yyyy/mm/dd");
		//SimpleDateFormat s2 = new SimpleDateFormat("yyyy-mm-dd");
		s04_top1_right.setText((sp04Entity.getSeiri().getStart()).replace("-", "/"));
		s04_top2_right.setText((sp04Entity.getHairan().getStart()).replace("-", "/"));
		int itemnum = 0;
		String about = getResources().getString(R.string.about);
		for (int i = 0; i < sp04Entity.getTerm().size(); i++) {

			View v1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.sp04_item, null);
			
			((TextView) v1.findViewById(R.id.sp04_bottom_text)).setText(sp04Entity.getTerm().get(i).getStart() + about+ "~" + sp04Entity.getTerm().get(i).getEnd()+ about);
			
			if (sp04Entity.getTerm().get(i).getStatus().equals("highest")) {
				((ImageView) v1.findViewById(R.id.sp04_bottom_image)).setImageResource(R.drawable.sp_04_text_1);
			}
			
			if (sp04Entity.getTerm().get(i).getStatus().equals("high")) {
				((ImageView) v1.findViewById(R.id.sp04_bottom_image)).setImageResource(R.drawable.sp_04_text_2);
			}
			
			if (sp04Entity.getTerm().get(i).getStatus().equals("middle")) {
				((ImageView) v1.findViewById(R.id.sp04_bottom_image)).setImageResource(R.drawable.sp_04_text_3);
			}
			
			if (sp04Entity.getTerm().get(i).getStatus().equals("low")) {
				((ImageView) v1.findViewById(R.id.sp04_bottom_image)).setImageResource(R.drawable.sp_04_text_4);
			}
			sp04_list_day.addView(v1);
			itemnum++;
			if (itemnum == sp04Entity.getTerm().size()) {
				((LinearLayout) v1.findViewById(R.id.seperator))
						.setVisibility(View.GONE);

			}

		}
		/*
		for (int i = 0; i < sp04Entity.getTerm().size(); i++) {
			if (sp04Entity.getTerm().get(i).getStatus().equals("highest")) {
				View v1 = LayoutInflater.from(getApplicationContext()).inflate(
						R.layout.sp04_item, null);
				((TextView) v1.findViewById(R.id.sp04_bottom_text))
						.setText(sp04Entity.getTerm().get(i).getStart() + about
								+ "~" + sp04Entity.getTerm().get(i).getEnd()
								+ about);
				((ImageView) v1.findViewById(R.id.sp04_bottom_image))
						.setImageResource(R.drawable.sp_04_text_1);
				sp04_list_day.addView(v1);
				itemnum++;
				if (itemnum == sp04Entity.getTerm().size()) {
					((LinearLayout) v1.findViewById(R.id.seperator))
							.setVisibility(View.GONE);
				}
			}

		}
		
		for (int i = 0; i < sp04Entity.getTerm().size(); i++) {
			if (sp04Entity.getTerm().get(i).getStatus().equals("high")) {
				View v1 = LayoutInflater.from(getApplicationContext()).inflate(
						R.layout.sp04_item, null);
				((TextView) v1.findViewById(R.id.sp04_bottom_text))
						.setText(sp04Entity.getTerm().get(i).getStart() + about
								+ "~" + sp04Entity.getTerm().get(i).getEnd()
								+ about);
				((ImageView) v1.findViewById(R.id.sp04_bottom_image))
						.setImageResource(R.drawable.sp_04_text_2);
				sp04_list_day.addView(v1);
				itemnum++;
				if (itemnum == sp04Entity.getTerm().size()) {
					((LinearLayout) v1.findViewById(R.id.seperator))
							.setVisibility(View.GONE);
				}
			}
		}

		for (int i = 0; i < sp04Entity.getTerm().size(); i++) {
			if (sp04Entity.getTerm().get(i).getStatus().equals("middle")) {
				View v1 = LayoutInflater.from(getApplicationContext()).inflate(
						R.layout.sp04_item, null);
				((TextView) v1.findViewById(R.id.sp04_bottom_text))
						.setText(sp04Entity.getTerm().get(i).getStart() + about
								+ "~" + sp04Entity.getTerm().get(i).getEnd()
								+ about);
				((ImageView) v1.findViewById(R.id.sp04_bottom_image))
						.setImageResource(R.drawable.sp_04_text_3);
				sp04_list_day.addView(v1);
				itemnum++;
				if (itemnum == sp04Entity.getTerm().size()) {
					((LinearLayout) v1.findViewById(R.id.seperator))
							.setVisibility(View.GONE);
				}
			}
		}

		for (int i = 0; i < sp04Entity.getTerm().size(); i++) {
			if (sp04Entity.getTerm().get(i).getStatus().equals("low")) {
				View v1 = LayoutInflater.from(getApplicationContext()).inflate(
						R.layout.sp04_item, null);
				((TextView) v1.findViewById(R.id.sp04_bottom_text))
						.setText(sp04Entity.getTerm().get(i).getStart() + about
								+ "~" + sp04Entity.getTerm().get(i).getEnd()
								+ about);
				((ImageView) v1.findViewById(R.id.sp04_bottom_image))
						.setImageResource(R.drawable.sp_04_text_4);
				sp04_list_day.addView(v1);
				itemnum++;
				if (itemnum == sp04Entity.getTerm().size()) {
					((LinearLayout) v1.findViewById(R.id.seperator))
							.setVisibility(View.GONE);
				}
			}
		}
		*/

		if (sp04Entity.getTerm().size() > 0) {
			sp04_list_day.setVisibility(View.VISIBLE);
		}
	}

	public void getUIComponent() {
		s04_top1_right = (TextView) findViewById(R.id.sp_04_top1_right);
		s04_top2_right = (TextView) findViewById(R.id.sp_04_top2_right);
		sp04_list_day = (LinearLayout) findViewById(R.id.list_days);
	}
}
