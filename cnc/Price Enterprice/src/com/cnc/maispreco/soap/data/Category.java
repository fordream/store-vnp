package com.cnc.maispreco.soap.data;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.maispreco.response.CategoryResponse;
import org.com.cnc.common.maispreco.response.ProductResponse;
import org.com.cnc.common.maispreco.response.Response;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;

import com.cnc.maispreco.common.StringCommon;
import com.cnc.maispreco.soap.SoapCommon;

public class Category {
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String RESULTTOTAL = "resultTotal";
	private String id = null;
	private String name = null;

	public String get(String tag) {
		if (ID.equals(tag))
			return id;

		if (NAME.equals(tag))
			return name;

		return null;
	}

	public void setId(Object id) {
		this.id = StringCommon.convertObjectToString(id);
	}

	public void setName(Object name) {
		this.name = StringCommon.convertObjectToString(name);
	}

	public static CategoryResponse getLCategory(String token, int page) {
		int index = 1;
		CategoryResponse categoryResponse = new CategoryResponse();
		List<Category> lCategory = new ArrayList<Category>();
		Search search = new Search();
		search.setPage("" + index);
		search.setToken(token);
		search.setSearchType("2");
		try {
			search.setPage("" + (page));
			index++;
			SoapObject data = SoapCommon.soapSearch(search);
			if (data != null) {
				SoapObject soap1 = (SoapObject) data.getProperty(0);
				categoryResponse.setStatusCode(soap1.getProperty(
						Response.STATUSCODE).toString());
				categoryResponse.setStatusMessage(soap1.getProperty(
						Response.TATUSMESSAGE).toString());
				categoryResponse.setResultTotal(soap1.getProperty(RESULTTOTAL)
						.toString());
			}
			try {
				((SoapObject) data.getProperty("return"))
						.getProperty("categories");
			} catch (Exception e) {
				data = null;
			}

			if (data != null) {
				SoapObject soap = (SoapObject) data.getProperty(0);
				for (int i = 1; i < soap.getPropertyCount() - 4; i++) {
					SoapObject item = (SoapObject) soap.getProperty(i);
					Category category = new Category();
					category.setId(item.getProperty(ID));
					category.setName(item.getProperty(NAME));

					lCategory.add(category);
				}
			} else {
				categoryResponse.setlProducts(lCategory);
				return categoryResponse;
			}
		} catch (Exception e) {
		}
		categoryResponse.setlProducts(lCategory);
		return categoryResponse;
	}

	// public static List<Category> getLCategory(Search search) {
	// int index = 1;
	// List<Category> lCategory = new ArrayList<Category>();
	//
	// search.setPage("" + index);
	// search.setSearchType("2");
	// int count = 0;
	// do {
	// search.setPage("" + index);
	// index++;
	// SoapObject data = SoapCommon.soapSearch(search);
	// try {
	// ((SoapObject) data.getProperty("return"))
	// .getProperty("categories");
	// } catch (Exception e) {
	// data = null;
	// }
	//
	// count = 0;
	// if (data != null) {
	// SoapObject soap = (SoapObject) data.getProperty(0);
	// for (int i = 1; i < soap.getPropertyCount() - 4; i++) {
	// count++;
	// SoapObject item = (SoapObject) soap.getProperty(i);
	// Category category = new Category();
	// category.setId(item.getProperty(ID));
	// category.setName(item.getProperty(NAME));
	// lCategory.add(category);
	// }
	// } else {
	// return lCategory;
	// }
	// } while (count != 0);
	//
	// return lCategory;
	// }

