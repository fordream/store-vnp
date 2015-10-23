package vn.vvn.bibook.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import vn.vvn.bibook.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

public class Splash extends Activity {
	
	public static final String SHARE_PREF = "share pref";
	public static final String CATEGORIES = "categories";
	Bitmap mBmSplash;
	LinearLayout mLlSplash;
	ProgressThread progressThread;
	SharedPreferences sharePrefs;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        mLlSplash = (LinearLayout) findViewById(R.id.llSplash);
        BitmapFactory.Options opts = new Options();
        opts.inPreferredConfig = Config.RGB_565;
        mBmSplash = BitmapFactory.decodeResource(getResources(), R.drawable.load_screen, opts);
        sharePrefs = getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE);
        mLlSplash.setBackgroundDrawable(new BitmapDrawable(mBmSplash));
        progressThread = new ProgressThread(handler);
        progressThread.start();
    }
    
    
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int complete = msg.arg1;
            
            if (complete >= 0){
                progressThread.setState(ProgressThread.STATE_DONE);
                progressThread.interrupt();
                Intent i = new Intent(Splash.this, TopScreen.class);
//                Intent i = new Intent(Splash.this, SetNameBook.class);
                startActivity(i);
                finish();
            }
        }
    };
    
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
            	String sdcard = Environment.getExternalStorageDirectory().toString();
            	String dir = sdcard + "/SmartBook";
            	File dirBook = new File(dir);
        		if (!dirBook.isDirectory() || !dirBook.exists()) {
        			dirBook.mkdirs();
        		}
        		try {
					HttpParams params = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					HttpConnectionParams.setSoTimeout(params, 4000);
					HttpProtocolParams.setContentCharset(params, "UTF-8");
					HttpGet httpget = new HttpGet(new URL(getString(R.string.url_view_categories)).toURI());
					HttpClient httpclient = new DefaultHttpClient(params);
					HttpResponse response = httpclient.execute(httpget);
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						InputStream instream = entity.getContent();
						BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
						String line = null;
						line = reader.readLine();
						Editor e = sharePrefs.edit();
						e.putString(CATEGORIES, line);
						e.commit();
					}
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (ClientProtocolException e1) {
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    Log.e("ERROR", "Thread Interrupted");
//                }
                Message msg = mHandler.obtainMessage();
                msg.arg1 = total;
                mHandler.sendMessage(msg);
                total++;
            }
        }
        
        /* sets the current state for the thread,
         * used to stop the thread */
        public void setState(int state) {
            mState = state;
        }
    }
}
