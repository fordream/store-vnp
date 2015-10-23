package com.icts.control;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.icts.itel.R;
import com.icts.object.StampObject;
import com.icts.utils.ImageCache;
import com.icts.utils.Utils;

public class StampFragment extends Fragment {
	final static String ARG_POSITION = "position";
	private int mCurrentPosition = -1;
	private OnClickStampImage onClick;
	private static final String KEY_CONTENT = "StampFragment:url";
	private final int[] id = new int[]{
		R.id.chat_stamp_img1,R.id.chat_stamp_img2,R.id.chat_stamp_img3,R.id.chat_stamp_img4,
		R.id.chat_stamp_img5,
		R.id.chat_stamp_img6
	};
	public static final int[] Emotion = new int[]{
			R.drawable.emotion_1,R.drawable.emotion_2,R.drawable.emotion_3,R.drawable.emotion_4,
			R.drawable.emotion_5,
			R.drawable.emotion_6
	};
	private final int[] llID = new int[]{
			R.id.chat_stamp_ll_line1,R.id.chat_stamp_ll_line2
	};
	public static StampFragment newInstance(StampObject[] stampObjects) {
		StampFragment fragment = new StampFragment();
		fragment.mContent = stampObjects;
		/*Bundle bundle = new Bundle();
        bundle.putParcelableArray(KEY_CONTENT, stampObjects);
        fragment.setArguments(bundle);*/
		return fragment;
	}
	
