package vn.com.vega.music.view.adapter;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.chacha.R;
import vn.com.vega.music.objects.Song;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MusicQualityListAdapter extends BaseAdapter {
	private List<Song> lSong = new ArrayList<Song>();
	private LayoutInflater mInflater;

	class ViewHolder {
		TextView first_text;
		ImageView image;
		ImageView arrow;

	}

	public MusicQualityListAdapter(Context context, List<Song> lSong) {
		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(context);
		this.lSong = lSong;
	}

	@Override
	public int getCount() {
		return lSong.size();
	}

	@Override
	public Object getItem(int position) {
		return lSong.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.view_listview_row_context_menu, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.common_context_menu_row_thumbnail_img);
			holder.arrow = (ImageView) convertView
					.findViewById(R.id.common_context_menu_row_arrow_img);
			holder.first_text = (TextView) convertView
					.findViewById(R.id.common_context_menu_row_first_txt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.image.setVisibility(ImageView.VISIBLE);
		holder.arrow.setVisibility(ImageView.VISIBLE);
		holder.arrow.setBackgroundResource(R.drawable.ic_listview_arrow);

		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp2.rightMargin = 20;
		lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp2.addRule(RelativeLayout.CENTER_VERTICAL);
		holder.arrow.setLayoutParams(lp2);

		Song song = lSong.get(position);

		holder.first_text.setText(song.album_title);
		holder.image.setBackgroundResource(R.drawable.ic_tranfer);
		
		return convertView;
	}
}