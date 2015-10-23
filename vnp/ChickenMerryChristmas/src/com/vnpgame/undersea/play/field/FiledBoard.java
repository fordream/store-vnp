package com.vnpgame.undersea.play.field;

import android.view.View.OnClickListener;

import com.vnpgame.chickenmerrychristmas.R;
import com.vnpgame.undersea.play.views.BoardView;
import com.vnpgame.undersea.play.views.RowView;

public class FiledBoard {
	public static final int SIZE = 10;
	private BoardView boardView;
	private RowView[] rowView = new RowView[SIZE];

	public FiledBoard(BoardView boardView) {
		super();
		this.boardView = boardView;
		rowView[0] = (RowView) this.boardView.findViewById(R.id.RowView10);
		rowView[1] = (RowView) this.boardView.findViewById(R.id.RowView09);
		rowView[2] = (RowView) this.boardView.findViewById(R.id.RowView08);
		rowView[3] = (RowView) this.boardView.findViewById(R.id.RowView07);
		rowView[4] = (RowView) this.boardView.findViewById(R.id.RowView05);
		rowView[5] = (RowView) this.boardView.findViewById(R.id.RowView04);
		rowView[6] = (RowView) this.boardView.findViewById(R.id.RowView03);
		rowView[7] = (RowView) this.boardView.findViewById(R.id.RowView02);
		rowView[8] = (RowView) this.boardView.findViewById(R.id.RowView01);
		rowView[9] = (RowView) this.boardView.findViewById(R.id.RowView06);
	}

	public void setOnClick(int i, int j, OnClickListener board) {
		rowView[i].setOnClick(j, board);
	}

	public void updateBoard(Board board) {
		rowView[0].updateBoard(0, board);
		rowView[1].updateBoard(1, board);
		rowView[2].updateBoard(2, board);
		rowView[3].updateBoard(3, board);
		rowView[4].updateBoard(4, board);
		rowView[5].updateBoard(5, board);
		rowView[6].updateBoard(6, board);
		rowView[7].updateBoard(7, board);
		rowView[8].updateBoard(8, board);
		rowView[9].updateBoard(9, board);
	}
}
