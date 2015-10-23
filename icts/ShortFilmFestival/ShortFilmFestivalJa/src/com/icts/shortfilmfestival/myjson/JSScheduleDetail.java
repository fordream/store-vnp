package com.icts.shortfilmfestival.myjson;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.icts.shortfilmfestival.entity.DetailNews;
import com.icts.shortfilmfestival.entity.ScheduleDetailEntity;

public class JSScheduleDetail {
	private static final String TAG = "LOG_JSNews";
	private int total;
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	private ArrayList<ScheduleDetailEntity> mArrayListScheduleDetailEntity;
	public JSScheduleDetail()
	{
		
	}
	
	public ArrayList<ScheduleDetailEntity> getmArrayListScheduleDetailEntity(String dataJson) {
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
		
			mArrayListScheduleDetailEntity = new ArrayList<ScheduleDetailEntity>();
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
						JSONObject mJSONObject = (JSONObject) mJSONArray.get(i);
						ScheduleDetailEntity mScheduleDetailEntity = new ScheduleDetailEntity();
						mScheduleDetailEntity.setProgramName(mJSONObject.getString("program_name"));
						mScheduleDetailEntity.setTitleMain(mJSONObject.getString("title_main"));
						mScheduleDetailEntity.setTitleSub(mJSONObject.getString("title_sub"));
						mScheduleDetailEntity.setfData(mJSONObject.getString("f-data"));
						mScheduleDetailEntity.setImage(mJSONObject.getString("image"));
						mScheduleDetailEntity.setDescription(mJSONObject.getString("description"));
						mScheduleDetailEntity.setLocation(mJSONObject.getString("location"));
						mScheduleDetailEntity.setPrize(mJSONObject.getString("prize"));
						mScheduleDetailEntity.setNote(mJSONObject.getString("note"));
						mScheduleDetailEntity.setLinkShare(mJSONObject.getString("linkshare"));
						mScheduleDetailEntity.setLinkShareGoogle(mJSONObject.getString("sharegoogle"));
						Log.d(TAG,"---" + i + "--" + mScheduleDetailEntity.getLinkShareGoogle());
						mScheduleDetailEntity.setTextShare(mJSONObject.getString("textshare"));
						String scheduleValue = "";
						JSONArray detailSchedule = new JSONArray(mJSONObject.getString("schedule"));
						
						if (detailSchedule != null && detailSchedule.length() > 0)
						{
							
							for (int j = 0; j < detailSchedule.length(); j++)
							{
								String scheduleJsonObject = (String)detailSchedule.get(j);
								scheduleValue += (scheduleJsonObject + ". ");
							}
						}
						mScheduleDetailEntity.setShedulteList(scheduleValue);
						
						mArrayListScheduleDetailEntity.add(mScheduleDetailEntity);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mArrayListScheduleDetailEntity;
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
			Log.d("ABC", mJSONObjectResponse.toString());
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
					e.printStackTrace();
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
