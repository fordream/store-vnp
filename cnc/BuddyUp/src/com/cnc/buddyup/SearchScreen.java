package com.cnc.buddyup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.buddyup.buddy.wheel.SportWheelActivity;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.data.parcelable.search.SearchRequestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.search.wheel.DayWheelActivity;
import com.cnc.buddyup.search.wheel.GenderWheelActivity;
import com.cnc.buddyup.search.wheel.TimeWheelActivity;
import com.cnc.buddyup.views.buddy.BuddyListInlineView;
import com.cnc.buddyup.views.search.SearchAdvancedView;
import com.cnc.buddyup.views.search.SearchResultItemView;
import com.cnc.buddyup.views.search.SearchResultView;
import com.cnc.buddyup.views.search.SearchSimpleView;
import com.cnc.buddyup.views.search.SearchSimpleView.Filed;
import com.cnc.buddyup.wheel.PhilosophyWheelActivity;

public class SearchScreen extends NActivity implements OnClickListener {
	private SearchSimpleView simpleViewView;
	private SearchAdvancedView advancedView;
	private SearchResultView resultView;
	private SearchResultItemView resultItemView;
	private BuddyListInlineView buddyListInlineView;
	private boolean isSimpleSearch = true;
	private Handler handler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// btn search simple search
			if (msg.what == Common.MESSAGE_WHAT_0) {
				SearchRequestParcelable parcelable = (SearchRequestParcelable) msg
						.getData().getParcelable(Common.ARG0);
				resultView.setListParcelable(Common.createListparcel1(),
						Common.createListparcel2());
				addView(resultView);

			} else if (msg.what == Common.MESSAGE_WHAT_1) {
				// btn advanced at simple search
				addView(advancedView);
			} else if (msg.what == Common.MESSAGE_WHAT_2) {
				// btn search at advanced search
				SearchRequestParcelable parcelable = (SearchRequestParcelable) msg
						.getData().getParcelable(Common.ARG0);
				addView(resultView);
				resultView.setListParcelable(Common.createListparcel(5),
						Common.createListparcel(2));
			} else if (msg.what == Common.MESSAGE_WHAT_COMMON) {
				CommonItemResquestParcelable parcelable = getCommonItemResquestParcelable(msg);
				addView(resultItemView);
			} else if (msg.what == Common.MESSAGE_WHAT_3) {
				// from result search User or groups
				CommonItemResquestParcelable parcelable = getCommonItemResquestParcelable(msg);
				addView(resultItemView);
			} else if (msg.what == Common.MESSAGE_WHAT_4) {
				// click button add Buddy at result item search
			} else if (msg.what == Common.MESSAGE_WHAT_5) {
				addView(buddyListInlineView);
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getButton(R.id.commonBtnAdd).setVisibility(View.GONE);
		getButton(R.id.commonbtnBack).setOnClickListener(this);
		configSimpleSearchView();

		configAdvancedSearch();
		configResultSearch();
		
		
		resultItemView = new SearchResultItemView(this, handler);
		buddyListInlineView = new BuddyListInlineView(this, handler);
		addView(simpleViewView);
	}

	private void configResultSearch() {
		resultView = new SearchResultView(this, handler);
		
	}

	private void configAdvancedSearch() {
		advancedView = new SearchAdvancedView(this, handler);
		OnClickListener clickListener = new OnClickListener() {

			public void onClick(View v) {
				SearchAdvancedView.Filed filed = advancedView.getFiled();
				if (v.getId() == filed.fTVDay.getId()) {
					Intent intent = new Intent(SearchScreen.this,
							DayWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				} else if (v.getId() == filed.fTVSport.getId()) {
					Intent intent = new Intent(SearchScreen.this,
							SportWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				} else if (v.getId() == filed.fTVTime.getId()) {
					Intent intent = new Intent(SearchScreen.this,
							TimeWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				}else if(v.getId() == filed.fTVPhilosophy.getId()){
					Intent intent = new Intent(SearchScreen.this,
							PhilosophyWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				}else if(v.getId() == filed.fTVSkillLevel.getId()){
					Intent intent = new Intent(SearchScreen.this,
							GenderWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				}
			}
		};
		advancedView.getFiled().fTVDay.setOnClickListener(clickListener);
		advancedView.getFiled().fTVPhilosophy.setOnClickListener(clickListener);
		advancedView.getFiled().fTVSkillLevel.setOnClickListener(clickListener);
		advancedView.getFiled().fTVSport.setOnClickListener(clickListener);
		advancedView.getFiled().fTVTime.setOnClickListener(clickListener);
	}

	private void configSimpleSearchView() {
		simpleViewView = new SearchSimpleView(this, handler);
		Filed filed = simpleViewView.getFiled();
		OnClickListener onClick = new OnClickListener() {
			public void onClick(View v) {

				Filed filed = simpleViewView.getFiled();
				if (v.getId() == filed.fTVDay.getId()) {
					Intent intent = new Intent(SearchScreen.this,
							DayWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				} else if (v.getId() == filed.fTVSport.getId()) {
					Intent intent = new Intent(SearchScreen.this,
							SportWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				} else if (v.getId() == filed.fTVTime.getId()) {
					Intent intent = new Intent(SearchScreen.this,
							TimeWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				}
			}
		};
		filed.fTVDay.setOnClickListener(onClick);
		filed.fTVSport.setOnClickListener(onClick);
		filed.fTVTime.setOnClickListener(onClick);
	}

	protected void addView(View view) {
		super.addView(view);

		changleBackground(true);
		commonImage.setBackgroundResource(R.drawable.tranper);
		commonImage.setVisibility(View.GONE);
		View view2 = llContent.getChildAt(0);
		if (view2 == simpleViewView) {
			changleBackground(false);
			isSimpleSearch = true;
			getButton(R.id.commonbtnBack).setVisibility(View.GONE);
			getTextView(R.id.commonETHearder).setText(Common.SEARCH_SIMPLE);
		} else {
			getButton(R.id.commonbtnBack).setVisibility(View.VISIBLE);
			if (view2 == advancedView) {
				isSimpleSearch = false;
				changleBackground(false);
				getTextView(R.id.commonETHearder).setText(
						Common.SEARCH_ADVANCED);
			} else if (view2 == resultView) {
				getTextView(R.id.commonETHearder).setText(Common.SEARCH_RESULT);
			} else if (view2 == resultItemView) {
				getTextView(R.id.commonETHearder).setText(
						Common.SEARCH_RESULT_ITEM);
			}else if(view2 == buddyListInlineView){
				commonETHearder.setText("Cobnounr");
				commonImage.setVisibility(View.VISIBLE);
				commonImage.setBackgroundResource(R.drawable.icon1);
			}
		}
	}

	public void onClick(View v) {
		onBack();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void onBack() {
		View view2 = llContent.getChildAt(0);

		if (addView(view2, buddyListInlineView, resultItemView)) {
			return;
		}
		if (!addView(view2, resultItemView, resultView)) {
			if (!addView(view2, advancedView, simpleViewView)) {
				if (!addView(view2, resultView, isSimpleSearch ? simpleViewView
						: advancedView)) {
					if (addView(view2, simpleViewView, null)) {
						finish();
					}
				}
			}
		}
	}
}
