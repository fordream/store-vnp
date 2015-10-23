package org.vnp.vas.controller;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import org.vnp.vas.R;
import org.vnp.vas.view.HeaderView;
import org.vnp.vas.view.MusicGridView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.ict.library.activity.CommonBaseActivity;

public class BaseGridActivity extends CommonBaseActivity implements
		OnItemLongClickListener, OnItemClickListener {
	private EditText evSearch;
	private MusicGridView musicGridView;
	private HeaderView headerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_tab);

		musicGridView = (MusicGridView) findViewById(R.id.musicGridView1);
		headerView = getView(R.id.headerView1);
		headerView.setVisibility(View.GONE);

		evSearch = getView(R.id.editText1);
		evSearch.setVisibility(View.GONE);

		musicGridView.setOnItemLongClickListener(this);

		musicGridView.setOnItemClickListener(this);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View View,
			int position, long arg3) {

		ActionItem addItem = new ActionItem(1, "Add", getResources()
				.getDrawable(R.drawable.indicator));
		ActionItem acceptItem = new ActionItem(2, "Accept", getResources()
				.getDrawable(R.drawable.indicator));
		ActionItem uploadItem = new ActionItem(3, "Upload", getResources()
				.getDrawable(R.drawable.indicator));

		final QuickAction mQuickAction = new QuickAction(this);

		mQuickAction.addActionItem(addItem);
		mQuickAction.addActionItem(acceptItem);
		mQuickAction.addActionItem(uploadItem);

		// setup the action item click listener
		mQuickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);

						// if (actionId == ID_ADD) { //Add item selected
						// Toast.makeText(getApplicationContext(),
						// "Add item selected on row " + mSelectedRow,
						// Toast.LENGTH_SHORT).show();
						// } else {
						// Toast.makeText(getApplicationContext(),
						// actionItem.getTitle() + " item selected on row "
						// + mSelectedRow, Toast.LENGTH_SHORT).show();
						// }
					}
				});

		// setup on dismiss listener, set the icon back to normal
		mQuickAction.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				// mMoreIv.setImageResource(R.drawable.ic_list_more);
			}
		});
		mQuickAction.show(View);
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

	}

	public void showSearch() {
		evSearch.setVisibility(View.VISIBLE);
	}

	public void showHeaderVeiew(String txtheader) {
		headerView.setVisibility(View.VISIBLE);
		headerView.setTitle(txtheader);
	}
}