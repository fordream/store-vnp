package minh.app.mbook.views;

import java.util.ArrayList;
import java.util.List;

import minh.app.mbook.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.vnp.core.common.BaseAdapter;
import com.vnp.core.common.BaseAdapter.CommonGenderView;
import com.vnp.core.view.CustomLinearLayoutView;

public class GroupItemView extends LinearLayout {
	private TableLayout gridView;

	public GroupItemView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.group, this);
		List<Object> lData = new ArrayList<Object>();
		lData.add(new Object());
		lData.add(new Object());
		lData.add(new Object());
		lData.add(new Object());
		lData.add(new Object());

		gridView = (TableLayout) findViewById(R.id.group_content);
		adapter = new BaseAdapter(getContext(), lData, new CommonGenderView() {

			@Override
			public CustomLinearLayoutView getView(Context arg0, Object arg1) {
				return new MCustomLinearLayoutView(arg0);
			}
		});

		//gridView.setAdapter(adapter);
		gridView.setVisibility(View.GONE);
		findViewById(R.id.group_name).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						int visibility = gridView.getVisibility() == View.GONE ? View.VISIBLE
								: View.GONE;
						gridView.setVisibility(visibility);
					}
				});

	}

	public void setGroupName(String groupName) {
		((TextView) findViewById(R.id.group_name)).setText(groupName);
	}

	private BaseAdapter adapter;

	private class MCustomLinearLayoutView extends CustomLinearLayoutView {

		public MCustomLinearLayoutView(Context context) {
			super(context);
			init(R.layout.item);
		}

		@Override
		public void setGender() {
			
		}
	}
}