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
import com.vn.icts.wendy.model.Shop;

/**
 * @author tvuong1pc
 * 
 */
public class ItemShopView extends CustomLinearLayoutView implements
		OnClickListener {
	private ImageView imgAvatar;

	private TextView tvPrice;

	private TextView tvName;
	private TextView tvComment;
	private TextView tvAddress;
	private TextView tvWebsite;

	/**
	 * @param context
	 */
	public ItemShopView(Context context) {
		super(context);
		init(R.layout.item_shop_view);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ItemShopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.item_shop_view);
	}

	@Override
	public void init(int res) {
		super.init(res);
		imgAvatar = getView(R.id.imageView6);
		tvAddress = getView(R.id.textView3);
		tvWebsite = getView(R.id.textView4);
		tvComment = getView(R.id.textView2);
		tvName = getView(R.id.textView1);
		tvPrice = getView(R.id.textView5);

		tvWebsite.setOnClickListener(this);
	}

	@Override
	public void setGender() {
		super.setGender();

		if (getData() instanceof Shop) {
			Shop shop = (Shop) getData();
			tvName.setText(shop.getName());
			tvAddress.setText("   " + shop.getAddress());
			tvComment.setText(shop.getComment());
			tvWebsite.setText("   " + shop.getWeb());
			tvPrice.setText("$" + shop.getPrice());
			
			ImageLoader downloader = new ImageLoader(getContext(), R.drawable.transfer);
			downloader.DisplayImage(shop.getUrlImage(),(Activity)getContext(), imgAvatar);

		}

	}

	@Override
	public void onClick(View v) {
		if (getData() instanceof Shop) {
			String url = ((Shop) getData()).getWeb();
			//
			if (url != null && !TextUtils.isEmpty(url)
					&& url.startsWith("http:")) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				getContext().startActivity(i);
			}
		}
	}
}
