package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.activity.Activity;
import org.com.cnc.rosemont.activity.HomeActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class HomeView extends LinearLayout implements IView,OnClickListener {
	private HeaderView headerView;
	private View keywordsearch;
	private View searchByName;
	private View searchByCategory;
	private View searchByIndications;
//	private AnimationSlide animationSlide=new AnimationSlide();
	private View legal;
	private View company;
	private View adverserse;
	private ViewFlipper flipper;
	public HomeView(Context context) {
		super(context);
		config(R.layout.home);
	}

	public HomeView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		config(R.layout.home);
	}
	private void config(int resource_layouy) {
	//	setAnimation(animationSlide.inFromRightAnimation());
		
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		
		headerView = (HeaderView) findViewById(R.id.headerView1);

		headerView.showButton(false, false);
		headerView.setType(HeaderView.TYPE_HOME_MAIN);
		keywordsearch = findViewById(R.id.linearLayout8);
		keywordsearch.setOnClickListener(this);

		searchByName = findViewById(R.id.LinearLayout04Name);
		searchByName.setOnClickListener(this);

		searchByCategory = findViewById(R.id.LinearLayout031);
		searchByCategory.setOnClickListener(this);

		searchByIndications = findViewById(R.id.linearLayout13);
		searchByIndications.setOnClickListener(this);

		legal = findViewById(R.id.LinearLayout05);
		legal.setOnClickListener(this);

		company = findViewById(R.id.LinearLayout04);
		company.setOnClickListener(this);

		adverserse = findViewById(R.id.linearLayout17);
		adverserse.setOnClickListener(this);
		flipper = (ViewFlipper) findViewById(R.id.flipper);
		
	
	}

	public void onClick(View view) {
		if (keywordsearch == view) {
			if (getContext() instanceof HomeActivity) {
				((HomeActivity) getContext()).sendMessageToSearch();
			}
		} else if (adverserse == view && isTabHome()) {
			((HomeActivity) getContext()).gotoAdverse();
		} else if (view == searchByName && isTabHome()) {

			((HomeActivity) getContext()).gotoProductList();
		} else if (view == searchByCategory && isTabHome()) {
			((HomeActivity) getContext()).gotoCatogoryList();
		} else if (view == searchByIndications && isTabHome()) {
			((HomeActivity) getContext()).gotoIndicationList();
		} else if (view == company) {
			/*flipper.setOutAnimation(outToLeftAnimation());
			flipper.setInAnimation(inFromRightAnimation());
			
			flipper.showPrevious();*/
			((IActivity) getContext()).gotoAbout();
			
		} else if (view == legal) {
			((IActivity) getContext()).gotoIntro();

		}
	}

	private boolean isTabHome() {
		return getContext() instanceof HomeActivity;
	}

	public void reset() {
		// TODO Auto-generated method stub
		((IActivity) getContext()).onBack1();
			}

}
