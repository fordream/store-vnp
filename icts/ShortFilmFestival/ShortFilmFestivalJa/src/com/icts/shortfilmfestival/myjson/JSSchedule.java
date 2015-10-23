package com.icts.shortfilmfestival.myjson;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.ScheduleEntity;
import com.icts.shortfilmfestival.inf.ISettings;

public class JSSchedule {
	private static final String TAG = "LOG_JSSCHEDULE";
	private int totalRow;
	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	private ArrayList<ScheduleEntity> mArrayListScheduleEntity;
	public JSSchedule()
	{
		
	}
	
	public ArrayList<ScheduleEntity> getmArrayListPhotoEntity(String dataJson) {
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
		
			mArrayListScheduleEntity = new ArrayList<ScheduleEntity>();
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
						ScheduleEntity mScheduleEntity = new ScheduleEntity();
						mScheduleEntity.setDate(mJSONObject.getString("date"));
						mScheduleEntity.setTime(mJSONObject.getString("time"));
						mScheduleEntity.setTitle(mJSONObject.getString("event"));
						mScheduleEntity.setFree(mJSONObject.getString("free"));
						mScheduleEntity.setLink(mJSONObject.getString("link"));
						
						mScheduleEntity.setTitle(mScheduleEntity.getTitle().replaceAll("&amp;nbsp;", " "));
						mScheduleEntity.setTitle(mScheduleEntity.getTitle().replaceAll("&lt;", "<"));
						mScheduleEntity.setTitle(mScheduleEntity.getTitle().replaceAll("&gt;", ">"));
						mScheduleEntity.setTitle(mScheduleEntity.getTitle().replaceAll("&amp;quot;", "\""));
						mScheduleEntity.setTitle(mScheduleEntity.getTitle().replaceAll("&quot;", "\""));
						mScheduleEntity.setTitle(mScheduleEntity.getTitle().replaceAll("&amp;", "&"));
						mScheduleEntity.setTitle(mScheduleEntity.getTitle().replaceAll("</a>", ""));
						if (mScheduleEntity.getDate() == null || mScheduleEntity.getDate().equals("null"))
						{
							mScheduleEntity.setDate("");
						}
						if (mScheduleEntity.getTime() == null || mScheduleEntity.getTime().equals("null"))
						{
							mScheduleEntity.setTime("");
						}
						if (mScheduleEntity.getTime() == null || mScheduleEntity.getTime().equals("null"))
						{
							mScheduleEntity.setTitle("");
						}
						if (mScheduleEntity.getFree() == null || mScheduleEntity.getFree().equals("null"))
						{
							mScheduleEntity.setFree("");
						}
						if (mScheduleEntity.getLink() == null || mScheduleEntity.getLink().equals("null"))
						{
							mScheduleEntity.setLink("");
						}
						
						JSScheduleDetail mJSScheduleDetail = new JSScheduleDetail();
						// Check if Locate # Japanese
					
						String url = mScheduleEntity.getLink() + "&lang=" + ISettings.LANGUAGE ;
						ArrayList mItemsTemp = mJSScheduleDetail.getmArrayListScheduleDetailEntity(
								SSSFApi.getAllScheduleDetail(url));
						if (mItemsTemp == null || mItemsTemp.size() == 0)
						{
							mScheduleEntity.setClick(false);
						}
						else
						{
							mScheduleEntity.setClick(true);
						}
						mArrayListScheduleEntity.add(mScheduleEntity);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mArrayListScheduleEntity;
	}
}
