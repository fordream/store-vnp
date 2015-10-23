package org.com.cnc.rosemont.views;

import java.io.File;
import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonNetwork;
import org.com.cnc.common.android.CommonView;
import org.com.cnc.common.android.database.table.CommonTable;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.asyn.SavePDFAsyn;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.views.widgets.ListViewDetail;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ProductDetailView extends LinearLayout implements
		View.OnClickListener, IView {

	private RosemontTable rosemontTable = new RosemontTable();
	private int index = 0;
	private ListViewDetail listView;
	private HeaderDetailView headerView;

	// private AnimationSlide animationSlide=new AnimationSlide();
	public ProductDetailView(Context context) {
		super(context);
		config(R.layout.productdetail2);
	}

	public ProductDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.productdetail2);
	}

	private void config(int resource_layouy) {

		LayoutInflater li;
		String setvice = Context.LAYOUT_INFLATER_SERVICE;
		li = (LayoutInflater) getContext().getSystemService(setvice);
		li.inflate(resource_layouy, this);

		headerView = (HeaderDetailView) findViewById(R.id.headerView1);
		headerView.showButton(true, true);
		headerView.setOnClick(onClickBack, onClickNext);

		listView = (ListViewDetail) findViewById(R.id.listView1);

		headerView.addOnCLick(left, right);
	}

	private OnClickListener left = new OnClickListener() {
		public void onClick(View v) {
			index--;
			if (index < 0) {
				index = 0;
			}

			useData(index);
		}
	};

	private OnClickListener right = new OnClickListener() {
		public void onClick(View v) {
			index++;
			if (index >= rosemontTable.sizeOfRow()) {
				index = rosemontTable.sizeOfRow() - 1;
			}

			useData(index);
		}
	};

	String id = "", idRow = "";

	public void addDataExample(String id, String idRow) {
		this.id = id;
		this.idRow = idRow;

		rosemontTable = new RosemontTable();

		int index = CommonApp.ROSEMONT.getIndex(idRow);
		String name = CommonApp.ROSEMONT.getGenericName(index);
		name = DataStore.getInstance().getConfig(Conts.PRODUCTNAME);

		rosemontTable = CommonApp.ROSEMONT.searchByName(name);
		Log.i("AAAA", "COUNT === " + rosemontTable.sizeOfRow());
		for (int i = 0; i < rosemontTable.sizeOfRow(); i++) {
			if (idRow.equals(rosemontTable.getIdTable(i))) {
				index = i;
				break;
			}
		}

		String strength = DataStore.getInstance().getConfig(Conts.STRENGTH);
		DataStore.getInstance().setConfig(Conts.STRENGTH, null);

		if (!Common.isNullOrBlank(strength)) {
			for (int i = 0; i < rosemontTable.sizeOfRow(); i++) {
				if (strength.equals(rosemontTable.getValue(i,
						RosemontTable.STRENGTH))) {
					index = i;
					break;
				}
			}
		}

		if (rosemontTable.sizeOfRow() == 0) {
			((IActivity) getContext()).onBack1();
		}

		useData(index);
	}

	private void useData(int index) {
		if (rosemontTable.sizeOfRow() == 0) {
			return;
		}

		if (index >= rosemontTable.sizeOfRow()) {
			index = 0;
		}

		headerView.setData(rosemontTable.getStreng(index));
		headerView.setButtonBacground(index, rosemontTable.sizeOfRow());

		listView.update(rosemontTable, index);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btnDosage) {
			((IActivity) getContext()).gotoCalculator(rosemontTable, index,
					true);
		} else if (v.getId() == R.id.btnReportAdverse) {
			((IActivity) getContext()).gotoAdverse();
		} else if (v.getId() == R.id.btnSummary) {
			// Intent intent = new Intent(getContext(),
			// CloseDialogActivity.class);
			// ((Activity) getContext()).startActivityForResult(intent,
			// Common.REQUESTCODE_01);
		} else if (v.getId() == R.id.btnCertificate) {
			String name = rosemontTable.getNameFile(index);
			name = CommonApp.changleName(name);
			String path = CommonApp.PATH + name;
			File file = new File(path);
			if (Common.isNullOrBlank(name)) {
				CommonView.viewDialog(getContext(), "", getResources()
						.getString(string.havent_cer));
			} else if (file.exists()) {
				CommonApp.startPDF(path, getContext());
			} else if (!CommonNetwork.haveConnectTed(getContext())) {
				CommonView.viewDialog(getContext(), "", getResources()
						.getString(string.havent_network));
			} else {
				new SavePDFAsyn(getContext(), name).execute("");
			}
		}
	}

	private OnClickListener onClickBack = new OnClickListener() {
		public void onClick(View v) {
			((IActivity) getContext()).onBack1();
		}
	};

	private OnClickListener onClickNext = new OnClickListener() {
		public void onClick(View v) {
		}
	};

	public void reset() {
		CommonApp.getDataROSEMONT(getContext());
		if (idRow != null && !CommonApp.ROSEMONT.isExits(idRow)) {
			((IActivity) getContext()).onBack1();

			return;

		}
		addDataExample(id, idRow);
	}

}
