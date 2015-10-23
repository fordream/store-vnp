package vn.vvn.bibook.activity;

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
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;

public class ViewBook extends Activity {

	protected static final int DIALOG_CANT_OPEN = 2;
	protected static final int DIALOG_PROGRESS = 1;

	private FrameLayout flMain;
	private NewGallery glPage;
	private PageAdapter mAdapter;
	// private Button btnHome;
	private int idCategory;
	private int idBook;
	private Intent mIntent;
	private List<Page> listPage;
	private File rootImage;
	private File rootSound;
	private int mWidth;
	private int mHeight;
	protected List<Polygon> listPo;
	MediaPlayer player;
	AudioManager mAudioManager;
	int mVolume;
	SharedPreferences sharePre;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_book);
		sharePre = getSharedPreferences(Splash.SHARE_PREF, Context.MODE_PRIVATE);
		initComponent();
		initContent();
		setEvent();
	}

	private void initComponent() {
		listPage = new ArrayList<Page>();
		flMain = (FrameLayout) findViewById(R.id.flMain);
		glPage = new NewGallery(this);
		glPage.setSpacing(3);
		flMain.addView(glPage, new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
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
		player.setVolume(mVolume, mVolume);
		player.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mp) {
				if (isAutoSlide)
					glPage.onFling(null, null, -1000, 0);

			}
		});
		String path = Environment.getExternalStorageDirectory()
				+ getString(R.string.path_to_book, idCategory, idBook);
		File fileAutoSlide = new File(path, "autoslide");
		try {
			FileInputStream fis = new FileInputStream(fileAutoSlide);
			if (fis.read() == 1) {
				isAutoSlide = true;
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
	}

	private void initContent() {
		mWidth = getWindowManager().getDefaultDisplay().getWidth();
		mHeight = getWindowManager().getDefaultDisplay().getHeight();
		// rootImage = new File(Environment.getExternalStorageDirectory(),
		// getString(R.string.path_to_image_folder, idCategory, idBook));
		// rootSound = new File(Environment.getExternalStorageDirectory(),
		// getString(R.string.path_to_sound_folder, idCategory, idBook));
		ThreadDecodeBook decode = new ThreadDecodeBook(this, Environment
				.getExternalStorageDirectory().toString()
				+ getString(R.string.path_to_book, idCategory, idBook), handler);
		showDialog(DIALOG_PROGRESS);
		decode.start();

	}

	public void playAudio(String path) {
		try {

			if (player.isPlaying()) {
				Log.d("test", "stop");
				player.stop();
			}
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
			progressDialog = new ProgressDialog(ViewBook.this);
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
			if (progressDialog.isShowing()) {
				removeDialog(DIALOG_PROGRESS);
			}
			if (total == 1001) {
				rootImage = new File(ViewBook.this.getFilesDir() + "/Pictures");
				rootSound = new File(ViewBook.this.getFilesDir() + "/Sounds");
				Log.d("haipn", "rootImage:" + rootImage);
				Log.d("haipn", "rootSound:" + rootSound);
				getListPage();
				mAdapter = new PageAdapter(ViewBook.this, listPage, mWidth,
						mHeight);
				glPage.setAdapter(mAdapter);
			} else {
				showDialog(DIALOG_CANT_OPEN);
			}

		}
	};
	protected boolean isAutoSlide;

	private void setEvent() {
		// btnHome.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// finish();
		// }
		// });
		glPage.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("haipn", "item selected: " + arg2);
				try {
					player.stop();
					player.reset();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				listPo = listPage.get(arg2).getListPolygon();
				if (arg2 == 0) {
					try {
						playAudio(listPo.get(0).getPathSound());
					} catch (NullPointerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						if (isAutoSlide) {
							playAudio(listPo.get(0).getPathSound());
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		glPage.setOnTouchListener(new OnTouchListener() {
			int idInside = -1;

			public boolean onTouch(View v, MotionEvent event) {
				PointF p = new PointF(event.getX(), event.getY());
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					if (listPo != null) {
						for (int i = 0; i < listPo.size(); i++) {
							if (listPo.get(i).isInside(p)) {
								idInside = i;
								break;
							}
						}
					}
					break;

				case MotionEvent.ACTION_UP:
					if (listPo != null) {
						for (int i = 0; i < listPo.size(); i++) {
							if (listPo.get(i).isInside(p) && idInside == i) {
								playAudio(listPo.get(i).getPathSound());
								break;
							}
						}
					}
					idInside = -1;
					break;

				default:
					break;

				}
				return false;
			}
		});
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
		deleteTempBook();
		try {
			player.stop();
			player.reset();
			player.release();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	private void deleteTempBook() {
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
	}

	@Override
	protected void onStop() {
		super.onStop();
		try {
			player.stop();
			player.reset();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
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

}
