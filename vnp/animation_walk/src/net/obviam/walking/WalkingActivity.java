package net.obviam.walking;

import net.obviam.walking.surfaceview.MainGamePanel;
import net.obviam.walking.tuong1pc.TActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class WalkingActivity extends Activity {
	private MainGamePanel gamePanel;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		gamePanel = new MainGamePanel(this);
		setContentView(gamePanel);
	
	
		startActivity(new Intent(this, TActivity.class));
	}

	protected void onDestroy() {
		//gamePanel.setStop();
		super.onDestroy();
	}
}