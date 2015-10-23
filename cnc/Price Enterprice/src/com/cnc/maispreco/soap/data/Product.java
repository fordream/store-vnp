package com.cnc.maispreco.soap.data;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.maispreco.response.ProductResponse;
import org.com.cnc.common.maispreco.response.Response;
import org.com.cnc.maispreco.common.BitmapCommon;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.cnc.maispreco.common.CommonView;
import com.cnc.maispreco.soap.SoapCommon;

public class Product {
	public static final String BULLID = "bullId";
	public static final String DRUGTYPE = "drugType";
	public static final String FP = "fp";
	public static final String GENERICSTARTINGAT = "genericStartingAt";
	public static final String ID = "id";
	public static final String LAB = "lab";
	public static final String LABID = "labId";
	public static final String MAXPRICE = "maxPrice";
	public static final String MINPRICE = "minPrice";
	public static final String NAME = "name";
	public static final String PMC = "pmc";
	public static final String PRODUCTTYPE = "productType";
	public static final String QTGENERIC = "qtGeneric";
	public static final String QTOFFERS = "qtOffers";
	public static final String QTSIMILAR = "qtSimilar";
	public static final String SAL = "sal";
	public static final String SIMILARSTARTINGAT = "similarStartingAt";
	public static final String URLIMAGE = "urlImage";
	public static final String GROUP = "group";
	public static final String RESULTTOTAL = "resultTotal";
	private String bullId = null;
	private String drugType = null;
	private String fp = null;
	private String genericStartingAt = null;
	private String id = null;
	private String lab = null;
	private String labId = null;
	private String maxPrice = null;
	private String minPrice = null;
	private String name = null;
	private String pmc = null;
	private String productType = null;
	private String qtGeneric = null;
	private String qtOffers = null;
	private String qtSimilar = null;
	private String sal = null;
	private String similarStartingAt = null;
	private String urlImage = null;
	private String resultTotal;

	private String group;
	private Bitmap bitmap;
	private Drawable drawable = null;

	public String get(String tag) {
		if (BULLID.equals(tag))
			return bullId;

		if (RESULTTOTAL.equals(tag))
			return resultTotal;

		if (GROUP.equals(tag))
			return group;
		if (DRUGTYPE.equals(tag))
			return drugType;

		if (FP.equals(tag))
			return fp;

		if (GENERICSTARTINGAT.equals(tag))
			return genericStartingAt;

		if (ID.equals(tag))
			return id;

		if (LAB.equals(tag))
			return lab;

		if (LABID.equals(tag))
			return labId;

		if (MAXPRICE.equals(tag))
			return maxPrice;

		if (MINPRICE.equals(tag))
			return minPrice;

		if (NAME.equals(tag))
			return name;

		if (PMC.equals(tag))
			return pmc;

		if (PRODUCTTYPE.equals(tag))
			return productType;

		if (QTGENERIC.equals(tag))
			return qtGeneric;

		if (QTOFFERS.equals(tag))
			return qtOffers;

		if (QTSIMILAR.equals(tag))
			return qtSimilar;

		if (SAL.equals(tag))
			return sal;

		if (SIMILARSTARTINGAT.equals(tag))
			return similarStartingAt;

		if (URLIMAGE.equals(tag))
			return urlImage;

		return null;
	}

	public void setBullId(Object bullId) {
		if (bullId != null)
			this.bullId = bullId.toString();
	}

	public void setDrugType(Object drugType) {
		if (drugType != null)
			this.drugType = drugType.toString();
	}

	public void setFp(Object fp) {
		if (fp != null)
			this.fp = fp.toString();
	}

	public void setGroup(Object flag) {
		if (flag != null)
			this.group = flag.toString();
	}

	public void setGenericStartingAt(Object genericStartingAt) {
		if (genericStartingAt != null)
			this.genericStartingAt = genericStartingAt.toString();
	}

	public void setId(Object id) {
		if (id != null)
			this.id = id.toString();
	}

	public void setLab(Object lab) {
		if (lab != null)
			this.lab = lab.toString();
	}

	public void setLabId(Object labId) {
		if (labId != null)
			this.labId = labId.toString();
	}

