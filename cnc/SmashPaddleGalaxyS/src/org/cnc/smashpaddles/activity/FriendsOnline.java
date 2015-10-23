package org.cnc.smashpaddles.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.cnc.smashpaddles.adapter.ConvertClass;
import org.cnc.smashpaddles.adapter.FriendsAdapter;
import org.cnc.smashpaddles.adapter.VerticalSeekBar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class FriendsOnline extends Activity {
	
	Typeface type;
	
	private String moneyString="6000";
	private int[] numbers={10,10,10,10,10,10};
	private ConvertClass convertClass=new ConvertClass();

	private String[] firstSymbolArray=new String[]{"orange","yellow","blue","orange","red","orange","yellow","blue","orange","red","orange","yellow","blue","orange","red","orange","yellow","blue","orange","red"};
	private String[] secondSymbolArray=new String[]{"busy","available","available","busy","busy","busy","available","available","busy","busy","busy","available","available","busy","busy","busy","available","available","busy","busy"};
	private String[] nameArray=new String[]{"Bob","Lan","Jessica","John","David","Bob","Lan","Jessica","John","David","Bob","Lan","Jessica","John","David","Bob","Lan","Jessica","John","David"};
	
	private ArrayList<HashMap<String, String>> onlinePlayers=new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String, String>> offlinePlayers=new ArrayList<HashMap<String,String>>();
	
	private FriendsAdapter friendsOnlineAdapter;
	private FriendsAdapter friendsOfflineAdapter;
	private int totalMoney=6000;
	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.friendsonline_screen);
		
		ImageView position1=(ImageView) findViewById(R.id.position1Friends);
		ImageView position2=(ImageView) findViewById(R.id.position2Friends);
		ImageView position3=(ImageView) findViewById(R.id.position3Friends);
		ImageView position4=(ImageView) findViewById(R.id.position4Friends);
		ImageView position5=(ImageView) findViewById(R.id.position5Friends);
		ImageView position6=(ImageView) findViewById(R.id.position6Friends);
		
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
		
		type=Typeface.createFromAsset(getAssets(), "fonts/Helvetica Rounded LT Bold Condensed.ttf");
		
		activity=this;
		
		for(int i=0;i<secondSymbolArray.length;i++)
		{
			HashMap<String, String> onlineplayer=new HashMap<String, String>();
			HashMap<String, String> offlineplayer=new HashMap<String, String>();
			
			onlineplayer.put("firstsymbol", firstSymbolArray[i]);
			onlineplayer.put("secondsymbol", secondSymbolArray[i]);
			onlineplayer.put("name", nameArray[i]);
			onlineplayer.put("button", "challenge");
			
			onlinePlayers.add(onlineplayer);
			
			offlineplayer.put("firstsymbol", firstSymbolArray[i]);
			offlineplayer.put("secondsymbol", "offline");
			offlineplayer.put("name", nameArray[i]);
			offlineplayer.put("button", "invite");
			
			offlinePlayers.add(offlineplayer);
		}
		
		friendsOnlineAdapter=new FriendsAdapter(activity, onlinePlayers);
		friendsOfflineAdapter=new FriendsAdapter(activity, offlinePlayers);
		
		final ListView onineListView=(ListView) findViewById(R.id.onlineListView);
		onineListView.setAdapter(friendsOnlineAdapter);
		
		final ListView offlineListView=(ListView) findViewById(R.id.offlineListView);
		offlineListView.setAdapter(friendsOfflineAdapter);
		
		Button backFriends=(Button) findViewById(R.id.backFriends);
		backFriends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		final VerticalSeekBar verticalSeekbarOnline=(VerticalSeekBar) findViewById(R.id.verticalSeekBarOnline);
		verticalSeekbarOnline.setLv(onineListView);
		verticalSeekbarOnline.setContextMore(this);
		verticalSeekbarOnline.setMaximum(onineListView.getCount()+4);
		verticalSeekbarOnline.setProgressAndThumb(onineListView.getCount());
		verticalSeekbarOnline.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				
			}
		});
		
		final VerticalSeekBar verticalSeekbarOffline=(VerticalSeekBar) findViewById(R.id.verticalSeekBarOffline);
		verticalSeekbarOffline.setLv(offlineListView);
		verticalSeekbarOffline.setContextMore(this);
		verticalSeekbarOffline.setMaximum(offlineListView.getCount()+4);
		verticalSeekbarOffline.setProgressAndThumb(offlineListView.getCount());
		
		verticalSeekbarOffline.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				
			}
		});
		onineListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				verticalSeekbarOnline.setProgressAndThumb(onineListView.getCount()-onineListView.getFirstVisiblePosition());
				
			}
		});
		offlineListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
//				int L=offlineListView.getCount(); // L = 5
//				int x=offlineListView.getFirstVisiblePosition(); // x = 0
//				int y = (int)(x*L/(L-4)); // y = 0
//				System.out.println("L : " +  L + " x : " + x + " y  : " + y);
//				
//				Log.d("FriendsOnline","L : " +  L + " x : " + x + " y  : " + y);
//				//Log.INFO("abc");
//				verticalSeekbarOffline.setProgressAndThumb(L-y);
				verticalSeekbarOffline.setProgressAndThumb(offlineListView.getCount()-offlineListView.getFirstVisiblePosition());
				
			}
		});
		
	}

}
