package org.cnc.smashpaddles.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.cnc.smashpaddles.activity.PreGame;
import org.cnc.smashpaddles.activity.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater=null;
	private HashMap<String, String> player=new HashMap<String, String>();
	
	public FriendsAdapter(Activity a,ArrayList<HashMap<String, String>> d) {
		super();
		// TODO Auto-generated constructor stub
		activity=a;
		data=d;
		inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi=convertView;
		if(convertView==null)
			vi=inflater.inflate(R.layout.list_row, null);
		
		ImageView firstSymbol=(ImageView) vi.findViewById(R.id.firstSymbol);
		ImageView secondSymbol=(ImageView) vi.findViewById(R.id.secondSymbol);
		TextView namePlayer=(TextView) vi.findViewById(R.id.namePlayer);
		Button challengeButton=(Button) vi.findViewById(R.id.challengeButton);
		
		
		player= data.get(position);
		
		//set first symbol
//		if(player.get("firstsymbol").equalsIgnoreCase("blue"))
//		{
//			firstSymbol.setImageResource(R.drawable.blue_box);
//		}
//		else if(player.get("firstsymbol").equalsIgnoreCase("orange")) {
//			firstSymbol.setImageResource(R.drawable.orange_box);
//		}
//		else if (player.get("firstsymbol").equalsIgnoreCase("red")) {
//			firstSymbol.setImageResource(R.drawable.red_box);
//		}
//		else if (player.get("firstsymbol").equalsIgnoreCase("yellow")) {
//			firstSymbol.setImageResource(R.drawable.yellow_box);
//		}
		

		challengeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(player.get("button").equalsIgnoreCase("challenge"))
				{
					Intent intent=new Intent(activity,PreGame.class);
					activity.startActivity(intent);
					//activity.finish();
				}
				
			}
		});
		
		secondSymbol.setImageResource(R.drawable.ready_notion);
		//set second symbol
		if(player.get("secondsymbol").equalsIgnoreCase("busy"))
		{
			secondSymbol.setImageResource(R.drawable.busy_notion);
		}
		else if (player.get("secondsymbol").equalsIgnoreCase("ready")) {
			secondSymbol.setImageResource(R.drawable.ready_notion);
		}
		else if (player.get("secondsymbol").equalsIgnoreCase("offline")) {
			secondSymbol.setImageResource(R.drawable.offline_notion);
		}
		//set button
		if(player.get("button").equalsIgnoreCase("invite"))
		{
			challengeButton.setBackgroundResource(R.drawable.invite);
		}
		
		Typeface type=Typeface.createFromAsset(activity.getAssets(), "fonts/Acme 9 Regular Bold.ttf");
		namePlayer.setTypeface(type);
		namePlayer.setText(player.get("name"));
		
		
		
		
		return vi;
	}

}
