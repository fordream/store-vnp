package com.cnc.maispreco.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cnc.maispreco.adpters.CategoryApater;
import com.cnc.maispreco.soap.data.Category;

public class CategoryViewControl extends LinearLayout {
	private ListView lVCategory;
	private ArrayList<Category> alCategory = new ArrayList<Category>();
	private ArrayList<Category> alCategoryStore = new ArrayList<Category>();
	private ArrayAdapter<Category> adapter;
	private LoadMoreView loadMoreView;
	private int cutentPage = 1;
	private String LOCALID = Common.LOCALID;
	private String id1 = "";
	
	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public String getLOCALID() {
		return LOCALID;
	}

	public void setLOCALID(String lOCALID) {
		LOCALID = lOCALID;
	}

	public CategoryViewControl(Context context,String id1) {
		super(context);
		this.id1 = id1;
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.categoryview, this);
		loadMoreView = new LoadMoreView(context);
		loadMoreView.setType(LoadMoreView.TYPE_CATEGORY);
		lVCategory = (ListView) findViewById(R.id.lVCategory);
		lVCategory.addFooterView(loadMoreView);
		loadMoreView.setVisibility(View.GONE);
		adapter = new CategoryApater(context, R.layout.item_category,
				alCategory, loadMoreView);

		lVCategory.setAdapter(adapter);

		lVCategory.setOnItemClickListener(onItemClickListener);
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg1 instanceof LoadMoreView) {
				if (alCategory.size() < alCategoryStore.size()) {
					cutentPage++;
				}
				notifyDataSetChanged();
				return;
			}

			String id = alCategory.get(arg2).get(Category.ID);
			MaisprecoScreen maisprecoScreen = (MaisprecoScreen) getContext();
			maisprecoScreen.addCategoryView(id);
//			new Category1Asyn(maisprecoScreen, null, null, getResources(),
//					null, 1, false, null, new CategoryViewControl(
//							maisprecoScreen), new HomeViewControl(
//							maisprecoScreen)).execute(id);
		}
	};

	public void addCatagory(List<Category> lCategories) {
		alCategoryStore.addAll(lCategories);
		loadMoreView.post(new Runnable() {
			public void run() {
				loadMoreView.setData(alCategory.size() + "",
						alCategoryStore.size() + "");
				if (alCategory.size() < alCategoryStore.size()) {
					loadMoreView.setVisibility(VISIBLE);
				}

				if (cutentPage * ConfigurationView.page > alCategory.size()) {
					notifyDataSetChanged();
				}
			}
		});
	}

	public void notifyDataSetChanged() {
		alCategory.clear();
		for (int i = 0; i < cutentPage * ConfigurationView.page; i++) {
			if (i < alCategoryStore.size()) {
				alCategory.add(alCategoryStore.get(i));
			}
		}

		if (alCategory.size() < alCategoryStore.size()) {
			loadMoreView.setData(alCategory.size() + "", alCategoryStore.size()
					+ "");
			loadMoreView.setVisibility(VISIBLE);
		} else {
			loadMoreView.setVisibility(GONE);
		}

		adapter.notifyDataSetChanged();
	}
}