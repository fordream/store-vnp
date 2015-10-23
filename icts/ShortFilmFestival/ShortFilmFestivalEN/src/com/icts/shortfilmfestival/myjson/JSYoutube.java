package com.icts.shortfilmfestival.myjson;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.icts.shortfilmfestival.entity.MovieEntity;
import com.icts.shortfilmfestival.inf.ISettings;

public class JSYoutube {
	private static final String TAG = "LOG_JSNews";
	private int total;
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private ArrayList<MovieEntity> mArrayListMovieEntity;
	private int type;
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public JSYoutube(int pType)
	{
		this.type = pType;
	}
	
	public ArrayList<MovieEntity> getmArrayListMovieEntity(String dataJson) {
		Log.d(TAG, "---------Type--------" + this.type);
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
		
			mArrayListMovieEntity = new ArrayList<MovieEntity>();
			JSONArray mJSONArray = null;
			try {
				mJSONArray = new JSONArray(mJSONObjectResponse.getString("data"));
				this.total = mJSONObjectResponse.getInt("total");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (mJSONArray != null)
			{
				for (int i = 0; i < mJSONArray.length(); i++)
				{
					try {
						JSONObject mJSONObject = (JSONObject) mJSONArray.get(i);
						MovieEntity mMovieEntity = new MovieEntity();
						if (this.type == ISettings.TYPE_YOUTUBE)
						{
							mMovieEntity.setId(mJSONObject.getString("videoId"));
							mMovieEntity.setLink(mJSONObject.getString("ytLink"));
							mMovieEntity.setAuthor(mJSONObject.getString("author"));
							mMovieEntity.setCountrator(mJSONObject.getString("countRaters"));
							mMovieEntity.setUstreamId(0);
						}
						else if (this.type == ISettings.TYPE_USTREAM)
						{
							mMovieEntity.setId("");
							mMovieEntity.setUstreamId(mJSONObject.getInt("id"));
							mMovieEntity.setLink(mJSONObject.getString("usLink"));
						}
						mMovieEntity.setTitle(mJSONObject.getString("title"));
						mMovieEntity.setThumbnail(mJSONObject.getString("thumbnail"));
						mMovieEntity.setRating(mJSONObject.getString("rating"));
						mMovieEntity.setDuration(mJSONObject.getString("duration"));
						mMovieEntity.setViewcount(mJSONObject.getString("viewCount"));
						
						mArrayListMovieEntity.add(mMovieEntity);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mArrayListMovieEntity;
	}
}
