package com.icts.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icts.app.ItelApplication;
import com.icts.itel.R;
import com.icts.object.FriendObject;
import com.icts.object.MessageObject;
import com.icts.utils.Constant;
import com.icts.utils.CountDownTime;
import com.icts.utils.DownloadImageTask;
import com.icts.utils.GeocoderTask;
import com.icts.utils.NewMessageCache;
import com.icts.utils.Utils;

/**
 * Adapter for list view in map and chat view
 * @author Luong
 *
 */
public class SwipListAdapter extends SwipAdapter<FriendObject>{
	public class ViewHolder{
		public TextView tvNewMessage;
		public ImageView imgAvatar;
		public ImageView imgLocationIcon;
		public EllipsizingTextView tvName;
		public EllipsizingTextView tvAddress;
		public EllipsizingTextView tvStatus;
		public EllipsizingTextView tvTime;
		//public View vRight;
		public View vMid;
		public View vText;
		public View vContent;
		public long time;
	}
	private ExecutorService executor;
	public boolean isChat = false;
	public Handler mHandler = null;
	//Resource for background 
	public final static Integer[] imageInnerResIds = new Integer[] {
         R.drawable.list_inner_tab_1, R.drawable.list_inner_tab_2, R.drawable.list_inner_tab_3,
         R.drawable.list_inner_tab_4, R.drawable.list_inner_tab_5, R.drawable.list_inner_tab_6};
	public final static Integer[] imageOutResIds = new Integer[] {
        R.drawable.list_outer_tab_1, R.drawable.list_outer_tab_2, R.drawable.list_outer_tab_3,
        R.drawable.list_outer_tab_4, R.drawable.list_outer_tab_5, R.drawable.list_outer_tab_6};

