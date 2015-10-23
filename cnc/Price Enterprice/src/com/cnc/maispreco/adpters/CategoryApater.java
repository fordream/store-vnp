package com.cnc.maispreco.adpters;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.cnc.maispreco.soap.data.Category;
import com.cnc.maispreco.views.CategoryView;
import com.cnc.maispreco.views.LoadMoreView;

public class CategoryApater extends ArrayAdapter<Category> {
	private List<Category> productList = new ArrayList<Category>();
	//private LoadMoreView loadMoreView;
	public CategoryApater(Context context, int textViewResourceId,
			ArrayList<Category> objects, LoadMoreView loadMoreView) {
		super(context, textViewResourceId, objects);
		productList = objects;
		//this.loadMoreView = loadMoreView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View workView = convertView;

		if (workView == null) {
			workView = new CategoryView(getContext());
		}

		final Category catagory = productList.get(position);

		if (catagory != null) {
			((CategoryView) workView).setData(catagory.get(Category.NAME));
		}
		return workView;
	}

	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		
	}
}