package com.icts.utils;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * Get address from latitude and longitude using library map of android.
 * Return 3 values: street, district, city
 * after getting address, this info updated to server
 * @author Luong
 *
 */
public class GeocoderTask implements Runnable{

	private View v;
	private double lat,lng;
	protected Context context;
	private String userID,uuid;
	protected String[] result;
	public GeocoderTask(View v,double lat,double lng, Context context, String userID,String uuid) {
		this.v = v;
		this.lat = lat;
		this.lng = lng;
		this.context = context;
		this.userID = userID;
		this.uuid = uuid;
	}
	@Override
	public void run() {
		String[] info = Utils.geocoder(lat, lng, context);
		try {
			result = info;
			if (info!=null){
				finish(v,info);
				//info[1] = "Nguyễn Trãi";
				//info[0] = "東京　あじのもと";
				//info[2] = "日本";
				Utils.postLocation(userID, uuid, lat, lng, info[1], info[2], info[0]);
			}
			else {
				fail(v);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			error(e);
		}
	}
	
	protected void finish(View v,String[] result){
		
	}
	
	protected void fail(View v){
		
	}
	
	protected void error(Exception e){
		
	}
}
