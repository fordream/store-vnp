package org.com.cnc.rosemont.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.HomeActivity;
import org.com.cnc.rosemont.activity.SortBy;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.adapter.CategoryAdapter;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.items.ItemSearch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CategoryListView extends LinearLayout implements
		OnItemClickListener,IView {
//	public AnimationSlide animationSlide=new AnimationSlide();
	private CategoryAdapter adapter;
	
	public CategoryListView(Context context) {
		super(context);
		config(R.layout.productlist1);
	}

	public CategoryListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.productlist1);
	}

	private void config(int resource_layouy) {
		//setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);

		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		// headerView.setText(resources.getString(string.tab_home_category_List));
		// headerView.setType(HeaderView.TYPE_HOME_MAIN);
	//	headerView.setText(getResources().getString(string.key5));
		headerView.setType(HeaderView.TYPE_LIST_CATEGORIES);
		headerView.showButton(true, false);
		headerView.setOnClick(onClickBack, null);

		ListView listView = (ListView) findViewById(R.id.listView1);
		
		ImageView imageView = new ImageView(getContext());
		imageView.setBackgroundResource(R.drawable.img_list_product_by);
		listView.addHeaderView(imageView);
		adapter = new CategoryAdapter(getContext(), listView,
				new ArrayList<ItemSearch>());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		adapter.setData(addDataExample());
	}

	// real data
	private List<ItemSearch> addDataExample() {
		List<ItemSearch> lItemSearchs = new ArrayList<ItemSearch>();
		for (int i = 0; i < CommonApp.ROSEMONT.sizeOfRow(); i++) {
			ItemSearch item = new ItemSearch();
			int index = CommonApp.ROSEMONT
					.getIndexColumns(RosemontTable.CATEGORY_NAME);
			String data = CommonApp.ROSEMONT.getRow(i).get(index);
			Log.i("aaaaaaaaaaaaaaaaaaa", ""+data);
			item.setTxtHeader(data);
			boolean check = false;
			for (int j = 0; j < lItemSearchs.size(); j++) {
				ItemSearch itemSearch = lItemSearchs.get(j);
				if (item.getTxtHeader().equals(itemSearch.getTxtHeader())) {
					check = true;
					break;
				}
			}
			if (!check) {
				lItemSearchs.add(item);
			}
		}

		return SortBy.sortAndAddHeader(lItemSearchs);
	}

	private OnClickListener onClickBack = new OnClickListener() {
		public void onClick(View v) {
			if (isTabHome()) {
				//setAnimation(animationSlide.outToLeftAnimation());
				((HomeActivity) getContext()).onBack();
			}
		}
	};

	private boolean isTabHome() {
		return getContext() instanceof HomeActivity;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		ItemSearch item = (ItemSearch) arg0.getItemAtPosition(position);

		if (!item.isAnphabe()) {
			((IActivity) getContext()).gotoCategory_Indication_Detail(
					Category_Indication_Detail.TYPE_CATEGORY,
					item.getTxtHeader());
		}
	}

	public void reset() {
		  CommonApp.getDataROSEMONT(getContext());
		  adapter.setData(addDataExample());
		  adapter.notifyDataSetChanged();
		 }

}
