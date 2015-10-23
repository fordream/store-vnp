package org.com.cnc.common.maispreco.response;

import java.util.List;

import com.cnc.maispreco.soap.data.Address;

public class AddressResponse {
	private String statusCode;
	private String statusMessage;
	private List<Address> lAddresses;

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

	public List<Address> getlAddresses() {
		return lAddresses;
	}

	public void setlAddresses(List<Address> lAddresses) {
		this.lAddresses = lAddresses;
	}

}
