package org.com.cnc.rosemont.request;

import org.com.cnc.common.android.request.CommonRequest;

public class RequestData extends CommonRequest {
	public RequestData() {
		setUrl("http://rosemontdev.pslink.org.uk/index.php?option=com_cncService&task=getProducts");
		setTimeout(30000);
	}
}