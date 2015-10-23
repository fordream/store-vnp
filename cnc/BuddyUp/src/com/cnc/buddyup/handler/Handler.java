package com.cnc.buddyup.handler;

import android.os.Message;
import android.os.Parcelable;

import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;

public class Handler extends android.os.Handler {
	public Parcelable getParcelable(Message msg, String key) {
		try {
			return msg.getData().getParcelable(key);
		} catch (Exception e) {
			return null;
		}
	}

	public CommonItemResquestParcelable getCommonItemResquestParcelable(Message msg) {
		try {
			String key = Common.ARG0;
			return (CommonItemResquestParcelable) msg.getData().getParcelable(
					key);
		} catch (Exception e) {
			return null;
		}
	}
}