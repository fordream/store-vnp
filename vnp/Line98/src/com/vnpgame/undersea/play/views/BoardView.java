package com.vnpgame.undersea.play.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.vnpgame.line98.R;
import com.vnpgame.undersea.play.field.Board;
import com.vnpgame.undersea.play.field.FiledBoard;
public class BoardView extends LinearLayout {
	private FiledBoard filedBoard;

	public BoardView(Context context) {
		super(context);
		config();
	}

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.board, this);
		filedBoard = new FiledBoard(this);
	}

	public void setOnClick(OnClickListener onClick) {
		
	}

	public void setOnClick(int i, int j, OnClickListener board) {
		filedBoard.setOnClick(i,j,board) ;
	}

	public void updateBoard(Board board) {
		filedBoard.updateBoard(board);
	}
}
