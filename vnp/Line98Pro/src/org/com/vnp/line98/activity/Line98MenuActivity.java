package org.com.vnp.line98.activity;

import org.com.vnp.line98.asyn.AsynCopyDB;
import org.com.vnp.line98.asyn.NewGameAsyn;
import org.com.vnp.line98.asyn.RunAsyn;
import org.com.vnp.line98.model.Board;
import org.com.vnp.line98.model.Point;
import org.com.vnp.line98.onclick.OnItemClick;
import org.com.vnp.line98.views.ItemRowView;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class Line98MenuActivity extends Activity implements OnClickListener {
	private ImageView img1, img2, img3;
	// private RelativeLayout viewRelativeLayout;
	private LinearLayout LinearLayout01;
	private Board board = new Board();
	public ItemRowView itemRowView1[] = new ItemRowView[Board.SIZE];
	private Point point = null;
	private LinearLayout llLoad;
	private Handler handler = new Handler();
	private Animation mAnimation;
	private int score = 0;
	private TextView tVscore;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		config(R.layout.play);
	}

	public void config(int resource_layouy) {
		LinearLayout01 = (LinearLayout) findViewById(R.id.LinearLayout01);
		img1 = getImageView(R.id.imageView1);
		img2 = getImageView(R.id.imageView2);
		img3 = getImageView(R.id.imageView3);
		tVscore = (TextView) findViewById(R.id.textView1);
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
		llLoad = (LinearLayout) findViewById(R.id.llLoad);
		llLoad.setOnClickListener(null);
		String adUnitId = "a14edad3d74598a";
		AdView adView = new AdView(this, AdSize.BANNER, adUnitId);
		AdRequest request = new AdRequest();
		request.setTesting(true);
		adView.loadAd(request);
		getLinearLayout(R.id.linearLayout1).addView(adView);

		mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim2);

		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.button1).setOnClickListener(this);

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
				itemRowView1[i].setOnClick(j, new OnItemClick(i, j, this));
			}
		}
		new NewGameAsyn(this).execute("");
		new AsynCopyDB(this).execute("");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Builder builder = new Builder(this);
			builder.setTitle("Message");
			builder.setMessage("Do you close game?");
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});

			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			builder.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void newGame() {
		score = 0;
		Runnable runnable = new Runnable() {
			public void run() {
				point = null;
				showLoad(true);
				board.newBoard();
				updateBoard();
				showLoad(false);
				updateScore(0);
			}
		};

		handler.post(runnable);
	}

	public void updateBoard() {
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
			Builder builder = new Builder(this);
			builder.setTitle("Message");
			builder.setMessage("Do you new game?");
			builder.setNegativeButton("No", null);

			DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					new NewGameAsyn(Line98MenuActivity.this).execute("");
				}
			};

			builder.setPositiveButton("Yes", clickListener);
			builder.show();
		} else if (arg0.getId() == R.id.button1) {
			Intent intent = new Intent(this, ScoreActivity.class);
			startActivity(intent);
		}
	}

	public void onClick(int x, int y) {
		if (board.isCanClick(x, y)) {
			round(x, y);
		} else {
			if (point != null) {
				new RunAsyn(point, new Point(x, y), board, this).execute("");
			}
		}
	}

	private void round(int x, int y) {

		if (point != null) {
			itemRowView1[point.x].setImageRounte(point.y, mAnimation, false);
		}
		point = new Point(x, y);
		itemRowView1[point.x].setImageRounte(point.y, mAnimation, true);
	}

	public void showLoad(final boolean b) {
		handler.post(new Runnable() {

			public void run() {
				llLoad.setVisibility(b ? View.VISIBLE : View.GONE);
			}
		});

	}

	public void setNull() {
		point = null;
	}

	public void updateScore(int count) {
		score = score
				+ (count >= 5 ? (count * 5 + (count - 5) * (count - 5)) : 0);
		String s = "";
		if (score < 10) {
			s = "000000";
		} else if (score < 100) {
			s = "00000";
		} else if (score < 1000) {
			s = "0000";
		} else if (score < 10000) {
			s = "000";
		} else if (score < 100000) {
			s = "00";
		} else if (score < 1000000) {
			s = "0";
		}
		tVscore.setText(s + score);
	}

	public void lose() {
		Intent intent = new Intent(this, LoseActivity.class);
		intent.putExtra("ARG0", score + "");
		startActivityForResult(intent, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			new NewGameAsyn(this).execute("");
		} else if (true) {

		}
	}

}