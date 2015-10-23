package com.cnc.maispreco.soap.data;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.maispreco.response.OffersResponse;
import org.com.cnc.maispreco.common.BitmapCommon;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.cnc.maispreco.soap.SoapCommon;

public class Offers {
	public static final String ID = "id";
	public static final String LAB = "lab";
	public static final String NAMESITE = "nameSite";
	public static final String OFFERINFORMATION = "offerInformation";
	public static final String PRICE = "price";
	public static final String PRICEBEFORE = "priceBefore";
	public static final String PRODUCTNAME = "productName";
	public static final String PROMODESCRIPTION = "promoDescription";
	public static final String SITEID = "siteId";
	public static final String URLLOGOSITE = "urlLogoSite";
	public static final String URLOFFER = "urlOffer";
	public static final String URLSITE = "urlSite";
	public static final String OFFERIMAGE = "offerImage";
	public static final String OFFERNAME = "offerName";
	private String id;
	private String lab;
	private String nameSite;
	private String offerInformation;
	private String price;
	private String priceBefore;
	private String productName;
	private String promoDescription;
	private String siteId;
	private String urlLogoSite;
	private String urlOffer;
	private String urlSite;
	private String offerName;
	private String offerImage;
	public static final String RESULTTOTAL = "resultTotal";
	private Drawable drawable;
	private Drawable drawableGroup;
	private Bitmap bitmap;

	public Drawable getDrawableGroup() {
		return drawableGroup;
	}

	public void setDrawableGroup(Drawable drawableGroup) {
		this.drawableGroup = drawableGroup;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
		if (drawable != null) {
			bitmap = ((BitmapDrawable) drawable).getBitmap();
		}
	}

	public void setDrawable() {
		this.drawable = BitmapCommon.LoadImageFromWebOperations(urlLogoSite);
		if (drawable != null) {
			bitmap = ((BitmapDrawable) drawable).getBitmap();
		}
	}

	public void setDrawable(Context context) {
		dowload.execute("");
	}

	AsyncTask<String, String, String> dowload = new AsyncTask<String, String, String>() {
		Drawable drawable;

		Drawable drawableGroup;

		protected String doInBackground(String... params) {
			drawable = BitmapCommon.LoadImageFromWebOperations(urlLogoSite);
			drawableGroup = BitmapCommon.LoadImageFromWebOperations(offerImage);
			return null;
		}

		protected void onPostExecute(String result) {
			if (drawable != null) {
				setDrawable(drawable);
				setDrawableGroup(drawableGroup);
			}
		}
	};

	public void setId(Object id) {
		if (id != null)
			this.id = id.toString();
	}

	public void setLab(Object lab) {
		if (lab != null)
			this.lab = lab.toString();
	}

	public void setNameSite(Object nameSite) {
		if (nameSite != null)
			this.nameSite = nameSite.toString();
	}

	public void setOfferInformation(Object offerInformation) {
		if (offerInformation != null)
			this.offerInformation = offerInformation.toString();
	}

	public void setPrice(Object price) {
		if (price != null)
			this.price = price.toString();
	}

	public void setPriceBefore(Object priceBefore) {
		if (priceBefore != null)
			this.priceBefore = priceBefore.toString();
	}

	public void setProductName(Object productName) {
		if (productName != null)
			this.productName = productName.toString();
	}

	public void setPromoDescription(Object promoDescription) {
		if (promoDescription != null)
			this.promoDescription = promoDescription.toString();
	}

	public void setSiteId(Object siteId) {
		if (siteId != null)
			this.siteId = siteId.toString();
	}

	public void setUrlLogoSite(Object urlLogoSite) {
		if (urlLogoSite != null)
			this.urlLogoSite = urlLogoSite.toString();
	}

	public void setUrlOffer(Object urlOffer) {
		if (urlOffer != null)
			this.urlOffer = urlOffer.toString();
	}

	public void setUrlSite(Object urlSite) {
		if (urlSite != null)
			this.urlSite = urlSite.toString();
	}

	public void setOfferImage(Object urlSite) {
		if (urlSite != null)
			this.offerImage = urlSite.toString();
	}

	public void setOfferName(Object urlSite) {
		if (urlSite != null)
			this.offerName = urlSite.toString();
	}

	public String get(String tag) {
		if (ID.equals(tag))
			return id;
		if (LAB.equals(tag))
			return lab;
		if (NAMESITE.equals(tag))
			return nameSite;
		if (OFFERINFORMATION.equals(tag))
			return offerInformation;
		if (PRICE.equals(tag))
			return price;
		if (PRICEBEFORE.equals(tag))
			return priceBefore;
		if (PRODUCTNAME.equals(tag))
			return productName;
		if (PROMODESCRIPTION.equals(tag))
			return promoDescription;
		if (SITEID.equals(tag))
			return siteId;
		if (URLLOGOSITE.equals(tag))
			return urlLogoSite;
		if (URLOFFER.equals(tag))
			return urlOffer;
		if (URLSITE.equals(tag))
			return urlSite;

		if (OFFERIMAGE.equals(tag))
			return offerImage;
		if (OFFERNAME.equals(tag))
			return offerName;

		return null;
	}

