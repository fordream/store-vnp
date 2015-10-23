package com.cnc.maispreco.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.maispreco.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.soap.data.Product;

public class OfferListView extends LinearLayout {
	private List<Offers> lOffersStore = new ArrayList<Offers>();
	private LoadMoreView loadMoreView;
	private LinearLayout llContentScroll;
	private boolean isGroup;
	private int page = 1;
	private Product product;

	public OfferListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();

	}

	public OfferListView(Context context) {
		super(context);
		config();
	}

	private void config() {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) this.getContext().getSystemService(service);
		li.inflate(R.layout.offerlistview, this, true);

		llContentScroll = (LinearLayout) findViewById(R.id.llContentScroll);

		loadMoreView = new LoadMoreView(getContext());
		loadMoreView.setType(LoadMoreView.TYPE_OFFER);
		loadMoreView.setData("0", "0");
		loadMoreView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int childCount = llContentScroll.getChildCount();
				if (childCount > 0
						& llContentScroll.getChildAt(childCount - 1) == loadMoreView) {
					childCount--;
				}

				if (childCount < lOffersStore.size()) {
					page++;
				}

				notifyDataSetChanged();
			}
		});

	}

	public void addListOffer(List<Offers> lOffers) {
		lOffersStore.addAll(lOffers);
		notifyDataSetChanged();

	}

	private void notifyDataSetChanged() {
		Runnable runnable = new Runnable() {
			public void run() {
				llContentScroll.removeView(loadMoreView);
				int index = llContentScroll.getChildCount();

				for (int i = index; i < page * ConfigurationView.page; i++) {
					if (i < lOffersStore.size()) {
						Offers offers = lOffersStore.get(i);
						OfferView child = new OfferView(getContext());
						child.setPrice(offers.get(Offers.PRICE));
						if (isGroup) {
							child.setOfferInformation(offers
									.get(Offers.OFFERNAME));
							child.setUrl(offers.get(Offers.OFFERIMAGE));
						} else {
							child.setOfferInformation(offers
									.get(Offers.NAMESITE));
							child.setUrl(offers.get(Offers.URLLOGOSITE));
						}

						child.setOffers(offers, product);
						llContentScroll.addView(child);
					}
				}

				if (llContentScroll.getChildCount() < lOffersStore.size()) {
					loadMoreView.setData(llContentScroll.getChildCount() + "",
							lOffersStore.size() + "");
					llContentScroll.addView(loadMoreView);
				}

			}
		};
		post(runnable);
	}

	public void setProduct(Product product2) {
		product = product2;
		for (int i = 0; i < llContentScroll.getChildCount(); i++) {
			if (llContentScroll.getChildAt(i) instanceof OfferView) {
				OfferView new_name = (OfferView) llContentScroll.getChildAt(i);
				new_name.setOffers(lOffersStore.get(i), product);
			}
		}
	}
}
