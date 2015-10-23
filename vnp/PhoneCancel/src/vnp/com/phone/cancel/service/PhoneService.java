package vnp.com.phone.cancel.service;

import org.pjsip.pjsua.Callback;
import org.pjsip.pjsua.ZrtpCallback;
import org.pjsip.pjsua.csipsimple_config;
import org.pjsip.pjsua.pj_str_t;
import org.pjsip.pjsua.pjsua;
import org.pjsip.pjsua.pjsua_config;
import org.pjsip.pjsua.pjsua_logging_config;
import org.pjsip.pjsua.pjsua_media_config;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PhoneService extends Service {
	private boolean isCreated = false;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		if (!isCreated) {
			isCreated = true;
			// start sip service
			int status = pjsua.create();
			pj_str_t[] stunServers = null;
			int stunServersCount = 0;
			pjsua_config cfg = new pjsua_config();
			pjsua_logging_config logCfg = new pjsua_logging_config();
			pjsua_media_config mediaCfg = new pjsua_media_config();
			csipsimple_config cssCfg = new csipsimple_config();

			pjsua.setCallbackObject(new Callback());
			pjsua.setZrtpCallbackObject(new ZrtpCallback());

			// CSS CONFIG
			pjsua.csipsimple_config_default(cssCfg);
			cssCfg.setUse_compact_form_headers(pjsua.PJ_TRUE);
			cssCfg.setUse_compact_form_sdp(pjsua.PJ_TRUE);
			cssCfg.setUse_no_update(pjsua.PJ_TRUE);
			cssCfg.setUse_noise_suppressor(pjsua.PJ_TRUE);

//			cssCfg.setTcp_keep_alive_interval(prefsWrapper
//					.getTcpKeepAliveInterval());
//			cssCfg.setTls_keep_alive_interval(prefsWrapper
//					.getTlsKeepAliveInterval());

			cssCfg.setDisable_tcp_switch(pjsua.PJ_TRUE);
			cssCfg.setDisable_rport(pjsua.PJ_TRUE);
			cssCfg.setAdd_bandwidth_tias_in_sdp(pjsua.PJ_TRUE);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isCreated = false;
	}
}