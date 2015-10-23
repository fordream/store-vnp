/**
 * 
 */
package org.vnp.vas.view.adapter;

import org.vnp.vas.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author tvuong1pc
 * 
 */
public class MusicGridViewAdapter extends CursorAdapter {

	/**
	 * @param context
	 * @param c
	 */
	public MusicGridViewAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param c
	 * @param autoRequery
	 */
	public MusicGridViewAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.CursorAdapter#bindView(android.view.View,
	 * android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ItemView itemView = (ItemView) view;
		// set data
		itemView.setData(cursor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.CursorAdapter#newView(android.content.Context,
	 * android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// genView

		ItemView itemView = new ItemView(context);
		// ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
		// layoutParams.height = layoutParams.width;
		// itemView.setLayoutParams(layoutParams);
		itemView.setParam(parent.getWidth() / 3);
		return itemView;
	}

	private class ItemView extends LinearLayout {
		private ImageView imgMusic;
		private TextView tvItem;

		public ItemView(Context context) {
			super(context);
			init();
		}

		public void setParam(int width) {
			LinearLayout.LayoutParams params = new LayoutParams(width, width);
			findViewById(R.id.item_main).setLayoutParams(params);
		}

		public void setData(Cursor cursor) {

		}

		private void init() {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.music_item, this);
			imgMusic = (ImageView) findViewById(R.id.imgMusic);
			tvItem = (TextView) findViewById(R.id.tvItem);
		}
	}
}