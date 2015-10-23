package org.com.vnp.line98.views;

import org.com.cnc.common.adnroid.CommonView;
import org.com.vnp.line98.activity.R;
import org.com.vnp.line98.model.Board;
import org.com.vnp.line98.model.Point;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class PlayView extends LinearLayout implements OnClickListener {
	private ImageView img1, img2, img3;
	// private RelativeLayout viewRelativeLayout;
	private LinearLayout LinearLayout01;
	private Activity activity;
	private Board board = new Board();
	private ItemRowView itemRowView1[] = new ItemRowView[Board.SIZE];
	private Point point = null;

	// private LinearLayout llLoad;

	public PlayView(Context context) {
		super(context);
		activity = (Activity) context;
		config(R.layout.play);
	}

	public PlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.play);
	}

	public void config(int resource_layouy) {
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		LinearLayout01 = (LinearLayout) findViewById(R.id.LinearLayout01);
		img1 = getImageView(R.id.imageView1);
		img2 = getImageView(R.id.imageView2);
		img3 = getImageView(R.id.imageView3);
		img1.post(new Runnable() {
			public void run() {
				int height = img1.getHeight();
				setRec(R.id.LinearLayout03, height, height);
				setRec(R.id.LinearLayout04, height, height);
				setRec(R.id.linearLayout3, height, height);
				int height1 = LinearLayout01.getHeight();
				if (LinearLayout01.getWidth() / 9 < height1) {
					height1 = LinearLayout01.getWidth() / 9;
				}
				setRec(R.id.linearLayout5, height1 * 3, height1);
				setRec(R.id.linearLayout4, height1 * 3, height1);
				setRec(R.id.linearLayout6, height1 * 3, height1);
			}
		});
		// viewRelativeLayout = getRelativeLayout(R.id.RelativeLayout2);
		// llLoad = (LinearLayout) findViewById(R.id.llLoad);

		String adUnitId = "a14edad3d74598a";
		AdView adView = new AdView(activity, AdSize.BANNER, adUnitId);
		AdRequest request = new AdRequest();
		request.setTesting(true);
		adView.loadAd(request);
		getLinearLayout(R.id.linearLayout1).addView(adView);
		findViewById(R.id.button3).setOnClickListener(this);
		itemRowView1[0] = (ItemRowView) findViewById(R.id.itemRowView1);
		itemRowView1[1] = (ItemRowView) findViewById(R.id.itemRowView2);
		itemRowView1[2] = (ItemRowView) findViewById(R.id.itemRowView3);
		itemRowView1[3] = (ItemRowView) findViewById(R.id.itemRowView4);
		itemRowView1[4] = (ItemRowView) findViewById(R.id.itemRowView5);
		itemRowView1[5] = (ItemRowView) findViewById(R.id.itemRowView6);
		itemRowView1[6] = (ItemRowView) findViewById(R.id.itemRowView7);
		itemRowView1[7] = (ItemRowView) findViewById(R.id.itemRowView8);
		itemRowView1[8] = (ItemRowView) findViewById(R.id.itemRowView9);
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				// itemRowView1[i].setOnClick(j, new OnItemClick(i, j, this));
			}
		}

		newGame();

	}

	public void newGame() {
		point = null;
		board.newBoard();
		updateBoard();

	}

	private void updateBoard() {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				itemRowView1[i].setImage(j, board.getResource(i, j));
			}
		}

		img1.setBackgroundResource(board.getRandomRes(0));
		img2.setBackgroundResource(board.getRandomRes(1));
		img3.setBackgroundResource(board.getRandomRes(2));
	}

	private void setRec(int res, int width, int height) {
		getLinearLayout(res).setLayoutParams(new LayoutParams(width, height));
	}

	public ImageView getImageView(int res) {
		return (ImageView) findViewById(res);
	}

	public LinearLayout getLinearLayout(int res) {
		return (LinearLayout) findViewById(res);
	}

	public RelativeLayout getRelativeLayout(int res) {
		return (RelativeLayout) findViewById(res);
	}

	public void onClick(View arg0) {
		if (arg0.getId() == R.id.button3) {
			newGame();
		}
	}

	public void onClick(int x, int y) {
		if (board.isCanClick(x, y)) {
			CommonView.makeText(activity, "Day1");
			point = new Point(x, y);
		} else {
			if (point != null) {
				CommonView.makeText(activity, "Day2");
				Point point2 = new Point(x, y);
				board.update(point, point2);
				updateBoard();
				point = null;
			}
		}
	}

}