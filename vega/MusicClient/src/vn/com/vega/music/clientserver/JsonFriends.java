package vn.com.vega.music.clientserver;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.com.vega.music.objects.Friend;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.VegaLog;
import android.util.Log;

public class JsonFriends extends JsonBase {

	private static final String LOG_TAG = JsonFriends.class.getSimpleName();

	public ArrayList<Friend> array_friend;
	public int register_friend_count;
	public int unregister_friend_count;

	// public ArrayList<FacebookFriend> array_gmail;

	protected JsonFriends() {
	}

	public static JsonFriends getFriendsList(int type, String keyword,
			int offset) {
		String url = null;
		switch (type) {
		case Const.TYPE_FRIEND_IDOL_LISTING:
			url = ClientServer.getInstance().getSubscribedFriends(offset);
			break;
		case Const.TYPE_FRIEND_FAN_LISTING:
			url = ClientServer.getInstance().getSubscribedMeFriends(offset);
			break;
		case Const.TYPE_FRIEND_IDOL_SEARCHING:
			url = ClientServer.getInstance().searchSubcribedFriends(offset,
					keyword);
			break;
		case Const.TYPE_FRIEND_FAN_SEARCHING:
			url = ClientServer.getInstance().searchSubcribedMeFriends(offset,
					keyword);
			break;
		case Const.TYPE_FRIEND_CHACHA:
			url = ClientServer.getInstance().findUser(keyword, offset);
			break;
		default:
			break;
		}
		return loadFriendList(url);
	}

	public static JsonFriends getFriendOnFacebook(int TYPE, String access_token) {
		String url = null;
		switch (TYPE) {
		case Const.TYPE_FRIEND_FACEBOOK:
			url = ClientServer.getInstance().getUrlFriendOnFacebook(
					access_token);
			break;
		}
		return loadFacebookFriend(url);
	}

	public static Boolean subscribed(String friendID) {
		String url = ClientServer.getInstance().Subscribed(friendID);
		return GetResult(url);
	}

	public static Boolean unsubscribed(String friendID) {
		String url = ClientServer.getInstance().UnSubscribed(friendID);
		return GetResult(url);
	}

	public static Boolean ShareWithFriend(Boolean is_share_song, int Id,
			String friendList) {
		String url = "";
		if (is_share_song)
			url = ClientServer.getInstance().GetUrlShareSong(Id, friendList);
		else
			url = ClientServer.getInstance()
					.GetUrlSharePlaylist(Id, friendList);
		return GetResult(url);
	}

	protected static Boolean GetResult(String url) {
		JsonFriends f = new JsonFriends();
		f.requestAndParseBasicJson(url);
		return f.isSuccess();
	}

	protected static JsonFriends loadFacebookFriend(String url) {
		JsonFriends result = new JsonFriends();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				JSONObject objs = json.getJSONObject("data");
				int register = objs.getInt("registered_friend_count");
				int unregister = objs.getInt("unregistered_friend_count");
				ArrayList<Friend> friends = new ArrayList<Friend>();
				JSONArray chachaFriend = objs
						.getJSONArray("registered_friend_list");
				for (int i = 0; i < chachaFriend.length(); i++) {
					JSONObject obj = chachaFriend.getJSONObject(i);
					Friend v = new Friend();
					v.friend_id = obj.getInt("id");
					v.friend_name = obj.getString("name");
					v.friend_info = obj.getString("info");
					v.my_idol = obj.getInt("my_idol");
					v.friend_avatar = obj.getString("avatar");
					JSONArray array = obj.getJSONArray("playlist");
					ArrayList<Playlist> array_playlist = new ArrayList<Playlist>();
					for (int j = 0; j < array.length(); j++) {
						JSONObject o = array.getJSONObject(j);
						array_playlist.add(result.parsePlaylist(o));
					}
					v.friend_playlist = array_playlist;
					friends.add(v);
				}
				// add to result
				result.array_friend = friends;
				result.register_friend_count = register;
				result.unregister_friend_count = unregister;

			} catch (Exception e) {
				// TODO: handle exception
				VegaLog.d(LOG_TAG, "Parsing Facebook friend list failed");
				result.setParsingError();
			}
		}
		return result;
	}

	protected static JsonFriends loadFriendList(String url) {
		JsonFriends result = new JsonFriends();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				ArrayList<Friend> subscribedList = new ArrayList<Friend>();
				JSONArray aData = json.getJSONArray("data");
				for (int i = 0; i < aData.length(); i++) {
					JSONObject obj = aData.getJSONObject(i);
					Friend v = new Friend();
					v.friend_id = obj.getInt("id");
					v.friend_name = obj.getString("name");
					v.friend_info = obj.getString("info");
					v.my_idol = obj.getInt("my_idol");
					v.friend_avatar = obj.getString("avatar");
					JSONArray array = obj.getJSONArray("playlist");
					ArrayList<Playlist> array_playlist = new ArrayList<Playlist>();
					for (int j = 0; j < array.length(); j++) {
						JSONObject o = array.getJSONObject(j);
						array_playlist.add(result.parsePlaylist(o));
					}
					v.friend_playlist = array_playlist;
					subscribedList.add(v);
				}
				// add to list
				result.array_friend = subscribedList;
			} catch (Throwable t) {
				VegaLog.d(LOG_TAG, "Parsing list friend list failed");
				result.setParsingError();
			}
		}
		return result;
	}

	protected Playlist parsePlaylist(JSONObject jo) throws JSONException {
		Log.e("JsonPlaylist", jo.toString());
		try {
			Playlist p = new Playlist();
			p.serverId = jo.getInt("id");
			p.type = jo.getInt("type");
			p.title = getString(jo, "name", false);
			p.userMsisdn = getString(jo, "creator_msisdn", false);
			p.userName = getString(jo, "creator_name", false);
			p.viewCount = getInt(jo, "view_count", false);
			p.userThumb = getString(jo, "creator_avatar_url", false);
			p.total_song = getInt(jo, "total_song", false);
			JSONArray jsonThumbs = new JSONArray();
			try {
				jsonThumbs = jo.getJSONArray("thumbnails");
			} catch (Exception e) {
				// TODO: handle exception
			}

			ArrayList<String> thumbs = new ArrayList<String>();
			for (int i = 0; i < jsonThumbs.length(); i++) {
				thumbs.add(jsonThumbs.get(i).toString());
			}
			ArrayList<String> temps = new ArrayList<String>();
			temps.add("");
			temps.add("");
			temps.add("");
			temps.add("");
			if (thumbs.size() < 4) {
				thumbs.addAll(temps);
			}
			p.thumbnails.addAll(thumbs);

			return p;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("parsePlaylist", e.getMessage());
		}
		return null;
	}
}
