package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.views.widgets.DialogOkNo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class IntroView extends LinearLayout implements IView, OnClickListener {
	private AnimationSlide animationSlide=new AnimationSlide();
	public IntroView(Context context) {
		super(context);
		config(R.layout.intro);
	}

	public IntroView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.intro);
	}

	private void config(int resource_layouy) {
		//setAnimation(animationSlide.inFromRightAnimation());
		String sv = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(sv);
		li.inflate(resource_layouy, this);
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.showButton(true, false);
		headerView.setText(string.key14);
		findViewById(R.id.ll_act1968).setOnClickListener(this);
		findViewById(R.id.ll_consumer1987).setOnClickListener(this);
		findViewById(R.id.ll_act_2010).setOnClickListener(this);
		findViewById(R.id.ll_human1998).setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.ll_act1968) {
			DialogOkNo dialog = new DialogOkNo(getContext(), R.string.key8);
			dialog.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(DialogInterface dialog) {
					if (dialog instanceof DialogOkNo) {
						if (((DialogOkNo) dialog).isOk()) {

							((IActivity) getContext()).gotoCrushingMedicines();
						}
					}
				}
			});
			dialog.show();

		} else if (v.getId() == R.id.ll_consumer1987) {
			DialogOkNo dialog = new DialogOkNo(getContext(), R.string.key8);
			dialog.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(DialogInterface dialog) {
					if (dialog instanceof DialogOkNo) {
						if (((DialogOkNo) dialog).isOk()) {

							((IActivity) getContext()).gotoCrushingConsumer();
						}
					}
				}
			});
			dialog.show();

		} else if (v.getId() == R.id.ll_act_2010) {
			DialogOkNo dialog = new DialogOkNo(getContext(), R.string.key8);
			dialog.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(DialogInterface dialog) {
					if (dialog instanceof DialogOkNo) {
						if (((DialogOkNo) dialog).isOk()) {

							((IActivity) getContext())
									.gotoCrushingTheEquality();
						}
					}
				}
			});
			dialog.show();

		} else if (v.getId() == R.id.ll_human1998) {
			DialogOkNo dialog = new DialogOkNo(getContext(), R.string.key8);
			dialog.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(DialogInterface dialog) {
					if (dialog instanceof DialogOkNo) {
						if (((DialogOkNo) dialog).isOk()) {

							((IActivity) getContext()).gotoCrushingTheHuman();
						}
					}
				}
			});
			dialog.show();

		}
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}