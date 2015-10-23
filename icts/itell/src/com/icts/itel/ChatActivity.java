package com.icts.itel;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icts.adapter.ChatAdapter;
import com.icts.adapter.StampAdapter;
import com.icts.adapter.StampCategoryAdapter;
import com.icts.adapter.StampCategoryAdapter.OnCatSelectedListener;
import com.icts.adapter.StampCategoryAdapter.ViewHolder;
import com.icts.app.ItelApplication;
import com.icts.control.CirclePageIndicator;
import com.icts.control.DirectionalViewPager;
import com.icts.control.StampFragment;
import com.icts.control.StampFragment.OnClickStampImage;
import com.icts.object.FriendObject;
import com.icts.object.MessageObject;
import com.icts.object.StampCatObject;
import com.icts.object.StampObject;
import com.icts.object.User;
import com.icts.socket.ChatCallbackAdapter;
import com.icts.socket.ChatConnect;
import com.icts.socket.IOAcknowledge;
import com.icts.socket.SocketIOException;
import com.icts.utils.Constant;
import com.icts.utils.Constant.ViewMode;
import com.icts.utils.CountDownTime.ChangeMenuListener;
import com.icts.utils.CountDownTime.TimeOutListener;
import com.icts.utils.CountDownTime.UpdateTimeListener;
import com.icts.utils.CountTime4Textview;
import com.icts.utils.DatabaseAdapter;
import com.icts.utils.ImageCache;
import com.icts.utils.NewMessageCache;
import com.icts.utils.Utils;
import com.icts.viewcustom.StatusDialog;

public class ChatActivity extends FragmentActivity {
	private int mCurrentPosition = 0;
	private final int CHAT_CHANGE_MENU = 4;
	private final int CHAT_NEW_MESSAGE = 3;
	private final int CHAT_STAMP = 1;
	private final int CHAT_DISABLE = 2;
	private final int CHAT_ITELL_UPDATE = 0;
	private enum ChatEvent {LOGIN,LOGOUT,JOIN,SEND_MESSAGE,NULL};
	//Stamp
	private ArrayList<StampObject> stampArr = new ArrayList<StampObject>();
	private ArrayList<StampCatObject> catStampArr = new ArrayList<StampCatObject>();
	
	private ChatConnect chatConnect;
	private final String BACKVIEW_TAG = "Back:Activity"; 
	private CountTime4Textview countDown;
	private RelativeLayout btnFriend;
	private Button btnSetting;
	private Button btnItell;
	private Button btnMap;
	private RelativeLayout btnChat;
	private LinearLayout llMain;
	private TextView tvNewChat;
	private TextView tvNewFriendInvite;
	
	private DatabaseAdapter database;
	private LayoutInflater layoutInflater;
	private View mView = null;
	//back to chat
	private ImageView btnBack;
	private ImageView btnStore;
	private ImageView btnList;
	
	//Time
	/*private TextView mTvHour;
	private TextView mTvMinute;
	private TextView mTvSecond;*/
	private TextView tvTime;
	
	//Icon itell
	private ImageView imgItell;	
	//Chat
	private LinearLayout llChat;
	private ListView lvChat;
	private LinearLayout llChatBottom;
	
	private LinearLayout llChatStamp;
	private ImageButton btnSend;
	private ImageButton btnChangeEmotion;
	private EditText edMessage;
	private ListView lvChatStampCat;
	private ProgressDialog progress;
	
