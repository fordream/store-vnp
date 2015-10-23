package vn.vvn.bibook.item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vn.vvn.bibook.R;
import vn.vvn.bibook.lazyadapter.ImageLoader;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GripDownloadAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Book> mListBook;
	private ImageLoader imageLoader;
	private Context mContext;
	private int mHeightRow;
	private Options opts;

	public GripDownloadAdapter(Context context, ArrayList<Book> listBook,
			int height) {
		mInflater = LayoutInflater.from(context);
		this.mListBook = listBook;
		mContext = context;
		imageLoader = new ImageLoader(mContext);
		mHeightRow = height;
		opts = new Options();
		opts.inPreferredConfig = Config.RGB_565;
	}

	public int getCount() {
		return mListBook.size();
	}

	public Object getItem(int position) {
		return mListBook.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mListBook.get(position).getBookId();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		Book book = mListBook.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_download, null);

			// LinearLayout.LayoutParams lp = new
			// LinearLayout.LayoutParams(mHeightRow, mHeightRow);
			// convertView.setLayoutParams(lp);
			// holder.llItem.setLayoutParams(new
			// LinearLayout.LayoutParams(mHeightRow, mHeightRow));
			holder.title = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.icon = (ImageView) convertView.findViewById(R.id.ivIcon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (book.getCoverUrl().contains("http")) {
			imageLoader.DisplayImage(book.getCoverUrl(), (Activity) mContext,
					holder.icon);
		} else {
			File file = new File(book.getCoverUrl() + ".jpg");
			if (!file.exists()) {
				file = new File(book.getCoverUrl() + ".jpeg");
				if (!file.exists())
					file = new File(book.getCoverUrl() + ".JPG");
				if (!file.exists())
					file = new File(book.getCoverUrl() + ".PNG");
				if (!file.exists())
					file = new File(book.getCoverUrl() + ".png");
			}
			Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
			holder.icon.setImageBitmap(bm);
		}
		holder.title.setText(book.getTitle());
		return convertView;
	}

	private class ViewHolder {
		TextView title;
		ImageView icon;
	}
}
