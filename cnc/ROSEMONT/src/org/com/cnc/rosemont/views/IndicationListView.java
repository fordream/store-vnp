package org.com.cnc.rosemont.views;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.HomeActivity;
import org.com.cnc.rosemont.activity.SortBy;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.adapter.IndicationAdapter;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.items.ItemSearch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class IndicationListView extends LinearLayout implements
		OnItemClickListener, IView {
	private IndicationAdapter adapter;
	private AnimationSlide animationSlide=new AnimationSlide();
	public IndicationListView(Context context) {
		super(context);
		config(R.layout.productlist1);
	}

	public IndicationListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.productlist1);
	}

	private void config(int resource_layouy) {
	//	setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);

		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);

		// headerView
		// .setText(resources.getString(string.tab_home_indication_List));
		// headerView.setType(HeaderView.TYPE_HOME_MAIN);

		// headerView.setText(getResources().getString(string.key6));
		headerView.setType(HeaderView.TYPE_LIST_INDICATIONS);
		headerView.showButton(true, false);
		headerView.setOnClick(onClickBack, null);

		ListView listView = (ListView) findViewById(R.id.listView1);
		// ImageView imageView = new ImageView(getContext());
		// imageView.setBackgroundResource(R.drawable.img_list_product_by);
		// listView.addHeaderView(imageView);

		adapter = new IndicationAdapter(getContext(), listView,
				new ArrayList<ItemSearch>());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		adapter.setData(addDataExample());
	}

	private OnClickListener onClickBack = new OnClickListener() {
		public void onClick(View v) {
			if (isTabHome()) {
				
				((HomeActivity) getContext()).onBack();
				//setAnimation(animationSlide.outToLeftAnimation());
			}
		}
	};

	private boolean isTabHome() {
		return getContext() instanceof HomeActivity;
	}

	// real data
	private List<ItemSearch> addDataExample() {
		List<ItemSearch> lItemSearchs = new ArrayList<ItemSearch>();
		for (int i = 0; i < CommonApp.ROSEMONT.sizeOfRow(); i++) {

			int index = CommonApp.ROSEMONT
					.getIndexColumns(RosemontTable.MAIN_INDICATIONS);
			String data = CommonApp.ROSEMONT.getRow(i).get(index);
			if (data != null && !data.trim().equals("")) {
				StringTokenizer tokenizer = new StringTokenizer(data, ",");
				for (int j = 0; j < tokenizer.countTokens(); j++) {
					String data1 = tokenizer.nextToken().trim();
					ItemSearch item = new ItemSearch();
					item.setTxtHeader(data1);
					boolean check = false;
					for (int k = 0; k < lItemSearchs.size(); k++) {
						ItemSearch itemSearch = lItemSearchs.get(k);
						if (item.getTxtHeader().equals(
								itemSearch.getTxtHeader())) {
							check = true;
							break;
						}
					}

					if (!check && !Common.isNullOrBlank(item.getTxtHeader())) {
						lItemSearchs.add(item);
					}
				}
			}
		}

		return SortBy.sortAndAddHeader(lItemSearchs);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		ItemSearch item = adapter.getItem(position);
		if (!item.isAnphabe()) {

			int type = Category_Indication_Detail.TYPE_INDICATION;
			String content = item.getTxtHeader();

			IActivity iActivity = ((IActivity) getContext());
			iActivity.gotoCategory_Indication_Detail(type, content);
		}
	}

	public void reset() {
		  CommonApp.getDataROSEMONT(getContext());
		  adapter.setData(addDataExample());
		  adapter.notifyDataSetChanged();
		 }

}
