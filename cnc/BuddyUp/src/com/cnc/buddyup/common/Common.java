package com.cnc.buddyup.common;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.widget.Spinner;
import android.widget.Toast;

import com.cnc.buddyup.R;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;

public class Common {

	public static final String ARG0 = "ARG0";
	public static final String ARG1 = "ARG1";
	public static final String ARG2 = "ARG2";
	public static final int MESSAGE_WHAT_0 = 0;
	public static final int MESSAGE_WHAT_1 = 1;
	public static final int MESSAGE_WHAT_2 = 2;
	public static final int MESSAGE_WHAT_3 = 3;
	public static final int MESSAGE_WHAT_4 = 4;
	public static final int MESSAGE_WHAT_5 = 5;
	public static final int MESSAGE_WHAT_COMMON = 101;
	public static final String SEARCH_ADVANCED = "Advanced Search";
	public static final String SEARCH_RESULT = "Search Result";
	public static final String SEARCH_SIMPLE = "Simple Search";
	public static final String SEARCH_RESULT_ITEM = "Administrator";
	public static final String SHEDULE_ACTIVITY = "Schedule Activities";
	public static final String PENDING_ACTIVITY = "Pending Activities";
	public static final int REQUEST_CODE_0 = 0;
	public static final int REQUEST_CODE_1 = 1;
	public static final int REQUEST_CODE_2 = 2;
	public static final int REQUEST_CODE_3 = 3;
	public static final int REQUEST_CODE_4 = 4;
	public static final int REQUEST_CODE_5 = 5;
	public static final int REQUEST_CODE_6 = 6;
	public static final int REQUEST_CODE_7 = 7;
	public static final int REQUEST_CODE_8 = 8;
	public static final int REQUEST_CODE_9 = 9;
	public static final int REQUEST_CODE_10 = 10;
	public static final int REQUEST_CODE_11 = 11;
	public static final int REQUEST_CODE_12 = 12;
	public static final int REQUEST_CODE_13 = 13;
	public static final String FOLLOW_ACTIVITY = "Activity Follow-Up";

	public static final String URL_SERVER = "http://buddyup.com/api";
	public static String token = "";
	public static String id = "";
	public static String BUDDIES_LIST = "Buddy List";
	public static String GROUP_OF_BUDDIES = "Group of Buddies";
	public static final int TIME_OUT = 10000;

	public static void sleep(long l) {
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
		}
	}

	public static Message createMessage(int what) {
		Message message = new Message();
		message.what = what;
		return message;
	}

	public static void makeText(Context context, String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}

	public static String getFromSpinner(Spinner spinner) {
		try {
			return spinner.getSelectedItem().toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static Message createMessage(int what, Parcelable parcelable) {
		Bundle data = new Bundle();
		data.putParcelable(Common.ARG0, parcelable);
		Message message = createMessage(what);
		message.setData(data);
		return message;
	}

	public static List<CommonItemResquestParcelable> createListparcel(int number) {
		List<CommonItemResquestParcelable> list = new ArrayList<CommonItemResquestParcelable>();
		list.add(create("id1", "Kraked"));
		list.add(create("id0", "cobund"));
		list.add(create("id1", "Rod"));
		list.add(create("id1", "Jon"));
		list.add(create("id1", "spinartist"));
		list.add(create("id1", "Kan"));
		list.add(create("id1", "Maria"));
		//
		// for (int i = 0; i < number; i++) {
		// CommonItemResquestParcelable parcelable = new
		// CommonItemResquestParcelable();
		// parcelable.setId("id" + i);
		// parcelable.setTxtView("View " + i + "_"
		// + (int) (Math.random() * 1000));
		// list.add(parcelable);
		// }

		return list;
	}

	public static List<CommonItemResquestParcelable> createListparcel1() {
		List<CommonItemResquestParcelable> list = new ArrayList<CommonItemResquestParcelable>();
		list.add(create("id1", "Kraked"));
		list.add(create("id0", "cobund"));
		list.add(create("id1", "Rod"));
		list.add(create("id1", "Jon"));
		list.add(create("id1", "spinartist"));
		list.add(create("id1", "Kan"));
		list.add(create("id1", "Maria"));

		return list;
	}

	public static List<CommonItemResquestParcelable> createListparcel2() {
		List<CommonItemResquestParcelable> list = new ArrayList<CommonItemResquestParcelable>();
		list.add(create("id1", "Kraked"));
		list.add(create("id0", "cobund"));
		list.add(create("id1", "Rod"));
		list.add(create("id1", "Jon"));
		list.add(create("id1", "spinartist"));
		list.add(create("id1", "Kan"));
		list.add(create("id1", "Maria"));

		return list;
	}

	public static List<CommonItemResquestParcelable> createListparcel3() {
		List<CommonItemResquestParcelable> list = new ArrayList<CommonItemResquestParcelable>();
		list.add(create("id0", "Kraked"));
		list.add(create("id1", "cobund"));
		list.add(create("id2", "Rod"));
		list.add(create("id3", "Jon"));
		list.add(create("id4", "spinartist"));
		list.add(create("id5", "Kan"));
		list.add(create("id6", "Maria"));

		return list;
	}

	public static CommonItemResquestParcelable create(String id, String text) {
		CommonItemResquestParcelable parcelable = new CommonItemResquestParcelable();
		parcelable.setId(id);
		parcelable.setTxtView(text);
		return parcelable;
	}

	public static void builder(Context context, String string, String message) {
		Builder builder = new Builder(context);
		builder.setTitle(string);
		builder.setMessage("" + message);
		builder.setCancelable(false);
		String text = context.getResources().getString(R.string.close);
		builder.setPositiveButton(text, null);
		builder.show();
	}

	public static Builder createBuilder(Context context, String string,
			String string2) {
		Builder builder = new Builder(context);
		builder.setTitle(string);
		builder.setMessage("" + string2);
		builder.setCancelable(false);
		String text = context.getResources().getString(R.string.close);
		builder.setPositiveButton(text, null);
		return builder;
	}

	public static String getString(Context context, int res) {
		try {
			return context.getResources().getString(res);
		} catch (Exception e) {
			return "";
		}
	}

	public static boolean compare(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}

		if (s1 != null || s2 != null) {
			return s1.equalsIgnoreCase(s2);
		}

		return false;
	}
}