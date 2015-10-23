package com.cnc.maispreco.common;

public class StringCommon {
	public static final String PRODUCT_NAME = "Product Name";
	public static final String CATAGORY_NAME = "Catagory Name";
	public static final String LABORATORY_NAME = "Laboratory Name";
	public static final String PRICE_FROM = "Price From";
	public static final String PRICE_TO = "Price To";
	public static final String PRODUCT_IMAGE = "Product Image";
	public static final String TOP_SEARCH = "Top Search";
	public static final String TO_SEARCH = "To Search";
	public static final String CATAGORIES = "Categories";
	public static final String CANCELAR = "Canelar";
	public static final String SCAN_A_BAR_CODE = "Scan a BarCode";
	public static final String RESULT_OF_SEARCH = "Result of search {0}";
	public static final String VOLTAR = "Voltar";
	public static final String OFFERS = "Offers";
	public static final String SIMILARS_PRODUCTS = "Similars products";
	public static final String GENERIC_PRODUCTS = "Generic products";
	public static final String PRODUCTS = "products";
	public static final String NUM_OF_OFFERS = "{0} offers";
	public static final String NUM_OF_PRODUCTS = "{0} products";
	public static final String BEST_PRICE = "Best Price";
	public static final String NEAR_BY = "Near by";
	public static final String OFFER_INFORMATION = "Offer Information";
	public static final String STORE = "Store";
	public static final String STORE_NAME = "Store Name";
	public static final String STORE_INFOR = "Store Info";
	public static final String ADDRESS = "Address";
	public static final String PHONE = "Phone";
	public static final String CALL = "Call";
	public static final String CALL_INFORMATION = "Call information";
	public static final String Cancel = "Cancel";

	public static final String TOKEN = "token";

	public static final String URL_SEARCH = "http://bot4.maisprecobot.com:9009/MaisPrecoWS/services/MaisPreco?wsdl?token={0}&&searchType={1}";

	public static CharSequence createPriceFromTo(long priceFrom, long priceTo) {
		return "$" + priceFrom + " - $" + priceTo;
	}

	public static boolean isNumber(String input) {
		String PATTERN = "0123456789";
		if (input == null) {
			return false;
		}

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if (!PATTERN.contains("" + ch)) {
				return false;
			}
		}
		return true;
	}

	public static String format(String value) {
		if (value != null) {
			if (value.equals("anyType{}")) {
				return "";
			}
		}
		return value;
	}

	public static String cut(String input, int count) {
		if (input != null) {
			if (input.length() > count) {
				input = input.substring(0, count) + "...";
			}
		}

		return input;
	}

	public static String convertObjectToString(Object object) {
		if (object != null) {
			return object.toString();
		}
		return null;
	}
}