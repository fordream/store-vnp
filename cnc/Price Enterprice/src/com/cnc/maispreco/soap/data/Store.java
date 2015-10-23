package com.cnc.maispreco.soap.data;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.maispreco.common.BitmapCommon;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.cnc.maispreco.soap.SoapCommon;

public class Store {
	public static final String IDSITE = "idSite";
	public static final String INFOSITE = "infoSite";
	public static final String NAMESITE = "nameSite";
	public static final String PHONESITE = "phoneSite";
	public static final String URLSITE = "urlSite";
	public static final String URLSITELOGO = "urlSiteLogo";

	private String idSite;
	private String infoSite;
	private String nameSite;
	private String phoneSite;
	private String urlSite;
	private String urlSiteLogo;
	private Drawable drawable;

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Context context) {
		this.drawable = BitmapCommon.LoadImageProduct(urlSiteLogo, context);
	}

	public String get(String tag) {
		if (IDSITE.equals(tag))
			return idSite;
		if (INFOSITE.equals(tag))
			return infoSite;
		if (NAMESITE.equals(tag))
			return nameSite;
		if (PHONESITE.equals(tag))
			return phoneSite;
		if (URLSITE.equals(tag))
			return urlSite;
		if (URLSITELOGO.equals(tag))
			return urlSiteLogo;
		return null;
	}

	public void setIdSite(Object idSite) {
		if (idSite != null)
			this.idSite = idSite.toString();
	}

	public void setInfoSite(Object infoSite) {
		if (infoSite != null)
			this.infoSite = infoSite.toString();
	}

	public void setNameSite(Object nameSite) {
		if (nameSite != null)
			this.nameSite = nameSite.toString();
	}

	public void setPhoneSite(Object phoneSite) {
		if (phoneSite != null)
			this.phoneSite = phoneSite.toString();
	}

	public void setUrlSite(Object urlSite) {
		if (urlSite != null)
			this.urlSite = urlSite.toString();
	}

	public void setUrlSiteLogo(Object urlSiteLogo) {
		if (urlSiteLogo != null)
			this.urlSiteLogo = urlSiteLogo.toString();
	}

	public void viewData() {
		System.out.println("-------------------------------");

		view(IDSITE, idSite);
		view(INFOSITE, infoSite);
		view(NAMESITE, nameSite);
		view(PHONESITE, phoneSite);
		view(URLSITE, urlSite);
		view(URLSITELOGO, urlSiteLogo);

	}

	private void view(String tag, String value) {
		if (value != null) {
			System.out.println(tag + " = " + value);
		}
	}

	public static List<Store> getLStore(Search search, Context context) {
		List<Store> lStore = new ArrayList<Store>();
		SoapObject data = SoapCommon.soapSearch(search);

		if (data != null) {
			SoapObject soap = (SoapObject) data.getProperty(0);

			for (int i = 0; i < soap.getPropertyCount(); i++) {
				try {
					if (soap.getProperty(i) instanceof SoapObject) {
						SoapObject item = (SoapObject) soap.getProperty(i);
						Store store = new Store();
						store.setIdSite(item.getProperty(IDSITE));
						store.setInfoSite(item.getProperty(INFOSITE));
						store.setNameSite(item.getProperty(NAMESITE));
						store.setPhoneSite(item.getProperty(PHONESITE));
						store.setUrlSite(item.getProperty(URLSITE));
						store.setUrlSiteLogo(item.getProperty(URLSITELOGO));
						store.setDrawable(context);
						lStore.add(store);
					}
				} catch (Exception e) {
				}
			}
		}

		return lStore;
	}

	public void setDrawable(Drawable drawable2) {
		this.drawable = drawable2;
		
	}

}
