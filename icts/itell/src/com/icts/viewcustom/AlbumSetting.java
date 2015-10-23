package com.icts.viewcustom;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.utils.Constant;
import com.icts.utils.DownloadImage;
import com.icts.utils.DownloadImageTask;
import com.icts.utils.Utils;

public class AlbumSetting {
	private final int TIME_SLIDE_ANIMATION = 500;
	private Context mContext;
	private MainActivity mainActivity;
	private LayoutInflater layoutInflater;
	private View mView = null;
	private RelativeLayout rlAdd;
	private Button btnBack;
	private int user;
	private String uid;
	private RelativeLayout llAlbumImage1,llAlbumImage2,llAlbumImage3;
	private ImageView imgAlbumImage1,imgAlbumImage2,imgAlbumImage3;
	private String urlPhoto1,urlPhoto2,urlPhoto3;
	private int index = 0;
	public ArrayList<AlbumObject> arrAlbum = new ArrayList<AlbumObject>();
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				if (arrAlbum.size()>0) {
					urlPhoto1 = null;
					urlPhoto2 = null;
					urlPhoto3 = null;
					if (arrAlbum.size()==1){
							urlPhoto1 = arrAlbum.get(0).getPath();
					}
					else if (arrAlbum.size()==2){
						urlPhoto1 = arrAlbum.get(0).getPath();
						urlPhoto2 = arrAlbum.get(1).getPath();
					}
					else if (arrAlbum.size()==3){
						urlPhoto1 = arrAlbum.get(0).getPath();
						urlPhoto2 = arrAlbum.get(1).getPath();
						urlPhoto3 = arrAlbum.get(2).getPath();
					}
				}
				showPhoto(urlPhoto1,llAlbumImage1,imgAlbumImage1,0);
				showPhoto(urlPhoto2,llAlbumImage2,imgAlbumImage2,1);
				showPhoto(urlPhoto3,llAlbumImage3,imgAlbumImage3,2);
			}
			else if (msg.what==1){
				Toast.makeText(mContext, "Error when deleting: error "+ItelApplication.err_message, Toast.LENGTH_LONG).show();
			}
			else if (msg.what==2){
				Toast.makeText(mContext, "Succeed", Toast.LENGTH_LONG).show();
				if (index ==1){
					llAlbumImage1.setVisibility(View.GONE);
					urlPhoto1 = null;
				}
				else if (index ==2){
					llAlbumImage2.setVisibility(View.GONE);
					urlPhoto2 = null;
				}
				else if (index ==3){
					llAlbumImage3.setVisibility(View.GONE);
					urlPhoto3 = null;
				}
				arrAlbum.set(index-1, null);
				index =0;
			}
			else if(msg.what == 5){
				getAlbumList(ItelApplication.user_id, ItelApplication.uuid,ItelApplication.user_id);
				Toast.makeText(mContext, "upload image successful",
						Toast.LENGTH_SHORT).show();
			}
		};
	};

	public AlbumSetting(Context context, MainActivity mActivity,int userID) {
		user = userID;
		uid = ItelApplication.uuid;
		mContext = context;
		mainActivity = mActivity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.layout_album, null);
		llAlbumImage1 = (RelativeLayout) mView.findViewById(R.id.llAlbumItem1);
		imgAlbumImage1 = (ImageView) mView.findViewById(R.id.imgAlbumItem1);
		
		llAlbumImage2 = (RelativeLayout) mView.findViewById(R.id.llAlbumItem2);
		imgAlbumImage2 = (ImageView) mView.findViewById(R.id.imgAlbumItem2);
		
		llAlbumImage3 = (RelativeLayout) mView.findViewById(R.id.llAlbumItem3);
		imgAlbumImage3 = (ImageView) mView.findViewById(R.id.imgAlbumItem3);
		llAlbumImage1.setVisibility(View.GONE);
		llAlbumImage2.setVisibility(View.GONE);
		llAlbumImage3.setVisibility(View.GONE);
		rlAdd = (RelativeLayout) mView.findViewById(R.id.album_rl1);
		btnBack = (Button) mView.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(onClickComponent);
		rlAdd.setFocusable(true);
		rlAdd.setFocusableInTouchMode(true);
		handleEvent(rlAdd);
		ItelApplication.user_id = 12;
		ItelApplication.uuid = "26234213123d6f182a46e615ac0d8a55";
		getAlbumList(ItelApplication.user_id, ItelApplication.uuid,ItelApplication.user_id);
		// getAlbumList(12, "26234213123d6f182a46e615ac0d8a55");
	}
	private ArrayList<DownloadImageTask> downArr = new ArrayList<DownloadImageTask>();
	private void showPhoto(String url,RelativeLayout rl,ImageView img,int index){
		if (url!=null){
			rl.setVisibility(View.VISIBLE);
			rl.setTag(arrAlbum.get(index).getId());
			DownloadImageTask down2= new DownloadImageTask(img, Constant.SCREEN_WIDTH, 
															Constant.SCREEN_HEIGHT, mContext,url);
			mHandler.post(down2);
			downArr.add(down2);
			//ItelApplication.submitOther(down2);
			
			if (rl!=null){
				rl.setFocusable(true);
				rl.setFocusableInTouchMode(true);
				handleEvent(rl);
			}
		}
		else {
			rl.setVisibility(View.GONE);
		}
	}
	
	private void getAlbumList(final int user_id, final String uuid,final int friend_id) {
		new Thread(new Runnable() {

			@Override
			public void run() {				
				JSONObject jObject = Constant.getJSONfromURL(Constant.GET_ALBUM
						+ user_id + Constant.uuid + uuid+"&friend_id=" + friend_id);
				JSONArray jsonAr;
				int l;
				JSONObject jsonObj;
				// Get friends
				try {
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
							mHandler.sendEmptyMessage(0);
						}
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

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

	class RowWrapper {
		ImageView timeStamp;
	}

	class AlbumObject {
		int id;
		String path;

		public AlbumObject() {
			// TODO Auto-generated constructor stub
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
		if (arrAlbum.size()>=2) {
		Toast.makeText(mContext, "User can only upload 3 images", Toast.LENGTH_SHORT).show();	
		}else{
		Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
		intent1.setType("image/*");
		((Activity) mContext).startActivityForResult(intent1, 6);
		}
	}
	
	public void updateAvatar(Intent data, Activity m) {
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
		Thread t = new Thread() {
			public void run() {
				Looper.prepare();
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(urlUpload);
				FileInputStream in = null;
				try {
					in = new FileInputStream(path);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Bitmap bitmapOrg1 = BitmapFactory.decodeStream(in);
				/*ByteArrayOutputStream bao1 = new ByteArrayOutputStream();
				bitmapOrg1.compress(Bitmap.CompressFormat.JPEG, 90, bao1);
				byte[] imagearray1 = bao1.toByteArray();*/
				
				byte[] imagearray1 = Utils.reduceSize(bitmapOrg1);
				String ba1 = Base64.encodeToString(imagearray1, Base64.DEFAULT);
				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);
					nameValuePairs.add(new BasicNameValuePair("user_id", String
							.valueOf(user_id)));
					nameValuePairs.add(new BasicNameValuePair("uuid",
							uuid_friend));
					nameValuePairs.add(new BasicNameValuePair("image", ba1));
					httppost.addHeader(BasicScheme
							.authenticate(new UsernamePasswordCredentials(
									Constant.ITELL_USERNAME_AUTHEN,
									Constant.ITELL_PASS_AUTHEN), "UTF-8", false));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
//					InputStream is = null;
					if (response.getStatusLine().getStatusCode() == 200) {
						mHandler.sendEmptyMessage(5);
					//						HttpEntity entity = response.getEntity();
//						is = entity.getContent();
					}
					// read response data
//					try {
//						BufferedReader reader = new BufferedReader(
//								new InputStreamReader(is, "iso-8859-1"), 8);
//						StringBuilder sb = new StringBuilder();
//						String line = null;
//						while ((line = reader.readLine()) != null) {
//							sb.append(line + "\n");
//						}
//						is.close();
//						Log.i("NDT", "respond " + sb.toString());
//						System.out.println("reponse upload avatar: "
//								+ response.getStatusLine().getStatusCode()
//								+ " content: " + sb.toString());
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
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
									if (slideFinish) { // &&
														// onItemSlideChangedListener
														// != null){
										/*if (direction == SwipAdapter.LEFT) {
											upImage();
										} else if (direction == SwipAdapter.RIGHT) {
											// back
											onClickComponent.onClick(btnBack);
										}*/
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
	
	private void deleteImage(final int id){
		new Thread(){
			@Override
			public void run() {
				boolean c= false;
				c = Utils.deleteImage(String.valueOf(ItelApplication.user_id), ItelApplication.uuid,id);
				if (c){
					//success
					mHandler.sendEmptyMessage(2);
				}
				else {
					mHandler.sendEmptyMessage(1);
				}
			}
		}.start();
	}
	
	private void slideFinish(View v,int direction){
    	switch (v.getId()) {
		case (R.id.album_rl1):	
			if (direction==SwipAdapter.LEFT){
				//chat
				upImage();
			}
			else if (direction==SwipAdapter.RIGHT){
				//back
				onClickComponent.onClick(btnBack);
			}
			
			break;
		case (R.id.llAlbumItem1):	
			if (direction==SwipAdapter.LEFT){
				previewPhoto(urlPhoto1);
			}
			else {
				//delete
				int id1 =Integer.parseInt(llAlbumImage1.getTag().toString());
				deleteImage(id1);
				index = 1;
			}
			break;
		case (R.id.llAlbumItem2):	
			if (direction==SwipAdapter.LEFT){
				previewPhoto(urlPhoto2);
			}
			else {
				//delete
				//delete
				int id2 =Integer.parseInt(llAlbumImage2.getTag().toString());
				deleteImage(id2);
				index = 2;
			}
			break;
		case (R.id.llAlbumItem3):	
			if (direction==SwipAdapter.LEFT){
				previewPhoto(urlPhoto3);
			}
			else {
				//delete
				int id3 =Integer.parseInt(llAlbumImage3.getTag().toString());
				deleteImage(id3);
				index = 3;
			}
			break;
		default:
			break;
		}
    }
	
	/**
     *  preview image
     * @param url: link to show image
     */
 	private void previewPhoto(String url) {
         final Dialog dia = new Dialog(mContext,R.style.FullScreenDialog);
         dia.setContentView(R.layout.preview_dialog);
         ImageView imageView = (ImageView) dia.findViewById(R.id.profile_preview_imageview);
         Button okButton = (Button) dia.findViewById(R.id.profile_preview_ok_button);
         okButton.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 dia.cancel();
             }
         });

         if (url!=null){
             try {
                
                 WindowManager windowManager = mainActivity.getWindowManager();
                 Display display = windowManager.getDefaultDisplay();
                 int mScreenWidth;
                 int mScreenHeight;
                 mScreenWidth = display.getWidth();
                 mScreenHeight = display.getHeight();
  
                 DownloadImageTask down = new DownloadImageTask(imageView,mScreenWidth,mScreenHeight,
                		 										mContext,url);
                 mHandler.post(down);
                 downArr.add(down);
                 //down.execute(url);
                 dia.show();
             } catch (OutOfMemoryError e) {
                 Toast.makeText(mContext, mContext.getResources().getString(R.string.message_load_image_error), 
                		 		Toast.LENGTH_LONG).show();
                 e.printStackTrace();
             } catch (Exception e) {}
         }
     }
}
