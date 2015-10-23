package com.vnpgame.undersea.score.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vnpgame.line98.R;
import com.vnpgame.line98.Score;
import com.vnpgame.undersea.score.views.ScoreItemView;

public class ScoreAdapter extends ArrayAdapter<Score> {
	private List<Score> listData = new ArrayList<Score>();

	public ScoreAdapter(Context context, List<Score> objects) {
		super(context, R.layout.scoreitem, objects);
		this.listData = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View workView = convertView;

		if (workView == null) {
			workView = new ScoreItemView(getContext());
		}

		final Score item = listData.get(position);
		ScoreItemView view = ((ScoreItemView) workView);
		if (item != null) {
			view.setData(item);
		}

		return workView;
	}
}
