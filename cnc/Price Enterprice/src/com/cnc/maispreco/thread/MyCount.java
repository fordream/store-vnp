package com.cnc.maispreco.thread;

import org.com.cnc.maispreco.common.Common;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;

public class MyCount extends CountDownTimer {
	Handler handler;

	public MyCount(Handler handler, long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		this.handler = handler;
	}

	public void onFinish() {
		Message message = new Message();
		message.what = Common.MESSAGE_WHAT_0;
		handler.sendMessage(message);
	}

	public void onTick(long millisUntilFinished) {
	}
}