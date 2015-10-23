package com.icts.viewcustom;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.control.SwipAdapter.OnItemSlideChangedListener;
import com.icts.control.SwipAdapter.OnItemSlideLeftListener;
import com.icts.control.SwipAdapter.OnItemSlideRightListener;
import com.icts.control.SwipListAdapter;
import com.icts.control.SwipListAdapter.ViewHolder;
import com.icts.control.SwipListView;
import com.icts.control.SwipListView.OnSetFinishSwip;
import com.icts.itel.ChatActivity;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.map.CustomItemizedOverlay;
import com.icts.map.CustomItemizedOverlay.OnClickOverlay;
import com.icts.map.CustomOverlayItem;
import com.icts.map.MyLocation;
import com.icts.map.MyLocation.LocationResult;
import com.icts.object.CompanyObject;
import com.icts.object.FriendObject;
import com.icts.utils.Constant;
import com.icts.utils.CountDownTime.UpdateTimeListViewListener;
import com.icts.utils.GeocoderTask;
import com.icts.utils.Utils;

/**
 * This class displays friends around 2km 
 * @author Luong
 *
 */
public class Map {
	private final String BACKVIEW_TAG = "Back:Activity"; 
	public static int totalFriends = 0;
	public static int totalCom = 0;
	public static int totalOther = 0;
	
