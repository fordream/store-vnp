package vn.com.vega.music.view.adapter.base;

import java.util.List;

import vn.com.vega.chacha.R;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FeaturedListAdapter;
import vn.com.vega.music.view.adapter.SongListAdapter;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.commonsware.cwac.endless.EndlessAdapter;

public class AutoLoadingAdapter extends EndlessAdapter implements Const {
	private Activity mContext;
	private AutoLoadingListener mListener;
	public List<Object> newObjs;
	public int mMaxItemCount = LIST_ITEM_LIMIT;

	public AutoLoadingAdapter(ListAdapter wrapped, AutoLoadingListener listener) {
		super(wrapped);
		mListener = listener;
		mContext = listener.getContext();
	}

	public AutoLoadingAdapter(ListAdapter wrapped, AutoLoadingListener listener, int max) {
		this(wrapped, listener);
		if (this.mMaxItemCount < max)
			this.mMaxItemCount = max;
	}

	@Override
	protected View getPendingView(ViewGroup parent) {
		return LayoutInflater.from(mContext).inflate(R.layout.view_loading_indicator_item, null);
	}

	@Override
	protected boolean cacheInBackground() throws Exception {
		newObjs = mListener.getMoreData();
		int count = getWrappedAdapter().getCount();
		if (newObjs.size() == 0 || count + newObjs.size() >= mMaxItemCount)
			return false;
		return true;
	}

	@Override
	protected void appendCachedData() {
		ListAdapter adapter = getWrappedAdapter();
		Log.e("appendCachedData", "Object size: " + newObjs.size());
		if (adapter instanceof FeaturedListAdapter) {
			FeaturedListAdapter featureAdapter = (FeaturedListAdapter) adapter;
			featureAdapter.notifyListObjectChanged(newObjs);
		} else if (adapter instanceof SongListAdapter) {
			SongListAdapter songListAdapter = (SongListAdapter) adapter;
			songListAdapter.notifyListObjectChanged(newObjs);
		}
	}

}