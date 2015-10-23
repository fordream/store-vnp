package com.icts.shortfilmfestival.zzz.t.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fedorvlasov.lazylist.ImageLoader;
import com.icts.shortfilmfestival.zzz.t.api.RestClient.RequestMethod;
import com.vnp.shortfilmfestival.R;

public class Rest {
	int count = 0;

	public void setLogo(final RelativeLayout layout) {
		new AsyncTask<String, String, String>() {
			private String link;
			// private Bitmap bitmap;
			String link_image;

			@Override
			protected String doInBackground(String... params) {
				String url = "http://49.212.161.19/apissff/api/get_button_images.php";
				RestClient client = new RestClient(url);

				try {
					client.execute(RequestMethod.GET);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONObject jsonObject = new JSONObject(client.getResponse());
					JSONArray array = jsonObject.getJSONArray("data");
					JSONObject item = array.getJSONObject(0);

					link = item.getString("link_url");
					link_image = item.getString("image_url");
					if (link_image != null) {
						link_image = link_image.replace("\\", "");
						Log.w("aaaaa", link_image);
					}

				} catch (Exception e) {
				}

				return null;
			}

			protected void onPostExecute(String result) {
				layout.setGravity(Gravity.CENTER);
				ImageView imageView = (ImageView) layout
						.findViewById(R.id.main_img_main);
				if (link_image != null) {
					ImageLoader download = new ImageLoader(
							imageView.getContext(), R.drawable.transfer);
					download.DisplayImage(link_image,
							(Activity) imageView.getContext(), imageView);
				}

				if (link != null) {
					layout.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							if (link.startsWith("http")) {
								Intent intent = new Intent(Intent.ACTION_VIEW,
										Uri.parse(link));
								layout.getContext().startActivity(intent);
							}
						}
					});
				}
			};

		}.execute("");
	}

	public void setSlide(final BannerView layout) {
		new AsyncTask<String, String, String>() {
			List<Item> lItems = new ArrayList<Item>();

			@Override
			protected String doInBackground(String... params) {
				String url = "http://49.212.161.19/apissff/api/get_banner_images.php";
				RestClient client = new RestClient(url);

				try {
					client.execute(RequestMethod.GET);
				} catch (Exception e) {
				}

				try {
					JSONObject jsonObject = new JSONObject(client.getResponse());
					JSONArray array = jsonObject.getJSONArray("data");

					for (int i = 0; i < array.length(); i++) {
						if (array.get(i) instanceof JSONObject) {
							JSONObject object = array.getJSONObject(i);
							Item item = new Item();
							item.setId(object.getString("id"));
							String link_image = object.getString("image_url");
							if (link_image != null) {
								link_image = link_image.replace("\\", "");
								item.setImage_url(link_image);
							}
							link_image = object.getString("link_url");
							if (link_image != null) {
								link_image = link_image.replace("\\", "");
								item.setLink_url(link_image);
							}
							lItems.add(item);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}

			protected void onPostExecute(String result) {
				if (lItems.size() > 0) {

					new Thread(new Runnable() {

						public void run() {

							while (!((Activity) layout.getContext())
									.isFinishing() && run) {
								try {
									Thread.sleep(3 * 1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								if (count < lItems.size() - 1)
									count++;
								else
									count = 0;
								if (lItems.size() >= 1) {
									layout.setUrl(lItems.get(count)
											.getImage_url(), lItems.get(count)
											.getLink_url());
								}
							}

						}
					}).start();

				}
			};

		}.execute("");
	}

	private boolean run = true;

	public void close() {
		run = false;
	}
}