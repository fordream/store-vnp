package com.caferhythm.csn.view;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

import com.caferhythm.csn.R;
import com.caferhythm.csn.data.TaionRow;

public class TemperatureGraphView extends View {
	// point data
	ArrayList<PointF> listPoints;
	Paint paint = new Paint();
	private Context mContext;
	private float xContentStep = 0.0F;
	private float textSize;
	private float xStep;
	private float yStep;
	private float xGraphContent;
	private float xAxisForTemperarure;
	private float yAxisForDate;
	private float maxX = 0.0f;
	private float minX = 0.0f;
	private final int height;
	private final int width;
	private float heightForHeart;
	private String day;
	private ArrayList<TaionRow> listTemper;

	private Paint textPaint;
	private Paint linePaint;
	private Paint heartLinePaint;
	private Paint ciclePaint;
	private Paint textPaint2;

	private Path myPath;
	DecimalFormat df = new DecimalFormat("0.00");

	public TemperatureGraphView(Context context, int w, ArrayList<TaionRow> t,
			boolean isDrawHeart) {
		super(context);
		listTemper = new ArrayList<TaionRow>();
		listPoints = new ArrayList<PointF>();
		mContext = context;
		listTemper = t;
		heartLinePaint = new Paint();
		heartLinePaint.setARGB(255, 255,178,177);
		heartLinePaint.setStrokeWidth(3);
		Log.i("test", "qaz list temper:" + listTemper.size());
		width = w;
		height = 3 * w / 5;
		day = context.getResources().getString(R.string.day);
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setARGB(225, 186, 85, 127);
		textPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint2.setARGB(225, 0, 179, 209);
		linePaint = new Paint();
		linePaint.setStrokeWidth(3);
		linePaint.setARGB(225,255,232,180);
		linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		linePaint.setStyle(Paint.Style.STROKE);
		ciclePaint = new Paint();
		ciclePaint.setARGB(225, 231,114,163);
		myPath = new Path();
		genPoints();
		setBackgroundResource(R.color.background_graph);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int measureSpec) {
		// int preferred = image.getWidth();
		int preferred = width;
		return getMeasurement(measureSpec, preferred);
	}

	private int measureHeight(int measureSpec) {
		int preferred = height;
		return getMeasurement(measureSpec, preferred);
	}

	private int getMeasurement(int measureSpec, int preferred) {
		int specSize = MeasureSpec.getSize(measureSpec);
		int measurement = 0;
		switch (MeasureSpec.getMode(measureSpec)) {
		case MeasureSpec.EXACTLY:
			measurement = specSize;
			break;
		case MeasureSpec.AT_MOST:
			measurement = Math.min(preferred, specSize);
			break;
		default:
			measurement = preferred;
			break;
		}
		return measurement;
	}

	@Override
	public void onDraw(Canvas canvas) {
		drawXaxis(canvas);
		canvas.drawPath(myPath, linePaint);
		for (int i = 0; i < listPoints.size(); i++) {
			canvas.drawCircle(listPoints.get(i).x, listPoints.get(i).y,
					4, ciclePaint);
		}
		//drawHeart(canvas);
	}

	private void genPoints() {
		textSize = width / 25;
		xGraphContent = 10;
		xAxisForTemperarure = width - width / 8;
		heightForHeart = height / 10;
		yAxisForDate = (9 * height) / 10;
		Log.i("test", "yasis:" + yAxisForDate + " height:" + height + " heart:"
				+ heightForHeart);
		maxX = 0;
		minX = 87.2f;
		for (int i = 0; i < listTemper.size(); i++) {
			TaionRow t = listTemper.get(i);
			if (t.getTaion().length() < 1)
				continue;
			Log.i("test", "taion:" + t.getTaion());
			if (maxX < Float.parseFloat(t.getTaion()))
				maxX = Float.parseFloat(t.getTaion());
			if (minX > Float.parseFloat(t.getTaion()))
				minX = Float.parseFloat(t.getTaion());
		}
		maxX += 0.2f;
		minX -= 0.2f;
		float scaleDegree = (yAxisForDate - heightForHeart - textSize)
				/ (maxX - minX);
		
		yStep = (yAxisForDate - heightForHeart - textSize) / 4;
		xStep = (xAxisForTemperarure - xGraphContent) / listTemper.size();
		
		for (int i = 0; i < listTemper.size(); i++) {
			TaionRow t = listTemper.get(i);
			if (t.getTaion().length() < 1)
				continue;
			Log.i("test", "taion:" + t.getTaion());
			PointF tempPoint = new PointF();
			tempPoint.x = xGraphContent + (i * xStep);
			tempPoint.y = heightForHeart
					+ (maxX - Float.parseFloat(t.getTaion())) * scaleDegree;
			listPoints.add(tempPoint);
		}
		if(listPoints.size()<1){
			maxX = 37.5f;
			minX = 35.0f;
		}
		xContentStep = (maxX - minX) / 4;
		if (listPoints.size() > 0) {
			myPath.moveTo(listPoints.get(0).x, listPoints.get(0).y);
			for (int i = 1; i < (listPoints.size()); i++) {
				myPath.lineTo(listPoints.get(i).x, listPoints.get(i).y);
			}
		}
		textPaint.setTextSize(textSize);
	}

//	private void drawHeart(Canvas c) {
//		if (!isDrawHeart)
//			return;
//		Date date = new Date();
//		float xHeart=(date.getDate()-1) * xStep + xGraphContent;
//		RectF rectF= new RectF(xHeart-heightForHeart/2, 0+heightForHeart/3, xHeart+heightForHeart-heightForHeart/2, heightForHeart);
//		c.drawBitmap(img, null, rectF, null);
//		c.drawLine(xHeart, heightForHeart,
//				xHeart, yAxisForDate, heartLinePaint);
//	}

	private void drawXaxis(Canvas c) {
		// draw y
		c.drawText("" + df.format(maxX) + "\u2103", xAxisForTemperarure, textSize/2
				+ heightForHeart, textPaint);
		c.drawText("" + df.format(maxX - xContentStep) + "\u2103", xAxisForTemperarure,
				textSize/2 + yStep + heightForHeart, textPaint);
		c.drawText("" + df.format(maxX - 2 * xContentStep) + "\u2103",
				xAxisForTemperarure, textSize/2 + 2 * yStep + heightForHeart,
				textPaint);
		c.drawText("" + df.format(maxX - 3 * xContentStep) + "\u2103",
				xAxisForTemperarure, textSize/2 + 3 * yStep + heightForHeart,
				textPaint);
		c.drawText("" + df.format(minX) + "\u2103", xAxisForTemperarure, textSize + 4
				* yStep + heightForHeart, textPaint);
		// draw x
		c.drawText("1" + day, xGraphContent, yAxisForDate + textSize + 1,
				textPaint2);
		c.drawText("10" + day, xGraphContent + 9 * xStep, yAxisForDate
				+ textSize + 1, textPaint2);
		c.drawText("20" + day, xGraphContent + 19 * xStep, yAxisForDate
				+ textSize + 1, textPaint2);
		c.drawText(""+listTemper.size() + day, xGraphContent + (listTemper.size()-1) * xStep, yAxisForDate
				+ textSize + 1, textPaint2);
		// draw content
		Paint pGragh = new Paint();
		pGragh.setColor(Color.WHITE);
		c.drawRect(xGraphContent, heightForHeart, xAxisForTemperarure,
				yAxisForDate, pGragh);
		c.drawLine(0, height, width, height, textPaint);
	}
}