	public static OffersResponse getLOffer(Search search, Context context,
			String count) {
		OffersResponse offersResponse = new OffersResponse();
		int _count = 1;

		try {
			_count = Integer.parseInt(count);

			_count = _count / 10 + (_count % 10 == 0 ? 0 : 1);
		} catch (Exception e) {
		}
		List<Offers> lOffers = new ArrayList<Offers>();
		for (int j = 0; j < _count + 1; j++) {
			search.setPage("" + (j + 1));
			SoapObject data = SoapCommon.soapSearch(search);
			if (data != null) {
				SoapObject soap = (SoapObject) data.getProperty(0);
				offersResponse.setStatusCode(soap.getProperty("statusCode")
						.toString());
				offersResponse.setStatusMessage(soap.getProperty(
						"statusMessage").toString());
				offersResponse.setResultTotal(soap.getProperty(RESULTTOTAL)
						.toString());
				if ("-1".equals(offersResponse.getStatusCode())) {
					return offersResponse;
				}
				for (int i = 0; i < soap.getPropertyCount(); i++) {
					try {
						SoapObject item = (SoapObject) soap.getProperty(i);
						Offers offers = new Offers();
						offers.setId(item.getProperty(ID));
						offers.setLab(item.getProperty(LAB));
						offers.setNameSite(item.getProperty(NAMESITE));
						offers.setOfferInformation(item
								.getProperty(OFFERINFORMATION));
						offers.setPrice(item.getProperty(PRICE));
						offers.setPriceBefore(item.getProperty(PRICEBEFORE));
						offers.setProductName(item.getProperty(PRODUCTNAME));
						offers.setPromoDescription(item
								.getProperty(PROMODESCRIPTION));
						offers.setSiteId(item.getProperty(SITEID));
						offers.setUrlLogoSite(item.getProperty(URLLOGOSITE));
						offers.setUrlOffer(item.getProperty(URLOFFER));
						offers.setUrlSite(item.getProperty(URLSITE));
						offers.setOfferImage(item.getProperty(OFFERIMAGE));
						offers.setOfferName(item.getProperty(OFFERNAME));
						offers.setDrawable(context);
						lOffers.add(offers);
					} catch (Exception e) {
					}
				}
			}
		}
		offersResponse.setLoOffersResponses(lOffers);
		return offersResponse;
	}

	public static OffersResponse getLOffer(Search search, Context context,
			int start) {
		OffersResponse offersResponse = new OffersResponse();

		List<Offers> lOffers = new ArrayList<Offers>();
		search.setPage("" + (start));
		SoapObject data = SoapCommon.soapSearch(search);
		if (data != null) {
			SoapObject soap = (SoapObject) data.getProperty(0);
			offersResponse.setStatusCode(soap.getProperty("statusCode")
					.toString());
			offersResponse.setStatusMessage(soap.getProperty("statusMessage")
					.toString());
			offersResponse.setResultTotal(soap.getProperty(RESULTTOTAL)
					.toString());
			if ("-1".equals(offersResponse.getStatusCode())) {
				return offersResponse;
			}
			for (int i = 0; i < soap.getPropertyCount(); i++) {
				try {
					SoapObject item = (SoapObject) soap.getProperty(i);
					Offers offers = new Offers();
					offers.setId(item.getProperty(ID));
					offers.setLab(item.getProperty(LAB));
					offers.setNameSite(item.getProperty(NAMESITE));
					offers.setOfferInformation(item
							.getProperty(OFFERINFORMATION));
					offers.setPrice(item.getProperty(PRICE));
					offers.setPriceBefore(item.getProperty(PRICEBEFORE));
					offers.setProductName(item.getProperty(PRODUCTNAME));
					offers.setPromoDescription(item
							.getProperty(PROMODESCRIPTION));
					offers.setSiteId(item.getProperty(SITEID));
					offers.setUrlLogoSite(item.getProperty(URLLOGOSITE));
					offers.setUrlOffer(item.getProperty(URLOFFER));
					offers.setUrlSite(item.getProperty(URLSITE));
					offers.setOfferImage(item.getProperty(OFFERIMAGE));
					offers.setOfferName(item.getProperty(OFFERNAME));
					offers.setDrawable(context);
					lOffers.add(offers);
				} catch (Exception e) {
				}
			}
		}
		offersResponse.setLoOffersResponses(lOffers);
		return offersResponse;
	}

	public void viewData() {
		System.out.println("-------------------------------");
		view(ID, id);
		view(LAB, lab);
		view(NAMESITE, nameSite);
		view(OFFERINFORMATION, offerInformation);
		view(PRICE, price);
		view(PRICEBEFORE, priceBefore);
		view(PRODUCTNAME, productName);
		view(PROMODESCRIPTION, promoDescription);
		view(SITEID, siteId);
		view(URLLOGOSITE, urlLogoSite);
		view(URLOFFER, urlOffer);
		view(URLSITE, urlSite);
	}

	private void view(String tag, String value) {
		if (value != null) {
			System.out.println(tag + " = " + value);
		}
	}

}
