package org.vnp.vas.view;

import org.vnp.vas.model.database.DBAdapter;
import org.vnp.vas.view.adapter.MusicGridViewAdapter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.GridView;

public class MusicGridView extends GridView {
	public MusicGridView(Context context) {
		super(context);
		init();
	}

	public MusicGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MusicGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setNumColumns(3);
		// setAdapter(new MusicGridViewAdapter(getContext(), genCursor(),
		// true));
		new AsyncTask<String, String, String>() {
			Cursor cursor;

			@Override
			protected String doInBackground(String... params) {
				cursor = genCursor();
				return null;
			}

			protected void onPostExecute(String result) {
				if (cursor != null) {
					setAdapter(new MusicGridViewAdapter(getContext(), cursor,
							true));
				}
			};
		}.execute("");

		
	}

	private Cursor genCursor() {

		DBAdapter adapter = new DBAdapter(getContext());
		adapter.open();
		return adapter.getSQLiteDatabase().query("music", null, null, null,
				null, null, null);
	}
}