package org.cnc.smashpaddles.activity;

import org.cnc.smashpaddles.adapter.ConvertClass;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Game extends Activity {

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		return;
	}
	private int totalMoney=725;
	private String moneyString="6000";
	private int[] numbers={10,10,10,10,10,10};
	private ConvertClass convertClass=new ConvertClass();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.game_screen);
		
		Typeface type=Typeface.createFromAsset(getAssets(), "fonts/Acme 9 Regular Bold.ttf");
		TextView leftUsername=(TextView) findViewById(R.id.leftUsername);
		leftUsername.setTypeface(type);
		
		TextView rightUsername=(TextView) findViewById(R.id.rightUsername);
		rightUsername.setTypeface(type);
		
		TextView leftScore=(TextView) findViewById(R.id.leftScore);
		leftScore.setTypeface(type);
		
		TextView rightScore=(TextView) findViewById(R.id.rightScore);
		rightScore.setTypeface(type);
		
		Button btn=(Button) findViewById(R.id.longGame);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(), Wins.class);
				startActivity(intent);
				finish();
				
			}
		});
	}
}
