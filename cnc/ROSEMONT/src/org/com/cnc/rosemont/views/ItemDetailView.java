package org.com.cnc.rosemont.views;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.drawable;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.items.ItemSearch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemDetailView extends LinearLayout implements IView{
	//private AnimationSlide animationSlide=new AnimationSlide();
	public ItemDetailView(Context context) {
		super(context);
		config();
	}

	public ItemDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public void config() {
//setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		int resource_layouy = R.layout.itemproductdetailtext;

		li.inflate(resource_layouy, this);
	}

	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		findViewById(R.id.ll).setLayoutParams(params);
	}

	public void updateData(ItemSearch item) {

		String header = CommonApp.upper(item.getTxtHeader());
		TextView textView1 = ((TextView) findViewById(R.id.textView1));
		textView1.setText(header);
		TextView textView = ((TextView) findViewById(R.id.textView2));
		textView.setText("");
		// // size for S, TAB
		// int size = heght * 20 / 960;
		//
		// // if is Galaxy Y
		// if (heght < CommonDeviceId.SIZE_HEIGHT_S) {
		// size = heght * 40 / 960;
		// }

		// textView.setTextSize(size);

		if (RosemontTable.PATIENT_INFO.replace("_", " ").equals(
				item.getTxtHeader())) {
			textView.setBackgroundResource(R.drawable.search);
		} else if (item.getTxtHeader().equals(RosemontTable.VISCOSITY)) {
			String value = item.getTxtContent();
			int water[] = new int[] { R.drawable.water1, R.drawable.water2,
					R.drawable.water3 };
			if (!Common.isNullOrBlank(value)) {
				if ("1".equals(value)) {
					textView.setBackgroundResource(water[0]);
				} else if ("2".equals(value)) {
					textView.setBackgroundResource(water[1]);
				} else if ("3".equals(value)) {
					textView.setBackgroundResource(water[2]);
				}
			}
		} else if (item.isChecked()) {
			boolean checked = "1".endsWith(item.getTxtContent());
			int res = checked ? drawable.checked : drawable.un_checked;
			textView.setBackgroundResource(res);
		} else {

			textView.setText(item.getTxtContent());
			// if (RosemontTable.PATIENT_INFO.equals(item.getTxtHeader())) {
			// textView.setBackgroundResource(drawable.search);
			// String text = item.getTxtContent().replace("Patient Info URL|",
			// "");
			// textView.setText(text);
			// // DialogOkNo dialog = new DialogOkNo(getContext(),
			// // string.key4);
			// // dialog.addAction(new View.OnClickListener() {
			// // public void onClick(View v) {
			// // ((Activity) getContext()).finish();
			// // CommonView.callWeb(getContext(), text);
			// // }
			// // });
			// // dialog.show();
			// } else {
			// textView.setText(item.getTxtContent());
			// }
		}

		// if (TYPE_CHECK == type) {
		// // ((CheckBox) findViewById(id.checkBox1)).setChecked("1"
		// // .endsWith(item.getTxtContent()));
		// // ((CheckBox) findViewById(id.checkBox1)).setEnabled(false);
		// // ((CheckBox) findViewById(id.checkBox1)).setFocusable(false);
		// } else if (type == TYPE_TEXT) {
		// TextView textView2 = ((TextView) findViewById(R.id.textView2));
		// textView2.setTextSize(size);
		// textView2.setText(item.getTxtContent());
		// } else if (type == TYPE_VISICOSITY) {
		// // View view = findViewById(R.id.linearLayout2);
		// // String value = item.getTxtContent();
		// // int water[] = new int[] { R.drawable.water1,
		// // R.drawable.water2,
		// // R.drawable.water3 };
		// // if (!Common.isNullOrBlank(value)) {
		// // if ("1".equals(value)) {
		// // view.setBackgroundResource(water[0]);
		// // } else if ("2".equals(value)) {
		// // view.setBackgroundResource(water[1]);
		// // } else if ("3".equals(value)) {
		// // view.setBackgroundResource(water[2]);
		// // }
		// } else {
		// // view.setBackgroundResource(water[0]);
		// }
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
