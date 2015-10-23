package com.cnc.buddyup.views.buddy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cnc.buddyup.BuddiesScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.common.views.SheduleWeekendView;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class BuddyListInlineView extends LinearLayout {
	public class Field {
		public TextView fprofille_sport_item_tVSportName;
		public TextView fprofille_sport_item_spSkillLevel;
		public TextView fprofille_sport_item_spPhilosophy;
		public SheduleWeekendView sheduleWeekendView;
		// public ProfileSportDayItemView[] sportDayItems = new
		// ProfileSportDayItemView[7];
	}

	private Field field = new Field();

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public BuddyListInlineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.byddy_list_inline);
	}

	public BuddyListInlineView(Context context, Handler handler) {
		super(context);
		config(R.layout.byddy_list_inline);
	}

	public void config(final int resLayout) {
		super.config(resLayout);
		field.sheduleWeekendView = (SheduleWeekendView) findViewById(R.id.sheduleWeekendView1);
		findViewById(R.id.commonbtnBack).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						((BuddiesScreen) getContext()).onBack();
					}
				});

		// field.fprofille_sport_item_spPhilosophy
		// =getTextView(R.id.profille_sport_item_spPhilosophy);
		// field.fprofille_sport_item_spSkillLevel
		// =getTextView(R.id.profille_sport_item_spSkillLevel);
		// field.fprofille_sport_item_tVSportName
		// =getTextView(R.id.profille_sport_item_tVSportName);
		//
		// field.sportDayItems[0] = (ProfileSportDayItemView)
		// findViewById(R.id.profile_sport_item__day1);
		// field.sportDayItems[0].setShowHeader();
		// field.sportDayItems[0].setText("Sunday");
		// field.sportDayItems[1] = (ProfileSportDayItemView)
		// findViewById(R.id.profile_sport_item__day2);
		// field.sportDayItems[2] = (ProfileSportDayItemView)
		// findViewById(R.id.profile_sport_item__day3);
		// field.sportDayItems[3] = (ProfileSportDayItemView)
		// findViewById(R.id.profile_sport_item__day4);
		// field.sportDayItems[4] = (ProfileSportDayItemView)
		// findViewById(R.id.profile_sport_item__day5);
		// field.sportDayItems[5] = (ProfileSportDayItemView)
		// findViewById(R.id.profile_sport_item__day6);
		// field.sportDayItems[6] = (ProfileSportDayItemView)
		// findViewById(R.id.profile_sport_item__day7);
		//
		// field.sportDayItems[1].setText("Monday");
		// field.sportDayItems[2].setText("Tuesday");
		// field.sportDayItems[3].setText("Wednesday");
		// field.sportDayItems[4].setText("Thurday");
		// field.sportDayItems[5].setText("Friday");
		// field.sportDayItems[6].setText("Satuday");
		//
		// field.sportDayItems[0].setEnabled();
		// field.sportDayItems[1].setEnabled();
		// field.sportDayItems[2].setEnabled();
		// field.sportDayItems[3].setEnabled();
		// field.sportDayItems[4].setEnabled();
		// field.sportDayItems[5].setEnabled();
		// field.sportDayItems[6].setEnabled();
		//
		// //data xem
		// field.sportDayItems[0].configValue(true,true,false,false,true);
		// field.sportDayItems[0].changType(1, false);
	}

}