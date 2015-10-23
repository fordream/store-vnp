package minh.app.mbook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MBook extends Activity {
	/** Called when the activity is first created. */

	private String text;
	private TextPaint myTextPaint;
	private int textSize = 20;
	private int textColor = 0xffA7573E;
	// int pageColor = 0xffFDF8A6;

	private int pageColor = 0x00000000;
	private int width, height, topPadding = 30, leftPadding = 10;
	// Vector<String> listOfPages = new Vector<String>();
	// TextProcess tp;
	private boolean done = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//
		Display display = ((WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();

		//
		//
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
				"fonts/UVNDaLat_R.TTF");

		myTextPaint = new TextPaint();
		myTextPaint.setColor(textColor);
		myTextPaint.setTextSize(textSize);
		myTextPaint.bgColor = pageColor;
		myTextPaint.setAntiAlias(true);
		myTextPaint.setTypeface(tf);

		// tp = new TextProcess(this, width, height, topPadding, leftPadding,
		// myTextPaint);

		// new LoadData(this).execute(tp);
		this.setContentView(new MBookView(this, width, height, topPadding,
				leftPadding, myTextPaint));
		// setContentView(R.layout.main);
	}

	public void onDestroy() {
		super.onDestroy();
		System.gc();
		finish();
	}
}