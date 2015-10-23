package org.com.cnc.rosemont.activity;

import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.asyn.AsynGetDataCaculator;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.views.CalculatorView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class CalculatorActivity extends Activity {
	boolean isFirst = true;
	public static final String MYPREFS = "mySharedPreferences";
	public static boolean ok = false;
	boolean isSecond = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//gotoCalculator(true);
		//CommonApp.isShowFirst = true;
		isSecond = true;
		//update();



	}

	private void update() {
		if (CommonApp.DATA.id != null) {
			if (lViews.size() == 1) {
				View view = lViews.get(lViews.size() - 1);
				if (view instanceof CalculatorView) {
					((CalculatorView) view).updateData(CommonApp.DATA.id,
							CommonApp.DATA.id_row);
					((CalculatorView) view).updateStrength();

				}
			}
		}

		CommonApp.DATA.reset();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (lViews.size() == 1) {
			Handler handler = (Handler) getIntent().getParcelableExtra(
					Common.KEY02);
			Message message = new Message();
			message.what = 0;
			handler.sendMessage(message);

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void gotoProductDetail(String id, String idRow) {
		RosemontTable rosemontTable = new RosemontTable();
		int index = 0;
		for (int i = 0; i < CommonApp.ROSEMONT.sizeOfRow(); i++) {
			List<String> row = CommonApp.ROSEMONT.getRow(i);

			int index_id = CommonApp.ROSEMONT
					.getIndexColumns(RosemontTable.ID_PRODUCT);
			Log.i("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",""+RosemontTable.ID_PRODUCT);
			if (row.get(index_id).equals(id)) {
				rosemontTable.addRow(row);
			}
		}

		for (int i = 0; i < rosemontTable.sizeOfRow(); i++) {
			if (idRow.equals(rosemontTable.getIdTable(i))) {
				index = i;
				break;
			}
		}

		onBack1();

		View view = lViews.get(lViews.size() - 1);
		if (view instanceof CalculatorView) {
			((CalculatorView) view).updateData(rosemontTable, index);
		}
	}

	protected void onResume() {
		ok = false;
		super.onResume();
		DataStore.getInstance().setConfig(Conts.ISCALCULATOR, true);
		if(DataStore.getInstance().getConfig(Conts.MG_STRENG, false)){
			DataStore.getInstance().setConfig(Conts.MG_STRENG, false);
			return;
		}
		
		if (DataStore.getInstance().getConfig(Conts.SEND, false)) {
			DataStore.getInstance().setConfig(Conts.SEND, false);
			clearActivity();
			gotoCalculator(true);
			update();
			
		} else if (!isStop) {
			clearActivity();
			gotoCalculator(true);
		}
		isStop = false;
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (DataStore.getInstance().getConfig(Conts.RE_SEARCH, false)) {
			new AsynGetDataCaculator(this).execute("");
			DataStore.getInstance().setConfig(Conts.RE_SEARCH, false);
		}

	}

	@Override
	protected void onPause() {
		ok = true;
		super.onPause();
		isSecond = false;
	}

	boolean isStop = false;

	@Override
	protected void onStop() {
		super.onStop();
		isStop = true;
	}

	@Override
	public void reset() {
		super.reset();
	}

}