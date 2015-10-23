package com.caferhythm.csn.calender;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caferhythm.csn.R;
import com.caferhythm.csn.activity.BaseActivityWithHeadtab;
import com.caferhythm.csn.activity.CalendarContentActivity;
import com.caferhythm.csn.activity.FlashScreenActivity;
import com.caferhythm.csn.activity.MyPageActivity;
import com.caferhythm.csn.activity.SP01Activity;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.data.S007Entity;
import com.caferhythm.csn.data.SP04Entity;
import com.caferhythm.csn.fragment.AdsFragment;
import com.caferhythm.csn.json.JsonPaser;
import com.caferhythm.csn.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@SuppressLint({ "ParserError", "ParserError" })
public class CalenderListViewActivity extends BaseActivityWithHeadtab {
	private ArrayList<String> listDate;
	private LinearLayout calender;
	private Calendar month;
	private Calendar today;
	private TextView title;
	private ImageView next;
	private ImageView previous;
	private SP04Entity sp04Entity;
	private ArrayList<S007Entity> listS007Entities;
	static final int FIRST_DAY_OF_WEEK = 0; // Sunday = 0, Monday = 1
	private ArrayList<CalenderItem> days;
	private boolean ispinked = false;
	private boolean isYellowed = false;
	private Button bt_head_item_menu1;
	private Button bt_head_item_menu2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentTab(getString(R.string.calender_activity),
				R.layout.calender_listview);
		super.onCreate(savedInstanceState);
		title = (TextView) findViewById(R.id.title_list);
		next = (ImageView) findViewById(R.id.next);
		previous = (ImageView) findViewById(R.id.previous);
		bt_head_item_menu1 = (Button) findViewById(R.id.bt_head_item_menu1);
		bt_head_item_menu2 = (Button) findViewById(R.id.bt_head_item_menu2);
		bt_head_item_menu1.setBackgroundResource(R.drawable.sidemenu0);
		bt_head_item_menu2.setBackgroundResource(R.drawable.sidemenu1);
		bt_head_item_menu1.setVisibility(View.VISIBLE);
		bt_head_item_menu2.setVisibility(View.VISIBLE);
		bt_head_item_menu1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						MyPageActivity.class);
				startActivity(intent);
			}
		});

		bt_head_item_menu2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						SP01Activity.class);
				startActivity(intent);
			}
		});
		month = Calendar.getInstance();
		today = Calendar.getInstance();
		month.set(Calendar.DAY_OF_MONTH, 1);
		title.setText(displayDate(month));
		listDate = new ArrayList<String>();
		calender = (LinearLayout) findViewById(R.id.calendar);
		for (int i = 0; i < 100; i++) {
			String s = "This is test string" + i;
			listDate.add(s);
		}
		getServerData();
		refreshDays();
		buildCalender();
		getS007Data((String) android.text.format.DateFormat.format("yyyy-MM",
				month));
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month
						.getActualMaximum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) + 1),
							month.getActualMinimum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
				}
				refreshCalender();
				getS007Data((String) android.text.format.DateFormat.format(
						"yyyy-MM", month));

			}

		});

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month
						.getActualMinimum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) - 1),
							month.getActualMaximum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
				}
				refreshCalender();
				getS007Data((String) android.text.format.DateFormat.format(
						"yyyy-MM", month));

			}
		});
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		AdsFragment adsFragment = new AdsFragment("mypage");
		ft.add(R.id.adsarea, adsFragment, "Ads");
		ft.commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		getS007Data((String) android.text.format.DateFormat.format("yyyy-MM",
				month));
		super.onResume();
	}

	private String displayDate(Calendar mon) {
		String day = mon.get(Calendar.YEAR) +getResources().getString(R.string.year)
				+ (mon.get(Calendar.MONTH) + 1) + getResources().getString(R.string.month);
		return day;
	}

	@SuppressLint({ "ResourceAsColor", "ResourceAsColor" })
	public void buildCalender() {
		calender.removeAllViews();
		for (int i = 0; i < 6; i++) {
			View view = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.calender_list_item, null);
			for (int j = 0; j < 7; j++) {
				final ViewHolder viewHolder = new ViewHolder();
				Log.e("TAG", "layout" + (j + 1));
				viewHolder.item = (LinearLayout) view.findViewWithTag("layout"
						+ (j + 1));
				viewHolder.icon = (ImageView) view
						.findViewWithTag("calender_iv_day" + (j + 1));
				viewHolder.itemNum = (i * 7) + j;

				if (days.get(viewHolder.itemNum).isToday) {
					viewHolder.item
							.setBackgroundResource(R.drawable.orange_box);
				}

				if (days.get(viewHolder.itemNum).isPink) {
					viewHolder.item.setBackgroundResource(R.drawable.pink_box);
				}

				viewHolder.day = (TextView) view
						.findViewWithTag("calender_tv_day" + (j + 1));
				if (viewHolder.day != null) {
					if (days.get(viewHolder.itemNum).isToday
							|| days.get(viewHolder.itemNum).isPink) {
						viewHolder.day.setTextColor(Color.WHITE);
					}
					viewHolder.day.setText(""
							+ days.get(viewHolder.itemNum).day);

				}
				if (days.get(viewHolder.itemNum).isYellow) {
					viewHolder.icon.setImageResource(R.drawable.chick_icon);
					viewHolder.icon.setVisibility(View.VISIBLE);
				}

				if (days.get(viewHolder.itemNum).isPinkMoon) {
					viewHolder.icon.setImageResource(R.drawable.moon_icon);
					viewHolder.icon.setVisibility(View.VISIBLE);
				}

				if (days.get(viewHolder.itemNum).isNote) {
					if (days.get(viewHolder.itemNum).isYellow) {
						viewHolder.icon.setImageResource(R.drawable.chickmusic);
						viewHolder.icon.setVisibility(View.VISIBLE);
					} else {
						if (days.get(viewHolder.itemNum).isPinkMoon) {
							viewHolder.icon
									.setImageResource(R.drawable.moonmusic);
							viewHolder.icon.setVisibility(View.VISIBLE);
						} else {
							viewHolder.icon.setVisibility(View.VISIBLE);
						}
					}
				}
				viewHolder.item.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						if (!days.get(viewHolder.itemNum).day.equals("")) {
							Intent i = new Intent(getApplicationContext(),
									CalendarContentActivity.class);
							if (days.get(viewHolder.itemNum).isNote) {
								i.putExtra("note",
										days.get(viewHolder.itemNum).memo);

							} else {
								i.putExtra("note", "");
							}

							days.get(viewHolder.itemNum).isNote = !days
									.get(viewHolder.itemNum).isNote;
							String dateShow = displayDate(month)
									+ days.get(viewHolder.itemNum).day
									+ getResources().getString(R.string.day);
							i.putExtra("dateShow", dateShow);
							i.putExtra("date",
									days.get(viewHolder.itemNum).fullDate);
							startActivity(i);
						}

					}
				});
			}
			calender.addView(view);
		}
	}

	public void getServerData() {
		Connection.get(API.API_S003 + FlashScreenActivity.token, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String res) {
						super.onSuccess(res);
						Log.e("RESSP04", res);
						sp04Entity = JsonPaser.getSp04Entity(res);

						if (sp04Entity.getErrorEntity().getCode() == 200) {
							refreshCalender();
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

	public void getS007Data(String month) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("month", month);
		requestParams.put("token", FlashScreenActivity.token);
		Connection.get(API.API_S007, requestParams,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String res) {
						super.onSuccess(res);
						Log.i("RESSP04", res);
						listS007Entities = JsonPaser.getS007Entities(res);
						refreshCalender();
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						super.onFailure(arg0, arg1);
						showError(getResources().getString(R.string.ms_cannot_get_data));
					}
				});
	}

	private void showError(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
				.show();
	}

	public boolean isToday(String day) {

		if (today.get(Calendar.MONTH) == month.get(Calendar.MONTH)
				&& today.get(Calendar.YEAR) == month.get(Calendar.YEAR)) {
			return ((today.get(Calendar.DAY_OF_MONTH) + "").equals(day));
		} else {
			return false;
		}
	}

	public boolean isPink(String day) {
		boolean result = false;
		if (sp04Entity != null) {
			for (int i = 0; i < sp04Entity.getTerm().size(); i++) {
				if (sp04Entity.getTerm().get(i).getStatus().equals("high")
						|| sp04Entity.getTerm().get(i).getStatus()
								.equals("highest")) {
					result = StringUtils.compareDate(sp04Entity.getTerm()
							.get(i).getStart(), day, sp04Entity.getTerm()
							.get(i).getEnd());
					if (result) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void checkAll(CalenderItem day) {

		if (listS007Entities != null) {
			for (int i = 0; i < listS007Entities.size(); i++) {
				S007Entity s007Entity = listS007Entities.get(i);
				if (s007Entity.getDays().equals(day.fullDate)) {
					if (s007Entity.getNextHairan() == 1 && !isYellowed) {
						day.isYellow = true;
						isYellowed = true;
					}
					if (s007Entity.getNextPeriod() == 1 && !ispinked) {
						day.isPinkMoon = true;
						ispinked = true;
					}
					if (!s007Entity.getMemo().equals("")) {
						day.isNote = true;
						day.memo = s007Entity.getMemo();
					}
				}
			}
		}
	}

	public void refreshCalender() {
		title.setText(displayDate(month));
		refreshDays();
		buildCalender();
	}

	public void refreshDays() {

		int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = (int) month.get(Calendar.DAY_OF_WEEK);
		int point = 0;
		isYellowed = false;
		ispinked = false;
		if (days == null) {
			days = new ArrayList<CalenderListViewActivity.CalenderItem>();
		} else {
			days.clear();
		}
		for (int p = 0; p < 42; p++) {
			CalenderItem c = new CalenderItem();
			c.day = "";
			c.isPink = false;
			c.isNote = false;
			c.isToday = false;
			c.isPinkMoon = false;
			c.isYellow = false;
			c.fullDate = "";
			days.add(c);
		}

		if (firstDay == 1) {
			point = lastDay + (FIRST_DAY_OF_WEEK * 6);
		} else {
			point = firstDay + lastDay - (FIRST_DAY_OF_WEEK + 1);
		}

		int j = FIRST_DAY_OF_WEEK;

		if (firstDay > 1) {
			j = firstDay - FIRST_DAY_OF_WEEK;
		} else {
			j = FIRST_DAY_OF_WEEK * 6 + 1;
		}
		// populate days
		int dayNumber = 1;
		for (int i = j - 1; i < point; i++) {
			if (dayNumber < 10) {
				days.get(i).day = "0" + dayNumber;
			} else {
				days.get(i).day = "" + dayNumber;
			}
			if ((month.get(Calendar.MONTH) + 1) < 10) {
				days.get(i).fullDate = month.get(Calendar.YEAR) + "-0"
						+ (month.get(Calendar.MONTH) + 1) + "-"
						+ days.get(i).day;
			} else {
				days.get(i).fullDate = month.get(Calendar.YEAR) + "-"
						+ (month.get(Calendar.MONTH) + 1) + "-"
						+ days.get(i).day;
			}
			if (isToday("" + dayNumber)) {
				days.get(i).isToday = true;
			}
			if (isPink(days.get(i).fullDate)) {
				days.get(i).isPink = true;
			}
			checkAll(days.get(i));
			dayNumber++;
		}
	}

	private static class ViewHolder {
		private LinearLayout item;
		private TextView day;
		private ImageView icon;
		private int itemNum;
	}

	private static class CalenderItem {
		private String day;
		private boolean isToday;
		private boolean isPink;
		private boolean isPinkMoon;
		private boolean isYellow;
		private boolean isNote;
		private String fullDate;
		private String memo;
	}

}
