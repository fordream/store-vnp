package com.icts.adapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icts.app.ItelApplication;
import com.icts.itel.R;
import com.icts.object.FriendObject;
import com.icts.object.MessageObject;
import com.icts.object.StampObject;
import com.icts.utils.Constant;
import com.icts.utils.DownloadImage;
import com.icts.utils.DownloadImageTask;
import com.icts.utils.ImageCache;
import com.icts.utils.Utils;

public class ChatAdapter extends ArrayAdapter<MessageObject> {
	public class ViewHolder{
		public LinearLayout llFriend;
		public LinearLayout llUser;
		public TextView tvFriendMessage;
		public TextView tvSelfMessage;
		public TextView tvFriendTime;
		public TextView tvSelfTime;
		public ImageView imgFriendStamp;
		public ImageView imgSelfStamp;
		public ImageView imgFriendAva;
		public ImageView imgSelfAva;
	}
	private ExecutorService executor;
	private FriendObject mFriend;
	private String mUserID;
	private ArrayList<MessageObject> listMessage;
	private Context mContext;
	private Drawable drawableMessage;
	public ChatAdapter(Context context, ArrayList<MessageObject> list,FriendObject friend,String userid) {
		super(context, R.layout.chat_message_item);
		mContext = context;
		listMessage = list;
		this.mFriend = friend;
		this.mUserID = userid;
		if (mFriend.isMale()){
			 drawableMessage = mContext.getResources().getDrawable(R.drawable.chat_bubble_blue_left);
		}
		else {
			 drawableMessage = mContext.getResources().getDrawable(R.drawable.chat_bubble_self_left);
		}
		executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
	}

	public void setFriend(FriendObject friend){
		mFriend = friend;
	}
	
	public void setUserID(String userID){
		mUserID = userID;
	}
	@Override
	public void add(MessageObject object) {
		if (object==null){
			return;
		}
		String friendID = String.valueOf(mFriend.getFriendID());
		String receiverID = object.getReceiverID();
		String senderID = object.getSenderID();
		if ((friendID.equalsIgnoreCase(receiverID)&&mUserID.equalsIgnoreCase(senderID))||
			(friendID.equalsIgnoreCase(senderID)&&mUserID.equalsIgnoreCase(receiverID))){
			super.add(object);
		}
	}
	
	public int getCount() {
		return listMessage.size();
	}

	public MessageObject getItem(int position) {
		return listMessage.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_message_item, null);
			holder = new ViewHolder();
			holder.llFriend =(LinearLayout) convertView.findViewById(R.id.chat_message_item_ll_friend);
			holder.llUser =(LinearLayout) convertView.findViewById(R.id.chat_message_item_ll_self);
			holder.imgFriendAva = (ImageView)convertView.findViewById(R.id.chat_message_item_img_friend);
			holder.imgSelfAva = (ImageView)convertView.findViewById(R.id.chat_message_item_img_self);
			holder.imgFriendStamp = (ImageView)convertView.findViewById(R.id.chat_message_item_img_stamp_friend);
			holder.imgSelfStamp = (ImageView)convertView.findViewById(R.id.chat_message_item_img_stamp_self);
			
