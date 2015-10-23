package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.items.ItemSearch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemSearchView extends LinearLayout implements IView{
	private TextView tvHeader;
	private TextView tvStrength;
	private TextView tvContent;
	private ImageView imageView;
	public ItemSearchView(Context context) {
		super(context);
		config(R.layout.item_search);
	}

	public ItemSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.item_search);
	}

	private void config(int resource_layouy) {
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		tvHeader = (TextView) findViewById(R.id.textView1);
		tvContent = (TextView) findViewById(R.id.textView2);
		tvStrength=(TextView) findViewById(R.id.textView3);
		imageView = (ImageView) findViewById(R.id.imageView1);
	}

	public void updateData(String txtHeader,  String txtContent) {
		tvHeader.setText(txtHeader);
		tvContent.setText(txtContent);
		//tvStrength.setText(item.getStrength());
	}

	public void updateData(ItemSearch item) {
		tvHeader.setText(item.getTxtHeader());
		tvContent.setText(item.getTxtContent());
		tvStrength.setText(item.getTxtStrength());
	//	Log.i("cccccccccccccccccccccccccc", ""+item.getStrength());
		if (!item.isSpec()) {
			imageView.setVisibility(GONE);
		}

	}

	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		findViewById(R.id.llItemsearchContent).setLayoutParams(params);
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}