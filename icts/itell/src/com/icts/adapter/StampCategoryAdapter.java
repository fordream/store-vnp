package com.icts.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.icts.itel.R;
import com.icts.object.StampCatObject;
import com.icts.utils.ImageCache;
import com.icts.utils.Utils;

/**
 * Adapter for list view in stamp category function
 * @author Luong
 *
 */
public class StampCategoryAdapter extends ArrayAdapter<StampCatObject>{

	private OnCatSelectedListener mCallback;
    public interface OnCatSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onCatSelected(View v,int old,Bitmap bitmap);
    }
	public class ViewHolder{
		public ImageView imgCategory;
	}
	//private ExecutorService executor;
	private Context context;
	private List<StampCatObject> list = null;
	
	public StampCategoryAdapter(Context context,List<StampCatObject> list ) {
		super(context, R.layout.chat_stamp_item);
		this.list = list;
		this.context = context;
		//executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
	}
	
	@Override
	public StampCatObject getItem(int position) {
		if (list!=null)
			return list.get(position);
		return null;
	}
	
	public void setListener(OnCatSelectedListener onClick){
		mCallback = onClick;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getCount() {
		if (list!=null)
			return list.size();
		return 0;
	}
	

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StampCatObject cat = (StampCatObject) getItem(position);
		ViewHolder holder;
		if (convertView ==null){
			LayoutInflater inflater = LayoutInflater.from(this.context); 
			convertView = inflater.inflate(R.layout.chat_stamp_item, null);
			holder = new ViewHolder();
			holder.imgCategory = (ImageView) convertView.findViewById(R.id.chat_stamp_category_img);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		//DownloadImage down = new DownloadImage(holder.imgCategory,30,30,context);
		//down.execute(url);
		if (cat.isFree()&&cat.isFromNative()){
			String nameInactiveDrawable = cat.getImageActive();
			String nameActiveDrawable = cat.getImageInactive();
			int resourceID = Utils.getIDResource(context, nameActiveDrawable);
			Bitmap active= ImageCache.getBitmapFromMemCache(nameActiveDrawable);
			Drawable drawActive = null;
			drawActive = context.getResources().getDrawable(resourceID);
			if (active==null){
				try{
					
					active = Utils.convert2bitmap(drawActive);
					ImageCache.addBitmapToCache(nameActiveDrawable, active);
				}catch(Exception e){
					active = null;
				}
			}
			
			int resourceID1 = Utils.getIDResource(context, nameInactiveDrawable);
			Drawable drawInactive = null;
			drawInactive = context.getResources().getDrawable(resourceID1);
			Bitmap inActive= ImageCache.getBitmapFromMemCache(nameInactiveDrawable);
			if (inActive==null){
				try{
					inActive = Utils.convert2bitmap(drawInactive);
					ImageCache.addBitmapToCache(nameInactiveDrawable, inActive);
				}catch(Exception e){
					inActive = null;
				}
			}
			
			//holder.imgCategory.setImageBitmap(active);
			//setStateSelector(drawActive, drawInactive, holder.imgCategory);
			holder.imgCategory.setTag(position);
			if (position==mCurSelector){
				curView = holder.imgCategory; 
				//holder.imgCategory.setSelected(true);
				holder.imgCategory.setImageBitmap(active);
			}
			else {
				holder.imgCategory.setImageBitmap(inActive);
			}
			holder.imgCategory.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mCallback!=null){
						int i = Integer.parseInt(v.getTag().toString());
						if (i==mCurSelector){
							return;
						}
						StampCatObject cat = getItem(mCurSelector);
						String nameInactiveDrawable = cat.getImageActive();
						Bitmap inActive= ImageCache.getBitmapFromMemCache(nameInactiveDrawable);
						int old = mCurSelector;
						mCurSelector = i;
						
						cat = getItem(mCurSelector);
						String nameActiveDrawable = cat.getImageInactive();
						
						Bitmap active= ImageCache.getBitmapFromMemCache(nameActiveDrawable);
						((ImageView)v).setImageBitmap(active);
						curView = v;
						mCallback.onCatSelected(v,old,inActive);
					}
				}
			});
		}
		//holder.imgCategory.setImageBitmap(Utils.convert2bitmap(context.getResources().getDrawable(R.drawable.emotion_cat_icon)));
		return convertView;
	}
	private int mCurSelector = 0;
	private View curView;
	private void setStateSelector(Drawable active, Drawable inactive,ImageView imageView){
		StateListDrawable states = new StateListDrawable();
		if (active!=null){
			states.addState(new int[] {android.R.attr.state_pressed},
			    active);
			states.addState(new int[] {android.R.attr.state_selected},
				    active);
			states.addState(new int[] {android.R.attr.state_focused},
			    active);
		}
		states.addState(new int[] {-android.R.attr.state_pressed,-android.R.attr.state_selected,-android.R.attr.state_focused },
		    inactive);
		
		imageView.setImageDrawable(states);
	}
	

	
}
