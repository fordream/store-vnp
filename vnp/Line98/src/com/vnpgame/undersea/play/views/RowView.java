package com.vnpgame.undersea.play.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.vnpgame.line98.R;
import com.vnpgame.undersea.play.field.Board;
import com.vnpgame.undersea.play.field.FiledRow;

public class RowView extends LinearLayout {
	private FiledRow filedRow;
	public RowView(Context context) {
		super(context);
		config();
	}

	public RowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.row, this);
		filedRow= new FiledRow(this);
	}

	public void setOnClick(int j, OnClickListener board) {
		filedRow.setOnClick(j,board);
	}

	public void updateBoard(int i, Board board) {
		filedRow.updateBoard(i,board);
		
	}
}
