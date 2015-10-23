package com.caferhythm.csn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	public static String dateFormat(String date) {
		String returnDate = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dd = simpleDateFormat.parse(date);
			simpleDateFormat.applyPattern("MM/d");
			returnDate = simpleDateFormat.format(dd);
		} catch (ParseException e) {
			e.printStackTrace();
			returnDate = date;
		}
		return returnDate;
	}
	
	public static boolean compareDate(String start,String middle,String end) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long startDate = simpleDateFormat.parse(start).getTime();
			long middleDate = simpleDateFormat.parse(middle).getTime();
			long endDate = simpleDateFormat.parse(end).getTime();
			if(startDate <= middleDate  && middleDate <= endDate){
				return true;
			}else{
				return false;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean compareDate(String start,String end) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long startDate = simpleDateFormat.parse(start).getTime();
			long endDate = simpleDateFormat.parse(end).getTime();
			if(startDate <= endDate){
				return true;
			}else{
				return false;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static final String validString(String str,int length) {
		if (str.length()>length) {
			str = str.substring(0,length)+"...";
		}
		return str;
	}
}
