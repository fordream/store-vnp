package org.com.vnp.shortheart;

import java.util.List;
import org.com.vnp.shortheart.database.DBAdapter;

import com.ict.library.common.CommonAndroid;
import com.ict.library.common.CommonResize;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuScreen extends MBaseActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MenuScreen.this,
						CaoThuBongBongScreen.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				List<Integer> list = new DBAdapter(MenuScreen.this).getlScore();
				String text = "";
				for (int i = list.size() - 1; i >= 0; i--) {
					text += (list.size() - i) + " : " + list.get(i) + "\n";
				}

				Builder builder = new Builder(MenuScreen.this);
				builder.setTitle("Score");
				builder.setCancelable(false);
				builder.setMessage(text);
				builder.setPositiveButton("OK", null);
				builder.show();
			}
		});
		findViewById(R.id.Button01).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonAndroid.showMarketPublish(v.getContext(), "Truong Vuong Van");
			}
		});

		CommonResize.resizeLandW480H320(findViewById(R.id.button2), 400, 50);
		CommonResize.resizeLandW480H320(findViewById(R.id.button1), 400, 50);
		CommonResize.resizeLandW480H320(findViewById(R.id.Button01), 400, 50);
	}
}
