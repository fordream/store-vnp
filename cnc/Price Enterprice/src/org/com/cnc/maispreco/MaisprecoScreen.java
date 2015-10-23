package org.com.cnc.maispreco;

import java.util.ArrayList;
import java.util.List;
import org.com.cnc.maispreco.common.Common;
import org.com.cnc.maispreco.filed.FiledMaisprecoScreen;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cnc.maispreco.asyn.AddressAsyn;
import com.cnc.maispreco.asyn.Category1Asyn;
import com.cnc.maispreco.asyn.CategoryAsyn;
import com.cnc.maispreco.asyn.HomeAsyn;
import com.cnc.maispreco.asyn.OfferBarcodeAsyn;
import com.cnc.maispreco.asyn.OfferFirstBestpriceAsyn;
import com.cnc.maispreco.asyn.SearchAsyn;
import com.cnc.maispreco.asyn.SimilarAsyn;
import com.cnc.maispreco.asyn.Store1Asyn;
import com.cnc.maispreco.asyn.TrackerAsyn;
import com.cnc.maispreco.common.CommonView;
import com.cnc.maispreco.parcelable.ProductParacel;
import com.cnc.maispreco.soap.data.Locality;
import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.soap.data.Store;
import com.cnc.maispreco.views.AddressViewControl;
import com.cnc.maispreco.views.CategoryViewControl;
import com.cnc.maispreco.views.ConfigurationView;
import com.cnc.maispreco.views.GenericProductsViewControl;
import com.cnc.maispreco.views.HomeViewControl;
import com.cnc.maispreco.views.LocalityView;
import com.cnc.maispreco.views.OffersViewControl;
import com.cnc.maispreco.views.ProductViewControl;
import com.cnc.maispreco.views.SearchViewControl;
import com.cnc.maispreco.views.SimilarProductsViewControl;
import com.cnc.maispreco.views.StoreViewControl;
import com.cnc.maispreco.views.TabView;
import com.cnc.maispreco.views.WebViewController;

public class MaisprecoScreen extends Activity {
	// private int tabCurent = 0;
	private int tabOld = 0;
	private FiledMaisprecoScreen filedMaisprecoScreen;
	public static final int ADDRESS_CODE = 1;
	public static final int REQUEST_SCAN = 0;
	public static List<View> lViewHome = new ArrayList<View>();
	public static List<View> lViewCategory = new ArrayList<View>();
	private List<View> lViewLocality = new ArrayList<View>();
	private List<View> lViewConfiguration = new ArrayList<View>();
	public static List<Locality> lLocality = new ArrayList<Locality>();
	private ImageView imgSearch;
	// tab
	private int curentTab = 0;
	private TabView tabView[] = new TabView[4];

	// header
	private TextView tVStatus;
	private EditText etSearch;
	private ImageView imgBanner;
	private LinearLayout llContent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		filedMaisprecoScreen = new FiledMaisprecoScreen(this);
		lViewHome.clear();
		lViewCategory.clear();
		lViewConfiguration.clear();
		lViewLocality.clear();
		configHeader();
		configTabView();

		configSearch();
		llContent = (LinearLayout) findViewById(R.id.llContent);
		filedMaisprecoScreen.viewHeader(false, true, true, true, true);
		loadHome();

		Display display = getWindowManager().getDefaultDisplay();
		CommonView.HEIGHT = display.getHeight() / 8;
		CommonView.HEIGHT = CommonView.HEIGHT > 110 ? 110 : CommonView.HEIGHT;

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onStop() {
		if (filedMaisprecoScreen.isStop()) {
			finish();
		}
		super.onStop();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBack();
			return true;
		}

