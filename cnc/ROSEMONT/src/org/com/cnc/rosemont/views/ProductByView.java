package org.com.cnc.rosemont.views;

import java.util.List;

import org.com.cnc.common.android.CommonDeviceId;
import org.com.cnc.common.android.CommonView;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.HomeActivity;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.items.ItemSearch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

public class ProductByView extends LinearLayout implements IView{
	private LinearLayout llCOntent;
	private boolean isCategory = false;
//	private AnimationSlide animationSlide=new AnimationSlide();

	// private String data = "";

	public ProductByView(Context context, boolean isCategory) {
		super(context);
		config(R.layout.prodyctlistby);
		this.isCategory = isCategory;
	}

	public ProductByView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.prodyctlistby);
	}

	private void config(int resource_layouy) {
	//	setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.setType(HeaderView.TYPE_LIST_PRODUCT_BY);
		headerView.showButton(true, false);
		headerView.setOnClick(onClickBack, null);
		llCOntent = (LinearLayout) findViewById(R.id.llContentProduct);
	}

	public void addExampleData(String data) {
		if (isCategory) {
			addData(CommonApp.ROSEMONT.getCategoryList(data));
		} else {
			addData(CommonApp.ROSEMONT.getIndicationsList(data));
		}
	}

	public void addData(final List<ItemSearch> ldata) {
		new AsyncTask<String, String, String>() {
			LinearLayout linearLayout;

			protected String doInBackground(String... params) {
				linearLayout = new LinearLayout(getContext());
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				for (int i = 0; i < ldata.size(); i++) {
					int index = 2;
					if (ldata.size() == 1) {
						index = 0;
					} else {
						if (i == 0) {
							index = 1;
						}

						if (i == ldata.size() - 1) {
							index = 3;
						}

						addItemListProduct(index, ldata.get(i).getTxtHeader(),
								linearLayout, ldata.get(i).getId(), ldata
										.get(i).getIdTable());
					}
				}

				return null;
			}

			protected void onPostExecute(String result) {
				llCOntent.addView(linearLayout);
			}
		}.execute("");

	}

	private void addItemListProduct(int index, String value,
			LinearLayout linearLayout, String id, String idRow) {
		Log.i("ID", id + "");
		final ItemProductListByView child = new ItemProductListByView(
				getContext(), index);
		child.setOnClickListener(new ItemClick(id, idRow));
		child.setData(value);
		linearLayout.addView(child);
		Runnable runnable = new Runnable() {
			public void run() {
				int width = CommonDeviceId.getWidth((Activity) (getContext()));
				width = width * 600 / 640;
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.item3);
				int height = bitmap.getHeight();
				height = height * width / bitmap.getWidth();
				LayoutParams params = CommonView.createLayoutParams(width,
						height);
				child.setLayoutParams(params);
			}
		};
		post(runnable);
	}

	private class ItemClick implements OnClickListener {
		String id = "";
		private String idrow;

		public ItemClick(String id, String idrow) {
			this.id = id;
			this.idrow = idrow;
		}

		public void onClick(View v) {
			if (isTabHome() && v instanceof ItemProductListByView) {
				((HomeActivity) getContext()).gotoProductDetail(id, idrow);
			}
		}
	};

	private OnClickListener onClickBack = new OnClickListener() {
		public void onClick(View v) {
			if (isTabHome()) {
				((HomeActivity) getContext()).onBack();
			}
		}
	};

	private boolean isTabHome() {
		return getContext() instanceof HomeActivity;
	}

	public void reset() {
		
	}

}
