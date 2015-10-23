package vn.com.vega.music.view.adapter;

import java.util.ArrayList;

import vn.com.vega.chacha.R;
import vn.com.vega.music.objects.FacebookFriend;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class InviteFriendAdapter extends ArrayAdapter<FacebookFriend> {

	private Context mContext;
	private ArrayList<FacebookFriend> arr;
	
	public InviteFriendAdapter(Context context,	int textViewResourceId, ArrayList<FacebookFriend> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mContext = context;
		arr = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.d("InviteFriendAdapter", "GetView");
		View v = convertView;
		ViewHolder holder;
		if(v == null) {
			LayoutInflater li = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.view_listview_row_invite_friend, null);
			holder = new ViewHolder();
			holder.chkSelect = (CheckBox)v.findViewById(R.id.chkSelect);
			holder.facebookName = (TextView)v.findViewById(R.id.facebookName);
			v.setTag(holder);
		}
		else
			holder = (ViewHolder)v.getTag();
		if(position == 0) {
			v.setBackgroundResource(R.drawable.selector_setting_friend_listview_row_first);
		}
		else if(position == arr.size() - 1) {
			v.setBackgroundResource(R.drawable.selector_setting_friend_listview_row_last);
		}
		else {
			v.setBackgroundResource(R.drawable.selector_setting_friend_listview_row_middle);
		}
		final FacebookFriend o = arr.get(position);
		if(o != null) {
			holder.chkSelect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					o.setIsChecked(isChecked);
				}
			});
			holder.facebookName.setText(o.getFacebookName());
			holder.chkSelect.setTag(o.getFacebookID());
			holder.chkSelect.setChecked(o.getIsChecked());
		}
		return v;
	}
	
	class ViewHolder {
		CheckBox chkSelect;
		TextView facebookName;
	}
}
