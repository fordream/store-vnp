package org.com.cnc.rosemont.activity;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonView;
import org.com.cnc.common.android.activity.CommonActivity;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.listen.HiddenKeyboard;
import org.com.cnc.rosemont.views.AboutView;
import org.com.cnc.rosemont.views.AdverseView;
import org.com.cnc.rosemont.views.AnimationSlide;
import org.com.cnc.rosemont.views.CalculatorView;
import org.com.cnc.rosemont.views.CategoryListView;
import org.com.cnc.rosemont.views.Category_Indication_Detail;
import org.com.cnc.rosemont.views.CrushingConsumerView;
import org.com.cnc.rosemont.views.CrushingMedicinesView;
import org.com.cnc.rosemont.views.CrushingTheEqualityView;
import org.com.cnc.rosemont.views.CrushingTheHumanView;
import org.com.cnc.rosemont.views.HomeView;
import org.com.cnc.rosemont.views.IndicationListView;
import org.com.cnc.rosemont.views.IntroView;
import org.com.cnc.rosemont.views.ManualView;
import org.com.cnc.rosemont.views.ProductByView;
import org.com.cnc.rosemont.views.ProductDetailView;
import org.com.cnc.rosemont.views.ProductListCalculatorView;
import org.com.cnc.rosemont.views.ProductListView;
import org.com.cnc.rosemont.views.SearchView;
import org.com.cnc.rosemont.views.SumaryView;
import org.com.cnc.rosemont.views.ViewOnlinePDF;
import org.com.cnc.rosemont.views.widgets.DialogOkNo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Activity extends CommonActivity implements IActivity {
	public List<View> lViews = new ArrayList<View>();

	public void gotoAdverse() {
		View view = new AdverseView(this);
		setContentView(view, true);
	}

	private void setContentView(View view, boolean isAdd) {
		if (isAdd) {
			lViews.add(view);
		}
		view.setFocusable(true);
		view.setOnClickListener(new HiddenKeyboard(this));
		setContentView(view);
	}

	public void gotoProductList() {
		View view = new ProductListView(this);
		setContentView(view, true);
	}

	public void gotoCalculator(RosemontTable rosemontTable, int index,
			boolean isShowBantBack) {
		CalculatorView view = new CalculatorView(this);
		view.showButton(isShowBantBack, false);
		((CalculatorView) view).updateData(rosemontTable, index);
		setContentView(view, true);

	}

	public void gotoCalculator(boolean isShowBack) {
		CalculatorView view = new CalculatorView(this);
		view.showButton(isShowBack, false);
		setContentView(view, true);
	}

	public void gotoCatogoryList() {
		View view = new CategoryListView(this);
		setContentView(view, true);
	}

	public void gotoIndicationList() {
		View view = new IndicationListView(this);
		setContentView(view, true);
	}

	public void gotoProductDetail(String id, String idRow) {
		View view = new ProductDetailView(this);
		((ProductDetailView) view).addDataExample(id, idRow);
		setContentView(view, true);
	}

	public void gotoProductListBy(boolean isCategory, String data) {
		View view = new ProductByView(this, isCategory);
		((ProductByView) view).addExampleData(data);
		setContentView(view, true);
	}

	public void gotoHome() {
		View view = new HomeView(this);
		setContentView(view, true);
	}

	public void gotoSearch() {
		SearchView view = new SearchView(this);
		// view.setFocusable();
		setContentView(view, true);
		showKeyBoard();
	}

	public void gotoCategory_Indication_Detail(int type, String data) {
		Category_Indication_Detail view = new Category_Indication_Detail(this);
		view.setData(type, data);
		setContentView(view, true);
	}

	public void gotoProductListCalculator(RosemontTable rosemontTable) {

		ProductListCalculatorView view = new ProductListCalculatorView(this,
				CommonApp.ROSEMONT);
		setContentView(view, true);
	}

	public void onBack1() {
		onBack();
	}

	/*
	 * public boolean onBack() { if (lViews.size() > 1) {
	 * lViews.remove(lViews.size() - 1); setContentView(lViews.get(lViews.size()
	 * - 1));
	 * 
	 * return true; }
	 * 
	 * return false; }
	 */

	public boolean onBack() {
		if (lViews.size() > 1) {
			lViews.remove(lViews.size() - 1);
			((IView) lViews.get(lViews.size() - 1)).reset();
			setContentView(lViews.get(lViews.size() - 1));

			return true;
		}

		return false;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == Common.REQUESTCODE_01) {
			finish();
		} else if (Common.REQUESTCODE_02 == requestCode
				&& resultCode == RESULT_OK) {
			int index = data.getIntExtra(Common.KEY01, -1);
			((CalculatorView) lViews.get(lViews.size() - 1)).setIndex(index);
		} else if (Common.REQUESTCODE_03 == requestCode
				&& resultCode == RESULT_OK) {
			String text = data.getStringExtra(Common.KEY01);
			((CalculatorView) lViews.get(lViews.size() - 1)).setMg(text);
		} else if (resultCode == RESULT_CANCELED
				&& requestCode == CommonApp.REQUESTCODE_FINISH) {
			finish();
		} else if (resultCode == RESULT_OK
				&& requestCode == CommonApp.REQUESTCODE_FINISH) {
			if (lViews.size() > 1
					&& lViews.get(lViews.size() - 1) instanceof IView) {
				reset();

				if (lViews.get(lViews.size() - 1) instanceof SearchView) {
					((SearchView) lViews.get(lViews.size() - 1)).setFocusable();
				}
			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && onBack()) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void sendMessageToSearch() {

	}

	public void clearActivity() {
		lViews.clear();

	}

	public int sizeOfView() {
		return lViews.size();
	}

	public void gotoSearch(String text) {
		SearchView view = new SearchView(this);
		view.setStrSearch(text);
		setContentView(view, true);
	}

	public void gotoSumary(String url) {
		SumaryView view = new SumaryView(this);
		setContentView(view, true);
	}

	public List<View> getLViews() {
		return lViews;
	}

	public void callWeb(final String url, int res_message) {

		DialogOkNo dialog = new DialogOkNo(this, res_message);
		dialog.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				if (dialog instanceof DialogOkNo) {
					if (((DialogOkNo) dialog).isOk()) {
						callWeb(url);
					}
				}
			}
		});
		dialog.show();
	}

	private void callWeb(String url) {
		CommonView.callWeb(this, url);
		finish();
	}

	public void updateCalculator(String id, String idTable, String strength) {
		onBack1();
		View view = lViews.get(lViews.size() - 1);

		if (view instanceof CalculatorView) {
			((CalculatorView) view).updateData(id, idTable, strength);
		}
	}

	public void gotoAbout() {
		View view = new AboutView(this);
		setContentView(view, true);
	}

	public void gotoIntro() {
		View view = new IntroView(this);
		setContentView(view, true);

	}

	public void gotoManual() {
		View view = new ManualView(this);
		setContentView(view, true);

	}

	public void gotoCrushingMedicines() {
		View view = new CrushingMedicinesView(this);
		setContentView(view, true);

	}

	public void gotoCrushingConsumer() {
		View view = new CrushingConsumerView(this);
		setContentView(view, true);
	}

	public void gotoCrushingTheHuman() {
		View view = new CrushingTheHumanView(this);
		setContentView(view, true);
	}

	public void gotoCrushingTheEquality() {
		View view = new CrushingTheEqualityView(this);
		setContentView(view, true);
	}

	public void gotoViewOnlinePDF(String pdfName) {
		View view = new ViewOnlinePDF(this, pdfName);
		setContentView(view, true);
	}

	public void reset() {
		if (lViews.size() > 0) {
			((IView) lViews.get(lViews.size() - 1)).reset();
			if (lViews.get(lViews.size() - 1) instanceof SearchView) {
				if (DataStore.getInstance().getConfig(Conts.SEARCH_RELOAD,
						false)) {
					DataStore.getInstance().setConfig(Conts.SEARCH_RELOAD,
							false);
					((SearchView) lViews.get(lViews.size() - 1)).setFocusable();
				}

			}

		}
	}

	public void showKeyBoard() {
		String service = Context.INPUT_METHOD_SERVICE;
		InputMethodManager imm = null;
		imm = (InputMethodManager) getSystemService(service);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
}