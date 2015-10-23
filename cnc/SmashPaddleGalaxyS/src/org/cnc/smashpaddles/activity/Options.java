package org.cnc.smashpaddles.activity;

import org.cnc.smashpaddles.adapter.ConvertClass;
import android.app.Activity;
import android.content.Context;
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

public class Options extends Activity {
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		return;
	}
	private String moneyString="6000";
	private int[] numbers={10,10,10,10,10,10};
	private ConvertClass convertClass=new ConvertClass();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.options_screen);
		//back button return splash screen
		Button backOptions=(Button) findViewById(R.id.backOptions);
		backOptions.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//ok button return splash screen
		Button okButton=(Button) findViewById(R.id.okOptions);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		ImageView position1=(ImageView) findViewById(R.id.position1Options);
		ImageView position2=(ImageView) findViewById(R.id.position2Options);
		ImageView position3=(ImageView) findViewById(R.id.position3Options);
		ImageView position4=(ImageView) findViewById(R.id.position4Options);
		ImageView position5=(ImageView) findViewById(R.id.position5Options);
		ImageView position6=(ImageView) findViewById(R.id.position6Options);
		
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
		
	}
}
