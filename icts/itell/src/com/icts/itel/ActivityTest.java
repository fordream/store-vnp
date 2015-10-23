package com.icts.itel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityTest extends Activity {
	private static String userAuthen = "itell";
	private static String passAuthen = "itell@2012";
	private String urlJsonUser = "http://49.212.140.145/itell/profile/get_profile?user_id=12";
	private String urlInviteFriend = "http://49.212.140.145/itell/profile/send_invite";
	private String urlDeleteFriend = "http://49.212.140.145/itell/profile/delete_friend";
	private String urlUploadAvatar = "http://49.212.140.145/itell/setting/upload_avatar";
	private int user = 12;
	private String uid = "26234213123d6f182a46e615ac0d8a55";
	private final String INTENT_CAPTURE_AVATAR = "INTENT_CAPTURE_AVATAR";
	private static String avatarPhotoPath = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getJsonProfileData();
		// sendInviteFriend(urlInviteFriend, "13",
		// "df0f9070f0367f8b8645121abac3b445", "11");
		// deleteFriend(urlDeleteFriend, "13",
		// "df0f9070f0367f8b8645121abac3b445",
		// "11");
		setContentView(R.layout.layout_profile_setting);
		initPhotoAlbum(this);
	}

	// code upload photo album
	ImageView[] photoList;
	ImageView imgViewAvatar;

	void initPhotoAlbum(Context _context) {
		// photoList = new ImageView[3];
		// photoList[0] = (ImageView) findViewById(R.id.imageview5);
		// photoList[1] = (ImageView) findViewById(R.id.imageview6);
		// photoList[2] = (ImageView) findViewById(R.id.imageview7);
		// photoList[0].setOnClickListener(photoSelectOnClickListener);
		// photoList[1].setOnClickListener(photoSelectOnClickListener);
		// photoList[2].setOnClickListener(photoSelectOnClickListener);
		imgViewAvatar = (ImageView) findViewById(R.id.avatar);
		imgViewAvatar.setOnClickListener(photoSelectOnClickListener);

	}

	OnClickListener photoSelectOnClickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			createPhoto(getApplicationContext());
			// int index = 0;
			// for (int i = 0; i < photoList.length; i++) {
			// if (photoList[i] == ((ImageView) v))
			// index = i;
			// }
		}
	};

	public void createPhoto(final Context _context) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setItems(getResources().getStringArray(R.array.cameraUploadItems),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							Intent intent = new Intent(_context,
									CameraActivity.class);
							intent.putExtra(INTENT_CAPTURE_AVATAR,
									avatarPhotoPath);
							startActivityForResult(intent, REQUEST_CAMERA_TAKE);
							break;
						case 1:
							Intent intent1 = new Intent(
									Intent.ACTION_GET_CONTENT);
							intent1.setType("image/*");
							startActivityForResult(intent1,
									REQUEST_PHOTO_SELECT);
							break;

						default:
							break;
						}
					}
				});
		AlertDialog alertDialog = adb.create();
		alertDialog.show();

	}

	private final int REQUEST_PHOTO_SELECT = 3;
	private final int REQUEST_CAMERA_TAKE = 4;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if ((resultCode == RESULT_OK) && (requestCode == REQUEST_PHOTO_SELECT)) {
			Uri select = data.getData();
			String[] str = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(select, str, null, null, null);
			int coloumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String s = cursor.getString(coloumn);
			System.out.println("path: " + s);
			uploadPhoto(urlUploadAvatar, user, uid, s);
			if (s.endsWith(".jpg") || s.endsWith(".jpeg") || s.endsWith(".png")
					|| s.endsWith(".bmp") || s.endsWith(".gif")) {
				try {
					File imgFile = new File(s);
					// for (int i = 0; i < photoList.length; i++) {
					if (imgFile.exists()) {
						Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
								.getAbsolutePath());
						myBitmap = CameraActivity.scaleImage(myBitmap, 50, 50);
						imgViewAvatar.setImageBitmap(myBitmap);
						// photoList[i].setImageBitmap(myBitmap);
						// }
					}
				} catch (OutOfMemoryError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// try {
				// for (int i = 0; i < photoList.length; i++) {
				// ContentResolver cr = getApplicationContext()
				// .getContentResolver();
				// Uri uri = Uri.parse(s);
				// InputStream is = null;
				// is = cr.openInputStream(uri);
				// BitmapFactory.Options opt = new BitmapFactory.Options();
				// opt.inSampleSize = 4;
				// Bitmap bmp = BitmapFactory.decodeStream(is, null, opt);
				// bmp = CameraActivity.scaleImage(bmp, 50, 50);
				// photoList[i].setImageBitmap(bmp);
				// }
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			} else {
				Toast.makeText(this, "Sai dinh dang", Toast.LENGTH_SHORT)
						.show();
			}
		} else if ((resultCode == RESULT_OK)) {
			avatarPhotoPath = (String) data.getExtras().get(
					INTENT_CAPTURE_AVATAR);
			System.out.println("path: " + avatarPhotoPath);
			uploadPhoto(urlUploadAvatar, user, uid, avatarPhotoPath);
			try {
				File imgFile = new File(avatarPhotoPath);
				if (imgFile.exists()) {
					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
							.getAbsolutePath());
					myBitmap = CameraActivity.scaleImage(myBitmap, 50, 50);
					imgViewAvatar.setImageBitmap(myBitmap);
				}
			} catch (OutOfMemoryError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * get data user profile
	 */
	public void getJsonProfileData() {
		JSONObject jObject = getJSONfromURL(urlJsonUser);
		if (jObject != null) {
			try {
				String user_data = String.valueOf(jObject
						.getString("user_data"));
				String timestamp = String.valueOf(jObject
						.getString("timestamp"));
				JSONObject json2 = jObject.getJSONObject("user_data");
				String name = (String) json2.get("name");
				String nick = (String) json2.get("nick");
				boolean gender = (boolean) json2.getBoolean("gender");
				System.out.println("timestamp: " + timestamp + " name: " + name
						+ " user_data: " + user_data);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * send invite user
	 * 
	 * @param urlPost
	 * @param user_id
	 * @param uuid_friend
	 * @param friend_id
	 */
	void sendInviteFriend(final String urlPost, final String user_id,
			final String uuid_friend, final String friend_id) {
		Thread t = new Thread() {
			public void run() {
				Looper.prepare();
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(urlPost);
				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);
					nameValuePairs.add(new BasicNameValuePair("user_id",
							user_id));
					nameValuePairs.add(new BasicNameValuePair("uuid",
							uuid_friend));
					nameValuePairs.add(new BasicNameValuePair("friend_id",
							friend_id));
					httppost.addHeader(BasicScheme.authenticate(
							new UsernamePasswordCredentials(userAuthen,
									passAuthen), "UTF-8", false));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					InputStream is = null;
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						is = entity.getContent();
					}
					// read response data
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is, "iso-8859-1"), 8);
						StringBuilder sb = new StringBuilder();
						String line = null;
						while ((line = reader.readLine()) != null) {
							sb.append(line + "\n");
						}
						is.close();
						System.out.println("reponse: "
								+ response.getStatusLine().getStatusCode()
								+ " content: " + sb.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				}
				Looper.loop();
			}
		};
		t.start();
	}

	void deleteFriend(final String urlDelete, final String user_id,
			final String uuid_friend, final String friend_id) {
		Thread t = new Thread() {
			public void run() {
				Looper.prepare();
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(urlDelete);
				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);
					nameValuePairs.add(new BasicNameValuePair("user_id",
							user_id));
					nameValuePairs.add(new BasicNameValuePair("uuid",
							uuid_friend));
					nameValuePairs.add(new BasicNameValuePair("friend_id",
							friend_id));
					httppost.addHeader(BasicScheme.authenticate(
							new UsernamePasswordCredentials(userAuthen,
									passAuthen), "UTF-8", false));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					InputStream is = null;
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						is = entity.getContent();
					}
					// read response data
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is, "iso-8859-1"), 8);
						StringBuilder sb = new StringBuilder();
						String line = null;
						while ((line = reader.readLine()) != null) {
							sb.append(line + "\n");
						}
						is.close();
						System.out.println("reponse: "
								+ response.getStatusLine().getStatusCode()
								+ " content: " + sb.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				}
				Looper.loop();
			}
		};
		t.start();
	}

	public static String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
		System.out.println("LOOK" + imageEncoded);
		return imageEncoded;
	}

	public static Bitmap decodeBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

	void uploadPhoto(final String urlUpload, final int user_id,
			final String uuid_friend, final String path) {
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
				Log.i("NDT", "user_id "+user_id+" "+uuid_friend);
				File file = new File(path);
				Bitmap bitmapOrg1 = BitmapFactory.decodeStream(in);
				ByteArrayOutputStream bao1 = new ByteArrayOutputStream();
				bitmapOrg1.compress(Bitmap.CompressFormat.JPEG, 90, bao1);
				byte[] imagearray1 = bao1.toByteArray();
				 String ba1= Base64.encodeToString(imagearray1,
				 Base64.DEFAULT);
				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);
					nameValuePairs.add(new BasicNameValuePair("user_id", String
							.valueOf(user_id)));
					nameValuePairs.add(new BasicNameValuePair("uuid",
							uuid_friend));
					nameValuePairs.add(new BasicNameValuePair("image",
							ba1));
					httppost.addHeader(BasicScheme.authenticate(
							new UsernamePasswordCredentials(userAuthen,
									passAuthen), "UTF-8", false));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					InputStream is = null;
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						is = entity.getContent();
					}
					// read response data
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is, "iso-8859-1"), 8);
						StringBuilder sb = new StringBuilder();
						String line = null;
						while ((line = reader.readLine()) != null) {
							sb.append(line + "\n");
						}
						is.close();
						Log.i("NDT", "respond "+sb.toString());
						System.out.println("reponse upload avatar: "
								+ response.getStatusLine().getStatusCode()
								+ " content: " + sb.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				}
				Looper.loop();
			}
		};
		t.start();
	}

	/**
	 * jsonObject get
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject getJSONfromURL(String url) {
		// initialize
		InputStream is = null;
		String result = "";
		JSONObject jObject = null;
		try {
			HttpParams httpParams = new BasicHttpParams();
			// HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			// use and pp
			HttpGet httpGet = new HttpGet(url);
			// add autheticate if co'
			httpGet.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(userAuthen, passAuthen),
					"UTF-8", false));
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			}
		} catch (Exception e) {
		}
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// try parse the string to a JSON object
		try {
			jObject = new JSONObject(result);
		} catch (JSONException e) {
		}
		return jObject;
	}

}
