package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icts.app.ItelApplication;
import com.icts.database.CheckUser;
import com.icts.drag.DragController;
import com.icts.drag.DragLayer;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.json.JsonAnalysis;
import com.icts.utils.CountDownTime.TimeOutListener;
import com.icts.utils.CountDownTime.UpdateTimeListener;
import com.icts.utils.Utils;

public class ITell implements View.OnLongClickListener, View.OnClickListener,
		View.OnTouchListener {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private TextView mTxtTime;
	private Button mBtnOk;
	private Button mBtnAll;
	private Button mBtnOther;
	private Button mBtnFriend;
	private Button mBtnCancel;
	public TextView tvTime;
	public TextView mTvHours;
	public TextView mTvSecond;
	public TextView mTvMinutes;
	private ImageView mImgVAll;
	private ImageView mImgVOther;
	private ImageView mImgVFriend;
	private LinearLayout mLLAll;
	private LinearLayout mLLFriend;
	private LinearLayout mLLOther;
	private MainActivity mMainActivity;
	JsonAnalysis mJsonAlalysis;
	private Context mC;
	private CheckUser mCheckUser;
	private int mMode = 1;

	private DragController mDragController; // Object that sends out drag-drop
	// events while a view is being
	// moved.
	private DragLayer mDragLayer; // The ViewGroup that supports drag-drop.
	private boolean mLongClickStartsDrag = true; // If true, it takes a long
	// click to start the drag
	// operation.
	// Otherwise, any touch
	// event starts a drag.

	private static final int CHANGE_TOUCH_MODE_MENU_ID = Menu.FIRST;

	public static final boolean Debugging = false;

	private UpdateTimeListener update = new UpdateTimeListener() {
		
		@Override
		public void updateText(String s, long timeRemain, TextView tv) {
			if (tvTime!=null){
				tvTime.setText(s);
			}
		}
	};
	
	private TimeOutListener tOut = new TimeOutListener() {
		
		@Override
		public void timeOut() {
			if (tvTime!=null){
				tvTime.setText("00:00:00");
			}
		}
	};
	//private CountTime4Textview countTime4Textview;
	public ITell(Context context, MainActivity mainActivity) {
		mMainActivity = mainActivity;
		mContext = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.itell, null);
		initData();
		initView();
		setEvent();
		if (ItelApplication.user!=null){
			//countTime4Textview = new CountTime4Textview(
			//		ItelApplication.user.getStatusUpdateTime(),
			//		mTvHours, mTvMinutes,
			//		mTvSecond);
			//countTime4Textview.start();
			
			/*ItelApplication.count.cancel();
			ItelApplication.count.setTimeServer(ItelApplication.timeStamp);
			ItelApplication.count.setTimeUser(ItelApplication.user.getStatusUpdateTime());
			ItelApplication.count.start();*/
			if (!ItelApplication.isSetTime){
				ItelApplication.count.setTimeServer(ItelApplication.user.getmTimeCurrent()*1000);
				ItelApplication.count.setTimeUser(ItelApplication.user.getStatusUpdateTime());
				ItelApplication.count.calculate();
			}
			ItelApplication.count.addUpdateTimeListener(update);
			ItelApplication.count.addTimeOutListener(tOut);
		}
	}

	public void initView() {
		DragController dragController = mDragController;

		mDragLayer = (DragLayer) mView.findViewById(R.id.drag_layer);
		mDragLayer.setDragController(dragController);
		dragController.addDropTarget(mDragLayer);

		mBtnAll = (Button) mView.findViewById(R.id.itell_btnAll);
		mBtnOther = (Button) mView.findViewById(R.id.itell_btnOther);
		mBtnFriend = (Button) mView.findViewById(R.id.itell_btnFriends);
		mBtnCancel = (Button) mView.findViewById(R.id.itell_btnCancel);
		/*mTvHours = (TextView) mView.findViewById(R.id.itell_tvHours);
		mTvSecond = (TextView) mView.findViewById(R.id.itell_tvSecond);
		mTvMinutes = (TextView) mView.findViewById(R.id.itell_tvMinutes);*/
		tvTime = (TextView) mView.findViewById(R.id.itell_tvTime);
		mImgVAll = (ImageView) mView.findViewById(R.id.itell_imgVSlideAll);
		mImgVOther = (ImageView) mView.findViewById(R.id.itell_imgVSlideOther);
		mImgVFriend = (ImageView) mView
				.findViewById(R.id.itell_imgVSlideFriend);
		mLLAll = (LinearLayout) mView.findViewById(R.id.itell_llAll);
		mLLOther = (LinearLayout) mView.findViewById(R.id.itell_llOther);
		mLLFriend = (LinearLayout) mView.findViewById(R.id.itell_llFriend);

		// ImageView i1 = (ImageView) findViewById(R.id.Image1);
		// ImageView i2 = (ImageView) findViewById(R.id.Image2);
		//
		// mBtnAll.setOnClickListener(this);
		// mBtnAll.setOnLongClickListener(this);
		mBtnAll.setOnTouchListener(onTouchAll);
		mBtnOther.setOnTouchListener(onTouchOther);
		mBtnFriend.setOnTouchListener(onTouchFriend);
		
		mBtnAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StatusDialog dialog = new StatusDialog(v.getContext(),
						android.R.style.Theme_Translucent_NoTitleBar,
						"All User", handlerPostItell);
			    dialog.show();

			}
		});
		mBtnFriend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				StatusDialog dialog = new StatusDialog(v.getContext(),
						android.R.style.Theme_Translucent_NoTitleBar,
						"Friends", handlerPostItell);
				dialog.show();

			}
		});

		mBtnOther.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				StatusDialog dialog = new StatusDialog(v.getContext(),
						android.R.style.Theme_Translucent_NoTitleBar, "Other",
						handlerPostItell);
				dialog.show();

			}
		});

		//
		// TextView tv = (TextView) findViewById(R.id.Text1);
		// tv.setOnLongClickListener(this);
		// tv.setOnTouchListener(this);

	}

	public void setEvent() {
		mBtnCancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (mMode == 1) {
					++mMode;
					String url = mMainActivity
							.getString(R.string.url_cancelItell);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);
					nameValuePairs.add(new BasicNameValuePair("user_id",
							ItelApplication.user.getUserId()));// isp
					// authen_code
					nameValuePairs.add(new BasicNameValuePair("uuid",
							ItelApplication.user.getUuid()));
					mC = v.getContext();
					mJsonAlalysis.executeLoadData(url, handlerCancel,
							v.getContext(), nameValuePairs);
				} else {
					--mMode;
					String url = mMainActivity
							.getString(R.string.url_postItell);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);
					nameValuePairs.add(new BasicNameValuePair("user_id",
							ItelApplication.user.getUserId()));// isp
					// authen_code
					// itell
					nameValuePairs.add(new BasicNameValuePair("uuid",
							ItelApplication.user.getUuid()));
					nameValuePairs.add(new BasicNameValuePair("itell",
							"i need money!!!!!!!!"));
					nameValuePairs.add(new BasicNameValuePair("itell_policy",
							"1"));
					mC = v.getContext();
					mJsonAlalysis.executeLoadData(url, handlerPostItell,
							v.getContext(), nameValuePairs);
				}

			}
		});
	}

	public void initData() {
		mDragController = new DragController(mMainActivity);
		mJsonAlalysis = new JsonAnalysis(mContext);
		mCheckUser = new CheckUser(mContext);

	}

	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = mView;
	}

	private float startX;
	private float startY;
	private float endY;
	private float endX;
	OnTouchListener onTouchAll = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent ev) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = ev.getX();
				startY = ev.getY();
				mImgVAll.setVisibility(View.VISIBLE);

				break;
			case MotionEvent.ACTION_CANCEL:

				break;
			case MotionEvent.ACTION_MOVE:

				break;
			case MotionEvent.ACTION_UP:
				endX = ev.getX();
				endY = ev.getY() + 120;
				mImgVAll.setVisibility(View.GONE);
				Log.e("", "endY==" + endY + "starty==" + startY);
				Log.e("",
						"1===" + Math.abs(endX - startX) + "2=="
								+ Math.abs(endY - startY));
				if (Math.abs(endX - startX) > 100
						|| Math.abs(endY - startY) > 100) {
					Log.e("", "okkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
					StatusDialog dialog = new StatusDialog(v.getContext(),
							android.R.style.Theme_Translucent_NoTitleBar,
							"All User", handlerPostItell);
					dialog.show();
				}
				break;

			default:
				break;
			}
			return true;
		}
	};
	OnTouchListener onTouchOther = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent ev) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = ev.getX();
				startY = ev.getY();
				mImgVOther.setVisibility(View.VISIBLE);

				break;
			case MotionEvent.ACTION_CANCEL:

				break;
			case MotionEvent.ACTION_MOVE:

				break;
			case MotionEvent.ACTION_UP:
				endX = ev.getX();
				endY = ev.getY() + 120;
				mImgVOther.setVisibility(View.GONE);
				Log.e("", "endY==" + endY + "starty==" + startY);
				Log.e("",
						"1===" + Math.abs(endX - startX) + "2=="
								+ Math.abs(endY - startY));
				if (Math.abs(endX - startX) > 100
						|| Math.abs(endY - startY) > 100) {
					Log.e("", "okkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
					StatusDialog dialog = new StatusDialog(v.getContext(),
							android.R.style.Theme_Translucent_NoTitleBar,
							"Other", handlerPostItell);
					dialog.show();
				}
				break;

			default:
				break;
			}
			return true;
		}
	};

	OnTouchListener onTouchFriend = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent ev) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = ev.getX();
				startY = ev.getY();
				mImgVFriend.setVisibility(View.VISIBLE);

				break;
			case MotionEvent.ACTION_CANCEL:

				break;
			case MotionEvent.ACTION_MOVE:

				break;
			case MotionEvent.ACTION_UP:
				endX = ev.getX();
				endY = ev.getY() + 120;
				mImgVFriend.setVisibility(View.GONE);
				Log.e("", "endY==" + endY + "starty==" + startY);
				Log.e("",
						"1===" + Math.abs(endX - startX) + "2=="
								+ Math.abs(endY - startY));
				if (Math.abs(endX - startX) > 100
						|| Math.abs(endY - startY) > 100) {
					Log.e("", "okkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
					StatusDialog dialog = new StatusDialog(v.getContext(),
							android.R.style.Theme_Translucent_NoTitleBar,
							"Friends", handlerPostItell);
					dialog.show();
				}
				break;

			default:
				break;
			}
			return true;
		}
	};

	public boolean onTouch(View v, MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = ev.getX();
			startY = ev.getY();
			mImgVAll.setVisibility(View.VISIBLE);

			break;
		case MotionEvent.ACTION_CANCEL:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
			endX = ev.getX();
			endY = ev.getY() + 120;
			mImgVAll.setVisibility(View.GONE);
			Log.e("", "endY==" + endY + "starty==" + startY);
			Log.e("",
					"1===" + Math.abs(endX - startX) + "2=="
							+ Math.abs(endY - startY));
			if (Math.abs(endX - startX) > 100 || Math.abs(endY - startY) > 100) {
				Log.e("", "okkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
				StatusDialog dialog = new StatusDialog(v.getContext(),
						android.R.style.Theme_Translucent_NoTitleBar,
						"All User", handlerPostItell);
				dialog.show();
			}
			break;

		default:
			break;
		}
		// If we are configured to start only on a long click, we are not going
		// to handle any events here.
		// if (mLongClickStartsDrag)
		// return false;
		//
		// boolean handledHere = false;
		//
		// final int action = ev.getAction();
		//
		// // In the situation where a long click is not needed to initiate a
		// drag,
		// // simply start on the down event.
		// if (action == MotionEvent.ACTION_DOWN) {
		// handledHere = startDrag(v);
		// }
		//
		// / return handledHere;
		return true;
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	public boolean onLongClick(View v) {
		if (mLongClickStartsDrag) {

			// trace ("onLongClick in view: " + v + " touchMode: " +
			// v.isInTouchMode ());

			// Make sure the drag was started by a long press as opposed to a
			// long click.
			// (Note: I got this from the Workspace object in the Android
			// Launcher code.
			// I think it is here to ensure that the device is still in touch
			// mode as we start the drag operation.)
			if (!v.isInTouchMode()) {
				// toast("isInTouchMode returned false. Try touching the view again.");
				return false;
			}
			return startDrag(v);
		}

		// If we get here, return false to indicate that we have not taken care
		// of the event.
		return false;
	}

	/**
	 * Start dragging a view.
	 * 
	 */

	public boolean startDrag(View v) {
		// Let the DragController initiate a drag-drop sequence.
		// I use the dragInfo to pass along the object being dragged.
		// I'm not sure how the Launcher designers do this.
		Object dragInfo = v;
		mDragController.startDrag(v, mDragLayer, dragInfo,
				DragController.DRAG_ACTION_COPY);
		return true;
	}

	/*
	 * 
	 * Handler cancel itell
	 */

	final Handler handlerCancel = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {

					try {
						if (mJsonAlalysis.getCode(data) != null) {
							// registerAc.changeViewLL(inputNumbe.getmView());
							//mTvHours.setText("00:");
							//mTvMinutes.setText("00:");
							//mTvSecond.setText("00");
							ItelApplication.count.setTime(0);
							ItelApplication.user.setStatusUpdateTime(0);
							tvTime.setText("00:00:00");
						} else {
							Utils.showDialogErorr(mC, "NETWORK ERORR");
							// registerAc.changeViewLL(inputNumbe.getmView());
						}

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

	/*
	 * 
	 * Handler cancel itell
	 */

	final Handler handlerPostItell = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				int total = msg.getData().getInt("total");
				String data = msg.getData().getString("data");
				boolean exeption = msg.getData().getBoolean("exeption");
				if (total >= 0) {
					if (exeption) {

						try {
							if (mJsonAlalysis.getCode(data) != null) {
								// registerAc.changeViewLL(inputNumbe.getmView());
								// mTvHours.setText("");
								// mTvMinutes.setText("00:");
								// mTvSecond.setText("00");
							} else {
								Utils.showDialogErorr(mC, "NETWORK ERORR");
								// registerAc.changeViewLL(inputNumbe.getmView());
							}

						} catch (JSONException e) {
							// Utils.showDialogServerError(json.mContext);
							e.printStackTrace();
						}
					} else {
						// Utils.showDialogServerError(json.mContext);
					}
				}
			} else if (msg.what == 0) {
				// move to map screen
//				if (BuildConfig.DEBUG) {
//					Log.i("NDT", "move to map");
//				}
				ItelApplication.count.setUpdate(true);
				mMainActivity.onClickMap.onClick(null);
				
			}
		}
	};

}
