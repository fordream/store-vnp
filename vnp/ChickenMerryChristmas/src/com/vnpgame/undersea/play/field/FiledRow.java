package com.vnpgame.undersea.play.field;

import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.vnpgame.chickenmerrychristmas.R;
import com.vnpgame.undersea.play.views.RowView;

public class FiledRow {
	private RowView rowView;
	public static final int SIZE = 9;
	ImageView[] imageViews = new ImageView[SIZE];

	public FiledRow(RowView context) {
		this.rowView = context;
		imageViews[0] = (ImageView) rowView.findViewById(R.id.ImageView12);
		imageViews[1] = (ImageView) rowView.findViewById(R.id.ImageView10);
		imageViews[2] = (ImageView) rowView.findViewById(R.id.ImageView09);
		imageViews[3] = (ImageView) rowView.findViewById(R.id.ImageView08);
		imageViews[4] = (ImageView) rowView.findViewById(R.id.ImageView07);
		imageViews[5] = (ImageView) rowView.findViewById(R.id.ImageView06);
		imageViews[6] = (ImageView) rowView.findViewById(R.id.ImageView05);
		imageViews[7] = (ImageView) rowView.findViewById(R.id.ImageView04);
		imageViews[8] = (ImageView) rowView.findViewById(R.id.ImageView03);
//		imageViews[9] = (ImageView) rowView.findViewById(R.id.ImageView02);
//		imageViews[10] = (ImageView) rowView.findViewById(R.id.ImageView01);
//		imageViews[11] = (ImageView) rowView.findViewById(R.id.ImageView11);
	}

	public void setOnClick(int j, OnClickListener board) {
		imageViews[j].setOnClickListener(board);
	}

	public void updateBoard(int i, Board board) {
		imageViews[0].setBackgroundResource(board.get(i, 0));
		imageViews[1].setBackgroundResource(board.get(i, 1));
		imageViews[2].setBackgroundResource(board.get(i, 2));
		imageViews[3].setBackgroundResource(board.get(i, 3));
		imageViews[4].setBackgroundResource(board.get(i, 4));
		imageViews[5].setBackgroundResource(board.get(i, 5));
		imageViews[6].setBackgroundResource(board.get(i, 6));
		imageViews[7].setBackgroundResource(board.get(i, 7));
		imageViews[8].setBackgroundResource(board.get(i, 8));
//		imageViews[9].setBackgroundResource(board.get(i, 9));
//		imageViews[10].setBackgroundResource(board.get(i, 10));
//		imageViews[11].setBackgroundResource(board.get(i, 11));
	}
}
