package com.cnc.buddyup.buddy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cnc.buddyup.BuddiesScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class GroupDetailView extends LinearLayout {
	public class Field {
		public TextView fETNameGroup;
		public TextView fTVGroupType;
		public TextView fTDate;
		public TextView fTVSkill;
		public EditText fETNumberMembers;
		public EditText fETDistance;
		//public EditText fETMessage;
		public Button fbtnAddmember;
		public Button fbtnWithDraw;
		public Button fbtnDisban;
	}

	private Field field = new Field();

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public GroupDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.groupdetailview);
	}

	public GroupDetailView(Context context) {
		super(context);
		config(R.layout.groupdetailview);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		field.fbtnAddmember = getButton(R.id.Button02);
		field.fbtnDisban= getButton(R.id.button1);
		field.fbtnWithDraw = getButton(R.id.Button01);
		
		field.fETDistance = getEditText(R.id.EditText02);
		field.fETNumberMembers = getEditText(R.id.EditText03);
		field.fTDate = getTextView(R.id.TextView1);
		field.fTVGroupType = getTextView(R.id.TextView08);
		field.fTVSkill = getTextView(R.id.TextView3);
		field.fETNameGroup = getTextView(R.id.TextViewname);
		findViewById(R.id.commonbtnBack).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((BuddiesScreen)getContext()).onBack();
			}
		});
		
		field.fbtnAddmember.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((BuddiesScreen)getContext()).addAddMember();
				
			}
		});
		
	}
}