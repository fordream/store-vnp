package vn.com.vega.music.view.adapter.base;

import java.util.List;

import android.app.Activity;

public interface AutoLoadingListener {
	/**
	 * Get context to create Pending view
	 * 
	 * @return
	 */
	public Activity getContext();

	/**
	 * Get more data from Internet or elsewhere
	 * 
	 * @return
	 */
	public List<Object> getMoreData();
}
