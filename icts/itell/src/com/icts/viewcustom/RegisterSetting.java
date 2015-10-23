package com.icts.viewcustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.icts.itel.MainActivity;
import com.icts.itel.R;

public class RegisterSetting {
	private Context mContext;
	private MainActivity mainActivity;
	private LayoutInflater layoutInflater;
	private View mView = null;
	private Button btnBack;

	public RegisterSetting(Context context, MainActivity mActivity) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mainActivity = mActivity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.layout_setting_register, null);
		initViewComponent();
	}

	void initViewComponent() {
		btnBack = (Button) mView.findViewById(R.id.btnRegisterBack);
		btnBack.setOnClickListener(onClickComponent);
	}

	OnClickListener onClickComponent = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnRegisterBack:
				SettingsScreen profile = new SettingsScreen(mContext,
						mainActivity);
				mainActivity.changeViewLL(profile.getmView());

				break;
			
			default:
				break;
			}
		}
	};

	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = mView;
	}
}
