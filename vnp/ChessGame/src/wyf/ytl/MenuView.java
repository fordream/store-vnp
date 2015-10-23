package wyf.ytl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 
 * The class is MenuView
 * 
 */
@SuppressLint("WrongCall")
public class MenuView extends LinearLayout {
	ChessActivity activity;

	public MenuView(Context context, ChessActivity activity1) { // Constructors
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.menuview, this);
		this.activity = activity1;

		findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						activity.myHandler.sendEmptyMessage(2);
					}
				});
		activity.myHandler.sendEmptyMessage(2);
	}

}