package minh.app.mbook.utils;

public class MbookManager {
	// ACTION
	public static final String ACTION_MAIN_LIST = "MAINLIST";
	public static final String ACTIONG_SERVICE = "ACTIONG_SERVICE";

	// server, get to update
	public static final String SERVER_MAIN = "https://app-server.googlecode.com/svn/trunk/app-mbook";
	public static final String SERVER = String.format("%s/update.txt",
			SERVER_MAIN);

	// get to file main
	public static final String SERVER_ZIP = String.format("%s/mBook.zip",
			SERVER_MAIN);
	public static final String SERVER_CONTENT = "SERVER_CONTENT";

	public static final String BROASTCAST_UPDATE_UI = "BROASTCAST_UPDATE_UI";

}
