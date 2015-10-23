package com.icts.viewcustom;

import java.security.Timestamp;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icts.app.ItelApplication;
import com.icts.itel.MainActivity;
import com.icts.json.JsonAnalysis;
import com.icts.utils.Constant;
import com.icts.utils.Utils;

public class StatusDialog extends Dialog {
	private String mName;
	private TextView mTvName;
	private LinearLayout mLLBg;
	private ImageButton mImgBtnDel;
	private ImageButton mImgBtnItell;
	private EditText mEditTItell;
	private Context mContext;
	private Handler mainHandler;
	int mPolicy = Constant.POLICY_FRIEND;
	MainActivity mMainActivity;
	InputMethodManager imm;

	public StatusDialog(Context context, int thme, String name, Handler mHandler) {
		super(context, thme);
		mName = name;
		this.mContext = context;
		this.mainHandler = mHandler;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogitell);
		imm = (InputMethodManager) getContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		}
		initView();
		initData();
		setEvent();

	}

	public void initView() {
		mLLBg = (LinearLayout) this.findViewById(R.id.dialog_ll);
		mTvName = (TextView) this.findViewById(R.id.dialog_tvName);
		mImgBtnDel = (ImageButton) this.findViewById(R.id.dialog_ImgBtnDell);
		mImgBtnItell = (ImageButton) this.findViewById(R.id.dialog_btnItell);
		mEditTItell = (EditText) this.findViewById(R.id.dialog_editStatus);
		// Animation logoMoveAnimation =
		// AnimationUtils.loadAnimation(mLLBg.getContext(),
		// R.anim.scale_button);

	}

	public void initData() {
		mTvName.setText(mName);
		if (mName.equals("All User")) {
			mPolicy = Constant.POLICY_ALL;
			mImgBtnItell.setImageResource(R.drawable.friend_tell_button_yellow);
			mLLBg.setBackgroundResource(R.drawable.all_user_bg);
			ScaleAnimation scale = new ScaleAnimation((float) 0.5, (float) 1,
					(float) 0.5, (float) 1);
			scale.setFillAfter(true);
			scale.setDuration(500);
			mLLBg.startAnimation(scale);

		} else {
			if (mName.equals("Other")) {
				mLLBg.setBackgroundResource(R.drawable.other_bg);
				mImgBtnItell
						.setImageResource(R.drawable.friend_tell_button_blue);
				mPolicy = Constant.POLICY_OTHER;
				// ScaleAnimation scale = new ScaleAnimation((float) 0.5,
				// (float) 1, (float) 0.5, (float) 1,1f,1f,);
				ScaleAnimation scale = new ScaleAnimation((float) 0.5,
						(float) 1, (float) 0.5, (float) 1,
						Animation.RELATIVE_TO_PARENT, 1f,
						Animation.RELATIVE_TO_PARENT, 1f);
				scale.setFillAfter(true);
				scale.setDuration(500);
				mLLBg.startAnimation(scale);
			} else {
				mPolicy = Constant.POLICY_FRIEND;
				mImgBtnItell.setImageResource(R.drawable.friend_tell_button);
				ScaleAnimation scale = new ScaleAnimation((float) 0.5,
						(float) 1, (float) 0.5, (float) 1,
						Animation.RELATIVE_TO_PARENT, 0f,
						Animation.RELATIVE_TO_PARENT, 1f);
				scale.setFillAfter(true);
				scale.setDuration(500);
				mLLBg.startAnimation(scale);

			}
		}
	}

	public void setEvent() {
		mImgBtnDel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				dismiss();
			}
		});
		mImgBtnItell.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

				mJsonAlalysis = new JsonAnalysis(v.getContext());
				String url = "http://49.212.140.145/itell/itell/post_itell";
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("user_id", String
						.valueOf(ItelApplication.user_id)));// isp
				// authen_code
				nameValuePairs.add(new BasicNameValuePair("uuid",
						ItelApplication.uuid));
				nameValuePairs.add(new BasicNameValuePair("itell", mEditTItell
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("itell_policy",
						Integer.toString(mPolicy)));
				Log.e("", "paramm==" + nameValuePairs.toString());
				mJsonAlalysis.executeLoadData(url, handlerLoad, v.getContext(),
						nameValuePairs);
				/*
				 * LayoutInflater layoutInflater = (LayoutInflater)
				 * v.getContext()
				 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View
				 * mView = layoutInflater.inflate(R.layout.map, null); mapView =
				 * new Map(v.getContext(), mMainActivity, mView);
				 */

			}
		});
	}

	Map mapView;
	JsonAnalysis mJsonAlalysis;

	/*
	 * 
	 * Handler doi load data tu server
	 */

	final Handler handlerLoad = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {
					try {
						if (mJsonAlalysis.getCode(data) != null) {
							ItelApplication.user.itellPolicy = mPolicy;
							ItelApplication.user.setStatus(mEditTItell
									.getText().toString());
							JSONObject json = new JSONObject(data);
							Date date = Utils.convertStringToDate(
									json.getString("start_time"), null);
							if (date != null) {
								ItelApplication.user.setStatusUpdateTime(date
										.getTime());
							}
							
							long timestamp = json.getLong("timestamp");
							ItelApplication.timeStamp = timestamp;
							ItelApplication.user.setmTimeCurrent(timestamp);
							ItelApplication.setTime(ItelApplication.user.getStatusUpdateTime());
							dismiss();
							mainHandler.sendEmptyMessage(0);
							Log.i("NDT", "register  " + data);
						} else {
							Toast.makeText(
									mContext,
									"Cannot change status with user id"
											+ ItelApplication.user_id,
									Toast.LENGTH_LONG).show();
						}

					} catch (JSONException e) {
						Toast.makeText(
								mContext,
								"Cannot change status with user id "
										+ ItelApplication.user_id + " error: "
										+ e.getMessage(), Toast.LENGTH_LONG)
								.show();
						e.printStackTrace();
					}
				} else {
					Toast.makeText(
							mContext,
							"Cannot change status with user id"
									+ ItelApplication.user_id,
							Toast.LENGTH_LONG).show();
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

}
