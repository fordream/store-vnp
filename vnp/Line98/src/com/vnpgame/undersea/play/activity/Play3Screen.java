package com.vnpgame.undersea.play.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.vnpgame.line98.R;
import com.vnpgame.undersea.music.Music;
import com.vnpgame.undersea.play.field.FieldPlay;

public class Play3Screen extends Activity {
	boolean isBack = false;
	
	protected void onPause() {
		super.onPause();
		if(!isBack){
			Music.playerBackground.pause();
		}
	}
	
	protected void onRestart() {
		super.onRestart();
		if(Music.runSoundBack){
			Music.playerBackground.start();
		}
	}
	private FieldPlay fieldPlay;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play3);
		fieldPlay = new FieldPlay(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Music.playerBackground();
		if (requestCode == 0 && resultCode == RESULT_OK) {
			fieldPlay.nextLevel();
		}
		if (requestCode == 1 && resultCode == RESULT_OK) {
			finish();
		}
	}
}
