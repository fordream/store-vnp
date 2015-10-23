package com.example.test;

import android.R.style;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ict.library.common.CommonLog;
import com.ict.library.common.CommonResize;
import com.ict.library.database.CommonDataStore;

/**
 * TODO: document your custom view class.
 */
public class OnePageView extends LinearLayout {
	private GridView gridView;
	private int position;

	public OnePageView(Context context) {
		super(context);
		init(null, 0);
	}

	public OnePageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	private void init(AttributeSet attrs, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.sample_one_page_view, this);
		gridView = (GridView) findViewById(R.id.gridView1);
		CommonResize._20130408_resizeLandW960H640(gridView, 760, 640);
		CommonResize._20130408_sendViewToPositionLandW960H640(gridView, 200, 0);
		int left = CommonResize._20130408_getSizeByScreenLandW960H640(getContext(), 10);
		int top = CommonResize._20130408_getSizeByScreenLandW960H640(getContext(), 15);
		int right = CommonResize._20130408_getSizeByScreenLandW960H640(getContext(), 10);
		int bottom = CommonResize._20130408_getSizeByScreenLandW960H640(getContext(), 20);

		gridView.setPadding(left, top, right, bottom);
		gridView.setVerticalSpacing(CommonResize._20130408_getSizeByScreenLandW960H640(getContext(), 35));
		gridView.setHorizontalSpacing(CommonResize._20130408_getSizeByScreenLandW960H640(getContext(), 10));
		gridView.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = new ItemView(getContext());
				}

				CommonDataStore.getInstance().init(getContext());
				if (CommonDataStore.getInstance().get(OnePageView.this.position + ";" + position, false)) {
					((ItemView) convertView).setAlpha(true);
				} else {
					((ItemView) convertView).setAlpha(false);
				}
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return "";
			}

			@Override
			public int getCount() {
				return 6;
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, final View view, final int position, long arg3) {
				final Dialog dialog = new Dialog(view.getContext(), style.Theme_Translucent_NoTitleBar_Fullscreen);

				dialog.setContentView(new ItemViewDetail(view.getContext()) {
					@Override
					public void onClickCancel() {
						dialog.dismiss();
					}
				});

				dialog.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						CommonDataStore.getInstance().init(getContext());
						CommonDataStore.getInstance().save(OnePageView.this.position + ";" + position, true);
						((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
					}
				});

				dialog.show();
			}
		});
	}

	// * class for show detail*//
	private abstract class ItemViewDetail extends LinearLayout implements OnClickListener {

		public ItemViewDetail(Context context) {
			super(context);
			init();
		}

		private void init() {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.buy_detail, this);
			CommonResize._20130408_resizeLandW960H640(findViewById(R.id.relati_detail), 960, 640);
			CommonResize._20130408_resizeLandW960H640(findViewById(R.id.detaoilimageView1), 960, 640);
			// hidden price
			CommonResize._20130408_resizeLandW960H640(findViewById(R.id.detail_imageView1), 420, 100);
			CommonResize._20130408_sendViewToPositionLandW960H640(findViewById(R.id.detail_imageView1), 960 / 2 - 420 / 2, 640 - 100 / 2 - 185);

			// ssss
			int size = 40;
			CommonResize._20130408_resizeLandW960H640(findViewById(R.id.progressBar1), size, size);
			CommonResize._20130408_sendViewToPositionLandW960H640(findViewById(R.id.progressBar1), 960 / 2 - size / 2, 640 - size / 2 - 185);

			// button cancel
			int size_button = 100;
			CommonResize._20130408_resizeLandW960H640(findViewById(R.id.button1), size_button, size_button);
			CommonResize._20130408_sendViewToPositionLandW960H640(findViewById(R.id.button1), 960 - size_button - 10, 5);
			findViewById(R.id.button1).setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.button1) {
				onClickCancel();
			}
		}

		public abstract void onClickCancel();
	}

	// * class for show icon*//
	private class ItemView extends LinearLayout {
		private ImageView imageView;

		public ItemView(Context context) {
			super(context);
			init();
			// AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.4f);
			// alphaAnimation.setFillAfter(true);
			// setAnimation(alphaAnimation);
		}

		public void setAlpha(boolean check) {
			AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.4f);
			if (!check) {
				alphaAnimation = new AlphaAnimation(0.4f, 1f);
			}
			alphaAnimation.setFillAfter(true);
			setAnimation(alphaAnimation);
			// imageView.setAnimation(alphaAnimation);
		}

		private void init() {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.item, this);
			imageView = (ImageView) findViewById(R.id.detail_imageView1);
			CommonResize._20130408_resizeLandW960H640(imageView, 370, 165);
		}
	}

	public void savePosition(int position) {
		this.position = position;
	}
}