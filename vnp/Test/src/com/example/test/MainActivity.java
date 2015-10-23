package com.example.test;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ict.library.common.CommonResize;
import com.ict.library.service.CommonRestClient.RequestMethod;
import com.ict.library.view.ResizeTextView;
import com.vnp.core.loadjar.MultiDexClassLoader;
import com.vnp.core.service.v2.CommonV2RestClientService;
import com.vnp.core.service.v2.RequestCallBack;
import com.vnp.core.service.v2.RequestInfo;
import com.vnp.core.service.v2.ResponseBroadcastReceiverCallBack;
import com.vnp.core.service.v2.ResponseInfor;
import com.vnp.core.service.v2.TypeResponse;
import com.vnp.core.service.v2.TypeServer;

public class MainActivity extends Activity {
	ViewPager pager;
	TextView textView;
	ResizeTextView resizeTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		resizeTextView = (ResizeTextView) findViewById(R.id.resize);

		CommonResize._20130408_resizeLandW960H640(resizeTextView, 960, 300);
		resizeTextView.setPer(0.4f);
		CommonResize._20130408_resizeLandW960H640(findViewById(R.id.id1), 960,
				640);
		// image resize
		ImageView imageView = (ImageView) findViewById(R.id.imageView4);
		CommonResize._20130408_resizeLandW960H640(imageView, 960, 640);
		CommonResize._20130408_sendViewToPositionW960(imageView, 0, 0);

		imageView = (ImageView) findViewById(R.id.imageView3);
		CommonResize._20130408_resizeLandW960H640(imageView, 200, 640);
		CommonResize._20130408_sendViewToPositionW960(imageView, 0, 0);

		// textView
		textView = (TextView) findViewById(R.id.textView1);
		CommonResize._20130408_resizeLandW960H640(textView, 760, 40);
		// CommonResize._20130408_sendViewToPositionW960(textView, 200, 600);

		// pager
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new PackagesPagerAdapter());
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int _pager) {
				changText();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		changText();

		String path = "sdcard/vol3ichinensei.jar";
		MultiDexClassLoader.getInstance().install(this, path);

		String className = "jp.co.xing.utaehon03.songs.Vol3Ichinensei";

		try {
			// Class<Fragment> fragmentClass = (Class<Fragment>)
			// MultiDexClassLoader.getInstance().loadClass(className);
			// Fragment fragment = fragmentClass.newInstance();
			// ft = getSupportFragmentManager().beginTransaction();
			// ft.add(R.id.dynamic_layout, fragment).commit();
			//
			// Class<View> fragmentClass = (Class<View>)
			// MultiDexClassLoader.getInstance().loadClass(className);
			// setContentView(fragmentClass.newInstance());
		} catch (Exception e) {
			// CommonLog.i("AAAAAAAAAAAAAAAAAAA", e);
		}

		// CommonShortCutUtils commonShortCutUtils = new
		// CommonShortCutUtils(this);

		// commonShortCutUtils.autoCreateShortCut(MainActivity.class,
		// R.string.app_name, R.drawable.ic_launcher);
		// commonShortCutUtils.createShortCutLauncher(R.string.app_name,
		// R.drawable.ic_launcher);
		// commonShortCutUtils.removieShortCutLauncher();
		// commonShortCutUtils.deleteShortCut(MainActivity.class,
		// R.string.app_name, R.drawable.ic_launcher);

		// startActivity(new Intent(this, TESTSHORTCUTActivity.class));
		// finish();

		test();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Toast.makeText(this, "" +((MAplication)getApplication()).getID(),
		// 1).show();
	}

	private void test() {
		RequestInfo arg1 = new RequestInfo();
		arg1.setMethod(RequestMethod.GET);

		arg1.setUrl("http://vnexpress.net/");
		arg1.setTypeServer(TypeServer.GET_CONTENT_STRING);
		arg1.setUrl("http://img.tamtay.vn/files/2008/06/08/phuong_661992_phuong/photos/174158/485460c3_48533467_48418955_51.jpg");
		arg1.setUrl("https://my-tvuong-document.googlecode.com/svn/trunk/code.zip");
		arg1.setTypeServer(TypeServer.DOWNLOAD_FILE);
		arg1.setFileFolderSaveFile("sdcard/download");
		arg1.setFileNameSave("hihi.zip");

		String id = arg1.getActionForCallBack();
		ResponseBroadcastReceiverCallBack callBack = new ResponseBroadcastReceiverCallBack(
				this) {

			@Override
			public void callback(ResponseInfor responseInfor, RequestInfo arg1) {

				if (responseInfor.getTypeResponse() == TypeResponse.END) {
					textView.setText("End");
				} else if (responseInfor.getTypeResponse() == TypeResponse.START) {
					textView.setText("Start");
				} else if (responseInfor.getTypeResponse() == TypeResponse.FAIL) {
					textView.setText("FAIL");
				} else if (responseInfor.getTypeResponse() == TypeResponse.RUNNING) {
					textView.setText(responseInfor.getTotalSize()
							+ " RUNNING : "
							+ (((double) responseInfor.getCurentSize() / +(double) responseInfor
									.getTotalSize()) * 100));
				}
			}
		};

		ViewExample viewExample = (ViewExample) findViewById(R.id.viewExample1);
		viewExample.setIdData(id);

		registerReceiver(callBack, new IntentFilter(id));
		CommonV2RestClientService.callServer(this, arg1);
	}

	private void changText() {
		PackagesPagerAdapter adapter = (PackagesPagerAdapter) pager
				.getAdapter();
		textView.setText((pager.getCurrentItem() + 1) + "/"
				+ adapter.getCount());
	}

	private RequestCallBack requestCallBack = new RequestCallBack() {
		@Override
		public void callback(ResponseInfor responseInfor, RequestInfo arg1) {
			super.callback(responseInfor, arg1);
			if (responseInfor.getTypeResponse() == TypeResponse.END) {
				textView.setText("End");
			} else if (responseInfor.getTypeResponse() == TypeResponse.START) {
				textView.setText("Start");
			} else if (responseInfor.getTypeResponse() == TypeResponse.FAIL) {
				textView.setText("FAIL");
			} else if (responseInfor.getTypeResponse() == TypeResponse.RUNNING) {
				textView.setText("RUN " + responseInfor.getCurentSize() + "/"
						+ responseInfor.getTotalSize());
			}
		}
	};

	private class PackagesPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 100;
		}

		@Override
		public Object instantiateItem(View collection, int position) {
			OnePageView layout = null;
			layout = new OnePageView(collection.getContext());
			layout.savePosition(position);
			((ViewPager) collection).addView(layout, 0);
			return layout;
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			OnePageView tmp = (OnePageView) view;
			((ViewPager) collection).removeView((OnePageView) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((OnePageView) object);
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}
}