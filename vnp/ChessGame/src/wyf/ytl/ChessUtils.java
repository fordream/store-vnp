package wyf.ytl;

import wyf.ytl.model.Item;
import android.graphics.Bitmap;

public class ChessUtils {
	// 34
	public static int SIZE_1 = 34;

	// 35
	public static int SIZE_2 = 35;

	public static void updateSize(Item qiPan) {
		float width = 300;
		float height = 350;
		// 275,300
		SIZE_1 = qiPan.getBitMap().getWidth() / 9;
		SIZE_2 = qiPan.getBitMap().getHeight() / 10;

	}

	// 12
	public static final int SIZE_12 = 12;

	// 13
	public static final int SIZE_13 = 13;

}
