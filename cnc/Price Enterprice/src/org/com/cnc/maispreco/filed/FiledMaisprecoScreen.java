package org.com.cnc.maispreco.filed;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;

public class FiledMaisprecoScreen implements OnClickListener {
	private ImageView imgBack;
	private TextView tVStatus;
	private ImageView imgSearch;
	private EditText etSearch;
	private ImageView imgBarcode;
	private RelativeLayout rlStatus;
	private RelativeLayout rlBarcode;
	private RelativeLayout rlSearch;
	private RelativeLayout rlBanner;
	private ImageView imgBanner;
	private MaisprecoScreen maisprecoScreen;
	private boolean isStop = true;

	public FiledMaisprecoScreen(MaisprecoScreen maisprecoScreen) {
		super();
		this.maisprecoScreen = maisprecoScreen;
		rlStatus = (RelativeLayout) maisprecoScreen.findViewById(R.id.rlStatus);
		rlBarcode = (RelativeLayout) maisprecoScreen
				.findViewById(R.id.rlBarcode);
		rlSearch = (RelativeLayout) maisprecoScreen.findViewById(R.id.rlSearch);
		rlBanner = (RelativeLayout) maisprecoScreen.findViewById(R.id.rlBanner);
		tVStatus = (TextView) maisprecoScreen.findViewById(R.id.tVStatus);
		imgBarcode = (ImageView) maisprecoScreen.findViewById(R.id.imgBarcode);
		imgBack = (ImageView) maisprecoScreen.findViewById(R.id.imgBack);
		etSearch = (EditText) maisprecoScreen.findViewById(R.id.etSearch);
		imgBack.setOnClickListener(this);
		rlBarcode.setOnClickListener(this);

		imgSearch = (ImageView) maisprecoScreen.findViewById(R.id.imgSearch);
		imgSearch.setOnClickListener(this);
		// configTabView();
	}

	public void onClick(View v) {
		if (v == rlBarcode) {
			setStop(false);
			Intent intent = new Intent(maisprecoScreen, CaptureActivity.class);
			maisprecoScreen.startActivityForResult(intent,
					MaisprecoScreen.REQUEST_SCAN);
		} else if (v == imgBack) {
			maisprecoScreen.onBack();
		} else if (v == imgSearch) {
			etSearch.setText("");
		}
	}

	public void viewHeader(boolean isShowBtnBack, boolean isShowBanner,
			boolean isShowSearch, boolean isShowBarcode, boolean isShowStatus) {
		imgBack.setVisibility(isShowBtnBack ? View.VISIBLE : View.GONE);
		rlBanner.setVisibility(isShowBanner ? View.VISIBLE : View.GONE);
		rlSearch.setVisibility(isShowSearch ? View.VISIBLE : View.GONE);
		rlBarcode.setVisibility(isShowBarcode ? View.VISIBLE : View.GONE);
		rlStatus.setVisibility(isShowStatus ? View.VISIBLE : View.GONE);
	}

	public ImageView getImgBack() {
		return imgBack;
	}

	public void setImgBack(ImageView imgBack) {
		this.imgBack = imgBack;
	}

	public TextView gettVStatus() {
		return tVStatus;
	}

	public void settVStatus(TextView tVStatus) {
		this.tVStatus = tVStatus;
	}

	public ImageView getImgSearch() {
		return imgSearch;
	}

	public void setImgSearch(ImageView imgSearch) {
		this.imgSearch = imgSearch;
	}

	public EditText getEtSearch() {
		return etSearch;
	}

	public void setEtSearch(EditText etSearch) {
		this.etSearch = etSearch;
	}

	public ImageView getImgBarcode() {
		return imgBarcode;
	}

	public void setImgBarcode(ImageView imgBarcode) {
		this.imgBarcode = imgBarcode;
	}

	public RelativeLayout getRlStatus() {
		return rlStatus;
	}

	public void setRlStatus(RelativeLayout rlStatus) {
		this.rlStatus = rlStatus;
	}

	public RelativeLayout getRlBarcode() {
		return rlBarcode;
	}

	public void setRlBarcode(RelativeLayout rlBarcode) {
		this.rlBarcode = rlBarcode;
	}

	public RelativeLayout getRlSearch() {
		return rlSearch;
	}

	public void setRlSearch(RelativeLayout rlSearch) {
		this.rlSearch = rlSearch;
	}

	public RelativeLayout getRlBanner() {
		return rlBanner;
	}

	public void setRlBanner(RelativeLayout rlBanner) {
		this.rlBanner = rlBanner;
	}

	public ImageView getImgBanner() {
		return imgBanner;
	}

	public void setImgBanner(ImageView imgBanner) {
		this.imgBanner = imgBanner;
	}

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean b) {
		isStop = b;
	}

}
