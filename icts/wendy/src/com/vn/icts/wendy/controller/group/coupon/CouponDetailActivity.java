/**
 * 
 */
package com.vn.icts.wendy.controller.group.coupon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.t.DialogError;
import com.facebook.android.t.Facebook;
import com.facebook.android.t.Facebook.DialogListener;
import com.facebook.android.t.FacebookError;
import com.fedorvlasov.lazylist.ImageLoader;
import com.ict.library.activity.BaseActivity;
import com.ict.library.anetwork.CommonNetwork;
import com.twitter.android.t.TwitterApp;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.model.Coupon;
import com.vn.icts.wendy.model.Setting;
import com.vn.icts.wendy.view.TopBarView;
import com.vn.icts.wendy.view.dialog.ImageDialog;

/**
 * @author tvuong1pc
 * 
 */
public class CouponDetailActivity extends BaseActivity implements
		OnClickListener {
	private Facebook mFacebook;
	private static final String[] PERMISSIONS = new String[] {
			"publish_stream", "read_stream" };
	private TextView tvName, tvComment, tvPost, tvLike, tvTime;
	private ImageView imgAvatar;
	private ImageView imgQrcode;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_detail);
		mFacebook = new Facebook("413680098662330");
		TopBarView topBarView = getView(R.id.coupondetail_topbar);
		topBarView.setTitle("COUPON DETAIL");

		tvName = getView(R.id.coupon_detail_name);
		tvComment = getView(R.id.coupon_detail_comment);
		tvPost = getView(R.id.coupon_detail_post);
		tvLike = getView(R.id.coupon_detail_like);
		tvTime = getView(R.id.coupon_detail_time);
		imgAvatar = getView(R.id.coupon_detail_avatar);
		imgQrcode = getView(R.id.coupon_detail_img_qrcode);

		getView(R.id.coupon_detail_face).setOnClickListener(this);
		getView(R.id.coupon_detail_twitter).setOnClickListener(this);
		imgQrcode.setOnClickListener(this);
		setData();
	}

	private void setData() {
		Coupon coupon = getIntent().getExtras().getParcelable("COUPON");
		tvComment.setText(coupon.getComment());
		tvLike.setText("   " + coupon.getLike() + " purchsed");
		tvName.setText(coupon.getName());
		tvPost.setText(coupon.getPost());
		tvTime.setText("   " + coupon.getTime() + " days left");

		// imgAvatar
		ImageLoader downloader = new ImageLoader(getParent(),
				R.drawable.transfer);
		downloader.DisplayImage(coupon.getUrlImage(), getParent(), imgAvatar);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Setting setting = new Setting();

		if (setting.isConnectFace()) {
			getView(R.id.coupon_detail_face).setVisibility(View.VISIBLE);
		} else {
			getView(R.id.coupon_detail_face).setVisibility(View.GONE);
		}

		if (setting.isConnectTwiiter()) {
			getView(R.id.coupon_detail_twitter).setVisibility(View.VISIBLE);
		} else {
			getView(R.id.coupon_detail_twitter).setVisibility(View.GONE);
		}
	}

	private class LoginFacebookDialogListener implements DialogListener {

		public void onComplete(Bundle values) {
			Coupon coupon = getIntent().getExtras().getParcelable("COUPON");
			String message = coupon.getName() + "\n" + coupon.getComment();
			message += "\n";
			postToWall(message, coupon.getUrlImage());
		}

		public void onFacebookError(FacebookError error) {
		}

		public void onError(DialogError error) {
		}

		public void onCancel() {
		}
	}

	public void postToWall(String message, String link) {
		Bundle parameters = new Bundle();
		parameters.putString("message", message + " ");

		parameters.putString("link", link + "");
		try {
			mFacebook.request("me");
			String response = mFacebook.request("me/feed", parameters, "POST");
			if (response == null || response.equals("")
					|| response.contains("error")) {
				Log.d("Post to Facebook Wall", "Failed to post to wall");
			} else {
				Log.d("Post Facebook Wall",
						"Message posted to your facebook wall!");
				Toast.makeText(this, "posted facebook wall successful!",
						Toast.LENGTH_LONG).show();

			}

		} catch (Exception e) {
			Log.e("Post Facebook Wall", "Failed to post to wall!");
			Toast.makeText(this, "Failed to post to wall!", Toast.LENGTH_LONG)
					.show();

		}
	}

	private static final String twitter_consumer_key = "qQZqxAzQHDnZPocGwLn9A";
	private static final String twitter_secret_key = "FoVXlAvaXqwH30nuK1bDv7CS14GROdxnWaZnV8sY5U";

	@Override
	public void onClick(View arg0) {
		Coupon coupon = getIntent().getExtras().getParcelable("COUPON");
		switch (arg0.getId()) {
		case R.id.coupon_detail_face:
			if (CommonNetwork.haveConnectTed(getParent())) {
				mFacebook.authorize(getParent(), PERMISSIONS,
						Facebook.FORCE_DIALOG_AUTH,
						new LoginFacebookDialogListener());
			} else {
				Toast.makeText(getParent(), "Check your network!",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.coupon_detail_twitter:
			if (CommonNetwork.haveConnectTed(getParent())) {
				String tweetUrl = "https://twitter.com/intent/tweet?text={0}&url="
						+ "{1}";
				tweetUrl = tweetUrl.replace("{0}", coupon.getName());
				tweetUrl = tweetUrl.replace("{1}", coupon.getUrlImage());
				Uri uri = Uri.parse(tweetUrl);
				startActivity(new Intent(Intent.ACTION_VIEW, uri));
				
			} else {
				Toast.makeText(getParent(), "Check your network!",
						Toast.LENGTH_SHORT).show();
			}
			
			// TODO
			break;

		case R.id.coupon_detail_img_qrcode:
			ImageDialog dialog = new ImageDialog(getParent());
			dialog.show();
			break;
		default:
			break;
		}
	}
}