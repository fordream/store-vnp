package org.com.vnp.thioi;

import org.com.vnp.thioi2.R;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.ict.library.activity.CommonAppListActivity;
import com.ict.library.activity.CommonBaseActivity;
import com.ict.library.common.CommonResize;
import com.ict.library.common.CommonScreenAction;
import com.vnp.core.baseapp.CommonGameAudio;
import com.vnp.core.basegame.CommonHelpListActivity;
import com.vnp.core.basegame.CommonScoreListActivity;

public class MainActivity extends CommonBaseActivity implements OnClickListener {
	private RelativeLayout layout;
	private Button button[] = new Button[5];
	private CommonGameAudio commonGameAudio;
	private CommonScreenAction commonScreenAction;
	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = getView(R.id.idMain);

		button[0] = getView(R.id.idMainHome);
		button[1] = getView(R.id.idMainNew);
		button[2] = getView(R.id.idMainPlay);
		button[3] = getView(R.id.idMainScore);
		button[4] = getView(R.id.idMainHelp);

		int size = CommonResize.getSizeByScreen960(this, 30);

		button[0].setTextSize(size);
		button[1].setTextSize(size);
		button[2].setTextSize(size);
		button[3].setTextSize(size);
		button[4].setTextSize(size);

		button[0].setVisibility(View.GONE);
		button[1].setVisibility(View.GONE);
		button[3].setVisibility(View.GONE);
		button[4].setVisibility(View.GONE);

		AnimationDrawable animation = (AnimationDrawable) button[2]
				.getBackground();
		animation.start();

		CommonResize.resizeLandW480H320(layout, 480, 320);

		// for main layout
		createPositionView480_320(layout, 480, 320, 0, 0);

		// for menu button
		createPositionView480_320(button[0], 60, 60, 20, 20);
		createPositionView480_320(button[1], 60, 60, 395, 20);

		createPositionView480_320(button[2], 117, 90, 250, 200);
		createPositionView480_320(button[3], 70, 70, 215, 160);
		createPositionView480_320(button[4], 70, 70, 300, 160);

		for (Button button1 : button) {
			button1.setOnClickListener(this);
		}

		// CommonScreenAction commonScreenAction = new CommonScreenAction(this)
		// {
		//
		// @Override
		// public void screenOn() {
		// CommonLog.e("AAAA", "on");
		// }
		//
		// @Override
		// public void screenOff() {
		// CommonLog.e("AAAA", "off");
		// }
		// };
		//
		// commonScreenAction.register();

		commonGameAudio = new CommonGameAudio(this);
		commonGameAudio.init(new int[] { R.raw.login_theme });
		commonGameAudio.start(0, true);

		commonScreenAction = new CommonScreenAction(this) {

			@Override
			public void screenUnlock() {
				if (isRestricted()) {
					commonGameAudio.start(0, true);
				}
			}

			@Override
			public void screenOnHaveLock() {

			}

			@Override
			public void screenOn() {
				if (isRestricted()) {
					commonGameAudio.start(0, true);
				}
			}

			@Override
			public void screenOff() {
				commonGameAudio.pause(0);
			}
		};

		commonScreenAction.register();

		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
		adView = new AdView(this, AdSize.BANNER, "a15181d6a63d8e4");

		// Add the adView to it
		layout.addView(adView);

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());

	}

	@Override
	protected void onResume() {
		super.onResume();

		// CommonBaseScore.getInstance().init(this);
		// CommonBaseScore.getInstance().save(
		// "a12sssssssssssssssssssssssssssssssssssssssssssss", 100);
		// CommonBaseScore.getInstance().save("a22", 200000000000000l);
		// CommonBaseScore.getInstance().save("aw", 100);
		// CommonBaseScore.getInstance().save("ar", 200000000000000l);
		// CommonBaseScore.getInstance().save(
		// "a12sssssssssssssssssssssssssssssssssssssssssssss", 100);
		// CommonBaseScore.getInstance().save("a22", 200000000000000l);
		if (!commonScreenAction.haveLockScreen()) {
			commonGameAudio.start(0, true);
		}
	}

	@Override
	protected void onPause() {
		commonGameAudio.pause(0);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		commonGameAudio.release();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (button[0] == v) {
			// home click
			finish();
		} else if (button[1] == v) {
			// newclick
			// Intent intent = new Intent(this, CommonAppListActivity.class);
			// startActivity(intent);

			startActivity(CommonAppListActivity.class);
			// deleteShortCut(this);
		} else if (button[2] == v) {
			// play click

			// Intent intent = new Intent(this, PlayActivity.class);
			// startActivity(intent);
			startActivity(PlayActivity.class);
			// autoCreateShortCut(true, this);
		} else if (button[3] == v) {
			// score click
			// Intent intent = new Intent(this, CommonScoreListActivity.class);
			// startActivity(intent);
			startActivity(CommonScoreListActivity.class);
		} else if (button[4] == v) {
			// help click
			// Intent intent = new Intent(this, CommonHelpListActivity.class);
			// startActivity(intent);
			startActivity(CommonHelpListActivity.class);
		}
	}

	private void startActivity(final Class clzz) {
		// MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(),
		// R.raw.m1);
		// mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
		// @Override
		// public void onCompletion(MediaPlayer mp) {
		if (!isFinishing()) {
			Intent intent = new Intent(MainActivity.this, clzz);
			startActivity(intent);
		}
		// }
		// });
		// mediaPlayer.start();

	}

}