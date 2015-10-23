package com.cnc.buddyup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.buddyup.activity.views.FollowUpDetailView;
import com.cnc.buddyup.activity.views.ReScheduleView;
import com.cnc.buddyup.activity.views.ScheduleActivityDetailView;
import com.cnc.buddyup.activity.wheel.FlexbilityWheelActivity;
import com.cnc.buddyup.activity.wheel.StarWheelActivity;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.message.views.NewMessageView;
import com.cnc.buddyup.views.activity.ActivityMainView;
import com.cnc.buddyup.views.activity.PendingInviteView;
import com.cnc.buddyup.views.activity.ScheduleActivityView;
import com.cnc.buddyup.views.activity.ScheduleBackpackingView;
import com.cnc.buddyup.views.activity.ScheduleInlineView;
import com.cnc.buddyup.views.common.CommonCountView;
import com.cnc.buddyup.wheel.SkillLevelWheelActivity;

public class ActivityScreen extends NActivity implements OnClickListener {
	private ActivityMainView mainView;
	private CommonCountView pendingActivityView;
	private ScheduleActivityView scheduleActivityView;
	private PendingInviteView inviteView;
	private ScheduleBackpackingView backpackingView;
	private ScheduleInlineView inlineView;
	private ScheduleActivityDetailView scheduleActivityDetailView;
	private ReScheduleView reScheduleView ;
	private NewMessageView newMessageView;
	private CommonCountView followup;
	private FollowUpDetailView followUpDetailView;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Common.MESSAGE_WHAT_COMMON) {
				redirectLevel1(msg);
			}
		}

		private void redirectLevel1(Message msg) {
			CommonItemResquestParcelable parcelable = getCommonItemResquestParcelable(msg);
			if ("id0".equals(parcelable.getId())) {
				addView(pendingActivityView);
			} else if ("id1".equals(parcelable.getId())) {
				addView(scheduleActivityView);
			}else if ("id2".equals(parcelable.getId())){
				addView(followup);
			} 
		}
	};

	private Handler handlerPending = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			addView(inviteView);
		}
	};
	private Handler handlerSchedule = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (getCommonItemResquestParcelable(msg).getId().endsWith("id0")) {
				addView(backpackingView);
				return;
			} else if (getCommonItemResquestParcelable(msg).getId().endsWith(
					"id1")) {
				addView(inlineView);
			} else {
				addView(scheduleActivityDetailView);
			}
		}
	};
	private Handler handlerPendingInviteView = new Handler() {
	};
	private Handler handlerScheduleBackpackingView = new Handler();
	private Handler handlerInline = new Handler();
	private Handler handlerFollowm = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
