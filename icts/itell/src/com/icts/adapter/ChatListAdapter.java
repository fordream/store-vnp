package com.icts.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icts.itel.R;
import com.icts.object.User;
import com.icts.utils.Constant;
import com.icts.utils.CountTime;

/**
 * Adapter for list view in map function
 * @author Luong
 *
 */
public class ChatListAdapter extends ArrayAdapter<User>{

	public class ViewHolder{
		TextView tv_user_name;
		TextView tv_numer_message;
		ImageView img_avatar;
	}
	
	private ExecutorService executor;
	private Context context;
	private List<User> list = null;
	private ViewHolder holder;
	public ChatListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	public ChatListAdapter(Context context,List<User> list ) {
		super(context, R.layout.item_chat_list);
		this.list = list;
		this.context = context;
		executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
	}
	
	@Override
	public User getItem(int position) {
		if (list!=null)
			return list.get(position);
		return null;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getCount() {
		if (list!=null)
			return list.size();
		return 0;
	}
	

	@Override
	public boolean areAllItemsEnabled() {
		//return super.areAllItemsEnabled();
		return false;
	}
	
	@Override
	public boolean isEnabled(int position) {
		//return super.isEnabled(position);
		return false;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		User user = (User) getItem(position);
		//ViewHolder holder;
		if (convertView ==null){
			LayoutInflater inflater = LayoutInflater.from(this.context); 
			convertView = inflater.inflate(R.layout.item_chat_list, null);
			holder = new ViewHolder();
			
			holder.tv_user_name = (TextView) convertView.findViewById(R.id.item_chat_user_name);
			holder.tv_numer_message= (TextView) convertView.findViewById(R.id.item_chat_message_number); 
			holder.img_avatar = (ImageView) convertView.findViewById(R.id.item_chat_user_image);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		setData(user, holder);
		return convertView;
	}
	
	/**
	 * Set data for layout using data of user
	 * @param user
	 * @param holder
	 */
	private void setData(User user,ViewHolder holder){
		holder.tv_numer_message.setText("");
		holder.tv_user_name.setText(user.getUserNick());
		
	}
}
