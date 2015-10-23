package com.icts.shortfilmfestival.myjson;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import com.icts.shortfilmfestival.entity.PhotosEntity;

public class JSPhoto {
	private static final String TAG = "LOG_JSNews";
	private int totalRow;
	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	private ArrayList<PhotosEntity> mArrayListPhotoEntity;
	public JSPhoto()
	{
		
	}
	
	public ArrayList<PhotosEntity> getmArrayListPhotoEntity(String dataJson) {
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
		
			mArrayListPhotoEntity = new ArrayList<PhotosEntity>();
			JSONArray mJSONArray = null;
			try {
				mJSONArray = new JSONArray(mJSONObjectResponse.getString("data"));
				this.setTotalRow(mJSONObjectResponse.getInt("total"));
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
						PhotosEntity mPhotosEntity = new PhotosEntity();
						mPhotosEntity.setImgSmall(mJSONObject.getString("img_small"));
						mPhotosEntity.setImgMedium(mJSONObject.getString("img_medium"));
						mPhotosEntity.setImgBig(mJSONObject.getString("img_big"));
						mPhotosEntity.setCommentCount(mJSONObject.getInt("comment_count"));
						mPhotosEntity.setListComment(mJSONObject.getString("listComment"));
						mPhotosEntity.setCaption(mJSONObject.getString("caption"));
						mPhotosEntity.setType(mJSONObject.getString("type"));
						if (mPhotosEntity.getType() != null && mPhotosEntity.getType().equals("ssff"))
						{
							mPhotosEntity.setCategory("Festival");
						}
						else if (mPhotosEntity.getType() != null && mPhotosEntity.getType().equals("biz"))
						{
							mPhotosEntity.setCategory("Biz");
						}
						else if (mPhotosEntity.getType() != null && mPhotosEntity.getType().equals("lounge"))
						{
							mPhotosEntity.setCategory("Lounge");
						}
						else if (mPhotosEntity.getType() != null && mPhotosEntity.getType().equals("theater"))
						{
							mPhotosEntity.setCategory("Theater");
						}
						mArrayListPhotoEntity.add(mPhotosEntity);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mArrayListPhotoEntity;
	}
}
