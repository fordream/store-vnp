package com.cnc.maispreco.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.database.DBAdapterPage;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cnc.maispreco.soap.data.Locality;

public class LocalityView extends LinearLayout {
	public static Locality itemChangle = null;
	public static Boolean isChangle = false;
	private LinearLayout llLocality_locality;
	private ArrayList<LocalityItemView> alViewLocalityItem = new ArrayList<LocalityItemView>();
	private List<Locality> localities = new ArrayList<Locality>();
	public static boolean checked = false;
	public List<Locality> getLocalities() {
		return localities;
	}

	public void setLocalities(List<Locality> localities,
			OnClickListener onClickListener) {
		this.localities.clear();
		for (int i = 0; i < localities.size(); i++) {
			this.localities.add(localities.get(i));
		}

		for (int i = 0; i < this.localities.size(); i++) {
			LocalityItemView view = new LocalityItemView(getContext(),
					this.localities.get(i));
			if (i == 0) {
				view.setBackgroundResource(R.drawable.bgconfiguration1indicator);
			} else if (i == this.localities.size() - 1) {
				view.setBackgroundResource(R.drawable.bgconfiguration3indicator);
			} else {
				view.setBackgroundResource(R.drawable.bgconfiguration2indicator);
			}

			Locality locality = localities.get(i);

			view.check("true".equals(locality.get(Locality.DEFAULTLOCALE)));

			view.setOnClickListener(onClickListener);
			this.llLocality_locality.addView(view);
		}
	}

	public void setLocalities(List<Locality> localities) {
		this.localities.clear();
		for (int i = 0; i < localities.size(); i++) {
			this.localities.add(localities.get(i));
		}

		for (int i = 0; i < this.localities.size(); i++) {
			LocalityItemView view = new LocalityItemView(getContext(),
					this.localities.get(i));
			if (i == 0) {
				view.setBackgroundResource(R.drawable.bgconfiguration1indicator);
			} else if (i == this.localities.size() - 1) {
				view.setBackgroundResource(R.drawable.bgconfiguration3indicator);
			} else {
				view.setBackgroundResource(R.drawable.bgconfiguration2indicator);
			}
			view.setOnClickListener(new OnClicklistenerLocality(i));
			view.setId(i);
			this.llLocality_locality.addView(view);
			Locality locality = localities.get(i);
			view.check(Common.LOCALID.equals(locality.get(Locality.ID)));
			alViewLocalityItem.add(view);
		}
	}

	public LocalityView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();

	}

	public LocalityView(Context context) {
		super(context);
		config();
		llLocality_locality = (LinearLayout) findViewById(R.id.llLocality_locality);
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.localityscreen, this, true);
	}

	private class OnClicklistenerLocality implements OnClickListener {

		public OnClicklistenerLocality(int id) {
			super();
		}

		public void onClick(View v) {
			isChangle = true;
			itemChangle = null;
			for (int i = 0; i < alViewLocalityItem.size(); i++) {
				if (v.getId() == alViewLocalityItem.get(i).getId()) {
					alViewLocalityItem.get(i).check(true);
					itemChangle = localities.get(i);
					Common.LOCALID = itemChangle.get(Locality.ID);
					new DBAdapterPage(getContext()).updateLocation(Common.LOCALID );
					checked = true;
				} else {
					alViewLocalityItem.get(i).check(false);
				}
			}
			
			((MaisprecoScreen)getContext()).onChangleLocation();
		}
	}
}
