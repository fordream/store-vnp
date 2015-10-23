package org.com.cnc.maispreco.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import org.com.cnc.common.maispreco.request.LoginRequest;
import org.com.cnc.common.maispreco.response.LoginResponse;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cnc.maispreco.common.CheckStatusNetwork;
import com.cnc.maispreco.common.CommonView;
import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.soap.data.Store;
import com.cnc.maispreco.views.AddressViewControl;
import com.cnc.maispreco.views.CategoryViewControl;
import com.cnc.maispreco.views.CommonHomeViewControl;
import com.cnc.maispreco.views.GenericProductsViewControl;
import com.cnc.maispreco.views.HomeViewControl;
import com.cnc.maispreco.views.OffersViewControl;
import com.cnc.maispreco.views.ProductViewControl;
import com.cnc.maispreco.views.SearchViewControl;
import com.cnc.maispreco.views.SimilarProductsViewControl;
import com.cnc.maispreco.views.StoreViewControl;
import com.cnc.maispreco.views.WebViewController;

public class Common {
	public static String tooken = null;
	public static Object dataLocality = null;
	public static double latitude = 0d;
	public static double longitude = 0d;

	public static final String TYPE_SEARCH_LOCALITY = "1";
	public static final String TYPE_SEARCH_CATEGORY = "2";
	public static final String TYPE_SEARCH_SEARCH = "3";
	public static final String TYPE_SEARCH_OFFER = "4";
	public static final String TYPE_SEARCH_GENERIC = "5";
	public static final String TYPE_SEARCH_SIMILAR = "6";
	public static final String TYPE_SEARCH_HOME = "8";
	public static final String TYPE_SEARCH_PRODUCT = "9";
	public static final String TYPE_SEARCH_STORE = "10";
	public static final String TYPE_SEARCH_ADDRESS = "11";

	public static final int MESSAGE_WHAT_0 = 0;
	public static final int MESSAGE_WHAT_1 = 1;
	public static final int MESSAGE_WHAT_2 = 2;
	public static final int MESSAGE_WHAT_3 = 3;
	public static final int MESSAGE_WHAT_4 = 4;
	public static final int MESSAGE_WHAT_5 = 5;
	public static final int MESSAGE_WHAT_6 = 6;
	public static final int MESSAGE_WHAT_7 = 7;
	public static final int MESSAGE_WHAT_8 = 8;
	public static final int MESSAGE_WHAT_9 = 9;
	public static final int MESSAGE_WHAT_10 = 10;
	public static int TIMEOUT = 20000;
	public static String URL_PRODUCT = null;
	public static String URL_OFFER = null;
	public static String LOCALID = "";

