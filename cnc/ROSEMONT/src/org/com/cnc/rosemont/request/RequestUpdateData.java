package org.com.cnc.rosemont.request;

import org.com.cnc.common.android.request.CommonRequest;

public class RequestUpdateData extends CommonRequest {
	public RequestUpdateData(String value) {
		setUrl("http://rosemontdev.pslink.org.uk/index.php?option=com_cncService&task=getUpdatedProducts");
		addParameter("dateUpdate", value);
		setGet(false);
		setTimeout(30000);
	}
}