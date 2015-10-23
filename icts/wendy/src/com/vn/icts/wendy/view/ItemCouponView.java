/**
 * 
 */
package com.vn.icts.wendy.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.fedorvlasov.lazylist.ImageLoader;
import com.ict.library.view.CustomLinearLayoutView;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.model.Coupon;

/**
 * @author tvuong1pc
 * 
 */
public class ItemCouponView extends CustomLinearLayoutView implements
		OnClickListener {
	private ImageView imgAvatar;

	private TextView tvName;
	private TextView tvComment;
	private TextView tvAddress;
	private TextView tvWebsite;

	/**
	 * @param context
	 */
	public ItemCouponView(Context context) {
		super(context);
		init(R.layout.item_coupon_view);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ItemCouponView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.item_coupon_view);
	}

	@Override
	public void init(int res) {
		super.init(res);
		imgAvatar = getView(R.id.imageView6);
		tvAddress = getView(R.id.textView3);
		tvWebsite = getView(R.id.textView4);
		tvComment = getView(R.id.textView2);
		tvName = getView(R.id.textView1);

		tvWebsite.setOnClickListener(this);
	}

	@Override
	public void setGender() {
		super.setGender();

		if (getData() instanceof Coupon) {
			Coupon shop = (Coupon) getData();
			tvName.setText(shop.getName());
			tvAddress.setText("   " + shop.getAddress());
			tvComment.setText(shop.getComment());
			tvWebsite.setText("   " + shop.getWeb());

			// imgAvatar
			
			ImageLoader downloader = new ImageLoader(getContext(), R.drawable.transfer);
			downloader.DisplayImage(shop.getUrlImage(),(Activity)getContext(), imgAvatar);

		}

	}

	@Override
	public void onClick(View v) {
		if (getData() instanceof Coupon) {
			String url = ((Coupon) getData()).getWeb();
			if (url != null && !TextUtils.isEmpty(url)
					&& url.startsWith("http:")) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				getContext().startActivity(i);
			}
		}
	}
}