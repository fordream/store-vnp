package com.icts.viewcustom;

import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.json.JsonAnalysis;
import com.icts.object.ObjFriend;

public class Profile {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private Button mBtnOk;
	private MainActivity registerAc;
	private JsonAnalysis mJsonAlalysis;
	private ObjFriend mObjFriend = new ObjFriend();

	public Profile(Context context, MainActivity register) {
		mContext = context;
		registerAc = register;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.profile, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		// mBtnOk = (Button) mView.findViewById(R.id.register_chose_btnOk);

	}

	public void initData() {
		String url = "";
	//	mJsonAlalysis = new JsonAlalysis();
	//	mJsonAlalysis.executeLoadData(url, handlerLoadData);

	}

	public void setEvent() {
		// mBtnOk.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		//
		// InputNumber input = new InputNumber(v.getContext(), registerAc,
		// 0);
		// registerAc.changeViewLL(input.getmView());
		// }
		// });
	}

	public View getView() {
		return mView;
	}

	public void setView(View mView) {
		this.mView = mView;
	}

	/*
	 * 
	 * Handler doi load data tu server
	 */

	final Handler handlerLoadData = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {
					try {
						mObjFriend = mJsonAlalysis.getFriend(data);

					} catch (JSONException e) {
						// Utils.showDialogServerError(json.mContext);
						e.printStackTrace();
					}
				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

}
