package com.cnc.maispreco.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnc.maispreco.adpters.OffersApater;
import com.cnc.maispreco.asyn.OfferFirstAsyn;
import com.cnc.maispreco.common.StringCommon;
import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.soap.data.Product;

public class OffersViewControl extends LinearLayout {
	private Product product;
	private Context context;
	private ImageView imgProduct;
	private TextView tVProductName;
	private TextView tVLaboratoryName;
	private TextView tVPriceFromTo;
	private ListView lVOffers;
	private ArrayList<Offers> alOffer = new ArrayList<Offers>();
	private List<Offers> lOffersStore = new ArrayList<Offers>();
	private ArrayList<Offers> alOffer1 = new ArrayList<Offers>();
	private List<Offers> lOffersStore1 = new ArrayList<Offers>();
	private int curent = 0;
	private int curent1 = 0;
	private OffersApater adapter;
	private OffersApater adapter1;
	private RelativeLayout rlBestPrice;
	private RelativeLayout rlNearby;
	private int tab = 0;
	private LoadMoreView loadMoreView;
	private Bitmap bitmap;
	private int curentPage0 = 1, curentPage1 = 1;
	private String LOCALID = Common.LOCALID;
	private OfferListView offerListView1, offerListView2;

	public String getLOCALID() {
		return LOCALID;
	}

	public void setLOCALID(String lOCALID) {
		LOCALID = lOCALID;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		setImageProduct(null);
	}

	public OffersViewControl(Context context) {
		super(context);
		this.context = context;
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.offersview, this);
		curent = ConfigurationView.page;
		curent1 = ConfigurationView.page;
		get();
		// addReload();
	}

//	private void addReload() {
//		MyCount myCount = new MyCount(150000, 1000);
//		myCount.start();
//	}

//	private class MyCount extends CountDownTimer {
//
//		public MyCount(long millisInFuture, long countDownInterval) {
//			super(millisInFuture, countDownInterval);
//		}
//
//		public void onFinish() {
//			adapter.notifyDataSetChanged();
//			adapter1.notifyDataSetChanged();
//		}
//
//		public void onTick(long millisUntilFinished) {
//			adapter.notifyDataSetChanged();
//			adapter1.notifyDataSetChanged();
//		}
//	}

	private void get() {
		imgProduct = (ImageView) findViewById(R.id.imgProduct);
		tVProductName = (TextView) findViewById(R.id.tVProductName);
		tVLaboratoryName = (TextView) findViewById(R.id.tVLaboratoryName);
		tVPriceFromTo = (TextView) findViewById(R.id.tVPrice);

		lVOffers = (ListView) findViewById(R.id.lVOffers);
		loadMoreView = new LoadMoreView(context);
		loadMoreView.setType(LoadMoreView.TYPE_OFFER);
		lVOffers.addFooterView(loadMoreView);
		loadMoreView.setVisibility(View.GONE);

		adapter = new OffersApater(context, R.layout.item_offers, alOffer);
		adapter1 = new OffersApater(context, R.layout.item_offers, alOffer1);

		lVOffers.setAdapter(adapter);
		lVOffers.setOnItemClickListener(onItemClickListener);

		rlBestPrice = (RelativeLayout) findViewById(R.id.rlBestPrice);
		rlNearby = (RelativeLayout) findViewById(R.id.rlNearby);

		offerListView1 = (OfferListView) findViewById(R.id.offerListView1);
		offerListView2 = (OfferListView) findViewById(R.id.offerListView2);
		offerListView2.setVisibility(GONE);
		rlBestPrice.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//
				rlBestPrice
						.setBackgroundResource(R.drawable.offer_bgstatus_selected);
				rlNearby.setBackgroundResource(R.drawable.offer_bgstatus);
//				lVOffers.setAdapter(adapter);
//				if (alOffer.size() < lOffersStore.size()) {
//					// curentPage0++;
//				}
//				tab = 0;
//				notifyDataSetChangedBesprice();
				offerListView1.setVisibility(VISIBLE);
				offerListView2.setVisibility(GONE);
			}
		});

		rlNearby.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				offerListView1.setVisibility(GONE);
				offerListView2.setVisibility(VISIBLE);
				
				rlBestPrice.setBackgroundResource(R.drawable.offer_bgstatus);
				rlNearby.setBackgroundResource(R.drawable.offer_bgstatus_selected);
