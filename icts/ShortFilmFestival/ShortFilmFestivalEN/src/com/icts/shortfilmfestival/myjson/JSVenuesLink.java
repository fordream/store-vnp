package com.icts.shortfilmfestival.myjson;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.icts.shortfilmfestival.entity.MovieEntity;
import com.icts.shortfilmfestival.inf.ISettings;

public class JSVenuesLink {
	private static final String TAG = "LOG_JSNews";
	private int total;
	

	private ArrayList<String> mArrayListVenuesLink;
	
	
	public ArrayList<String> getmArrayListVenuesLink(String dataJson) {
		JSONObject mJSONObjectResponse = null;
		try {
			mJSONObjectResponse = new JSONObject(dataJson);
			//Log.d("ABC", mJSONObjectResponse.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (mJSONObjectResponse != null)
		{
		
			mArrayListVenuesLink = new ArrayList<String>();
			JSONArray mJSONArray = null;
			try {
				mJSONArray = new JSONArray(mJSONObjectResponse.getString("data"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (mJSONArray != null)
			{
				for (int i = 0; i < mJSONArray.length(); i++)
				{
					try {
						String mUrl = (String) mJSONArray.get(i);
						Log.d(TAG, mUrl);
						mArrayListVenuesLink.add(mUrl);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mArrayListVenuesLink;
	}
}
