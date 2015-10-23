package vn.vvn.bibook.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import vn.vvn.bibook.R;
import vn.vvn.bibook.item.Book;
import vn.vvn.bibook.item.BookAdapter;
import vn.vvn.bibook.item.MyGridView;
import vn.vvn.bibook.item.NewScrollView;
import vn.vvn.bibook.item.ScrollViewListener;
import vn.vvn.bibook.pagecurlgallery.ViewBook2;
import vn.vvn.bibook.util.ParseXmlHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ListBookInCategory extends Activity implements ScrollViewListener {

	public static final String BOOK_ID = "book id";
	public static final String CATEGORY_ID = "category id";
	private static final int DIALOG_CONFIRM_DEL_BOOK = 0;
	private static final String TAG = "ListBookInCategory";

	Button mBtnHome;
	Button mBtnNext;
	Button mBtnPrev;
	MyGridView[] mGvs;

	ToggleButton mBtnEdit;
	TextView[] mTvTitles;

	BookAdapter[] mAdapters;

	ArrayList<Book>[] listBooks;

	LinearLayout llTitle;

	NewScrollView mSvBookshelf;

	private int mIdCategoryDel;
	private int mIdBookDel;

	BookAdapter mAdapter;
	Intent mIntent;
	private int idCategory;
	private int heightRow;
	SharedPreferences sharePref;
	ArrayList<String> listCategories;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookshelf);
		sharePref = getSharedPreferences(Splash.SHARE_PREF,
				Context.MODE_PRIVATE);
		Log.d(TAG, "onCreate");
		mIntent = getIntent();
		idCategory = mIntent.getIntExtra(CATEGORY_ID, 0);
		initComponent();
		initContent();
		setEvent();
	}

	private void initComponent() {
		mIdCategoryDel = -1;
		mIdBookDel = -1;
		parseCategories();
		mGvs = new MyGridView[6];
		mTvTitles = new TextView[6];
		mAdapters = new BookAdapter[6];
		listBooks = (ArrayList<Book>[]) new ArrayList[6];
		for (int i = 0; i < listBooks.length; i++) {
			listBooks[i] = new ArrayList<Book>();
		}
		mGvs[0] = (MyGridView) findViewById(R.id.gvBehoctoan);
		mGvs[1] = (MyGridView) findViewById(R.id.gvThiennhien);
		mGvs[2] = (MyGridView) findViewById(R.id.gvAnhVan);
		mGvs[3] = (MyGridView) findViewById(R.id.gvKechuyen);
		mGvs[4] = (MyGridView) findViewById(R.id.gvDongvat);
		mGvs[5] = (MyGridView) findViewById(R.id.gvSachkhac);

		mSvBookshelf = (NewScrollView) findViewById(R.id.svBookshelf);
		mSvBookshelf.setScrollViewListener(this);

		mBtnHome = (Button) findViewById(R.id.btnHome);
		mBtnEdit = (ToggleButton) findViewById(R.id.btnEdit);
		// mBtnEdit.setVisibility(View.INVISIBLE);
		llTitle = (LinearLayout) findViewById(R.id.llTitle);

		mTvTitles[0] = (TextView) findViewById(R.id.tvCategory);

		mTvTitles[1] = (TextView) findViewById(R.id.tvCategoryThiennhien);
		mTvTitles[2] = (TextView) findViewById(R.id.tvCategoryAnhVan);
		mTvTitles[3] = (TextView) findViewById(R.id.tvCategoryKechuyen);
		mTvTitles[4] = (TextView) findViewById(R.id.tvCategoryDongvat);
		mTvTitles[5] = (TextView) findViewById(R.id.tvCategorySachkhac);

	}

	private void parseCategories() {
		listCategories = new ArrayList<String>();
		String cats = sharePref.getString(Splash.CATEGORIES, getString(R.string.categories));
		String[] item = cats.split(",");
		for (int i = 0; i < item.length; i++) {
			listCategories.add(item[i]);
		}
	}

	private void initContent() {
		initBooks();
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inPreferredConfig = Config.RGB_565;
		Bitmap background = BitmapFactory.decodeResource(getResources(),
				R.drawable.gia_sach, o);
		heightRow = background.getHeight();
		background.recycle();

		for (int i = 0; i < 6; i++) {
			mAdapters[i] = new BookAdapter(ListBookInCategory.this,
					listBooks[i], handlerDel);
			mGvs[i].setAdapter(mAdapters[i]);
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mGvs[i]
					.getLayoutParams();
			lp.height = measureRealHeight(mGvs[i]);
			mGvs[i].setLayoutParams(lp);
			mTvTitles[i].setText(listCategories.get(i));
		}
	}

	final Handler handlerDel = new Handler() {
		public void handleMessage(Message msg) {
			mIdBookDel = msg.arg1;
			mIdCategoryDel = msg.arg2;
			mPosition = msg.what;
			Log.d("haipn", "mPosition:" + mPosition);
			showDialog(DIALOG_CONFIRM_DEL_BOOK);
		}
	};
	protected int mPosition;

	private void setEvent() {
		for (int i = 0; i < 6; i++) {
			final int id = i;
			mGvs[i].setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
//					Intent i = new Intent(ListBookInCategory.this,
//							ViewBook.class);
					Intent i = new Intent(ListBookInCategory.this,
							ViewBook2.class);
					i.putExtra(BOOK_ID, listBooks[id].get(arg2).getBookId());
					i.putExtra(CATEGORY_ID, id + 1);
					startActivity(i);
				}
			});
		}

		mBtnHome.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		mBtnEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < listBooks[i].size(); j++) {
						if (isChecked)
							listBooks[i].get(j).setShowDel(true);
						else
							listBooks[i].get(j).setShowDel(false);
					}
					mAdapters[i].notifyDataSetChanged();
				}

			}
		});
	}

	public void initBooks() {
		for (int i = 0; i < 6; i++) {
			File bookFolder = new File(
					Environment.getExternalStorageDirectory()
							+ getString(R.string.path_to_category, i + 1));
			if (bookFolder.isDirectory() && bookFolder.exists()) {
				File[] fol = bookFolder.listFiles();
				Book book;
				for (File fo : fol) {
					book = readInfoFromXml(new File(fo, "info.xml"));
					if (book == null)
						continue;
					float mWidth = getWindowManager().getDefaultDisplay().getWidth();
					float mHeight = getWindowManager().getDefaultDisplay().getHeight();
//					if(mWidth>mHeight && mWidth >= 960 || mWidth< mHeight && mHeight >= 960){
//						book.setCoverUrl(new File(fo, "Pictures/Cover").getAbsolutePath());
//					}else{
						book.setCoverUrl(new File(fo, "Pictures/CoverIcon").getAbsolutePath());
//					}
							
					book.setBookId(Integer.valueOf(fo.getName()));
					book.setIdCategory(i + 1);
					listBooks[i].add(book);
				}
			}
		}

	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
	}
	private Book readInfoFromXml(File xml) {
		ParseXmlHandler mContentHandler = new ParseXmlHandler();
		InputStream is;
		try {
			is = new FileInputStream(xml);
			parseInputStream(is, mContentHandler);
		} catch (ParseException e) {

			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {

			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		return mContentHandler.getParseData();
	}

	public void parseInputStream(InputStream is, ContentHandler mContentHandler)
			throws IOException, SAXException, ConnectException,
			ParserConfigurationException {

		/* Get a SAXParser from the SAXPArserFactory. */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		/* Get the XMLReader of the SAXParser we created. */
		XMLReader xr = sp.getXMLReader();
		xr.setContentHandler(mContentHandler);
		InputSource input = new InputSource(is);
		// input.setEncoding("UTF-8");
		xr.parse(input);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_CONFIRM_DEL_BOOK:
			AlertDialog.Builder builderMessage = new AlertDialog.Builder(this);
			builderMessage
					.setMessage(getString(R.string.message_confirm_del))
					.setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							})
					.setPositiveButton(R.string.OK,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									delBook(mIdCategoryDel, mIdBookDel);
									listBooks[mIdCategoryDel - 1]
											.remove(mPosition);
									mAdapters[mIdCategoryDel - 1]
											.notifyDataSetChanged();
									dialog.dismiss();
								}
							});
			AlertDialog messageDialogWifi = builderMessage.create();
			return messageDialogWifi;

		}
		return super.onCreateDialog(id);
	}

	public void delBook(int idCat, int idBook) {
		Log.d("DEL FOLDER", "DEL FOLDER ---------------- DEL FOLDER");
		File catFolder = new File(Environment.getExternalStorageDirectory()
				+ getString(R.string.path_to_category, idCat));
		if (!catFolder.isDirectory() || !catFolder.exists())
			return;
		File bookFolder = new File(Environment.getExternalStorageDirectory()
				+ getString(R.string.path_to_book, idCat, idBook));
		if (!bookFolder.isDirectory() || !bookFolder.exists())
			return;
		// File[] fol = bookFolder.listFiles();
		// for (File fo : fol) {
		//
		// File[] files = fo.listFiles();
		// for (File f : files) {
		// f.delete();
		// }
		// fo.delete();
		// }
		delFolder(bookFolder);

	}

	public void delFolder(File folder) {
		File[] files = folder.listFiles();
		for (File f : files) {
			if (f == null)
				continue;
			if (f.isDirectory()) {
				delFolder(f);
				f.delete();
			} else
				f.delete();

		}
		folder.delete();
	}

	public void onScrollChanged(int y) {
		if (!mSvBookshelf.ismIsBottom())
			resetHeight();

		for (int i = 0; i < 6; i++) {
			if (y >= mGvs[i].getFrom() && y < mGvs[i].getTo()) {
				mTvTitles[0].setText(listCategories.get(i));
				break;
			}
		}
	}

	private int measureRealHeight(MyGridView myGridView) {
		int picsCount = myGridView.getCount();

		int columnsCount = 4;

		int rowsCount = picsCount / columnsCount
				+ (picsCount % columnsCount == 0 ? 0 : 1);
		int ret = (int) (heightRow * rowsCount);
		if (ret < heightRow * 3) {
			ret = heightRow * 3;
		}
		return ret;
	}

	public void resetHeight() {
		int barHeight = llTitle.getHeight();
		int from = 0;
		int to = 0;
		for (int i = 0; i < 6; i++) {
			mGvs[i].setFrom(from);
			to = from + mGvs[i].getHeight();
			mGvs[i].setTo(to);
			from = to + barHeight;
		}
	}
}
