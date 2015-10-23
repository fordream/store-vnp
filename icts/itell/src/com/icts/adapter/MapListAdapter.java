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
public class MapListAdapter extends ArrayAdapter<User>{

	public class ViewHolder{
		TextView tv_status;
		TextView tv_user_name;
		TextView tv_address;
		TextView tv_time;
		ImageView img_avatar;
		ImageView img_sexual;
		CountTime countTime;
	}
	
	private Handler handler;
	private ExecutorService executor;
	private Context context;
	private List<User> list = null;
	private CountTime countTime;
	private ViewHolder holder;
	public MapListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	public MapListAdapter(Context context,List<User> list,Handler handler ) {
		super(context, R.layout.item_map_listfriend);
		this.list = list;
		this.context = context;
		executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
		this.handler = handler;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		User user = (User) getItem(position);
		//ViewHolder holder;
		if (convertView ==null){
			LayoutInflater inflater = LayoutInflater.from(this.context); 
			convertView = inflater.inflate(R.layout.item_map_listfriend, null);
			holder = new ViewHolder();
			
			holder.tv_status = (TextView) convertView.findViewById(R.id.item_map_user_status);
			holder.tv_address= (TextView) convertView.findViewById(R.id.item_map_user_address); 
			holder.img_avatar = (ImageView) convertView.findViewById(R.id.item_map_user_image);
			holder.tv_time = (TextView) convertView.findViewById(R.id.item_map_update_time);
			holder.tv_user_name = (TextView) convertView.findViewById(R.id.item_map_user_name);
			holder.img_sexual = (ImageView)convertView.findViewById(R.id.item_map_image_sexual);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		/*if (holder.countTime==null){
			holder.countTime = new CountTime(user.getStatusUpdateTime(), holder.tv_time);
			holder.countTime.start();
		}*/
		setData(user, holder);
		//Title
		//holder.tvTitle.setEllipsize(TruncateAt.END);
		//holder.tvTitle.setMaxLines(3);
		//holder.tvTitle.setText();
		return convertView;
	}
	
	/**
	 * Set data for layout using data of user
	 * @param user
	 * @param holder
	 */
	private void setData(User user,ViewHolder holder){
		holder.tv_address.setText(user.getAddress());
		holder.tv_status.setText(user.getStatus());
		holder.tv_user_name.setText(user.getUserNick());
		
	}
	
	public void stopCount(){
		if (holder.countTime!=null){
			holder.countTime.cancel();
		}
	}

}
