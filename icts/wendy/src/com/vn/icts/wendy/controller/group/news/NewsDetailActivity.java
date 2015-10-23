/**
 * 
 */
package com.vn.icts.wendy.controller.group.news;

import android.widget.ImageView;
import android.widget.TextView;

import com.fedorvlasov.lazylist.ImageLoader;
import com.ict.library.activity.BaseActivity;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.model.News;
import com.vn.icts.wendy.view.TopBarView;

/**
 * @author tvuong1pc
 * 
 */
public class NewsDetailActivity extends BaseActivity {

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_detail);
		setData();
	}

	private void setData() {
		News news = getIntent().getExtras().getParcelable("NEWS");
		TextView tvName = getView(R.id.new_detail_name);
		tvName.setText(news.getName());

		TopBarView barView = getView(R.id.newDetail_topbar);
		barView.setTitle(news.getName());

		TextView tvContet = getView(R.id.new_detail_content);
		tvContet.setText(news.getComment());

		ImageView imageView = getView(R.id.newDetailAvatar);

		// imgAvatar
		ImageLoader downloader = new ImageLoader(getParent(), R.drawable.transfer);
		downloader.DisplayImage(news.getUrlImage(),getParent(), imageView);

	}
}
