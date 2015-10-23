package org.com.cnc.rosemont.activity;

import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.asyn.AsynGetDataSearch;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.views.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class SearchActivity extends Activity {
	boolean isSecond = false;
	boolean isThread = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataStore.getInstance().setConfig(Conts.POST, false);
		DataStore.getInstance().setConfig(Conts.FIRST, true);
		gotoSearch();
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean isCalculator = DataStore.getInstance().getConfig(
				Conts.ISCALCULATOR, false);
		;
		int tabId = DataStore.getInstance().getConfig(Conts.TAB_ID, 0);
		DataStore.getInstance().setConfig(Conts.TAB_ID, 1);
		DataStore.getInstance().setConfig(Conts.ISCALCULATOR, false);
		if (DataStore.getInstance().getConfig(Conts.FIRST, false)) {
			DataStore.getInstance().setConfig(Conts.FIRST, false);
			return;
		}


		

		if (isSecond) {
			isSecond = false;
			return;
		}


		if (DataStore.getInstance().getConfig(Conts.POST, false)) {
			DataStore.getInstance().setConfig(Conts.POST, false);
			if (lViews.size() == 1) {
				SearchView searchView = (SearchView) lViews.get(0);
				searchView.setFocusable();
			}

			return;
		}


		if (DataStore.getInstance().getConfig(Conts.BACKFROMCALCULATOR, false)) {
			DataStore.getInstance().setConfig(Conts.BACKFROMCALCULATOR, false);
			
			if(lViews.size() == 1){
				showKeyBoard();
			}
			return;
		}


		if (tabId != 1 || isCalculator) {
			if (!DataStore.getInstance().getConfig(Conts.BACKFROMCALCULATOR,
					false)) {
				SearchView searchView = (SearchView) lViews.get(0);
				lViews.clear();
				lViews.add(searchView);
				setContentView(searchView);
				searchView.clear();
				//searchView.setFocusable();
				showKeyBoard();
				return;
			}

		}

	}

	@Override
	protected void onStart() {
		isThread = true;
		super.onStart();
		if (DataStore.getInstance().getConfig(Conts.RE_SEARCH, false)) {
			new AsynGetDataSearch(this).execute("");
			DataStore.getInstance().setConfig(Conts.RE_SEARCH, false);
		}

	}

	@Override
	protected void onPause() {

		if (lViews.size() == 1) {
			InputMethodManager imm = (InputMethodManager) getParent()
					.getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getParent().getCurrentFocus()
					.getWindowToken(), 0);
		}

		super.onPause();
	}

	@Override
	protected void onStop() {
		isSecond = true;
		DataStore.getInstance().setConfig(Conts.SEARCH_RELOAD, true);
		super.onStop();

	}

	public void onBack1() {
		super.onBack1();
		if (sizeOfView() == 1) {
			if (getLViews().get(0) instanceof SearchView) {
				String search = ((SearchView) getLViews().get(0))
						.getTextSearch();
				clearActivity();
				gotoSearch(search);
			}
		}
	}

}