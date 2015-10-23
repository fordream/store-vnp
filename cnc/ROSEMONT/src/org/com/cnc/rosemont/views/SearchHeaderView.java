package org.com.cnc.rosemont.views;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonView;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.Activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchHeaderView extends LinearLayout implements OnClickListener,
		OnTouchListener, OnFocusChangeListener, IView {
	private View viewbacground;
	public static EditText etSearch;
	private View viewClear;

	// private AnimationSlide animationSlide=new AnimationSlide();

	public SearchHeaderView(Context context) {
		super(context);
		config(R.layout.searchheader1);
	}

	public SearchHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.searchheader1);
	}

	private void config(int resource_layouy) {
		// setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);

		viewClear = findViewById(R.id.llSearchController);
		viewClear.setOnClickListener(this);

		viewbacground = findViewById(R.id.llBackground);
		etSearch = (EditText) findViewById(R.id.etSearch);
		etSearch.addTextChangedListener(watcher);
		etSearch.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

			}
		});
		updateBackground();

	}

	private TextWatcher watcher = new TextWatcher() {
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			updateBackground();
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		public void afterTextChanged(Editable s) {

		}
	};

	public void onClick(View v) {
		clear();
	}

	private void clear() {
		etSearch.setText("");
		updateBackground();
	}

	public void updateBackground() {
		if (etSearch.getText().toString().equals("")) {
			viewbacground.setBackgroundResource(R.drawable.tab_search_none);
		} else {
			viewbacground.setBackgroundResource(R.drawable.tab_search);
		}
	}

	public void addTextChangedListener(TextWatcher watcher) {
		etSearch.addTextChangedListener(watcher);
	}

	public String getText() {
		return etSearch.getText().toString();
	}

	public void setTextSearch(String text) {
		etSearch.setText(text);
	}

	public void setFocus() {
		etSearch.setSelection(0);
	}

	public void setFocus1() {
		etSearch.requestFocus();
		String service = Context.INPUT_METHOD_SERVICE;
		InputMethodManager imm = null;
		imm = (InputMethodManager) getContext().getSystemService(service);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//		
		
		
	}

	public EditText getEdiText() {
		return etSearch;
	}

	public boolean onTouch(View arg0, MotionEvent arg1) {
		etSearch.requestFocusFromTouch();
		etSearch.requestFocus();

		return true;
	}

	public void onFocusChange(View v, boolean hasFocus) {
		Log.e("SearchHeaderView", "focuschange: " + v.toString());
		if ((v == etSearch) && (hasFocus)) {
			InputMethodManager imm = (InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.RESULT_SHOWN, 0);
		}
	}

	public void reset() {
		// TODO Auto-generated method stub

	}
}
