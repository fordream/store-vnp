package wyf.ytl.model;

import wyf.ytl.ChessUtils;
import wyf.ytl.R;
import android.content.res.Resources;
import android.graphics.Point;

public class PlayItemExit extends Item {

	public PlayItemExit(Resources resources) {
		super(R.drawable.exit2, resources, new Point(
				10 + ChessUtils.SIZE_1 * 8, ChessUtils.SIZE_2 * 15));
	}
}
