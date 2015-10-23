package vn.com.vega.music.clientserver;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vn.com.vega.music.objects.Package;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.VegaLog;

public class JsonPackage extends JsonBase {

	private static final String LOG_TAG = Const.LOG_PREF + JsonPackage.class.getSimpleName();
	
	public ArrayList<Package> packages;

	protected JsonPackage() {
	}

	public static JsonPackage loadPackageList() {
		String url = ClientServer.getInstance().getUrlPackage();
		JsonPackage result = new JsonPackage();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				result.parsePackageList(json);
			} catch (Exception ex) {
				VegaLog.d(LOG_TAG, "Parsing package list failed");
				result.setParsingError();
			}
		}
		return result;
	}

	/**
	 * parse Artist json from server return: JSONArtist object
	 */
	protected void parsePackageList(JSONObject json) throws JSONException {
		ArrayList<Package> detailPackages = new ArrayList<Package>();
		JSONArray aData = json.getJSONArray("data");
		for (int i = 0; i < aData.length(); i++) {
			JSONObject obj = aData.getJSONObject(i);
			Package v = new Package();
			v.type = obj.getString("_type");
			v.code = obj.getString("code");
			v.name = obj.getString("name");
			v.description = obj.getString("description");
			v.smsServer = obj.getString("sms_server");
			v.smsContent = obj.getString("sms_content");
			v.price = obj.getString("price");
			detailPackages.add(v);
		}
		packages = detailPackages;
	}
}
