package vn.vvn.bibook.util;

import java.util.ArrayList;

import android.graphics.PointF;

public class Polygon {
	private ArrayList<PointF> listPoint;
	private String pathSound;
	private String mNameSound;
	private int mColor;
	private int ccw(PointF p0, PointF p1, PointF p2) {
		float dx1, dx2, dy1, dy2;
		dx1 = p1.x - p0.x;
		dy1 = p1.y - p0.y;
		dx2 = p2.x - p0.x;
		dy2 = p2.y - p0.y;
		if (dx1 * dy2 > dy1 * dx2)
			return 1;
		if (dx1 * dy2 < dy1 * dx2)
			return -1;
		if ((dx1 * dx2 < 0) || (dy1 * dy2 < 0))
			return -1;
		if ((dx1 * dx1 + dy1 * dy1) < (dx2 * dx2 + dy2 * dy2))
			return 1;
		return 0;
	}

	private boolean intersect(Line l1, Line l2) {
		return ((ccw(l1.p1, l1.p2, l2.p1) * ccw(l1.p1, l1.p2, l2.p2)) < 0)
				&& ((ccw(l2.p1, l2.p2, l1.p1) * ccw(l2.p1, l2.p2, l1.p2)) < 0);
	}

	public boolean isInside(PointF t) {
		int i, count = 0;
		Line lt = new Line();
		Line lp = new Line();

		PointF t2 = new PointF(t.x, 4000);
		lt = new Line(t, t2);

		try {
			for (i = 0; i < listPoint.size(); i++) {
				lp.p1 = listPoint.get(i);
				if (i + 1 == listPoint.size())
					lp.p2 = listPoint.get(0);
				else
					lp.p2 = listPoint.get(i + 1);

				if (intersect(lt, lp)) {
					count++;
				}
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (count == 1)
			return true;
		return false;

	}

	public ArrayList<PointF> getListPoint() {
		return listPoint;
	}

	public void setListPoint(ArrayList<PointF> listPoint) {
		this.listPoint = listPoint;
	}

	public String getPathSound() {
		return pathSound;
	}

	public void setPathSound(String pathSound) {
		this.pathSound = pathSound;
	}

	/**
	 * @return the mNameSound
	 */
	public String getmNameSound() {
		return mNameSound;
	}

	/**
	 * @param mNameSound the mNameSound to set
	 */
	public void setmNameSound(String mNameSound) {
		this.mNameSound = mNameSound;
	}

	/**
	 * @return the mColor
	 */
	public int getmColor() {
		return mColor;
	}

	/**
	 * @param mColor the mColor to set
	 */
	public void setmColor(int mColor) {
		this.mColor = mColor;
	}

	class Line {
		PointF p1;
		PointF p2;

		public Line() {
			p1 = new PointF();
			p2 = new PointF();
		}

		public Line(PointF _p1, PointF _p2) {
			p1 = new PointF(_p1.x, _p1.y);
			p2 = new PointF(_p2.x, _p2.y);
		}
	}

}