	public static void hiddenKeyboard(Context context, EditText et) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}

	public static boolean isOnline(Context context) {
		Object service = context.getSystemService(Context.CONNECTIVITY_SERVICE);
		ConnectivityManager cm = (ConnectivityManager) service;
		if (!(CheckStatusNetwork.checkInternetConnection(cm) || CheckStatusNetwork.checkStatus3G(cm) || CheckStatusNetwork.checkStatusWifi(cm))) {
			Resources resources = context.getResources();
			resources.getString(R.string.network);
			String message = "Connection timeout. \n Please check your internet connection.";
			message = resources.getString(R.string.No_internet_connection_);
			CommonView.makeText(context, message);

			return false;
		}

		return true;
	}

	public static String getStringFromSoap(SoapObject soapObject, int index) {
		String result = "";
		try {
			result = ((SoapObject) soapObject).getProperty(index).toString();
			if (result.equals("anyType{}")) {
				result = "";
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String getStringFromSoap(SoapObject soapObject, String key) {
		String result = "";
		try {
			result = ((SoapObject) soapObject).getProperty(key).toString();
			if (result.equals("anyType{}")) {
				result = "";
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String convertToMD5(String toEnc) {
		try {
			MessageDigest mdEnc = MessageDigest.getInstance("MD5");
			mdEnc.update(toEnc.getBytes(), 0, toEnc.length());
			return new BigInteger(1, mdEnc.digest()).toString(16);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean moreThanOne(String string) {
		if (string != null) {
			try {
				return string.contains("$");
				// return Integer.parseInt(string) > 0;
			} catch (Exception e) {
			}
		}
		return false;
	}

	public static boolean havePrice(String string) {
		return string == null ? false : !"?".equals(string);
	}

	public static int convertToInt(String count) {
		try {
			return Integer.parseInt(count);
		} catch (Exception e) {
			return 0;
		}
	}

	public static void relogin() {
		LoginRequest loginRequest = new LoginRequest("" + Common.latitude, "" + Common.longitude);
		LoginResponse loginResponse = LoginResponse.getLoginResponse(loginRequest);
		if (loginResponse != null) {
			Common.tooken = loginResponse.getToken();
		}
	}

	public static String convertPrice(String minPrice, String maxPrice) {
		String text = minPrice + "~" + maxPrice;
		if (!havePrice(minPrice)) {
			text = maxPrice;
		}

		if (!havePrice(maxPrice)) {
			text = minPrice;
		}

		if (!havePrice(maxPrice) && !havePrice(minPrice)) {
			text = "";
		}
		return text;
	}

	public static void builder(Context context, String string) {
		Builder builder = new Builder(context);
		builder.setMessage(string + "");
		builder.setNegativeButton("YES", null);
		builder.show();
	}

	public static String getLocalId(View view) {
		if (view instanceof CategoryViewControl) {
			return ((CategoryViewControl) view).getLOCALID();
		} else if (view instanceof CommonHomeViewControl) {
			return ((CommonHomeViewControl) view).getLOCALID();
		} else if (view instanceof ProductViewControl) {
			return ((ProductViewControl) view).getLOCALID();
		} else if (view instanceof OffersViewControl) {
			return ((OffersViewControl) view).getLOCALID();
		} else if (view instanceof AddressViewControl) {
			return ((AddressViewControl) view).getLOCALID();
		} else if (view instanceof WebViewController) {
			return ((WebViewController) view).getLOCALID();
		} else if (view instanceof StoreViewControl) {
			return ((StoreViewControl) view).getLOCALID();
		}

		return null;
	}

	public static void reload(MaisprecoScreen maisprecoScreen, List<View> lisViews) {
		View view = lisViews.get(lisViews.size() - 1);
		lisViews.remove(lisViews.size() - 1);
		if (view instanceof CommonHomeViewControl) {
			// search
			if (view instanceof SearchViewControl) {
				String strSearch = ((SearchViewControl) view).getStrSearch();
				maisprecoScreen.addSearchView(strSearch);
			}
			// simi
			if (view instanceof SimilarProductsViewControl) {
				Product product = ((SimilarProductsViewControl) view).getProduct();
				maisprecoScreen.addSimilarsProductView(product);
			}
			// gender
			if (view instanceof GenericProductsViewControl) {
				Product product = ((GenericProductsViewControl) view).getProduct();
				maisprecoScreen.addSimilarsProductView(product);
			}

			if (view instanceof HomeViewControl) {
				String id = ((HomeViewControl) view).getId1();
				maisprecoScreen.addCategoryView(id);

			}
		} else if (view instanceof ProductViewControl) {
			maisprecoScreen.addProductView(((ProductViewControl) view).getProduct());
		} else if (view instanceof OffersViewControl) {
			Product product = ((OffersViewControl) view).getProduct();
			maisprecoScreen.addOfferView(product);
		} else if (view instanceof StoreViewControl) {
			Product product = ((StoreViewControl) view).getProduct();
			Offers offers = ((StoreViewControl) view).getOffers();
			maisprecoScreen.addStoreView(offers, product);
		} else if (view instanceof AddressViewControl) {
			Store store = ((AddressViewControl) view).getStore();
			maisprecoScreen.addAddressViewControl(store);
		} else if (view instanceof CategoryViewControl) {
			String id = ((CategoryViewControl) view).getId1();
			maisprecoScreen.addCategoryView(id);
		}
	}

	public static boolean isDebug(Context context) {
		return context.getResources().getBoolean(R.bool.debug);

	}

	public static boolean isV2(Context context) {
		return context.getResources().getBoolean(R.bool.v2);

	}

	public static boolean isSupportGPS(Context context) {
		LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		List<String> lAllProviders = manager.getAllProviders();
		for (int i = 0; i < lAllProviders.size(); i++) {
			if (LocationManager.GPS_PROVIDER.equals(lAllProviders.get(i))) {
				return true;
			}
		}
		return false;
	}
}
