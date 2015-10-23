package com.cnc.maispreco.adpters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.views.OfferView;

public class OffersApater extends ArrayAdapter<Offers> {
	private List<Offers> offerList = new ArrayList<Offers>();
	private boolean isGroupEnable = false;

	public OffersApater(Context context, int textViewResourceId,
			ArrayList<Offers> objects) {
		super(context, textViewResourceId, objects);
		offerList = objects;
	}

	public boolean isGroupEnable() {
		return isGroupEnable;
	}

	public void setGroupEnable(boolean isGroupEnable) {
		this.isGroupEnable = isGroupEnable;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View workView = convertView;

		if (workView == null) {
			workView = new OfferView(getContext());
		}

		Offers offer = offerList.get(position);

		if (offer != null) {
			TextView tVOfferInformation = ((OfferView) workView)
					.gettVOfferInformation();
			TextView tVOfferPrice = ((OfferView) workView).gettVOfferPrice();
			
			tVOfferPrice.setText(offer.get(Offers.PRICE));

			if (isGroupEnable) {
				String infor = offer.get(Offers.OFFERNAME);
				tVOfferInformation.setText(infor);
				
				
				((OfferView) workView).setNewName(offer.get(Offers.NAMESITE));
				if (offer.getDrawableGroup() != null ) {
					((OfferView) workView).setImage(offer.getDrawableGroup());
					((OfferView) workView).setUrl(offer.get(Offers.OFFERIMAGE));
				}
			} else {
				String infor = offer.get(Offers.NAMESITE);
				tVOfferInformation.setText(infor);
				if (offer.getDrawable() != null ) {
					((OfferView) workView).setImage(offer.getDrawable());
					((OfferView) workView).setUrl(offer.get(Offers.URLLOGOSITE));
				}
			}

		}

		return workView;
	}
}