		if (keyCode == KeyEvent.KEYCODE_POWER) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void configHeader() {
		tVStatus = (TextView) findViewById(R.id.tVStatus);
		imgBanner = (ImageView) findViewById(R.id.imgBanner);
		OnClickListener clickListener = new OnClickListener() {
			public void onClick(View v) {
				if (LocalityView.itemChangle != null) {
					lViewHome.clear();
					lViewCategory.clear();
					LocalityView.itemChangle = null;
				}

				curentTab = 0;
				tabView[0].setBgSelected(curentTab == 0);
				tabView[1].setBgSelected(curentTab == 1);
				tabView[2].setBgSelected(curentTab == 2);
				tabView[3].setBgSelected(curentTab == 3);
				lViewHome.clear();
				loadHome();
			}
		};

		imgBanner.setOnClickListener(clickListener);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		filedMaisprecoScreen.setStop(true);
		if (requestCode == REQUEST_SCAN && resultCode == RESULT_OK) {
			if (resultCode == RESULT_OK) {
				try {
					String contents = data.getStringExtra("key");
					if ("".equals(contents) || contents == null) {
						return;
					}
					OffersViewControl offersViewControl = new OffersViewControl(
							MaisprecoScreen.this);
					addlView(offersViewControl);
					new OfferBarcodeAsyn(MaisprecoScreen.this, getResources(),
							null, offersViewControl, 1, false, null)
							.execute(contents);
				} catch (Exception e) {
					addlView(null);
					return;
				}
			} else if (resultCode == RESULT_CANCELED) {
				addlView(null);
			} else {
				addlView(null);
			}
		} else if (requestCode == ADDRESS_CODE) {
			if (resultCode == RESULT_OK) {
				int type = data.getExtras().getInt(AddressChoiseScreen.ARG0);
				String search = data.getExtras().getString(
						AddressChoiseScreen.ARG1);

				AddressViewControl view = null;
				if (curentTab == 0) {
					if (lViewHome.get(lViewHome.size() - 1) instanceof AddressViewControl) {
						view = (AddressViewControl) lViewHome.get(lViewHome
								.size() - 1);
					}
				} else if (curentTab == 1) {
					if (lViewCategory.get(lViewCategory.size() - 1) instanceof AddressViewControl) {
						view = (AddressViewControl) lViewCategory
								.get(lViewCategory.size() - 1);
					}
				}

				if (view != null) {
					view.notifyDataSetChanged(type, search, false);
				}
			}
		}
	}

	private void loadHome() {
		etSearch.setText("");
		if (lViewHome.size() == 0) {
			HomeViewControl view = new HomeViewControl(this, null, null);
			this.addlView(view);
			new HomeAsyn(MaisprecoScreen.this, null, view).execute("");
		}

	}

	public List<View> getLisViews(int curentTab) {
		if (curentTab == 0) {
			return lViewHome;
		} else if (curentTab == 1) {
			return lViewCategory;
		} else if (curentTab == 2) {
			return lViewLocality;
		} else {
			return lViewConfiguration;
		}
	}