//			Builder builder = new Builder(ActivityScreen.this);
//			builder.setMessage(getCommonItemResquestParcelable(msg).getId());
//			builder.show();
			addView(followUpDetailView);
		}
		
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getButton(R.id.commonBtnAdd).setVisibility(View.GONE);
		getButton(R.id.commonbtnBack).setVisibility(View.GONE);
		getButton(R.id.commonbtnBack).setOnClickListener(this);
		mainView = new ActivityMainView(this, handler);
		scheduleActivityView = new ScheduleActivityView(this, handlerSchedule);

		pendingActivityView = new CommonCountView(this, handlerPending, true);
		inviteView = new PendingInviteView(this, handlerPendingInviteView);
		backpackingView = new ScheduleBackpackingView(this,
				handlerScheduleBackpackingView);
		backpackingView.getFiled().fFlexibility
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(ActivityScreen.this,
								FlexbilityWheelActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString(Common.ARG0, "");
						intent.putExtras(bundle);

						startActivityForResult(intent, 1000);
						//addView(followUpDetailView);
					}
				});

		inlineView = new ScheduleInlineView(this, handlerInline);

		configScheduleActivityDetail();
		
		configReschedule();
		configSendMessage();
		configFollowUp();
		configFollowUpDetail();
		addView(mainView);
	}

	private void configFollowUpDetail() {
		followUpDetailView = new FollowUpDetailView(this);
		OnClickListener onClick = new OnClickListener() {
			public void onClick(View v) {
				if(v == followUpDetailView.getFiled().fSkillLevel){
					Intent intent = new Intent(ActivityScreen.this, SkillLevelWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				}if(v == followUpDetailView.getFiled().fDepend){
					Intent intent = new Intent(ActivityScreen.this, StarWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				}if(v == followUpDetailView.getFiled().fOverRating){
					Intent intent = new Intent(ActivityScreen.this, StarWheelActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(Common.ARG0, "");
					intent.putExtras(bundle);
					startActivityForResult(intent, 1000);
				}
			}
		};
		
		followUpDetailView.getFiled().fDepend.setOnClickListener(onClick);
		followUpDetailView.getFiled().fOverRating.setOnClickListener(onClick);
		followUpDetailView.getFiled().fSkillLevel.setOnClickListener(onClick);
	}

	private void configFollowUp() {
		followup = new CommonCountView(this, handlerFollowm, false,true);
	}

	private void configSendMessage() {
		newMessageView = new NewMessageView(this);		
	}

	private void configReschedule() {
		reScheduleView = new ReScheduleView(this);
		reScheduleView.getFiled().fTVFlexxibility.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(ActivityScreen.this, FlexbilityWheelActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(Common.ARG0,"");
				intent.putExtras(bundle);
				startActivityForResult(intent, 1000);
			}
		});
	}

	private void configScheduleActivityDetail() {
		scheduleActivityDetailView = new ScheduleActivityDetailView(this);
		scheduleActivityDetailView.getFiled().fReschedule
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// add Reschedule
						addView(reScheduleView);
					}
				});
		scheduleActivityDetailView.getFiled().fsendMessage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addView(newMessageView);
			}
		});
	}

	protected void addView(View view) {
		super.addView(view);
		configHeader(view);
	}

	private void configHeader(View view) {

		// QA
		// changleBackground(addView(view, inlineView, null));
		changleBackground(R.drawable.buddy_main_bg1);
		findViewById(R.id.tvScheduteV).setVisibility(View.GONE);
		if (!addView(view, mainView, null) && view != null) {
			getButton(R.id.commonbtnBack).setVisibility(View.VISIBLE);
		}

		if (addView(view, scheduleActivityDetailView, null)) {
			findViewById(R.id.tvScheduteV).setVisibility(View.VISIBLE);
			//setHeader("Schedule Activity Detail");
			setHeader("");
			// getButton(R.id.commonbtnBack).setVisibility(View.GONE);
			return;
		}
		if (addView(view, followUpDetailView, null)) {
			setHeader("Follow-Up Detail");
			//setHeader("");
			//findViewById(R.id.tvScheduteV).setVisibility(View.VISIBLE);
			changleBackground(R.drawable.profile_bg);
			// getButton(R.id.commonbtnBack).setVisibility(View.GONE);
			return;
		}
		if (addView(view, followup, null)) {
			setHeader("Activity Follow-Up");
			changleBackground(R.drawable.profile_bg);
			// getButton(R.id.commonbtnBack).setVisibility(View.GONE);
			return;
		}
		if (addView(view, newMessageView, null)) {
			setHeader("Send Message");
			// getButton(R.id.commonbtnBack).setVisibility(View.GONE);
			return;
		}
		if (addView(view, reScheduleView, null)) {
			setHeader("Reschedule");
			// getButton(R.id.commonbtnBack).setVisibility(View.GONE);
			return;
		}

		if (addView(view, mainView, null)) {
			setHeader("Activities");
			getButton(R.id.commonbtnBack).setVisibility(View.GONE);
		} else if (addView(view, pendingActivityView, null)) {
			setHeader("Pending Activities");
		} else if (addView(view, scheduleActivityView, null)) {
			setHeader("Schedule Activities");
		} else if (addView(view, inviteView, null)) {
			changleBackground(R.drawable.profile_bg);
			setHeader("Pending invite");
		}
		if (addView(view, backpackingView, null)) {
			changleBackground(R.drawable.profile_bg);
			setHeader("Backpacking");
		}

		if (addView(view, inlineView, null)) {
			// changleBackground(R.drawable.profile_bg);
			setHeader("Inline Skating");
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void onBack() {
		View view2 = llContent.getChildAt(0);
		configHeader(view2);

		if (addView(view2, inlineView, scheduleActivityView)) {
			return;
		}
		
		if (addView(view2, followUpDetailView, followup)) {
			return;
		}
		if (addView(view2, followup, mainView)) {
			return;
		}
		
		
		
		if (addView(view2, reScheduleView, scheduleActivityDetailView)) {
			return;
		}
		
		if (addView(view2, newMessageView, scheduleActivityDetailView)) {
			return;
		}


		if (addView(view2, scheduleActivityDetailView, scheduleActivityView)) {
			return;
		}

		if (!addView(view2, backpackingView, scheduleActivityView)) {
			if (!addView(view2, inviteView, pendingActivityView)) {
				if (!addView(view2, pendingActivityView, mainView)) {
					getButton(R.id.commonbtnBack).setVisibility(View.GONE);
					if (!addView(view2, scheduleActivityView, mainView)) {
						getButton(R.id.commonbtnBack).setVisibility(View.GONE);
						if (addView(view2, mainView, null)) {
							finish();
						}
					}
				}
			}
		}
	}

	public void onClick(View arg0) {
		onBack();
	}
}