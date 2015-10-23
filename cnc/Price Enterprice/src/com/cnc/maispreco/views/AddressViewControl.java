package com.cnc.maispreco.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.maispreco.AddressChoiseScreen;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.WheelFilterActivity;
import org.com.cnc.maispreco.common.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnc.maispreco.adpters.AddressApater;
import com.cnc.maispreco.soap.data.Address;
import com.cnc.maispreco.soap.data.Store;

public class AddressViewControl extends LinearLayout implements OnClickListener {

	public static final int STATE = 1;
	public static final int CITY = 2;
	public static final int NEIGHBORHOOD = 3;
	public static final int NONE = 4;
	private Store store;
	private Context context;
	private ImageView imgStore;
	private String text;
	private String textState;
	private String textCity;
	private String textNeighborhood;
	private ImageButton imgBtnWebsite;
	private ImageButton imgBtnAddress;
	private TextView tVCommen;
	private LinearLayout lVAddress;
	private ArrayList<Address> alAddress = new ArrayList<Address>();
	private ArrayList<Address> alAddressStore = new ArrayList<Address>();
	private AddressApater adapter;
	private Button btnEstado;
	private Button btnCidade;
	private Button btnBairro;
	private TextView tVStoreName;
	private TextView tVFind;
	private Button btnShowAll;
	private LinearLayout llImage;
	private String LOCALID = Common.LOCALID;

	public String getLOCALID() {
		return LOCALID;
	}

	public void setLOCALID(String lOCALID) {
		LOCALID = lOCALID;
	}

	public ArrayList<Address> getAlAddressStore() {
		return alAddressStore;
	}

	public void setAlAddressStore(ArrayList<Address> alAddressStore) {
		this.alAddressStore = alAddressStore;
		btnBairro.setVisibility(GONE);
		btnCidade.setVisibility(GONE);
		btnEstado.setVisibility(GONE);

		if (alAddressStore.size() >= 1) {
			List<String> cities = new ArrayList<String>();
			List<String> states = new ArrayList<String>();
			List<String> neighboods = new ArrayList<String>();
			for (int i = 0; i < alAddressStore.size(); i++) {
				String city = alAddressStore.get(i).get(Address.CITY);
				boolean check = false;
				for (int j = 0; j < cities.size(); j++) {
					if (cities.get(j).equals(city)) {
						check = true;
						break;
					}
				}

				if (!check && city != null) {
					cities.add(city);
				}

				city = alAddressStore.get(i).get(Address.NEIGHBORHOOD);
				check = false;
				for (int j = 0; j < neighboods.size(); j++) {
					if (neighboods.get(j).equals(city)) {
						check = true;
						break;
					}
				}

				if (!check && city != null) {
					neighboods.add(city);
				}

				city = alAddressStore.get(i).get(Address.STATE);
				check = false;
				for (int j = 0; j < states.size(); j++) {
					if (states.get(j).equals(city)) {
						check = true;
						break;
					}
				}

				if (!check && city != null) {
					states.add(city);
				}
			}

			if (cities.size() > 1) {
				btnCidade.setVisibility(VISIBLE);
			}

			if (neighboods.size() > 1) {
				btnBairro.setVisibility(VISIBLE);
			}

			if (states.size() > 1) {
				btnEstado.setVisibility(VISIBLE);
			}
		}

	}

