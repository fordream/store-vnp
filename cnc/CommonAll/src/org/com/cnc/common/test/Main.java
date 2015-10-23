package org.com.cnc.common.test;

import org.com.cnc.common.adnroid.JSonCommon;
import org.json.JSONObject;

public class Main {
	public static void main(String[] args) {
		JSONObject jsonObject = JSonCommon.getJSONfromURL("http://buddyup.com/api/user/countrylist.aspx");
		System.out.println(jsonObject);
	}
}
