package com.cnc.buddyup.views.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnc.buddyup.Activity;
import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.response.item.ItemWeekend;
import com.cnc.buddyup.wheel.PhilosophyWheelActivity;
import com.cnc.buddyup.wheel.SkillLevelWheelActivity;
import com.cnc.buddyup.wheel.SportNameWheelActivity;

public class ProfileSportItemView extends LinearLayout {
	private Context context;
	private int index = 0;

	private String idSport = "", idSkillevel = "", idPhiloshophy = "";

	public void setInDex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public class Field {
		public TextView fprofille_sport_item_tVSportName;
		public TextView fprofille_sport_item_spSkillLevel;
		public TextView fprofille_sport_item_spPhilosophy;
		public ProfileSportDayItemView[] sportDayItems = new ProfileSportDayItemView[7];
	}

	private Field field = new Field();

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public ProfileSportItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		config(R.layout.profile_sport_item);
	}

	public ProfileSportItemView(Context context) {
		super(context);
		this.context = context;
		config(R.layout.profile_sport_item);
	}

	public void config(int resLayout) {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Activity.LAYOUT_INFLATER_SERVICE);
		li.inflate(resLayout, this);

		field.fprofille_sport_item_spPhilosophy = (TextView) findViewById(R.id.profille_sport_item_spPhilosophy);
		field.fprofille_sport_item_spSkillLevel = (TextView) findViewById(R.id.profille_sport_item_spSkillLevel);
		field.fprofille_sport_item_tVSportName = (TextView) findViewById(R.id.profille_sport_item_tVSportName);

		field.fprofille_sport_item_spSkillLevel
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(context,
								SkillLevelWheelActivity.class);
						Bundle data = new Bundle();
						data.putString(Common.ARG0, idSkillevel + "");
						data.putString(Common.ARG1, index + "");
						intent.putExtras(data);
						((android.app.Activity) context)
								.startActivityForResult(intent,
										Common.REQUEST_CODE_3);
					}
				});
		field.fprofille_sport_item_tVSportName
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(context,
								SportNameWheelActivity.class);
						Bundle data = new Bundle();
						data.putString(Common.ARG0, idSport + "");
						data.putString(Common.ARG1, index + "");
						intent.putExtras(data);
						((android.app.Activity) context)
								.startActivityForResult(intent,
										Common.REQUEST_CODE_4);
					}
				});
		field.fprofille_sport_item_spPhilosophy
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(context,
								PhilosophyWheelActivity.class);
						Bundle data = new Bundle();
						data.putString(Common.ARG0, idPhiloshophy + "");

						data.putString(Common.ARG1, index + "");
						intent.putExtras(data);
						((android.app.Activity) context)
								.startActivityForResult(intent,
										Common.REQUEST_CODE_5);
					}
				});

		field.sportDayItems[0] = (ProfileSportDayItemView) findViewById(R.id.profile_sport_item__day1);
		field.sportDayItems[0].setShowHeader();
		field.sportDayItems[0].setText("Sunday");
		field.sportDayItems[1] = (ProfileSportDayItemView) findViewById(R.id.profile_sport_item__day2);
		field.sportDayItems[2] = (ProfileSportDayItemView) findViewById(R.id.profile_sport_item__day3);
		field.sportDayItems[3] = (ProfileSportDayItemView) findViewById(R.id.profile_sport_item__day4);
		field.sportDayItems[4] = (ProfileSportDayItemView) findViewById(R.id.profile_sport_item__day5);
		field.sportDayItems[5] = (ProfileSportDayItemView) findViewById(R.id.profile_sport_item__day6);
		field.sportDayItems[6] = (ProfileSportDayItemView) findViewById(R.id.profile_sport_item__day7);

		field.sportDayItems[1].setText("Monday");
		field.sportDayItems[2].setText("Tuesday");
		field.sportDayItems[3].setText("Wednesday");
		field.sportDayItems[4].setText("Thurday");
		field.sportDayItems[5].setText("Friday");
		field.sportDayItems[6].setText("Satuday");

	}

	public void updateSkillLevel(String id, String name) {
		idSkillevel = id;
		sport1.setSkilllevel(id);
		field.fprofille_sport_item_spSkillLevel.setText(name);
	}

	public void updateSportName(String id, String name) {
		idSport = id;
		sport1.setIdSport(id);
		sport1.setName(name);
		field.fprofille_sport_item_tVSportName.setText(name);

	}

	public void updatePhiloshophy(String id, String name) {
		idPhiloshophy = id;
		sport1.setPhilosophy(id);
		field.fprofille_sport_item_spPhilosophy.setText(name);
	}

	private ItemWeekend sport1 = new ItemWeekend();

	public void update(ItemWeekend sport1) {
		this.sport1 = sport1;
		idSkillevel = this.sport1.getSkilllevel();
		idSport = this.sport1.getIdSport();
		idPhiloshophy = this.sport1.getPhilosophy();

		field.fprofille_sport_item_spPhilosophy.setText(this.sport1
				.getPhilosophy());
		field.fprofille_sport_item_tVSportName.setText(this.sport1.getName());
		field.fprofille_sport_item_spSkillLevel.setText(this.sport1
				.getSkilllevel());

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
				boolean b = "true".equals(sport1.getSchedule(i, j));
				field.sportDayItems[i].setChecked(j, b);
			}

		}
	}
}