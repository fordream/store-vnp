package vnp.com.phone.cancel;

import vnp.com.phone.cancel.process.MProcess;
import vnp.com.phone.cancel.process.PROCESSTYPE;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ict.library.activity.CommonBaseActivity;
import com.ict.library.common.CommonResize;

public class LoginActivity extends CommonBaseActivity implements
		OnClickListener {
	private EditText editText, editText2;
	private static final int TEXTHEIGHT = 150;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		editText = getView(R.id.editText1);
		editText2 = getView(R.id.editText2);
		button = getView(R.id.button1);

		CommonResize._20130408_resizeW960(editText, TEXTHEIGHT * 4, TEXTHEIGHT);
		CommonResize
				._20130408_resizeW960(editText2, TEXTHEIGHT * 4, TEXTHEIGHT);
		CommonResize._20130408_resizeW960(button, TEXTHEIGHT * 4, TEXTHEIGHT);
		int textSize = CommonResize._20130408_getSizeByScreenW960(this,
				TEXTHEIGHT / 3);

		editText.setTextSize(textSize);
		editText2.setTextSize(textSize);
		button.setTextSize(textSize);

		button.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		String userName = editText.getText().toString();
		String password = editText2.getText().toString();

		MProcess mProcess = new MProcess(this) {
			@Override
			public void resultProcessor(Intent intent) {
				PROCESSTYPE result = (PROCESSTYPE) intent
						.getSerializableExtra("PROCESSTYPE");
				if (result == PROCESSTYPE.FAIL) {
					toast("fail");
				} else {
//					startActivity(new Intent(LoginActivity.this,
//							ListPhoneActivity.class));
//					finish();
				}
				
				startActivity(new Intent(LoginActivity.this,
						ListPhoneActivity.class));
				finish();
			}

		};

		Bundle bundle = new Bundle();
		bundle.putString("user", userName);
		bundle.putString("password", password);
		bundle.putSerializable("PROCESSTYPE", PROCESSTYPE.LOGIN);
		mProcess.executeAysn(bundle);

	}

	private void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}