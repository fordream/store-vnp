package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import com.icts.itel.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BadgetDialog extends Dialog implements android.view.View.OnClickListener {
	private String mNick;
	private String friendID;
	private Context context;
	private Handler mainHandler;
	private TextView tvNick;
	private ImageView imgAvatar;
	private Button btnAsses;
	private LinearLayout llGood,llNormal,llBad;
	private String urlAvatar;

	public BadgetDialog(Context context, String friendID, String friendNick,String urlAvatar, Handler mHandler) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		mNick= friendNick;
		this.friendID = friendID;
		this.context = context;
		this.mainHandler = mHandler;
		this.urlAvatar = urlAvatar;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_badget);
		initView();
		initDataAndEvent();

	}

	/**
	 * Get view from layout
	 */
	private void initView() {
		tvNick = (TextView)findViewById(R.id.badget_tv_nick);
		llGood = (LinearLayout)findViewById(R.id.badget_ll_good);
		llNormal = (LinearLayout)findViewById(R.id.badget_ll_normal);
		llBad = (LinearLayout)findViewById(R.id.badget_ll_bad);
		imgAvatar = (ImageView)findViewById(R.id.badget_img_avatar);
		btnAsses = (Button)findViewById(R.id.badget_btn);
	}
	
	private void initDataAndEvent(){
		if (tvNick!=null){
			tvNick.setText(mNick);
		}
		
		if (llGood!=null){
			llGood.setOnClickListener(this);
		}
		
		if (llBad!=null){
			llBad.setOnClickListener(this);
		}
		
		if (llNormal!=null){
			llNormal.setOnClickListener(this);
		}
		
		if (btnAsses!=null){
			btnAsses.setOnClickListener(this);
		}
		
	}

	@Override
	public void onClick(View v) {
		
	}

	
	

}
