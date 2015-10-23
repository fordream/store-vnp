package vn.vvn.bibook.pagecurlgallery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import vn.vvn.bibook.R;
import vn.vvn.bibook.activity.ListBookInCategory;
import vn.vvn.bibook.activity.Splash;
import vn.vvn.bibook.encrypter.ThreadDecodeBook;
import vn.vvn.bibook.item.PageAdapter;
import vn.vvn.bibook.util.NewGallery;
import vn.vvn.bibook.util.Page;
import vn.vvn.bibook.util.Polygon;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ViewBook2 extends Activity {

	protected static final int DIALOG_CANT_OPEN = 2;
	protected static final int DIALOG_PROGRESS = 1;

	private FrameLayout flMain;
	private NewGallery glPage;
	private PageAdapter mAdapter;
	// private Button btnHome;
	private int idCategory;
	private int idBook;
	private Intent mIntent;
	private static List<Page> listPage;
	private File rootImage;
	private File rootSound;
	private int mWidth;
	private int mHeight;
	private Button mBtLock;
	protected static List<Polygon> listPo;
	static MediaPlayer player;
	AudioManager mAudioManager;
	int mVolume;
	SharedPreferences sharePre;
	static CurlView mCurlView;
	public static boolean isLock = false;
	private BitmapProvider2 mBitmapProvider = null;
	int nowPage = 0;
	private int IDTRUYEN = 3;
//	ImageView mIvTemp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_book2);
		sharePre = getSharedPreferences(Splash.SHARE_PREF, Context.MODE_PRIVATE);
		initComponent();
		initContent();
		setEvent();
	}

	private void initComponent() {
	//	mIvTemp = (ImageView) findViewById(R.id.iv_temp);
		listPage = new ArrayList<Page>();
		mBtLock = (Button) findViewById(R.id.bt_lock);
		mBtLock.setBackgroundResource(R.drawable.unlock);
		flMain = (FrameLayout) findViewById(R.id.flMain);
		// glPage = new NewGallery(this);
		// glPage.setSpacing(3);
		// flMain.addView(glPage, new FrameLayout.LayoutParams(
		// ViewGroup.LayoutParams.FILL_PARENT,
		// ViewGroup.LayoutParams.FILL_PARENT));

		mCurlView = (CurlView) findViewById(R.id.curl);

		if (mBitmapProvider == null) {
			Log.d(TAG, "nullaaaaaaaaaaaaaaaaaa");
		}
		// RelativeLayout view = (RelativeLayout) getLayoutInflater().inflate(
		// R.layout.button_home,
		// (ViewGroup) findViewById(R.id.rlNavigation));
		// btnHome = (Button) view.findViewById(R.id.btnHome);

		// flMain.addView(view, new FrameLayout.LayoutParams(
		// ViewGroup.LayoutParams.FILL_PARENT,
		// ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
		mIntent = getIntent();
		idCategory = mIntent.getIntExtra(ListBookInCategory.CATEGORY_ID, 0);
		idBook = mIntent.getIntExtra(ListBookInCategory.BOOK_ID, 0);
		player = new MediaPlayer();
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
		//player.setVolume(mVolume, mVolume);
		player.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mp) {
				if (isAutoSlide)
					mCurlView.nextPage();
				// glPage.onFling(null, null, -1000, 0);

			}
		});
		String path = Environment.getExternalStorageDirectory()
				+ getString(R.string.path_to_book, idCategory, idBook);
		File fileAutoSlide = new File(path, "autoslide");
		try {
			FileInputStream fis = new FileInputStream(fileAutoSlide);
			if (fis.read() == 1) {
				isAutoSlide = true;
				mBtLock.setVisibility(View.VISIBLE);
			} else {
				isAutoSlide = false;
			}
			Log.d("haipn", "is auto slide:" + isAutoSlide);
		} catch (FileNotFoundException e) {
			isAutoSlide = false;
			e.printStackTrace();
		} catch (IOException e) {
			isAutoSlide = false;
			e.printStackTrace();
		}
		// if(idCategory == IDTRUYEN){
		// isAutoSlide = true;
		// }else{
		// isAutoSlide = false;
		// }
		// isAutoSlide = sharePre.getBoolean(String.valueOf(idBook), false);
		Log.d("vietln", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa   " + isAutoSlide);
	}

	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		public void onSizeChanged(int w, int h) {
			if (w > h) {
				// mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
				mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
				// mCurlView.setMargins(.1f, .05f, .1f, .05f);
				mCurlView.setMargins(.0f, .0f, .0f, .0f);
			} else {
				mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
				// mCurlView.setMargins(.1f, .1f, .1f, .1f);
				mCurlView.setMargins(.0f, .0f, .0f, .0f);
			}
		}
	}

	final String TAG = "Bibook";
	Bitmap bitmap;

	// Bitmap b;
	public class BitmapProvider2 implements CurlView.BitmapProvider {
		Uri mCurrentUri = null;

		public int mImagesCount = 10;
		Cursor mImageCur = null;

		public BitmapProvider2(int size) {
			mImagesCount = size;
		}

		boolean isFirst = true;

		public Bitmap getBitmap(int width, int height, int index) {
			// Bitmap tmp = BitmapFactory.decodeResource(getResources(),
			// R.drawable.frame_border_texture);
			// b = Bitmap.createScaledBitmap(tmp, width, height, true);
			// /tmp.recycle();
			// tmp = null;
			Canvas c = new Canvas();
			Log.v(TAG, "_ID=" + index + "===" + height + "--" + width + "---"
					+ listPage.size());
			Log.v(TAG, "DATA=" + listPage.get(index).getPathImage());
			// mCurrentUri = Uri.parse(mListFile[nextpage]);

			try {

				bitmap = getBitmapFromUri(listPage.get(index).getPathImage(),
						width, height);

				Drawable d = new BitmapDrawable(bitmap);
				int margin = 0/* 7 */;
				int border = 0/* 3 */;
				Rect r = new Rect(margin, margin, width - margin, height
						- margin);

				int imageWidth = r.width() - (border * 2);
				int imageHeight = imageWidth * d.getIntrinsicHeight()
						/ d.getIntrinsicWidth();
				if (imageHeight > r.height() - (border * 2)) {
					imageHeight = r.height() - (border * 2);
					imageWidth = imageHeight * d.getIntrinsicWidth()
							/ d.getIntrinsicHeight();
				}

				r.left += ((r.width() - imageWidth) / 2) - border;
				r.right = r.left + imageWidth + border + border;
				r.top += ((r.height() - imageHeight) / 2) - border;
				r.bottom = r.top + imageHeight + border + border;

				Paint p = new Paint();
				p.setColor(0xFFC0C0C0);
				c.drawRect(r, p);
				r.left += border;
				r.right -= border;
				r.top += border;
				r.bottom -= border;

				d.setBounds(r);
				d.draw(c);
				if (isAutoSlide && index == 0) {
					autoPlay();
					// nowPage = index;
				}
				if (!isAutoSlide && mCurlView.getCurrentIndex() == 0 && isFirst) {
					try {
						listPo = listPage.get(0).getListPolygon();
						playAudio(listPo.get(0).getPathSound());
						isFirst = false;
					} catch (NullPointerException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO
				// Ã¨â€¡ÂªÃ¥â€¹â€¢Ã§â€�Å¸Ã¦Ë†ï¿½Ã£ï¿½â€¢Ã£â€šÅ’Ã£ï¿½Å¸
				// catch
				// Ã£Æ’â€“Ã£Æ’Â­Ã£Æ’Æ’Ã£â€šÂ¯
				e.printStackTrace();
			}

			return bitmap;
		}

		public int getBitmapCount() {
			return mImagesCount;
		}

		public Uri getCurrentUri() {
			return mCurrentUri;
		}

	}

	public static float insamplesize;

	public Bitmap getBitmapFromUri(String imagePath, int width, int height)
			throws IOException {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inPreferredConfig = Config.RGB_565;
		Bitmap bmp = BitmapFactory.decodeFile(imagePath, bmpFactoryOptions);
		// Log.d(TAG, "size " + bmp.getWidth());
		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) height);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) width);
		Log.v("HEIGHTRATIO", "" + heightRatio);
		Log.v("WIDTHRATIO", "" + widthRatio);
		int scale = 1;

		// if (heightRatio > 1 && widthRatio > 1) {
		// if (heightRatio > widthRatio) {
		// // Height ratio is larger, scale according to it
		// bmpFactoryOptions.inSampleSize = heightRatio;
		// insamplesize = bmpFactoryOptions.outHeight / (float) height;
		// } else {
		// // Width ratio is larger, scale according to it
		// bmpFactoryOptions.inSampleSize = widthRatio;
		// insamplesize = bmpFactoryOptions.outWidth / (float) width;
		// }
		// }

		final int REQUIRED_SIZE = width;
		// int width_tmp = o.outWidth, height_tmp = o.outHeight;
		// int scale = 1;
		while (true) {
			if (widthRatio / 2 < REQUIRED_SIZE
					|| heightRatio / 2 < REQUIRED_SIZE)
				break;
			widthRatio /= 2;
			heightRatio /= 2;
			scale *= 2;
		}
		Log.e("vietln", "aaaaaaaaaaaaa " + scale);
		insamplesize = scale;
		// if(scale==1){
		// scale = 2;
		// }
		bmpFactoryOptions.inSampleSize = scale;
		bmp = BitmapFactory.decodeFile(imagePath, bmpFactoryOptions);
		bmp = Bitmap.createScaledBitmap(bmp, width, height, true);
		return bmp;
	}

	ThreadDecodeBook decode;

	private void initContent() {
		mWidth = getWindowManager().getDefaultDisplay().getWidth();
		mHeight = getWindowManager().getDefaultDisplay().getHeight();
		// rootImage = new File(Environment.getExternalStorageDirectory(),
		// getString(R.string.path_to_image_folder, idCategory, idBook));
		// rootSound = new File(Environment.getExternalStorageDirectory(),
		// getString(R.string.path_to_sound_folder, idCategory, idBook));
		decode = new ThreadDecodeBook(this, Environment
				.getExternalStorageDirectory().toString()
				+ getString(R.string.path_to_book, idCategory, idBook), handler);
		showDialog(DIALOG_PROGRESS);
		decode.start();

	}

	public static void playAudio(String path) {
		try {

			if (player.isPlaying()) {
				Log.d("test", "stop");
				player.stop();
			}
			Log.d("vietln", "test play audio");
			player.reset();
			File mFile = new File(path);
			FileInputStream fileInputStream = new FileInputStream(mFile);
			player.setDataSource(fileInputStream.getFD());
			player.prepare();
			player.start();
			// player.reset();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	ProgressDialog progressDialog;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_PROGRESS:
			progressDialog = new ProgressDialog(ViewBook2.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Loading...");
			return progressDialog;
		case DIALOG_CANT_OPEN:
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
		}
		return null;
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.arg1;
			try{
			if (progressDialog.isShowing()) {
				removeDialog(DIALOG_PROGRESS);
			}
			//
			//mIvTemp.setVisibility(View.GONE);
			if (total == 1001) {
				rootImage = new File(ViewBook2.this.getFilesDir() + "/Pictures");
				rootSound = new File(ViewBook2.this.getFilesDir() + "/Sounds");
				Log.d("haipn", "rootImage:" + rootImage);
				Log.d("haipn", "rootSound:" + rootSound);
				getListPage();
				Log.d(TAG, "list page " + listPage.size());
				int index = 0;
				if (getLastNonConfigurationInstance() != null) {
					index = (Integer) getLastNonConfigurationInstance();
				}

				mCurlView.setSizeChangedObserver(new SizeChangedObserver());
				mCurlView.setCurrentIndex(index);
				mCurlView.setAllowLastPageCurl(false);
				mCurlView.setBackgroundColor(0xFF202830);
				mBitmapProvider = new BitmapProvider2(listPage.size());
				mCurlView.setBitmapProvider(mBitmapProvider);

				// mAdapter = new PageAdapter(ViewBook2.this, listPage, mWidth,
				// mHeight);
				// glPage.setAdapter(mAdapter);
			} else {
				showDialog(DIALOG_CANT_OPEN);
			}
			decode.setState(ThreadDecodeBook.STATE_DONE);
			decode = null;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	};
	static int idInside;
	public static boolean isAutoSlide;
	private boolean isTouch = false;

	public static void checkInside(float x, float y) {
		Log.d("vietln", "check inside" + x + "-----" + y);
		listPo = listPage.get(mCurlView.getCurrentIndex()).getListPolygon();
		PointF p = new PointF(x, y);
		if (listPo != null) {
			for (int i = 0; i < listPo.size(); i++) {
				Log.d("vietln", "check inside list pro" + listPo.size());
				if (listPo.get(i).isInside(p)) {
					Log.d("vietln", "check inside list pro222 " + i);
					idInside = i;
					break;
				}
			}
		}
	}

	public static void autoPlay() {
		if (isAutoSlide) {
			try{
			listPo = listPage.get(mCurlView.getCurrentIndex()).getListPolygon();
			playAudio(listPo.get(0).getPathSound());
			}catch (Exception e) {
				// TODO: handle exception
				player.stop();
				e.printStackTrace();
			}
		}
	}

	public static void stopPlay() {
		if (player.isPlaying()) {
			Log.d("test", "stop");
			player.stop();
		}
	}

	public static void upCheckInside(float x, float y) {
		Log.d("vietln", "check inside ............................." + x
				+ "-----" + y + "==" + idInside);
		// listPo = listPage.get(mCurlView.getCurrentIndex()).getListPolygon();
		if (mCurlView.getCurrentIndex() != 0) {
			PointF p = new PointF(x, y);
			if (listPo != null) {
				for (int i = 0; i < listPo.size(); i++) {
					if (listPo.get(i).isInside(p) && idInside == i) {
						playAudio(listPo.get(i).getPathSound());
						break;
					}
				}
			}
		} else if (!isAutoSlide && mCurlView.getCurrentIndex() == 0) {
			try {
				listPo = listPage.get(0).getListPolygon();
				playAudio(listPo.get(0).getPathSound());
			} catch (NullPointerException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		idInside = -1;

	}

	private void setEvent() {
		mBtLock.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isLock == false) {
					mBtLock.setBackgroundResource(R.drawable.lock);
					isLock = true;
				} else {
					mBtLock.setBackgroundResource(R.drawable.unlock);
					isLock = false;
				}
			}
		});
		// mCurlView.setOnTouchListener(new OnTouchListener() {
		// int idInside = -1;
		//
		// public boolean onTouch(View v, MotionEvent event) {
		// PointF p = new PointF(event.getX(), event.getY());
		// Log.d("vietln",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		// switch (event.getAction()) {
		//
		// case MotionEvent.ACTION_DOWN: {
		// // isTouch = mCurlView.onTouchDown(event.getX(),
		// // event.getY(),
		// // event.getPressure());
		// Log.d("vietln",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		// + isTouch);
		// if (isTouch) {
		// if (listPo != null) {
		// for (int i = 0; i < listPo.size(); i++) {
		// if (listPo.get(i).isInside(p)) {
		// idInside = i;
		// break;
		// }
		// }
		// }
		// break;
		// }
		// break;
		// }
		// case MotionEvent.ACTION_MOVE: {
		// Log.d("vietln",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaamoveeeeeeeeeeeeeeeee");
		// }
		// case MotionEvent.ACTION_UP:
		// Log.d("vietln",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaupppppppp");
		// if (isTouch) {
		// if (listPo != null) {
		// for (int i = 0; i < listPo.size(); i++) {
		// if (listPo.get(i).isInside(p) && idInside == i) {
		// playAudio(listPo.get(i).getPathSound());
		// break;
		// }
		// }
		// }
		// idInside = -1;
		// break;
		// } else {
		// // mCurlView.onTouchUp(event.getX(), event.getY(),
		// // event.getPressure());
		// }
		// isTouch = false;
		// break;
		// default:
		// break;
		//
		// }
		// return false;
		// }
		// });
		isLock = false;
	}

	private void getListPage() {
		File rootText = new File(Environment.getExternalStorageDirectory(),
				getString(R.string.path_to_text_folder, idCategory, idBook));
		File file = new File(rootImage, "Cover.jpg");
		if (!file.exists()) {
			file = new File(rootImage, "Cover.jpeg");
			if (!file.exists())
				file = new File(rootImage, "Cover.JPG");
			if (!file.exists())
				file = new File(rootImage, "Cover.PNG");
			if (!file.exists())
				file = new File(rootImage, "Cover.png");
		}
		Page p = new Page();
		Log.d("haipn", "path cover:" + file.getAbsolutePath());
		p.setPathImage(file.getAbsolutePath());
		File pathCover = new File(rootSound, "coverSound.mp3");
		if (!pathCover.exists()) {
			file = new File(rootSound, "coverSound.wav");

		}

		if (pathCover.exists()) {
			ArrayList<Polygon> a = new ArrayList<Polygon>();
			Polygon polygon = new Polygon();
			polygon.setPathSound(pathCover.getAbsolutePath());
			a.add(polygon);
			p.setListPolygon(a);
		}
		listPage.add(p);
		try {
			if (rootText.isDirectory() && rootText.exists()) {
				File[] txtFile = rootText.listFiles();
				Arrays.sort(txtFile, new Comparator<File>() {
					public int compare(File f1, File f2) {
						if (f1.getName().length() == f2.getName().length()) {
							return f1.getName().compareToIgnoreCase(
									f2.getName());
						} else if (f1.getName().length() < f2.getName()
								.length())
							return -1;
						else
							return 1;
					}
				});

				Page page;
				for (File txt : txtFile) {
					Log.d("haipn", "text file: " + txt.getAbsolutePath());
					FileInputStream fis = new FileInputStream(txt);
					String line = convertStreamToString(fis);
					page = parserPageInfo(line);
					listPage.add(page);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Page parserPageInfo(String line) {
		// line = "abc|def|ghd|ijk";
		Page page = new Page();
		String[] first = line.split("\\|");
		// for (String string : first) {
		// Log.d("haipn", "string: " + string);
		// }
		File image = new File(rootImage, first[0]);
		Log.d("haipn", "path image:" + image.getAbsolutePath());
		page.setPathImage(image.getAbsolutePath());
		int i = 3;
		if (first.length > 3) {
			ArrayList<Polygon> polys = new ArrayList<Polygon>();
			Polygon poly = null;
			while (i < first.length) {
				poly = new Polygon();
				File sound = new File(rootSound, first[i++]);
				Log.d("haipn", "path sound:" + sound.getAbsolutePath());
				poly.setPathSound(sound.getAbsolutePath());
				poly.setListPoint(parserListPoint(first[i++]));
				polys.add(poly);
			}
			page.setListPolygon(polys);
		}
		return page;
	}

	private ArrayList<PointF> parserListPoint(String string) {
		ArrayList<PointF> ret = new ArrayList<PointF>();
		String[] points = string.split(":");
		for (int i = 0; i < points.length; i++) {
			PointF point = new PointF();

			String[] po = points[i].split(",");

			try {
				point.x = Float.valueOf(po[0]) * mWidth / 100;
				point.y = Float.valueOf(po[1]) * mHeight / 100;
				ret.add(point);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				continue;
			}

		}
		return ret;
	}

	@Override
	protected void onDestroy() {
		Log.d("ViewBook", "onDestroy");
		super.onDestroy();
		//deleteTempBook();
		try {
			if (player.isPlaying()) {
				player.stop();
				player.reset();
				player.release();
				player = null;
			}
			// listPo.clear();
			try{
			if (decode != null
					&& decode.getmState() == ThreadDecodeBook.STATE_RUNNING) {
				decode.stop();
				decode.interrupt();
				decode.setState(ThreadDecodeBook.STATE_DONE);

			}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try {
				//deleteTempBook();
				listPage.clear();
				listPo.clear();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

			e.printStackTrace();
		}
		System.gc();
	}

	private void deleteTempBook() {
		try {
			File[] images = rootImage.listFiles();
			for (File file : images) {
				Log.d("haipn", "del file: " + file.getAbsolutePath());
				file.delete();
			}
			images = rootSound.listFiles();
			for (File file : images) {
				Log.d("haipn", "del file: " + file.getAbsolutePath());
				file.delete();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		try {
			Log.d("vietln", "onstop");
			if (player.isPlaying()) {
				player.stop();
				player.reset();
			}
			try {
				bitmap.recycle();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private String convertStreamToString(InputStream is)
			throws UnsupportedEncodingException {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		StringBuilder sb = new StringBuilder();
		// returnValueList list;
		// returnValue value=null;
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Log.d("vietln", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
