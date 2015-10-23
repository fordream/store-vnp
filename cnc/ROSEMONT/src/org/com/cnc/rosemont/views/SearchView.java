package org.com.cnc.rosemont.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.android.CommonView;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.SearchActivity;
import org.com.cnc.rosemont.activity.SortBy;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.adapter.SearchAdapter;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.items.ItemSearch;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SearchView extends LinearLayout implements OnItemClickListener,
		OnFocusChangeListener, IView {
	private SearchHeaderView headerView;
	private SearchAdapter adapter;

	public SearchView(Context context) {
		super(context);
		config(R.layout.search);
	}

	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.search);
	}

	private void config(int resource_layouy) {
		   /*InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.toggleSoftInput(InputMethodManager.RESULT_SHOWN, 0);*/
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		headerView = (SearchHeaderView) findViewById(R.id.searchHeaderView1);
		headerView.setOnFocusChangeListener(this);
		headerView.addTextChangedListener(textWatcher);

		ListView listView = (ListView) findViewById(R.id.listView1);
		adapter = new SearchAdapter(getContext(), listView,
				new ArrayList<ItemSearch>());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	private List<ItemSearch> addDataExample(String strSearch) {
		return SortBy.sortAndAddHeader(CommonApp.ROSEMONT.search(strSearch,
				false));
	}

	private TextWatcher textWatcher = new TextWatcher() {
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			headerView.updateBackground();
			String txtSearch = headerView.getText();
			adapter.setData(addDataExample(txtSearch));
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void afterTextChanged(Editable s) {

		}
	};

	private boolean isTabSearch() {
		return getContext() instanceof SearchActivity;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		CommonView.hiddenKeyBoard((Activity) getContext());
		ItemSearch item = (ItemSearch) arg0.getItemAtPosition(position);
		if (!item.isAnphabe() && isTabSearch()) {
			String strength = item.getStrength();
			DataStore.getInstance().setConfig(Conts.STRENGTH, strength);
			DataStore.getInstance().setConfig(Conts.PRODUCTNAME,item.getTxtHeader());
			((IActivity) getContext()).gotoProductDetail(item.getId(),
					item.getIdTable());

		}
	}

	public void setStrSearch(String text) {
		headerView.setTextSearch(text);
		adapter.setData(addDataExample(text));
	}

	public String getTextSearch() {
		return headerView.getText();
	}

	public EditText getEditText() {
		return headerView.getEdiText();
	}

	public void onFocusChange(View v, boolean hasFocus) {
		Log.e("Searc", "focuschange: " + v.toString());
		if ((v == getEditText()) && (hasFocus)) {
			InputMethodManager imm = (InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.RESULT_SHOWN, 0);
		}
	}

	public void reset() {
		// adapter.setData();
	//	adapter.notifyDataSetChanged();
	}

	public void setFocusable() {
		// tim ham set focus
		headerView.setFocus1();
		
	}

	public void clear() {
		headerView.setTextSearch("");
	}

}
