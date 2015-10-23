package org.com.vnp.thioi;

import org.com.vnp.thioi.view.PlayView;
import org.com.vnp.thioi.view.StatusGame;
import org.com.vnp.thioi2.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ict.library.activity.CommonBaseActivity;
import com.ict.library.view.PauseView;

public class PlayActivity extends CommonBaseActivity {
	private RelativeLayout relativeLayout;
	private PauseView pauseView;
	private PlayView playView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		relativeLayout = getView(R.id.idCenter);
		createPositionView480_320(relativeLayout, 480, 320, 0, 0);
		playView = new PlayView(this) {
			@Override
			public void callBackEndGame() {
				configMessagePopUp(StatusGame.END);
			}

		};
		relativeLayout.addView(playView);
		createPositionView480_320(playView, 480, 320, 0, 0);

		ImageView imageView = new ImageView(this);
		imageView.setBackgroundResource(R.drawable.gianthi);
		relativeLayout.addView(imageView);
		createPositionView480_320(imageView, 480, 68, 0, 0);

		pauseView = new PauseView(this) {
			@Override
			public void pauseViewClose() {
				if (playView.getStatusGame() == StatusGame.END) {
					configMessagePopUp(StatusGame.END);
					finish();
				} else {
					configMessagePopUp(StatusGame.START);
				}
			}
		};

		relativeLayout.addView(pauseView);

		configMessagePopUp(StatusGame.CREATEGAME);
	}

	@Override
	protected void onPause() {
		if (playView.getStatusGame() == StatusGame.END) {
			configMessagePopUp(StatusGame.END);
		} else if (playView.getStatusGame() == StatusGame.CREATEGAME) {
			configMessagePopUp(StatusGame.CREATEGAME);
		} else {
			configMessagePopUp(StatusGame.PAUSE);
		}
		super.onPause();
	}

	@Override
	protected void onStop() {
		if (playView.getStatusGame() == StatusGame.END) {
			configMessagePopUp(StatusGame.END);
		} else if (playView.getStatusGame() == StatusGame.CREATEGAME) {
			configMessagePopUp(StatusGame.CREATEGAME);
		} else {
			configMessagePopUp(StatusGame.PAUSE);
		}
		super.onStop();
	}

	private void configMessagePopUp(StatusGame game) {
		playView.setStatusGame(game);
		if (playView.getStatusGame() == StatusGame.CREATEGAME) {
			pauseView.setVisibility(View.VISIBLE);
			pauseView.setTitle("start game");
			pauseView.setTitleButton("start");
		} else if (playView.getStatusGame() == StatusGame.PAUSE
				&& !isFinishing()) {
			pauseView.setVisibility(View.VISIBLE);
			pauseView.setTitle("pause game");
			pauseView.setTitleButton("resume");
		} else if (playView.getStatusGame() == StatusGame.END && !isFinishing()) {
			pauseView.setVisibility(View.VISIBLE);
			pauseView.setTitle("score : \n " + playView.score);
			pauseView.setTitleButton("save");
		} else if (playView.getStatusGame() == StatusGame.START) {
			pauseView.setVisibility(View.GONE);
		}
	}
}