//				tab = 1;
//				lVOffers.setAdapter(adapter1);
//				adapter1.notifyDataSetChanged();
//				loadMoreView.setVisibility(GONE);
				if (first) {
					first = false;
					try {
						if (!"".endsWith(product.get(Product.ID))
								&& product.get(Product.ID) != null)
							new OfferFirstAsyn((MaisprecoScreen) context,
									product, 1, false, OffersViewControl.this)
									.execute("");
					} catch (Exception e) {
					}
					return;
				}

//				if (alOffer1.size() < lOffersStore1.size()) {
//					// curentPage1++;
//				}
//				lVOffers.setAdapter(adapter1);
//				notifyDataSetChangedNearyBy();
			}
		});
	}

	private boolean first = true;

	// public void addOffers(List<Offers> lOffer, List<Offers> lOffer1) {
	// if (lOffer != null) {
	// lOffersStore.addAll(lOffer);
	// }
	// offerListView1.addListOffer(lOffer);
	//
	// if (tab == 0) {
	// loadMoreView.post(new Runnable() {
	// public void run() {
	// loadMoreView.setData(alOffer.size() + "",
	// lOffersStore.size() + "");
	// if (alOffer.size() < lOffersStore.size()) {
	// loadMoreView.setVisibility(VISIBLE);
	// }
	// }
	// });
	// }
	// }

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg1 instanceof LoadMoreView) {
				if (tab == 0) {
					curentPage0++;
					notifyDataSetChangedBesprice();
				} else {
					curentPage1++;
					notifyDataSetChangedNearyBy();
				}
				// notifyDataSetChanged();
				return;
			} else {
				Offers offers = getAlOffer().get(arg2);
				product = getProduct();
				MaisprecoScreen maisprecoScreen = (MaisprecoScreen) getContext();
				maisprecoScreen.addStoreView(offers, product);
			}
		}
	};

	public void reload() {
		try {
			imgProduct.setImageBitmap(bitmap);
			imgProduct.invalidate();
			tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
					40));
			tVLaboratoryName.setText(product.get(Product.LAB));

			String minPrice = product.get(Product.MINPRICE);
			String maxPrice = product.get(Product.MAXPRICE);
			String text = Common.convertPrice(minPrice, maxPrice);
			tVPriceFromTo.setText(text);

			adapter.notifyDataSetChanged();
			adapter1.notifyDataSetChanged();
		} catch (Exception e) {
		}
	}

	public void notifyDataSetChanged(boolean b) {
		if (tab == 0) {
			if (curent != ConfigurationView.page) {
				alOffer.clear();
				curent = ConfigurationView.page;
			}
			int index = alOffer.size();

			alOffer.clear();
			for (int i = 0; i < index + ConfigurationView.page; i++) {
				if (i < lOffersStore.size()) {
					alOffer.add(lOffersStore.get(i));
				}
			}

			if (alOffer.size() < lOffersStore.size()) {
				loadMoreView.setData(alOffer.size() + "", lOffersStore.size()
						+ "");
				loadMoreView.setVisibility(VISIBLE);
			} else {
				loadMoreView.setVisibility(GONE);
			}

			adapter.notifyDataSetChanged();

			tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
					40));
			tVLaboratoryName.setText(product.get(Product.LAB));

			String minPrice = product.get(Product.MINPRICE);
			String maxPrice = product.get(Product.MAXPRICE);
			String text = Common.convertPrice(minPrice, maxPrice);
			tVPriceFromTo.setText(text);
		} else {
			if (curent1 != ConfigurationView.page) {
				alOffer1.clear();
				curent1 = ConfigurationView.page;
			}
			int index = alOffer1.size();

			alOffer1.clear();
			for (int i = 0; i < index + ConfigurationView.page; i++) {
				if (i < lOffersStore1.size()) {
					alOffer1.add(lOffersStore1.get(i));
				}
			}

			if (alOffer1.size() < lOffersStore1.size()) {
				loadMoreView.setData(alOffer1.size() + "", lOffersStore1.size()
						+ "");
				loadMoreView.setVisibility(VISIBLE);
			} else {
				loadMoreView.setVisibility(GONE);
			}

			adapter1.notifyDataSetChanged();
			tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
					40));
			tVLaboratoryName.setText(product.get(Product.LAB));
			String minPrice = product.get(Product.MINPRICE);
			String maxPrice = product.get(Product.MAXPRICE);
			String text = Common.convertPrice(minPrice, maxPrice);
			tVPriceFromTo.setText(text);
		}
	}

	// public void notifyDataSetChanged() {
	// if (tab == 0) {
	// if (curent != ConfigurationView.page) {
	// alOffer.clear();
	// curent = ConfigurationView.page;
	// }
	// int index = alOffer.size();
	// alOffer.clear();
	// for (int i = 0; i < index + ConfigurationView.page; i++) {
	// if (i < lOffersStore.size()) {
	// alOffer.add(lOffersStore.get(i));
	// }
	// }
	//
	// if (alOffer.size() < lOffersStore.size()) {
	// loadMoreView.setData(alOffer.size() + "", lOffersStore.size()
	// + "");
	// loadMoreView.setVisibility(VISIBLE);
	// } else {
	// loadMoreView.setVisibility(GONE);
	// }
	//
	// adapter.notifyDataSetChanged();
	//
	// // imgProduct.setBackgroundDrawable(product.getDrawable());
	// tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
	// 40));
	// tVLaboratoryName.setText(product.get(Product.LAB));
	// String minPrice = product.get(Product.MINPRICE);
	// String maxPrice = product.get(Product.MAXPRICE);
	// String text = Common.convertPrice(minPrice, maxPrice);
	// tVPriceFromTo.setText(text);
	// } else {
	// if (curent1 != ConfigurationView.page) {
	// alOffer1.clear();
	// curent1 = ConfigurationView.page;
	// }
	//
	// int index = alOffer1.size();
	//
	// alOffer1.clear();
	// for (int i = 0; i < index + ConfigurationView.page; i++) {
	// if (i < lOffersStore1.size()) {
	// alOffer1.add(lOffersStore1.get(i));
	// }
	// }
	//
	// if (alOffer1.size() < lOffersStore1.size()) {
	// loadMoreView.setData(alOffer1.size() + "", lOffersStore1.size()
	// + "");
	// loadMoreView.setVisibility(VISIBLE);
	// } else {
	// loadMoreView.setVisibility(GONE);
	// }
	//
	// adapter1.notifyDataSetChanged();
	//
	// // imgProduct.setBackgroundDrawable(product.getDrawable());
	// tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
	// 40));
	// tVLaboratoryName.setText(product.get(Product.LAB));
	// String minPrice = product.get(Product.MINPRICE);
	// String maxPrice = product.get(Product.MAXPRICE);
	// String text = Common.convertPrice(minPrice, maxPrice);
	// tVPriceFromTo.setText(text);
	// }
	// }

	// public void notifyDataSetChanged(int tab, boolean b) {
	// loadMoreView.setVisibility(GONE);
	// if (this.tab == 0) {
	// if (curent != ConfigurationView.page) {
	// alOffer.clear();
	// curent = ConfigurationView.page;
	// }
	// int index = alOffer.size();
	//
	// alOffer.clear();
	// for (int i = 0; i < index; i++) {
	// if (i < lOffersStore.size()) {
	// alOffer.add(lOffersStore.get(i));
	// }
	// }
	//
	// if (alOffer.size() < lOffersStore.size()) {
	// loadMoreView.setData(alOffer.size() + "", lOffersStore.size()
	// + "");
	// loadMoreView.setVisibility(VISIBLE);
	// } else {
	// loadMoreView.setVisibility(GONE);
	// }
	//
	// adapter.notifyDataSetChanged();
	//
	// tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
	// 40));
	// tVLaboratoryName.setText(product.get(Product.LAB));
	// String minPrice = product.get(Product.MINPRICE);
	// String maxPrice = product.get(Product.MAXPRICE);
	// String text = Common.convertPrice(minPrice, maxPrice);
	// tVPriceFromTo.setText(text);
	// } else {
	// if (curent1 != ConfigurationView.page) {
	// alOffer1.clear();
	// curent1 = ConfigurationView.page;
	// }
	//
	// int index = alOffer1.size();
	//
	// if (index == 0) {
	// if (lOffersStore1.size() > curent1) {
	// index = curent1;
	// } else {
	// index = lOffersStore1.size();
	// }
	// }
	//
	// alOffer1.clear();
	// for (int i = 0; i < index; i++) {
	// if (i < lOffersStore1.size()) {
	// alOffer1.add(lOffersStore1.get(i));
	// }
	// }
	//
	// if (alOffer1.size() < lOffersStore1.size()) {
	// loadMoreView.setData(alOffer1.size() + "", lOffersStore1.size()
	// + "");
	// loadMoreView.setVisibility(VISIBLE);
	// } else {
	// loadMoreView.setVisibility(GONE);
	// }
	//
	// adapter1.notifyDataSetChanged();
	//
	// tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
	// 40));
	// tVLaboratoryName.setText(product.get(Product.LAB));
	// String minPrice = product.get(Product.MINPRICE);
	// String maxPrice = product.get(Product.MAXPRICE);
	// String text = Common.convertPrice(minPrice, maxPrice);
	// tVPriceFromTo.setText(text);
	// }
	//
	// }

	public void notifyDataSetChangedNearyBy() {
		if (tab == 0) {
			return;
		}
		loadMoreView.setVisibility(GONE);
		alOffer1.clear();
		for (int i = 0; i < curentPage1 * ConfigurationView.page; i++) {
			if (i < lOffersStore1.size()) {
				alOffer1.add(lOffersStore1.get(i));
			}
		}

		if (alOffer1.size() < lOffersStore1.size()) {
			loadMoreView.setData(alOffer1.size() + "", lOffersStore1.size()
					+ "");
			loadMoreView.setVisibility(VISIBLE);
		} else {
			loadMoreView.setVisibility(GONE);
		}
		adapter1.setGroupEnable("true".equals(product.get(Product.GROUP)));
		adapter1.notifyDataSetChanged();
	}

	public void notifyDataSetChangedBesprice() {
		if (tab == 1) {
			return;
		}
		loadMoreView.setVisibility(GONE);
		alOffer.clear();
		for (int i = 0; i < curentPage0 * ConfigurationView.page; i++) {
			if (i < lOffersStore.size()) {
				alOffer.add(lOffersStore.get(i));
			}
		}

		if (alOffer.size() < lOffersStore.size()) {
			loadMoreView.setData(alOffer.size() + "", lOffersStore.size() + "");
			loadMoreView.setVisibility(VISIBLE);
		} else {
			loadMoreView.setVisibility(GONE);
		}
		adapter.setGroupEnable("true".equals(product.get(Product.GROUP)));
		adapter.notifyDataSetChanged();

	}

	// public void notifyDataSetChanged(int tab) {
	// loadMoreView.setVisibility(GONE);
	// if (this.tab == tab) {
	// if (curent != ConfigurationView.page) {
	// alOffer.clear();
	// curent = ConfigurationView.page;
	// }
	// int index = alOffer.size();
	//
	// alOffer.clear();
	// for (int i = 0; i < index + ConfigurationView.page; i++) {
	// if (i < lOffersStore.size()) {
	// alOffer.add(lOffersStore.get(i));
	// }
	// }
	//
	// if (alOffer.size() < lOffersStore.size()) {
	// loadMoreView.setData(alOffer.size() + "", lOffersStore.size()
	// + "");
	// loadMoreView.setVisibility(VISIBLE);
	// } else {
	// loadMoreView.setVisibility(GONE);
	// }
	//
	// adapter.notifyDataSetChanged();
	// tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
	// 40));
	// tVLaboratoryName.setText(product.get(Product.LAB));
	// String minPrice = product.get(Product.MINPRICE);
	// String maxPrice = product.get(Product.MAXPRICE);
	// String text = Common.convertPrice(minPrice, maxPrice);
	// tVPriceFromTo.setText(text);
	// } else {
	// if (curent1 != ConfigurationView.page) {
	// alOffer1.clear();
	// curent1 = ConfigurationView.page;
	// }
	//
	// int index = alOffer1.size();
	//
	// alOffer1.clear();
	// for (int i = 0; i < index + ConfigurationView.page; i++) {
	// if (i < lOffersStore1.size()) {
	// alOffer1.add(lOffersStore1.get(i));
	// }
	// }
	//
	// if (alOffer1.size() < lOffersStore1.size()) {
	// loadMoreView.setData(alOffer1.size() + "", lOffersStore1.size()
	// + "");
	// loadMoreView.setVisibility(VISIBLE);
	// } else {
	// loadMoreView.setVisibility(GONE);
	// }
	//
	// adapter1.notifyDataSetChanged();
	// tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
	// 40));
	// tVLaboratoryName.setText(product.get(Product.LAB));
	// String minPrice = product.get(Product.MINPRICE);
	// String maxPrice = product.get(Product.MAXPRICE);
	// String text = Common.convertPrice(minPrice, maxPrice);
	// tVPriceFromTo.setText(text);
	// }
	// }

	public ImageView getImgProduct() {
		return imgProduct;
	}

	public void setImgProduct(ImageView imgProduct) {
		this.imgProduct = imgProduct;
	}

	public TextView gettVProductName() {
		return tVProductName;
	}

	public void settVProductName(TextView tVProductName) {
		this.tVProductName = tVProductName;

	}

	public TextView gettVLaboratoryName() {
		return tVLaboratoryName;
	}

	public void settVLaboratoryName(TextView tVLaboratoryName) {
		this.tVLaboratoryName = tVLaboratoryName;
	}

	public TextView gettVPriceFromTo() {
		return tVPriceFromTo;
	}

	public void settVPriceFromTo(TextView tVPriceFromTo) {
		this.tVPriceFromTo = tVPriceFromTo;
	}

	public ArrayList<Offers> getAlOffer() {
		if (tab == 0)
			return alOffer;
		else
			return alOffer1;
	}

	public void setAlOffer(ArrayList<Offers> alOffer) {
		this.alOffer = alOffer;
	}

	public void addList(List<Offers> list) {
		offerListView2.addListOffer(list);
		offerListView2.setProduct(product);
		offerListView1.setProduct(product);
		
//		lOffersStore1.addAll(list);
//		if (tab == 1) {
//			loadMoreView.post(new Runnable() {
//				public void run() {
//					loadMoreView.setData(alOffer1.size() + "",
//							lOffersStore1.size() + "");
//					if (alOffer1.size() < lOffersStore1.size()) {
//						loadMoreView.setVisibility(VISIBLE);
//					}
//
//					if (curentPage1 * ConfigurationView.page > lOffersStore1
//							.size()) {
//						notifyDataSetChangedNearyBy();
//					}
//				}
//			});
//		}
	}

	public void setImageProduct(Drawable drawable) {
		if (drawable != null) {
			bitmap = ((BitmapDrawable) drawable).getBitmap();
		} else if (product.getDrawable() != null) {
			bitmap = ((BitmapDrawable) product.getDrawable()).getBitmap();
		}
		reload();
	}

	public void addOffers(List<Offers> loOffersResponses) {
		offerListView1.addListOffer(loOffersResponses);
		offerListView2.setProduct(product);
		offerListView1.setProduct(product);
//		lOffersStore.addAll(loOffersResponses);
//		if (tab == 0) {
//			loadMoreView.post(new Runnable() {
//				public void run() {
//					loadMoreView.setData(alOffer.size() + "",
//							lOffersStore.size() + "");
//					if (alOffer.size() < lOffersStore.size()) {
//						loadMoreView.setVisibility(VISIBLE);
//					}
//
//					if (curentPage0 * ConfigurationView.page > lOffersStore
//							.size()) {
//						notifyDataSetChangedBesprice();
//					}
//				}
//			});
//		}
	}
}