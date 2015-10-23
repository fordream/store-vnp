package vn.com.vega.music.view.adapter;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.chacha.R;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.objects.Playlist;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeaturePlaylistAdapter extends BaseAdapter {

	private Activity mContext;
	private ImageLoader mImageLoader;
	private LayoutInflater mInflater;
	private List<Object> mListObject = new ArrayList<Object>();

	public FeaturePlaylistAdapter(Activity context, List<Object> objects, boolean _isLocalMusic) {
		mContext = context;
		mImageLoader = ImageLoader.getInstance(context);
		mInflater = LayoutInflater.from(context);
		if (objects != null) {
			mListObject = objects;
		}
	}

	@Override
	public int getCount() {
		return mListObject.size();
	}

	@Override
	public Object getItem(int position) {
		return mListObject.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		Object obj = mListObject.get(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.view_listview_feature_playlist, null);

			holder = new ViewHolder();
			holder.playlistThumbs = (ImageView) convertView.findViewById(R.id.feature_playlist_thumb);

			holder.thumb1 = (ImageView) convertView.findViewById(R.id.feature_playlist_thumb1);
			holder.thumb2 = (ImageView) convertView.findViewById(R.id.feature_playlist_thumb2);
			holder.thumb3 = (ImageView) convertView.findViewById(R.id.feature_playlist_thumb3);
			holder.thumb4 = (ImageView) convertView.findViewById(R.id.feature_playlist_thumb4);
			holder.info_ex = (TextView) convertView.findViewById(R.id.feature_playlist_info_extra_txt);
			holder.title = (TextView) convertView.findViewById(R.id.feature_playlist_title_txt);
			holder.info = (TextView) convertView.findViewById(R.id.feature_playlist_info_txt);
			holder.songCount = (TextView) convertView.findViewById(R.id.feature_playlist_song_count_txt);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == 0)
			convertView.setBackgroundResource(R.drawable.selector_listview_online_playlist_row_first);
		else if (position == (getCount() - 1))
			convertView.setBackgroundResource(R.drawable.selector_listview_online_playlist_row_last);
		else
			convertView.setBackgroundResource(R.drawable.selector_listview_online_playlist_row_middle);

		if (obj instanceof Playlist) {
			Playlist playlist = (Playlist) obj;
			holder.title.setText(playlist.title);
			if(playlist.userName != null && !playlist.equals(""))
				holder.info.setText("bởi " + playlist.userName);
			else
				holder.info.setText("Unknown");
			holder.info_ex.setText(" - " + playlist.viewCount + " lượt xem");
			holder.songCount.setText(playlist.total_song + " bài hát");
			mImageLoader.DisplayImage(playlist.userThumb, mContext, holder.playlistThumbs, ImageLoader.TYPE_PLAYLIST);
			setThumbsAdapter(playlist.thumbnails, holder);
		}

		return convertView;
	}

	class ViewHolder {
		ImageView playlistThumbs;
		ImageView thumb1, thumb2, thumb3, thumb4;
		TextView title;
		TextView info;
		TextView info_ex;
		TextView songCount;
	}

	private void setThumbsAdapter(ArrayList<String> thumbList, ViewHolder holder) {
		
		String thumb_first = "";
		String thumb_second = "";
		String thumb_third = "";
		String thumb_four = "";
		
		if(thumbList.get(0) != null)
			thumb_first = thumbList.get(0);
		if(thumbList.get(1) != null)
			thumb_second = thumbList.get(1);
		if(thumbList.get(2) != null)
			thumb_third = thumbList.get(2);
		if(thumbList.get(3) != null)
			thumb_four = thumbList.get(3);
		
		mImageLoader.DisplayImage(thumb_four, mContext, holder.thumb4, ImageLoader.TYPE_ARTIST);
		mImageLoader.DisplayImage(thumb_third, mContext, holder.thumb3, ImageLoader.TYPE_ARTIST);
		mImageLoader.DisplayImage(thumb_second, mContext, holder.thumb2, ImageLoader.TYPE_ARTIST);
		mImageLoader.DisplayImage(thumb_first, mContext, holder.thumb1, ImageLoader.TYPE_ARTIST);

		// if (thumbList == null) {
		// return;
		// }
		//
		// int thumbSize = thumbList.size();

		// if (thumbSize == 4) {
		// mImageLoader
		// .DisplayImage(thumbList.get(3), mContext, holder.thumb4,
		// ImageLoader.TYPE_PLAYLIST);
		// mImageLoader
		// .DisplayImage(thumbList.get(2), mContext, holder.thumb3,
		// ImageLoader.TYPE_PLAYLIST);
		// mImageLoader
		// .DisplayImage(thumbList.get(1), mContext, holder.thumb2,
		// ImageLoader.TYPE_PLAYLIST);
		// mImageLoader
		// .DisplayImage(thumbList.get(0), mContext, holder.thumb1,
		// ImageLoader.TYPE_PLAYLIST);
		// } else if (thumbSize == 3) {
		// holder.thumb4.setImageDrawable(null);
		// mImageLoader
		// .DisplayImage(thumbList.get(2), mContext, holder.thumb3,
		// ImageLoader.TYPE_PLAYLIST);
		// mImageLoader
		// .DisplayImage(thumbList.get(1), mContext, holder.thumb2,
		// ImageLoader.TYPE_PLAYLIST);
		// mImageLoader
		// .DisplayImage(thumbList.get(0), mContext, holder.thumb1,
		// ImageLoader.TYPE_PLAYLIST);
		// } else if (thumbSize == 2) {
		// holder.thumb4.setImageDrawable(null);
		// holder.thumb3.setImageDrawable(null);
		// mImageLoader
		// .DisplayImage(thumbList.get(1), mContext, holder.thumb2,
		// ImageLoader.TYPE_PLAYLIST);
		// mImageLoader
		// .DisplayImage(thumbList.get(0), mContext, holder.thumb1,
		// ImageLoader.TYPE_PLAYLIST);
		// } else if (thumbSize == 1) {
		// holder.thumb4.setImageDrawable(null);
		// holder.thumb3.setImageDrawable(null);
		// holder.thumb2.setImageDrawable(null);
		// mImageLoader
		// .DisplayImage(thumbList.get(0), mContext, holder.thumb1,
		// ImageLoader.TYPE_PLAYLIST);
		// }
	}

	public void notifyListObjectChanged(List<Object> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			for (Object obj : list) {
				mListObject.add(obj);
			}
			notifyDataSetChanged();
		}
	}

	public List<Object> getListObject() {
		// TODO Auto-generated method stub
		return mListObject;
	}

}
