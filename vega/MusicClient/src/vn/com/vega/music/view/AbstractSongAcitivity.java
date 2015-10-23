package vn.com.vega.music.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public abstract class AbstractSongAcitivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public abstract void onThumbnailClickListener(View v);
	public abstract void onCheckboxClickListener(View v);

}