	private StampObject[] mContent = new StampObject[6];
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = (StampObject[]) savedInstanceState.getParcelableArray(KEY_CONTENT);
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}
		View v = inflater.inflate(R.layout.chat_stamp_category_item, container,false);
		updateInfo(v);
		return v;
	}
	@Override
	public void onStart() {
		super.onStart();
		
	}
	public void updateInfo(View v){
		int index = 0;
		LinearLayout ll1 =(LinearLayout) v.findViewById(llID[0]);
		LinearLayout ll2 =(LinearLayout) v.findViewById(llID[1]);
		ll1.setVisibility(View.VISIBLE);
		for (int i = 0;i<6;i++){
			if (mContent[i]!=null){
				if (i>=3){
					if (ll2.getVisibility()!=View.VISIBLE){
						ll2.setVisibility(View.VISIBLE);
					}
				}
			}
			else {
				index = i;
				if (i<=3){
					ll2.setVisibility(View.GONE);
				}
				break;
			}
			ImageView img = (ImageView)v.findViewById(id[i]);
			img.setTag(mContent[i]);
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (onClick!=null){
						StampObject stamp =(StampObject) v.getTag();
						onClick.onClickStampImage(stamp);
					}
					
				}
			});
			
			
			//Download bitmap
			//DownloadImage down  = new DownloadImage(img, 100, 100, getActivity());
			//down.execute(mContent[i].getImageLink());
			//img.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(Emotion[i])));
			if (mContent[i].isFree()&&mContent[i].isFromNative()){
				String nameDrawable = mContent[i].getImageLink();
				int resourceID = Utils.getIDResource(v.getContext(), nameDrawable);
				Bitmap b = ImageCache.getBitmapFromMemCache(mContent[i].getCode());
				if (b==null){
					Drawable draw = v.getContext().getResources().getDrawable(resourceID);
					b = Utils.convert2bitmap(draw);
					ImageCache.addBitmapToCache(mContent[i].getCode(), b);
				}
				img.setImageBitmap(b);
			}
		}
		if (mContent.length>0&&index>0){
			if (index<3){
				for (int i = index;i<3;i++){
					ImageView img = (ImageView)v.findViewById(id[i]);
					img.setImageBitmap(null);
					img.setOnClickListener(null);
				}
			}
			else if (index>4){
				if (index<6){
					for (int i = index;i<6;i++){
						ImageView img = (ImageView)v.findViewById(id[i]);
						img.setImageBitmap(null);
						img.setOnClickListener(null);
					}
				}
			}
		}
	}
	
	public void updateInfo(View v,StampObject[] mContent){
		int index = 0;
		this.mContent= mContent; 
		LinearLayout ll1 =(LinearLayout) v.findViewById(llID[0]);
		LinearLayout ll2 =(LinearLayout) v.findViewById(llID[1]);
		ll1.setVisibility(View.VISIBLE);
		for (int i = 0;i<6;i++){
			if (mContent[i]!=null){
				if (i>=3){
					ll2.setVisibility(View.VISIBLE);
				}
			}
			else {
				index = i;
				if (i<=3){
					ll2.setVisibility(View.GONE);
				}
				break;
			}
			ImageView img = (ImageView)v.findViewById(id[i]);
			img.setTag(mContent[i]);
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (onClick!=null){
						StampObject stamp =(StampObject) v.getTag();
						onClick.onClickStampImage(stamp);
					}
					
				}
			});
			
			//Download bitmap
			//DownloadImage down  = new DownloadImage(img, 100, 100, getActivity());
			//down.execute(mContent[i].getImageLink());
			//img.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(Emotion[i])));
			if (mContent[i].isFree()&&mContent[i].isFromNative()){
				String nameDrawable = mContent[i].getImageLink();
				int resourceID = Utils.getIDResource(v.getContext(), nameDrawable);
				Bitmap b = ImageCache.getBitmapFromMemCache(mContent[i].getCode());
				if (b==null){
					Drawable draw = v.getContext().getResources().getDrawable(resourceID);
					b = Utils.convert2bitmap(draw);
					ImageCache.addBitmapToCache(mContent[i].getCode(), b);
				}
				img.setImageBitmap(b);
			}
		}
		if (mContent.length>0&&index>0){
			if (index<3){
				for (int i = index;i<3;i++){
					ImageView img = (ImageView)v.findViewById(id[i]);
					img.setImageBitmap(null);
					img.setOnClickListener(null);
				}
			}
			else if (index>=4){
				if (index<6){
					for (int i = index;i<6;i++){
						ImageView img = (ImageView)v.findViewById(id[i]);
						img.setImageBitmap(null);
						img.setOnClickListener(null);
					}
				}
			}
		}
	}
	public void updateInfo(FragmentActivity ac){
		LinearLayout ll1 =(LinearLayout) ac.findViewById(llID[0]);
		LinearLayout ll2 =(LinearLayout) ac.findViewById(llID[1]);
		ll1.setVisibility(View.VISIBLE);
		for (int i = 0;i<6;i++){
			if (mContent[i]!=null){
				if (i>=3){
					if (ll2.getVisibility()!=View.VISIBLE){
						ll2.setVisibility(View.VISIBLE);
					}
				}
			}
			else {
				if (i<=3){
					ll2.setVisibility(View.GONE);
				}
				break;
			}
			ImageView img = (ImageView)ac.findViewById(id[i]);
			img.setTag(mContent[i]);
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (onClick!=null){
						StampObject stamp =(StampObject) v.getTag();
						onClick.onClickStampImage(stamp);
					}
					
				}
			});
			
			//Download bitmap
			//DownloadImage down  = new DownloadImage(img, 100, 100, getActivity());
			//down.execute(mContent[i].getImageLink());
			//img.setImageBitmap(Utils.convert2bitmap(getResources().getDrawable(Emotion[i])));
			if (mContent[i].isFree()&&mContent[i].isFromNative()){
				String nameDrawable = mContent[i].getImageLink();
				int resourceID = Utils.getIDResource(ac, nameDrawable);
				Bitmap b = ImageCache.getBitmapFromMemCache(mContent[i].getCode());
				if (b==null){
					Drawable draw = getResources().getDrawable(resourceID);
					b = Utils.convert2bitmap(draw);
					ImageCache.addBitmapToCache(mContent[i].getCode(), b);
				}
				img.setImageBitmap(b);
			}
		}
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArray(KEY_CONTENT, mContent);
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}
	
	public void setOnClickStampImage(OnClickStampImage onClick){
		this.onClick = onClick;
	}
	
	public interface OnClickStampImage{
		public abstract void onClickStampImage(StampObject stamp);
	}
}

