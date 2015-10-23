package com.icts.shortfilmfestival.adapter;

import java.util.List;

import com.icts.shortfilmfestival.entity.PhotosEntity;
import com.vnp.shortfilmfestival.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class PhotosAdapter extends ArrayAdapter<PhotosEntity> {
	private LayoutInflater mInflater;

	public PhotosAdapter(Context context, List<PhotosEntity> objects) {
		super(context, R.layout.photo_item, objects);
		mInflater = LayoutInflater.from(context.getApplicationContext());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("PhotosAdapter", position + "-------------");
		View view = convertView;
		Wrapper wrapper;

		if (view == null) {
			view = mInflater.inflate(R.layout.photo_item, null);
			wrapper = new Wrapper(view);
			view.setTag(wrapper);
		} else {
			wrapper = (Wrapper) view.getTag();
		}
		wrapper.getPhotoSmartImageView().setImageUrl(
				getItem(position).getImgSmall(), 0, R.drawable.loading);

		return view;
	}

	// use an wrapper (or view holder) object to limit calling the
	// findViewById() method, which parses the entire structure of your
	// XML in search for the ID of your view
	private class Wrapper {
		private final View mRoot;
		private SmartImageView mPhotoSmartImageView;

		public Wrapper(View root) {
			mRoot = root;
		}

		public SmartImageView getPhotoSmartImageView() {
			if (mPhotoSmartImageView == null) {
				mPhotoSmartImageView = (SmartImageView) mRoot
						.findViewById(R.id.photo_image_id);
			}
			return mPhotoSmartImageView;
		}
	}

}
