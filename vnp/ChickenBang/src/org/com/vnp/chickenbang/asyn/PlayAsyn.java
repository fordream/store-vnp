package org.com.vnp.chickenbang.asyn;

import org.com.cnc.common.adnroid.activity.CommonActivity;
import org.com.cnc.common.android.asyn.CommonAsyncTask;
import org.com.vnp.chickenbang.views.BitmapStore;
import org.com.vnp.chickenbang.views.DrawView;

public class PlayAsyn extends CommonAsyncTask {
	private DrawView drawView;

	public PlayAsyn(CommonActivity activity, DrawView drawView) {
		super(activity);
		this.drawView = drawView;
	}

	protected String doInBackground(String... arg0) {
		while (!isClose()) {
			if (drawView.getlImageViews().size() <= 10) {
				BitmapStore bitmapStore = new BitmapStore();
				bitmapStore.create(getActivity(), drawView.getWidth(),
						drawView.getHeight());
				drawView.getlImageViews().add(bitmapStore);
			}
			for (int i = 0; i < drawView.getlImageViews().size(); i++) {
				try {
					BitmapStore imageView = drawView.getlImageViews().get(i);
					imageView.update();
					if(( imageView.check(drawView.getWidth(), drawView.getHeight()))){
						drawView.getlImageViews().remove(imageView);
					}
				} catch (Exception e) {
				}
			}
			handler.post(new Runnable() {
				public void run() {
					drawView.invalidate();
				}
			});
		}
		return super.doInBackground(arg0);
	}
}
