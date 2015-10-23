package com.caferhythm.csn.json;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.ErrorJSON;
import com.caferhythm.csn.data.ErrorEntity;
import com.caferhythm.csn.data.Fluctuation;
import com.caferhythm.csn.data.InfoEntity;
import com.caferhythm.csn.data.PeriodTimeEntity;
import com.caferhythm.csn.data.S0032Entity;
import com.caferhythm.csn.data.S007Entity;
import com.caferhythm.csn.data.SP04Entity;
import com.caferhythm.csn.data.TaionRow;
import com.caferhythm.csn.data.TimeEntity;

public class JsonPaser {
	public static ErrorJSON getErrorJSON(JSONObject obj) {
		ErrorJSON result = new ErrorJSON();
		try {
			result.setCode(obj.getJSONObject("error").getString("code"));
			result.setMesage(obj.getJSONObject("error").getString("message"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static int PaserUUIDCheck(String input) {
		JSONObject obj;
		try {
			obj = new JSONObject(input);
			ErrorJSON e = getErrorJSON(obj);
			if (e.isNoError())
				return obj.getJSONObject("data").getJSONObject("check-uuid")
						.getInt("unique");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public static String PaserAccesstoken(String input) {
		String result = "";
		JSONObject obj;
		try {
			obj = new JSONObject(input);
			ErrorJSON e = getErrorJSON(obj);
			if (e.isNoError()) {
				result = obj.getJSONObject("data").getJSONObject("access")
						.getString("token");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String paserRegisterResult(String input) {
		String result = "";
		JSONObject obj;
		try {
			obj = new JSONObject(input);
			ErrorJSON e = getErrorJSON(obj);
			if (e.isNoError()) {
				result = obj.getJSONObject("data").getJSONObject("user")
						.getString("name");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static SP04Entity getSp04Entity(String response) {
		SP04Entity sp04Entity = new SP04Entity();

		try {
			JSONObject jsonObject = new JSONObject(response);

			JSONObject errorJsonObject = jsonObject.optJSONObject("error");
			if (errorJsonObject != null) {
				ErrorEntity errorEntity = new ErrorEntity();
				errorEntity.setCode(errorJsonObject.getInt("code"));
				errorEntity.setMessage(errorJsonObject.getString("message"));
				sp04Entity.setErrorEntity(errorEntity);
			}

			JSONObject dataJsonObject = jsonObject.optJSONObject("data");
			if (dataJsonObject != null) {

				JSONObject periodJsonObject = dataJsonObject
						.getJSONObject("period");
				sp04Entity.setImage(periodJsonObject.getString("image"));
				sp04Entity.setImageSub(periodJsonObject.getString("image_sub"));
				JSONObject infoJsonObject = periodJsonObject
						.getJSONObject("info");
				InfoEntity infoEntity = new InfoEntity();
				infoEntity.setCycle(infoJsonObject.getInt("cycle"));
				infoEntity.setHairanbi(infoJsonObject.getInt("hairanbi"));
				infoEntity.setPrevious(infoJsonObject.getString("previous"));
				infoEntity.setSpan(infoJsonObject.getInt("span"));
				infoEntity.setStable(infoJsonObject.getInt("stable"));

				sp04Entity.setInfoEntity(infoEntity);

				JSONObject nextJsonObject = periodJsonObject
						.getJSONObject("next");

				TimeEntity seiriEntity = new TimeEntity();
				seiriEntity.setStart(nextJsonObject.getJSONObject("seiri")
						.getString("start"));
				seiriEntity.setEnd(nextJsonObject.getJSONObject("seiri")
						.getString("end"));

				TimeEntity hairanEntity = new TimeEntity();
				hairanEntity.setStart(nextJsonObject.getJSONObject("hairan")
						.getString("start"));
				hairanEntity.setEnd(nextJsonObject.getJSONObject("hairan")
						.getString("end"));

				sp04Entity.setSeiri(seiriEntity);
				sp04Entity.setHairan(hairanEntity);

				JSONArray termJsonArray = periodJsonObject.getJSONArray("term");
				ArrayList<PeriodTimeEntity> termArrayList = new ArrayList<PeriodTimeEntity>();
				for (int i = 0; i < termJsonArray.length(); i++) {
					JSONObject termItem = termJsonArray.getJSONObject(i);
					PeriodTimeEntity periodTimeEntity = new PeriodTimeEntity();
					periodTimeEntity.setStart(termItem.getString("start"));
					periodTimeEntity.setEnd(termItem.getString("end"));
					periodTimeEntity.setStatus(termItem.getString("status"));
					termArrayList.add(periodTimeEntity);
				}
				
				sp04Entity.setTerm(termArrayList);
				sp04Entity.sortDate();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sp04Entity;
	}

	public static Fluctuation paserFluctuation(String response) {
		Fluctuation result = new Fluctuation();
		try {
			JSONObject jsonObject = new JSONObject(response);

			JSONObject errorJsonObject = jsonObject.optJSONObject("error");
			if (errorJsonObject != null) {
				ErrorEntity errorEntity = new ErrorEntity();
				errorEntity.setCode(errorJsonObject.getInt("code"));
				errorEntity.setMessage(errorJsonObject.getString("message"));
				result.setError(errorEntity);
			}
			if(!jsonObject.optJSONObject("data").has("fluctuation"))
				return result;
			JSONObject fluctuationJson = jsonObject.optJSONObject("data")
					.getJSONObject("fluctuation");
			if (fluctuationJson != null) {
				result.setPt_01(fluctuationJson.getInt("pt_01"));
				result.setPt_02(fluctuationJson.getInt("pt_02"));
				result.setPt_03(fluctuationJson.getInt("pt_03"));
				result.setPt_04(fluctuationJson.getInt("pt_04"));
				result.setMessage_01(fluctuationJson.getString("message_01"));
				result.setMessage_02(fluctuationJson.getString("message_02"));
				result.setMessage_03(fluctuationJson.getString("message_03"));
				result.setMessage_04(fluctuationJson.getString("message_04"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static ArrayList<S0032Entity> getListFluctuation(Resources res,Fluctuation fluctuation) {
		ArrayList<S0032Entity> list =  new ArrayList<S0032Entity>();
		S0032Entity s1 = new S0032Entity();
		s1.setPt(fluctuation.getPt_01());
		s1.setMessage(fluctuation.getMessage01());
		s1.setTitle(res.getString(R.string.fl_3));
		s1.setStar((s1.getPt() / 20)+1);
		
		s1.setLeft(BitmapFactory.decodeResource(res, R.drawable.mypage_icon_3));
		

		S0032Entity s2 = new S0032Entity();
		s2.setPt(fluctuation.getPt_02());
		s2.setMessage(fluctuation.getMessage02());
		s2.setTitle(res.getString(R.string.fl_2));
		s2.setStar((s2.getPt() / 20)+1);
		s2.setLeft(BitmapFactory.decodeResource(res, R.drawable.mypage_icon_2));
		

		S0032Entity s3 = new S0032Entity();
		s3.setPt(fluctuation.getPt_03());
		s3.setMessage(fluctuation.getMessage03());
		s3.setTitle("PMS");
		s3.setStar((s3.getPt() / 20)+1);
		s3.setLeft(BitmapFactory.decodeResource(res, R.drawable.mypage_icon_4));
		

		S0032Entity s4 = new S0032Entity();
		s4.setPt(fluctuation.getPt_04());
		s4.setMessage(fluctuation.getMessage04());
		s4.setTitle(res.getString(R.string.fl_1));
		s4.setStar((s4.getPt() / 20)+1);
		s4.setLeft(BitmapFactory.decodeResource(res, R.drawable.mypage_icon_1));
		list.add(s4);
		list.add(s2);
		list.add(s1);
		list.add(s3);
		return list;
	}

	public static ArrayList<TaionRow> getTemperature(String arg0) {
		ArrayList<TaionRow> result = new ArrayList<TaionRow>();
		try {
			JSONObject jsonObject = new JSONObject(arg0);

			JSONObject errorJsonObject = jsonObject.optJSONObject("error");
			if (errorJsonObject != null) {
				ErrorEntity errorEntity = new ErrorEntity();
				errorEntity.setCode(errorJsonObject.getInt("code"));
				errorEntity.setMessage(errorJsonObject.getString("message"));
				// result.setError(errorEntity);
			}
			JSONArray temperature = jsonObject.optJSONObject("data")
					.getJSONArray("temperature");
			if (temperature != null) {
				for (int i = 0; i < temperature.length(); i++) {
					JSONObject j = temperature.getJSONObject(i);
					Iterator it = j.keys();
					while (it.hasNext()) {
						TaionRow t = new TaionRow();
						String s = (String) it.next();
						t.setDate(s);
						t.setTaion(j.getString(s));
						result.add(t);
					}

				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	@SuppressLint("ParserError")
	public static ArrayList<S007Entity> getS007Entities(String response) {
		ArrayList<S007Entity> listS007Entities = new ArrayList<S007Entity>();
		
		try {
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.getJSONObject("error").getInt("code") == 200){
				JSONObject data = jsonObject.getJSONObject("data");
				JSONArray jsonArray = data.optJSONArray("calender");
				if(jsonArray != null){
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject j = jsonArray.getJSONObject(i);
						@SuppressWarnings("rawtypes")
						Iterator it = j.keys();
						while (it.hasNext()) {
							S007Entity s007Entity = new S007Entity();
							String s = (String) it.next();
							JSONObject js = j.getJSONObject(s);
							s007Entity.setDays(s);
							s007Entity.setMemo(js.getString("memo"));
							s007Entity.setNextHairan(js.getInt("next_hairan"));
							s007Entity.setNextPeriod(js.getInt("next_period"));
							s007Entity.setPreviousPeriod(js.getInt("previous_period"));
							listS007Entities.add(s007Entity);
						}
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listS007Entities;
	}
}
