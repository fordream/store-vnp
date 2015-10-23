package org.com.cnc.common.maispreco.response;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.maispreco.request.StoreRequest;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

public class StoreResponse {
	private SoapObject data = null;

	public SoapObject getData() {
		return data;
	}

	public void setData(SoapObject data) {
		this.data = data;
	}

	public static final String fbannerImageUrl = "bannerImageUrl";
	public static final String fresultTitle = "resultTitle";
	public static final String fresultTotal = "resultTotal";
	public static final String fstatusCode = "statusCode";
	public static final String fstatusMessage = "statusMessage";

	public static final String fidSite = "idSite";
	public static final String finfoSite = "infoSite";
	public static final String fnameSite = "nameSite";
	public static final String fphoneSite = "phoneSite";

	private static final String fsite = "site";

	public static final String fphones = "phones";

	public static StoreResponse getLStore(StoreRequest search) {
		StoreResponse response = new StoreResponse();
		SoapObject data = soapSearch(search);
		response.setData(data);
		return response;
	}

	public static SoapObject soapSearch(final StoreRequest search) {
		// String URL_SEARCH =
		// "http://bot4.maisprecobot.com:9009/MaisPrecoWS/services/MaisPreco?wsdl";
		String URL_SEARCH = "http://webservice.maispreco.com/services/MaisPreco?wsdl";
		String NAMESPACE_SEARCH = "http://core.maispreco.com";
		String METHOD_NAME_SEARCH = "search";
		String SOAP_ACTION_SEARCH = "" + NAMESPACE_SEARCH + "/"
				+ METHOD_NAME_SEARCH;
		SoapObject request = new SoapObject(NAMESPACE_SEARCH,
				METHOD_NAME_SEARCH);

		SoapObject _search = new SoapObject(NAMESPACE_SEARCH,
				METHOD_NAME_SEARCH);

		if (search.getLocaleIdString() != null) {
			_search.addProperty("localeId", search.getLocaleIdString());
		}

		if (search.getNearBy() != null) {
			_search.addProperty("nearBy", search.getNearBy());
		}

		if (search.getPage() != null) {
			_search.addProperty("page", search.getPage());
		}

		if (search.getSearch() != null) {
			_search.addProperty("search", search.getSearch());
		}

		if (search.getSearchType() != null) {
			_search.addProperty("searchType", search.getSearchType());
		}

		if (search.getToken() != null) {
			_search.addProperty("token", search.getToken());
		}

		request.addProperty("search", _search);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = false;

		envelope.setOutputSoapObject(request);
		AndroidHttpTransport httpTransport = new AndroidHttpTransport(
				URL_SEARCH);
		envelope.encodingStyle = SoapSerializationEnvelope.XSD;

		httpTransport.debug = true;

		try {
			httpTransport.call(SOAP_ACTION_SEARCH, envelope);
			if (envelope.bodyIn instanceof SoapObject) {
				return (SoapObject) envelope.bodyIn;
			}

			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public Object get(String tab) {
		if (data != null) {
			SoapObject _return = (SoapObject) data.getProperty("return");
			return _return.getProperty(tab);
		}
		return null;
	}

	public void view() {
		List<String> lPhones = new ArrayList<String>();
		lPhones = getLPhones();
		for (int i = 0; i < lPhones.size(); i++) {
			System.out.println(lPhones.get(i));
		}
	}

	public List<String> getLPhones() {
		List<String> lPhones = new ArrayList<String>();
		if (data != null) {
			SoapObject object = (SoapObject) get(fsite);
			for (int i = 4; i < object.getPropertyCount() - 2; i++) {
				Object object2 = object.getProperty(i);
				if (object2 != null)
					lPhones.add(object2.toString());
			}
		}
		return lPhones;
	}

	public String getStatusCode() {
		try {
			if (data != null) {
				SoapObject object = (SoapObject) data;
				object = (SoapObject) object.getProperty(0);
				return object.getProperty(fstatusCode).toString();
			}
		} catch (Exception e) {
		}
		return null;
	}

	public String getStatusMessage() {
		try {
			if (data != null) {
				SoapObject object = (SoapObject) data;
				object = (SoapObject) object.getProperty(0);
				return object.getProperty(fstatusMessage).toString();
			}
		} catch (Exception e) {
		}
		return null;
	}
}
