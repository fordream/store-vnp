package com.caferhythm.csn.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.caferhythm.csn.R;
import com.caferhythm.csn.calender.CalenderListViewActivity;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.configure.Configure;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.data.Fluctuation;
import com.caferhythm.csn.data.SP04Entity;
import com.caferhythm.csn.fragment.AdsFragment;
import com.caferhythm.csn.fragment.LoadNewFeedFragment;
import com.caferhythm.csn.json.JsonPaser;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MyPageActivity extends BaseActivityWithHeadtab {

	public static final String DATESTRING = "date string";
	// view on screen
	private LinearLayout area01LO;
	private LinearLayout area02LO;
	private LinearLayout area03LO;
	private LinearLayout area04LO;
	private TextView dateIndicator01;
	private TextView dateIndicator02;
	private TextView dateIndicator03;
	private TextView dateIndicator04;
	private ImageView starImage;
	private TextView date01;
	private TextView date02;
	private TextView date03;
	private TextView date04;
	private ImageView imgHeart;

	final SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
	final SimpleDateFormat s2 = new SimpleDateFormat("MM-dd");
	final SimpleDateFormat s3 = new SimpleDateFormat("M/dd");
	final SimpleDateFormat s4 = new SimpleDateFormat("EEE");

	// data
	private Fluctuation fluctuation;
	private SP04Entity period;

	private String saveDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		fluctuation = new Fluctuation();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentTab(getString(R.string.mypage), R.layout.my_page_screen);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		starImage = (ImageView) findViewById(R.id.img_mypage_star);
		saveDate = getSharedPreferences(Configure.SHARED_PREFERENCES,
				MODE_PRIVATE).getString(DATESTRING, "");
		genView();
		if (saveDate.length() > 0)
			date01.setText(saveDate.substring(5).replace("-", "/"));
		getFluctuation();
		getPeriod();

		LoadNewFeedFragment loadNewFeedFragment = new LoadNewFeedFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.newsarea, loadNewFeedFragment, "News");
		AdsFragment adsFragment = new AdsFragment("mypage");
		ft.add(R.id.adsarea, adsFragment, "Ads");
		ft.commit();
	}

	private void genView() {
		area01LO = (LinearLayout) findViewById(R.id.lo_mypage_star1);
		area02LO = (LinearLayout) findViewById(R.id.lo_mypage_star2);
		area03LO = (LinearLayout) findViewById(R.id.lo_mypage_star3);
		area04LO = (LinearLayout) findViewById(R.id.lo_mypage_star4);
		dateIndicator01 = (TextView) findViewById(R.id.tv_mypage_02);
		dateIndicator02 = (TextView) findViewById(R.id.tv_mypage_03);
		dateIndicator03 = (TextView) findViewById(R.id.tv_mypage_06);
		dateIndicator04 = (TextView) findViewById(R.id.tv_mypage_07);
		date01 = (TextView) findViewById(R.id.tv_mypage_day1);
		date02 = (TextView) findViewById(R.id.tv_mypage_day2);
		date03 = (TextView) findViewById(R.id.tv_mypage_day3);
		date04 = (TextView) findViewById(R.id.tv_mypage_day4);
		findViewById(R.id.bt_head_item_menu1).setVisibility(View.VISIBLE);
		findViewById(R.id.bt_head_item_menu2).setVisibility(View.VISIBLE);
		imgHeart = (ImageView) findViewById(R.id.img_heart_line);
		findViewById(R.id.bt_head_item_menu1).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),
								SP01Activity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
		findViewById(R.id.bt_head_item_menu2).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),
								CalenderListViewActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
		findViewById(R.id.lo_graph).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						Sp04Activity.class));
			}
		});
		findViewById(R.id.lo_mypage_diet).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (fluctuation.getPt_01() > 0) {
							Intent i = new Intent(getApplicationContext(),
									Sp05Activity.class);
							i.putExtra("fluctuation", fluctuation);
							i.putExtra("page", 1);
							startActivity(i);
						}
					}
				});
		findViewById(R.id.mypage_page_4).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (fluctuation.getPt_04() > 0) {
							Intent i = new Intent(getApplicationContext(),
									Sp05Activity.class);
							i.putExtra("fluctuation", fluctuation);
							i.putExtra("page", 4);
							startActivity(i);
						}
					}
				});
		findViewById(R.id.mypage_type_2).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (fluctuation.getPt_02() > 0) {
							Intent i = new Intent(getApplicationContext(),
									Sp05Activity.class);
							i.putExtra("fluctuation", fluctuation);
							i.putExtra("page", 2);
							startActivity(i);
						}
					}
				});
		findViewById(R.id.mypage_type_3).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (fluctuation.getPt_03() > 0) {
							Intent i = new Intent(getApplicationContext(),
									Sp05Activity.class);
							i.putExtra("fluctuation", fluctuation);
							i.putExtra("page", 3);
							startActivity(i);
						}
					}
				});
	}

	private void getFluctuation() {
		Connection.get(API.API_S0032 + FlashScreenActivity.token, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						Log.i("test", "return:" + arg0);
						fluctuation = JsonPaser.paserFluctuation(arg0);
						drawStar(area01LO, fluctuation.getPt_04());
						drawStar(area02LO, fluctuation.getPt_02());
						drawStar(area03LO, fluctuation.getPt_01());
						drawStar(area04LO, fluctuation.getPt_03());
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
					}
				});
	}

	private void getPeriod() {
		Connection.get(API.API_S003 + FlashScreenActivity.token, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						Log.i("test","return rs:"+arg0);
						period = JsonPaser.getSp04Entity(arg0);
						if (period.getHairan().getEnd().length() < 1)
							return;
						String temp = "";
						temp = period.getHairan().getStart();
						temp = temp.substring(5).replace("-", "/");
						date02.setText(temp);
						temp = period.getSeiri().getEnd();
						temp = temp.substring(5).replace("-", "/");
						date04.setText(temp);

						try {
							dateIndicator04.setText(s4.format(s1.parse(period
									.getSeiri().getStart())));
							dateIndicator03.setText(s3.format(s1.parse(period
									.getSeiri().getStart())));
							Date current = new Date();
							if (current.after(s1.parse(period.getSeiri()
									.getEnd()))) {
								dateIndicator01.setText(getResources()
										.getString(R.string.excess));
							}
							int d = 24 * 3600000;

							dateIndicator02.setText(""
									+ ((int) Math.abs((current.getTime() - s1
											.parse(period.getSeiri().getStart())
											.getTime())
											/ d)+1));
							Date d1 = s1.parse(period.getHairan().getStart());
							Date d2 = s1.parse(period.getSeiri().getEnd());
							Date d3 = (Date) d1.clone();
							long timeDiff = d2.getTime() - d1.getTime();
							Date t = new Date(d3.getTime() + timeDiff / 2);
							date03.setText(s2.format(t).replace("-", "/"));
							RelativeLayout l = (RelativeLayout) findViewById(R.id.lo_graph);
							LayoutParams lpr = (LayoutParams) date03
									.getLayoutParams();
							int te = ((l.getWidth() - date01.getWidth() / 2 - date04
									.getWidth() / 2)) / 3;
							lpr.leftMargin = 2 * te + date03.getWidth() / 2
									- date01.getWidth() / 2;
							date03.setLayoutParams(lpr);
							lpr = (LayoutParams) date02.getLayoutParams();
							lpr.leftMargin = te + date02.getWidth() / 2
									- date01.getWidth() / 2;
							date02.setLayoutParams(lpr);
							// Date d3 = (d2.)/2 +d1;
							drawHeart();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
					}
				});
	}

	private void drawStar(LinearLayout layout, int rate) {
		for (int i = 1; i <= rate / 20; i++) {
			if(i==rate/20&&rate%20==0)
				break;
			ImageView tempImage = new ImageView(this);
			tempImage.setImageResource(R.drawable.star_icon);
			tempImage.setLayoutParams(starImage.getLayoutParams());
			layout.addView(tempImage);
		}
	}

	private void drawHeart() {
		Date current = new Date();
		int position;
		RelativeLayout graphLO = (RelativeLayout) findViewById(R.id.lo_graph);

		String t = getSharedPreferences(Configure.SHARED_PREFERENCES,
				MODE_PRIVATE).getString(DATESTRING, "");
		if (t.length() < 1)
			return;
		try {
			if (current.before(s1.parse(t))) {
				imgHeart.setVisibility(View.GONE);
				return;
			} else if (current.after(s1.parse(period.getSeiri().getEnd()))) {
				findViewById(R.id.img_message).setVisibility(View.VISIBLE);
				imgHeart.setVisibility(View.GONE);
				return;
			} else {
				if (current.before(s1.parse(period.getHairan().getStart()))) {
					float f1 = subDate(current, s1.parse(t));
					float f2 = subDate(s1.parse(t),
							s1.parse(period.getHairan().getStart()));
					Log.i("test", f1 + "--" + f2);
					position = (date01.getWidth() - imgHeart.getWidth())
							/ 2
							+ (int) (f1 / f2 * (1.0f / 3.0f) * graphLO
									.getWidth());
					Log.i("test", "" + date02.getLeft());
				} else {
					float f1 = subDate(current,
							s1.parse(period.getHairan().getStart()));
					float f2 = subDate(s1.parse(period.getHairan().getStart()),
							s1.parse(period.getSeiri().getStart()));
					Log.i("test", "f1:" + f1 + " f2:" + f2);
					position = (int) ((1.0f / 3.0f) * (findViewById(R.id.lo_graph)
							.getWidth()))
							+ ((int) ((f1 / f2) * (2f / 3f) * (findViewById(R.id.lo_graph)
									.getWidth()-date04.getWidth())));
					Log.i("test", "ngon:" + date02.getWidth() / 2);

				}
				imgHeart.setVisibility(View.VISIBLE);
				LayoutParams lp = (LayoutParams) imgHeart.getLayoutParams();
				lp.leftMargin = (int) (position + graphLO.getLeft());
				imgHeart.setLayoutParams(lp);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private float subDate(Date d1, Date d2) {
		return Math.abs(d1.getTime() - d2.getTime()) / (24 * 3600000);
	}

}
