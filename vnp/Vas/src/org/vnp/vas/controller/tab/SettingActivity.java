package org.vnp.vas.controller.tab;

import java.util.ArrayList;

import org.vnp.vas.R;
import org.vnp.vas.controller.LoginActivity;
import org.vnp.vas.model.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ict.library.activity.CommonBaseActivity;
import com.ict.library.adapter.CommonBaseAdapter;

public class SettingActivity extends CommonBaseActivity {
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		listView = getView(R.id.listView1);

		listView.addFooterView(createBottom());
		listView.setAdapter(new CommonBaseAdapter(this, new ArrayList<Object>(), null));
	}

	private View createBottom() {
		LinearLayout layout = new LinearLayout(this);
		layout.setGravity(Gravity.CENTER);
		layout.setPadding(10, 5, 10, 5);
		Button button = new Button(this);
		button.setText("Logout");
		layout.addView(button);
		ViewGroup.LayoutParams params = button.getLayoutParams();
		params.width = LayoutParams.FILL_PARENT;
		button.setLayoutParams(params);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Login login = new Login();
				login.setLogin(false);
				startActivity(new Intent(SettingActivity.this,
						LoginActivity.class));
				finish();
			}
		});
		return layout;
	}
}