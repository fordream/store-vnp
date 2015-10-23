package org.cnc.qrcode;

import java.util.List;

import org.cnc.qrcode.R.string;
import org.cnc.qrcode.asyn.SaveHistory;
import org.cnc.qrcode.database.item.History2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class AnswerMapActivity extends MapActivity {
	private String address;
	private MapView mapView;
	private MapController mc;
	GeoPoint p;

	class MapOverlay extends com.google.android.maps.Overlay {
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);

			// ---translate the GeoPoint to screen pixels---
			Point screenPts = new Point();
			mapView.getProjection().toPixels(p, screenPts);

			// ---add the marker---
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.mapmk);
			// canvas.drawBitmap(bmp, screenPts.x + 11, screenPts.y-36, null);
			int x = screenPts.x - bmp.getWidth() / 2;
			int y = screenPts.y - bmp.getHeight();
			canvas.drawBitmap(bmp, x, y, null);
			// add
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			// canvas.drawText("day", x, y, paint);

			return true;
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answermap);
		Bundle extras = getIntent().getExtras();
		String latit = extras.getString("lat5");
		String longtit = extras.getString("long5");
		//address = extras.getString("address");
		String text = getResources().getString(string.location_trans) + " : ";
		((TextView) findViewById(R.id.textView1)).setText(text + address);
		mapView = (MapView) findViewById(R.id.mapview2);

		LinearLayout zoomLayout = (LinearLayout) findViewById(R.id.zoom);
		@SuppressWarnings("deprecation")
		View zoomView = mapView.getZoomControls();

		zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mapView.displayZoomControls(true);

		mc = mapView.getController();

		String coordinates[] = { latit, longtit };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);

		p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

		mc.animateTo(p);
		mc.setZoom(17);

		MapOverlay mapOverlay = new MapOverlay();
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		listOfOverlays.add(mapOverlay);

		mapView.invalidate();

		History2 history = new History2();
		history.setAddress(address);
		history.set_long(longtit);
		history.setLat(latit);
		history.setKey(GlobalActivity.questionContent);
		new SaveHistory(this, history).execute("");
	}

	protected boolean isRouteDisplayed() {
		return false;
	}
}
