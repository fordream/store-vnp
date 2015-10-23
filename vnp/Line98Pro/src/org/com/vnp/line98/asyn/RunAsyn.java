package org.com.vnp.line98.asyn;

import java.util.List;

import org.com.vnp.line98.activity.Line98MenuActivity;
import org.com.vnp.line98.model.Board;
import org.com.vnp.line98.model.Point;

import android.os.AsyncTask;
import android.os.Handler;

public class RunAsyn extends AsyncTask<String, String, String> {
	private Point point, point2;
	private Board board;
	private Line98MenuActivity activity;
	private Handler handler = new Handler();
	private long TIME = 200;

	public RunAsyn(Point point, Point point2, Board board,
			Line98MenuActivity activity) {
		this.point = point;
		this.point2 = point2;
		this.board = board;
		this.activity = activity;
	}

	List<Point> lPoints;

	protected String doInBackground(String... params) {

		activity.showLoad(true);
		lPoints = board.findWay(point, point2);

		if (lPoints.size() > 1) {
			handler.post(new Runnable() {
				public void run() {
					// lPoints = board.findWay(point, point2);
					for (int i = lPoints.size() - 1; i >= 0; i--) {
						final Point point1 = lPoints.get(i);
						activity.itemRowView1[point1.x].setImage(point1.y,
								board.getResource(point.x, point.y));

					}
					board.update(point, point2);

				}
			});
			sleep(TIME);
			handler.post(new Runnable() {
				public void run() {
					activity.updateBoard();
				}
			});
			sleep(TIME);
			List<Point> list = board.getEat();
			if (list.size() >= 5) {
				board.update(list);
				sleep(TIME);
				final int count= list.size();
				handler.post(new Runnable() {
					public void run() {
						activity.updateBoard();
						activity.updateScore(count);
					}
				});
			} else {
				board.growBoard();
				board.nextBoard();
				board.nextRandom();
				sleep(TIME);
				handler.post(new Runnable() {
					public void run() {
						activity.updateBoard();
					}
				});
				list = board.getEat();
				if (list.size() >= 5) {
					board.update(list);
					sleep(TIME);
					final int count= list.size();
					handler.post(new Runnable() {
						public void run() {
							activity.updateBoard();
							activity.updateScore(count);
						}
					});
				}
			}

			activity.setNull();
			point = null;
		}


		activity.showLoad(false);

		return null;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (board.countSpace() <= 3) {
			activity.lose();
		}
	}

	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

}
