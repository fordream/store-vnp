package org.com.cnc.common.android.views1;

import org.com.cnc.common.android.CommonView;
import org.com.cnc.common.android._interface.ISearchView;
import org.com.vnp.storeapp.R;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchView extends RelativeLayout implements OnClickListener,
		TextWatcher, OnEditorActionListener {
	private EditText etSearch;
	private ButtonX buttonX;
	private ButtonSearch buttonSearch;

	public SearchView(Context context) {
		super(context);
		init();
	}

	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SearchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(service);
		li.inflate(R.layout.search, this);

		buttonSearch = (ButtonSearch) findViewById(R.id.buttonSearch1);
		buttonX = (ButtonX) findViewById(R.id.buttonX1);
		buttonX.setVisibility(GONE);

		etSearch = (EditText) findViewById(R.id.editText1);

		buttonSearch.setOnClickListener(this);
		buttonX.setOnClickListener(this);
		etSearch.addTextChangedListener(this);

		etSearch.setOnEditorActionListener(this);
	}

	public void onClick(View v) {
		if (v == buttonX) {
			etSearch.setText(R.string.blank);
		} else if (v == buttonSearch) {

			try {
				CommonView.hiddenKeyBoard((Activity) getContext());
			} catch (Exception e) {
			}

			String text = etSearch.getText().toString();
			((ISearchView) getContext()).onClickButtonSearchView(text);

		}
	}

	public void showKeyBoard() {
		CommonView.showKeyBoard((Activity) getContext(), etSearch);
	}

	// text changle
	public void afterTextChanged(Editable arg0) {

	}

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if (arg0.toString().equals("")) {
			buttonX.setVisibility(GONE);
		} else {
			buttonX.setVisibility(VISIBLE);
		}

		((ISearchView) getContext()).onSearchViewBeforeTextChanged(arg0
				.toString());
	}

	// check action done
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			((ISearchView) getContext()).onSearchViewKeySearch(v.getText()
					.toString());
		}
		return false;
	}

}
