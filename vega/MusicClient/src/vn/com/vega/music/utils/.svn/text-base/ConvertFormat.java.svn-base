package vn.com.vega.music.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class ConvertFormat {

	public static String createHMFormatfromM(long _value) {

		String _res, _strMinute, _strSecond = null;
		long _tempsecond, _second, _minute = 0;

		_tempsecond = _value / MILISECOND;
		_second = _tempsecond % MINUTE;
		_minute = _tempsecond / MINUTE;

		if (_minute < 10) {
			_strMinute = "0" + String.valueOf(_minute);
		} else {
			_strMinute = String.valueOf(_minute);
		}

		if (_second < 10) {
			_strSecond = "0" + String.valueOf(_second);
		} else {
			_strSecond = String.valueOf(_second);
		}

		_res = _strMinute + ":" + _strSecond;

		return _res;
	}

	public static final int MILISECOND = 1000;
	public static final int MINUTE = 60;
	
	public static String intToStringTimeFormat(int time) {
		String strTemp = new String();
		int minutes = time / 60;
		int seconds = time % 60;

		if (minutes < 10)
			strTemp = "0" + Integer.toString(minutes) + ":";
		else
			strTemp = Integer.toString(minutes) + ":";

		if (seconds < 10)
			strTemp = strTemp + "0" + Integer.toString(seconds);
		else
			strTemp = strTemp + Integer.toString(seconds);

		return strTemp;
	}
	
	
	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");  
	public static String convertDateToString(Date date, String format) {
	    SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat(format);
	    
	    
	    return mySimpleDateFormat.format(date);
	}

	public static Date convertStringToDate(String dateStr, String format) {
	    SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat(format);
	    try {
	        return mySimpleDateFormat.parse(dateStr);
	    } catch (ParseException e) {
	        return null;
	    }
	}
}
