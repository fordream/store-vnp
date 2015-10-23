package vn.com.vega.music.view.adapter;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.chacha.R;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.Genre;
import vn.com.vega.music.objects.NewsEntry;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.objects.Video;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * 
 * @author khainv
 * @since 10/2011
 */
public class FeaturedListAdapter extends BaseAdapter {
	private Activity mContext;
	private ImageLoader mImageLoader;
	private LayoutInflater mInflater;
	private List<Object> mListObject = new ArrayList<Object>();

	private boolean isLocalMusic = false;

	public FeaturedListAdapter(Activity context, List<Object> objects,
			boolean _isLocalMusic) {
		mContext = context;
		isLocalMusic = _isLocalMusic;
		mImageLoader = ImageLoader.getInstance(context);
		mInflater = LayoutInflater.from(context);
		if (objects != null) {
			mListObject = objects;
		}
	}

	public void setIsLocalMusic(boolean value) {
		isLocalMusic = value;
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
			convertView = mInflater.inflate(
					R.layout.view_listview_row_feature_list, null);
			holder = new ViewHolder();

			holder.icon = (ImageView) convertView
					.findViewById(R.id.feature_listitem_icon);
			holder.title = (TextView) convertView
					.findViewById(R.id.feature_listitem_title);

			holder.first_info = (TextView) convertView
					.findViewById(R.id.feature_listitem_first_info);
			holder.second_info = (TextView) convertView
					.findViewById(R.id.feature_listitem_second_info);
			holder.divider = (TextView)convertView.findViewById(R.id.feature_listitem_divider);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == 0)
			convertView
					.setBackgroundResource(R.drawable.selector_listview_row_first);
		else if (position == (getCount() - 1))
			convertView
					.setBackgroundResource(R.drawable.selector_listview_row_last);
		else
			convertView
					.setBackgroundResource(R.drawable.selector_listview_row_middle);

		if (obj instanceof Artist) {
			Artist artist = (Artist) obj;
			if (isLocalMusic) {
				holder.icon.setImageResource(R.drawable.ic_artist_default);
				holder.title.setText(artist.name);
				holder.first_info.setText(artist.songCount + " bài hát");
				// holder.divider.setVisibility(TextView.GONE);
			} else {
				holder.title.setText(artist.name);
				holder.first_info.setText(String.format(mContext.getResources()
						.getString(R.string.artist_info), artist.albumCount,
						artist.songCount));
				mImageLoader.DisplayImage(artist.imageUrl, mContext,
						holder.icon, ImageLoader.TYPE_ARTIST);
			}

		} else if (obj instanceof Song) {
			Song song = (Song) obj;

			holder.title.setText(String.format(
					mContext.getString(R.string.format_song_title), position,
					song.title));
			holder.first_info.setText(String.format(
					mContext.getString(R.string.format_by), song.artist_name));
			holder.second_info.setText(String.format(
					mContext.getString(R.string.format_view_count),
					song.viewCount));

		} else if (obj instanceof Video) {

			Video video = (Video) obj;
			holder.title.setText(video.title);

			holder.first_info.setText(formatTime(Integer
					.parseInt(video.duration)));
			if (video.price.length() == 0) {
				holder.second_info.setText(String.format(
						mContext.getString(R.string.format_price), "miễn phí"));
			} else {
				holder.second_info.setText(String.format(
						mContext.getString(R.string.format_price), video.price));
			}

			mImageLoader.DisplayImage(video.thumbnail_large, mContext,
					holder.icon, ImageLoader.TYPE_VIDEO);

		} else if (obj instanceof Album) {
			Album album = (Album) obj;
			holder.title.setText(album.title);
			holder.first_info.setText(String.format(
					mContext.getString(R.string.format_by), album.artistName));
			holder.second_info.setText(String.format(
					mContext.getString(R.string.format_view_count),
					album.viewCount));

			mImageLoader.DisplayImage(album.coverUrl, mContext, holder.icon, ImageLoader.TYPE_ALBUM);
		} else if (obj instanceof NewsEntry) {
			NewsEntry news = (NewsEntry) obj;
			holder.title.setText(news.title);
			holder.first_info.setText(news.datetime);
			holder.second_info.setText(String.format(
					mContext.getString(R.string.format_view_count),
					news.viewCount));

			mImageLoader.DisplayImage(news.thumbUrl, mContext, holder.icon, ImageLoader.TYPE_SONG);
		} else if (obj instanceof Genre) {
			Genre genre = (Genre) obj;
			//holder.icon.setBackgroundResource(R.drawable.ic_song_default);
			holder.icon.setImageResource(R.drawable.ic_song_default);
			if(genre.name.equals(""))
				holder.title.setText("Unknown");
			else
				holder.title.setText(genre.name);
			// holder.title.setPadding(0, 20, 0, 0);
			//LayoutParams param = (RelativeLayout.LayoutParams) holder.title
					//.getLayoutParams();
			//param.addRule(RelativeLayout.CENTER_VERTICAL);
			//holder.title.setLayoutParams(param);
			// holder.info.setText(0 + " bài hát");
			holder.first_info.setText(genre.songCount + " bài hát");
			// holder.divider.setVisibility(TextView.GONE);
		}
		
		if ((obj instanceof Artist && isLocalMusic) || obj instanceof Genre) {
			holder.divider.setVisibility(TextView.GONE);
		} else {
			holder.divider.setVisibility(TextView.VISIBLE);
		}

		return convertView;
	}

	class ViewHolder {
		ImageView icon;
		TextView title;
		TextView first_info;
		TextView second_info;
		TextView divider;
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

	private String format(int value) {
		if (value < 10) {
			return "0" + value;
		} else {
			return String.valueOf(value);
		}
	}

	private String formatTime(int duration) {
		if (duration < 60) {
			return String.valueOf(duration);
		} else if (duration >= 60 && duration < 3600) {
			return duration / 60 + ":" + duration % 60;
		} else {
			return format(duration / 3600) + ":" + format(duration % 3600 / 60)
					+ ":" + format(duration % 3600 % 60);
		}
	}
}
