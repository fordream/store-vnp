package vn.com.vega.music.utils;

public interface Const {
	
	public static final String LOG_PREF = "ChaCha ";
	public static final int VEGA_LOG_LEVEL = VegaLog.VEGA_LOG_INFO;

	/**
	 * Feature type
	 * 
	 * @author khainv
	 */
	public static final int TYPE_FEATURED_NONE = -1;
	public static final int TYPE_FEATURED_ARTISTS = 0;
	public static final int TYPE_FEATURED_SONGS = 1;
	public static final int TYPE_FEATURED_VIDEOS = 2;
	public static final int TYPE_FEATURED_ALBUMS = 3;
	public static final int TYPE_FEATURED_PLAYLISTS = 4;
	public static final int TYPE_FEATURED_NEWS = 5;

	/**
	 * Tab selected
	 */
	public static final int TAB_SONG = 1;
	public static final int TAB_ALBUM = 2;

	/**
	 * Object
	 */
	public static final String ARTIST_OBJECT = "artist";
	public static final String ALBUM_OBJECT = "album";
	public static final String SONG_OBJECT = "song";
	public static final String PLAYLIST_OBJECT = "playlist";
	public static final String NEWS_OBJECT = "news";
	public static final String VIDEO_OBJECT = "video";
	public static final String OBJECT = "object";

	public static final String OBJECT_ID = "id";
	public static final String OBJECT_NAME = "name";
	public static final String OBJECT_IMAGE_URL = "image_url";

	public static final String SONG_COUNT = "song_count";
	public static final String ALBUM_COUNT = "album_count";

	public static final String FOLDER_IMAGE = "images";
	public static final String FOLDER_MUSIC = "music";
	public static final String FOLDER_LOGS = "logs";
	public static final String MUSIC_EXT = ".vega";

	/**
	 * ListView
	 */
	public static final int LIST_LIMITED = 100;

	/**
	 * Inter-thread communication code
	 */
	public static final int MSG_SUCCESS = 0;
	public static final int MSG_FAILED = -1;
	public static final int MSG_FATAL = -1000;

	/**
	 * Search state
	 */
	public static final int STATE_SEARCH_OLD = 0;
	public static final int STATE_SEARCH_NEW = 1;

	/**
	 * Dialog
	 */
	public static final int DIALOG_SWITCH_ONLINE_MODE = 0;
	public static final int DIALOG_LOGOUT = 1;

	/**
	 * Preferences
	 */
	public static final String PREFERNCE_NAME = "vn.com.vega.imuzik";
	public static final int MODE_PRIVATE = 0;

	/**
	 * Download
	 */
	public static final int CONNECTING_VALUE = 0;
	public static final int WAITING_VALUE = 200;
	public static final int DOWNLOADED_VALUE = 100;

	public static final String EMPTY_LIST = "0";

	public static final String VALUE_OFFLINE = "offline";
	public static final String VALUE_ONLINE = "online";
	public static final String VALUE_OFF = "Off";
	public static final String VALUE_ON = "On";

	public static final String MY_PLAYLIST = "My Playlist";
	public static final String MY_PLAYLIST_DETAIL = "My Playlist Detail";

	// suffix. for example: 30 bài
	public static final String SONG_SUFFIX = " bài";
	public static final String SONG_PREFIX = "Có ";

	// name of bundles that pass through intent
	public static final String BUNDLE_PLAYLIST_TYPE = "Playlist Type";
	public static final String BUNDLE_PLAYLIST_ID = "Playlist Id";
	public static final String BUNDLE_SONG_ID = "Song Id";
	public static final String BUNDLE_SONG_LIST = "Song List";
	public static final String BUNDLE_NETWORK_TYPE = "Network Type";
	public static final String BUNDLE_NOT_IN_GROUP = "Not In Group";
	public static final String BUNDLE_IS_LOGOUT = "Is Logout";
	
	public static final String BUNDLE_DOWNLOADED_MUSIC = "Downloaded Music";
	
	
	public static final String SELECTED_TAB = "Selected tab";

	// Result status
	public static final int INITIALIZE_SUCCESS = 0;
	public static final int INITIALIZE_FAILURE = 1;

	// Config
	public static final String CURRENT_VERSION = "Current Version";
	public static final String OFFLINE_MODE = "Offline Mode";
	public static final String KEY_MSISDN = "Msisdn";
	public static final String KEY_DOWNLOAD_VIA_3G = "Download Via 3G";
	public static final String KEY_NOTIFY_WHEN_NEW_PLAYLIST = "Notify_New_Playlist";
	public static final String KEY_NOTIFY_WHEN_LIKE_PLAYLIST = "Notify_Like_Playlist";
	public static final String KEY_NOTIFY_WHEN_SERVER_UPDATE = "Notify_Server_Update";
	public static final String KEY_NOTIFY_WHEN_SHARING = "Notify_Sharing";
	public static final String KEY_NOTIFY_WHEN_ARTIST_UPDATE = "Notify_Artis_Update";
	public static final String KEY_PRICE = "Price";
	public static final String KEY_PASSWORD = "Password";
	public static final String MEMORY_LIMIT = "Memory Limit";
	public static final String KEY_SMS_SERVER = "Sms server";
	public static final String KEY_SMS_CONTENT = "Sms content";
	public static final String KEY_WARNING_MSG = "Warning message";
	public static final String KEY_ASK_RATE = "Ask rate";
	public static final String KEY_LOGIN_COUNT = "Login count";
	public static final String KEY_NOT_REGISTERED = "Not registered";

	
	public static final String KEY_FB_ACCESS_TOKEN = "Facebook access token";
	public static final String KEY_FB_ACCESS_EXPIRE = "Facebook access expire";
	public static final String KEY_GM_ACCESS_TOKEN = "Gmail access token";
	public static final String KEY_GM_ACCESS_EXPIRE = "Gmail access expire";

	
	// Config: Account
	public static final String KEY_LOGIN_NAME = "login_name";
	public static final String KEY_PACKAGE_CODE = "package_code";
	public static final String KEY_BEGIN_DATE = "begin_date";
	public static final String KEY_END_DATE = "end_date";
	public static final String KEY_ACCOUNT_INFO = "account_info";
	public static final String KEY_AVATAR = "avatar";
	public static final String KEY_FULL_NAME = "full_name";
	public static final String KEY_PHONE_NUMBER = "phone_number";
	public static final String KEY_PHONE_NUMBER_EDITABLE = "phone_number_editable";
	public static final String KEY_DOB = "dob";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_EMAIL_EDITABLE = "email_editable";

	// intent action
	public static final String ACTION_UPDATE_SETTING_SCREEN = "Update Setting";
	public static final String ACTION_DIMISS_DIALOG = "Dismiss Dialog";
	
	// SECTION HEADER
	
	public static final int FIRST_SECTION_HEADER = 101;
	public static final int SECOND_SECTION_HEADER = 102;
	
	// List item limit
	public static final int LIST_ITEM_LIMIT = 100;
	
	/**
	 * Friend type
	 */
	public static final int TYPE_FRIEND_IDOL_LISTING = 1;
	public static final int TYPE_FRIEND_FAN_LISTING = 2;
	public static final int TYPE_FRIEND_IDOL_SEARCHING = 3;
	public static final int TYPE_FRIEND_FAN_SEARCHING = 4;
	public static final int TYPE_FRIEND_CHACHA = 5;
	public static final int TYPE_FRIEND_FACEBOOK = 6;
	public static final int TYPE_FRIEND_GMAIL = 7;
}
