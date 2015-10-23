package vnp.com.phone.cancel;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import vnp.com.phone.cancel.process.MProcess;
import vnp.com.phone.cancel.process.PROCESSTYPE;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.ict.library.activity.CommonBaseListActivity;
import com.ict.library.adapter.CommonBaseAdapter;
import com.ict.library.adapter.CommonGenderView;
import com.ict.library.database.CommonDataStore;
import com.ict.library.view.CustomLinearLayoutView;
import com.vnp.core.basegame.HeaderView;

public class ListPhoneActivity extends CommonBaseListActivity implements
		OnItemClickListener {
	private CommonBaseAdapter commonBaseAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HeaderView headerView = new HeaderView(this);
		headerView.setTextHeader("Contact list");
		headerView.setPadding(0, 0, 0, 1);
		getListView().addHeaderView(headerView);
		List<Object> list = new ArrayList<Object>();

		getListView().setAdapter(
				(commonBaseAdapter = new CommonBaseAdapter(this, list,
						new CommonGenderView() {
							@Override
							public CustomLinearLayoutView getView(Context arg0,
									Object arg1) {
								HeaderView headerView = new HeaderView(arg0);
								return headerView;
							}
						}) {
					@Override
					public View getView(int position, View convertView,
							ViewGroup parent) {
						HeaderView view = (HeaderView) super.getView(position,
								convertView, parent);

						view.setPadding(0, 0, 0, 1);
						return view;
					}
				}));

		getListView().setOnItemClickListener(this);
		setContent();
	}

	private void setContent() {
		CommonDataStore.getInstance().init(this);
		String content = CommonDataStore.getInstance().get("phonesave", "");
		content = "123456<;>12345<;>0987163199<;>12345<;>0987163199<;>12345<;>0987163199<;>12345<;>0987163199<;>12345<;>0987163199<;>12345<;>0987163199";
		StringTokenizer stringTokenizer = new StringTokenizer(content, "<;>");
		List<Object> list = new ArrayList<Object>();
		while (stringTokenizer.hasMoreElements()) {
			list.add(stringTokenizer.nextElement());
		}

		commonBaseAdapter.addAll(list);
	}

	@Override
	protected void onUserLeaveHint() {
		finish();
		super.onUserLeaveHint();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && RESULT_OK == resultCode) {
			String numberPhone = "numberPhone";
			Bundle bundle = new Bundle();
			bundle.putString("numberPhone", numberPhone);
			bundle.putSerializable("PROCESSTYPE", PROCESSTYPE.SAVE_NUMBER_PHONE);
			MProcess mProcess = new MProcess(this) {
				@Override
				public void resultProcessor(Intent intent) {
					// PROCESSTYPE result = (PROCESSTYPE) intent
					// .getSerializableExtra("PROCESSTYPE");
					// if (result == PROCESSTYPE.FAIL) {
					// } else {
					// }
					setContent();
				}
			};

			mProcess.executeAysn(bundle);

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		if (position != 0) {
			String number = adapterView.getItemAtPosition(position).toString();
			Builder builder = new Builder(arg1.getContext());
			builder.setTitle(number);
			builder.setItems(new String[] { "Call", "Message", "Delete" },
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == 2) {
								// delete
							} else if (which == 0) {
								// call
							} else if (which == 1) {
								// send mail
							}
						}
					});
			builder.show();
		}
	}
}