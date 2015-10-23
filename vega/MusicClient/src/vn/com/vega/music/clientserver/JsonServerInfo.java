//structure
//{
//	"errorCode":0,
//	"message":"success",
//	"msisdn":"84982011610",
//	"key":"baf9e8e6c42e75e3e5e7b4c5047c754a",
//	"server":{
//				"live":[
//						{"host":"123.30.188.121",
//						"port":"30112"}
//				],
//				"upload":[
//						{"host":"123.30.188.121",
//						"port":"40710"}
//				]
//			},
//	"client":{"update_url":"","valid":1,"message":""}
//}

package vn.com.vega.music.clientserver;

import org.json.JSONException;
import org.json.JSONObject;

import vn.com.vega.music.objects.DataInfo;

public class JsonServerInfo extends JsonBase {

	public DataInfo dataInfo;

	public JsonServerInfo() {
	}

	public static JsonServerInfo getServerInfo() {
		JsonServerInfo result = new JsonServerInfo();
		String url = ClientServer.getInstance().getUrlSystemConfig();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				result.parseServerInfo(json);
			} catch (Exception ex) {
				result.setParsingError();
			}
		}
		return result;
	}

	protected void parseServerInfo(JSONObject json) throws JSONException {
		JSONObject dataObject = json.getJSONObject("data");
		dataInfo = new DataInfo();
		dataInfo.phone_number = dataObject.getString("phone_number");
		dataInfo.package_code = dataObject.getString("package_code");
		dataInfo.account_info = dataObject.getString("account_info");
	}
}
