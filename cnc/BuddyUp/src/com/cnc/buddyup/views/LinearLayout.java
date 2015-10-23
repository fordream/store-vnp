package com.cnc.buddyup.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cnc.buddyup.Activity;
import com.cnc.buddyup._interface.IActivityView;

public class LinearLayout extends android.widget.LinearLayout implements
		IActivityView {

	protected Handler handler;

	public LinearLayout(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs);
		this.handler = handler;
	}

	public LinearLayout(Context context, Handler handler) {
		super(context);
		this.handler = handler;
	}

	public LinearLayout(Context context, AttributeSet attrs) {
		super(context);
	}

	public LinearLayout(Context context) {
		super(context);
	}

	public void config(int resLayout, ViewGroup v) {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Activity.LAYOUT_INFLATER_SERVICE);
		li.inflate(resLayout, v);
	}

	public void config(int resLayout) {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Activity.LAYOUT_INFLATER_SERVICE);
		li.inflate(resLayout, this);
	}

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

	public android.widget.LinearLayout getLinearLayout(int res) {
		return (android.widget.LinearLayout) findViewById(res);
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
		
	}

}