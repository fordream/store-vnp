package com.icts.shortfilmfestival.tabgroup;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.icts.shortfilmfestival.adapter.AppsAdapter;
import com.icts.shortfilmfestival.entity.AppsEntity;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival_en.MainTabActivity;
import com.vnp.shortfilmfestival.R;

public class AppsTab extends Activity implements OnItemClickListener{
	
	private GridView mGridView;
	private ArrayList<AppsEntity> mAppsArrayList;
	
	 public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   setContentView(R.layout.list_apps);
		   mGridView = (GridView) findViewById(R.id.gridview);
		   mGridView.setOnItemClickListener(this);
		   mAppsArrayList = new ArrayList<AppsEntity>();
		   
		   for (int i = 0; i < ISettings.LIST_APPS.length; i++)
		   {
			   String strItem =ISettings.LIST_APPS[i];
			   String[] arrayItem = strItem.split("_");
			   AppsEntity mAppsEntity = new AppsEntity();
			   mAppsEntity.setmName(arrayItem[0]);
			   mAppsEntity.setmImage(ISettings.LIST_LOGO_APP[i]);
			   if (ISettings.LANGUAGE.equals("ja"))
			   {
				   mAppsEntity.setmDesc(arrayItem[2]);
			   }
			   else if (ISettings.LANGUAGE.equals("en"))
			   {
				   mAppsEntity.setmDesc(arrayItem[3]);
			   }
			   Log.d("Apps",mAppsEntity.getmDesc());
			   mAppsEntity.setmPrice(arrayItem[4]);
			   mAppsEntity.setUrl(arrayItem[5]);
			   mAppsArrayList.add(mAppsEntity);
		   }
		   AppsAdapter mAppsAdapter = new AppsAdapter(this, mAppsArrayList);
		   mGridView.setAdapter(mAppsAdapter);
	 }
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(mAppsArrayList.get(position).getUrl()));
		startActivity(intent);
	}
	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MainTabActivity.isFromApps = false;
	}
	
	 
		protected void onDestroy() {
			// TODO Auto-generated method stub
		 	System.gc();
			super.onDestroy();
		}
}
