package org.com.shoppie;

import java.util.ArrayList;
import java.util.List;

import org.com.shoppie.adapter.MPagerAdapter;
import org.com.shoppie.model.Merchant;
import org.com.shoppie.service.RemoteServiceBindManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.TestExample;
import com.vnp.core.common.CommonXMLfunctions;
import com.vnp.core.common.ImageLoader;
import com.vnp.core.service.RestClient;
import com.vnp.core.service.RestClient.RequestMethod;

public class MainActivity extends MBaseActivity implements OnKeyListener {
	private ViewPager viewPager;

	private ImageLoader imageLoader;
	private RemoteServiceBindManager binManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Main");
		setContentView(R.layout.activity_main);
		setUseService(true);
		
		binManager = new RemoteServiceBindManager(this);
		
		imageLoader = new ImageLoader(this);
		viewPager = getView(R.id.viewPager);
		viewPager.setAdapter(new MPagerAdapter(this) {
			@Override
			public Object instantiateItem(View collection, final int position) {

				Merchant merchant = (Merchant) list.get(position);

				ImageView view = new ImageView(collection.getContext());
				view.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(final View v) {
						AnimationSet animationSet = new AnimationSet(false);
						animationSet
								.setAnimationListener(new AnimationListener() {

									@Override
									public void onAnimationStart(
											Animation animation) {
										v.setEnabled(false);
									}

									@Override
									public void onAnimationRepeat(
											Animation animation) {

									}

									@Override
									public void onAnimationEnd(
											Animation animation) {
										v.setEnabled(true);
									}
								});
						int duaration = 500;

						AlphaAnimation alphaAnimation = new AlphaAnimation(1f,
								0.5f);
						alphaAnimation.setDuration(duaration);
						ScaleAnimation zoom = new ScaleAnimation(1, 2, 1, 2);
						zoom.setDuration(duaration);
						animationSet.addAnimation(zoom);

						animationSet.addAnimation(alphaAnimation);

						float fromXDelta = v.getLeft();
						float fromYDelta = v.getTop();
						float toYDelta = fromXDelta - v.getWidth();
						float toXDelta = fromXDelta - v.getHeight();

						TranslateAnimation translateAnimation = new TranslateAnimation(
								fromXDelta, toXDelta, fromYDelta, toYDelta);
						// translateAnimation.setDuration(duaration);
						animationSet.addAnimation(translateAnimation);
						v.startAnimation(animationSet);
					}
				});

				LinearLayout linearLayout = new LinearLayout(collection
						.getContext());
				linearLayout.setOrientation(1);

				linearLayout.setGravity(Gravity.CENTER);
				linearLayout.addView(view);

				ViewGroup.LayoutParams params = view.getLayoutParams();
				params.height = 100;
				params.width = 200;

				view.setLayoutParams(params);

				imageLoader.DisplayImage(merchant.getMerchImage(), view);
				TextView textView = new TextView(collection.getContext());
				linearLayout.addView(textView);
				textView.setGravity(Gravity.CENTER);

				textView.setText(merchant.getMerchName());
				((ViewPager) collection).addView(linearLayout);
				return linearLayout;
			}
		});

		// bindService(new Intent(IShopPieService.class.getName()), mConnection,
		// Context.BIND_AUTO_CREATE);

		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
		// plugin headset or unplugin headset
		intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
		intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY + 100);
		registerReceiver(broadcastReceiver, intentFilter);

		intentFilter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);
		// plugin headset or unplugin headset
		intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY + 100);
		registerReceiver(headsetReceiver, intentFilter);
		
		toast(TestExample.testArea());
	}

	
	private void toast(String testExample) {
		Toast.makeText(this, testExample, 1).show();
	}


	@Override
	protected void onStart() {
		super.onStart();
		binManager.onStart();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		binManager.onStop();
		super.onStop();
	}
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(Intent.ACTION_HEADSET_PLUG)) {
				String data = intent.getDataString();
				Bundle extraData = intent.getExtras();

				String st = intent.getStringExtra("state");
				String nm = intent.getStringExtra("name");
				String mic = intent.getStringExtra("microphone");
				String all = String.format("st=%s, nm=%s, mic=%s", st, nm, mic);
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();

		unregisterReceiver(broadcastReceiver);
		// unbindService(mConnection);
	}

	@Override
	protected void onResume() {
		super.onResume();
		execute();
	}

	@Override
	protected Object _doInBackground() {

		RestClient restClient = new RestClient(
				"http://shoppie.top50.vn/index.php/api/webservice/merchants");
		try {
			restClient.execute(RequestMethod.GET);
		} catch (Exception e) {
			// Log.e("sss", "bbb", e);
		}
		return restClient;
	}

	@Override
	protected void _onPostExecute(Object data) {
		if (((RestClient) data).getResponseCode() == 200) {
			dataStore.save("data", ((RestClient) data).getResponse());
		}
		List<Object> list = new ArrayList<Object>();
		String dataXml = dataStore.get("data", "");
		Document document = CommonXMLfunctions.XMLfromString(dataXml);
		if (document != null) {
			NodeList nodes = document.getElementsByTagName("Merchant");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element e = (Element) nodes.item(i);
				Merchant merchant = new Merchant(e);
				list.add(merchant);
			}
		}

		((MPagerAdapter) viewPager.getAdapter()).addData(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_action_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/* creating an intent filter */
	private final BroadcastReceiver headsetReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String intentAction = intent.getAction();
			if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction))
				return;
			KeyEvent event = (KeyEvent) intent
					.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			int keycode = event.getKeyCode();
			int action = event.getAction();
			// onKeyDown(keyCode, event)
			if (keycode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
					|| keycode == KeyEvent.KEYCODE_HEADSETHOOK)
				if (action == KeyEvent.ACTION_DOWN) {

				}
			// playButton.performClick();
			if (keycode == KeyEvent.KEYCODE_MEDIA_NEXT)
				if (action == KeyEvent.ACTION_DOWN) {

				}
			// skipButton.performClick();
			if (keycode == KeyEvent.KEYCODE_MEDIA_PREVIOUS)
				if (action == KeyEvent.ACTION_DOWN) {

				}
			// stopButton.performClick();

		}

	};

	/* media controls */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_MEDIA_NEXT:
			// skipButton.performClick();
			make(String.valueOf(KeyEvent.KEYCODE_MEDIA_NEXT));
			return true;
		case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
			make(String.valueOf(KeyEvent.KEYCODE_MEDIA_PREVIOUS));
			// stopButton.performClick();
			return true;
		case KeyEvent.KEYCODE_HEADSETHOOK:
			// playButton.performClick();
			make(String.valueOf(KeyEvent.KEYCODE_HEADSETHOOK));
			return true;
		}
		return false;
	}

	private void make(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}