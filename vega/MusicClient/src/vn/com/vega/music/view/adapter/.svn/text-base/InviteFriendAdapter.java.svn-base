package vn.com.vega.music.view.adapter;

import java.util.ArrayList;

import vn.com.vega.chacha.R;
import vn.com.vega.music.objects.UnregisteredFriend;
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

public class InviteFriendAdapter extends ArrayAdapter<UnregisteredFriend> {

	private Context mContext;
	private ArrayList<UnregisteredFriend> arr;

	public InviteFriendAdapter(Context context, int textViewResourceId,
			ArrayList<UnregisteredFriend> objects) {
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
		if (v == null) {
			LayoutInflater li = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.view_listview_row_invite_friend, null);
			holder = new ViewHolder();
			holder.chkSelect = (CheckBox) v.findViewById(R.id.chkSelect);
			holder.name = (TextView) v.findViewById(R.id.facebookName);
			v.setTag(holder);
		} else
			holder = (ViewHolder) v.getTag();
		if (position == 0) {
			v.setBackgroundResource(R.drawable.selector_setting_friend_listview_row_first);
		} else if (position == arr.size() - 1) {
			v.setBackgroundResource(R.drawable.selector_setting_friend_listview_row_last);
		} else {
			v.setBackgroundResource(R.drawable.selector_setting_friend_listview_row_middle);
		}
		final UnregisteredFriend o = arr.get(position);
		if (o != null) {
			holder.chkSelect.setChecked(o.getSelected());
			holder.chkSelect
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							o.setSelected(isChecked);
						}
					});
			holder.name.setText(o.getName());
			holder.chkSelect.setTag(o.getId());
		}
		return v;
	}
	
	public void notifyListObjectChanged(ArrayList<UnregisteredFriend> objs) {
		if (objs != null) {
			arr.addAll(objs);
			notifyDataSetChanged();
		}
	}
	
	public void OnResume() {
		notifyDataSetChanged();
	}

	class ViewHolder {
		CheckBox chkSelect;
		TextView name;
	}
}