	public void addlView(View view) {
		if (view != null) {
			getLisViews(curentTab).add(view);
		} else {
			try {
				view = getLisViews(curentTab).get(
						getLisViews(curentTab).size() - 1);
			} catch (Exception e) {
				loadHome();
				return;
			}
		}

		Resources resources = getResources();
		if (curentTab == 0) {
			View _view = lViewHome.get(lViewHome.size() - 1);
			if (lViewHome.size() == 1) {
				tVStatus.setText(resources.getString(R.string.top_search));
				filedMaisprecoScreen.viewHeader(false, true, true, true, true);
			} else if (_view instanceof SearchViewControl) {
				tVStatus.setText(resources.getString(R.string.result_of_search)
						+ ((SearchViewControl) lViewHome.get(lViewHome.size() - 1))
								.get_strSearch());
				filedMaisprecoScreen.viewHeader(true, true, true, true, true);
			} else if (_view instanceof GenericProductsViewControl) {
				tVStatus.setText(resources
						.getString(R.string.generic_products_));
				filedMaisprecoScreen.viewHeader(true, true, true, true, true);
			} else if (_view instanceof SimilarProductsViewControl) {
				tVStatus.setText(resources.getString(R.string.similar_products));
				filedMaisprecoScreen.viewHeader(true, true, true, true, true);
			} else if (_view instanceof WebViewController) {
				filedMaisprecoScreen.viewHeader(true, true, true, false, false);
			} else {
				filedMaisprecoScreen.viewHeader(true, true, true, false, false);
			}
		} else if (curentTab == 1) {
			View _view = lViewCategory.get(lViewCategory.size() - 1);

			if (lViewCategory.size() == 1) {
				tVStatus.setText(resources.getString(R.string.category));
				filedMaisprecoScreen.viewHeader(false, true, true, true, true);
			} else {
				if (lViewCategory.get(lViewCategory.size() - 1) instanceof CategoryViewControl) {
					tVStatus.setText(resources.getString(R.string.category));
					filedMaisprecoScreen.viewHeader(true, true, true, true,
							true);
				} else if (lViewCategory.get(lViewCategory.size() - 1) instanceof SearchViewControl) {

					tVStatus.setText(resources
							.getString(R.string.result_of_search)
							+ ((SearchViewControl) lViewCategory
									.get(lViewCategory.size() - 1))
									.get_strSearch());
					filedMaisprecoScreen.viewHeader(true, true, true, true,
							true);
				} else if (lViewCategory.get(lViewCategory.size() - 1) instanceof SimilarProductsViewControl) {
					filedMaisprecoScreen.viewHeader(true, true, true, true,
							true);
					tVStatus.setText(resources
							.getString(R.string.similar_products));
				} else if (lViewCategory.get(lViewCategory.size() - 1) instanceof GenericProductsViewControl) {
					filedMaisprecoScreen.viewHeader(true, true, true, true,
							true);
					tVStatus.setText(resources
							.getString(R.string.generic_products_));
				} else if (_view instanceof HomeViewControl) {
					filedMaisprecoScreen.viewHeader(true, true, true, true,
							true);
				} else if (_view instanceof WebViewController) {
					filedMaisprecoScreen.viewHeader(true, true, true, false,
							false);
				} else {
					tVStatus.setText(resources.getString(R.string.category));
					filedMaisprecoScreen.viewHeader(true, true, true, false,
							false);
				}
			}

		} else if (curentTab == 2) {
			filedMaisprecoScreen.viewHeader(false, true, false, false, false);
		} else if (curentTab == 3) {
			if (lViewConfiguration.size() > 1) {
				filedMaisprecoScreen
						.viewHeader(true, true, false, false, false);
			} else {
				filedMaisprecoScreen.viewHeader(false, true, false, false,
						false);
			}
		}

		if (view instanceof OffersViewControl) {
			((OffersViewControl) view).reload();
		}

		if (view instanceof GenericProductsViewControl) {
			((GenericProductsViewControl) view).reload();
		}
		if (view instanceof HomeViewControl) {
			((HomeViewControl) view).reload();
		}

		if (view instanceof ProductViewControl) {
			((ProductViewControl) view).reload();
		}

		if (view instanceof SearchViewControl) {
			((SearchViewControl) view).reload();
		}

		if (view instanceof SimilarProductsViewControl) {
			((SimilarProductsViewControl) view).reload();
		}

		viewContent(view);
	}

	private void viewContent(View view) {
		llContent.removeAllViews();
		int width = LayoutParams.WRAP_CONTENT;
		int height = LayoutParams.WRAP_CONTENT;
		LayoutParams layoutParams = new LayoutParams(width, height);
		llContent.addView(view, layoutParams);
	}

