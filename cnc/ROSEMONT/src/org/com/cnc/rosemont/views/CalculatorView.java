package org.com.cnc.rosemont.views;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonView;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.CalculatorActivity;
import org.com.cnc.rosemont.activity.DialogWarnningCalculatorActivity;
import org.com.cnc.rosemont.activity.ListMgActivity;
import org.com.cnc.rosemont.activity.ListStrengActivity;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.views.widgets.DialogOk;
import org.com.cnc.rosemont.views.widgets.DialogOk.OkClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class CalculatorView extends LinearLayout implements IView {
	int count = 0;
	private Holder holder;
	private boolean isSecondEdit = false;
	private boolean needShowDialog;
	private RosemontTable rosemontTable = new RosemontTable();
	private int index;

	// public AnimationSlide animationSlide=new AnimationSlide();
	public CalculatorView(Context context) {
		super(context);
		config(R.layout.calculator1);
	}

	public CalculatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.calculator1);
	}

	private void config(int resource_layouy) {
		// setAnimation(animationSlide.inFromRightAnimation());
		String sv = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(sv);
		li.inflate(resource_layouy, this);

		holder = new Holder();

		// click on background hidden keyBoard
		OnClickListener hiddenKeyBoard = new OnClickListener() {
			public void onClick(View v) {
				CommonView.hiddenKeyBoard((Activity) getContext());

			}
		};

		findViewById(R.id.lnlContent).setOnClickListener(hiddenKeyBoard);
		findViewById(R.id.llContent).setOnClickListener(hiddenKeyBoard);
		if (!CalculatorActivity.ok) {
			new DialogOk(getContext(), R.string.key9).show();
		}
		// truong vuong add
		boolean adust = DataStore.getInstance().getConfig(
				org.com.cnc.rosemont.views.Conts.ADUST, true);
		boolean weight = DataStore.getInstance().getConfig(
				(org.com.cnc.rosemont.views.Conts.WEIGHT), true);

		if (adust) {
			RadioButton rB = (RadioButton) holder.radioGroupAudult
					.getChildAt(0);
			rB.setChecked(true);
		} else {
			RadioButton rB = (RadioButton) holder.radioGroupAudult
					.getChildAt(1);
			rB.setChecked(true);
		}

		if (weight) {
			RadioButton rBWeight = (RadioButton) holder.radioGroup
					.getChildAt(0);
			rBWeight.setChecked(true);
		} else {
			RadioButton rBWeight = (RadioButton) holder.radioGroup
					.getChildAt(1);
			rBWeight.setChecked(true);
		}
	}

	boolean isFirst = true;

	private void calculator() {
		// isFirst = true;
		float result = 0;
		String strength = null;
		String Z = holder.etDose.getText().toString();
		if (rosemontTable.sizeOfRow() > 0) {
			try {
				strength = rosemontTable.getStreng(index);
			} catch (Exception e) {
				index = 0;
				strength = rosemontTable.getStreng(index);
			}
		}

		if (holder.radioAdult.isChecked() && strength != null) {
			result = CommonApp.calculator(strength, Z);
		} else {
			String W = holder.etAge.getText().toString();

			boolean isWeight = ((RadioButton) holder.radioGroup.getChildAt(0))
					.isChecked();
			if (!isWeight) {
				result = CommonApp.calculator(strength, Z);
			} else {
				result = CommonApp.calculator(strength, Z, W);
			}
		}

		String check = getResources().getString(R.string.dosage_in_milligams1);

		if (check.equals(holder.tvMg.getText().toString())) {
			result /= 1000;
		}

		int result1 = (int) (result * 1000);
		result = ((float) result1) / 1000;

		if (result == 0) {
			holder.tvResult.setText("0.000 ml");
		} else {
			holder.tvResult.setText(convert(result));
		}

		isFirst = false;
	}

	private String convert(float result) {
		// if result is integer
		if ((int) result == result) {
			return result + "00 ml";
		}

		// if result have one
		if ((int) (result * 10) == result * 10) {
			return result + "00 ml";
		}

		// if result have two
		if ((int) (result * 100) == result * 100) {
			return result + "0 ml";
		}

		return result + " ml";
	}

	public void showButton(boolean isShowBack, boolean isShowNext) {
		holder.headerView.showButton(isShowBack, isShowNext);
	}

	public void updateData(RosemontTable rosemontTable, int index) {
		if (rosemontTable != null) {
			this.rosemontTable = rosemontTable;
		}

		this.index = index;
		updateText();
		holder.reset();
	}

	public void setIndex(int index2) {
		this.index = index2;
		updateText();
		holder.reset();
	}

	private void updateText() {
		holder.tvStreng.setText(rosemontTable.getStreng(index) + "");
		holder.tvChooseProduct.setText(rosemontTable.getGenericName(index));
		// holder.reset();
	}

	private void onBack() {
		DataStore.getInstance().setConfig(Conts.BACKFROMCALCULATOR, true);
		Handler handler = (Handler) ((Activity) getContext()).getIntent()
				.getParcelableExtra(Common.KEY02);
		Message message = new Message();
		message.what = 0;
		handler.sendMessage(message);
		// setAnimation(animationSlide.inFromRightAnimation());
	}

	private class Holder implements OnClickListener, OnEditorActionListener,
			RadioGroup.OnCheckedChangeListener {
		RadioGroup radioGroupAudult;
		EditText etAge;
		public EditText etDose;
		RadioGroup radioGroup;
		// RadioGroup RadioGroup01;
		RadioButton radioAdult;
		TextView tvChooseProduct;
		TextView tvStreng;
		TextView tvResult;
		TextView tvMg;
		View llWeight_Age;
		HeaderView headerView;
		TextView lbWeight;
		View view;

		public Holder() {

			tvChooseProduct = (TextView) findViewById(R.id.TextView02);
			tvStreng = (TextView) findViewById(R.id.TextView01);
			tvMg = (TextView) findViewById(R.id.textView1);
			tvResult = ((TextView) findViewById(R.id.TextView04));
			etAge = (EditText) findViewById(R.id.EditText02);
			llWeight_Age = findViewById(R.id.LinearLayout04);
			etDose = (EditText) findViewById(R.id.EditText01);
			radioAdult = (RadioButton) findViewById(R.id.RadioButtonSettingLeft01);
			lbWeight = (TextView) findViewById(R.id.TextView06);
			etDose.setOnClickListener(this);

			radioGroupAudult = (RadioGroup) findViewById(R.id.RadioGroup01);
			radioGroupAudult.setOnCheckedChangeListener(this);

			radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
			radioGroup.setOnCheckedChangeListener(this);

			headerView = (HeaderView) findViewById(R.id.headerView1);

			etAge.addTextChangedListener(textWatcher);
			etDose.setOnEditorActionListener(this);

			headerView.showButton(true, false);
			headerView.setType(HeaderView.TYPE_CACULATOR);
			headerView.setOnClick(onback, null);

			// add action for text
			tvChooseProduct.setOnClickListener(this);
			tvStreng.setOnClickListener(this);
			tvMg.setOnClickListener(this);
			view = findViewById(R.id.linearLayout31);

			config();

		}

		// configuration when checkBox change
		private void config() {
			// radio adult
			RadioButton rB = (RadioButton) radioGroupAudult.getChildAt(0);
			llWeight_Age.setVisibility(rB.isChecked() ? GONE : VISIBLE);
			view.setVisibility(rB.isChecked() ? GONE : VISIBLE);

			RadioButton rBWeight = (RadioButton) radioGroup.getChildAt(0);

			// radio Age

			RadioButton rBAge = (RadioButton) radioGroup.getChildAt(1);

			rBAge.setEnabled(!rB.isChecked());
			rBWeight.setEnabled(!rB.isChecked());

			// resource for hint age
			int resource = string.enter_weight_in_kg;
			int resLbWeight = string.weight;
			if (!rBWeight.isChecked()) {
				resource = string.enter_weight_in_age;
				resLbWeight = string.age;
			}

			lbWeight.setText(resLbWeight);
			etAge.setHint(CommonApp.getString(getResources(), resource));

		}

		public void reset() {
			etDose.setText("");

			etAge.setText("");
			tvResult.setText("0.0 ml");
		}

		private OnClickListener onback = new OnClickListener() {
			public void onClick(View v) {
				if (getContext() instanceof CalculatorActivity) {
					onBack();

				} else {
					((IActivity) getContext()).onBack1();

				}
			}
		};

		public void onClick(View v) {
			if (tvMg == v) {
				Toast.makeText(getContext(), "come on 1", Toast.LENGTH_SHORT);
				Activity activity = ((Activity) getContext());
				Intent intent = new Intent(activity, ListMgActivity.class);
				activity.startActivityForResult(intent, Common.REQUESTCODE_03);
			} else if (v == tvChooseProduct) {
				Toast.makeText(getContext(), "come on 1", Toast.LENGTH_SHORT);
				if (getContext() instanceof CalculatorActivity) {
					// etDose.setText("");
					InputMethodManager imm = (InputMethodManager) getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					((IActivity) getContext()).gotoProductList();

				} else {
					Toast.makeText(getContext(), "come on 2",
							Toast.LENGTH_SHORT);
					((IActivity) getContext())
							.gotoProductListCalculator(rosemontTable);

				}
				etDose.clearFocus();
				etAge.clearFocus();
			} else if (v == tvStreng) {
				Toast.makeText(getContext(), "come on 2", Toast.LENGTH_SHORT);
				Activity activity = ((Activity) getContext());
				Intent intent = new Intent(activity, ListStrengActivity.class);

				intent.putExtra(Common.KEY01, rosemontTable.sizeOfRow());
				for (int i = 0; i < rosemontTable.sizeOfRow(); i++) {
					intent.putExtra(Common.KEY01 + i,
							rosemontTable.getStreng(i));
				}

				activity.startActivityForResult(intent, Common.REQUESTCODE_02);
			} else if (v == etDose) {
				if (needShowDialog) {
					etDose.setText("");
					showManualBox();
				}
			}
		}

		/*
		 * private void showManualBox(String content) { AlertDialog dialog = new
		 * AlertDialog.Builder(getContext()).create();
		 * dialog.setMessage(content);
		 * dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * public void onClick(DialogInterface dialog, int which) {
		 * dialog.dismiss(); etDose.requestFocus(); } }); dialog.show(); }
		 */

		private void showManualBox() {
			needShowDialog = false;
			etDose.clearFocus();
			DialogOk dialogOk = new DialogOk(getContext(), R.string.key9);
			dialogOk.setOkClickListener(new OkClickListener() {

				public void performOkClick() {
					// TODO Auto-generated method stub
					etDose.requestFocus();
					InputMethodManager keyboard = (InputMethodManager) getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				}
			});
			dialogOk.show();
		}

		private TextWatcher textWatcher = new TextWatcher() {
			// Age from 1 to 16
			// Weight 200
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String newValue = etAge.getText().toString().trim();
				if (!newValue.equals("")) {
					int value = Integer.parseInt(newValue);
					RadioButton radioButton = ((RadioButton) radioGroup
							.getChildAt(0));

					if (radioButton.isChecked() && value > 200) {
						value /= 10;
						etAge.setText(value + "");
						// etAge.setText(data);
						int pos = etAge.getText().length();
						etAge.setSelection(pos);

					} else if (!radioButton.isChecked() && value > 16) {
						value /= 10;
						etAge.setText(value + "");
						int pos = etAge.getText().length();
						etAge.setSelection(pos);
						// data
						// etAge.setText(data);
					}
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {

			}
		};

		public boolean onEditorAction(TextView textView, int action,
				KeyEvent arg2) {
			if (etDose == textView && action == EditorInfo.IME_ACTION_DONE) {
				etDose.clearFocus();

				if (rosemontTable != null && rosemontTable.sizeOfRow() == 0) {
					new DialogOk(getContext(), R.string.key2).show();
					return true;
				}

				String W = holder.etAge.getText().toString();
				if (W.equals("") && !holder.radioAdult.isChecked()) {
					Intent intent = new Intent(getContext(),
							DialogWarnningCalculatorActivity.class);
					getContext().startActivity(intent);

				} else {
					calculator();
				}
			}

			return false;
		}

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (group == radioGroupAudult || group == radioGroup) {
				reset();
				config();
				if (group == radioGroupAudult) {
					RadioButton rb = (RadioButton) radioGroupAudult
							.getChildAt(0);
					if (!rb.isChecked()) {
					}
				}
				RadioButton rB = (RadioButton) holder.radioGroupAudult
						.getChildAt(0);
				RadioButton rBWeight = (RadioButton) holder.radioGroup
						.getChildAt(0);
				DataStore.getInstance().setConfig(
						org.com.cnc.rosemont.views.Conts.ADUST, rB.isChecked());
				DataStore.getInstance().setConfig(
						org.com.cnc.rosemont.views.Conts.WEIGHT,
						rBWeight.isChecked());
			}
		}
	}

	// call when change unit
	public void setMg(String text) {

		// Show dialog when change
		/*
		 * if (text.contains("mg")) { new DialogOk(getContext(),
		 * R.string.key9).show(); } else { new DialogOk(getContext(),
		 * R.string.key10).show(); }
		 */

		if (!holder.tvMg.getText().toString().equals(text)) {
			if (holder.etDose.getText().toString().length() > 0) {
				try {
					String textDosage = holder.etDose.getText().toString();

					float f = Float.parseFloat(textDosage);
					if (text.contains("mg")) {
						f /= 1000;
					} else {
						f *= 1000;
					}

					holder.etDose.setText(f + "");

				} catch (Exception e) {
				}
			}
		}

		holder.tvMg.setText(text);
		String check = getResources().getString(R.string.dosage_in_milligams1);
		if (check.equals(holder.tvMg.getText().toString())) {
			holder.etDose.setHint(R.string.enter_patient_dose_in_mg2);
		} else {
			holder.etDose.setHint(R.string.enter_patient_dose_in_mg);
		}

		calculator();
		// holder.reset();
	}

	public void updateData(String id, String idRow, String strength) {
		int index = 0;
		for (int i = 0; i < rosemontTable.sizeOfRow(); i++) {
			if (Common.contains(rosemontTable.getStreng(i), strength)) {
				index = i;
				// break;
			}
		}

		// String strength =
		// DataStore.getInstance().getConfig(Conts.STRENGTH_CALCULATOR);

		updateData(null, index);
	}

	public void updateData(String id, String id_row) {
		rosemontTable = CommonApp.ROSEMONT.createRosemontable(id);
		index = rosemontTable.getIndex(id_row);

		// String strength =
		// DataStore.getInstance().getConfig(Conts.STRENGTH_CALCULATOR);
		updateData(rosemontTable, index);

	}

	public void reset() {

	}

	public void updateStrength() {
		holder.tvStreng.setText(DataStore.getInstance().getConfig(
				Conts.STRENGTH_CALCULATOR, ""));
	}

	public void showDialog() {
		DialogOk dialogOk = new DialogOk(getContext(), "Hien thi di cung");
		dialogOk.setOnDismissListener(new OnDismissListener() {

			public void onDismiss(DialogInterface dialog) {
				((IActivity) CalculatorView.this.getContext()).showKeyBoard();
				holder.etDose.requestFocus();
			}
		});

		dialogOk.show();
	}

}