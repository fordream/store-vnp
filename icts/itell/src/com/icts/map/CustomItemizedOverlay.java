/***
 * Copyright (c) 2011 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.icts.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.icts.itel.R;

public class CustomItemizedOverlay<Item extends CustomOverlayItem> extends ItemizedOverlay<CustomOverlayItem> {

	private OnClickOverlay<Item> onClick; 
	
	private static Handler handler = new Handler();
	private Drawable defaultMarker;
	//private Drawable selectedMarker;
	private MapView mapView;
	private OverlayView<CustomOverlayItem> overlayView;
	private View clickRegion;
	private int viewOffset;
	final MapController mc;
	private int width;
	private CustomOverlayItem currentFocusedItem;
	private int currentFocusedIndex;
	private static boolean isInflating = false;
	private ArrayList<CustomOverlayItem> m_overlays = new ArrayList<CustomOverlayItem>();
	private Context c;
	
	public CustomItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super (boundCenter(defaultMarker));
		//super (null);
		this.defaultMarker = defaultMarker;
		this.mapView = mapView;
		viewOffset = 0;//-12;//
		width = Math.abs(defaultMarker.getBounds().top-defaultMarker.getBounds().bottom)/2;
		mc = mapView.getController();
		c = mapView.getContext();
	}
	
	public CustomItemizedOverlay(Drawable defaultMarker, Drawable selectMarker,MapView mapView) {
		super (boundCenter(defaultMarker));
		//super (null);
		this.defaultMarker = defaultMarker;
		this.mapView = mapView;
		viewOffset = 0;//5
		width = Math.abs(defaultMarker.getBounds().top-defaultMarker.getBounds().bottom)/2;
		mc = mapView.getController();
		c = mapView.getContext();
	}

	public void addOverlay(CustomOverlayItem overlay) {
	    m_overlays.add(overlay);
	    createAndDisplayOverlay(overlay);
	    populate();
	}

	public void removeOverlay(CustomOverlayItem overlay) {
		m_overlays.remove(overlay);
		if (overlayView!=null){
			mapView.removeView(overlayView);
		}
		mapView.invalidate();
        populate();
    }

	public void clear() {
        m_overlays.clear();
        if (overlayView!=null){
        	overlayView.stopDownloadImage();
        }
        mapView.removeAllViews();
        mapView.invalidate();
        currentFocusedItem = null;
        setLastFocusedIndex(-1);
        populate();
    }
	
	public void setOnClick(OnClickOverlay<Item> onclick){
		this.onClick = onclick; 
	}
	
	public void stopOtherThread(){
		if (overlayView!=null){
        	overlayView.stopDownloadImage();
        }
		if (handler!=null){
			handler.removeCallbacks(finishBalloonInflation);
		}
		if (onClick!=null){
			onClick = null;
		}
	}
	@Override
	public int size() {
		return m_overlays.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Item createItem(int i) {
		Item item = (Item) m_overlays.get(i);
		return item;
	}

	
	/**
	 * Set the horizontal distance between the marker and the bottom of the information
	 * balloon. The default is 0 which works well for center bounded markers. If your
	 * marker is center-bottom bounded, call this before adding overlay items to ensure
	 * the balloon hovers exactly above the marker. 
	 * 
	 * @param pixels - The padding between the center point and the bottom of the
	 * information balloon.
	 */
	public void setBottomOffset(int pixels) {
		viewOffset = pixels;
	}
	public int getBottomOffset() {
		return viewOffset;
	}
	
	/**
	 * Override this method to handle a "tap" on a balloon. By default, does nothing 
	 * and returns false.
	 * 
	 * @param index - The index of the item whose overlay is tapped.
	 * @param item - The item whose overlay is tapped.
	 * @return true if you handled the tap, otherwise false.
	 */
	protected boolean onOverlayTap(int index, Item item) {
		currentFocusedIndex = index;
		currentFocusedItem = createItem(index);
		bringBalloonToFront();
		Item item1 = createItem(index);
		if (onClick!=null){
			onClick.onClickOverlayItem(item1,index);
		}
		return true;
	}

	/**
	 * Override this method to perform actions upon an item being tapped before 
	 * its overlay is displayed.
	 * 
	 * @param index - The index of the item tapped.
	 */
	protected void onOverlayOpen(int index) {}

	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#onTap(int)
	 */
	@Override
	public final boolean onTap(int index) {
		return true;
	}

	/**
	 * Animates to the given center point. Override to customize how the
	 * MapView is animated to the given center point
	 *
	 * @param index The index of the item to center
	 * @param center The center point of the item
	 */
	protected void animateTo(int index, GeoPoint center) {
		mc.animateTo(center);
	}

	/**
	 * Creates the layout for overlay. Override to create a sub-classed view that
	 * can populate additional sub-views.
	 */
	protected OverlayView<CustomOverlayItem> createOverlayView() {
		return new OverlayView<CustomOverlayItem>(getMapView().getContext(), getBottomOffset());
	}
	
	/**
	 * Creates the layout for overlay. Override to create a sub-classed view that
	 * can populate additional sub-views.
	 */
	protected OverlayView<CustomOverlayItem> createOverlayView(CustomOverlayItem overlay) {
		return new OverlayView<CustomOverlayItem>(getMapView().getContext(), getBottomOffset(),overlay);
	}
	
	/**
	 * Expose map view to subclasses.
	 * Helps with creation of overlay views. 
	 */
	protected MapView getMapView() {
		return mapView;
	}

	/**
	 * Makes the balloon the topmost item by calling View.bringToFront().
	 */
	public void bringBalloonToFront() {
		if (overlayView != null) {
			overlayView.bringToFront();
		}
	}

	/**
	 * Sets the visibility of this overlay view to GONE and unfocus the item. 
	 */
	public void hideOverlay() {
		if (overlayView != null) {
			overlayView.setVisibility(View.GONE);
		}
		currentFocusedItem = null;
	}
	
	/**
	 * Hides the balloon view for any other CustomItemizedOverlay instances
	 * that might be present on the MapView.
	 * 
	 * @param overlays - list of overlays (including this) on the MapView.
	 */
	private void hideOtherOverlays(List<Overlay> overlays) {
		
		for (Overlay overlay : overlays) {
			if (overlay instanceof CustomItemizedOverlay<?> && overlay != this) {
				((CustomItemizedOverlay<?>) overlay).hideOverlay();
				for (int i = 0; i < ((CustomItemizedOverlay<?>) overlay).size(); i++) {
					((CustomItemizedOverlay<?>) overlay)
						.getItem(i).setMarker(boundCenter(defaultMarker));
			    }
			}
		}
		
	}
	
	public void hideAllBalloons() {
		if (!isInflating) {
			List<Overlay> mapOverlays = mapView.getOverlays();
			if (mapOverlays.size() > 1) {
				hideOtherOverlays(mapOverlays);
			}
			hideOverlay();
		}
	}
	
	/**
	 * Sets the onTouchListener for the balloon being displayed, calling the
	 * overridden {@link #onOverlayTap} method.
	 */
	private OnTouchListener createOverlayTouchListener() {
		return new OnTouchListener() {
			
			float startX;
			float startY;
			
			@SuppressWarnings("unchecked")
			public boolean onTouch(View v, MotionEvent event) {
				
				View l =  ((View) v.getParent()).findViewById(R.id.overlay_main_layout);
				Drawable d = l.getBackground();
				
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (d != null) {
						int[] states = {android.R.attr.state_pressed};
						if (d.setState(states)) {
							d.invalidateSelf();
						}
					}
					startX = event.getX();
					startY = event.getY();
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (d != null) {
						int newStates[] = {};
						if (d.setState(newStates)) {
							d.invalidateSelf();
						}
					}
					if (Math.abs(startX - event.getX()) < 40 && 
							Math.abs(startY - event.getY()) < 40 ) {
						// call overridden method
						onOverlayTap(currentFocusedIndex,(Item) currentFocusedItem);
					}
					return true;
				} else {
					return false;
				}
				
			}
		};
	}
	
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#getFocus()
	 */
	@Override
	public CustomOverlayItem getFocus() {
		return currentFocusedItem;
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#setFocus(Item)
	 */
	@Override
	public void setFocus(CustomOverlayItem item) {
		super.setFocus(item);	
		currentFocusedIndex = getLastFocusedIndex();
		currentFocusedItem = item;
		if (currentFocusedItem == null) {
			hideOverlay();
		} else {
			createAndDisplayOverlay(item);
		}	
	}
	
	/**
	 * Creates and displays the balloon overlay by recycling the current 
	 * balloon or by inflating it from xml. 
	 * @return true if the balloon was recycled false otherwise 
	 */
	@SuppressWarnings("unused")
	private boolean createAndDisplayOverlay(){
		boolean isRecycled;
		if (overlayView == null) {
			overlayView = createOverlayView();
			//clickRegion = (View) overlayView.findViewById(R.id.overlay_inner_layout);
			clickRegion = (View) overlayView.findViewById(R.id.overlay_main_layout);
			clickRegion.setOnTouchListener(createOverlayTouchListener());
			isRecycled = false;
		} else {
			isRecycled = true;
		}
	
		//overlayView.setVisibility(View.GONE);
		overlayView.setVisibility(View.VISIBLE);
		
		if (currentFocusedItem != null){
			overlayView.setData(currentFocusedItem);
		}
		
		GeoPoint point = currentFocusedItem.getPoint();
		Point p = new Point();
		mapView.getProjection().toPixels(point, p);
		p.y = p.y+width;
		GeoPoint point1 = mapView.getProjection().fromPixels(p.x, p.y);
		MapView.LayoutParams params = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point1,
				MapView.LayoutParams.BOTTOM_CENTER);
		params.mode = MapView.LayoutParams.MODE_MAP;
		overlayView.setVisibility(View.VISIBLE);
		if (isRecycled) {
			overlayView.setLayoutParams(params);
		} else {
			mapView.addView(overlayView, params);
		}
		
		return isRecycled;
	}
	/**
	 * Creates and displays the balloon overlay by recycling the current 
	 * balloon or by inflating it from xml. 
	 * @return true if the balloon was recycled false otherwise 
	 */
	private boolean createAndDisplayOverlay(CustomOverlayItem overlay){
		boolean isRecycled;
		if (overlayView == null) {
			overlayView = createOverlayView(overlay);
			//clickRegion = (View) overlayView.findViewById(R.id.overlay_inner_layout);
			clickRegion = (View) overlayView.findViewById(R.id.overlay_main_layout);
			clickRegion.setOnTouchListener(createOverlayTouchListener());
			isRecycled = false;
		} else {
			isRecycled = true;
		}
	
		overlayView.setVisibility(View.GONE);
		
		if (overlay != null){
			overlayView.setData(overlay);
		}
		
		
		GeoPoint point = overlay.getPoint();
		Point p = new Point();
		mapView.getProjection().toPixels(point, p);
		p.y = p.y+width;
		GeoPoint point1 = mapView.getProjection().fromPixels(p.x, p.y);
		final MapView.LayoutParams params = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point1,
				MapView.LayoutParams.BOTTOM_CENTER);//MapView.LayoutParams.LEFT|MapView.LayoutParams.BOTTOM_CENTER);
		params.mode = MapView.LayoutParams.MODE_MAP;
		overlayView.setVisibility(View.VISIBLE);
		if (isRecycled) {
			overlayView.setLayoutParams(params);
		} else {
			mapView.post(new Runnable() {
				
				public void run() {
					mapView.addView(overlayView, params);
				}
			});
			//mapView.addView(overlayView, params);
		}
		
		return isRecycled;
	}

	
	/**
	 * Remove shadow
	 */
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		if (!shadow){
			return;
		}
	}
	
	public static boolean isInflating() {
		return isInflating;
	}
	
	private static Runnable finishBalloonInflation = new Runnable() {
		public void run() {
			isInflating = false;
		}
	};
	
	public interface OnClickOverlay<T>{
		public abstract void onClickOverlayItem(T item, int index);
	}
	
}
