package com.cnc.maispreco.soap.data;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.cnc.maispreco.common.StringCommon;
import com.cnc.maispreco.soap.SoapCommon;

public class Locality {
	public static final String DEFAULTLOCALE = "defaultLocale";
	public static final String ID = "id";
	public static final String NAME = "name";
	private String defaultLocale;
	private String id;
	private String name;

	public void setDefaultLocale(Object defaultLocale) {
		this.defaultLocale = StringCommon.convertObjectToString(defaultLocale);
	}

	public void setId(Object id) {
		this.id = StringCommon.convertObjectToString(id);
	}

	public void setName(Object name) {
		this.name = StringCommon.convertObjectToString(name);
	}

	public String get(String tag) {
		if (ID.equals(tag))
			return id;
		if (DEFAULTLOCALE.equals(tag))
			return defaultLocale;
		if (NAME.equals(tag))
			return name;
		return null;

	}

	public static Locality getLocality(SoapObject data) {
		try {
			Locality locality = new Locality();
			locality.setDefaultLocale(data.getProperty(DEFAULTLOCALE));
			locality.setId(data.getProperty(ID));
			locality.setName(data.getProperty(NAME));
			return locality;
		} catch (Exception e) {
			return null;
		}
	}

	public static List<Locality> getLLocality(Search search) {

		List<Locality> lLocality = new ArrayList<Locality>();
		search.setSearchType("1");
		SoapObject data = SoapCommon.soapSearch(search);
		if (data != null) {
			SoapObject soap = (SoapObject) data.getProperty(0);

			for (int i = 0; i < soap.getPropertyCount(); i++) {
				if (soap.getProperty(i) instanceof SoapObject) {
					SoapObject item = (SoapObject) soap.getProperty(i);
					Locality locality = getLocality(item);
					if (locality != null) {
						lLocality.add(locality);
					}
				}
			}
		}

		return lLocality;
	}

	public void viewData() {
		System.out.println("-------------------------------");

		// product
		view(DEFAULTLOCALE, defaultLocale);
		view(ID, id);
		view(NAME, name);

	}

	private void view(String tag, String value) {
		if (value != null) {
			System.out.println(tag + " = " + value);
		}
	}
}
