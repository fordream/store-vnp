package org.com.vnp.defenserun;

import com.vnp.core.activity.BaseActivity;
import com.vnp.core.common.VNPResize;

public class MBaseActivity extends BaseActivity {
	VNPResize vnpResize = VNPResize.getInstance();

	public void resize(int res, int width, int height) {
		vnpResize.resizeSacle(getView(res), width, height);
	}

	public void setTextSize(int res, int height) {
		vnpResize.setTextsize(getView(res), height);
	}
}
