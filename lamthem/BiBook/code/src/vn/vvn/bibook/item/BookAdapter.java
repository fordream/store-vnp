package vn.vvn.bibook.item;

import java.io.File;
import java.util.ArrayList;

import vn.vvn.bibook.R;
import vn.vvn.bibook.lazyadapter.ImageLoader;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Book> mListBook;
	private ImageLoader imageLoader;
	private Handler mHandler;
	private Context mContext;
	Options opts;
float mWidth, mHeight;
	public BookAdapter(Context context, ArrayList<Book> listBook,
			Handler handler) {
		mInflater = LayoutInflater.from(context);
		this.mListBook = listBook;
		mContext = context;
		imageLoader = new ImageLoader(mContext);
		opts = new Options();
		opts.inPreferredConfig = Config.RGB_565;
		mWidth = ((Activity) (mContext)).getWindowManager()
				.getDefaultDisplay().getWidth();
		mHeight = ((Activity) (mContext)).getWindowManager()
				.getDefaultDisplay().getHeight();
		mHandler = handler;
	}

	public int getCount() {
		return mListBook.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListBook.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mListBook.get(position).getBookId();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final int current = position;
		final Book book = mListBook.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_download, null);
			holder.title = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.icon = (ImageView) convertView.findViewById(R.id.ivIcon);
			holder.delete = (ImageView) convertView.findViewById(R.id.ivDel);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		File file = new File(book.getCoverUrl() + ".jpg");
		if (!file.exists()) {
			file = new File(book.getCoverUrl() + ".jpeg");
			if (!file.exists())
				file = new File(book.getCoverUrl() + ".JPG");
			if (!file.exists())
				file = new File(book.getCoverUrl() + ".PNG");
			if (!file.exists())
				file = new File(book.getCoverUrl() + ".png");
			if (!file.exists())
				file = new File(book.getCoverUrl() + ".vvn");
		}
		
//		if(mWidth>mHeight && mWidth >= 960 || mWidth< mHeight && mHeight >= 960)
//			opts.inSampleSize = 6;
		opts.inPreferredConfig = Config.RGB_565;
		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		holder.icon.setImageBitmap(bm);
		if (book.isShowDel())
			holder.delete.setVisibility(View.VISIBLE);
		else
			holder.delete.setVisibility(View.GONE);
		holder.title.setText(book.getTitle());
		holder.delete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.d("haipn", "book Id:" + book.getBookId());
				Message msg = mHandler.obtainMessage();
				msg.arg1 = book.getBookId();
				msg.arg2 = book.getIdCategory();
				msg.what = current;
				mHandler.sendMessage(msg);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		TextView title;
		ImageView icon;
		ImageView delete;
	}

}
