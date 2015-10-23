package org.com.cnc.rosemont.request;

import org.com.cnc.common.android.request.CommonRequest;

public class RequestLastUpdate extends CommonRequest {
	public RequestLastUpdate() {
		setUrl("http://rosemontdev.pslink.org.uk/index.php?option=com_cncService&task=checkUpdate");
		setTimeout(15000);
	}
}
