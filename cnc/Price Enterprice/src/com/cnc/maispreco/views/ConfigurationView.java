package com.cnc.maispreco.views;

import org.com.cnc.common.database.DBAdapterPage;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;

import com.cnc.maispreco.asyn.AbountAsyn;
import com.cnc.maispreco.asyn.ContactUsAsyn;
import com.cnc.maispreco.asyn.HelpAsyn;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ConfigurationView extends LinearLayout {
	public static int page = 5;
	private RelativeLayout rl5items;
	private RelativeLayout rl10items;
	private RelativeLayout rl30items;
	private ImageView img5items;
	private ImageView img10items;
	private ImageView img30items;

	private RelativeLayout rlAbout;
	private RelativeLayout rlContacts;
	private RelativeLayout rlHep;

	private Context context;

	public RelativeLayout getRlAbout() {
		return rlAbout;
	}

	public void setRlAbout(RelativeLayout rlAbout) {
		this.rlAbout = rlAbout;
	}

	public RelativeLayout getRlContacts() {
		return rlContacts;
	}

	public void setRlContacts(RelativeLayout rlContacts) {
		this.rlContacts = rlContacts;
	}

	public RelativeLayout getRlHep() {
		return rlHep;
	}

	public void setRlHep(RelativeLayout rlHep) {
		this.rlHep = rlHep;
	}

	public ConfigurationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		config();

	}

	public ConfigurationView(Context context) {
		super(context);
		this.context = context;
		config();

	}

	private void configPage() {
		rl5items = (RelativeLayout) findViewById(R.id.rl5items);
		rl10items = (RelativeLayout) findViewById(R.id.rl10items);
		rl30items = (RelativeLayout) findViewById(R.id.rl30items);
		img5items = (ImageView) findViewById(R.id.img5items);
		img10items = (ImageView) findViewById(R.id.img10items);
		img30items = (ImageView) findViewById(R.id.img30items);
		page = new DBAdapterPage(context).getPage();
		img5items.setBackgroundResource(page == 5 ? R.drawable.check
				: R.drawable.uncheck);
		img10items.setBackgroundResource(page == 10 ? R.drawable.check
				: R.drawable.uncheck);
		img30items.setBackgroundResource(page == 30 ? R.drawable.check
				: R.drawable.uncheck);
		//
		// Builder builder = new Builder(context);
		// builder.setMessage("" + page);
		// builder.show();
		rl5items.setOnClickListener(new OnClickListenerPage());
		rl10items.setOnClickListener(new OnClickListenerPage());
		rl30items.setOnClickListener(new OnClickListenerPage());
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.configurationscreen, this, true);
		// llConfiguration = (LinearLayout) findViewById(R.id.llConfiguration);
		configPage();
		configAbaout();
	}

	private void configAbaout() {
		rlAbout = (RelativeLayout) findViewById(R.id.rlAbout);
		rlContacts = (RelativeLayout) findViewById(R.id.rlContacts);
		rlHep = (RelativeLayout) findViewById(R.id.rlHep);

		OnClickListener listener = new OnClickListener() {

			public void onClick(View v) {
				MaisprecoScreen maisprecoScreen = (MaisprecoScreen) getContext();
				if (v.getId() == getRlAbout().getId()) {
					new AbountAsyn(maisprecoScreen).execute("");
				} else if (v.getId() == getRlHep().getId()) {
					new HelpAsyn(maisprecoScreen).execute("");
				} else if (v.getId() == getRlContacts().getId()) {
					new ContactUsAsyn(maisprecoScreen).execute("");
				}

			}
		};
		rlAbout.setOnClickListener(listener);
		rlHep.setOnClickListener(listener);
		rlContacts.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");

				String aEmailList[] = { "contato@maispreco.com" };
				String aEmailCCList[] = {};
				String aEmailBCCList[] = {};

				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						aEmailList);
				emailIntent.putExtra(android.content.Intent.EXTRA_CC,
						aEmailCCList);
				emailIntent.putExtra(android.content.Intent.EXTRA_BCC,
						aEmailBCCList);

				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Fale Conosco");

				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

				getContext().startActivity(
						Intent.createChooser(emailIntent, "Send mail..."));
				// }
				// Intent intent = new Intent(Intent.ACTION_SEND);
				// intent.setType("text/html");
				// intent.setType("plain/text");
				//
				// intent.putExtra(Intent.EXTRA_EMAIL, new
				// String[]{"app@noblejohnston.com"});
				// intent.putExtra(Intent.EXTRA_CC, new
				// String[]{"app@nja.com"});
				// intent.putExtra(Intent.EXTRA_SUBJECT, "Quote from App");
				// intent.putExtra(Intent.EXTRA_TEXT, "ssss");
				// getContext().startActivity(Intent.createChooser(intent,
				// "send"));

			}
		});
	}

	private class OnClickListenerPage implements OnClickListener {
		public void onClick(View v) {

			boolean isChange = false;
			DBAdapterPage adapterPage = new DBAdapterPage(context);
			if (v.getId() == rl5items.getId()) {
				if (page != 5) {
					isChange = true;
				}
				page = 5;

				img5items.setBackgroundResource(R.drawable.check);
				img10items.setBackgroundResource(R.drawable.uncheck);
				img30items.setBackgroundResource(R.drawable.uncheck);
			} else if (v.getId() == rl10items.getId()) {
				if (page != 10) {
					isChange = true;
					// change();
				}
				page = 10;
				img5items.setBackgroundResource(R.drawable.uncheck);
				img10items.setBackgroundResource(R.drawable.check);
				img30items.setBackgroundResource(R.drawable.uncheck);
			} else if (v.getId() == rl30items.getId()) {
				if (page != 30) {
					isChange = true;
					// change();
				}
				page = 30;
				img5items.setBackgroundResource(R.drawable.uncheck);
				img10items.setBackgroundResource(R.drawable.uncheck);
				img30items.setBackgroundResource(R.drawable.check);
			}

			adapterPage.updatePage(page);

			if (isChange) {
				change();
			}

		}

		private void change() {
			for (int i = 0; i < MaisprecoScreen.lViewHome.size(); i++) {
				notifyDataSetChanged(MaisprecoScreen.lViewHome.get(i));
			}

			for (int i = 0; i < MaisprecoScreen.lViewCategory.size(); i++) {
				notifyDataSetChanged(MaisprecoScreen.lViewCategory.get(i));
			}
		}
	}

	private void notifyDataSetChanged(View view) {
		if (view == null) {
			return;
		} else if (view instanceof CategoryViewControl) {
			((CategoryViewControl) view).notifyDataSetChanged();
		} else if (view instanceof OffersViewControl) {
			((OffersViewControl) view).notifyDataSetChangedBesprice();
			((OffersViewControl) view).notifyDataSetChangedNearyBy();
		} else if (view instanceof HomeViewControl) {
			((HomeViewControl) view).notifyDataSetChanged();
		} else if (view instanceof GenericProductsViewControl) {
			((GenericProductsViewControl) view).notifyDataSetChanged();
		} else if (view instanceof SearchViewControl) {
			((SearchViewControl) view).notifyDataSetChanged();
		} else if (view instanceof SimilarProductsViewControl) {
			((SimilarProductsViewControl) view).notifyDataSetChanged();
		}
	}
}
