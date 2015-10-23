package org.com.cnc.maispreco;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Redeem extends Activity implements OnClickListener{
	private Bundle b ;
	private String key ;
	private Button b1, b2 ;
	private ImageView im ;
	private String result= "" ;
	private ArrayList<Response> arrayList ;
	private ProgressBar progress ;
	private ImageView imgIcon ;
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle) ;
		setContentView(R.layout.check) ;
		//Button New Scan hoac Try Again
		b1 = (Button)findViewById(R.id.b1) ;
		b1.setOnClickListener(this) ;
		//Button Exit
		b2 = (Button)findViewById(R.id.b2) ;
		b2.setOnClickListener(this) ;
		
		//Image Icon
		im = (ImageView)findViewById(R.id.tv) ;
		b = this.getIntent().getExtras() ;
		key= b.getString("key") ;
		
		//Progress Bar
		progress = (ProgressBar)findViewById(R.id.progressBar1) ;
		imgIcon= (ImageView)findViewById(R.id.imvIcon) ;
		new UpdateUI().execute() ;
	}
	
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.b1:
				 if(result.equals("Redeem Successful")){
				 	startActivity(new Intent(this, Main.class)) ;
				 }else{
					 Bundle b = new Bundle() ;
				 	 b.putString("key",key) ;
				 	 startActivity(new Intent(this, Redeem.class).putExtras(b)) ;
				 }
				 this.finish() ;
				 break ;
			case R.id.b2:
				this.finish() ;
				break ;
		}
	}
	public ArrayList<Response> searchByKey( String key){
		StringBuffer surl = new StringBuffer();
		surl.append("http://95.131.66.248/StormVoucher/StormVoucher.svc/pox/VoucherCheck?action=redeem&barcode=");
		surl.append(key) ;
		try {
			URL url = new URL(surl.toString());
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader xmlreader = parser.getXMLReader();
			ParseResponse response = new ParseResponse();
			xmlreader.setContentHandler(response);
 			InputSource is = new InputSource(url.openStream());
			xmlreader.parse(is);
			return response.getResponse() ;
		} catch (Exception e) {
		}
		return null ;
	}
	public void createDialog(String content){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(content)
		.setCancelable(false)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				startActivity(new Intent(getBaseContext(), Main.class));
				finish() ;
			}
		});
		AlertDialog alert = builder.create();
		alert.show() ;
	}
	public class UpdateUI extends AsyncTask<Void, Void, ArrayList<Long>>{
		@Override
		protected ArrayList<Long> doInBackground(Void... params) {
			arrayList= searchByKey(key)  ;
			return null;
		}
		@Override
		protected void onPostExecute(ArrayList<Long> result1) {
			b1.setVisibility(View.VISIBLE) ;
			b2.setVisibility(View.VISIBLE) ;
			progress.setVisibility(View.GONE) ;
	 		if(arrayList!=null){
	 			imgIcon.setVisibility(View.VISIBLE) ;
				result = arrayList.get(0).getResponse();
				if(result.equals("Redeem Successful")){
					im.setBackgroundResource(R.drawable.redeemsuces) ;
					b1.setBackgroundResource(R.drawable.newscan) ;
					b2.setBackgroundResource(R.drawable.exit) ;
				}else{
					im.setBackgroundResource(R.drawable.notsuccessful) ;
					b1.setBackgroundResource(R.drawable.tryagain) ;
					b2.setBackgroundResource(R.drawable.exit);
				}		
			}else{
	        	createDialog("Network not connected!") ;
	        	b1.setVisibility(View.GONE) ;
	        	b2.setVisibility(View.GONE) ;
	        	im.setVisibility(View.GONE) ;
	        }
			super.onPostExecute(result1);
		}
	}
}
