package vn.com.vega.music.clientserver;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vn.com.vega.music.objects.UnregisteredFriend;
import vn.com.vega.music.utils.Const;

public class JsonUnregisterFriend extends JsonBase {
	public ArrayList<UnregisteredFriend> friendlist;
	public int offset;
	public static JsonUnregisterFriend GetFBFriendList(Integer TYPE,
			String access_token, int offset) {
		// TODO Auto-generated method stub
		String url = null;
		switch (TYPE) {
		case Const.TYPE_FRIEND_FACEBOOK:
			url = ClientServer.getInstance().GetUrlUnregisterFriendOnFB(
					access_token,offset);
			break;
		}
		return loadFriend(url);
	}

	private static JsonUnregisterFriend loadFriend(String url) {
		// TODO Auto-generated method stub
		JsonUnregisterFriend result = new JsonUnregisterFriend();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				JSONObject objs = json.getJSONObject("data");
				result.offset = objs.getInt("offset");
				ArrayList<UnregisteredFriend> friends = new ArrayList<UnregisteredFriend>();
				JSONArray chachaFriend = objs.getJSONArray("outfriend");
				for (int i = 0; i < chachaFriend.length(); i++) {
					JSONObject obj = chachaFriend.getJSONObject(i)
							.getJSONObject("data");
					UnregisteredFriend v = new UnregisteredFriend();
					v.id = obj.getString("id");
					v.name = obj.getString("name");
					friends.add(v);
				}
				// add to result
				result.friendlist = friends;

			} catch (Exception e) {
				// TODO: handle exception
				result.setParsingError();
			}
		}
		return result;
	}

}
