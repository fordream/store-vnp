/**
 * 
 */
package com.vn.icts.wendy.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fedorvlasov.lazylist.ImageLoader;
import com.ict.library.database.DataStore;
import com.ict.library.view.CustomLinearLayoutView;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.model.News;

/**
 * @author tvuong1pc
 * 
 */
public class ItemNewView extends CustomLinearLayoutView {
	private ImageView imgAvatar;
	private String textShow = null;
	private TextView tvName;
	private TextView tvComment;
	private TextView tvTime;

	/**
	 * @param context
	 */
	public ItemNewView(Context context) {
		super(context);
		init(R.layout.item_new_view);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ItemNewView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.item_new_view);
	}

	@Override
	public void init(int res) {
		super.init(res);
		imgAvatar = getView(R.id.imageView6);
		tvTime = getView(R.id.textView2);
		tvComment = getView(R.id.textView3);
		tvName = getView(R.id.textView1);

	}

	@Override
	public void setGender() {
		super.setGender();

		if (getData() instanceof News) {
			News shop = (News) getData();
			tvName.setText(shop.getName());
			try {
				String time = shop.getTime();
				String first = time.substring(0, 10);
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				Date date = dateFormat.parse(time);
				String last = "";
				if (date.getHours() < 10) {
					last = "0" + date.getHours();
				} else if (date.getHours() > 12) {
					last = (date.getHours() - 12) + "";
				} else {
					last = (date.getHours()) + "";
				}

				last += ":";

				if (date.getMinutes() < 10) {
					last += "0" + date.getMinutes();
				} else {
					last += date.getMinutes();
				}
				
				if(date.getHours() > 12){
					last +="pm";
				}else{
					last +="am";
				}
				tvTime.setText(first +" "+ last);
			} catch (Exception e) {

			}
			StringBuilder builder = new StringBuilder();
			builder.append("<html><body>");
			builder.append(shop.getComment());
			builder.append("<font color=\"red\">Learn more</font>");
			builder.append("</body></html>");
			tvComment.setText(Html.fromHtml(builder.toString()));

			ImageLoader downloader = new ImageLoader(getContext(),
					R.drawable.transfer);
			downloader.DisplayImage(shop.getUrlImage(),
					(Activity) getContext(), imgAvatar);
			
			// set Backgrond
//			DataStore dataStore = DataStore.getInstance();
			String ids = DataStore.getInstance().get("news_id", "");
			Log.e("ids", "22222222222"+ids);
			if(ids.contains(";"+shop.getId() + ";") ){
				findViewById(R.id.item_new_view_ll).setBackgroundResource(R.drawable.new_old);
			}else{
				findViewById(R.id.item_new_view_ll).setBackgroundResource(R.drawable.transfer);
			}
		}
	}
}