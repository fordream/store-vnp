package com.vn.icts.wendy.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.ict.library.service.RestClient;
import com.ict.library.service.RestClient.RequestMethod;
import com.vn.icts.wendy.model.Coupon;
import com.vn.icts.wendy.model.News;
import com.vn.icts.wendy.model.Shop;
import com.vn.icts.wendy.util.Const;

public class WendyAsyn extends AsyncTask<String, String, String> {
	public interface CallBack {
		public void callBack(List<Object> lDatas);
	}

	private CallBack callBack;
	public static final int TYPE_NEW_LIST = 1;
	public static final int TYPE_SHOP_LIST = 2;
	public static final int TYPE_ALL_COUPON_LIST = 3;
	public static final int TYPE_DISTINCT_MAP = 4;

	private int type = TYPE_NEW_LIST;
	private Bundle extras;

	public WendyAsyn(int type, CallBack callBack, Bundle extras) {
		super();
		this.type = type;
		this.callBack = callBack;
		this.extras = extras;
	}

	private List<Object> lData = new ArrayList<Object>();

	@Override
	protected String doInBackground(String... params) {
		RestClient restClient = null;
		if (type == TYPE_NEW_LIST) {
			restClient = new RestClient(Const.URL_NEWS_LIST);
		} else if (type == TYPE_SHOP_LIST) {
			restClient = new RestClient(Const.URL_SHOPS_LIST);
		} else if (type == TYPE_ALL_COUPON_LIST) {
			restClient = new RestClient(Const.URL_ALL_COUPON_LIST);
		} else if (type == TYPE_DISTINCT_MAP) {
			restClient = new RestClient(Const.URL_DISTINCT_MAP);
			restClient.addParam("sensor", "true");
			restClient.addParam("mode", "driving");
		}

		if (extras != null) {
			Set<String> keys = extras.keySet();
			for (String key : keys) {
				restClient.addParam(key, extras.getString(key));
			}
		}

		if (type == TYPE_NEW_LIST || type == TYPE_SHOP_LIST
				|| TYPE_ALL_COUPON_LIST == type || type == TYPE_DISTINCT_MAP) {
			try {
				restClient.execute(RequestMethod.GET);
			} catch (Exception e) {
			}
		}

		Log.w("RESPONSE", restClient.getResponse() + "");

		if (type == TYPE_DISTINCT_MAP) {
			try {
				JSONObject jsonObject = new JSONObject(restClient.getResponse());
				String value = jsonObject.getJSONArray("routes")
						.getJSONObject(0).getJSONArray("legs").getJSONObject(0)
						.getJSONObject("distance").getString("value");
				lData.add(value);
			} catch (Exception e) {
				//
			}
			return null;
		}
		try {
			JSONObject jsonObject = new JSONObject(restClient.getResponse());
			JSONArray array = jsonObject.getJSONArray("data");

			for (int i = 0; i < array.length(); i++) {
				try {
					Object object = null;
					if (type == TYPE_NEW_LIST) {
						object = new News(array.getJSONObject(i));
					} else if (type == TYPE_SHOP_LIST) {
						object = new Shop(array.getJSONObject(i));
					} else if (type == TYPE_ALL_COUPON_LIST) {
						object = new Coupon(array.getJSONObject(i));
					}
					if (object != null)
						lData.add(object);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		callBack.callBack(lData);
	}

}
