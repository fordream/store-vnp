package com.cnc.buddyup;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.buddyup.buddy.views.AddBuddyView;
import com.cnc.buddyup.buddy.views.AddMemberView;
import com.cnc.buddyup.buddy.views.BuddyListView;
import com.cnc.buddyup.buddy.views.CreateGroupView;
import com.cnc.buddyup.buddy.views.GroupDetailView;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.message.views.ReplyMessageView;
import com.cnc.buddyup.views.BuddyGroupListView;
import com.cnc.buddyup.views.activity.BuddiesMainView;
import com.cnc.buddyup.views.buddy.BuddyListAdministratorView;
import com.cnc.buddyup.views.buddy.BuddyListInlineView;

public class BuddiesScreen extends NActivity implements OnClickListener {

	private List<View> lViews = new ArrayList<View>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = new BuddiesMainView(this);
		lViews.add(view);
		setContentView(view);
	}

	public void onClick(View arg0) {

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBack() {
		if (lViews.size() > 1) {
			lViews.remove(lViews.size() - 1);
			setContentView(lViews.get(lViews.size() - 1));
		} else {
			finish();
		}
	}

	public void addBuddiesList() {
		View view = new BuddyListView(this);
		lViews.add(view);
		setContentView(view);
	}

	public void addAddbudies() {
		View view = new AddBuddyView(this);
		lViews.add(view);
		setContentView(view);
	}

	public void addBuddyListAdministratorView() {
		View view = new BuddyListAdministratorView(this, new Handler());
		lViews.add(view);
		setContentView(view);
	}
	
	public void addBuddyListInlineView() {
		View view = new BuddyListInlineView(this, new Handler());
		lViews.add(view);
		setContentView(view);

	}

	public void addRepLyMessage() {
		View view = new ReplyMessageView(this);
		lViews.add(view);
		setContentView(view);
	}

	// Group
	public void addGroupList() {
		View view = new BuddyGroupListView(this, new Handler());
		lViews.add(view);
		setContentView(view);
	}

	public void addAddGroup() {
		View view = new CreateGroupView(this);
		lViews.add(view);
		setContentView(view);
	}

	public void addGroupDetail() {
		View view = new GroupDetailView(this);
		lViews.add(view);
		setContentView(view);

	}

	public void addAddMember() {
		View view = new AddMemberView(this);
		lViews.add(view);
		setContentView(view);
	}
}
