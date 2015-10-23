package vnp.com.phone.cancel.process;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ict.library.database.CommonDataStore;
import com.vnp.core.processor.BaseProcessor;

public abstract class MProcess extends BaseProcessor {

	public MProcess(Context context) {
		super(context);
	}

	@Override
	public Intent onProcessor(Bundle bundle) {
		Intent intent = null;

		PROCESSTYPE processtype = (PROCESSTYPE) bundle
				.getSerializable("PROCESSTYPE");
		CommonDataStore.getInstance().init(mContext);
		// Login
		if (processtype == PROCESSTYPE.LOGIN) {
			intent = login(bundle);
		} else if (processtype == PROCESSTYPE.SAVE_NUMBER_PHONE) {
			intent = saveNumberPhone(bundle);
		}
		return intent;
	}

	private Intent saveNumberPhone(Bundle bundle) {
		Intent intent = new Intent();
		// get new phone for add
		String numberPhone = bundle.getString("numberPhone").trim();
		CommonDataStore.getInstance().init(mContext);

		// add phone
		String content = CommonDataStore.getInstance().get("phonesave", "");
		if (content.trim().equals("") && !numberPhone.equals("")) {
			content = numberPhone;
		} else if (!content.trim().equals("") && !numberPhone.equals("")) {
			content = content + "<;>" + numberPhone;
		}

		// save contact
		CommonDataStore.getInstance().save("phonesave", content);
		intent.putExtra("PROCESSTYPE", PROCESSTYPE.SUCESS);
		return intent;
	}

	private Intent login(Bundle bundle) {
		Intent intent = new Intent();
		String user = bundle.getString("user").trim();
		String password = bundle.getString("password").trim();
		PROCESSTYPE result = PROCESSTYPE.FAIL;
		if (!user.equals("") && !password.equals("")) {
			String userSave = CommonDataStore.getInstance().get("userSave", "");
			if (userSave.equals("")) {
				userSave = user + ";" + password;
				CommonDataStore.getInstance().save("userSave", userSave);
			}

			if (userSave.equals(user + ";" + password)) {
				result = PROCESSTYPE.SUCESS;
			}
		}

		intent.putExtra("PROCESSTYPE", result);
		return intent;
	}
}