	public void setMaxPrice(Object maxPrice) {
		if (maxPrice != null) {
			this.maxPrice = maxPrice.toString();
			if (maxPrice.toString().contains("any")) {
				this.maxPrice = "?";
			}
		}

	}

	public void setMinPrice(Object minPrice) {
		if (minPrice != null) {
			this.minPrice = minPrice.toString();
			if (minPrice.toString().contains("any")) {
				this.minPrice = "?";
			}
		}

	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void setName(Object name) {
		if (name != null)
			this.name = name.toString();
	}

	public void setPmc(Object pmc) {
		if (pmc != null)
			this.pmc = pmc.toString();
	}

	public void setProductType(Object productType) {
		if (productType != null)
			this.productType = productType.toString();
	}

	public void setQtGeneric(Object qtGeneric) {
		if (qtGeneric != null)
			this.qtGeneric = qtGeneric.toString();
	}

	public void setQtOffers(Object qtOffers) {
		if (qtOffers != null)
			this.qtOffers = qtOffers.toString();
	}

	public void setQtSimilar(Object qtSimilar) {
		if (qtSimilar != null)
			this.qtSimilar = qtSimilar.toString();
	}

	public void setSal(Object sal) {
		if (sal != null)
			this.sal = sal.toString();
	}

	public void setSimilarStartingAt(Object similarStartingAt) {
		if (similarStartingAt != null)
			this.similarStartingAt = similarStartingAt.toString();
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Context context) {
		if (context == null) {
			this.drawable = null;
		} else {
			dowload.execute(urlImage);
		}
	}

	AsyncTask<String, String, String> dowload = new AsyncTask<String, String, String>() {
		Drawable drawable;

		protected String doInBackground(String... params) {
			String url = params[0];
			drawable = BitmapCommon.LoadImageFromWebOperations(url);
			return null;
		}

		protected void onPostExecute(String result) {
			if (bitmap != null) {
			}

			if (drawable != null) {
				setDrawable(drawable);
			}
		}
	};

	public void setDrawable(Drawable context) {
		this.drawable = context;
		Bitmap bitmap = CommonView.convertBitmap(((BitmapDrawable) drawable)
				.getBitmap());
		setBitmap(bitmap);
	}

	public void setUrlImage(Object urlImage) {
		if (urlImage != null)
			this.urlImage = urlImage.toString();
	}

	public static ProductResponse getLProduct(Search search, Context context) {
		ProductResponse productResponse = new ProductResponse();
		int index = 1;
		List<Product> lProduct = new ArrayList<Product>();

		int count = 0;
		do {
			search.setPage("" + index);
			Search search2 = new Search();
			search2.setAppVersion(search.getAppVersion());
			search2.setCode(search.getCode());
			search2.setLatitude(search.getLatitude());
			search2.setLocaleId(search.getLocaleId());
			search2.setNearBy(search.getNearBy());
			search2.setPage("" + index);
			search2.setSearch(search.getSearch());
			search2.setSearchType(search.getSearchType());
			search2.setToken(search.getToken());
			index++;
			count = 0;
			SoapObject data = SoapCommon.soapSearch(search);
			if (data != null) {
				SoapObject soap = (SoapObject) data.getProperty(0);
				productResponse.setStatusCode(soap.getProperty(
						Response.STATUSCODE).toString());
				productResponse.setStatusMessage(soap.getProperty(
						Response.TATUSMESSAGE).toString());
				productResponse.setResultTotal(soap.getProperty(RESULTTOTAL)
						.toString());
				Log.i("ABC", productResponse.getResultTotal());
				if ("-1".equals(productResponse.getStatusCode())) {
					return productResponse;
				}
				for (int i = 0; i < soap.getPropertyCount(); i++) {
					try {
						SoapObject item = (SoapObject) soap.getProperty(i);
						Product product = new Product();
						product.setUrlImage(item.getProperty(URLIMAGE));
						product.setBullId(item.getProperty(BULLID));
						product.setDrugType(item.getProperty(DRUGTYPE));
						product.setFp(item.getProperty(FP));
						product.setGenericStartingAt(item
								.getProperty(GENERICSTARTINGAT));
						product.setId(item.getProperty(ID));
						product.setLab(item.getProperty(LAB));
						product.setLabId(item.getProperty(LABID));
						product.setMaxPrice(item.getProperty(MAXPRICE));
						product.setMinPrice(item.getProperty(MINPRICE));
						product.setName(item.getProperty(NAME));
						product.setPmc(item.getProperty(PMC));
						product.setProductType(item.getProperty(PRODUCTTYPE));
						product.setQtGeneric(item.getProperty(QTGENERIC));
						product.setQtOffers(item.getProperty(QTOFFERS));
						product.setQtSimilar(item.getProperty(QTSIMILAR));
						product.setSal(item.getProperty(SAL));
						product.setSimilarStartingAt(item
								.getProperty(SIMILARSTARTINGAT));
						product.setGroup(item.getProperty(GROUP));
						product.setDrawable(context);
						if (!("_".equals(product.get(MINPRICE)) && "_"
								.equals(product.get(MAXPRICE)))) {
							for (int k = 0; k < lProduct.size(); k++) {
								Product product2 = lProduct.get(k);
								if (product2.get(Product.ID).equals(
										product.get(Product.ID))) {
									throw new Exception();
								}
							}
							lProduct.add(product);
						}
						count++;
					} catch (Exception e) {
					}

				}
			}
			Log.i("ABC", "" + lProduct.size());
		} while (count != 0);
		productResponse.setlProducts(lProduct);
		return productResponse;
	}

	public static ProductResponse getLProductHome(Search search, Context context) {
		ProductResponse productResponse = new ProductResponse();
		List<Product> lProduct = new ArrayList<Product>();
		SoapObject data = SoapCommon.soapSearch(search);
		if (data != null) {
			SoapObject soap = (SoapObject) data.getProperty(0);
			productResponse.setStatusCode(soap.getProperty(Response.STATUSCODE)
					.toString());
			productResponse.setStatusMessage(soap.getProperty(
					Response.TATUSMESSAGE).toString());
			productResponse.setResultTotal(soap.getProperty(RESULTTOTAL)
					.toString());
			Log.i("ABC", productResponse.getResultTotal());
			if ("-1".equals(productResponse.getStatusCode())) {
				return productResponse;
			}

			for (int i = 0; i < soap.getPropertyCount(); i++) {
				try {
					SoapObject item = (SoapObject) soap.getProperty(i);
					Product product = new Product();
					product.setUrlImage(item.getProperty(URLIMAGE));
					product.setBullId(item.getProperty(BULLID));
					product.setDrugType(item.getProperty(DRUGTYPE));
					product.setFp(item.getProperty(FP));
					product.setGenericStartingAt(item
							.getProperty(GENERICSTARTINGAT));
					product.setId(item.getProperty(ID));
					product.setLab(item.getProperty(LAB));
					product.setLabId(item.getProperty(LABID));
					product.setMaxPrice(item.getProperty(MAXPRICE));
					product.setMinPrice(item.getProperty(MINPRICE));
					product.setName(item.getProperty(NAME));
					product.setPmc(item.getProperty(PMC));
					product.setProductType(item.getProperty(PRODUCTTYPE));
					product.setQtGeneric(item.getProperty(QTGENERIC));
					product.setQtOffers(item.getProperty(QTOFFERS));
					product.setQtSimilar(item.getProperty(QTSIMILAR));
					product.setSal(item.getProperty(SAL));
					product.setSimilarStartingAt(item
							.getProperty(SIMILARSTARTINGAT));
					product.setGroup(item.getProperty(GROUP));
					product.setDrawable(context);
					if (!("_".equals(product.get(MINPRICE)) && "_"
							.equals(product.get(MAXPRICE)))) {
						for (int k = 0; k < lProduct.size(); k++) {
							Product product2 = lProduct.get(k);
							if (product2.get(Product.ID).equals(
									product.get(Product.ID))) {
								throw new Exception();
							}
						}

						lProduct.add(product);
					}
				} catch (Exception e) {
				}
			}
		}
		productResponse.setlProducts(lProduct);
		return productResponse;
	}

}
