package org.com.vnp.lmhtmanager.view;

import org.com.vnp.lmhtmanager.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

//org.com.vnp.lmhtmanager.view.MoreView
public class MoreView extends LinearLayout {

	public MoreView(Context context) {
		super(context);
		init();
	}

	public MoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		try {
			((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu, this);
			findViewById(R.id.menu_more).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					v.setVisibility(View.GONE);
				}
			});

			findViewById(R.id.menu_more).setVisibility(View.GONE);

			ListView menu_more_list = (ListView) findViewById(R.id.menu_more_list);

			String string[] = getContext().getResources().getStringArray(R.array.lan);
			menu_more_list.setAdapter(new ArrayAdapter<String>(getContext(), 0, string) {
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {

					if (convertView == null) {
						convertView = new MenuMoreItemView(parent.getContext());
					}
					((MenuMoreItemView) convertView).setData(getItem(position));
					((MenuMoreItemView) convertView).setGender();

					return convertView;
				}
			});
		} catch (Exception exception) {

		}
	}

	public void showmenu() {
		findViewById(R.id.menu_more).setVisibility(View.VISIBLE);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		ListView menu_more_list = (ListView) findViewById(R.id.menu_more_list);
		menu_more_list.setOnItemClickListener(onItemClickListener);
	}

	public void hidden() {
		findViewById(R.id.menu_more).setVisibility(View.GONE);

	}
}