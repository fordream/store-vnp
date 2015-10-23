package org.com.vnp.vanga;

import android.app.Dialog;
import android.app.ProgressDialog;

import com.ict.library.activity.CommonBaseActivity;

public class BaseActivity extends CommonBaseActivity {
	public static final int DIALOG_DOWNLOAD = 1;

	@Override
	protected Dialog onCreateDialog(int id) {

		if (id == DIALOG_DOWNLOAD) {
			return ProgressDialog.show(this, null, null);
		}
		
		return super.onCreateDialog(id);
	}
}