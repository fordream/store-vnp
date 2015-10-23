/**
 * 
 */
package org.com.vnp.lmhtmanager.service;

import java.util.List;

import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

import org.com.vnp.lmhtmanager.service.LMHTServiceManager.LMHTServiceManagerCallBack;
import org.com.vnp.lmhtmanager.utils.LMHTUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.vnp.core.base.callback.CallBack;
import com.vnp.core.base.callback.ExeCallBack;
import com.vnp.core.base.datastore.DataStore;

import constant.Region;
import dto.Champion.Champion;

/**
 * @author teemo
 * 
 */
public class LMHTService extends Service {
	public static final String ACTION = "org.com.vnp.lmhtmanager.service.LMHTService";
	public static final String ACTION_UPDATE = "org.com.vnp.lmhtmanager.service.LMHTService.ACTION_UPDATE";
	public RiotApi RIOT_API;
	private LMHTBinder lmhtBinder;

	@Override
	public void onCreate() {
		super.onCreate();
		lmhtBinder = new LMHTBinder(this);
		DataStore.getInstance().init(this);
		RIOT_API = new RiotApi(LMHTUtils.RIOT_KEY, Region.NA);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return lmhtBinder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// new ExeCallBack().executeAsynCallBack(new CallBack() {
		// @Override
		// public void onCallBack(Object object) {
		// try {
		// List<Champion> championList = RIOT_API.getChampions()
		// .getChampions();
		//
		// if (championList != null) {
		// for (Champion champion : championList) {
		// dto.Static.Champion mChampion = RIOT_API
		// .getDataChampion((int) champion.getId());
		// // if (champion2 != null) {
		// // champion2.getName();
		// //
		// // }
		// }
		// }
		// } catch (RiotApiException e) {
		// }
		// }
		//
		// @Override
		// public Object execute() {
		// return null;
		// }
		// });
		return super.onStartCommand(intent, flags, startId);
	}

	public void loadContent(final LMHTServiceManagerCallBack serviceManagerCallBack) {
		new ExeCallBack().executeAsynCallBack(new CallBack() {
			@Override
			public void onCallBack(Object object) {
				serviceManagerCallBack.onProgessSuccess(null);
			}

			@Override
			public Object execute() {
				try {
					serviceManagerCallBack.onProgess("loading ...");
					List<Champion> championList = RIOT_API.getChampions().getChampions();
					serviceManagerCallBack.onProgess("loading champion...");
					if (championList != null) {
						for (Champion champion : championList) {
							dto.Static.Champion mChampion = RIOT_API.getDataChampion((int) champion.getId());

							if (mChampion != null) {
								serviceManagerCallBack.onProgess("save champion..." + mChampion.getName());
							}
						}
					}
				} catch (RiotApiException e) {
				}
				return null;
			}
		});
	}
}