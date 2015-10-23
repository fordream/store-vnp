package vn.vvn.bibook.item;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import vn.vvn.bibook.lazyadapter.MemoryCache;
import vn.vvn.bibook.util.NewGallery;
import vn.vvn.bibook.util.Page;
import vn.vvn.bibook.util.Polygon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class PageAdapter extends ArrayAdapter<Page> {

	private LayoutInflater mInflater;
	private int mWidth;
	private int mHeight;
	private Context mContext;
	MemoryCache cache;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public PageAdapter(Context context, List<Page> objects, int w, int h) {
		super(context, 0, objects);
		Activity activity = (Activity) getContext();
		mInflater = activity.getLayoutInflater();
		mContext = context;
		mWidth = w;
		mHeight = h;
		cache = new MemoryCache();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Page page = getItem(position);
		Bitmap bmp;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = new ImageView(mContext);
			convertView.setTag(holder);
			bmp = decodeFile(new File(page.getPathImage()));
			cache.put(page.getPathImage(), bmp);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
			
		}
		bmp = cache.get(page.getPathImage());
		convertView.setBackgroundDrawable(new BitmapDrawable(bmp));
		Gallery.LayoutParams lp = new Gallery.LayoutParams(mWidth, mHeight);
		convertView.setLayoutParams(lp);
		return convertView;
	}

	

	class ViewHolder {
		LinearLayout PageView;
	}
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = mWidth;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inPreferredConfig = Config.RGB_565;
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

}