	public static int currentPageFriends = 0;
	public static int currentPageCom = 0;
	public static int currentPageOther = 0;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what==0){
				if (mProgress!=null){
					mProgress.dismiss();
				}
			}
		};
	};
	
	private final int MAPZOOM =14;
	private boolean isMapMode = true;
	private View mView = null;
	private Context mContext;
	//private TextView mTxtCoin;
	//private Button mBtnOk;
	private SwipListView listView;
	
	private TextView mTvTitle;
	private ImageView btnMap;
	private ImageView btnList;
	
	private MapView mMapView;
	private ArrayList<FriendObject> mUserList = new ArrayList<FriendObject>();
	private ArrayList<CompanyObject> mComList = new ArrayList<CompanyObject>();
	private MainActivity mainActivity;
	private double lat =-1;
	private double lng = -1;
	private List<Overlay> mapOverlays;
	private Drawable maleFriendsDrawable;
	private Drawable femaleFriendsDrawable;
	private Drawable maleOtherDrawable;
	private Drawable femaleOtherDrawable;
	private CustomItemizedOverlay<CustomOverlayItem> myItemizedOverlay;
	private ArrayList<CustomItemizedOverlay<CustomOverlayItem>> list = 
				new ArrayList<CustomItemizedOverlay<CustomOverlayItem>>();
	private MapController mc;
	//private CountTime count;
	
	private ProgressDialog mProgress;
	
	//get your Location
	private MyLocation myLocation = new MyLocation();
	//Handle when click on overlay
	private OnClickOverlay<CustomOverlayItem> onClick = new OnClickOverlay<CustomOverlayItem>() {
		@Override
		public void onClickOverlayItem(CustomOverlayItem item,int index) {
			//Toast.makeText(mContext, "onOVerlayTap for overlay index "+item.getTitle()+" "+item.getSnippet(),
			//		Toast.LENGTH_LONG).show();
			if (!item.isCompanyAcc()){
				FriendObject friend = item.getFriend();
				ProfileOther profile = new ProfileOther(listView.getContext(),
						mainActivity,friend);
				mainActivity.changeViewLL(profile.getmView());
			}
		}
	};
	
	public Map(Context context, MainActivity activity,View mView) {
		mContext = context;
		mainActivity = activity;
		this.mView = mView;
		mProgress = new ProgressDialog(mContext);
		mProgress.setMessage("Loading data...");
		mProgress.show();
		initView();
		myLocation.getLocation(mContext, locationResult);
		mHandler.post(new Runnable() {
			public void run() {
				initDataMap();
			}
		});
		
	}

	public void initView() {
		listView = (SwipListView) mView.findViewById(R.id.map_lv);
		mTvTitle = (TextView) mView.findViewById(R.id.map_tv_title);
		
		btnMap = (ImageView) mView.findViewById(R.id.map_btn_map);
		btnList = (ImageView) mView.findViewById(R.id.map_btn_list);
		//mMapView = (MapView) mView.findViewById(R.id.map_mapview);
		initialMap();
		btnList.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (isMapMode){
					if (ItelApplication.count!=null){
						ItelApplication.count.addUpdateLVListener(updateLV);
					}
				
					mHandler.post(new Runnable() {
						public void run() {
							btnList.setBackgroundDrawable(mainActivity.getResources().
									getDrawable(R.drawable.map_list_button_active));
							btnMap.setBackgroundDrawable(null);
							btnMap.setBackgroundDrawable(mainActivity.getResources().
									getDrawable(R.drawable.map_map_button_selector));
							listView.setVisibility(View.VISIBLE);
							//mMapView.setVisibility(View.GONE);
							ItelApplication.holderView.setVisibility(View.GONE);
						}
					});
					initDataList(lat,lng,false);
				}
				isMapMode = false;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SwipListAdapter adapter = (SwipListAdapter)listView.getAdapter();
				FriendObject friend = adapter.getItem(arg2);
				ProfileSetting profile = new ProfileSetting(arg1.getContext(),
						mainActivity);
				mainActivity.changeViewLL(profile.getmView());
			}
		});
		btnMap.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (!isMapMode){
					if (ItelApplication.count!=null){
						ItelApplication.count.addUpdateLVListener(null);
					}
					/*ItelApplication.lastView = ItelApplication.currentView;
					ItelApplication.currentView = ViewMode.MAP;*/
					mTvTitle.setText(Constant.MAP_VIEW);
					stopCount();
					mHandler.post(new Runnable() {
						
						public void run() {
							btnMap.setBackgroundDrawable(mainActivity.getResources().
									getDrawable(R.drawable.map_map_button_active));
							btnList.setBackgroundDrawable(null);
							btnList.setBackgroundDrawable(mainActivity.getResources().
									getDrawable(R.drawable.map_list_button_selector));
							listView.setVisibility(View.GONE);
							//mMapView.setVisibility(View.VISIBLE);
							ItelApplication.holderView.setVisibility(View.VISIBLE);
						}
					});
				}
				isMapMode = true;
			}
		});
		
		btnMap.setBackgroundDrawable(mainActivity.getResources().
				getDrawable(R.drawable.map_map_button_active));
		btnList.setBackgroundDrawable(null);
		btnList.setBackgroundDrawable(mainActivity.getResources().
				getDrawable(R.drawable.map_list_button_selector));
		listView.setVisibility(View.GONE);
		//mMapView.setVisibility(View.VISIBLE);
		ItelApplication.holderView.setVisibility(View.VISIBLE);
		mTvTitle.setText(Constant.MAP_VIEW);
	}

	public void initDataMap() {
		if (ItelApplication.count!=null){
			ItelApplication.count.addUpdateLVListener(null);
		}
		mMapView.setBuiltInZoomControls(true);
		
		mapOverlays = mMapView.getOverlays();
		/*myLocation.getLocation(mContext, locationResult);*/
		maleFriendsDrawable = mContext.getResources().getDrawable(R.drawable.map_boy_friends);
		femaleFriendsDrawable = mContext.getResources().getDrawable(R.drawable.map_girl_friends);
		maleOtherDrawable = mContext.getResources().getDrawable(R.drawable.map_boy_other);
		femaleOtherDrawable = mContext.getResources().getDrawable(R.drawable.map_girl_other);
		mc = mMapView.getController();
		mc.setZoom(MAPZOOM);
	}
	
	public void removeView(){
		((ViewGroup)mView).removeView(mMapView);
	}
	
	public void initDataList(double lat, double lng, boolean getData){
		if (getData){
			mUserList.clear();
			mUserList = getFriends(lat, lng);
		}
		if (mUserList==null){
			return;
		}
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				final SwipListAdapter adapter = new SwipListAdapter(mainActivity, R.layout.map_list_item, 
															  R.id.map_list_tv_name, mUserList);
		        adapter.setOnItemSlideLeftListener(new OnItemSlideLeftListener() {
		
		            public void onItemSlideLeft(View v) {
		            	ViewHolder holder = (ViewHolder)v.getTag();
		                final TextView tvLeft =(TextView) holder.vText.findViewById(R.id.list_item_text_left);
		                if (tvLeft!=null){
		                	if (tvLeft.getVisibility()==View.VISIBLE){
		                		tvLeft.setVisibility(View.GONE);
		                	}
		                }
		                final TextView tvRight =(TextView) holder.vText.findViewById(R.id.list_item_text_right);
		                if (tvRight!=null){
		                	if (tvRight.getVisibility()!=View.VISIBLE){
		                		tvRight.setVisibility(View.VISIBLE);
		                	}
		                }
		            }
		        });
		        
		        adapter.setOnItemSlideRightListener(new OnItemSlideRightListener() {
		
		            public void onItemSlideRight(View v) {
		                ViewHolder holder = (ViewHolder)v.getTag();
		                final TextView tvLeft =(TextView) holder.vText.findViewById(R.id.list_item_text_left);
		                if (tvLeft!=null){
		                	if (tvLeft.getVisibility()!=View.VISIBLE){
		                		tvLeft.setVisibility(View.VISIBLE);
		                	}
		                }
		                final TextView tvRight =(TextView) holder.vText.findViewById(R.id.list_item_text_right);
		                if (tvRight!=null){
		                	if (tvRight.getVisibility()==View.VISIBLE){
		                		tvRight.setVisibility(View.GONE);
		                	}
		                }
		            }
		        });
		        
		        adapter.setOnItemSlideChangedListener(new OnItemSlideChangedListener() {
		            public void onItemSlideChanged(final View v, int direction) {
		            	ViewHolder holder = (ViewHolder)v.getTag();
		               
		                SwipListAdapter adapter =  (SwipListAdapter)listView.getAdapter();
		                adapter.setBusy(true);
		                TextView tv = (TextView)holder.vContent.findViewById(R.id.list_item_text_title);
		                if(direction == SwipAdapter.LEFT){
		                	holder.vMid.setVisibility(View.GONE);
			                holder.vText.setVisibility(View.GONE);
			                holder.vContent.setVisibility(View.VISIBLE);
		                    tv.setText("PROFILE");
		                    stopCount();
		                    listView.swip(listView.getPositionForView(v),direction);
		                    
		                }
		                if(direction == SwipAdapter.RIGHT){
		                	
		                	int index = listView.getPositionForView(v);
		                	FriendObject friend = adapter.getItem(index);
		                	if (!Utils.checkValidateTime(ItelApplication.user, friend)){
		                		/*final TextView tvLeft = (TextView) holder.vText
		        						.findViewById(R.id.list_item_text_left);
		        				if (tvLeft != null) {
		        					tvLeft.setVisibility(View.GONE);
		        				}
		        				final TextView tvRight = (TextView) holder.vText
		        						.findViewById(R.id.list_item_text_right);
		        				tvRight.setVisibility(View.GONE);
		                		tv.setVisibility(View.GONE);*/
		                		holder.vMid.setVisibility(View.VISIBLE);
		                		//holder.vText.setVisibility(View.GONE);
		                		String s = "itell time of friend or yours can be invalid.";
		                		Utils.showMessage(mContext, s);
		                		adapter.setBusy(false);
		                		adapter.resetViewFirstPosition(v);
		                		v.clearAnimation();
		                		v.requestLayout();
		                		return;
		                		
		                	}
		                	holder.vMid.setVisibility(View.GONE);
			                holder.vText.setVisibility(View.GONE);
			                holder.vContent.setVisibility(View.VISIBLE);
		                    tv.setText("CHAT");
		                    stopCount();
		                    listView.swip(index,direction);
		                }
		               
		            }
		        });
		        listView.setAdapter(adapter);
		        listView.setClipChildren(false);
		        listView.setClipToPadding(false);
		        listView.setDividerHeight(0);
		        listView.setFinishSwip(new OnSetFinishSwip() {
					
					@Override
					public void onFinishSwip(int direction, int index) {
						if (direction==SwipAdapter.LEFT){
							FriendObject friend = adapter.getItem(index);
							ProfileOther profile = new ProfileOther(mainActivity,
									mainActivity,friend);
							mainActivity.changeViewLL(profile.getmView());
						}
						else {
							FriendObject friend = adapter.getItem(index);
							//ChatScreen chat = new ChatScreen(lvUser.getContext(), mainActivity,friend);
							//mainActivity.changeViewLL(chat.getView());
							ItelApplication.friendChat = friend;
							Intent intent = new Intent(mainActivity,
													   ChatActivity.class);
							
							Bundle bundle = new Bundle();
							bundle.putString(BACKVIEW_TAG, Constant.LIST_VIEW);
							intent.putExtras(bundle);
							
							mainActivity.startActivity(intent);
							mainActivity.finish();
							stopCount();
						}
						
					}
				});
			}
		});
	}

	public View getView() {
		return mView;
	}

	public void setView(View mView) {
		this.mView = mView;
	}
	
	private LocationResult locationResult = new LocationResult() {
		
		@Override
		public void gotLocation(Location location) {
			if (location != null) {
				lat = location.getLatitude();
				lng = location.getLongitude();
				// debug tokyo
				//lat = 35.6894875; lng = 139.6917063999993;
				// debug ha noi
				//if (BuildConfig.DEBUG){
				//	lat = 20.986244; 
				//	lng = 105.798805;
				//}
				//mHandler.post(getInfo);
				Log.i("NDT", "lat "+lat+" long "+lng);
				Log.i("NDT", "user_id "+ItelApplication.user_id+" uuid "+ItelApplication.uuid);
				GeocoderTask geoTask = new GeocoderTask(null, lat, lng, mainActivity, 
						String.valueOf(ItelApplication.user_id), ItelApplication.uuid);
				ItelApplication.submit(geoTask);
				ItelApplication.submit(getInfo);
				//new Thread(getInfo).start();
			}
		}
	};

	//Loading data for map
	private Runnable getInfo = new Runnable() {
		public void run() {
			/*String[] info = Utils.geocoder(lat, lng, mainActivity);
			String s = String.valueOf(ItelApplication.user_id);
			try {
				if (info!=null){
					Utils.postLocation(s, 
						ItelApplication.uuid, lat, lng, info[1], info[2], info[0]);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
			String s = String.valueOf(ItelApplication.user_id);
			if (isMapMode){
				
				//get friends around
				mUserList = getFriends(lat,lng);
				mComList = Utils.getMapCompany(s,ItelApplication.uuid, lat, lng,0);
				
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// my location
						GeoPoint point2 = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
						clearMap();
						if (mc==null){
							mc = mMapView.getController();
						}
						if ((mUserList!=null)
								||(mComList!=null)){
							setMarker(mUserList,mComList);
						}
						
						mc.animateTo(point2);
						mc.setCenter(point2);
						mc.setZoom(MAPZOOM);
					}
				});
			}
			else {
				initDataList(lat, lng, true);
			}
			mHandler.sendEmptyMessage(0);
		}
	};
	
	private ArrayList<FriendObject> getFriends(double lat,double lng){
		String s = String.valueOf(ItelApplication.user_id);
		ArrayList<FriendObject> arr = Utils.getMapFriends(s,
										     ItelApplication.uuid, lat, lng,0);
		
		ArrayList<FriendObject> arr1 = Utils.getMapOthers(s,
										     ItelApplication.uuid, lat, lng,0);
		
		if (arr==null){
			if (arr1==null){
				return null;
			}
			else {
				arr = arr1;
			}
		}
		else {
			if (arr1!=null){
				arr.addAll(arr1);
			}
		}
		
		return arr;
	}
	
	private void setMarker(ArrayList<FriendObject> arr,ArrayList<CompanyObject> arrCom){
		clearMap();
		if (list!=null){
			list.clear();
		}
		else {
			list = new ArrayList<CustomItemizedOverlay<CustomOverlayItem>>();
		}
		if (arr!=null){
			for (FriendObject friend : arr) {
				GeoPoint point = new GeoPoint((int)(friend.getFriendLat()*1E6),
						  (int)(friend.getFriendLong()*1E6));
				setMarker(friend, point,false);
			}
		}
		if (arrCom!=null){
			for (CompanyObject com : arrCom) {
				GeoPoint point = new GeoPoint((int)(com.getmLat()*1E6),
						  (int)(com.getmLong()*1E6));
				setMarker(com, point);
			}
		}
		if (mapOverlays==null){
			mapOverlays = mMapView.getOverlays();
		}
		mapOverlays.addAll(list);
	}
	
	/**
	 * Create marker based on the sex of user
	 * @param user
	 */
	private void setMarker(FriendObject user,GeoPoint point, boolean isCurrent){
		CustomOverlayItem overlayItem = new CustomOverlayItem(point, user);
		if (!isCurrent){
			if (user.isMale()){
				CustomItemizedOverlay<CustomOverlayItem>  maleItemizedOverlay = null;
				if (user.isFriend()){
					if (maleFriendsDrawable==null){
						maleFriendsDrawable = mContext.getResources().getDrawable(R.drawable.map_boy_friends);
					}
					maleItemizedOverlay 
						= new CustomItemizedOverlay<CustomOverlayItem>(maleFriendsDrawable, mMapView);
					
					maleItemizedOverlay.setOnClick(onClick);
				}
				else {
					if (maleOtherDrawable==null){
						maleOtherDrawable = mContext.getResources().getDrawable(R.drawable.map_boy_other);
					}
					maleItemizedOverlay 
						= new CustomItemizedOverlay<CustomOverlayItem>(maleOtherDrawable, mMapView);
					maleItemizedOverlay.setOnClick(onClick);
				}
				maleItemizedOverlay.addOverlay(overlayItem);
				list.add(maleItemizedOverlay);
				
			}
			else {
				CustomItemizedOverlay<CustomOverlayItem> femaleItemizedOverlay =null;
				if (user.isFriend()){
					if (femaleFriendsDrawable==null){
						femaleFriendsDrawable = mContext.getResources().getDrawable(R.drawable.map_girl_friends);
					}
					femaleItemizedOverlay 
						= new CustomItemizedOverlay<CustomOverlayItem>(femaleFriendsDrawable, 
																	   mMapView);
					femaleItemizedOverlay.setOnClick(onClick);
				}
				else {
					if (femaleOtherDrawable==null){
						femaleOtherDrawable = mContext.getResources().getDrawable(R.drawable.map_girl_other);
					}
					femaleItemizedOverlay 
						= new CustomItemizedOverlay<CustomOverlayItem>(femaleOtherDrawable, 
																   mMapView);
					femaleItemizedOverlay.setOnClick(onClick);
				}
				femaleItemizedOverlay.addOverlay(overlayItem);
				list.add(femaleItemizedOverlay);
			}
		}
	}
	
	private void initialMap(){
		if (ItelApplication.holderView != null) {
			ItelApplication.holderView.removeView(ItelApplication.mapView);
		}
		if (ItelApplication.mapView == null) {
			ItelApplication.mapView = new MapView(mainActivity,
					Constant.API_MAP_KEY);
			ItelApplication.mapView.setClickable(true);
			
		} else {
			ItelApplication.mapView.getOverlays().clear();
		}
		
		ItelApplication.holderView = (LinearLayout) mView
				.findViewById(R.id.map_ll_mapview);
		RelativeLayout.LayoutParams para1 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		para1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		ItelApplication.mapView.setLayoutParams(para1);
		ItelApplication.mapView.setClickable(true);
		ItelApplication.holderView.addView(ItelApplication.mapView, 0);
		
		mMapView = ItelApplication.mapView;
	}
	/**
	 * Create marker based on the sex of user
	 * @param user
	 */
	private void setMarker(CompanyObject com,GeoPoint point){
		CustomOverlayItem overlayItem = new CustomOverlayItem(point, com);
		CustomItemizedOverlay<CustomOverlayItem>  comItemizedOverlay = null;
		if (maleFriendsDrawable==null){
				maleFriendsDrawable = mContext.getResources().getDrawable(R.drawable.map_boy_friends);
		}
		comItemizedOverlay 
			= new CustomItemizedOverlay<CustomOverlayItem>(maleFriendsDrawable, mMapView);
					
		comItemizedOverlay.setOnClick(onClick);
		comItemizedOverlay.addOverlay(overlayItem);
		list.add(comItemizedOverlay);
	}
	/**
	 * remove overlay on map 
	 */
	public void clearMap(){
		if (myLocation!=null){
			myLocation.disableTimer();
		}
		if ((mapOverlays!=null)&&
			(mMapView!=null)){
			for (Overlay overlay : mapOverlays) {
				if (overlay instanceof CustomItemizedOverlay<?>){
					((CustomItemizedOverlay<?>)overlay).setOnClick(null);
					((CustomItemizedOverlay<?>)overlay).clear();
				}
			}
			
			mapOverlays.clear();
			mMapView.invalidate();
		}
	}
	
	/**
	 * release data
	 */
	public void destroy(){
		if (myLocation!=null){
			myLocation.disableTimer();
		}
		if ((mapOverlays!=null)&&
			(mMapView!=null)){
			for (Overlay overlay : mapOverlays) {
				if (overlay instanceof CustomItemizedOverlay<?>){
					((CustomItemizedOverlay<?>)overlay).clear();
					((CustomItemizedOverlay<?>)overlay).stopOtherThread();
				}
			}
			mapOverlays.clear();
			mMapView.invalidate();
		}
		
		//Stop if running
		if (mHandler!=null){
			if (getInfo!=null){
				mHandler.removeCallbacks(getInfo);
			}
		}
		
		if (ItelApplication.count!=null){
			ItelApplication.count.addUpdateLVListener(null);
		}
	}
	
	private void stopCount(){
		if (ItelApplication.count!=null){
			ItelApplication.count.addUpdateLVListener(null);
		}
	}
	
	private UpdateTimeListViewListener updateLV = new UpdateTimeListViewListener() {
		
		@Override
		public void update(long timeServer, long minute) {
			int first = listView.getFirstVisiblePosition();
			int last = listView.getLastVisiblePosition();
			for (int i = first;i<=last;i++){
				View v = listView.getChildAt(i);
				if (v==null){
					continue;
				}
				ViewHolder holder =(ViewHolder) v.getTag();
				//SwipListAdapter adapter = (SwipListAdapter)listView.getAdapter();
				long time = Utils.calculate(timeServer, holder.time, minute);//adapter.timeServer
				if (time<=0){
					holder.tvTime.setText(Constant.THREE_HOUR+"");
				}
				else {
					long m= time/ 60000;
					holder.tvTime.setText(String.valueOf(m));
				}
			}
			
		}
	};
	
}