	public static Response getLCategory1(Search search, Context context,
			int page) {
		Response response = new CategoryResponse();
		int index = 1;
		List<Category> lCategory = new ArrayList<Category>();
		List<Product> lProducts = new ArrayList<Product>();
		search.setPage("" + index);
		search.setSearchType("2");
		boolean checkCategory = false;
		search.setPage("" + page);
		SoapObject data = SoapCommon.soapSearch(search);

		if (data != null) {
			SoapObject object = (SoapObject) data.getProperty(0);
			response.setStatusCode(object.getProperty("statusCode").toString());
			response.setStatusMessage(object.getProperty("statusMessage")
					.toString());
			response.setResultTotal(object.getProperty(RESULTTOTAL).toString());
		}

		if ("-1".equals(response.getStatusCode())) {
			return response;
		}

		try {
			((SoapObject) data.getProperty("return")).getProperty("categories");
			if (index == 1) {
				response = new CategoryResponse();
			}
			if (data.getProperty(0) instanceof SoapObject) {
				SoapObject soap = (SoapObject) data.getProperty(0);

				for (int i = 0; i < soap.getPropertyCount(); i++) {
					try {
						SoapObject item = (SoapObject) soap.getProperty(i);
						Category category = new Category();
						category.setId(item.getProperty(ID));
						category.setName(item.getProperty(NAME));
						boolean check = false;
						for (int k = 0; k < lCategory.size(); k++) {
							if (lCategory.get(k).get(Category.ID)
									.equals(category.get(Category.ID))) {
								check = true;
								break;
							}
						}
						if (check) {
							((CategoryResponse) response)
									.setlProducts(lCategory);
							return response;
						}
						lCategory.add(category);
					} catch (Exception e) {
					}
				}
			}

		} catch (Exception e) {
			if (!checkCategory) {
				if (data != null) {
					if (index == 1) {
						response = new ProductResponse();
					}

					if (data.getProperty(0) instanceof SoapObject) {
						SoapObject soap = (SoapObject) data.getProperty(0);
						for (int i = 0; i < soap.getPropertyCount(); i++) {
							try {
								SoapObject item = (SoapObject) soap
										.getProperty(i);
								Product product = new Product();
								product.setUrlImage(item
										.getProperty(Product.URLIMAGE));
								product.setBullId(item
										.getProperty(Product.BULLID));
								product.setDrugType(item
										.getProperty(Product.DRUGTYPE));
								product.setFp(item.getProperty(Product.FP));
								product.setGenericStartingAt(item
										.getProperty(Product.GENERICSTARTINGAT));
								product.setId(item.getProperty(Product.ID));
								product.setLab(item.getProperty(Product.LAB));
								product.setLabId(item
										.getProperty(Product.LABID));
								product.setMaxPrice(item
										.getProperty(Product.MAXPRICE));
								product.setMinPrice(item
										.getProperty(Product.MINPRICE));
								product.setName(item.getProperty(Product.NAME));
								product.setPmc(item.getProperty(Product.PMC));
								product.setProductType(item
										.getProperty(Product.PRODUCTTYPE));
								product.setQtGeneric(item
										.getProperty(Product.QTGENERIC));
								product.setQtOffers(item
										.getProperty(Product.QTOFFERS));
								product.setQtSimilar(item
										.getProperty(Product.QTSIMILAR));
								product.setSal(item.getProperty(Product.SAL));
								product.setSimilarStartingAt(item
										.getProperty(Product.SIMILARSTARTINGAT));
								product.setGroup(item
										.getProperty(Product.GROUP));
								product.setDrawable(context);
								if (!("_".equals(product.get(Product.MINPRICE)) && "_"
										.equals(product.get(Product.MAXPRICE)))) {
									boolean check = false;
									for (int k = 0; k < lProducts.size(); k++) {
										if (lProducts
												.get(k)
												.get(Product.ID)
												.equals(product.get(Product.ID))) {
											check = true;
											break;
										}
									}
									if (!check) {
										lProducts.add(product);
									} else {
										((ProductResponse) response)
												.setlProducts(lProducts);
										return response;
									}
								}
							} catch (Exception e1) {
							}
						}
					}
				}
			}
		}

		if (response instanceof CategoryResponse) {
			((CategoryResponse) response).setlProducts(lCategory);
		} else {
			((ProductResponse) response).setlProducts(lProducts);
		}
		return response;
	}

//	public static List<Category> getLCategory(SoapObject data) {
//
//		List<Category> lCategory = new ArrayList<Category>();
//		try {
//			((SoapObject) data.getProperty("return")).getProperty("categories");
//		} catch (Exception e) {
//			return lCategory;
//		}
//		if (data != null) {
//			SoapObject soap = (SoapObject) data.getProperty(0);
//
//			for (int i = 1; i < soap.getPropertyCount() - 4; i++) {
//				try {
//					SoapObject item = (SoapObject) soap.getProperty(i);
//					Category category = new Category();
//					category.setId(item.getProperty(ID));
//					category.setName(item.getProperty(NAME));
//
//					lCategory.add(category);
//				} catch (Exception e) {
//					return new ArrayList<Category>();
//				}
//			}
//		}
//
//		return lCategory;
//	}

	public void view() {
		System.out.println(ID + " = " + id);
		System.out.println(NAME + " = " + name);
	}

	public boolean isCategory(SoapObject data) {
		try {
			((SoapObject) data.getProperty("return")).getProperty("categories");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}