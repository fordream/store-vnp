package vn.com.vega.music.utils;

//adb shell setprop log.tag.<YOUR_LOG_TAG> <LEVEL>

public class VegaLog {
	
	public static final int VEGA_LOG_ERROR = 1;
	public static final int VEGA_LOG_WARN = 2;
	public static final int VEGA_LOG_DEBUG = 3;
	public static final int VEGA_LOG_INFO = 4;
	
	private static String stag = "(All)";
	private static String tag = Const.LOG_PREF;

	//Error:Trong nhung truong hop exception, ham tra ve error
	public static void e(String msg) {
		e(stag, msg);
	}
	
	public static void e(String stag, String msg) {
		if (Const.VEGA_LOG_LEVEL >= VEGA_LOG_ERROR) {
			System.out.println(tag + ": (" + stag + ") " + msg);
		}
	}
	
	//Debug:Trace flow	
	public static void d(String msg) {
		d(stag, msg);
	}
	public static void d(String stag, String msg) {
		if (Const.VEGA_LOG_LEVEL >= VEGA_LOG_DEBUG) {
			System.out.println(tag + ": (" + stag + ") " + msg);
		}
	}
	
	//Information:Xem gia tri cac bien, thong tin muon theo doi
	public static void i(String msg) {
		i(stag, msg);
	}
	public static void i(String stag, String msg) {
		if (Const.VEGA_LOG_LEVEL >= VEGA_LOG_INFO) {
			System.out.println(tag + ": (" + stag + ") " + msg);
		}
	}
	
	//Warning: param null, gia tri ko hop le (nhung ko gay ra loi)...
	public static void w(String msg) {
		w(stag, msg);
	}
	public static void w(String stag, String msg) {
		if (Const.VEGA_LOG_LEVEL >= VEGA_LOG_WARN) {
			System.out.println(tag + ": (" + stag + ") " + msg);
		}
	}
	
	//Warning: param null, gia tri ko hop le (nhung ko gay ra loi)...
	public static void v(String msg) {
		v(stag, msg);
	}
	public static void v(String stag, String msg) {
		if (Const.VEGA_LOG_LEVEL >= VEGA_LOG_INFO) {
			System.out.println(tag + ": (" + stag + ") " + msg);
		}
	}

}