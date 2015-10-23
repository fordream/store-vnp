package com.caferhythm.csn.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.configure.Configure;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.data.SP04Entity;
import com.caferhythm.csn.fragment.AdsFragment;
import com.caferhythm.csn.fragment.LoadNewFeedFragment;
import com.caferhythm.csn.json.JsonPaser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Seiri01Activity extends BaseActivityWithHeadtab {
	// view on screen
	private TextView maikaiTV;
	private TextView seirikikanTV;
	private TextView seirisyukiTV;
	private TextView anteiseiTV;
	private Button tourokuBT;

	private Context mContext;
	private String cycle = "";
	private String span = "";
	private String stable = "";
	private String dateString = "";
	private boolean isTourokuKanou = false;
	private Editor e;

	private ProgressDialog pd;

	private SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat s2;
	private RelativeLayout seri1_row1;
	private RelativeLayout seri1_row2;
	private RelativeLayout seri1_row3;
	private RelativeLayout seri1_row4;

	public void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentTab(getString(R.string.seiribi), R.layout.seiri01_screen);
		super.onCreate(savedInstanceState);
		mContext = this;
		e = getSharedPreferences(Configure.SHARED_PREFERENCES, MODE_PRIVATE)
				.edit();
		pd = new ProgressDialog(this);
		if (getIntent().getStringExtra("first") != null) {
			findViewById(R.id.bt_head_menu).setVisibility(View.INVISIBLE);
		}
		Resources r = getResources();
		s2 = new SimpleDateFormat("yyyy" + r.getString(R.string.year) + "MM"
				+ r.getString(R.string.month) + "dd"
				+ r.getString(R.string.day));
		// final LayoutInflater inflater = (LayoutInflater)
		// getSystemService(LAYOUT_INFLATER_SERVICE);
		maikaiTV = (TextView) findViewById(R.id.tv_seiri_01_date_picker);
		seirikikanTV = (TextView) findViewById(R.id.tv_seiri_01_time_picker);
		seirisyukiTV = (TextView) findViewById(R.id.tv_seiri_01_syuki);
		anteiseiTV = (TextView) findViewById(R.id.tv_seiri_01_save_level);
		tourokuBT = (Button) findViewById(R.id.bt_seiri01_screen_touroku);
		seri1_row1 = (RelativeLayout) findViewById(R.id.seri1_row1);
		seri1_row2 = (RelativeLayout) findViewById(R.id.seri1_row2);
		seri1_row3 = (RelativeLayout) findViewById(R.id.seri1_row3);
		seri1_row4 = (RelativeLayout) findViewById(R.id.seri1_row4);
		getDataFromServer();
		seri1_row1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						DateChosingActivity.class);
				if (dateString.length() > 0)
					i.putExtra("date", dateString);
				startActivityForResult(i, Configure.DATE_CHOSING);
			}
		});
		seri1_row3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						SeirisyukiChosingActivity.class);
				if (cycle.length() > 0)
					i.putExtra("extra", cycle);
				startActivityForResult(i, Configure.SEIRISYUKI_CHOSING);
			}
		});
		seri1_row2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						DataChosingActivity.class);
				if (span.length() > 0)
					i.putExtra("extra", span);
				startActivityForResult(i, Configure.DAYSEIRI_CHOSING);
			}
		});
		seri1_row4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						AnteiseiChosingActivity.class);
				if (stable.length() > 0)
					i.putExtra("extra", stable);
				startActivityForResult(i, Configure.ANTEISEI_CHOSING);
			}
		});
		tourokuBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isTourokuKanou) {
					if (pd != null && !pd.isShowing()) {
						pd.setTitle("Loading...");
						pd.show();
					}
					tourokuData();
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.ms_fill_out_all),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		if (getIntent().getStringExtra("first") == null) {
			LoadNewFeedFragment loadNewFeedFragment = new LoadNewFeedFragment();
			AdsFragment adsFragment = new AdsFragment("period_ins");
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.add(R.id.newsarea, loadNewFeedFragment, "News");
			ft.add(R.id.adsarea, adsFragment, "Ads");
			ft.commit();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (getIntent().getStringExtra("first") != null) {
			return;
		}
		super.onBackPressed();
	}

	private void tourokuData() {
		RequestParams params = new RequestParams();
		params.put("date", dateString);
		params.put("cycle", cycle);
		params.put("span", span);
		params.put("stable", stable);
		Log.i("test", "params:" + params);
		Connection.post(API.API_UPLOAD_DATA + FlashScreenActivity.token,
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						Log.i("test", "return :" + arg0);
						e.commit();
						if (pd != null && pd.isShowing())
							pd.dismiss();
						startActivity(new Intent(getApplicationContext(),
								MyPageActivity.class));
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						Log.i("test", "return on failure :" + arg0);
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Resources r = getResources();
		if (resultCode == RESULT_OK && requestCode == Configure.DATE_CHOSING) {
			if (data.hasExtra(DateChosingActivity.YEAR)) {
				String tm = "" + data.getIntExtra(DateChosingActivity.MONTH, 0);
				if (tm.length() < 2)
					tm = "0" + tm;
				maikaiTV.setText(data.getIntExtra(DateChosingActivity.YEAR, 0)
						+ r.getString(R.string.year)
						+ data.getIntExtra(DateChosingActivity.MONTH, 0)
						+ r.getString(R.string.month)
						+ data.getStringExtra(DateChosingActivity.DAY)
						+ r.getString(R.string.day));
				dateString = "" + data.getIntExtra(DateChosingActivity.YEAR, 0)
						+ "-" + tm + "-"
						+ data.getStringExtra(DateChosingActivity.DAY);
				e.putString(MyPageActivity.DATESTRING, dateString);
				isTourokuKanou = checkTourokuKanou();
			}
		}
		if (resultCode == RESULT_OK
				&& requestCode == Configure.DAYSEIRI_CHOSING) {
			if (data.hasExtra(DataChosingActivity.DAYSEIRI)) {
				seirikikanTV
						.setText(""
								+ (data.getIntExtra(
										DataChosingActivity.DAYSEIRI, 0) + 1)
								+ getResources().getString(R.string.day));
				span = ""
						+ (data.getIntExtra(DataChosingActivity.DAYSEIRI, 0) + 1);
				isTourokuKanou = checkTourokuKanou();
			}
		}
		if (resultCode == RESULT_OK
				&& requestCode == Configure.SEIRISYUKI_CHOSING) {
			if (data.hasExtra(SeirisyukiChosingActivity.SEIRISYUKI)) {
				seirisyukiTV.setText(""
						+ (data.getIntExtra(
								SeirisyukiChosingActivity.SEIRISYUKI, 0) + 20)
						+ getResources().getString(R.string.day));
				cycle = ""
						+ (data.getIntExtra(
								SeirisyukiChosingActivity.SEIRISYUKI, 0) + 20);
				isTourokuKanou = checkTourokuKanou();
			}
		}
		if (resultCode == RESULT_OK
				&& requestCode == Configure.ANTEISEI_CHOSING) {
			if (data.hasExtra(AnteiseiChosingActivity.ANTEISEI)) {
				anteiseiTV
						.setText(r.getStringArray(R.array.anteisei_array)[data
								.getIntExtra(AnteiseiChosingActivity.ANTEISEI,
										0)]);
				stable = ""
						+ (data.getIntExtra(AnteiseiChosingActivity.ANTEISEI, 0) + 1);
				isTourokuKanou = checkTourokuKanou();
			}
		}
	}

	private void getDataFromServer() {
		Connection.get(API.API_S003 + FlashScreenActivity.token, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						SP04Entity period = JsonPaser.getSp04Entity(arg0);
						if (period.getInfoEntity().getPrevious().length() < 1)
							return;
						stable = "" + period.getInfoEntity().getStable();
						span = "" + period.getInfoEntity().getSpan();
						cycle = "" + period.getInfoEntity().getCycle();
						dateString = period.getInfoEntity().getPrevious();
						try {
							maikaiTV.setText(s2.format(s1.parse(period
									.getInfoEntity().getPrevious())));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						seirikikanTV.setText(""
								+ period.getInfoEntity().getSpan()
								+ getResources().getString(R.string.day));
						seirisyukiTV.setText(""
								+ period.getInfoEntity().getCycle()
								+ getResources().getString(R.string.day));
						anteiseiTV.setText(getResources().getStringArray(
								R.array.anteisei_array)[period.getInfoEntity()
								.getStable() - 1]);
						isTourokuKanou = checkTourokuKanou();
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						Toast.makeText(
								mContext,
								getResources().getString(
										R.string.ms_cannot_connect_server),
								Toast.LENGTH_LONG).show();
					}
				});
	}

	private boolean checkTourokuKanou() {
		if (cycle.length() < 1)
			return false;
		if (stable.length() < 1)
			return false;
		if (span.length() < 1)
			return false;
		if (dateString.length() < 1)
			return false;
		return true;
	}
}
