package org.com.cnc.rosemont.views;

import org.com.cnc.common.android.CommonView;
import org.com.cnc.rosemont.R;
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

public class AdverseView extends LinearLayout implements OnClickListener,IView {
	private View callWeb;
	private View callPhone;
	public AnimationSlide animationSlide=new AnimationSlide();
	public AdverseView(Context context) {
		super(context);
		config(R.layout.adverse);
	}

	
	public AdverseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.adverse);
	}

	private void config(int resource_layouy) {
		//setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		 headerView.setType(HeaderView.TYPE_ADVERSE);
		//int idString = string.advese_event_reporting;
	//	headerView.setText(getResources().getString(idString));
		headerView.showButton(false, false);
		headerView.setOnClick(onClickBack, null);
		
		callWeb = findViewById(R.id.linearLayout4);
		callPhone = findViewById(R.id.linearLayout6);
		callWeb.setOnClickListener(this);
		callPhone.setOnClickListener(this);
	}

	private OnClickListener onClickBack = new OnClickListener() {
		public void onClick(View v) {
			((IActivity) getContext()).onBack1();
		}
	};

	// private boolean isTabHome() {
	// return getContext() instanceof HomeActivity;
	// }

	public void onClick(View v) {
		if (callWeb == v) {
/*			Intent intent = new Intent(getContext(), CloseDialogActivity.class);
			((Activity) getContext()).startActivityForResult(intent,
					Common.REQUESTCODE_01);*/
			DialogOkNo dialog = new DialogOkNo(getContext(),
					R.string.key8);
			dialog.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(DialogInterface dialog) {
					if (dialog instanceof DialogOkNo) {
						if (((DialogOkNo) dialog).isOk()) {		
								String url = "http://www.yellowcard.gov.uk";
								CommonView.callWeb(getContext(), url);

						}
					}
				}
			});
			dialog.show();


		} else if (callPhone == v) {
			String phone = "01132241400";
			CommonView.callPhone(getContext(), phone);
		}
	}


	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
