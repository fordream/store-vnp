package com.cnc.buddyup;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cnc.buddyup._interface.IActivityView;
import com.cnc.buddyup.common.views.LoadingView;

public class Activity extends android.app.Activity implements IActivityView {
	protected LoadingView loadingView;

	public CheckBox getCheckBox(int res) {
		return (CheckBox) findViewById(res);
	}

	public EditText getEditText(int res) {
		return (EditText) findViewById(res);
	}

	public TextView getTextView(int res) {
		return (TextView) findViewById(res);
	}

	public Button getButton(int res) {
		return (Button) findViewById(res);
	}

	public ImageButton getImageButton(int res) {
		return (ImageButton) findViewById(res);
	}

	public ImageView getImageView(int res) {
		return (ImageView) findViewById(res);
	}

	public ListView getListView(int res) {
		return (ListView) findViewById(res);
	}

	public RadioButton getRadioButton(int res) {
		return (RadioButton) findViewById(res);
	}

	public LinearLayout getLinearLayout(int res) {
		return (LinearLayout) findViewById(res);
	}

	public Spinner getSpinner(int res) {
		return (Spinner) findViewById(res);
	}

	public void hiddenKeyboard(Context context, EditText et) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}

	public String getText(EditText eText) {
		return eText.getText().toString();
	}

	public String getText(TextView eText) {
		return eText.getText().toString();
	}

	public void showDialog(boolean show) {
		if (loadingView != null) {
			loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
		}
	}
}