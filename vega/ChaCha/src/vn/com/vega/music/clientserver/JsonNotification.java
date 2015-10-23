package vn.com.vega.music.clientserver;

import org.json.JSONObject;

import vn.com.vega.music.objects.Notification;
import vn.com.vega.music.utils.VegaLog;

public class JsonNotification extends JsonBase {

	private static final String LOG_TAG = JsonNotification.class
			.getSimpleName();

	public Notification notif;

	protected JsonNotification() {
	}

	public static JsonNotification updateNotificationConfig(int param1,
			int param2, int param3, int param4, int param5) {
		String url = ClientServer.getInstance().getUrlSetNotificationConfig(
				param1, param2, param3, param4, param5);
		JsonNotification result = new JsonNotification();
		result.requestAndParseBasicJson(url);
		return result;
	}

	public static JsonNotification getNotificationConfig() {
		String url = ClientServer.getInstance().getUrlGetNotificationConfig();
		return loadNotificationConfig(url);
	}

	protected static JsonNotification loadNotificationConfig(String url) {
		JsonNotification result = new JsonNotification();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				Notification n = new Notification();

				JSONObject obj = json.getJSONObject("data");
				n.email_on_new_pl = obj.getBoolean("email_on_new_playlist");
				n.email_on_like_pl = obj.getBoolean("email_on_like_playlist");
				n.email_on_server_news = obj
						.getBoolean("email_on_service_news");
				n.sms_on_shared_to_me = obj.getBoolean("sms_on_shared_to_me");
				n.sms_on_artist_updated = obj
						.getBoolean("sms_on_artist_updated");

				result.notif = n;
			} catch (Throwable t) {
				VegaLog.d(LOG_TAG, "Parsing notification config failed");
				result.setParsingError();
			}
		}
		return result;
	}
}