	public AddressViewControl(Context context) {
		super(context);
		this.context = context;
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.addressview, this);
		get();
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	private void get() {
		tVFind = (TextView) findViewById(R.id.textView1);
		tVFind.setText("\n\n\n");
		btnShowAll = (Button) findViewById(R.id.button1);
		btnShowAll.setOnClickListener(this);
		// tVFind.setVisibility(GONE);
		btnShowAll.setVisibility(GONE);

		tVStoreName = (TextView) findViewById(R.id.tVStoreName);
		imgStore = (ImageView) findViewById(R.id.imgStore);

		imgBtnWebsite = (ImageButton) findViewById(R.id.imgBtnWebsite);

		imgBtnAddress = (ImageButton) findViewById(R.id.imgBtnAddress);
		tVCommen = (TextView) findViewById(R.id.tVCommen);

		lVAddress = (LinearLayout) findViewById(R.id.lVAddress);

		adapter = new AddressApater(context, alAddress);

		btnEstado = (Button) findViewById(R.id.btnEstado);

		btnCidade = (Button) findViewById(R.id.btnCidade);

		btnBairro = (Button) findViewById(R.id.btnBairro);
		llImage = (LinearLayout) findViewById(R.id.linearLayout7);
		btnEstado.setOnClickListener(clickListener);
		btnBairro.setOnClickListener(clickListener);
		btnCidade.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			int tab = NONE;
			String textCity = getTextCity();
			String textNeighborhood = getTextNeighborhood();
			String textState = getTextState();
			if (v == btnBairro) {
				tab = NEIGHBORHOOD;
				textNeighborhood = "";
				setText(getTextNeighborhood());
			} else if (v == btnCidade) {
				tab = CITY;
				textCity = "";
				textNeighborhood = "";
				setText(getTextCity());
			} else if (v == btnEstado) {
				tab = STATE;
				textState = "";
				textCity = "";
				textNeighborhood = "";
				setText(getTextState());
			}

			AddressChoiseScreen.lAddresses = alAddressStore;
			Intent intent = new Intent(getContext(), WheelFilterActivity.class);
			Bundle data = new Bundle();
			data.putInt(AddressChoiseScreen.ARG0, tab);
			data.putString(AddressChoiseScreen.ARG1, getText() == null ? ""
					: getText());
			data.putString(AddressChoiseScreen.ARG2, textCity);
			data.putString(AddressChoiseScreen.ARG3, textState);
			data.putString(AddressChoiseScreen.ARG4, textNeighborhood);
			intent.putExtras(data);
			((Activity) getContext()).startActivityForResult(intent,
					MaisprecoScreen.ADDRESS_CODE);
		}
	};

	private void choise(int i, String search) {
		text = search;
		btnEstado.setBackgroundResource(R.drawable.btn_indicator);
		btnCidade.setBackgroundResource(R.drawable.btn_indicator);
		btnBairro.setBackgroundResource(R.drawable.btn_indicator);
		if (i == STATE) {
			textState = search;
			btnEstado.setBackgroundResource(R.drawable.btn_indicator_selected);
			setTextCity("");
			setTextNeighborhood("");
		}
		if (i == CITY) {
			textCity = search;
			btnCidade.setBackgroundResource(R.drawable.btn_indicator_selected);
			if (!textState.equals("")) {
				btnEstado
						.setBackgroundResource(R.drawable.btn_indicator_selected);
			}
			setTextNeighborhood("");
		}
		if (i == NEIGHBORHOOD) {
			textNeighborhood = search;
			if (!textState.equals("")) {
				btnEstado
						.setBackgroundResource(R.drawable.btn_indicator_selected);
			}
			if (!textCity.equals("")) {
				btnCidade
						.setBackgroundResource(R.drawable.btn_indicator_selected);
			}

			btnBairro.setBackgroundResource(R.drawable.btn_indicator_selected);
		}
		if (i == NONE) {
			textCity = "";
			textNeighborhood = "";
			textState = "";

		}
		Resources res = getResources();
		String fill = res.getString(R.string.fill) + " ";

		String message = "";

		if (!textState.equals("")) {
			message += fill + res.getString(R.string.estado) + " : "
					+ textState + "\n";
		} else {
			message += "\n";
		}

		if (!textCity.equals("")) {
			message += fill + res.getString(R.string.cidade) + " : " + textCity
					+ "\n";
		} else {
			message += "\n";
		}
		if (!textNeighborhood.equals("")) {
			message += fill + res.getString(R.string.bairro) + " : "
					+ textNeighborhood + "\n";
		} else {
			message += "\n";
		}

		if (alAddress.size() > 0) {
			tVFind.setText(message);
			// tVFind.setVisibility(i == NONE ? GONE : VISIBLE);
			btnShowAll.setVisibility(i == NONE ? GONE : VISIBLE);
		} else {
			// tVFind.setVisibility(GONE);
			btnShowAll.setVisibility(GONE);
		}
	}

	public void reload() {

		llImage.post(runnable);

	}

	Runnable runnable = new Runnable() {
		public void run() {
			tVCommen.setText(store.get(Store.INFOSITE));
			tVStoreName.setText(store.get(Store.NAMESITE));
			try {
				Bitmap bitmap = ((BitmapDrawable) store.getDrawable())
						.getBitmap();
				bitmap = ((BitmapDrawable) AddressViewControl.this.drawable)
						.getBitmap();
				imgStore.setImageBitmap(bitmap);
				imgStore.invalidate();
			} catch (Exception e) {
			}

			tVCommen.setText(store.get(Store.INFOSITE));
			tVStoreName.setText(store.get(Store.NAMESITE));

		}
	};

	public void addAddress(List<Address> list) {
		alAddress.clear();
		alAddressStore.clear();
		for (int i = 0; i < list.size(); i++) {
			alAddressStore.add(list.get(i));
		}
		btnBairro.setVisibility(GONE);
		btnCidade.setVisibility(GONE);
		btnEstado.setVisibility(GONE);

		if (alAddressStore.size() >= 1) {
			List<String> cities = new ArrayList<String>();
			List<String> states = new ArrayList<String>();
			List<String> neighboods = new ArrayList<String>();
			for (int i = 0; i < alAddressStore.size(); i++) {
				String city = alAddressStore.get(i).get(Address.CITY);
				boolean check = false;
				for (int j = 0; j < cities.size(); j++) {
					if (cities.get(j).equals(city)) {
						check = true;
						break;
					}
				}

				if (!check && city != null) {
					cities.add(city);
				}

				city = alAddressStore.get(i).get(Address.NEIGHBORHOOD);
				check = false;
				for (int j = 0; j < neighboods.size(); j++) {
					if (neighboods.get(j).equals(city)) {
						check = true;
						break;
					}
				}

				if (!check && city != null) {
					neighboods.add(city);
				}

				city = alAddressStore.get(i).get(Address.STATE);
				check = false;
				for (int j = 0; j < states.size(); j++) {
					if (states.get(j).equals(city)) {
						check = true;
						break;
					}
				}

				if (!check && city != null) {
					states.add(city);
				}
			}

			if (cities.size() > 1) {
				btnCidade.setVisibility(VISIBLE);
			}

			if (neighboods.size() > 1) {
				btnBairro.setVisibility(VISIBLE);
			}

			if (states.size() > 1) {
				btnEstado.setVisibility(VISIBLE);
			}
		}

		// tVFind.setVisibility(GONE);
		btnShowAll.setVisibility(GONE);
	}

	public void addOnClickListener(OnClickListener listener) {
		// btnEstado.setOnClickListener(listener);
		// btnBairro.setOnClickListener(listener);
		// btnCidade.setOnClickListener(listener);
	}

	public void notifyDataSetChanged(int tab, String search, boolean first) {
		choise(tab, search);

		if (first) {
			if (alAddressStore.size() > 0) {
				search = alAddressStore.get(0).get(Address.STATE);
			}
		}

		alAddress.clear();

		for (int i = 0; i < alAddressStore.size(); i++) {
			Address address = alAddressStore.get(i);
			if (check(address)) {
				alAddress.add(address);
			}
			// if (tab == STATE
			// && search.equals(alAddressStore.get(i).get(Address.STATE))) {
			// alAddress.add(alAddressStore.get(i));
			// } else if (tab == CITY
			// && search.equals(alAddressStore.get(i).get(Address.CITY))) {
			// alAddress.add(alAddressStore.get(i));
			// } else if (tab == NEIGHBORHOOD
			// && search.equals(alAddressStore.get(i).get(
			// Address.NEIGHBORHOOD))) {
			// alAddress.add(alAddressStore.get(i));
			// } else if (tab == NONE) {
			// alAddress.add(alAddressStore.get(i));
			// }
		}

		lVAddress.removeAllViews();
		for (int i = 0; i < alAddress.size(); i++) {
			AddressItemView addressItemView = new AddressItemView(context);
			addressItemView.setData(alAddress.get(i));
			lVAddress.addView(addressItemView);
		}
		// Common.builder(getContext(), "" + alAddress.size());
	}

	private boolean check(Address address) {
		boolean check = true;
		if (equalsCheck(textCity, address.get(Address.CITY))) {
			check = false;
		}

		if (equalsCheck(textNeighborhood, address.get(Address.NEIGHBORHOOD))) {
			check = false;
		}
		if (equalsCheck(textState, address.get(Address.STATE))) {
			check = false;
		}
		return check;
	}

	public ImageView getImgStore() {
		return imgStore;
	}

	public void setImgStore(ImageView imgStore) {
		this.imgStore = imgStore;
	}

	public ImageButton getImgBtnWebsite() {
		return imgBtnWebsite;
	}

	public void setImgBtnWebsite(ImageButton imgBtnWebsite) {
		this.imgBtnWebsite = imgBtnWebsite;
	}

	public ImageButton getImgBtnAddress() {
		return imgBtnAddress;
	}

	public void setImgBtnAddress(ImageButton imgBtnAddress) {
		this.imgBtnAddress = imgBtnAddress;
	}

	public TextView gettVCommen() {
		return tVCommen;
	}

	public void settVCommen(TextView tVCommen) {
		this.tVCommen = tVCommen;
	}

	public ArrayList<Address> getAlAddress() {
		return alAddress;
	}

	public void setAlAddress(ArrayList<Address> alAddress) {
		this.alAddress = alAddress;
	}

	public ArrayAdapter<Address> getAdapter() {
		return adapter;
	}

	public void setAdapter(AddressApater adapter) {
		this.adapter = adapter;
	}

	public Button getBtnEstado() {
		return btnEstado;
	}

	public void setBtnEstado(Button btnEstado) {
		this.btnEstado = btnEstado;
	}

	public Button getBtnCidade() {
		return btnCidade;
	}

	public void setBtnCidade(Button btnCidade) {
		this.btnCidade = btnCidade;
	}

	public Button getBtnBairro() {
		return btnBairro;
	}

	public void setBtnBairro(Button btnBairro) {
		this.btnBairro = btnBairro;
	}

	public void onClick(View arg0) {
		text = "";

		choise(NONE, "");

		alAddress.clear();

		lVAddress.removeAllViews();
		alAddress.addAll(alAddressStore);
		for (int i = 0; i < alAddress.size(); i++) {
			AddressItemView addressItemView = new AddressItemView(context);
			addressItemView.setData(alAddress.get(i));
			lVAddress.addView(addressItemView);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	Drawable drawable;

	public void setImageOffer(Drawable drawable) {
		if (drawable != null) {
			this.drawable = drawable;
		} else {
			drawable = store.getDrawable();
		}
	}

	public String getTextState() {
		return textState;
	}

	public void setTextState(String textState) {
		this.textState = textState;
	}

	public String getTextCity() {
		return textCity;
	}

	public void setTextCity(String textCity) {
		this.textCity = textCity;
	}

	public String getTextNeighborhood() {
		return textNeighborhood;
	}

	public void setTextNeighborhood(String textNeighborhood) {
		this.textNeighborhood = textNeighborhood;
	}

	private boolean equalsCheck(String s1, String s2) {
		if (s1.equals("") || s2.equals("")) {
			return false;
		}

		return !s1.equals(s2);
	}
}