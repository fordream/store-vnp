package com.cnc.maispreco.soap.data;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.maispreco.response.AddressResponse;
import org.ksoap2.serialization.SoapObject;

import com.cnc.maispreco.common.StringCommon;
import com.cnc.maispreco.soap.SoapCommon;

public class Address {
	public static final String ADDRESS = "address";
	public static final String CITY = "city";
	public static final String INFO = "info";
	public static final String NEIGHBORHOOD = "neighborhood";
	public static final String PHONE = "phone";
	public static final String STATE = "state";

	private String address;
	private String city;
	private String info;
	private String neighborhood;
	private String phone;
	private String state;

	public String get(String tag) {
		if (ADDRESS.equals(tag))
			return address;
		if (CITY.equals(tag))
			return city;
		if (INFO.equals(tag))
			return info;
		if (NEIGHBORHOOD.equals(tag))
			return neighborhood;
		if (PHONE.equals(tag))
			return phone;
		if (STATE.equals(tag))
			return state;
		return null;
	}

	public void setAddress(Object address) {
		if (address != null) {
			this.address = address.toString();
		}
	}

	public void setCity(Object city) {
		this.city = StringCommon.convertObjectToString(city);
	}

	public void setInfo(Object info) {
		this.info = StringCommon.convertObjectToString(info);
	}

	public void setNeighborhood(Object neighborhood) {
		this.neighborhood = StringCommon.convertObjectToString(neighborhood);
	}

	public void setPhone(Object phone) {
		this.phone = StringCommon.convertObjectToString(phone);
	}

	public void setState(Object state) {
		this.state = StringCommon.convertObjectToString(state);
	}

	public void viewData() {
		System.out.println("-------------------------------");

		view(ADDRESS, address);
		view(CITY, city);
		view(INFO, info);
		view(NEIGHBORHOOD, neighborhood);
		view(PHONE, phone);
		view(STATE, state);

	}

	private void view(String tag, String value) {
		if (value != null) {
			System.out.println(tag + " = " + value);
		}
	}

	public static Address getAddress(SoapObject data) {
		try {
			Address address = new Address();
			address.setAddress(data.getProperty(ADDRESS));
			address.setCity(data.getProperty(CITY));
			address.setInfo(data.getProperty(INFO));
			address.setNeighborhood(data.getProperty(NEIGHBORHOOD));
			address.setPhone(data.getProperty(PHONE));
			address.setState(data.getProperty(STATE));
			return address;
		} catch (Exception e) {
			return null;
		}
	}


	public static AddressResponse getLAddress(Search search) {
		AddressResponse addressResponse = new AddressResponse();
		List<Address> lAddress = new ArrayList<Address>();
		SoapObject data = SoapCommon.soapSearch(search);
		if (data != null) {
			SoapObject soap = (SoapObject) data.getProperty(0);
			Object object = soap.getProperty("statusCode");
			addressResponse.setStatusCode(object == null ? "" : object
					.toString());
			object = soap.getProperty("statusMessage");
			addressResponse.setStatusMessage(object == null ? "" : object
					.toString());
			for (int i = 0; i < soap.getPropertyCount(); i++) {
				if (soap.getProperty(i) instanceof SoapObject) {
					SoapObject item = (SoapObject) soap.getProperty(i);
					Address address = getAddress(item);
					if (address != null) {
						lAddress.add(address);
					}
				}
			}
		}
		addressResponse.setlAddresses(lAddress);
		return addressResponse;
	}
}
