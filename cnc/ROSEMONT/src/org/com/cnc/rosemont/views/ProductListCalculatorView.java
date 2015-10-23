package org.com.cnc.rosemont.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.SortBy;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.adapter.ProductAdapter;
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

public class ProductListCalculatorView extends LinearLayout implements
		OnItemClickListener, IView {
	private ProductAdapter adapter;
	private RosemontTable rosemontTable = new RosemontTable();
	//private AnimationSlide animationSlide=new AnimationSlide();

	public ProductListCalculatorView(Context context,
			RosemontTable rosemontTable) {

		super(context);
		this.rosemontTable = rosemontTable;
		config(R.layout.productlist1);
	}

	public ProductListCalculatorView(Context context, AttributeSet attrs,
			RosemontTable rosemontTable) {
		super(context, attrs);
		this.rosemontTable = rosemontTable;
		config(R.layout.productlist1);
	}

	private void config(int resource_layouy) {
	//	setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);

		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		// headerView.setType(HeaderView.TYPE_LIST_PRODUCT);
		headerView.setText(getResources().getString(R.string.list_of_product));
		headerView.showButton(true, false);
		headerView.setOnClick(onClickBack, null);

		ListView listView = (ListView) findViewById(R.id.listView1);
		adapter = new ProductAdapter(getContext(), listView,
				new ArrayList<ItemSearch>());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		adapter.setData(addDataExample());
	}

	private OnClickListener onClickBack = new OnClickListener() {
		public void onClick(View v) {
			((IActivity) getContext()).onBack1();
		}
	};

	// real data
	private List<ItemSearch> addDataExample() {
		return SortBy.sortAndAddHeader(rosemontTable.search("", true));
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		ItemSearch item = adapter.getItem(position);
		if (!item.isAnphabe()) {
			String id = item.getId();
			String idTable = item.getIdTable();
			String strength = item.getTxtHeader();
			((IActivity) getContext()).updateCalculator(id, idTable, strength);
		}
	}

	public void reset() {
		adapter.setData(addDataExample());
		adapter.notifyDataSetChanged();
	}
}
