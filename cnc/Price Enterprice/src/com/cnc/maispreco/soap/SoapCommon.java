package com.cnc.maispreco.soap;

import org.com.cnc.maispreco.common.Common;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cnc.maispreco.soap.data.Search;

public class SoapCommon {
	public static final int TYPE_CATEGORY = 1;
	public static final int TYPE_PRODUCT = 2;
	public static final int TYPE_OFFERS = 3;
	public static final int TYPE_STORE = 4;
	public static final int TYPE_ADDRESS = 5;
	public static final int TYPE_LOCALITY = 6;
	public static final String BLANK = "";

	// public static final String URL =
	// "http://bot4.maisprecobot.com:9009/MaisPrecoWS/services/MaisPreco?wsdl";
	// public static final String URL =
	// "http://webservice.maispreco.com/services/MaisPreco?wsdl";
	// public static final String NAMESPACE = "http://core.maispreco.com";
	// public static final String METHOD_NAME_LOGIN = "login";
	// public static final String SOAP_ACTION_LOGIN = NAMESPACE + "/"
	// + METHOD_NAME_LOGIN;
	// public static final String URL_SEARCH =
	// "http://bot4.maisprecobot.com:9009/MaisPrecoWS/services/MaisPreco?wsdl";
	public static final String URL_SEARCH = "http://webservice.maispreco.com/services/MaisPreco?wsdl";
	public static final String NAMESPACE_SEARCH = "http://core.maispreco.com";
	public static final String METHOD_NAME_SEARCH = "search";
	public static final String SOAP_ACTION_SEARCH = "" + NAMESPACE_SEARCH + "/"
			+ METHOD_NAME_SEARCH;

	public static SoapObject soapSearch(final Search search) {

		SoapObject request = new SoapObject(NAMESPACE_SEARCH,
				METHOD_NAME_SEARCH);

		SoapObject _search = new SoapObject(NAMESPACE_SEARCH,
				METHOD_NAME_SEARCH);

		if (search.getLocaleId() != null) {
			// _search.addProperty("localeId", search.getLocaleId());
		}

		if (Common.LOCALID != null) {
			_search.addProperty("localeId", Common.LOCALID);
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
		//AndroidHttpTransport httpTransport = new AndroidHttpTransport(
		///		URL_SEARCH, 1000);
		HttpTransportSE httpTransport = new HttpTransportSE(URL_SEARCH, 10000);
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
}