	private DirectionalViewPager pager;
	private CirclePageIndicator mIndicator;
	private OnCatSelectedListener onCatItemSelected = new OnCatSelectedListener() {
		
		@Override
		public void onCatSelected(View v,int old,Bitmap bitmap) {
			int position = Integer.parseInt(v.getTag().toString());
			if (position!=mCurrentPosition){
				mCurrentPosition = position;
				if (bitmap!=null){
					View vi= lvChatStampCat.getChildAt(old-lvChatStampCat.getFirstVisiblePosition());
					ViewHolder holder =(ViewHolder) vi.getTag();
					holder.imgCategory.setImageBitmap(null);
					holder.imgCategory.setImageBitmap(bitmap);
				}
				
				StampFragment frag =(StampFragment) adapterStamp.getActiveFragment(pager, mCurrentPage);
				StampCatObject cat = catStampArr.get(position);
				if (cat!=null){
					stampArr = cat.getArrStamps();
					ArrayList<StampObject[]> arr = createStampArray(stampArr);
					frag.updateInfo(frag.getView(), arr.get(0));
					adapterStamp.setData(arr);
					adapterStamp.notifyDataSetChanged();
					mIndicator.notifyDataSetChanged();
				/*	for (int i = 0;i<adapterStamp.getCount();i++){
						Fragment frag = adapterStamp.getItem(i);
						adapterStamp.destroyItem(pager, i, frag);
					}
					
					
					adapterStamp.removeAll();
					adapterStamp.startUpdate(pager);
					pager.requestLayout();
					stampArr = cat.getArrStamps();
					ArrayList<StampObject[]> arr = createStampArray(stampArr);
					
					adapterStamp.setData(arr);
					adapterStamp.startUpdate(pager);
					pager.requestLayout();
					StampFragment frag =(StampFragment) adapterStamp.getItem(0);
						frag.onCreateView(layoutInflater, pager, null);
						//frag.updateInfo(frag.getView());
					//mIndicator.notifyDataSetChanged();
					//pager.dataSetChanged();
					initStampView(cat);
					adapterStamp.notifyDataSetChanged();
					adapterStamp.startUpdate(pager);
					StampFragment stampFrag = (StampFragment)frag;
					stampFrag.updateInfo(ChatActivity.this);*/
				}
			}
			//Toast.makeText(ChatActivity.this, " "+position, Toast.LENGTH_SHORT).show();
		}
	};
	
	
	private ChangeMenuListener changeMenuListener = new ChangeMenuListener() {
		@Override
		public void changeMenu(long frame) {
			if (ItelApplication.user.itellPolicy==Constant.POLICY_ALL){
				btnItell.setBackgroundDrawable(getResources().getDrawable(Constant.ALL_ITELL_BUTTON_IMAGES[(int)frame]));
			}
			else if (ItelApplication.user.itellPolicy==Constant.POLICY_FRIEND){
				btnItell.setBackgroundDrawable(getResources().getDrawable(Constant.FRIEND_ITELL_BUTTON_IMAGES[(int)frame]));
			}
			else if (ItelApplication.user.itellPolicy==Constant.POLICY_OTHER){
				btnItell.setBackgroundDrawable(getResources().getDrawable(Constant.OTHER_ITELL_BUTTON_IMAGES[(int)frame]));
			}
			else{
				btnItell.setBackgroundDrawable(getResources().getDrawable(Constant.ALL_ITELL_BUTTON_IMAGES[29]));
			}
		}
	}; 
	private OnClickStampImage onClickStampItem = new OnClickStampImage() {
		
		@Override
		public void onClickStampImage(StampObject stamp) {
			if (disable){
				showMessageAlert();
				return;
			}
			MessageObject msgObj = new MessageObject();
			msgObj.setStamp(true);
			//msgObj.setMessage(Constant.STAMP_TAG+stamp.getCode());
			msgObj.setMessage(stamp.getCode());
			
			msgObj.setTimestamp(Utils.getTimeStamp());
			msgObj.setSenderID(String.valueOf(ItelApplication.user_id));
			if (mFriend!=null){
				msgObj.setReceiverID(String.valueOf(mFriend.getFriendID()));
			}
			else if (mUser!=null){
				msgObj.setReceiverID(mUser.getUserId());
			}
			ImageCache.putStamp(stamp);
			Message msg = Message.obtain();
			msg.what = CHAT_NEW_MESSAGE;
			msg.obj = msgObj;
			
			/*chatConnect.sendMessage(String.valueOf(ItelApplication.user_id),
					msgObj.getReceiverID(), Constant.STAMP_TAG+msgObj.getMessage());*/
			chatConnect.sendMessage(String.valueOf(ItelApplication.user_id),
					msgObj.getReceiverID(), msgObj.getMessage());
			
			mHandler.sendMessage(msg);
			//Toast.makeText(ChatActivity.this, stamp.getCode()+" "+stamp.getImageLink(), Toast.LENGTH_LONG).show();
		}
	};
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CHAT_ITELL_UPDATE:
				ItelApplication.count.setUpdate(true);
				break;
			case CHAT_CHANGE_MENU:
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						
						displayNumberInMenu(
								NewMessageCache.getCountNewMessage(), true);
					}
				});
				break;
			case CHAT_STAMP:
				if (progress!=null){
					progress.dismiss();
				}
				//Utils.animation(mView,500);
				if (catStampArr!=null){
					if (catStampArr.size()>0){
				        StampCategoryAdapter adapterCat = new StampCategoryAdapter(ChatActivity.this, catStampArr);
				        adapterCat.setListener(onCatItemSelected);
				        lvChatStampCat.setAdapter(adapterCat);
						initStampView(catStampArr.get(0));
					}
				}
				
				break;
			case CHAT_DISABLE:
				if (ItelApplication.count!=null){
					ItelApplication.count.clearTimeOutListener();
					ItelApplication.count.clearUpdateTimeListener();
				}
				showMessageAlert();
				break;
			case CHAT_NEW_MESSAGE:
				addMessage(msg);
				break;
			default:
				break;
			};
		};
	};
	private ArrayList<MessageObject> arrMessage= new ArrayList<MessageObject>();
	private ChatAdapter adapter;
	private TextView tvTitleUseName;
	private boolean mStampView = false;
	private User mUser;
	private FriendObject mFriend;
	private String backView = null;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		progress = ProgressDialog.show(this, null,getString(R.string.message_loading));
		ItelApplication.lastView = ItelApplication.currentView;
		ItelApplication.currentView = ViewMode.CHAT_LIST;
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		Constant.SCREEN_WIDTH = displaymetrics.widthPixels;
		Constant.SCREEN_HEIGHT = displaymetrics.heightPixels;
		
		//Get friends object
		mFriend = ItelApplication.friendChat;
		
		//Get backView
		Bundle b = getIntent().getExtras();
		if (b!=null){
			backView = b.getString(BACKVIEW_TAG);
		}
		
		setContentView(R.layout.main);
		initView();
		
		//Set up the pager
		//initStampView();
        initData();
        setEvent();
        //initSocket(chatCallBackApdapter);
        chatConnect = ItelApplication.getChatConnect();
        
        if (chatConnect==null){//||!chatConnect.isAlive()||!chatConnect.getSocket().isConnected()
        	new Thread(){
        		public void run() {
        			ItelApplication.initSocket(chatCallBackApdapter);
        			chatConnect = ItelApplication.getChatConnect();
        		};
        	}.start();
        }
        else {
        	ItelApplication.setAdapter(chatCallBackApdapter);
        }
        new Thread(getStamp).start();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		InputMethodManager imm = (InputMethodManager) this.getSystemService(
				Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		}
	}
	
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
				disable = true;
				mHandler.sendEmptyMessage(CHAT_DISABLE);
			}
		}
	};
	private void showMessageAlert(){
		if (disable){
			Toast.makeText(ChatActivity.this,"Please click itell to continue to chat", Toast.LENGTH_LONG).show();
		}
	}
	private int mCurrentPage = 0;
	private boolean disable = false;
	private StampAdapter adapterStamp;
	private void initStampView(StampCatObject cat){
		stampArr = cat.getArrStamps();
		if (pager==null){
			pager = (DirectionalViewPager)findViewById(R.id.chat_pager);
		}
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				mCurrentPage = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        ArrayList<StampObject[]> arr = createStampArray(stampArr);
        adapterStamp = new StampAdapter(getSupportFragmentManager(), arr);
        adapterStamp.setOnClickStampImage(onClickStampItem);
        pager.setOrientation(DirectionalViewPager.VERTICAL);
        pager.setAdapter(adapterStamp);
        //adapterStamp.getItem(0);
        //pager.invalidate();
        mIndicator = (CirclePageIndicator)findViewById(R.id.chat_indicator);
        mIndicator.setOrientation(LinearLayout.VERTICAL);
        mIndicator.setViewPager(pager);
	}
	
	private ArrayList<StampObject[]> createStampArray(ArrayList<StampObject> arrStamp){
		ArrayList<StampObject[]> arr = new ArrayList<StampObject[]>();
        int numberOfPage =(int) Math.ceil((double)stampArr.size()/Constant.NUMBER_STAMP_IN_PAGE);
        for (int k =0;k<numberOfPage;k++){
	        StampObject[] s = new StampObject[Constant.NUMBER_STAMP_IN_PAGE];
	        //int num = stampArr.size() - k*Constant.NUMBER_STAMP_IN_PAGE;
	        for (int i = 0;i<Constant.NUMBER_STAMP_IN_PAGE;i++){
	        	int index = i+k*Constant.NUMBER_STAMP_IN_PAGE;
	        	if (index>=stampArr.size()){
	        		break;
	        	}
	        	s[i] = stampArr.get(index);
	        }
	        arr.add(s);
        }
        return arr;
	}
	private void addMessage(Message msg){
		MessageObject message =(MessageObject) msg.obj;
		arrMessage.add(message);
		adapter.notifyDataSetChanged();
		lvChat.setSelection(arrMessage.size()-1);
		lvChat.invalidate();
		
		//Add to database
		if (database ==null){
			database =  new DatabaseAdapter(ChatActivity.this);
		}
		if (database!=null){
			database.open();
			if (mUser!=null){
				database.createUser(mUser);
			}
			if (mFriend!=null){
				database.createUser(mFriend);
			}
			
			database.createMessage(message);
			database.close();
		}
	}
	private Runnable getStamp = new Runnable() {
		@Override
		public void run() {			
			//stampArr = Utils.loadResource("stamps.json", ChatActivity.this);
			catStampArr = Utils.loadResource1("stamps_1.json", ChatActivity.this);
			/*if (catStampArr!=null){
				if (catStampArr.size()>0){
					stampArr = catStampArr.get(0).getArrStamps();
				}
			}*/
			//stampArr = Utils.getStamp(String.valueOf(ItelApplication.user_id), ItelApplication.uuid, 0, true);
			mHandler.sendEmptyMessage(CHAT_STAMP);
		}
	};
	private void clickItell(){
		String name = "Friend";
		if (ItelApplication.user.itellPolicy==Constant.POLICY_ALL){
			name = "All User";
		}
		else if (ItelApplication.user.itellPolicy==Constant.POLICY_OTHER){
			name = "Other";
		}
		StatusDialog dialog = new StatusDialog(ChatActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar, name,
				mHandler);
		dialog.show();
	}
	private void initView() {
		btnChat = (RelativeLayout) findViewById(R.id.menu_button_chat);
		btnSetting = (Button) findViewById(R.id.menu_button_setting);
		btnMap = (Button) findViewById(R.id.menu_button_map);
		btnItell = (Button) findViewById(R.id.menu_button_iTell);
		btnFriend = (RelativeLayout) findViewById(R.id.menu_button_friend);
		llMain = (LinearLayout) findViewById(R.id.main_llmain);
		tvNewChat = (TextView) findViewById(R.id.menu_chat_tv_number);
		tvNewFriendInvite = (TextView) findViewById(R.id.menu_friend_tv_number);
		
		layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.chat_layout, null);
		changeViewLL(mView);
		if (mView!=null){
			//Title
			tvTitleUseName = (TextView) mView.findViewById(R.id.chat_tv_acc);
			btnBack = (ImageView)mView.findViewById(R.id.chat_btn_back);
			btnList = (ImageView)mView.findViewById(R.id.chat_btn_list);
			btnStore = (ImageView)mView.findViewById(R.id.chat_btn_stamp_store);
			
			tvTime =(TextView) mView.findViewById(R.id.chat_tv_time);
			//Change image using policy
			imgItell = (ImageView)mView.findViewById(R.id.chat_img_itell);
			imgItell.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					clickItell();
				}
			});
			//Chat
			llChat = (LinearLayout) mView.findViewById(R.id.chat_ll_content);
			lvChat = (ListView) mView.findViewById(R.id.chat_lv_content);
			llChatBottom = (LinearLayout) mView.findViewById(R.id.chat_ll_bottom);
			
			llChatStamp = (LinearLayout) mView.findViewById(R.id.chat_ll_stamp);
			btnSend = (ImageButton) mView.findViewById(R.id.chat_btn_send);
			btnChangeEmotion = (ImageButton) mView.findViewById(R.id.chat_btn_change_emotion);
			edMessage = (EditText) mView.findViewById(R.id.chat_ed_message);
			
			lvChatStampCat = (ListView) mView.findViewById(R.id.chat_lv_stamp_category);
			//chatPager = (DirectionalViewPager) mView.findViewById(R.id.chat_pager);
			//chatCircleIndocator = (CirclePageIndicator) mView.findViewById(R.id.chat_indicator);
		}
		
		if (!ItelApplication.count.hasChangeMenuListener(changeMenuListener)){
			ItelApplication.count.clearChangeMenuListener();
			ItelApplication.count.addChangeMenuListener(changeMenuListener);
		}

		ItelApplication.count.clearTimeOutListener();
		ItelApplication.count.clearUpdateTimeListener();
        ItelApplication.count.addUpdateTimeListener(update);
        ItelApplication.count.addTimeOutListener(tOut);
        
		long frame = ItelApplication.count.getFrame();
		if (ItelApplication.user!=null){
			if (ItelApplication.user.itellPolicy==Constant.POLICY_ALL){
				btnItell.setBackgroundDrawable(getResources().getDrawable(Constant.ALL_ITELL_BUTTON_IMAGES[(int)frame]));
			}
			else if (ItelApplication.user.itellPolicy==Constant.POLICY_FRIEND){
				btnItell.setBackgroundDrawable(getResources().getDrawable(Constant.FRIEND_ITELL_BUTTON_IMAGES[(int)frame]));
			}
			else if (ItelApplication.user.itellPolicy==Constant.POLICY_OTHER){
				btnItell.setBackgroundDrawable(getResources().getDrawable(Constant.OTHER_ITELL_BUTTON_IMAGES[(int)frame]));
			}
			else {
				btnItell.setBackgroundDrawable(getResources().getDrawable(Constant.ALL_ITELL_BUTTON_IMAGES[29]));
			}
		}
		else {
			btnItell.setBackgroundDrawable(getResources().getDrawable(Constant.ALL_ITELL_BUTTON_IMAGES[29]));
		}
	}
	
	//add View
	private void changeViewLL(View view) {
		llMain.removeAllViews();
		LayoutParams pParrams = new LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		llMain.addView(view, pParrams);
		Utils.animation(view,500);
	}
	
	private void initData() {
		database = new DatabaseAdapter(this);
		database.open();
		String friendId = null;
		if (mUser!=null){
			friendId = mUser.getUserId();
			database.createUser(mUser);
			if (mUser.getUserNick()!=null){
				tvTitleUseName.setText(mUser.getUserNick());
			}
		}
		
		if (mFriend!=null){
			database.createUser(mFriend);
			friendId = String.valueOf(mFriend.getFriendID());
			if (mFriend.getFriendNick()!=null){
				tvTitleUseName.setText(mFriend.getFriendNick());
			}
		}
		if (friendId!=null){
			arrMessage = database.getMessages(friendId,String.valueOf(ItelApplication.user_id));
		}
		database.close();
		llChatStamp.setVisibility(View.GONE);
		btnChangeEmotion.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mStampView){
					btnChangeEmotion.setSelected(false);
					llChatStamp.setVisibility(View.GONE);
					btnSend.setEnabled(true);
					edMessage.setEnabled(true);
				}
				else {
					btnChangeEmotion.setSelected(true);
					llChatStamp.setVisibility(View.VISIBLE);
					btnSend.setEnabled(false);
					edMessage.setEnabled(false);
				}
				mStampView = !mStampView;
			}
		});
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (disable){
					showMessageAlert();
					return;
				}
				String message = edMessage.getText().toString();
				if (message==null){
					return;
				}
				if (message.equalsIgnoreCase("")){
					return;
				}
				//Save database
				String friendId = null;
				if (mUser!=null){
					friendId = mUser.getUserId();
				}
				if (mFriend!=null){
					friendId = String.valueOf(mFriend.getFriendID());
				}
				MessageObject m = new MessageObject(friendId,
													String.valueOf(ItelApplication.user_id), message);
				m.setStamp(false);
				chatConnect.sendMessage(String.valueOf(ItelApplication.user_id),
										friendId, message);
				m.setTimestamp(Utils.getTimeStamp());
				arrMessage.add(m);
				adapter.notifyDataSetChanged();
				lvChat.invalidate();
				int pos = arrMessage.size()-1;
				lvChat.setSelection(pos);
				//Add to database
				if (database ==null){
					database =  new DatabaseAdapter(ChatActivity.this);
				}
				if (database!=null){
					database.open();
					if (mUser!=null){
						database.createUser(mUser);
					}
					if (mFriend!=null){
						database.createUser(mFriend);
					}
					
					database.createMessage(m);
					database.close();
				}
					
				edMessage.setText("");
				Utils.hideSoftKeyboard(ChatActivity.this, v);
			}
		});
		btnList.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				onClickChat.onClick(v);
			}
		});
		
		btnBack.setOnClickListener(onBackClick);
		//createDataTest();
		Utils.sortArrayList(arrMessage);
		adapter = new ChatAdapter(this, arrMessage, mFriend, 
								  String.valueOf(ItelApplication.user_id));
		lvChat.setAdapter(adapter);
		lvChat.setFocusable(false);
		lvChat.setClipChildren(false);
		lvChat.setClipToPadding(false);
		lvChat.setDividerHeight(0);
		lvChat.setFocusableInTouchMode(false);
		lvChat.setClickable(false);
		lvChat.setDrawSelectorOnTop(false);
		if (arrMessage.size()>1){
			lvChat.setSelection(arrMessage.size()-1);
		}
		
		if (ItelApplication.user.itellPolicy==Constant.POLICY_ALL){
			imgItell.setImageBitmap(null);
			imgItell.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(R.drawable.chat_orange_button)));
			//imgItell.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(R.drawable.chat_pink_button)));
		}
		else if (ItelApplication.user.itellPolicy==Constant.POLICY_FRIEND){
			imgItell.setImageBitmap(null);
			imgItell.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(R.drawable.chat_pink_button)));
		}
		else if (ItelApplication.user.itellPolicy==Constant.POLICY_OTHER){
			imgItell.setImageBitmap(null);
			imgItell.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(R.drawable.chat_blue_button)));
			//imgItell.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(R.drawable.chat_pink_button)));
		}
		else {
			imgItell.setImageBitmap(null);
			imgItell.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(R.drawable.chat_orange_button)));
			//imgItell.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(R.drawable.chat_pink_button)));
		}
	}
	
	/**
	 * Set Event
	 */
	private void setEvent() {
		btnChat.setOnClickListener(onClickChat);
		btnFriend.setOnClickListener(onClickFriend);
		btnItell.setOnClickListener(onClickItell);
		btnMap.setOnClickListener(onClickMap);
		btnSetting.setOnClickListener(onClickSetting);
	}

	private OnClickListener onBackClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			if (backView.equalsIgnoreCase(Constant.LIST_VIEW)){
				onClickMap.onClick(v);
			}
			else if (backView.equalsIgnoreCase(Constant.MAP_VIEW)){
				onClickMap.onClick(v);
			}
			else {
				onClickChat.onClick(v);
			}
		}
	};
	private OnClickListener onClickChat = new OnClickListener() {
		public void onClick(View v) {
			btnFriend.setBackgroundResource(R.drawable.menu_friend_button_selector);
			btnChat.setBackgroundResource(R.drawable.menu_chat_button_active);
			btnSetting.setBackgroundResource(R.drawable.menu_setting_button_selector);
			btnMap.setBackgroundResource(R.drawable.menu_map_button_selector);
			if (countDown!=null){
				countDown.cancel();
			}
			Intent i = new Intent(ChatActivity.this,MainActivity.class);
			Bundle b = new Bundle();
			b.putString("Activity", Constant.CHAT_VIEW);
			i.putExtras(b);
			startActivity(i);
			finish();
			ItelApplication.currentView = ViewMode.CHAT;
		}
	};
	private OnClickListener onClickFriend = new OnClickListener() {
		public void onClick(View v) {
			btnFriend.setBackgroundResource(R.drawable.menu_friend_button_active);
			btnChat.setBackgroundResource(R.drawable.menu_chat_button_selector);
			btnSetting.setBackgroundResource(R.drawable.menu_setting_button_selector);
			btnMap.setBackgroundResource(R.drawable.menu_map_button_selector);
			if (countDown!=null){
				countDown.cancel();
			}
			Intent i = new Intent(ChatActivity.this,MainActivity.class);
			Bundle b = new Bundle();
			b.putString("Activity", Constant.FRIEND_VIEW);
			i.putExtras(b);
			startActivity(i);
			finish();

		}
	};
	private OnClickListener onClickSetting = new OnClickListener() {
		public void onClick(View v) {
			btnFriend.setBackgroundResource(R.drawable.menu_friend_button_selector);
			btnChat.setBackgroundResource(R.drawable.menu_chat_button_selector);
			btnSetting.setBackgroundResource(R.drawable.menu_setting_button_active);
			btnMap.setBackgroundResource(R.drawable.menu_map_button_selector);
			if (countDown!=null){
				countDown.cancel();
			}
			Intent i = new Intent(ChatActivity.this,MainActivity.class);
			Bundle b = new Bundle();
			b.putString("Activity", Constant.SETTING_VIEW);
			i.putExtras(b);
			startActivity(i);
			finish();
			ItelApplication.currentView = ViewMode.SETTING;
		}
	};
	private OnClickListener onClickMap = new OnClickListener() {
		public void onClick(View v) {
			btnFriend.setBackgroundResource(R.drawable.menu_friend_button_selector);
			btnChat.setBackgroundResource(R.drawable.menu_chat_button_selector);
			btnSetting.setBackgroundResource(R.drawable.menu_setting_button_selector);
			btnMap.setBackgroundResource(R.drawable.menu_map_button_active);
			if (countDown!=null){
				countDown.cancel();
			}
			Intent i = new Intent(ChatActivity.this,MainActivity.class);
			Bundle b = new Bundle();
			b.putString("Activity", Constant.MAP_VIEW);
			i.putExtras(b);
			startActivity(i);
			finish();
			ItelApplication.currentView = ViewMode.MAP;

		}
	};
	private OnClickListener onClickItell = new OnClickListener() {
		public void onClick(View v) {
			btnFriend.setBackgroundResource(R.drawable.menu_friend_button_selector);
			btnChat.setBackgroundResource(R.drawable.menu_chat_button_selector);
			btnSetting.setBackgroundResource(R.drawable.menu_setting_button_selector);
			btnMap.setBackgroundResource(R.drawable.menu_map_button_selector);
			if (countDown!=null){
				countDown.cancel();
			}
			Intent i = new Intent(ChatActivity.this,MainActivity.class);
			Bundle b = new Bundle();
			b.putString("Activity", Constant.ITELL_VIEW);
			i.putExtras(b);
			startActivity(i);
			finish();
			ItelApplication.currentView = ViewMode.ITELL;
		}
	};

	public void displayNumberInMenu(int number, boolean newChat) {
		if (newChat) {
			if (number > 0) {
				tvNewChat.setVisibility(View.VISIBLE);
				tvNewChat.setText(String.valueOf(number));
			} else {
				tvNewChat.setVisibility(View.GONE);
				tvNewChat.setText("0");
			}
		} else {
			if (number > 0) {
				tvNewFriendInvite.setVisibility(View.VISIBLE);
				tvNewFriendInvite.setText(String.valueOf(number));
			} else {
				tvNewFriendInvite.setVisibility(View.GONE);
				tvNewFriendInvite.setText("0");
			}

		}
	}
	private boolean join = false;
	private ChatEvent currentEvent = ChatEvent.JOIN;
	private ChatCallbackAdapter chatCallBackApdapter = new ChatCallbackAdapter() {
		
		@Override
		public void onMessage(JSONObject json) {
			Log.i("NDT", "on message 1 "+ json.toString());
		}
		
		@Override
		public void onMessage(String message) {
			Log.i("NDT", "on message 2 "+ message);
		}
		
		@Override
		public void onDisconnect() {
			currentEvent = ChatEvent.NULL;
			Log.i("NDT", "on Disconnect ");
			join = false;
		}
		
		@Override
		public void onConnectFailure(SocketIOException socketIOException) {
			
		}
		
		@Override
		public void onConnect() {
			if (!join){
				currentEvent = ChatEvent.JOIN;
				chatConnect.joinServer(String.valueOf(ItelApplication.user_id),
								   ItelApplication.uuid);
			}
			join = true;
		}
		
		@Override
		public void on(String event, String data) {
			if (event.equalsIgnoreCase("message")){
				try{
					JSONObject json = new JSONObject(data);
	            	final MessageObject message = Utils.getMessage(json);
	            	message.setTimestamp(json.getLong("timestamp"));
	            	if (!checkNewMessage(message)){
	            		return;
	            	}
	            	NewMessageCache.addMessageToCache(message, 
	            									  String.valueOf(ItelApplication.user_id));
	//            	if (BuildConfig.DEBUG){
	//            		Log.i("NDT","new message "+NewMessageCache.getCountNewMessage()+" "+message.getMessage());
	//            	}
	            	
	            	mHandler.sendMessage(mHandler.obtainMessage(CHAT_NEW_MESSAGE, message));
				}
				catch(Exception ex){
					Log.i("NDT","error on "+ex.getMessage());
				}
            }
		}
		
		@Override
		public void on(String event, JSONObject data) {
			try{
				if (event.equals("logout")){
	            	String user = data.getString("user");
	            	if (user.equalsIgnoreCase(String.valueOf(ItelApplication.user_id))){
	            		join = false;
	            		chatConnect.getSocket().reconnect();
	            	}
	            }
	            else if (event.equalsIgnoreCase("message")){
	            	if (data.has("type")){
						String type = data.optString("type");
						String sendID = data.optString("user_id");
						if (type.equalsIgnoreCase(Constant.INVITE)){
							NewMessageCache.add2InviteCache(data, String.valueOf(ItelApplication.user_id));
							//NewMessageCache.getInviteCount(data);
							if (ItelApplication.currentView!=ViewMode.FRIEND){
								final int count;
								if (sendID==null){
									count = NewMessageCache.getInvite();
								}
								else {
									count = NewMessageCache.getCountInvite();
								}
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										displayNumberInMenu(count, false);
									}
								});
							}
							return;
						}
					}
	            	final MessageObject message = Utils.getMessage(data);
	            	message.setTimestamp(data.getLong("timestamp"));
	            	if (!checkNewMessage(message)){
	            		if (!message.getSenderID().equals(String.valueOf(mFriend.getFriendID()))){
	            			if (message.getReceiverID().equals(String.valueOf(ItelApplication.user_id))){
	            				NewMessageCache.addMessageToCache(message, 
		            									  String.valueOf(ItelApplication.user_id));
	            				mHandler.sendMessage(mHandler.obtainMessage(CHAT_CHANGE_MENU, message));
	            			}
		            	}
	            		return;
	            	}
	            	
//	            	if (BuildConfig.DEBUG){
//	            		Log.i("NDT","new message "+NewMessageCache.getCountNewMessage()+" "+message.getMessage());
//	            	}
	            	
	            	mHandler.sendMessage(mHandler.obtainMessage(CHAT_NEW_MESSAGE, message));
	            }
			}
			catch(Exception ex){
				Log.i("NDT","error on "+ex.getMessage());
			}
		}
		
		
		
		@Override
		public void callback(JSONArray data) throws JSONException {
			if (join){
				if (currentEvent==ChatEvent.SEND_MESSAGE){
					
				}
				Log.i("NDT", "callback1 join" + data.toString());
			}
			else {
				Log.i("NDT", "callback1 " + data.toString());
			}
		}
	};
	
	private void initSocket(ChatCallbackAdapter chatCallBackAdapter){
		boolean c = Utils.getServerInfo(ItelApplication.user_id, ItelApplication.uuid);
		String s = ItelApplication.server;
		if (s!=null){
			s = "http://"+s.trim();
			chatConnect = new ChatConnect(chatCallBackAdapter,s);
			chatConnect.start();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (chatConnect!=null){
			chatConnect.logout();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			chatConnect.logout();
			onBackClick.onClick(null);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private boolean checkNewMessage(MessageObject  object){
		String friendID = String.valueOf(mFriend.getFriendID());
		String receiverID = object.getReceiverID();
		String senderID = object.getSenderID();
		String mUserID = String.valueOf(ItelApplication.user_id);
		if ((friendID.equalsIgnoreCase(receiverID)&&mUserID.equalsIgnoreCase(senderID))||
			(friendID.equalsIgnoreCase(senderID)&&mUserID.equalsIgnoreCase(receiverID))){
			return true;
		}
		return false;
	}
	
	private IOAcknowledge ackSendMessage = new IOAcknowledge() {
		@Override
		public void ack(Object... args) {
			JSONArray jsonArr=  new JSONArray(Arrays.asList(args));
			JSONObject json = jsonArr.optJSONObject(0);
			boolean ret;
			try {
				ret = json.getBoolean("retval");
				if (ret){
					String message = json.getString("msg");
					String senderID = json.getString("sender");
					long time = json.getLong("timestamp");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	};
}
