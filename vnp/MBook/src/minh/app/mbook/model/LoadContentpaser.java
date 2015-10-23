package minh.app.mbook.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class LoadContentpaser {
	private String server;
	private String server_icon;
	private String server_books;
	private List<Item> lItems = new ArrayList<LoadContentpaser.Item>();

	public LoadContentpaser(String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			server = jsonObject.getString("server");
			server_icon = jsonObject.getString("server-icon");
			server_books = jsonObject.getString("server-books");
			JSONArray array = jsonObject.getJSONArray("items");

			for (int index = 0; index < array.length(); index++) {
				JSONObject object = array.getJSONObject(index);
				lItems.add(new Item(object));
			}
		} catch (JSONException e) {
			
		}
	}

	class Item {
		public Item(JSONObject object) {
			try {
				id = object.getString("id");
				group = object.getString("group");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		public String id;
		public String group;
	}

	public int size() {
		return lItems.size();
	}

	public Item get(int i) {
		return lItems.get(i);
	}
}