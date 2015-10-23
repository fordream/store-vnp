package com.icts.viewcustom;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.control.SwipAdapter.OnItemSlideChangedListener;
import com.icts.control.SwipAdapter.OnItemSlideLeftListener;
import com.icts.control.SwipAdapter.OnItemSlideRightListener;
import com.icts.control.SwipListView;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.utils.Constant;
import com.icts.utils.DownloadImage;
import com.icts.utils.DownloadImageTask;
import com.icts.utils.Utils;

public class CopyOfAlbumSetting {
	private final int TIME_SLIDE_ANIMATION = 500;
	private Context mContext;
	private MainActivity mainActivity;
	private LayoutInflater layoutInflater;
	private View mView = null;
	private RelativeLayout rlAdd;
	private Button btnBack;
	private static String uid;
	private SwipListView mListView;
	private AlbumAdapter mAdapter;
	private ProgressDialog progress;
	public ArrayList<AlbumObject> arrAlbum = new ArrayList<AlbumObject>();
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (progress != null) {
				progress.dismiss();
			}
			if (msg.what == 0) {
				setData();
				initListView();

			} else if (msg.what == 1) {
				Toast.makeText(
						mContext,
						"Error when deleting: error "
								+ ItelApplication.err_message,
						Toast.LENGTH_LONG).show();
			} else if (msg.what == 2) {
				Toast.makeText(mContext, "Succeed", Toast.LENGTH_LONG).show();
				int pos = msg.arg2;
				AlbumObject obj = arrAlbum.get(pos);
				mAdapter.remove(obj);
				mAdapter.notifyDataSetChanged();
			} else if (msg.what == 4) {
				if (progress!=null){
					progress.dismiss();
				}
				Toast.makeText(mContext, "Error when uploading",
						Toast.LENGTH_LONG).show();
				getAlbumList(ItelApplication.user_id, ItelApplication.uuid,
						ItelApplication.user_id, false);
			} else if (msg.what == 5) {
				if (progress!=null){
					progress.dismiss();
				}
				getAlbumList(ItelApplication.user_id, ItelApplication.uuid,
						ItelApplication.user_id, true);
				// System.out.println("arr size: " + arrAlbum.size());
				Toast.makeText(mContext, "upload image successful",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == 6) {
				mAdapter.notifyDataSetChanged();
				mAdapter.notifyDataSetInvalidated();
			}
		};
	};

	private void setData() {
		if (arrAlbum.size() > 0) {
			mAdapter = new AlbumAdapter(mContext, R.layout.layout_album_item,
					R.id.tv3, arrAlbum,mHandler);
			mAdapter.setLeftMargin(0);
			mAdapter.setRightMargin(0);
			mAdapter.setOnItemSlideLeftListener(new OnItemSlideLeftListener() {

				@Override
				public void onItemSlideLeft(View v) {
					RowWrapper holder =(RowWrapper) v.getTag();
					if (holder!=null){
						holder.rlRight.setVisibility(View.VISIBLE);
						holder.tvPreview.setVisibility(View.VISIBLE);
						holder.imgDelete.setVisibility(View.GONE);
					}

				}

			});
			mAdapter.setOnItemSlideRightListener(new OnItemSlideRightListener() {

				@Override
				public void onItemSlideRight(View v) {
					RowWrapper holder =(RowWrapper) v.getTag();
					if (holder!=null){
						holder.rlRight.setVisibility(View.VISIBLE);
						holder.tvPreview.setVisibility(View.GONE);
						holder.imgDelete.setVisibility(View.VISIBLE);
					}
				}

			});

			mAdapter.setOnItemSlideChangedListener(new OnItemSlideChangedListener() {

				@Override
				public void onItemSlideChanged(final View v, int direction) {
					//mAdapter.setBusy(true);
					if (direction == SwipAdapter.LEFT) {
						mAdapter.setBusy(true);
						Object o = v.getTag();
						if (o instanceof RowWrapper){
							RowWrapper holder =(RowWrapper) o;
							if (holder!=null){
								holder.rlRight.setVisibility(View.GONE);
							}
							mAdapter.resetViewFirstPosition(v);
							v.clearAnimation();
							v.requestLayout();
							int position = mListView.getPositionForView(v);
							AlbumObject obj =(AlbumObject) mAdapter.getItem(position);
							previewPhoto(obj.getPath());
						}
						
					} else if (direction == SwipAdapter.RIGHT) {
						int position = mListView.getPositionForView(v);
						//RowWrapper holder =(RowWrapper) v.getTag();
						/*if (holder!=null){
							AlbumObject obj =(AlbumObject) holder.timeStamp.getTag();
							deleteImage(obj.getId());
						}*/
						AlbumObject obj =(AlbumObject) mAdapter.getItem(position);
						deleteImage(obj.getId(),position);
					}
				}

			});

		}
	}

	private void initListView() {
		mListView.setAdapter(mAdapter);
		mListView.setClipChildren(false);
		mListView.setClipToPadding(false);
		mListView.setDividerHeight(0);
		mListView.invalidate();
	}

	public CopyOfAlbumSetting(Context context, MainActivity mActivity,
			int userID) {
		uid = ItelApplication.uuid;
		mContext = context;
		mainActivity = mActivity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.layout_album, null);
		mListView = (SwipListView) mView.findViewById(R.id.listView1);
		rlAdd = (RelativeLayout) mView.findViewById(R.id.album_rl1);
		btnBack = (Button) mView.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(onClickComponent);
		rlAdd.setFocusable(true);
		rlAdd.setFocusableInTouchMode(true);
		handleEvent(rlAdd);
		getAlbumList(ItelApplication.user_id, ItelApplication.uuid,
				ItelApplication.user_id, false);
		// getAlbumList(12, "26234213123d6f182a46e615ac0d8a55");
	}

	public CopyOfAlbumSetting(Context context, MainActivity mActivity,
			Intent data) {
		uid = ItelApplication.uuid;
		mContext = context;
		mainActivity = mActivity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.layout_album, null);
		mListView = (SwipListView) mView.findViewById(R.id.listView1);
		rlAdd = (RelativeLayout) mView.findViewById(R.id.album_rl1);
		btnBack = (Button) mView.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(onClickComponent);
		rlAdd.setFocusable(true);
		rlAdd.setFocusableInTouchMode(true);
		handleEvent(rlAdd);
		updateAvatar(data, mActivity);
		/*
		 * getAlbumList(ItelApplication.user_id, ItelApplication.uuid,
		 * ItelApplication.user_id,false); // getAlbumList(12,
		 * "26234213123d6f182a46e615ac0d8a55");
		 */}

	private void getAlbumList(final int user_id, final String uuid,
			final int friend_id, final boolean update) {
		progress = ProgressDialog.show(mainActivity, null,
				mainActivity.getString(R.string.message_loading));
		new Thread(new Runnable() {

			@Override
			public void run() {
				// Looper.prepare();
				JSONObject jObject = Constant.getJSONfromURL(Constant.GET_ALBUM
						+ user_id + Constant.uuid + uuid + "&friend_id="
						+ friend_id);
				System.out.println("json: " + Constant.GET_ALBUM + user_id
						+ Constant.uuid + uuid + "&friend_id=" + friend_id);
				JSONArray jsonAr;
				int l;
				JSONObject jsonObj;
				// Get friends
				try {
					if (jObject != null) {
						arrAlbum.clear();
						if (jObject.has("album")) {
							jsonAr = jObject.getJSONArray("album");
							if (jsonAr != null) {
								l = jsonAr.length();
								AlbumObject albumObj = null;
								for (int i = 0; i < l; i++) {
									jsonObj = jsonAr.optJSONObject(i);
									albumObj = new AlbumObject();
									albumObj.setId(jsonObj.getInt("id"));
									albumObj.setPath(jsonObj.getString("url"));
									arrAlbum.add(albumObj);
								}

								if (update) {
									mHandler.sendEmptyMessage(0);
									return;
								}
							}
						}
					}
					mHandler.sendEmptyMessage(0);

				} catch (JSONException e) {
					mHandler.sendEmptyMessage(0);
					e.printStackTrace();
				}
				// Looper.loop();
			}
		}).start();
	}

	/**
	 * Hanlde when clicking some button
	 */
	OnClickListener onClickComponent = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnBack:
				ProfileSetting setting = new ProfileSetting(mContext,
						mainActivity);
				mainActivity.changeViewLL(setting.getmView());
				break;
			default:
				break;
			}
		}
	};

	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = mView;
	}

	class AlbumAdapter extends SwipAdapter<AlbumObject> {

		private ArrayList<AlbumObject> arrObj;
		private ExecutorService executor;
		private Handler handler;
		public AlbumAdapter(Context context, ArrayList<AlbumObject> objects) {
			super(context, R.layout.layout_album_item, objects);
			mContext = context;
			this.arrObj = objects;
			executor = Executors.newFixedThreadPool(3);
		}

		public AlbumAdapter(Context context, int resource,
				int textViewResourceId, ArrayList<AlbumObject> objects) {
			super(context, resource, textViewResourceId);
			mContext = context;
			this.arrObj = objects;
			executor = Executors.newFixedThreadPool(3);
		}
		
		public AlbumAdapter(Context context, int resource,
				int textViewResourceId, ArrayList<AlbumObject> objects,Handler handler) {
			super(context, resource, textViewResourceId);
			mContext = context;
			this.arrObj = objects;
			executor = Executors.newFixedThreadPool(3);
			this.handler = handler;
		}

		@Override
		public AlbumObject getItem(int position) {
			return this.arrObj.get(position);
		}

		@Override
		public int getCount() {
			return this.arrObj.size();
		}

		@Override
		public void add(AlbumObject object) {
			this.arrObj.add(object);
		}

		@Override
		public void clear() {
			this.arrObj.clear();
		}

		public void destroy() {
			ItelApplication.killTask(executor);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
			AlbumObject albumObj = getItem(position);
			RowWrapper wrapper;
			if (v == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.layout_album_item, null);
				wrapper = new RowWrapper();
				wrapper.timeStamp = (ImageView) convertView
						.findViewById(R.id.imgTimestamp);
				wrapper.imgDelete = (ImageView) convertView
										.findViewById(R.id.album_img_delete_item);
				wrapper.tvPreview = (TextView) convertView
										.findViewById(R.id.album_tv_preview_item);
				wrapper.rlRight = (RelativeLayout) convertView
										.findViewById(R.id.album_rl_item_text_right);
				wrapper.rlImage = (RelativeLayout) convertView
										.findViewById(R.id.album_ll_image_item);
				convertView.setTag(wrapper);
				v = convertView;
				//handleEvent(v);
			} else {
				wrapper = (RowWrapper) v.getTag();
				if (wrapper == null) {
					wrapper = new RowWrapper();
					wrapper.timeStamp = (ImageView) v
							.findViewById(R.id.imgTimestamp);
					wrapper.imgDelete = (ImageView) v
							.findViewById(R.id.album_img_delete_item);
					wrapper.tvPreview = (TextView) v
							.findViewById(R.id.album_tv_preview_item);
					
					wrapper.rlRight = (RelativeLayout) v
							.findViewById(R.id.album_rl_item_text_right);
					wrapper.rlImage = (RelativeLayout) v
							.findViewById(R.id.album_ll_image_item);
					v.setTag(wrapper);
					//handleEvent(v);
				}

			}
			// DownloadImage down = new DownloadImage(wrapper.timeStamp, 60, 60,
			// mContext);
			// down.execute(albumObj.getPath());
			//wrapper.timeStamp.setTag(albumObj);
			/*DownloadImageTask down2 = new DownloadImageTask(wrapper.timeStamp,
					80, 80, mContext, albumObj.getPath());
			// new Thread(down2).start();
			if (executor == null) {
				executor = Executors.newFixedThreadPool(3);
			}
			executor.submit(down2);*/
			DownloadTask down2 = new DownloadTask(wrapper.timeStamp,
					80, 80, mContext,wrapper.rlRight,wrapper.rlImage, albumObj.getPath(),handler);
			executor.submit(down2);
			return v;
		}
		
		private class DownloadTask extends DownloadImageTask{

	 		private View v;
	 		private RelativeLayout p;
	 		private Handler handler;
			public DownloadTask(ImageView imageView, int width, int height,
					Context context, String url) {
				super(imageView, width, height, context, url);
			}
			
			public DownloadTask(ImageView imageView, int width, int height,
					Context context,View v,RelativeLayout p,String url,Handler handler) {
				super(imageView, width, height, context,url);
				this.v = v;
				this.p = p;
				this.handler = handler;
				this.handler.post(new Runnable() {
					
					@Override
					public void run() {
						if (DownloadTask.this.v!=null){
							DownloadTask.this.v.setVisibility(View.GONE);
						}
					}
				});
			}
			@Override
			protected void setBitmap( Bitmap bitmap) {
				super.setBitmap(bitmap);
				final Bitmap b = bitmap;
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						if (b!=null){
							if (v!=null){
								v.setVisibility(View.VISIBLE);
							}
							if (p!=null){
								p.setVisibility(View.VISIBLE);
								p.setFocusable(true);
								p.setFocusableInTouchMode(true);
								handleEvent(p);
							}
							
						}
						else {
							if (p!=null){
								p.setVisibility(View.GONE);
							}
						}
						
					}
				});
				
			}
	 		
	 	}
	}

	private class RowWrapper {
		ImageView timeStamp;
		ImageView imgDelete;
		TextView tvPreview;
		RelativeLayout rlRight;
		RelativeLayout rlImage;
	}

	private class AlbumObject {
		int id;
		String path;

		public AlbumObject() {
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

	}

	private void upImage() {
		if (arrAlbum.size() >= 3) {
			Toast.makeText(mContext, "User can only upload 3 images",
					Toast.LENGTH_SHORT).show();
		} else {
			Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
			intent1.setType("image/*");
			mainActivity.startActivityForResult(intent1, 6);
			// mainActivity.startActivityForResult(intent1, 6);
		}
	}

	public void updateAvatar(Intent data, Activity m) {
		//progress = ProgressDialog.show(mContext, null, mContext.getString(R.string.message_upload));
		Uri select = data.getData();
		String[] str = { MediaStore.Images.Media.DATA };
		Cursor cursor = m.managedQuery(select, str, null, null, null);
		int coloumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String s = cursor.getString(coloumn);
		uploadPhoto(Constant.SETTING_UPLOAD_IMAGE, ItelApplication.user_id,
				uid, s, m);
	}

	public void uploadPhoto(final String urlUpload, final int user_id,
			final String uuid_friend, final String path, final Context context) {
		progress = ProgressDialog.show(context, null,
				context.getString(R.string.message_upload));
		Thread t = new Thread() {
			public void run() {
				Looper.prepare();
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(urlUpload);
				FileInputStream in = null;
				try {
					in = new FileInputStream(path);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				// Log.i("NDT", "user_id "+user_id+" "+uuid_friend);
				// File file = new File(path);
				try{
					Bitmap bitmapOrg1 = BitmapFactory.decodeStream(in);
					ByteArrayOutputStream bao1 = new ByteArrayOutputStream();
					bitmapOrg1.compress(Bitmap.CompressFormat.JPEG, 90, bao1);
					byte[] imagearray1 = null;
					imagearray1 = Utils.reduceSize(bitmapOrg1);
					String ba1 = Base64.encodeToString(imagearray1, Base64.DEFAULT);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								3);
						nameValuePairs.add(new BasicNameValuePair("user_id", String
								.valueOf(user_id)));
						nameValuePairs.add(new BasicNameValuePair("uuid",
								uuid_friend));
						nameValuePairs.add(new BasicNameValuePair("image", ba1));
						httppost.addHeader(BasicScheme
								.authenticate(new UsernamePasswordCredentials(
										Constant.ITELL_USERNAME_AUTHEN,
										Constant.ITELL_PASS_AUTHEN), HTTP.UTF_8, false));
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						HttpResponse response = httpclient.execute(httppost);
						InputStream is = null;
						if (response.getStatusLine().getStatusCode() == 200) {
							HttpEntity entity = response.getEntity();
							is = entity.getContent();
							try {
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(is, HTTP.ISO_8859_1), 8);
								StringBuilder sb = new StringBuilder();
								String line = null;
								while ((line = reader.readLine()) != null) {
									sb.append(line + "\n");
								}
								is.close();
								Log.i("NDT", "respond " + sb.toString());
								JSONObject json = new JSONObject(sb.toString());
								boolean c = json.getBoolean("retval");
								if (c) {
									mHandler.sendEmptyMessage(5);
								} else {
									mHandler.sendEmptyMessage(4);
								}
							} catch (Exception e) {
								ItelApplication.err_message = e.getMessage();
								mHandler.sendEmptyMessage(4);
								e.printStackTrace();
							}
							// HttpEntity entity = response.getEntity();
							// is = entity.getContent();
						}
						// read response data
						// try {
						// BufferedReader reader = new BufferedReader(
						// new InputStreamReader(is, "iso-8859-1"), 8);
						// StringBuilder sb = new StringBuilder();
						// String line = null;
						// while ((line = reader.readLine()) != null) {
						// sb.append(line + "\n");
						// }
						// is.close();
						// Log.i("NDT", "respond " + sb.toString());
						// System.out.println("reponse upload avatar: "
						// + response.getStatusLine().getStatusCode()
						// + " content: " + sb.toString());
						// } catch (Exception e) {
						// e.printStackTrace();
						// }
					} catch (ClientProtocolException e) {
						mHandler.sendEmptyMessage(4);
						ItelApplication.err_message = e.getMessage();
						e.printStackTrace();
					} catch (IOException e) {
						mHandler.sendEmptyMessage(4);
						ItelApplication.err_message = e.getMessage();
						e.printStackTrace();
					}
				}
				catch(OutOfMemoryError e){
					mHandler.sendEmptyMessage(4);
					ItelApplication.err_message = e.getMessage();
					e.printStackTrace();
				}
				Looper.loop();
			}
		};
		t.start();
	}

	/**
	 * create slide animation
	 * 
	 * @param v
	 */
	protected void handleEvent(final View v) {
		v.setOnTouchListener(new OnTouchListener() {
			private float xStart;
			private int direction = 0;
			View itemUp = null;
			private boolean slideFinish = false;

			public boolean onTouch(final View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					direction = 0;
					itemUp = v.findViewWithTag("item_move");
					xStart = event.getX();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					float offsetX = event.getX() - xStart;
					if (offsetX < 0) {
						if (direction >= 0) {
							direction = SwipAdapter.LEFT;
							// onItemSlideLeftListener.onItemSlideLeft(v);
						}
					} else if (offsetX > 0) {
						if (direction <= 0) {
							direction = SwipAdapter.RIGHT;
						}
					} else {
						direction = 0;
					}
					// if (offsetX<0){
					RelativeLayout.LayoutParams params = (LayoutParams) itemUp
							.getLayoutParams();
					params.leftMargin = (int) offsetX;
					params.rightMargin = (int) -offsetX;
					itemUp.setLayoutParams(params);
					// }
				} else if (event.getAction() == MotionEvent.ACTION_OUTSIDE
						|| event.getAction() == MotionEvent.ACTION_CANCEL
						|| event.getAction() == MotionEvent.ACTION_UP) {
					final RelativeLayout.LayoutParams params = (LayoutParams) itemUp
							.getLayoutParams();
					final int leftMargin = params.leftMargin;
					final int rightMargin = params.rightMargin;
					Animation slideBackAnimation = new Animation() {
						@Override
						protected void applyTransformation(
								float interpolatedTime, Transformation t) {
							if (direction == SwipAdapter.LEFT
									&& Math.abs(leftMargin) > Constant.SCREEN_WIDTH / 3) {
								params.leftMargin = (int) (leftMargin - (Constant.SCREEN_WIDTH + leftMargin)
										* interpolatedTime);
								params.rightMargin = -params.leftMargin;
								this.setDuration(Math.abs(Constant.SCREEN_WIDTH
										+ leftMargin)
										* TIME_SLIDE_ANIMATION
										/ Constant.SCREEN_WIDTH);
								slideFinish = true;
							} else if (direction == SwipAdapter.RIGHT
									&& Math.abs(leftMargin) > Constant.SCREEN_WIDTH / 3) {
								params.leftMargin = (int) (leftMargin + (Constant.SCREEN_WIDTH - leftMargin)
										* interpolatedTime);
								params.rightMargin = -params.leftMargin;
								this.setDuration(Math.abs(Constant.SCREEN_WIDTH
										- leftMargin)
										* TIME_SLIDE_ANIMATION
										/ Constant.SCREEN_WIDTH);
								slideFinish = true;
							} else {
								params.leftMargin = (int) (leftMargin * (1 - interpolatedTime));
								params.rightMargin = (int) (rightMargin * (1 - interpolatedTime));
								this.setDuration(Math.abs(leftMargin)
										* TIME_SLIDE_ANIMATION
										/ Constant.SCREEN_WIDTH);
								slideFinish = false;
							}
							itemUp.setLayoutParams(params);
						};
					};
					itemUp.startAnimation(slideBackAnimation);
					slideBackAnimation
							.setAnimationListener(new AnimationListener() {

								public void onAnimationStart(Animation animation) {

								}

								public void onAnimationRepeat(
										Animation animation) {

								}

								public void onAnimationEnd(Animation animation) {
									if (slideFinish) { 
										slideFinish(v, direction);
									} else {
										final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) itemUp
												.getLayoutParams();
										params.leftMargin = 0;
										params.rightMargin = 0;
										itemUp.setLayoutParams(params);
									}
									direction = 0;
								}
							});
				}
				return true;
			}
		});
	}

	/**
	 * Handle 
	 * @param v
	 * @param direction
	 */
	private void slideFinish(View v, int direction) {
		switch (v.getId()) {
		case (R.id.album_rl1):
			if (direction == SwipAdapter.LEFT) {
				// add photo in album
				upImage();
			} else if (direction == SwipAdapter.RIGHT) {
				// back
				onClickComponent.onClick(btnBack);
			}

			break;
		}
	}

	/**
	 * Delete image in album
	 * @param id
	 * @param position
	 */
	private void deleteImage(final int id,final int position) {
		new Thread() {
			@Override
			public void run() {
				boolean c = false;
				c = Utils.deleteImage(String.valueOf(ItelApplication.user_id),
						ItelApplication.uuid, id);
				if (c) {
					// success
					mHandler.sendMessage(mHandler.obtainMessage(2, id,position));
				} else {
					mHandler.sendEmptyMessage(1);
				}
			}
		}.start();
	}

	/**
	 * preview image
	 * 
	 * @param url
	 *            : link to show image
	 */
	private void previewPhoto(String url) {
		final Dialog dia = new Dialog(mContext, R.style.FullScreenDialog){
			@Override
			protected void onStop() {
				super.onStop();
				mAdapter.setBusy(false);
			}
			
		};
		dia.setContentView(R.layout.preview_dialog);
		ImageView imageView = (ImageView) dia
				.findViewById(R.id.profile_preview_imageview);
		Button okButton = (Button) dia
				.findViewById(R.id.profile_preview_ok_button);
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dia.cancel();
			}
		});

		if (url != null) {
			try {

				WindowManager windowManager = mainActivity.getWindowManager();
				Display display = windowManager.getDefaultDisplay();
				int mScreenWidth;
				int mScreenHeight;
				mScreenWidth = display.getWidth();
				mScreenHeight = display.getHeight();

				DownloadImageTask down = new DownloadImageTask(imageView, mScreenWidth,
						mScreenHeight, mContext,url);
				//down.execute(url);
				mHandler.post(down);
				dia.show();
			} catch (OutOfMemoryError e) {
				Toast.makeText(
						mContext,
						mContext.getResources().getString(
								R.string.message_load_image_error),
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			} catch (Exception e) {
			}
		}
	}
}
