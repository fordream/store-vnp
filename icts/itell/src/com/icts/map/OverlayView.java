package com.icts.map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icts.app.ItelApplication;
import com.icts.itel.R;
import com.icts.utils.Constant;
import com.icts.utils.DownloadImageTask;

//@SuppressLint("ViewConstructor")
public class OverlayView<Item extends CustomOverlayItem> extends FrameLayout{

	private final String DEFAULT_COMPANY = "Default";
	
	private LinearLayout layout;
	private TextView titleCom;
	private TextView title;
	private TextView snippet;
	private ImageView image;
	//private DownloadImage down;
	private int bottomOffset;
	private Context mContext;
	private ExecutorService executor;
	
	/**
	 * Initial a new OverlayView object
	 * @param context the activity context
	 * @param bottomOffset the bottom padding (in pixels) when rendering this view
	 */
	public OverlayView(Context context,int bottomOffset) {
		super(context);
		executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
		mContext = context;
		layout = new LimitLinearLayout(context);
		layout.setVisibility(VISIBLE);
		this.bottomOffset = bottomOffset;
		setupView(context, layout, null);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL;//NO_GRAVITY;

		addView(layout, params);
	}
	
	/**
	 * Initial a new OverlayView object
	 * @param context the activity context
	 * @param bottomOffset the bottom padding (in pixels) when rendering this view
	 */
	public OverlayView(Context context,int bottomOffset,Item item) {
		super(context);
		executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
		mContext = context;
		layout = new LimitLinearLayout(context);
		layout.setVisibility(VISIBLE);
		this.bottomOffset = bottomOffset;
		setupView(context, layout, item);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL;//NO_GRAVITY;

		addView(layout, params);
	}
	

	
	/**
	 * Inflate and initialize the OverlayView UI. Override this method
	 * to provide a custom view/layout for the overlay. 
	 * 
	 * @param context - The activity context.
	 * @param parent - The root layout into which you must inflate your view.
	 */
	protected void setupView(Context context, final ViewGroup parent,Item item) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.overlay_info, parent);
		if (!item.isCompanyAcc){
			title = (TextView) v.findViewById(R.id.overlay_item_title);
			snippet = (TextView) v.findViewById(R.id.overlay_item_snippet);
			image = (ImageView) v.findViewById(R.id.overlay_item_image);
			LinearLayout llInner =(LinearLayout) v.findViewById(R.id.overlay_inner_layout);
			llInner.setVisibility(View.VISIBLE);
			LinearLayout llCom =(LinearLayout) v.findViewById(R.id.map_ll_com_acc);
			llCom.setVisibility(View.GONE);
			
			//Set background
			LinearLayout llTitle = (LinearLayout)v.findViewById(R.id.map_overlay_ll_title);
			LinearLayout llAvatar = (LinearLayout)v.findViewById(R.id.map_overlay_ll_avatar);
			ImageView imgPin = (ImageView)v.findViewById(R.id.overlay_item_pin);
			if (!item.isMale){
				//girl
				//llTitle.setBackgroundResource(R.drawable.shape_map_girl_overlay_bg);
				//llAvatar.setBackgroundResource(R.drawable.shape_map_girl_overlay_bg);
				llTitle.setBackgroundResource(R.drawable.overlay_marker_girl_selector);
				llAvatar.setBackgroundResource(R.drawable.overlay_marker_girl_selector);
				if (item.isFriend){
					imgPin.setImageResource(R.drawable.map_girl_friends);
				}
				else{
					imgPin.setImageResource(R.drawable.map_girl_other);
				}
			}
			else {
				//boy
				//llTitle.setBackgroundResource(R.drawable.shape_map_boy_overlay_bg);
				//llAvatar.setBackgroundResource(R.drawable.shape_map_boy_overlay_bg);
				llTitle.setBackgroundResource(R.drawable.overlay_marker_boy_selector);
				llAvatar.setBackgroundResource(R.drawable.overlay_marker_boy_selector);
				if (item.isFriend){
					imgPin.setImageResource(R.drawable.map_boy_friends);
				}
				else{
					imgPin.setImageResource(R.drawable.map_boy_other);
				}
			}
		}
		else {
			titleCom = (TextView) v.findViewById(R.id.overlay_item_title_com);
			LinearLayout ll_inner =(LinearLayout) v.findViewById(R.id.overlay_inner_layout);
			ll_inner.setVisibility(View.GONE);
			LinearLayout ll_com =(LinearLayout) v.findViewById(R.id.map_ll_com_acc);
			ll_com.setVisibility(View.VISIBLE);
		}
		
	}
	
	/**
	 * Sets the view data from a given overlay item.
	 * @param item - The overlay item containing the relevant view data. 
	 */
	public void setData(Item item) {
		layout.setVisibility(VISIBLE);
		if (!item.isCompanyAcc){
			setOverlayData(item, layout);
		}
		else {
			if (titleCom!=null){
				if (item.getComName()!=null){
					titleCom.setText(item.getComName());
				}
				else{
					titleCom.setText(DEFAULT_COMPANY);
				}
			}
		}
	}
	
	/**
	 * Sets the view data from a given overlay item. Override this method to create
	 * your own data/view mappings.
	 * 
	 * @param item - The overlay item containing the relevant view data.
	 * @param parent - The parent layout for this OverlayView.
	 */
	protected void setOverlayData(Item item, ViewGroup parent) {
		//set data for overlay
		if (item.getTitle() != null) {
			title.setVisibility(VISIBLE);
			title.setText(item.getTitle());
		} else {
			title.setText("");
			title.setVisibility(GONE);
		}
		if (item.getSnippet() != null) {
			snippet.setVisibility(VISIBLE);
			setPadding(60, 0, 10, bottomOffset);
			snippet.setText(item.getSnippet());
		} else {
			snippet.setText("");
			snippet.setVisibility(GONE);
			setPadding(10, 0, 10, bottomOffset);
		}
		
		// get remote image from network.
		DownloadImageTask task = new DownloadImageTask(image, 30, 30, 
				mContext, item.getImageURL(),item.isMale);
		task.setIsAvatar(true);
		executor.submit(task);
		//down = new DownloadImage(image, 30, 30, mContext);
		//down.execute(item.getImageURL());
	}
	
	/**
	 * Cancel download image
	 */
	public void stopDownloadImage(){
		/*if (down!=null){
			down.cancel(true);
		}*/
		if (executor!=null){
			ItelApplication.killTask(executor);
		}
		//ItelApplication.killTask(ItelApplication.executorOther);
	}
	
	
	/**
	 * This class ensures width of overlay that is not over MAX_WIDTH_DP =180  
	 * @author Luong
	 *
	 */
	private class LimitLinearLayout extends LinearLayout {

	    private static final int MAX_WIDTH_DP = 180;//280;
	    
	    final float SCALE = getContext().getResources().getDisplayMetrics().density;

	    public LimitLinearLayout(Context context) {
	        super(context);
	    }

	    public LimitLinearLayout(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }

	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        int mode = MeasureSpec.getMode(widthMeasureSpec);
	        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
	        int adjustedMaxWidth = (int)(MAX_WIDTH_DP * SCALE + 0.5f);
	        int adjustedWidth = Math.min(measuredWidth, adjustedMaxWidth);
	        int adjustedWidthMeasureSpec = MeasureSpec.makeMeasureSpec(adjustedWidth, mode);
	        super.onMeasure(adjustedWidthMeasureSpec, heightMeasureSpec);
	    }
	}

}
