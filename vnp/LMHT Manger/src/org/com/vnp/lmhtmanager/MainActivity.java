package org.com.vnp.lmhtmanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.com.vnp.lmhtmanager.view.MenuItemView;
import org.com.vnp.lmhtmanager.view.MoreView;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vnp.core.base.activity.BaseActivity;
import com.vnp.core.base.datastore.DataStore;
import com.vnp.core.v2.BaseAdapter;

public class MainActivity extends BaseActivity {
	// public static RiotApi RIOT_API;
	private SlidingPaneLayout pane;
	private ListView left_drawer_list;
	private GridView main_listView;
	private MoreView main_more_view;
	private CheckBox menu_enable_edit;

	private Bitmap getBitmapFromAsset(String strName) {
		AssetManager assetManager = getAssets();
		InputStream istr = null;
		try {
			istr = assetManager.open("images/champions-icon/" + strName);
		} catch (IOException e) {
			Log.e("passer", e.getMessage(), e);
		}
		Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return bitmap;
	}

	private String type = null;
	private boolean isEnableEditMote = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.a_bot_to_top_in, R.anim.a_nothing);
		DataStore.getInstance().init(this);
		setContentView(R.layout.activity_main1);

		menu_enable_edit = getView(R.id.menu_enable_edit);
		menu_enable_edit.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isEnableEditMote = menu_enable_edit.isChecked();
				showData(null);
			}
		});

		main_more_view = (MoreView) findViewById(R.id.main_more_view);
		main_more_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				main_more_view.hidden();
				type = parent.getItemAtPosition(position).toString();
				view.postDelayed(new Runnable() {

					@Override
					public void run() {
						showData(null);

					}
				}, 250);
			}
		});
		pane = (SlidingPaneLayout) findViewById(R.id.sp);
		main_listView = getView(R.id.main_listView);
		main_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (isEnableEditMote && type != null) {
					String data = DataStore.getInstance().get(type, "");
					Cursor cursor = ((Cursor) parent.getItemAtPosition(position));
					String mId = cursor.getString(cursor.getColumnIndex("id"));
					if (data.contains("<" + mId + ">")) {
						data = data.replace("<" + mId + ">", "");
					} else {
						data = data + "<" + mId + ">";
					}
					DataStore.getInstance().save(type, data);
					((CursorAdapter) main_listView.getAdapter()).notifyDataSetChanged();
				}
			}
		});
		left_drawer_list = (ListView) findViewById(R.id.left_drawer_list);

		Cursor cursor = ((LMHTApplication) getApplication()).getSQLiteDatabase().query("searchTags", null, null, null, null, null, null);
		left_drawer_list.setAdapter(new CursorAdapter(this, cursor) {
			@Override
			public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
				return new MenuItemView(arg0);
			}

			@Override
			public void bindView(View arg0, Context arg1, Cursor arg2) {
				((MenuItemView) arg0).setData(arg2);
				((MenuItemView) arg0).setGender();
			}
		});
		left_drawer_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
				pane.closePane();
				type = null;
				view.postDelayed(new Runnable() {

					@Override
					public void run() {
						Cursor cursor = (Cursor) parent.getItemAtPosition(position);
						showData(cursor);

					}
				}, 250);

			}
		});
		findViewById(R.id.Menu_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pane.openPane();
			}
		});
		pane.setPanelSlideListener(new PaneListener());
		findViewById(R.id.Menu_right).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				main_more_view.showmenu();

			}
		});
		pane.setPanelSlideListener(new PaneListener());
		showData(null);
	}

	private void showData(Cursor mCursor) {

		TextView header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText(mCursor == null ? "All" : mCursor.getString(mCursor.getColumnIndex("displayName")));

		String whes = null;
		if (type != null) {
			header_text.setText(type);
			whes = String.format("id in(%s)", DataStore.getInstance().get(type, ""));
			whes = whes.replace("><", ",").replace("<", "").replace(">", "");
			if (isEnableEditMote) {
				whes = "";
			}

			menu_enable_edit.setVisibility(View.VISIBLE);
		} else {
			menu_enable_edit.setVisibility(View.GONE);
		}

		final String where = whes != null ? whes : mCursor == null ? null : String.format("tags = '%s'", mCursor.getString(mCursor.getColumnIndex("name")));

		new AsyncTask<String, String, Cursor>() {

			@Override
			protected Cursor doInBackground(String... params) {
				return ((LMHTApplication) getApplication()).getSQLiteDatabase().query("champions", null, where, null, null, null, null);
			}

			protected void onPostExecute(Cursor result) {
				main_listView.setAdapter(new CursorAdapter(MainActivity.this, result) {
					@Override
					public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
						View view = ((LayoutInflater) arg2.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.champion_item, null);
						return view;
					}

					@Override
					public void bindView(View arg0, Context arg1, Cursor arg2) {
						((TextView) arg0.findViewById(R.id.champion_item_name)).setText(arg2.getString(arg2.getColumnIndex("displayName")));
						((TextView) arg0.findViewById(R.id.champion_item_title)).setText(arg2.getString(arg2.getColumnIndex("title")));
						String iconPath = arg2.getString(arg2.getColumnIndex("iconPath"));
						ImageView champion_item_avatar = ((ImageView) arg0.findViewById(R.id.champion_item_avatar));
						champion_item_avatar.setImageBitmap(getBitmapFromAsset(iconPath));
						String id = arg2.getString(arg2.getColumnIndex("id"));
						if (type != null && isEnableEditMote) {
							if (String.format("%s", DataStore.getInstance().get(type, "")).contains(String.format("<%s>", id))) {
								arg0.findViewById(R.id.champion_item_bg).setBackgroundResource(R.drawable.btn_red);
							} else {
								arg0.findViewById(R.id.champion_item_bg).setBackgroundResource(R.drawable.tranfer);
							}

						}
					}
				});
			};
		}.execute("");

	}

	private class PaneListener implements SlidingPaneLayout.PanelSlideListener {

		@Override
		public void onPanelClosed(View view) {

		}

		@Override
		public void onPanelOpened(View view) {

		}

		@Override
		public void onPanelSlide(View view, float arg1) {
		}
	}

}