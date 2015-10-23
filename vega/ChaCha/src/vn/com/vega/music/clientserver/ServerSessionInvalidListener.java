package vn.com.vega.music.clientserver;

public interface ServerSessionInvalidListener {
	
	void onLoginAgain();
	
	void onValidateAgain();
	
	void onPowerExpire(String preferred_package_code, String msg);
}
