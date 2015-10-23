/**
 * Copyright (c) 2011, 2012 Sentaca Communications Ltd.
 */
package com.icts.shortfilmfestival.utils;

import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontUtils {
  public static final String TAG_EN = "en";

  public static final String TAG_JP = "jp";
  private static final int ICE_CREAM_SANDWITCH = 14;

  private static Typeface fontEn;
  private static Typeface fontEnTitle;
  private static Typeface fontJp;

  private static void processsViewGroup(ViewGroup v, final int len) {

    for (int i = 0; i < len; i++) {
      final View c = v.getChildAt(i);
      if (c instanceof TextView) {
        //setCustomFont((TextView) c);
      } else if (c instanceof ViewGroup) {
        //setCustomFont((ViewGroup) c);
      }
    }
  }

  private static void setCustomFont(TextView c, boolean isTitle, boolean isJp) {
//    Object tag = c.getTag();
//    if (tag instanceof String) {
//      final String tagString = (String) tag;
//      if (tagString.contains(TAG_JP)) {
//        c.setTypeface(fontJp);
//        return;
//      }
//      if (tagString.contains(TAG_EN)) {
//        c.setTypeface(fontEn);
//        return;
//      }
//    }
	  if (!Resource.localization.equals(ISettings.LANG_JP_FONT))
	  {
		  if (isTitle)
		  {
			  c.setTypeface(fontEnTitle);
		  }
		  else
		  {
			  c.setTypeface(fontEn);
		  }
	  }
	  else
	  {
		  if (isJp)
		  {
			  c.setTypeface(fontJp);
		  }
		  else
		  {
			  c.setTypeface(fontEn);
		  }
	  }
  }
 

  public static void setCustomFont(View topView, boolean isTitle, boolean isJp, AssetManager assetsManager) {
    if (Build.VERSION.SDK_INT >= ICE_CREAM_SANDWITCH) {
      return;
    }
    initTypefaces(assetsManager);

    if (topView instanceof ViewGroup) {
      setCustomFont((ViewGroup) topView);
    } else if (topView instanceof TextView) {
      setCustomFont((TextView) topView, isTitle, isJp);
    }
  }

  private static void initTypefaces(AssetManager assetsManager) {
    if (fontEn == null) {
    	fontEn = Typeface.createFromAsset(assetsManager, "fonts/helr45w.ttf");
    }
    if (fontJp == null)
    {	
    	fontJp = Typeface.createFromAsset(assetsManager, "fonts/helr45w.ttf");
    }
    if (fontEnTitle == null)
    {
    	fontEnTitle = Typeface.createFromAsset(assetsManager, "fonts/HelveticaBd.ttf");
    }
  }

  private static void setCustomFont(ViewGroup v) {
    final int len = v.getChildCount();
    processsViewGroup(v, len);
  }

  public static Typeface getTypefaceNormal(AssetManager assetsManager) {
    initTypefaces(assetsManager);
    return fontEn;
  }
}
