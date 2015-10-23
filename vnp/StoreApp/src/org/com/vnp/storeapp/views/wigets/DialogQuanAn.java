package org.com.vnp.storeapp.views.wigets;

import java.util.ArrayList;
import java.util.List;

import org.com.vnp.storeapp.MyListData;
import org.com.vnp.storeapp.R;
import org.com.vnp.storeapp.database.DBAdapter;
import org.com.vnp.storeapp.database.QuanAn;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

public class DialogQuanAn extends Dialog implements
		android.view.View.OnClickListener {
	private EditText editText1, editText2;
	private MyListData myListData;

	public DialogQuanAn(Context arg0) {
		super(arg0, R.style.Theme_Dialog_Translucent);

		setContentView(R.layout.dialogquanan);

		myListData = (MyListData) arg0;
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button1).setOnClickListener(this);

		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
	}

	public void onClick(View v) {
		if (R.id.button2 == v.getId()) {
			dismiss();
		} else {
			QuanAn quanAn = new QuanAn();
			List<String> list = new ArrayList<String>();
			list.add(editText1.getText().toString());
			list.add(editText2.getText().toString());
			quanAn.addRow(list);

			DBAdapter.getInstance().insert(quanAn);
			dismiss();
			myListData.onReload();
		}
	}
}