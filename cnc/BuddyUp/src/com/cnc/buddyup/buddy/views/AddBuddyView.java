package com.cnc.buddyup.buddy.views;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cnc.buddyup.BuddiesScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.common.views.LoadingView;
import com.cnc.buddyup.request.RequestAddbuddyList;
import com.cnc.buddyup.response.ResponseAddBuddyList;
import com.cnc.buddyup.views.LinearLayout;

public class AddBuddyView extends LinearLayout {
	private LoadingView loadingView;
	public class Filed{
		public EditText fETName;
		public EditText fFTDiscription;
		public Button fBtnAddBuddy;
	}
	
	private Filed filed= new Filed();
	
	
	public Filed getFiled() {
		return filed;
	}


	public void setFiled(Filed filed) {
		this.filed = filed;
	}

	public AddBuddyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.addbuddy);
	}

	
	public AddBuddyView(Context context) {
		super(context);
		config(R.layout.addbuddy);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		filed.fETName = getEditText(R.id.editText1);
		filed.fFTDiscription = getEditText(R.id.EditText01);
		filed.fBtnAddBuddy = getButton(R.id.button1);
		filed.fBtnAddBuddy.setOnClickListener(clickListener);
		loadingView = (LoadingView)findViewById(R.id.loadingView1);
		showLoading(false);
		findViewById(R.id.commonbtnBack).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((BuddiesScreen)getContext()).onBack();
			}
		});
	}
	
	private void showLoading(final boolean isShow){
		loadingView.post(new Runnable() {
			public void run() {
				loadingView.setVisibility(isShow? VISIBLE : GONE);
			}
		});
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			new AddBuddyAsyn().execute("");
		}
	};
	
	private class AddBuddyAsyn extends AsyncTask<String, String, String>{
		private ResponseAddBuddyList response;
		protected String doInBackground(String... params) {
			showLoading(true);
			RequestAddbuddyList request = new RequestAddbuddyList();
			request.setUsername(filed.fETName.getText().toString());
			request.setDescription(filed.fFTDiscription.getText().toString());
			response = ResponseAddBuddyList.getResponseAddBuddyList(request);
			showLoading(false);
			return null;
		}
		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if("true".equals(response.getStatus())){
				Common.builder(getContext(), "Message", response.getMessage());
			}else{
				Common.builder(getContext(), "Message", response.getMessage());
			}
		}
		
	}
	
	
}