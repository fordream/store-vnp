package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnc.maispreco.soap.data.Locality;

public class LocalityItemView extends LinearLayout {
	private Locality ll;
	private TextView tVLocalityName;
	private RelativeLayout rlLocality_item;
	private ImageView img_Locality_Item;

	public LocalityItemView(Context context, Locality ll) {
		super(context);
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.locality_item, this, true);
		img_Locality_Item = (ImageView)findViewById(R.id.img_Locality_Item1);
		this.ll = ll;
		tVLocalityName = (TextView) findViewById(R.id.tVLocalityName);

		tVLocalityName.setText(this.ll.get(Locality.NAME));
	}

	public Locality getLl() {
		return ll;
	}

	public void setLl(Locality ll) {
		this.ll = ll;
	}

	public ImageView getImg_Locality_Item() {
		return img_Locality_Item;
	}

	public void setImg_Locality_Item(ImageView img_Locality_Item) {
		this.img_Locality_Item = img_Locality_Item;
	}

	public RelativeLayout getRlLocality_item() {
		return rlLocality_item;
	}

	public void setRlLocality_item(RelativeLayout rlLocality_item) {
		this.rlLocality_item = rlLocality_item;
	}

	public TextView gettVLocalityName() {
		return tVLocalityName;
	}

	public void settVLocalityName(TextView tVLocalityName) {
		this.tVLocalityName = tVLocalityName;
	}

	public void check(boolean isCheck) {
		if (img_Locality_Item != null)
			img_Locality_Item.setBackgroundResource(isCheck ? R.drawable.check
					: R.drawable.uncheck);
	}
}