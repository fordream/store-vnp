package com.icts.shortfilmfestival.myjson;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.icts.shortfilmfestival.entity.MovieEntity;
import com.icts.shortfilmfestival.inf.ISettings;

public class JSNewsTop {
	private static final String TAG = "LOG_JSNewsTop";
	

	private MovieEntity mMovieEntity;
	
	
	public MovieEntity getmMovieEntity(String dataJson) {
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
			Log.d(TAG,  "-----------");
			mMovieEntity = new MovieEntity();
//			JSONArray mJSONArray = null;
//			try {
//				mJSONArray = new JSONArray(mJSONObjectResponse.getString("data"));
//				Log.d(TAG,  mJSONArray.length() + "");
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if (mJSONObjectResponse != null)
			{
				
					try {
						JSONObject mJSONObject = new JSONObject(mJSONObjectResponse.getString("data"));
						mMovieEntity.setId(mJSONObject.getString("videoId"));
						Log.d(TAG, mMovieEntity.getId() + "");
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
			}
		}
		return mMovieEntity;
	}
}
