package com.vnpgame.undersea.option;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.vnpgame.line98.R;
import com.vnpgame.undersea.music.Music;

public class OptionScreen extends Activity implements OnClickListener, OnCheckedChangeListener {
	boolean isback = false;
	CheckBox checkBox;
	CheckBox checkBox2;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option);
		findViewById(R.id.button1).setOnClickListener(this);
		checkBox = (CheckBox)findViewById(R.id.CheckBox01);
		checkBox2 = (CheckBox)findViewById(R.id.CheckBox02);
		checkBox2.setChecked(Music.runSoundBack);
		checkBox.setChecked(Music.runSoundEffect);
		checkBox.setOnCheckedChangeListener(this);
		checkBox2.setOnCheckedChangeListener(this);
	}

	protected void onPause() {
		super.onPause();
		if(!isback){
			Music.playerBackground.pause();
		}
	}
	
	protected void onRestart() {
		super.onRestart();
		if(Music.runSoundBack){
			Music.playerBackground.start();
		}
	}
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.button1){
			finish();
		}
	}

	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		if(arg0.getId() == checkBox2.getId()){
			Music.runSoundBack = checkBox2.isChecked();
			if(Music.runSoundBack){
				Music.playerBackground.start();
			}else{
				Music.playerBackground.pause();
			}
		}else{
			Music.runSoundEffect = checkBox.isChecked();
		}
	}

}
