package org.com.vnp.lmhtmanager.service;

import android.os.Binder;

public class LMHTBinder extends Binder {
	private LMHTService lmhtService;

	public LMHTBinder(LMHTService lmhtService) {
		super();
		this.lmhtService = lmhtService;
	}

	public LMHTService getLmhtService() {
		return lmhtService;
	}
}