package org.com.cnc.rosemont.views.widgets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonNetwork;
import org.com.cnc.common.android.CommonView;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.drawable;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.adapter.DetailAdapter;
import org.com.cnc.rosemont.asyn.SavePDFAsyn;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.items.ItemSearch;
import org.com.cnc.rosemont.views.Conts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewDetail extends ListView implements
		android.widget.AdapterView.OnItemClickListener {
	// private AnimationSlide animationSlide = new AnimationSlide();
	private RosemontTable rosemontTable = new RosemontTable();
	int index = -1;
	DetailAdapter adapter;
	private Header header;

	public ListViewDetail(Context context) {

		super(context);
		// setAnimation(animationSlide.inFromRightAnimation());
		config();
	}

	public ListViewDetail(Context context, AttributeSet attrs) {
		super(context, attrs);
		// setAnimation(animationSlide.inFromRightAnimation());
		config();
	}

	public ListViewDetail(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		config();
	}

	private void config() {

		addHeaderView((header = new Header(getContext())));

		adapter = new DetailAdapter(getContext(), this,
				new ArrayList<ItemSearch>());
		setAdapter(adapter);
		setOnItemClickListener(this);
	}

	private class Header extends LinearLayout implements OnClickListener {

		private TextView tvNameProductDetail;

		private TextView tvActionMode;

		private View btn1;
		private View btn2;
		private View btn3;
		private LinearLayout layoutSpecial;

		public Header(Context context) {
			super(context);
			config();
		}

		private void config() {
			// setAnimation(animationSlide.inFromRightAnimation());
			LayoutInflater li;
			String service = Context.LAYOUT_INFLATER_SERVICE;
			li = (LayoutInflater) getContext().getSystemService(service);
			li.inflate(R.layout.header_productdetail, this);

			tvNameProductDetail = (TextView) findViewById(R.id.tvNameProductDetail);

			tvActionMode = (TextView) findViewById(R.id.textView1);

			btn1 = findViewById(R.id.btnSummary);

			btn2 = findViewById(R.id.btnReportAdverse);

			btn3 = findViewById(R.id.btnDosage);

			btn1.setOnClickListener(this);

			btn2.setOnClickListener(this);

			btn3.setOnClickListener(this);

			layoutSpecial = (LinearLayout) findViewById(R.id.layoutSpecial);

		}

		public void onClick(View v) {
			if (btn1 == v) {
				String product_type = rosemontTable.getValue(index,
						RosemontTable.PRODUCT_TYPE);
				boolean isSpec = "0".equals(product_type);
				if (isSpec) {
					String name = rosemontTable.getNameFile(index).toString();
					
					if (CommonNetwork.haveConnectTed(getContext())) {
						name = name.substring(11, name.length());
						Log.i("PDF name: ", "" + index);
						((IActivity) getContext()).gotoViewOnlinePDF(name);
					} else {
						File dir = new File(CommonApp.PATH + "/" + name);
						if (!dir.exists()) {
							DialogOk dialog = new DialogOk(getContext(),
									R.string.key20);
							dialog.setOnDismissListener(new OnDismissListener() {
								public void onDismiss(DialogInterface dialog) {
									if (dialog instanceof DialogOk) {

									}
								}
							});
							dialog.show();
						} else {
							name = CommonApp.changleName(name);
							String path = CommonApp.PATH + name;
							File file = new File(path);
							if (Common.isNullOrBlank(name)) {
								CommonView.viewDialog(
										getContext(),
										"",
										getResources().getString(
												string.havent_cer));
							} else if (file.exists()) {
								CommonApp.startPDF(path, getContext());
							} else if (!CommonNetwork
									.haveConnectTed(getContext())) {
								CommonView.viewDialog(
										getContext(),
										"",
										getResources().getString(
												string.havent_network));
							} else {
								new SavePDFAsyn(getContext(), name).execute("");
							}
						}
					}
				} else {
					DialogOkNo dialog = new DialogOkNo(getContext(),
							R.string.key8);
					dialog.setOnDismissListener(new OnDismissListener() {
						public void onDismiss(DialogInterface dialog) {
							if (dialog instanceof DialogOkNo) {
								if (((DialogOkNo) dialog).isOk()) {
									String url = "http://www.yellowcard.gov.uk";
									((IActivity) getContext()).gotoSumary(url);
								}
							}
						}
					});
					dialog.show();

				}
			} else if (btn2 == v) {
				((IActivity) getContext()).gotoAdverse();
			} else if (btn3 == v) {
				String idRow = rosemontTable.getValue(index, "id");
				String id_product = rosemontTable.getValue(index,
						RosemontTable.ID_PRODUCT);

				Handler handler = (Handler) ((Activity) getContext())
						.getIntent().getParcelableExtra(Common.KEY02);
				Message message = new Message();
				message.what = 1;

				Bundle bundle = new Bundle();
				bundle.putString(Common.KEY03, idRow);
				bundle.putString(Common.KEY04, id_product);

				String strength = rosemontTable.getValue(index,
						RosemontTable.STRENGTH);

				DataStore.getInstance().setConfig(Conts.STRENGTH_CALCULATOR,
						strength);
				DataStore.getInstance().setConfig(Conts.SEND, true);
				message.setData(bundle);

				handler.sendMessage(message);

			}
		}

		public void update(int[] resource) {
			btn1.setBackgroundResource(resource[0]);
			btn2.setBackgroundResource(resource[1]);
			btn3.setBackgroundResource(resource[2]);
		}

	}

	private void update(List<ItemSearch> lData, String nameProduct,
			String mode, boolean isSpec) {

		header.tvNameProductDetail.setText(nameProduct);

		String modeOfAction = getResources().getString(R.string.key3);
		modeOfAction += mode;
		header.tvActionMode.setText(modeOfAction);

		int[] resource = new int[] { drawable.btn_cer, drawable.btn_report,
				drawable.btn_dosage };
		if (!isSpec) {
			resource = new int[] { drawable.btn_sum, drawable.btn_report,
					drawable.btn_dosage };
		}

		adapter.update(lData);

		adapter.notifyDataSetChanged();

		header.update(resource);

		header.layoutSpecial.setVisibility(isSpec ? VISIBLE : GONE);
	}

	public void update(RosemontTable rosemontTable2, int index2) {
		rosemontTable = rosemontTable2;
		index = index2;
		List<ItemSearch> lData = rosemontTable.getDetailList(index);

		String nameProduct = rosemontTable.getGenericName(index);

		String mode = rosemontTable.getValue(index, RosemontTable.MODE);

		String product_type = rosemontTable.getValue(index,
				RosemontTable.PRODUCT_TYPE);
		boolean isSpec = "0".equals(product_type);
		update(lData, nameProduct, mode, isSpec);

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String header = adapter.getItem(arg2 - 1).getTxtHeader();
		final String url = adapter.getItem(arg2 - 1).getTxtContent()
				.replace("Patient Info URL|", "");
		if (RosemontTable.PATIENT_INFO.replace("_", " ").equals(header)) {
			((IActivity) getContext()).callWeb(url, R.string.key4);
		}
	}
}
