package vn.vvn.bibook.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.vvn.bibook.R;
import vn.vvn.bibook.item.Book;
import vn.vvn.bibook.item.GripDownloadAdapter;
import vn.vvn.bibook.item.MyGridView;
import vn.vvn.bibook.item.NewScrollView;
import vn.vvn.bibook.item.ScrollViewListener;
import vn.vvn.bibook.util.Parameter;
import vn.vvn.bibook.util.RestClient;
import vn.vvn.bibook.util.ThreadDownload;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListDownload extends Activity implements ScrollViewListener {

	private static final String ZIP_FOLDER_NAME = "zip_folder_name";
	private static final String CATEGORY_IDS = "category_ids";
	private static final String CATEGORIES = "categories";
	private static final String NUMBER_LIKE = "number_likes";
	private static final String NUMBER_COMMENT = "number_comments";
	private static final String COVER_URL = "cover_url";
	private static final String DESCRIPTION = "description";
	private static final String TITLE = "title";
	private static final String ID = "id";
	private static final String AUTOSLIDE = "autoslide";
	protected static final int PROGRESS_DIALOG = 0;
	protected static final int DIALOG_CONFIRM_DOWNLOAD = 1;
	protected static final int DIALOG_BOOK_EXIST = 2;
	protected static final int DIALOG_DOWNLOAD_FAIL = 3;
	protected static final int DIALOG_NO_INTERNET = 4;
	private static final String TAG = "ListDownload";
	private static final int COLUMN_NUMBER = 4;
	private static final int CATEGORY_NUMBER = 6;

	MyGridView[] mGvs;

	Button mBtnHome;
	Button mBtnEdit;
	TextView[] mTvTitles;

	GripDownloadAdapter[] mAdapters;

	ArrayList<Book>[] listBooks;
	ArrayList<Book>[] tempListBooks;
	LinearLayout[] llTitles;
	private int mIdCategoryClick;
	private int mIdClick;

	ProgressThread progressThread;
	ProgressDialog progressDialog;

	// ThreadDownload downloadThread;

	NewScrollView mSvBookshelf;
	protected int heightRow;
	private SharedPreferences sharePref;
	private ArrayList<String> listCategories;
	protected Book book;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookshelf);
		Log.d("ListDownload", "onCreate");
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initComponent();
		initContent();
		setEvent();
	}

	private void parseCategories() {
		listCategories = new ArrayList<String>();
		String cats = sharePref.getString(Splash.CATEGORIES, "null");
		if (cats == null) {
			cats = getString(R.string.categories);
		}
		String[] item = cats.split(",");
		for (int i = 0; i < item.length; i++) {
			listCategories.add(item[i]);
		}
	}

	private void initComponent() {
		sharePref = getSharedPreferences(Splash.SHARE_PREF,
				Context.MODE_PRIVATE);
		parseCategories();
		mGvs = new MyGridView[CATEGORY_NUMBER];
		mTvTitles = new TextView[CATEGORY_NUMBER];
		mAdapters = new GripDownloadAdapter[CATEGORY_NUMBER];
		listBooks = (ArrayList<Book>[]) new ArrayList[CATEGORY_NUMBER];

		for (int i = 0; i < listBooks.length; i++) {
			listBooks[i] = new ArrayList<Book>();
		}
		tempListBooks = (ArrayList<Book>[]) new ArrayList[CATEGORY_NUMBER];
		for (int i = 0; i < tempListBooks.length; i++) {
			tempListBooks[i] = new ArrayList<Book>();
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
		mBtnEdit = (Button) findViewById(R.id.btnEdit);
		mBtnEdit.setVisibility(View.INVISIBLE);
		llTitles = new LinearLayout[CATEGORY_NUMBER];
		llTitles[0] = (LinearLayout) findViewById(R.id.llTitle);
		llTitles[1] = (LinearLayout) findViewById(R.id.llTitleThiennhien);
		llTitles[2] = (LinearLayout) findViewById(R.id.llTitleAnhvan);
		llTitles[3] = (LinearLayout) findViewById(R.id.llTitleKechuyen);
		llTitles[4] = (LinearLayout) findViewById(R.id.llTitleDongvat);
		llTitles[5] = (LinearLayout) findViewById(R.id.llTitleSachkhac);

		mTvTitles[0] = (TextView) findViewById(R.id.tvCategory);

		mTvTitles[1] = (TextView) findViewById(R.id.tvCategoryThiennhien);
		mTvTitles[2] = (TextView) findViewById(R.id.tvCategoryAnhVan);
		mTvTitles[3] = (TextView) findViewById(R.id.tvCategoryKechuyen);
		mTvTitles[4] = (TextView) findViewById(R.id.tvCategoryDongvat);
		mTvTitles[5] = (TextView) findViewById(R.id.tvCategorySachkhac);

	}

	private void initContent() {
		// listBooksAnhvan = new ArrayList<Book>();
		// listBooksBehoctoan = new ArrayList<Book>();
		// listBooksDongvat = new ArrayList<Book>();
		// listBooksKechuyen = new ArrayList<Book>();
		// listBooksSachkhac = new ArrayList<Book>();
		// listBooksThiennhien = new ArrayList<Book>();

		progressThread = new ProgressThread(handler);
		progressThread.start();
		showDialog(PROGRESS_DIALOG);
	}

	private void setEvent() {
		for (int i = 0; i < CATEGORY_NUMBER; i++) {
			final int id = i;
			mGvs[i].setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					mIdCategoryClick = id;
					mIdClick = arg2;
					if (Parameter.threaddownload == null
							|| (Parameter.threaddownload == null && Parameter.threaddownload
									.getmState() != ThreadDownload.STATE_RUNNING)) {
						showDialog(DIALOG_CONFIRM_DOWNLOAD);
					} else {
						//showDialog(DIALOG_BOOK_EXIST);
					}
				}
			});
			llTitles[i].setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					int size = listBooks[id].size() < mGvs[id].getCount()
							+ COLUMN_NUMBER * 2 ? listBooks[id].size()
							: mGvs[id].getCount() + COLUMN_NUMBER * 2;
					for (int j = mGvs[id].getCount(); j < size; j++) {
						tempListBooks[id].add(listBooks[id].get(j));
					}
					mAdapters[id].notifyDataSetChanged();
					LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mGvs[id]
							.getLayoutParams();
					lp.height = measureRealHeight(mGvs[id]);
					mGvs[id].setLayoutParams(lp);
				}
			});
		}

		mBtnHome.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG:
			progressDialog = new ProgressDialog(ListDownload.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Loading...");
			return progressDialog;
		case DIALOG_NO_INTERNET:
			AlertDialog.Builder builderMessageInternet = new AlertDialog.Builder(
					this);
			builderMessageInternet.setMessage(
					getString(R.string.message_no_internet)).setPositiveButton(
					getString(R.string.OK),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							finish();
						}
					});
			AlertDialog messageDialogWifi = builderMessageInternet.create();
			return messageDialogWifi;
		case DIALOG_BOOK_EXIST:
			AlertDialog.Builder builderMessage = new AlertDialog.Builder(this);
			builderMessage.setMessage(getString(R.string.message_book_exist))
					.setPositiveButton(getString(R.string.OK),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});
			AlertDialog messageDialogExist = builderMessage.create();
			return messageDialogExist;
		case DIALOG_DOWNLOAD_FAIL:
			AlertDialog.Builder builderMessageFail = new AlertDialog.Builder(
					this);
			builderMessageFail.setMessage(
					getString(R.string.message_download_failed))
					.setPositiveButton(getString(R.string.OK),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});
			AlertDialog messageDialogFail = builderMessageFail.create();
			return messageDialogFail;
		case DIALOG_CONFIRM_DOWNLOAD:
			// Log.d("haipn", "idclick : " + mIdClick);
			// Log.d("haipn", "idcategory: " + mIdCategoryClick);
			AlertDialog.Builder builderMessageDelAllPicture = new AlertDialog.Builder(
					this);
			builderMessageDelAllPicture
					.setIcon(R.drawable.ic_launcher)
					.setTitle(
							listBooks[mIdCategoryClick].get(mIdClick)
									.getDescription())
					.setMessage(
							listBooks[mIdCategoryClick].get(mIdClick)
									.getCategories())
					.setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									removeDialog(DIALOG_CONFIRM_DOWNLOAD);
								}
							})
					.setPositiveButton(R.string.download,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									String sdcard = Environment
											.getExternalStorageDirectory()
											.toString();
									boolean existBook = true;
									book = listBooks[mIdCategoryClick]
											.get(mIdClick);
									for (int i = 0; i < book
											.getListCategoryId().size(); i++) {
										String path = sdcard
												+ getString(
														R.string.path_to_book,
														book.getListCategoryId()
																.get(i), book
																.getBookId());
										File file = new File(path);
										Log.e("test", "path " + path);
										if (!file.exists()) {
											existBook = false;
											break;
										}
									}
									if (existBook) {
										showDialog(DIALOG_BOOK_EXIST);
										removeDialog(DIALOG_CONFIRM_DOWNLOAD);
									} else {

										showDialog(PROGRESS_DIALOG);
										Parameter.threaddownload = new ThreadDownload(
												ListDownload.this,
												handlerDownload,
												listBooks[mIdCategoryClick]
														.get(mIdClick));
										Parameter.threaddownload.start();
										// dialog.dismiss();
										removeDialog(DIALOG_CONFIRM_DOWNLOAD);

									}
								}
							});

			AlertDialog messageDialogDelAllPicture = builderMessageDelAllPicture
					.create();
			return messageDialogDelAllPicture;
		default:
			return null;
		}
	}

	public int getListBook() {

		JSONArray books = RestClient.connect(getString(R.string.url_view_all));
		try {
			JSONObject object;
			Book book;
			for (int i = 0; i < books.length(); i++) {

				object = books.getJSONObject(i);
				book = new Book();
				book.setBookId(object.getInt(ID));
				book.setTitle(object.getString(TITLE));
				book.setDescription(object.getString(DESCRIPTION));
				book.setAutoSlide(object.getInt(AUTOSLIDE) == 1);
				if (object.getString(NUMBER_COMMENT).equals("null")) {
					book.setNumber_comments(-1);
				} else {
					book.setNumber_comments(object.getInt(NUMBER_COMMENT));
				}
				if (object.getString(NUMBER_LIKE).equals("null")) {
					book.setNumber_likes(-1);
				} else {
					book.setNumber_likes(object.getInt(NUMBER_LIKE));
				}

				book.setCategories(object.getString(CATEGORIES));
				book.setCategoryIds(object.getString(CATEGORY_IDS));
				book.setZipFolderName(object.getString(ZIP_FOLDER_NAME));
				for (int j = 0; j < book.getListCategoryId().size(); j++) {

					int id = book.getListCategoryId().get(j) - 1;
					File bookFolder = new File(
							Environment.getExternalStorageDirectory()
									+ getString(R.string.path_to_book, id + 1,
											book.getBookId()));
					if (bookFolder.exists())
						book.setCoverUrl(new File(bookFolder,
								"Pictures/CoverIcon").getAbsolutePath());
					else
						book.setCoverUrl(object.getString(COVER_URL));
					listBooks[id].add(book);
					if (mGvs[id].getTitleCategory() == null) {
						mGvs[id].setTitleCategory(book.getListCategory().get(j));
					}
				}

			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			return -2;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	// Define the Handler that receives messages from the thread and update the
	// progress
	final Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			int total = msg.arg1;
			if (total >= 0) {
				removeDialog(PROGRESS_DIALOG);
				progressThread.setState(ProgressThread.STATE_DONE);
				progressThread.interrupt();

				BitmapFactory.Options o = new BitmapFactory.Options();
				o.inPreferredConfig = Config.RGB_565;
				Bitmap background = BitmapFactory.decodeResource(
						getResources(), R.drawable.gia_sach, o);
				heightRow = background.getHeight();
				background.recycle();
				for (int i = 0; i < CATEGORY_NUMBER; i++) {
					int size = listBooks[i].size()/* < COLUMN_NUMBER * 2 ? listBooks[i]
							.size() : COLUMN_NUMBER * 2*/;
					Log.d("haipn",
							"listbook size " + i + " " + listBooks[i].size());
					for (int j = 0; j < size; j++) {
						tempListBooks[i].add(listBooks[i].get(j));
					}
					mAdapters[i] = new GripDownloadAdapter(ListDownload.this,
							tempListBooks[i], heightRow);
					mGvs[i].setAdapter(mAdapters[i]);
					LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mGvs[i]
							.getLayoutParams();
					lp.height = measureRealHeight(mGvs[i]);
					mGvs[i].setLayoutParams(lp);
					try{
					mTvTitles[i].setText(listCategories.get(i));
					}catch (IndexOutOfBoundsException e) {
						// TODO: handle exception
						parseCategories();
						mTvTitles[i].setText(listCategories.get(i));
					}
				}
			} else {
				removeDialog(PROGRESS_DIALOG);
				showDialog(DIALOG_NO_INTERNET);
				progressThread.setState(ProgressThread.STATE_DONE);
				progressThread.interrupt();
			}
		}
	};

	public void resetHeight() {
		int barHeight = llTitles[0].getHeight();
		int from = 0;
		int to = 0;
		for (int i = 0; i < CATEGORY_NUMBER; i++) {
			mGvs[i].setFrom(from);
			to = from + mGvs[i].getHeight();
			mGvs[i].setTo(to);
			from = to + barHeight;
		}
	}

	protected void onResume() {
		Log.d("ListDownload", "onResume");
		// if (Parameter.threaddownload != null
		// && downloadThread.getmState() == ThreadDownload.STATE_DONE) {
		// try {
		// removeDialog(PROGRESS_DIALOG);
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// }
		// }

		super.onResume();

	};

	@Override
	protected void onPause() {
		Log.d("ListDownload", "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.d("ListDownload", "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}

	private int measureRealHeight(MyGridView myGridView) {
		int picsCount = myGridView.getCount();
		int columnsCount = COLUMN_NUMBER;

		int rowsCount = picsCount / columnsCount
				+ (picsCount % columnsCount == 0 ? 0 : 1);
		int ret = (int) (heightRow * rowsCount);
		if (ret < heightRow * 2) {
			ret = heightRow * 2;
		}
		return ret;

	}

	final Handler handlerDownload = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.arg1;
			if (total >= 0) {
				try {
					removeDialog(PROGRESS_DIALOG);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				try {
					Parameter.threaddownload
							.setState(ThreadDownload.STATE_DONE);
					Parameter.threaddownload.interrupt();
					Parameter.threaddownload = null;
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				Toast.makeText(ListDownload.this,
						R.string.message_download_completed, Toast.LENGTH_SHORT)
						.show();
				String path = Environment.getExternalStorageDirectory()
						+ getString(R.string.path_to_book, book
								.getListCategoryId().get(0), book.getBookId());
				File fileAutoSlide = new File(path, "autoslide");
				try {
					FileOutputStream fos = new FileOutputStream(fileAutoSlide);
					if (book.isAutoSlide()) {
						fos.write(1);

					} else {
						fos.write(0);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Editor e = sharePref.edit();
				// e.putBoolean(String.valueOf(book.getBookId()),
				// book.isAutoSlide());
				// e.commit();
			} else {
				try {
					Parameter.threaddownload
							.setState(ThreadDownload.STATE_DONE);
					Parameter.threaddownload.interrupt();
					Parameter.threaddownload = null;
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				try {
					try {
						showDialog(DIALOG_DOWNLOAD_FAIL);
						removeDialog(PROGRESS_DIALOG);
					} catch (BadTokenException e) {
						// TODO: handle exception

					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}

		}
	};

	/** Nested class that performs progress calculations (counting) */
	private class ProgressThread extends Thread {
		Handler mHandler;
		final static int STATE_DONE = 0;
		final static int STATE_RUNNING = 1;
		int mState;
		int total;

		ProgressThread(Handler h) {
			mHandler = h;
		}

		public void run() {
			mState = STATE_RUNNING;
			total = 0;
			if (mState == STATE_RUNNING) {
				total = getListBook();
				Message msg = mHandler.obtainMessage();
				msg.arg1 = total;
				mHandler.sendMessage(msg);
				total++;
			}
		}

		/*
		 * sets the current state for the thread, used to stop the thread
		 */
		public void setState(int state) {
			mState = state;
		}
	}

	public void onScrollChanged(int y) {
		if (!mSvBookshelf.ismIsBottom())
			resetHeight();
		for (int i = 0; i < CATEGORY_NUMBER; i++) {
			if (y >= mGvs[i].getFrom() && y < mGvs[i].getTo()) {
				mTvTitles[0].setText(listCategories.get(i));
				break;
			}
		}
	}
}
