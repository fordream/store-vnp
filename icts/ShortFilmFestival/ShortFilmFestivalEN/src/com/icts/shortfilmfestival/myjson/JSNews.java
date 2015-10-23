package com.icts.shortfilmfestival.myjson;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.icts.shortfilmfestival.entity.DetailNews;
import com.icts.shortfilmfestival.entity.NewsEntity;

public class JSNews {
	private static final String TAG = "LOG_JSNews";
	private int total;
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	private ArrayList<NewsEntity> mArrayListNewsEntity;
	public JSNews()
	{
		
	}
	
	public ArrayList<NewsEntity> getmArrayListNewsEntity(String dataJson) {
		Log.d(TAG, dataJson);
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
		
			mArrayListNewsEntity = new ArrayList<NewsEntity>();
			JSONArray mJSONArray = null;
			try {
				mJSONArray = new JSONArray(mJSONObjectResponse.getString("data"));
				this.setTotal(mJSONObjectResponse.getInt("total"));
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
						NewsEntity mNewsEntity = new NewsEntity();
						mNewsEntity.setType(mJSONObject.getString("_type"));
						mNewsEntity.setId(mJSONObject.getInt("data_id"));
						mNewsEntity.setDatetime(mJSONObject.getString("date"));
						mNewsEntity.setTitle(mJSONObject.getString("title").replace("\"", "\'"));
						mNewsEntity.setUrl(mJSONObject.getString("url").replace("\"", "\'"));
						mNewsEntity.setContent(mJSONObject.getString("url").replace("\"", "\'"));
						mNewsEntity.setDesc(mJSONObject.getString("shortdesc").replace("\"", "\'"));
						mNewsEntity.setHasPhoto(mJSONObject.getInt("hasPhoto"));
						mNewsEntity.setHasMovie(mJSONObject.getInt("hasVideo"));
						mNewsEntity.setThumbnail(mJSONObject.getString("photoUrl"));
						
						if (mNewsEntity.getTitle() == null || mNewsEntity.getTitle().equals("null"))
						{
							mNewsEntity.setTitle("");
						}
						
						if (mNewsEntity.getDesc() == null || mNewsEntity.getDesc().equals("null"))
						{
							mNewsEntity.setDesc("");
						}
						if (mNewsEntity.getDatetime() == null || mNewsEntity.getDatetime().equals("null"))
						{
							mNewsEntity.setDatetime("");
						}
						if (mNewsEntity.getThumbnail() == null)
						{
							mNewsEntity.setThumbnail("");
						}
						mArrayListNewsEntity.add(mNewsEntity);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mArrayListNewsEntity;
	}
	public DetailNews getDetailNews(String dataJson) {
		Log.d(TAG, dataJson);
		JSONObject mJSONObjectResponse = null;
		JSONArray mJSONObjectDetailNews = null;
		JSONArray mJSONObjectImagesNews = null;
		String strArrayImages = "";
		DetailNews mDetailNewsEntity = null;
		mDetailNewsEntity = new DetailNews();
		try {
			mJSONObjectResponse = new JSONObject(dataJson);
			mDetailNewsEntity.setError(mJSONObjectResponse.getInt("error_code") > 0 ? true:false);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (mJSONObjectResponse != null)
		{
			try {
				mJSONObjectDetailNews = new JSONArray(mJSONObjectResponse.getString("data"));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (mJSONObjectDetailNews != null)
			{
				try {
					
					mDetailNewsEntity.setId(((JSONObject)mJSONObjectDetailNews.get(0)).getInt("data_id"));
					mDetailNewsEntity.setDate(((JSONObject)mJSONObjectDetailNews.get(0)).getString("date"));
					mDetailNewsEntity.setTitle(((JSONObject)mJSONObjectDetailNews.get(0)).getString("title"));
					mDetailNewsEntity.setContent(((JSONObject)mJSONObjectDetailNews.get(0)).getString("body"));
					
					mDetailNewsEntity.setTitle(mDetailNewsEntity.getTitle().replaceAll("&amp;nbsp;", " "));
					mDetailNewsEntity.setTitle(mDetailNewsEntity.getTitle().replaceAll("&lt;", "<"));
					mDetailNewsEntity.setTitle(mDetailNewsEntity.getTitle().replaceAll("&gt;", ">"));
					mDetailNewsEntity.setTitle(mDetailNewsEntity.getTitle().replaceAll("&#039;", "'"));
					mDetailNewsEntity.setTitle(mDetailNewsEntity.getTitle().replaceAll("&amp;quot;", "\""));
					mDetailNewsEntity.setTitle(mDetailNewsEntity.getTitle().replaceAll("&quot;", "\""));
					mDetailNewsEntity.setTitle(mDetailNewsEntity.getTitle().replaceAll("&amp;", "&"));
					mDetailNewsEntity.setTitle(mDetailNewsEntity.getTitle().replaceAll("&amp;", "&"));
					mDetailNewsEntity.setTitle(mDetailNewsEntity.getTitle().replaceAll("\"", "'"));
					
					
					mDetailNewsEntity.setContent(mDetailNewsEntity.getContent().replaceAll("&amp;nbsp;", " "));
					mDetailNewsEntity.setContent(mDetailNewsEntity.getContent().replaceAll("&lt;", "<"));
					mDetailNewsEntity.setContent(mDetailNewsEntity.getContent().replaceAll("&gt;", ">"));
					mDetailNewsEntity.setContent(mDetailNewsEntity.getContent().replaceAll("&amp;quot;", "\""));
					mDetailNewsEntity.setContent(mDetailNewsEntity.getContent().replaceAll("&quot;", "\""));
					mDetailNewsEntity.setContent(mDetailNewsEntity.getContent().replaceAll("&amp;", "&"));
					mDetailNewsEntity.setContent(mDetailNewsEntity.getContent().replaceAll("\"", "'"));
					Log.d("LOG_NewsDetail", mDetailNewsEntity.getContent());
					strArrayImages = ((JSONObject)mJSONObjectDetailNews.get(0)).getString("att_imgs");
					mJSONObjectImagesNews = new JSONArray(strArrayImages);
					mDetailNewsEntity.setArrayImage(new String[mJSONObjectImagesNews.length()]);
					for (int i = 0; i < mJSONObjectImagesNews.length(); i++)
					{
						(mDetailNewsEntity.getArrayImage())[i] = ((JSONObject)mJSONObjectImagesNews.get(i)).getString("img_src").replace("\"", "\'");
						Log.d(TAG, (mDetailNewsEntity.getArrayImage())[i]);
					}
					if (mDetailNewsEntity.getDate() == null)
					{
						mDetailNewsEntity.setDate("");
					}
					if (mDetailNewsEntity.getTitle() == null)
					{
						mDetailNewsEntity.setTitle("");
					}
					if (mDetailNewsEntity.getContent() == null)
					{
						mDetailNewsEntity.setContent("");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return mDetailNewsEntity;
	}
	
	public int getNumberLikeFaceBook(String dataJson)
	{
		Log.d(TAG + "--Facebook", dataJson);
		JSONObject mJSONObjectResponse = null;
		try {
			mJSONObjectResponse = new JSONObject(dataJson);
			Log.d("ABC", mJSONObjectResponse.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (mJSONObjectResponse != null)
		{
			
			if (mJSONObjectResponse != null)
			{
				try {
					
					return mJSONObjectResponse.getInt("shares");
				} catch (JSONException e) {
				}
			}
		}
		return 0;
	}
	
	public int getNumberLikeTwitter(String dataJson)
	{
		Log.d(TAG, dataJson);
		JSONObject mJSONObjectResponse = null;
		try {
			mJSONObjectResponse = new JSONObject(dataJson);
			Log.d("ABC", mJSONObjectResponse.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (mJSONObjectResponse != null)
		{
			try {
				return mJSONObjectResponse.getInt("count");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
