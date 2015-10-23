package org.cnc.smashpaddles.activity;

import org.cnc.smashpaddles.adapter.ConvertClass;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Wins extends Activity{

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		return;
	}

	private int totalMoney=6000;
	private int moneyEarn=80;
	private String moneyString="6000";
	private int[] numbers={10,10,10,10,10,10};
	private ConvertClass convertClass=new ConvertClass();
	ImageView position1Earn;
	ImageView position2Earn;
	private boolean check=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.win_screen);
		
		check=false;
		//username
		Typeface type=Typeface.createFromAsset(getAssets(), "fonts/Acme 9 Regular Bold.ttf");
		TextView leftUsernameWins=(TextView) findViewById(R.id.leftUsernameWins);
		leftUsernameWins.setTypeface(type);
		
		TextView rightUsernamWins=(TextView) findViewById(R.id.rightUserWins);
		rightUsernamWins.setTypeface(type);
		//score
		TextView leftScoreWins=(TextView) findViewById(R.id.leftScoreWins);
		leftScoreWins.setTypeface(type);
		
		TextView rightScoreWins=(TextView) findViewById(R.id.rightScoreWins);
		rightScoreWins.setTypeface(type);
		
		TextView winUsername=(TextView) findViewById(R.id.winUsername);
		winUsername.setTypeface(type);
		
		Button rematchButton=(Button) findViewById(R.id.rematchButton);
		rematchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(), PreGame.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		Button mainMenu=(Button) findViewById(R.id.mainMenuButton);
		mainMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),SplashScreen.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("check", false);
				startActivity(intent);
				finish();
			}
		});
		
		Button getCoinsWins=(Button) findViewById(R.id.getcoinWins);
		getCoinsWins.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getcoinsPopUp();
			}
		});
		Button freeCoinsWins=(Button) findViewById(R.id.freecoinsWins);
		freeCoinsWins.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(), FreeCoins.class);
				startActivity(intent);
			}
		});
		
		ImageView position1=(ImageView) findViewById(R.id.position1Wins);
		ImageView position2=(ImageView) findViewById(R.id.position2Wins);
		ImageView position3=(ImageView) findViewById(R.id.position3Wins);
		ImageView position4=(ImageView) findViewById(R.id.position4Wins);
		ImageView position5=(ImageView) findViewById(R.id.position5Wins);
		ImageView position6=(ImageView) findViewById(R.id.position6Wins);
		position1Earn=(ImageView) findViewById(R.id.position1Earn);
		position2Earn=(ImageView) findViewById(R.id.position2Earn);
		
		for(int i=0;i<moneyString.length();i++)
		{
			numbers[i]=Integer.parseInt(moneyString.substring(i, i+1));
		}
		//convert total money to image
		if(numbers[0]!=10)
		{
			position1.setImageResource(convertClass.convertNumberToImageOrange(numbers[0]));
		}
		if(numbers[1]!=10)
		{
			position2.setImageResource(convertClass.convertNumberToImageOrange(numbers[1]));
		}
		if(numbers[2]!=10)
		{
			position3.setImageResource(convertClass.convertNumberToImageOrange(numbers[2]));
		}
		if(numbers[3]!=10)
		{
			position4.setImageResource(convertClass.convertNumberToImageOrange(numbers[3]));
		}
		if(numbers[4]!=10)
		{
			position5.setImageResource(convertClass.convertNumberToImageOrange(numbers[4]));
		}
		if(numbers[5]!=10)
		{
			position6.setImageResource(convertClass.convertNumberToImageOrange(numbers[5]));
		}
		
		drawEarn(Integer.toString(moneyEarn));
		
		
		
	}
	
	 public void getcoinsPopUp() {
			final Dialog dialog=new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
			dialog.setContentView(R.layout.getcoins_popup);
			
			Button circleXGetcoins=(Button) dialog.findViewById(R.id.circleXGetCoins);
			circleXGetcoins.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			dialog.show();
			
	}
	
	 public void drawEarn(String moneyEarnString) {
		int[] earnnumber={10,10};
		for(int i=0;i<moneyEarnString.length();i++)
		{
			earnnumber[i]=Integer.parseInt(moneyEarnString.substring(i, i+1));
		}
		position1Earn.setImageResource(R.drawable.blank);
		position2Earn.setImageResource(R.drawable.blank);
		
		if(earnnumber[0]!=10)
		{
			position1Earn.setImageResource(convertClass.convertNumberToImageBig(earnnumber[0]));
		}
		if(earnnumber[1]!=10)
		{
			position2Earn.setImageResource(convertClass.convertNumberToImageBig(earnnumber[1]));
		}
	}
}
