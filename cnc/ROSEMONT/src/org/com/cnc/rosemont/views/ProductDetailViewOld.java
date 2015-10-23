package org.com.cnc.rosemont.views;

import java.io.File;
import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonNetwork;
import org.com.cnc.common.android.CommonView;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.CloseDialogActivity;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.adapter.ProductDetailAdapter;
import org.com.cnc.rosemont.asyn.SavePDFAsyn;
import org.com.cnc.rosemont.database.table.RosemontTable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ProductDetailViewOld extends LinearLayout implements
		View.OnClickListener ,IView{
	private int layouts[] = new int[] {
			org.com.cnc.rosemont.R.layout.productdetail,
			org.com.cnc.rosemont.R.layout.productdetail1 };

	private RosemontTable rosemontTable = new RosemontTable();
	private int index = 0;
	private ListView listView;
	private HeaderDetailView headerView;
	private TextView tvNameProductDetail;

	public ProductDetailViewOld(Context context) {
		super(context);
	}

	public ProductDetailViewOld(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void config(int resource_layouy) {
		LayoutInflater li;
		String setvice = Context.LAYOUT_INFLATER_SERVICE;
		li = (LayoutInflater) getContext().getSystemService(setvice);
		li.inflate(resource_layouy, this);
		headerView = (HeaderDetailView) findViewById(R.id.headerView1);
		headerView.showButton(true, true);
		headerView.setOnClick(onClickBack, onClickNext);
		findViewById(R.id.btnDosage).setOnClickListener(this);
		findViewById(R.id.btnReportAdverse).setOnClickListener(this);
		findViewById(R.id.btnSummary).setOnClickListener(this);
		try {
			findViewById(R.id.btnCertificate).setOnClickListener(this);
		} catch (Exception e) {
		}
		listView = (ListView) findViewById(R.id.listView1);

		headerView.addOnCLick(left, right);
		tvNameProductDetail = (TextView) findViewById(R.id.tvNameProductDetail);
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

	public void addDataExample(String id, String idRow) {
		for (int i = 0; i < CommonApp.ROSEMONT.sizeOfRow(); i++) {
			List<String> row = CommonApp.ROSEMONT.getRow(i);

			int index_id = CommonApp.ROSEMONT
					.getIndexColumns(RosemontTable.ID_PRODUCT);

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

		useData(index);
	}

	private void useData(int index) {

		if (rosemontTable.sizeOfRow() == 0) {
			return;
		}

		if (index >= rosemontTable.sizeOfRow()) {
			index = 0;
		}
		String product_type = rosemontTable.getValue(index,
				RosemontTable.PRODUCT_TYPE);
		// CommonView.viewDialog(getContext(), "", product_type + "");
		if ("0".equals(product_type)) {
			config(layouts[0]);
		} else {
			config(layouts[1]);
		}

		ProductDetailAdapter adapter = new ProductDetailAdapter(getContext(),
				listView, rosemontTable.getDetailList(index));
		listView.setAdapter(adapter);
		headerView.setData(rosemontTable.getStreng(index));
		headerView.setButtonBacground(index, rosemontTable.sizeOfRow());
		tvNameProductDetail.setText(rosemontTable.getGenericName(index));
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btnDosage) {
			((IActivity) getContext()).gotoCalculator(rosemontTable, index,
					true);
		} else if (v.getId() == R.id.btnReportAdverse) {
			((IActivity) getContext()).gotoAdverse();
		} else if (v.getId() == R.id.btnSummary) {
			Intent intent = new Intent(getContext(), CloseDialogActivity.class);
			((Activity) getContext()).startActivityForResult(intent,
					Common.REQUESTCODE_01);
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
		// TODO Auto-generated method stub
		
	}

}
