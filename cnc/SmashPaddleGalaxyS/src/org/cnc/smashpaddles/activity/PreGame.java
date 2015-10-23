package org.cnc.smashpaddles.activity;

import org.cnc.smashpaddles.adapter.ConvertClass;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PreGame extends Activity {
	@Override
	public void onBackPressed() {
		return;
	}
	private int totalMoney=6000;
	
	private int longNumber=0;
	private int stickyNumber=0;
	private int smashNumber=0;
	private int breakerNumber=0;
	private int blackoutNumber=0;
	
	private ImageView longAvailable;
	private ImageView longAvailable1;
	private ImageView stickyAvailable;
	private ImageView stickyAvailable1;
	private ImageView smashPaddleAvailable;
	private ImageView smashPaddleAvailable1;
	private ImageView breakerPaddleAvailable;
	private ImageView breakerPaddleAvailable1;
	private ImageView blackoutAvailable;
	private ImageView blackoutAvailable1;
	
	private int[] longNumbers={10,10};
	private int[] stickyNumbers={10,10};
	private int[] smashNumbers={10,10};
	private int[] breakerNumbers={10,10};
	private int[] blackoutNumbers={10,10};
	ImageView position1;
	ImageView position2;
	ImageView position3;
	ImageView position4;
	ImageView position5;
	ImageView position6;
	
	private ConvertClass convertClass=new ConvertClass();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pregame_screen);
		//draw money
		position1=(ImageView) findViewById(R.id.position1);
		position2=(ImageView) findViewById(R.id.position2);
		position3=(ImageView) findViewById(R.id.position3);
		position4=(ImageView) findViewById(R.id.position4);
		position5=(ImageView) findViewById(R.id.position5);
		position6=(ImageView) findViewById(R.id.position6);
		longAvailable=(ImageView) findViewById(R.id.longAvailable);
		longAvailable1=(ImageView) findViewById(R.id.longAvailable1);
		stickyAvailable=(ImageView) findViewById(R.id.stickyAvailable);
		stickyAvailable1=(ImageView) findViewById(R.id.stickyAvailable1);
		smashPaddleAvailable=(ImageView) findViewById(R.id.smashpaddleAvailable);
		smashPaddleAvailable1=(ImageView) findViewById(R.id.smashAvailable1);
		breakerPaddleAvailable=(ImageView) findViewById(R.id.breakerpaddleAvailable);
		breakerPaddleAvailable1=(ImageView) findViewById(R.id.breakerAvailable1);
		blackoutAvailable=(ImageView) findViewById(R.id.blackoutAvailable);
		blackoutAvailable1=(ImageView) findViewById(R.id.blackoutAvailable1);
		//total money 
		drawMoney(Integer.toString(totalMoney));
		drawLong(Integer.toString(longNumber));
		drawSticky(Integer.toString(stickyNumber));
		drawSmash(Integer.toString(smashNumber));
		drawBreaker(Integer.toString(breakerNumber));
		drawBlackout(Integer.toString(blackoutNumber));
		//long add button
		Button longPaddleButton=(Button) findViewById(R.id.longPaddleButton);
		longPaddleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(totalMoney>=25&&longNumber<99)
				{
					totalMoney=totalMoney-25;
					longNumber=longNumber+1;
					drawMoney(Integer.toString(totalMoney));
					drawLong(Integer.toString(longNumber));
					
				}
				
			}
		});
		
		
		//sticky add button
		Button stickyPaddleButton=(Button) findViewById(R.id.stickyPaddleButton);
		stickyPaddleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(totalMoney>=15&&stickyNumber<99)
				{
					totalMoney=totalMoney-15;
					stickyNumber=stickyNumber+1;
					drawMoney(Integer.toString(totalMoney));
					drawSticky(Integer.toString(stickyNumber));
				}
				
			}
		});
		Button smashPaddleButton=(Button) findViewById(R.id.smashPaddleButton);;
		smashPaddleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(totalMoney>=35&&smashNumber<99)
				{
					totalMoney=totalMoney-35;
					smashNumber=smashNumber+1;
					drawMoney(Integer.toString(totalMoney));
					drawSmash(Integer.toString(smashNumber));
				}
				
			}
		});
		
		
		//button breaker add 
		Button breakerPaddleButton=(Button) findViewById(R.id.breakerPaddleButton);
		breakerPaddleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(totalMoney>=40&&breakerNumber<99)
				{
					totalMoney=totalMoney-40;
					breakerNumber=breakerNumber+1;
					drawMoney(Integer.toString(totalMoney));
					drawBreaker(Integer.toString(breakerNumber));
				}
				
			}
		});
	
		Button blackoutPaddleButton=(Button) findViewById(R.id.blackoutPaddleButton);
		blackoutPaddleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(totalMoney>=50&&blackoutNumber<99)
				{
					totalMoney=totalMoney-50;
					blackoutNumber=blackoutNumber+1;
					drawMoney(Integer.toString(totalMoney));
					drawBlackout(Integer.toString(blackoutNumber));
				}
				
			}
		});
		//golden add button
		Button goldenPaddleButton=(Button) findViewById(R.id.goldenPaddleButton);
		goldenPaddleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(totalMoney>=5000)
				{
					totalMoney=totalMoney-5000;
					drawMoney(Integer.toString(totalMoney));
					
				}
			}
		});
		// back splash screen
		Button backButton=(Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				PreGame.this.finish();
			}
		});
		// go to game screen
		Button readyButton=(Button) findViewById(R.id.readyButton);
		readyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getApplicationContext(),Game.class);
				startActivity(intent);
				finish();
			}
		});
		//detail all boosts
		
		Button longDetail=(Button) findViewById(R.id.longDetail);
		longDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showLongDetail();
			}
		});
		
		Button stickyDetail=(Button) findViewById(R.id.stickyDetail);
		stickyDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showStickyDetail();
			}
		});
		
		Button smashDetail=(Button) findViewById(R.id.smashDetail);
		smashDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showSmashDetail();
			}
		});
		
		Button breakerDetail=(Button) findViewById(R.id.breakerDetail);
		breakerDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showBreakerDetail();
			}
		});
		
		Button blackoutDetail=(Button) findViewById(R.id.blackoutDetail);
		blackoutDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showBlackoutDetail();
			}
		});
		
		Button goldenDetail=(Button) findViewById(R.id.goldenDetail);
		goldenDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showGoldenDetail();
			}
		});
	}
	//pop up all boosts
	
	public void showGoldenDetail() {
		final Dialog dialog=new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.golden_detail);
		
		Button goldenClose=(Button) dialog.findViewById(R.id.goldenClose);
		goldenClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		dialog.show();
		
	}
	
	public void showLongDetail() {
		final Dialog dialog=new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.long_detail);
		
		Button longClose=(Button) dialog.findViewById(R.id.longClose);
		longClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	public void showStickyDetail() {
		final Dialog dialog=new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.sticky_detail);
		
		Button stickyClose=(Button) dialog.findViewById(R.id.stickyClose);
		stickyClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
		
	}
	
	public void showSmashDetail() {
		final Dialog dialog=new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.smash_detail);
		
		Button smashClose=(Button) dialog.findViewById(R.id.smashClose);
		smashClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	public void showBreakerDetail() {
		final Dialog dialog=new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.breaker_detail);
		
		Button breakerClose=(Button) dialog.findViewById(R.id.breakerClose);
		breakerClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	public void showBlackoutDetail() {
		final Dialog dialog=new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.blackout_detail);
		
		Button blackoutClose=(Button) dialog.findViewById(R.id.blackoutClose);
		blackoutClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	public void drawLong(String longString) {
		for (int i = 0; i < longString.length(); i++) {
			longNumbers[i]=Integer.parseInt(longString.substring(i,i+1));
		}
		longAvailable.setImageResource(R.drawable.small_blank);
		longAvailable1.setImageResource(R.drawable.small_blank);
		if(longNumbers[0]!=10)
		{
			longAvailable.setImageResource(convertClass.convertNumberToImageBlue(longNumbers[0]));
		}
		if(longNumbers[1]!=10)
		{
			longAvailable1.setImageResource(convertClass.convertNumberToImageBlue(longNumbers[1]));
		}
		
	}
	public void drawSticky(String stickyString) {
		for (int i = 0; i < stickyString.length(); i++) {
			stickyNumbers[i]=Integer.parseInt(stickyString.substring(i,i+1));
		}
		stickyAvailable.setImageResource(R.drawable.small_blank);
		stickyAvailable1.setImageResource(R.drawable.small_blank);
		if(stickyNumbers[0]!=10)
		{
			stickyAvailable.setImageResource(convertClass.convertNumberToImageBlue(stickyNumbers[0]));
		}
		if(stickyNumbers[1]!=10)
		{
			stickyAvailable1.setImageResource(convertClass.convertNumberToImageBlue(stickyNumbers[1]));
		}
	}
	public void drawSmash(String smashString) {
		for (int i = 0; i < smashString.length(); i++) {
			smashNumbers[i]=Integer.parseInt(smashString.substring(i,i+1));
		}
		smashPaddleAvailable.setImageResource(R.drawable.small_blank);
		smashPaddleAvailable1.setImageResource(R.drawable.small_blank);
		if(smashNumbers[0]!=10)
		{
			smashPaddleAvailable.setImageResource(convertClass.convertNumberToImageBlue(smashNumbers[0]));
		}
		if(smashNumbers[1]!=10)
		{
			smashPaddleAvailable1.setImageResource(convertClass.convertNumberToImageBlue(smashNumbers[1]));
		}
	}
	public void drawBreaker(String breakerString) {
		for (int i = 0; i < breakerString.length(); i++) {
			breakerNumbers[i]=Integer.parseInt(breakerString.substring(i,i+1));
		}
		breakerPaddleAvailable.setImageResource(R.drawable.small_blank);
		breakerPaddleAvailable1.setImageResource(R.drawable.small_blank);
		if(breakerNumbers[0]!=10)
		{
			breakerPaddleAvailable.setImageResource(convertClass.convertNumberToImageBlue(breakerNumbers[0]));
		}
		if(breakerNumbers[1]!=10)
		{
			breakerPaddleAvailable1.setImageResource(convertClass.convertNumberToImageBlue(breakerNumbers[1]));
		}
	}
	public void drawBlackout(String blackoutString) {
		for (int i = 0; i < blackoutString.length(); i++) {
			blackoutNumbers[i]=Integer.parseInt(blackoutString.substring(i,i+1));
		}
		blackoutAvailable.setImageResource(R.drawable.small_blank);
		blackoutAvailable1.setImageResource(R.drawable.small_blank);
		if(blackoutNumbers[0]!=10)
		{
			blackoutAvailable.setImageResource(convertClass.convertNumberToImageBlue(blackoutNumbers[0]));
			
		}
		if(blackoutNumbers[1]!=10)
		{
			blackoutAvailable1.setImageResource(convertClass.convertNumberToImageBlue(blackoutNumbers[1]));
		}
	}
	public void drawMoney(String money) {
		int[] numbers={10,10,10,10,10,10};
		for(int i=0;i<money.length();i++)
		{
			numbers[i]=Integer.parseInt(money.substring(i, i+1));
		}
		position1.setImageResource(R.drawable.small_blank);
		position2.setImageResource(R.drawable.small_blank);
		position3.setImageResource(R.drawable.small_blank);
		position4.setImageResource(R.drawable.small_blank);
		position5.setImageResource(R.drawable.small_blank);
		position6.setImageResource(R.drawable.small_blank);
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
	}

}
