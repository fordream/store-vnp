package com.cnc.buddyup.buddy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cnc.buddyup.BuddiesScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class CreateGroupView extends LinearLayout {
	public class Field {
		public EditText fETNameGroup;
		public TextView fTVGroupType;
		public TextView fTVSport;
		public TextView fTVSkill;
		public EditText fETMembers;
		public EditText fETDistance;
		public EditText fETMessage;
	}

	private Field field = new Field();

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public CreateGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.creategroupview);
	}

	public CreateGroupView(Context context) {
		super(context);
		config(R.layout.creategroupview);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		field.fETDistance = getEditText(R.id.EditText02);
		field.fETMembers = getEditText(R.id.EditText03);
		field.fETMessage = getEditText(R.id.EditText01);
		field.fETNameGroup = getEditText(R.id.EditText07);
		field.fTVGroupType = getTextView(R.id.TextView1);
		field.fTVSkill = getTextView(R.id.TextView3);
		field.fTVSport = getTextView(R.id.TextView2);

		findViewById(R.id.commonbtnBack).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						((BuddiesScreen) getContext()).onBack();
					}
				});
	}
}