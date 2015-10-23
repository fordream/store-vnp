package org.com.vnp.xathubongbong2.model;

import java.io.File;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class Font {
	public static final String PATH = "src/org/com/cnc/common/android/font/";
	public static final String BRADHITC = "BRADHITC.TTF";
	public static final String AGENCYB = "AGENCYB.TTF";
	public static final String BROADW = "BROADW.TTF";
	public static final String ALGER = "ALGER.TTF";

	public static void setTypefaceFromAsset(Context context, TextView tv,
			String fileAsset) {
		try {
			AssetManager assertManager = context.getAssets();
			Typeface tf = Typeface.createFromAsset(assertManager, fileAsset);
			tv.setTypeface(tf);
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		File file = new File(PATH + ALGER);
		System.out.println(file.exists());
	}
}