package org.com.vnp.line98.views;

import org.com.cnc.common.adnroid.views.CommonLinearLayout;
import org.com.vnp.line98.activity.R;
import org.com.vnp.line98.model.Board;
import org.com.vnp.line98.onclick.OnItemClick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;

public class ItemRowView extends CommonLinearLayout {
	private ItemView[] itemView = new ItemView[Board.SIZE];

	public ItemRowView(Context context) {
		super(context);
		config(R.layout.itemrow);
	}

	public ItemRowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.itemrow);
	}

	public void config(int resource_layouy) {
		super.config(resource_layouy);
		itemView[0] = (ItemView) findViewById(R.id.itemView1);
		itemView[1] = (ItemView) findViewById(R.id.itemView2);
		itemView[2] = (ItemView) findViewById(R.id.itemView3);
		itemView[3] = (ItemView) findViewById(R.id.itemView4);
		itemView[4] = (ItemView) findViewById(R.id.itemView5);
		itemView[5] = (ItemView) findViewById(R.id.itemView6);
		itemView[6] = (ItemView) findViewById(R.id.itemView7);
		itemView[7] = (ItemView) findViewById(R.id.itemView8);
		itemView[8] = (ItemView) findViewById(R.id.itemView9);

	}

	public void setImage(int i, int res) {
		itemView[i].setImage(res);
	}

	public void setOnClick(int index,OnItemClick onItemClick) {
		itemView[index].setOnClickListener(onItemClick);
	}

	public void setImageRounte(int index, Animation animation, boolean b) {
		itemView[index].setImageRounte(b,animation);
	}
}