			holder.tvFriendMessage = (TextView)convertView.findViewById(R.id.chat_message_item_message_friend);
			holder.tvFriendTime = (TextView)convertView.findViewById(R.id.chat_message_item_time_friend);
			holder.tvSelfMessage = (TextView)convertView.findViewById(R.id.chat_message_item_tv_message_self);
			holder.tvSelfTime= (TextView)convertView.findViewById(R.id.chat_message_item_time_self);
			convertView.setTag(holder);
		}
		else {
			holder =(ViewHolder) convertView.getTag();
		}
		
		setData(holder, position);
		return convertView;
	}
	
	private void setData(ViewHolder holder, int position){
		holder.tvFriendMessage.setBackgroundDrawable(drawableMessage);
		MessageObject message = listMessage.get(position);
		String friend_id = String.valueOf(mFriend.getFriendID());
		if (message.getSenderID().equalsIgnoreCase(mUserID)){
			holder.llUser.setVisibility(View.VISIBLE);
			holder.llFriend.setVisibility(View.GONE);
			String link = message.getMessage();
    		if (!message.isStamp()){
				holder.tvSelfMessage.setVisibility(View.VISIBLE);
				holder.imgSelfStamp.setVisibility(View.GONE);
				holder.tvSelfMessage.setText(link);
			}
			else {
				holder.tvSelfMessage.setVisibility(View.GONE);
				holder.imgSelfStamp.setVisibility(View.VISIBLE);
				while (link.startsWith("'")){
					link = link.substring(1);
				}
				while (link.endsWith("'")){
					int l = link.length();
					link = link.substring(0,l-1);
				}
				//DownloadImage down = new DownloadImage(holder.imgSelfStamp, 40, 40, mContext);
	    		//down.execute(link);
				/*String s = ImageCache.subMessage(link);
				if (s!=null){
					link = s;
				}
				while (link.startsWith("'")){
					link = link.substring(1);
				}
				while (link.endsWith("'")){
					int l = link.length();
					link = link.substring(0,l-1);
				}*/
				Bitmap b = ImageCache.getBitmapFromMemCache(link);
				if (b==null){
					try{
						StampObject stamp = ImageCache.getStampInNewCache(link);
						if (stamp.isFree()&&stamp.isFromNative()){
							String nameDrawable = stamp.getImageLink();
							int resourceID = Utils.getIDResource(mContext, nameDrawable);
								Drawable draw = mContext.getResources().getDrawable(resourceID);
								b = Utils.convert2bitmap(draw);
								ImageCache.addBitmapToCache(stamp.getCode(), b);
						}
					}
					catch(Exception ex){
						b = null;
					}
				}
				if (b!=null){
					holder.imgSelfStamp.setImageBitmap(b);
				}
				else {
					holder.tvSelfMessage.setVisibility(View.VISIBLE);
					holder.imgSelfStamp.setVisibility(View.GONE);
					holder.tvSelfMessage.setText(link);
				}
			}
			holder.tvSelfTime.setText(Utils.convertToString2(message.getTimestamp()));
		}
		else if (message.getSenderID().equalsIgnoreCase(friend_id)){
			holder.llUser.setVisibility(View.GONE);
			holder.llFriend.setVisibility(View.VISIBLE);
			String url = mFriend.getFriendAva();
			if ((url == null)||(url.equals(""))) {
				if (mFriend.isMale()) {
					Drawable d = mContext.getResources().getDrawable(
							R.drawable.avatar_male_default);
					holder.imgFriendAva.setImageBitmap(null);
					holder.imgFriendAva.setImageBitmap(Utils.convert2bitmap(d));
				}
				else {
					Drawable d = mContext.getResources().getDrawable(
							R.drawable.avatar_female_default);
					holder.imgFriendAva.setImageBitmap(null);
					holder.imgFriendAva.setImageBitmap(Utils.convert2bitmap(d));
				}
			}
			else {
				DownloadImageTask down = new DownloadImageTask(holder.imgFriendAva,40,40,mContext,url,mFriend.isMale()) ;
				executor.submit(down);
			}
    		
    		String link = message.getMessage();
    		
    		if (!message.isStamp()){
				holder.tvFriendMessage.setVisibility(View.VISIBLE);
				holder.imgFriendStamp.setVisibility(View.GONE);
				holder.tvFriendMessage.setText(link);
			}
			else {
				holder.tvFriendMessage.setVisibility(View.GONE);
				holder.imgFriendStamp.setVisibility(View.VISIBLE);
				while (link.startsWith("'")){
					link = link.substring(1);
				}
				while (link.endsWith("'")){
					int l = link.length();
					link = link.substring(0,l-1);
				}
				/*String s = ImageCache.subMessage(link);
				if (s!=null){
					link = s;
				}
				while (link.startsWith("'")){
					link = link.substring(1);
				}
				while (link.endsWith("'")){
					int l = link.length();
					link = link.substring(0,l-1);
				}
				if (s!=null){
					link = s;
				}*/
				Bitmap b = ImageCache.getBitmapFromMemCache(link);
				if (b==null){
					try{
						StampObject stamp = ImageCache.getStampInNewCache(link);
						if (stamp.isFree()&&stamp.isFromNative()){
							String nameDrawable = stamp.getImageLink();
							int resourceID = Utils.getIDResource(mContext, nameDrawable);
								Drawable draw = mContext.getResources().getDrawable(resourceID);
								b = Utils.convert2bitmap(draw);
								ImageCache.addBitmapToCache(stamp.getCode(), b);
						}
					}
					catch(Exception ex){
						b = null;
					}
				}
				if (b!=null){
					holder.imgFriendStamp.setImageBitmap(b);
				}
				else {
					holder.tvFriendMessage.setVisibility(View.VISIBLE);
					holder.imgFriendStamp.setVisibility(View.GONE);
					holder.tvFriendMessage.setText(link);
				}
			}
			holder.tvFriendTime.setText(Utils.convertToString2(message.getTimestamp()));
		} 
	}
	
	
	protected String isStamp(String message){
		String code = ImageCache.subMessage(message);
		if (code!=null){
			if (ImageCache.hasStamp(code)){
				return ImageCache.getStamp(code);
			}
		}
		return null;
	}

}