	private void configSearch() {
		imgSearch = (ImageView) findViewById(R.id.imgSearch);
		etSearch = (EditText) findViewById(R.id.etSearch);
		etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					InputMethodManager imm = (InputMethodManager) MaisprecoScreen.this
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
					performSearch();
					return true;
				}
				return false;
			}

			private void performSearch() {
				String strSearch = etSearch.getText().toString();
				addSearchView(strSearch);
				// 1111
				// SearchViewControl view = new SearchViewControl(
				// MaisprecoScreen.this, null);
				// view.set_strSearch(strSearch);
				// addlView(view);
				// SearchAsyn search = new SearchAsyn(MaisprecoScreen.this,
				// getResources(), null, view, 1, false);
				// search.execute(strSearch);
			}
		});

		etSearch.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (etSearch.getText().toString().equals("")) {
					imgSearch.setBackgroundResource(R.drawable.x);
				} else {
					imgSearch.setBackgroundResource(R.drawable.x_selected);
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {

			}
		});

	}

	public void onBack() {
		if (curentTab == 0) {
			if (lViewHome.size() > 1) {
				lViewHome.remove(lViewHome.size() - 1);
				/* New 1 */
				View view = getView();
				String localId = Common.getLocalId(view);
				if (localId != null && Common.LOCALID != null) {
					if (!localId.equals(Common.LOCALID)) {
						if (getLisViews(curentTab) == lViewCategory
								&& getLisViews(curentTab).size() == 1) {
							getLisViews(curentTab).clear();
							addFirstCategoryView();
						} else if (getLisViews(curentTab) == lViewHome
								&& getLisViews(curentTab).size() == 1) {
							getLisViews(curentTab).clear();
							loadHome();
						} else if (getView() instanceof WebViewController) {
							addlView(null);
						} else {
							Common.reload(this, getLisViews(curentTab));
						}
						return;
					}
				}
				/* End new1 */
				addlView(null);
			} else {
				finish();
			}
		} else if (curentTab == 1) {
			if (lViewCategory.size() > 1) {
				lViewCategory.remove(lViewCategory.size() - 1);
				/* New1 */
				View view = getView();
				String localId = Common.getLocalId(view);
				if (localId != null && Common.LOCALID != null) {
					if (!localId.equals(Common.LOCALID)) {
						if (getLisViews(curentTab) == lViewCategory
								&& getLisViews(curentTab).size() == 1) {
							getLisViews(curentTab).clear();
							addFirstCategoryView();
						} else if (getLisViews(curentTab) == lViewHome
								&& getLisViews(curentTab).size() == 1) {
							getLisViews(curentTab).clear();
							loadHome();
						} else if (getView() instanceof WebViewController) {
							addlView(null);
						} else {
							Common.reload(this, getLisViews(curentTab));
						}
						return;
					}
				}
				/* End new1 */

				addlView(null);
			} else {
				finish();
				// curentTab = 0;
				// tabView[0].setBgSelected(true);
				// tabView[1].setBgSelected(false);
				// addlView(null);
			}
		} else if (curentTab == 2) {
			// View view = getView(tabOld);
			// if (view != null) {
			// String localId = Common.LOCALID;
			// localId = Common.getLocalId(view);
			// if (localId != null && Common.LOCALID != null) {
			// if (!localId.equals(Common.LOCALID)) {
			// curentTab = tabOld;
			// // View view2 = getView();
			// tabView[0].setBgSelected(false);
			// tabView[1].setBgSelected(false);
			// tabView[2].setBgSelected(false);
			// tabView[3].setBgSelected(false);
			// tabView[curentTab].setBgSelected(true);
			//
			// if (getLisViews(curentTab) == lViewCategory
			// && getLisViews(curentTab).size() == 1) {
			// getLisViews(curentTab).clear();
			// addFirstCategoryView();
			// } else if (getLisViews(curentTab) == lViewHome
			// && getLisViews(curentTab).size() == 1) {
			// getLisViews(curentTab).clear();
			// loadHome();
			// } else if (getView() instanceof WebViewController) {
			// addlView(null);
			// } else {
			// Common.reload(this, getLisViews(curentTab));
			// }
			// return;
			// }
			// }
			// }
			// curentTab = 0;
			// tabView[0].setBgSelected(true);
			// tabView[2].setBgSelected(false);
			// if (LocalityView.checked) {
			// lViewHome.clear();
			// loadHome();
			// LocalityView.checked = false;
			// }
			//
			// addlView(null);
			finish();
		} else if (curentTab == 3) {
			if (lViewConfiguration.size() == 1) {
				finish();
				curentTab = 0;
			} else {
				lViewConfiguration.remove(lViewConfiguration.size() - 1);
				addlView(null);
			}
		}
	}

	private void configTabView() {
		tabView[0] = (TabView) findViewById(R.id.tabView1);
		tabView[1] = (TabView) findViewById(R.id.tabView2);
		tabView[2] = (TabView) findViewById(R.id.tabView3);
		tabView[3] = (TabView) findViewById(R.id.tabView4);

		tabView[0].set_Id(0);
		tabView[1].set_Id(1);
		tabView[2].set_Id(2);
		tabView[3].set_Id(3);

		tabView[0].setImgTab(R.drawable.menu_home);
		tabView[1].setImgTab(R.drawable.menu_categories);
		tabView[2].setImgTab(R.drawable.menu_locality);
		tabView[3].setImgTab(R.drawable.menu_configuration);

		tabView[0].setOnClickListener(new OnClickListenerTabView());
		tabView[1].setOnClickListener(new OnClickListenerTabView());
		tabView[2].setOnClickListener(new OnClickListenerTabView());
		tabView[3].setOnClickListener(new OnClickListenerTabView());

		tabView[0].setBgSelected(true);

	}

	private class OnClickListenerTabView implements OnClickListener {
		public void onClick(View v) {
			int _id = ((TabView) v).get_Id();

			if (_id == curentTab && (curentTab == 2 || curentTab == 3)) {
				return;
			}

			if (curentTab == 0 || curentTab == 1) {
				tabOld = curentTab;
			}

			curentTab = _id;

			if (curentTab == 1) {
				if (lViewCategory.size() == 0) {
					addFirstCategoryView();
				} else {
					CategoryViewControl view = (CategoryViewControl) lViewCategory
							.get(0);
					lViewCategory.clear();
					if (view.getLOCALID() != null && Common.LOCALID != null) {
						if (!view.getLOCALID().equals(Common.LOCALID)) {
							addFirstCategoryView();
							return;
						}
					}
					addlView(view);
				}

			} else if (curentTab == 0) {
				lViewHome.clear();
				loadHome();
			} else if (curentTab == 2) {
				new TrackerAsyn(MaisprecoScreen.this, "/localidades")
						.execute("");
				if (lViewLocality.size() == 0) {
					LocalityView view = new LocalityView(MaisprecoScreen.this);
					view.setLocalities(lLocality);

					addlView(view);
				} else {
					addlView(null);
				}
			} else if (curentTab == 3) {
				new TrackerAsyn(MaisprecoScreen.this, "/configuracao")
						.execute("");
				if (lViewConfiguration.size() == 0) {
					ConfigurationView view = new ConfigurationView(
							MaisprecoScreen.this);
					addlView(view);
				} else {
					addlView(null);
				}
			}

			tabView[0].setBgSelected(tabView[0].get_Id() == _id);
			tabView[1].setBgSelected(tabView[1].get_Id() == _id);
			tabView[2].setBgSelected(tabView[2].get_Id() == _id);
			tabView[3].setBgSelected(tabView[3].get_Id() == _id);

		}
	}

	public static void addLocality(List<Locality> alLocality) {
		lLocality.clear();
		lLocality.addAll(alLocality);
	}

	public void addFirstCategoryView() {
		CategoryViewControl view = new CategoryViewControl(this, null);
		addlView(view);
		new CategoryAsyn(MaisprecoScreen.this, getResources(), view, 1, false)
				.execute("");
	}

	public void addStoreView(Offers offers, Product product) {
		StoreViewControl view = new StoreViewControl(this);
		view.setProduct(product, offers);
		addlView(view);
		new Store1Asyn(MaisprecoScreen.this, offers, product, view, false)
				.execute("");
	}

	public void addAddressViewControl(Store store) {
		AddressViewControl view = new AddressViewControl(this);
		view.setStore(store);
		addlView(view);
		new AddressAsyn(this, store, view, false, new OnClickListenerAddress())
				.execute("");
	}

	private class OnClickListenerAddress implements OnClickListener {
		public void onClick(View v) {
			AddressViewControl view = null;
			if (curentTab == 0) {
				if (lViewHome.get(lViewHome.size() - 1) instanceof AddressViewControl) {
					view = (AddressViewControl) lViewHome
							.get(lViewHome.size() - 1);
				}
			} else if (curentTab == 1) {
				if (lViewCategory.get(lViewCategory.size() - 1) instanceof AddressViewControl) {
					view = (AddressViewControl) lViewCategory.get(lViewCategory
							.size() - 1);
				}
			}

			if (view != null) {
				int tab = AddressViewControl.STATE;
				if (v.getId() == view.getBtnEstado().getId()) {
					tab = AddressViewControl.STATE;
				} else if (v.getId() == view.getBtnCidade().getId()) {
					tab = AddressViewControl.CITY;
				} else if (v.getId() == view.getBtnBairro().getId()) {
					tab = AddressViewControl.NEIGHBORHOOD;
				}

				Bundle data = new Bundle();
				data.putInt(AddressChoiseScreen.ARG0, tab);
				data.putString(AddressChoiseScreen.ARG1,
						view.getText() == null ? "" : view.getText());
				AddressChoiseScreen.lAddresses = view.getAlAddressStore();
				if (AddressChoiseScreen.lAddresses.size() > 0) {
					Intent intent = new Intent(getBaseContext(),
							WheelFilterActivity.class);
					intent.putExtras(data);
					startActivityForResult(intent, ADDRESS_CODE);
				} else {
					Resources resources = getResources();
					resources
							.getString(R.string.Sorry_there_is_no_result_available);
					CommonView
							.makeText(
									MaisprecoScreen.this,
									resources
											.getString(R.string.Sorry_there_is_no_result_available));
					view.notifyDataSetChanged(tab, null, false);
				}
			}

		}
	}

	public View getView() {
		List<View> lViews = getLisViews(curentTab);
		return lViews.get(lViews.size() - 1);
	}

	public View getView(int tabOld) {
		List<View> lViews = getLisViews(tabOld);
		try {
			return lViews.get(lViews.size() - 1);
		} catch (Exception e) {
			return null;
		}
	}

	public void addOfferView(Product product) {
		OffersViewControl view = new OffersViewControl(this);
		view.setProduct(product);
		addlView(view);
		new OfferFirstBestpriceAsyn(this, product, 1, false, view).execute("");
	}

	public void addProductView(Product product) {
		ProductViewControl view = new ProductViewControl(this, product);
		view.addProduct(null);
		addlView(view);
	}

	public void addSimilarsProductView(Product product) {
		ProductParacel paracel = new ProductParacel();
		paracel.setMaisprecoScreen(MaisprecoScreen.this);
		paracel.setProduct(product);
		paracel.setHandler(null);
		paracel.setSearchType("6");
		SimilarProductsViewControl view = new SimilarProductsViewControl(
				MaisprecoScreen.this, product);
		addlView(view);
		new SimilarAsyn(paracel, this, getResources(), view, 1, false)
				.execute("");
	}

	public void addGenericProductsViewControl(Product product) {
		ProductParacel paracel = new ProductParacel();
		paracel.setMaisprecoScreen(MaisprecoScreen.this);
		paracel.setProduct(product);
		paracel.setHandler(null);
		paracel.setSearchType("5");
		GenericProductsViewControl view = new GenericProductsViewControl(
				MaisprecoScreen.this, product);
		addlView(view);
		new SimilarAsyn(paracel, this, getResources(), view, 1, false)
				.execute("");
	}

	public void addCategoryView(String id) {
		new Category1Asyn(this, null, null, getResources(), null, 1, false,
				null, new CategoryViewControl(this, id), new HomeViewControl(
						this, null, id)).execute(id);
	}

	public void addSearchView(String strSearch) {
		SearchViewControl view = new SearchViewControl(MaisprecoScreen.this,
				null);
		view.set_strSearch(strSearch);
		addlView(view);
		SearchAsyn search = new SearchAsyn(MaisprecoScreen.this,
				getResources(), null, view, 1, false);
		search.execute(strSearch);
	}

	public boolean onChangleLocation() {
		View view = getView(tabOld);
		if (view != null) {
			String localId = Common.LOCALID;
			localId = Common.getLocalId(view);
			if (localId != null && Common.LOCALID != null) {
				if (!localId.equals(Common.LOCALID)) {
					curentTab = tabOld;
					tabView[0].setBgSelected(false);
					tabView[1].setBgSelected(false);
					tabView[2].setBgSelected(false);
					tabView[3].setBgSelected(false);
					tabView[curentTab].setBgSelected(true);

					if (getLisViews(curentTab) == lViewCategory
							&& getLisViews(curentTab).size() == 1) {
						getLisViews(curentTab).clear();
						addFirstCategoryView();
					} else if (getLisViews(curentTab) == lViewHome
							&& getLisViews(curentTab).size() == 1) {
						getLisViews(curentTab).clear();
						loadHome();
					} else if (getView() instanceof WebViewController) {
						addlView(null);
					} else {
						Common.reload(this, getLisViews(curentTab));
					}
					return true;
				}
			}
		}
		return false;
	}

	public void setStop(boolean b) {
		filedMaisprecoScreen.setStop(b);
	}
}