	private ArrayList<FriendObject> arr;
	private Context mContext;
	private Map<String, List<MessageObject>> messageArr = null;
    public SwipListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        mContext = context;
    }

    public SwipListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mContext = context;
    }

    public CountDownTime coundown;
    public long timeServer;
    public SwipListAdapter(Context context, int resource, int textViewResourceId,
            ArrayList<FriendObject> objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
        this.arr = objects;
        executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
    }
    
    public void setNewMessagesArray(Map<String, List<MessageObject>> arr){
    	messageArr = arr;
    }
    
    public void addNewMessage(String receiverID,MessageObject message){
    	//NewMessageCache.addMessageToCache(message, receiverID, messageArr);
    	if (message.getReceiverID().equals(receiverID)){
    		notifyDataSetChanged();
    		notifyDataSetInvalidated();
    	}
    }
    
    public void clearMessage(String id){
    	if (messageArr!=null){
    		if (messageArr.containsKey(id)){
    			messageArr.remove(id);
			}
    	}
    }
    public SwipListAdapter(Context context, int resource, int textViewResourceId,
            ArrayList<FriendObject> objects,Handler handler) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
        this.arr = objects;
    }

    @Override
    public int getCount() {
    	if (arr==null){
    		return 0;
    	}
    	return arr.size();
    }

   
    @Override
    public FriendObject getItem(int position) {
    	if (arr==null){
    		return null;
    	}
    	if (position<0||position>=arr.size()){
    		return null;
    	}
    	return arr.get(position);
    }
    
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ViewHolder holder;
        if (v==null){
        	LayoutInflater inflater = LayoutInflater.from(mContext); 
			convertView = inflater.inflate(R.layout.map_list_item, null);
			holder = new ViewHolder();
			
			holder.imgAvatar = (ImageView) convertView.findViewById(R.id.map_list_item_ava);
			holder.tvNewMessage = (TextView) convertView.findViewById(R.id.map_list_item_img_message);
			holder.imgLocationIcon = (ImageView) convertView.findViewById(R.id.map_list_item_img_acc);
    		holder.tvName = (EllipsizingTextView) convertView.findViewById(R.id.map_list_tv_name);
    		holder.tvName.setEllipsize(TruncateAt.END);
    		holder.tvName.setMaxLines(3);
    	    
    		holder.tvAddress = (EllipsizingTextView) convertView.findViewById(R.id.map_list_tv_address);
    		holder.tvAddress.setEllipsize(TruncateAt.END);
    		holder.tvAddress.setMaxLines(3);
    		
    		holder.tvStatus = (EllipsizingTextView) convertView.findViewById(R.id.map_list_tv_status);
    		holder.tvStatus.setEllipsize(TruncateAt.END);
    		holder.tvStatus.setMaxLines(3);
    		
    		holder.tvTime = (EllipsizingTextView) convertView.findViewById(R.id.map_list_tv_time);
    		holder.tvTime.setEllipsize(TruncateAt.END);
    		holder.tvTime.setMaxLines(3);
    		
    		//holder.vRight = convertView.findViewById(R.id.map_list_item_rl);
    		holder.vMid = convertView.findViewById(R.id.list_item);
    		holder.vText = convertView.findViewById(R.id.list_item_text);
    		holder.vContent = convertView.findViewById(R.id.list_item_layout_content);
    		
			convertView.setTag(holder);
			v = convertView;
        }
        else {
        	holder = (ViewHolder) v.getTag();
        	if (holder ==null){
        		holder = new ViewHolder();
        		holder.tvNewMessage = (TextView) v.findViewById(R.id.map_list_item_img_message);
        		holder.imgLocationIcon = (ImageView) v.findViewById(R.id.map_list_item_img_acc);
    			holder.imgAvatar = (ImageView) v.findViewById(R.id.map_list_item_ava);
        		holder.tvName = (EllipsizingTextView) v.findViewById(R.id.map_list_tv_name);
        		holder.tvName.setEllipsize(TruncateAt.END);
        		holder.tvName.setMaxLines(3);
        	    
        		holder.tvAddress = (EllipsizingTextView) v.findViewById(R.id.map_list_tv_address);
        		holder.tvAddress.setEllipsize(TruncateAt.END);
        		holder.tvAddress.setMaxLines(3);
        		
        		holder.tvStatus = (EllipsizingTextView) v.findViewById(R.id.map_list_tv_status);
        		holder.tvStatus.setEllipsize(TruncateAt.END);
        		holder.tvStatus.setMaxLines(3);
        		
        		holder.tvTime = (EllipsizingTextView) v.findViewById(R.id.map_list_tv_time);
        		holder.tvTime.setEllipsize(TruncateAt.END);
        		holder.tvTime.setMaxLines(3);
        		
        		//holder.vRight = v.findViewById(R.id.map_list_item_rl);
        		holder.vText = v.findViewById(R.id.list_item_text);
        		holder.vMid = v.findViewById(R.id.list_item);
        		holder.vContent = v.findViewById(R.id.list_item_layout_content);
        		
    			v.setTag(holder);
        	}
        	handleEvent(v);
        }
        initData(v,position,holder);
        return v;
    }
    
    /**
     * Initial data for v: background, set text,...
     * @param v
     */
    private void initData(View v,int position,ViewHolder holder){
    	//Right background 
		//holder.vRight.setBackgroundResource(imageOutResIds[position%6]);
    	
    	holder.vText.setBackgroundResource(imageOutResIds[position%6]);
    	holder.vMid.setBackgroundResource(imageInnerResIds[position%6]);
    	
    	final FriendObject obj = arr.get(position);
    	if (obj!=null){
    		//at chat screen?
    		if (!isChat){
        		holder.tvNewMessage.setVisibility(View.GONE);
        	}
        	else {
        		//yes->check data
        		if (messageArr!=null){
        			String id = String.valueOf(obj.getFriendID());
        			if (messageArr.containsKey(id)){
        				ArrayList<MessageObject> list =(ArrayList<MessageObject>) messageArr.get(id);
        				int size = list.size();
        				if (size>0){
	        				holder.tvNewMessage.setVisibility(View.VISIBLE);
	        				holder.tvNewMessage.setText(String.valueOf(size));
        				}
        				else {
        					holder.tvNewMessage.setVisibility(View.GONE);
        				}
        			}
        			else {
        				holder.tvNewMessage.setVisibility(View.GONE);
        			}
        		}
        	}
    		/*DownloadImage down = new DownloadImage(holder.imgAvatar,40,40,mContext) ;
    		down.execute(obj.getFriendAva());*/
    		String s  = obj.getFriendAva();
    		if (s != null&&(!s.equals(""))) {
				/*DownloadImage down = new DownloadImage(imgViewAvatar, 60,
						60, mContext);
				down.execute(userObject.getImageUrl());*/
    			DownloadImageTask task = new DownloadImageTask(holder.imgAvatar, 40, 40, 
    					mContext, s,obj.isMale());
    			executor.submit(task);
			}
			else {
				setDefaultAvatar(obj,holder.imgAvatar);
			}
    		//s = "http://49.212.140.145//itell//img//uploads//users//20120910-QIw5Kk0A5d5Lt7o.png";
    		
    		
    		//Set text 
    		holder.tvName.setText(obj.getFriendNick());
    		
    		if (obj.getFriendAddress()==null){
    			// get address from latitude, longitude
    			double lat = obj.getFriendLat();
    			double lng = obj.getFriendLong();
    			holder.tvAddress.setText("");
    			GeocoderTask geo = new GeocoderTask(holder.tvAddress, lat, lng, mContext, 
    									String.valueOf(ItelApplication.user_id), ItelApplication.uuid){
    				@Override
    				protected void finish(View v,
    						String[] result) {
    					super.finish(v, result);
    					if (v!=null){
    						if (result!=null){
    							String address = Utils.getAddress(result[2], result[0], result[1]);
    							((EllipsizingTextView)v).setText(address);
    							 obj.setFriendAddress(address);
    						}
    					}
    				}
    			};
    			executor.submit(geo);
    		}
    		else {
    			holder.tvAddress.setText(obj.getFriendAddress());
    		}
    		holder.tvStatus.setText(obj.getFriendiTell());
    		
    		holder.time = obj.getFriendStatTime();
    		View tv = holder.vContent.findViewById(R.id.list_item_text_title);
    		tv.setBackgroundResource(imageOutResIds[position%6]);
    		holder.imgLocationIcon.setImageBitmap(null);
    		Drawable drawable = null;
    		if (obj.isFriend()){
    			drawable = mContext.getResources().getDrawable(R.drawable.list_location_2);
    			holder.imgLocationIcon.setImageBitmap(((BitmapDrawable)drawable).getBitmap());
    		}
    		else {
    			drawable = mContext.getResources().getDrawable(R.drawable.list_location_1);
    			holder.imgLocationIcon.setImageBitmap(((BitmapDrawable)drawable).getBitmap());
    		}
    	}
    	
    }
    private void setDefaultAvatar(FriendObject userObject,ImageView imgViewAvatar){
		if (userObject.isMale()) {
			Drawable d = mContext.getResources().getDrawable(
					R.drawable.avatar_male_default);
			imgViewAvatar.setImageBitmap(null);
			imgViewAvatar.setImageBitmap(Utils.convert2bitmap(d));
		}
		else {
			Drawable d = mContext.getResources().getDrawable(
					R.drawable.avatar_female_default);
			imgViewAvatar.setImageBitmap(null);
			imgViewAvatar.setImageBitmap(Utils.convert2bitmap(d));
		}
	}
}
