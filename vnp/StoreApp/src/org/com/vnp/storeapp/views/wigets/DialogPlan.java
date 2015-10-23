package org.com.vnp.storeapp.views.wigets;

import java.util.ArrayList;
import java.util.List;

import org.com.vnp.storeapp.MyListData;
import org.com.vnp.storeapp.R;
import org.com.vnp.storeapp.database.DBAdapter;
import org.com.vnp.storeapp.database.Plan;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class DialogPlan extends Dialog implements
		android.view.View.OnClickListener {
	private EditText editText1;
	private MyListData myListData;

	private DatePicker datePicker;

	private TimePicker timePicker;

	public DialogPlan(Context arg0) {
		super(arg0, R.style.Theme_Dialog_Translucent);

		setContentView(R.layout.dialogplan);

		myListData = (MyListData) arg0;
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button1).setOnClickListener(this);

		editText1 = (EditText) findViewById(R.id.editText1);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
	}

	public void onClick(View v) {
		if (R.id.button2 == v.getId()) {
			dismiss();
		} else {
			Plan plan = new Plan();
			List<String> list = new ArrayList<String>();
			list.add(editText1.getText().toString());
			String date = datePicker.getYear() + "/" + datePicker.getMonth()
					+ "/" + datePicker.getDayOfMonth();

			date += " ";
			date += timePicker.getCurrentHour() + ":"+timePicker.getCurrentMinute();
			
			 list.add(date);
			 plan.addRow(list);
			//
			 DBAdapter.getInstance().insert(plan);
			 dismiss();
			 myListData.onReload();
		}
	}
}