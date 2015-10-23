package org.com.cnc.common.maispreco.response;

import org.com.cnc.common.maispreco.request.LoginRequest;
import org.com.cnc.maispreco.common.Common;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.cnc.maispreco.soap.SoapCommon;

public class LoginResponse {
	private String newCode;
	private String statusCode;
	private String statusMessage;
	private String token;
	private String messageBoxContent;
	private String messageBoxTitle;

	public String getMessageBoxContent() {
		return messageBoxContent;
	}

	public void setMessageBoxContent(String messageBoxContent) {
		this.messageBoxContent = messageBoxContent;
	}

	public String getMessageBoxTitle() {
		return messageBoxTitle;
	}

	public void setMessageBoxTitle(String messageBoxTitle) {
		this.messageBoxTitle = messageBoxTitle;
	}

	public String getNewCode() {
		return newCode;
	}

	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setData(Object soapObject) {
		if (soapObject instanceof SoapObject) {
			SoapObject object = (SoapObject) soapObject;
			newCode = Common.getStringFromSoap(object, "newCode");
			statusCode = Common.getStringFromSoap(object, "statusCode");
			statusMessage = Common.getStringFromSoap(object, "statusMessage");
			token = Common.getStringFromSoap(object, "token");
			messageBoxContent = Common.getStringFromSoap(object,
					"messageBoxContent");
			messageBoxTitle = Common.getStringFromSoap(object,
					"messageBoxTitle");
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NewCode : ").append(newCode).append("\n");
		builder.append("StatusCode : ").append(statusCode).append("\n");
		builder.append("StatusMessage : ").append(statusMessage).append("\n");
		builder.append("Token : ").append(token).append("\n");
		return builder.toString();
	}

	private static LoginResponse soapLogin(LoginRequest loginRequest) {
		LoginResponse loginResponse = new LoginResponse();
		//String URL = "http://bot4.maisprecobot.com:9009/MaisPrecoWS/services/MaisPreco?wsdl";
		//String URL = "http://webservice.maispreco.com/services/MaisPreco?wsdl";
		String URL = SoapCommon.URL_SEARCH;
		String NAMESPACE = "http://core.maispreco.com";
		String METHOD_NAME_LOGIN = "login";
		String SOAP_ACTION_LOGIN = NAMESPACE + "/" + METHOD_NAME_LOGIN;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_LOGIN);

		request.addProperty("code", loginRequest.getCode());
		request.addProperty("appVersion", loginRequest.getAppVersion());
		request.addProperty("latitude", loginRequest.getLatitude());
		request.addProperty("longitude", loginRequest.getLongitude());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		AndroidHttpTransport httpTransport = new AndroidHttpTransport(URL);
		httpTransport.debug = true;
		try {
			httpTransport.call(SOAP_ACTION_LOGIN, envelope);
			loginResponse.setData(envelope.getResponse());
		} catch (Exception e) {
		}
		return loginResponse;
	}

	public static LoginResponse getLoginResponse(LoginRequest loginRequest) {
		LoginResponse loginResponse1 = null;
		loginResponse1 = LoginResponse.soapLogin(loginRequest);
		//Log.i("abc123456789", "" + loginResponse1.getToken());
		if ("0".equals(loginResponse1.getStatusCode())) {
			return loginResponse1;
		} else {
			String latitude = loginRequest.getLatitude();
			String longitude = loginRequest.getLongitude();
			LoginRequest request = new LoginRequest(latitude, longitude);
			request.setAppVersion(loginRequest.getAppVersion());
			request.setCode(Common.convertToMD5(loginResponse1.getNewCode()));
			// loginRequest.setCode(Common.convertToMD5(loginResponse1
			// .getNewCode()));
			LoginResponse response = LoginResponse.soapLogin(request);
			//Log.i("abc___________", response.getToken());
			return response;

			// if ("0".equals(loginResponse1.getStatusCode())) {
			// return loginResponse1;
			// }
		}

		// return loginResponse1;
	}
}
