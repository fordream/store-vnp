package vn.com.vega.music.clientserver;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.utils.VegaLog;

public class ClientServer {

	private static final String LOG_TAG = ClientServer.class.getSimpleName();

	private static final int DEFAULT_LIMIT = 20;

	private static final String PREF_API_PRIVATE_3G = null;
	private static final String PREF_API_PUBLIC_1 = "http://api.chacha.vn";
	private static final String PREF_API_PUBLIC_2 = null;

	private static final String CLIENT_PROFILE = "Imuzik3G_Android_1.0";
	private static final String SECRET_KEY = "pOwErOvErwhElmIng";
	private static final String CLIENT_VERSION = "20111121";

	private static ClientServer sInstance = null;

	/**
	 * Instance variables
	 */
	private class ServerAddress {

		public static final int PREF_3G = 0;
		public static final int PREF_NONE = 2;

		public String mServerAddress;
		public boolean mTried = false;
		public int mNetworkPrefer = 0;

		public ServerAddress(String addr, int pref) {
			mServerAddress = addr;
			mNetworkPrefer = pref;
		}
	}

	private int mCurrentServerIndex = -1;
	private String URL_API_ROOT = null;
	private ArrayList<ServerAddress> mServerAddresses = new ArrayList<ServerAddress>();

	public static ClientServer getInstance() {
		synchronized (ClientServer.class) {
			if (sInstance == null) {
				sInstance = new ClientServer();
			}
			return sInstance;
		}
	}

	public static void delloc() {
		synchronized (ClientServer.class) {
			sInstance = null;
		}
	}

	// Avoid creating an instance
	private ClientServer() {
		if (mServerAddresses.isEmpty()) {
			addServer(PREF_API_PRIVATE_3G, ServerAddress.PREF_3G);
			addServer(PREF_API_PUBLIC_1, ServerAddress.PREF_NONE);
			addServer(PREF_API_PUBLIC_2, ServerAddress.PREF_NONE);
		}
	}

	private void addServer(String url, int pref) {
		if (url != null) {
			mServerAddresses.add(new ServerAddress(url, pref));
		}
	}

	public boolean isCurrentServerIsPreferedFor3G() {
		if ((mCurrentServerIndex < 0) || (mCurrentServerIndex >= mServerAddresses.size())) {
			selectNextServer();
		}
		if ((mCurrentServerIndex >= 0) && (mCurrentServerIndex < mServerAddresses.size())) {
			return (mServerAddresses.get(mCurrentServerIndex).mNetworkPrefer == ServerAddress.PREF_3G);
		}
		return false;
	}

	public void resetAllServerStatus() {
		mCurrentServerIndex = -1;
		URL_API_ROOT = null;
		for (ServerAddress server : mServerAddresses) {
			server.mTried = false;
		}
		selectNextServer();
	}

	public boolean onFailedSwitchNextServer() {
		if ((mCurrentServerIndex >= 0) && (mCurrentServerIndex < mServerAddresses.size())) {
			mServerAddresses.get(mCurrentServerIndex).mTried = false;
		}
		return selectNextServer();
	}

	private void checkSelectServer() {
		if (URL_API_ROOT == null) {
			selectNextServer();
		}
	}

	private boolean selectNextServer() {
		int network = NetworkUtility.getNetworkStatus();
		VegaLog.e(LOG_TAG, "Networ = " + network);

		// Search if in 3G
		if (network == NetworkUtility.CONNECTION_TYPE_3G) {
			for (int i = 0; i < mServerAddresses.size(); i++) {
				ServerAddress server = mServerAddresses.get(i);
				VegaLog.e(LOG_TAG, "Server: " + server.mServerAddress);

				if (!server.mTried && (server.mNetworkPrefer == ServerAddress.PREF_3G)) {
					this.URL_API_ROOT = server.mServerAddress;
					mCurrentServerIndex = i;
					return true;
				}
			}
		}

		// Not found or not 3G, search for public IPs
		for (int i = 0; i < mServerAddresses.size(); i++) {
			ServerAddress server = mServerAddresses.get(i);
			if (!server.mTried && (server.mNetworkPrefer == ServerAddress.PREF_NONE)) {
				this.URL_API_ROOT = server.mServerAddress;
				mCurrentServerIndex = i;
				return true;
			}
		}

		// Still not found, search for any available address
		for (int i = 0; i < mServerAddresses.size(); i++) {
			ServerAddress server = mServerAddresses.get(i);
			if (!server.mTried) {
				this.URL_API_ROOT = server.mServerAddress;
				mCurrentServerIndex = i;
				return true;
			}
		}

		// Still not found, what's crazy
		return false;
	}

	/*
	 * Authentication
	 */
	protected String getUrlAuthRequestRandomKey() {
		checkSelectServer();
		return URL_API_ROOT + "/auth/randomkey";
	}

	protected String getUrlAuthLogout() {
		checkSelectServer();
		return URL_API_ROOT + "/account/logout";
	}

	protected String getUrlAuthSubmitHash() {
		checkSelectServer();
		return URL_API_ROOT + "/auth/sessionkey";
	}

	protected String calcAuthHash(String ranKey) {
		// String finalKey = "VegaMusicStation" + key + "@2011"; // old code
		String finalKey = SECRET_KEY + ranKey + CLIENT_PROFILE + CLIENT_VERSION;
		// VegaLog.v(LOG_TAG, "finalKey=" + finalKey);
		return standardMD5(finalKey);
	}

	protected String standardMD5(String md5) {
		try {

			// VegaLog.v(LOG_TAG, "Source:: |" + md5 + "|");

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(md5.getBytes());

			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();

			// convert the byte to hex format method 1
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			// VegaLog.v(LOG_TAG, "Digest(in hex format):: |" + sb.toString() +
			// "|");
			return sb.toString();

			/*
			 * //convert the byte to hex format method 2 StringBuffer hexString
			 * = new StringBuffer(); for (int i=0;i<byteData.length;i++) {
			 * String hex=Integer.toHexString(0xff & byteData[i]);
			 * if(hex.length()==1) hexString.append('0'); hexString.append(hex);
			 * } //System.out.println("Digest(in hex format):: " +
			 * hexString.toString()); return sb.toString();
			 */
		} catch (java.security.NoSuchAlgorithmException e) {
			return "";
		}

	}

	protected String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	protected String getUrlAuthLogin(String user, String pass) {
		checkSelectServer();
		return URL_API_ROOT + "/account/login/?username=" + user + "&password=" + pass;
	}

	protected String getUrlAuthValidate(String hash) {
		checkSelectServer();
		return URL_API_ROOT + "/auth/validate/?hash=" + hash + "&client=" + URLEncoder.encode(CLIENT_PROFILE) + "&version="
				+ URLEncoder.encode(CLIENT_VERSION);
	}

	protected String getUrlAuthIdentify() {
		checkSelectServer();
		return URL_API_ROOT + "/account/identify";
	}

	protected String getUrlPackage() {
		checkSelectServer();
		return URL_API_ROOT + "/package/list";
	}

	protected String getUrlAuthSubscribe(String packageID) {
		checkSelectServer();
		return URL_API_ROOT + "/account/register/" + packageID;
	}
	
	protected String getUrlUploadAvatar(){
		checkSelectServer();
		return URL_API_ROOT + "/account/avatar";
	}
	
	protected String getUrlSetNotificationConfig(int param1, int param2, int param3, int param4, int param5){
		checkSelectServer();
		return URL_API_ROOT + "/account/updatenotificationconfig?email_on_new_playlist=" + param1 + "&email_on_like_playlist=" + param2 + "&email_on_service_news=" + param3 + "&sms_on_shared_to_me=" + param4 + "&sms_on_artist_updated=" + param5;
	}

	protected String getUrlGetNotificationConfig(){
		checkSelectServer();
		return URL_API_ROOT + "/account/getnotificationconfig";
	}
	
	protected String getUrlShareSong(int songId){
		checkSelectServer();
		return URL_API_ROOT + "/songs/" + songId + "/share";
	}
	
	protected String getUrlSharePlaylist(int playlistId){
		checkSelectServer();
		return URL_API_ROOT + "/playlist/" + playlistId + "/share";
	}
	
	protected String getUrlAuthUnsubscribe() {
		checkSelectServer();
		return URL_API_ROOT + "/account/cancel";
	}

	protected String getUrlAuthAddTime() {
		checkSelectServer();
		return URL_API_ROOT + "/account/addtime";
	}
	
	protected String getUrlAuthUpdateAccount(String name, String phone, String email, String dob, int gender, String address) {
		checkSelectServer();
		String url = URL_API_ROOT + "/account/updateinfo?full_name=" + URLEncoder.encode(name)							
							+ "&dob=" + URLEncoder.encode(dob)
							+ "&gender=" + gender
							+ "&address=" + URLEncoder.encode(address);
		if (phone != null)
			url += "&phone_number=" + phone;
		if (email != null)
			url += "&email=" + email;
		return url;
	}

	/*
	 * Sync
	 */
	protected String getUrlSync() {
		checkSelectServer();
		// return URL_API_ROOT + "/index.php?_controller=playlist_sync";
		return URL_API_ROOT + "/playlist/sync";
	}

	/*
	 * Common
	 */
	protected String getUrlSystemConfig() {
		checkSelectServer();
		return URL_API_ROOT + "/config";
	}

	/*
	 * Artist
	 */
	protected String getUrlListTopArtist(int offset) {
		checkSelectServer();
		if (offset >= 0) {
			return URL_API_ROOT + "/artists/top?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
		} else {
			return URL_API_ROOT + "/artists/top?offset=0&limit=" + DEFAULT_LIMIT;
		}
	}
	
	/*
	 * Friend
	 */
	protected String getSubscribedFriends(int offset) {
		checkSelectServer();
		if (offset >= 0) {
			return URL_API_ROOT + "/account/subscribes?limit=" + DEFAULT_LIMIT;
		} else {
			return URL_API_ROOT + "/account/subscribes?limit=" + DEFAULT_LIMIT;
		}

	}
	
	protected String searchSubcribedFriends(int offset, String keyword) {
		checkSelectServer();
		if (offset >= 0) {
			return URL_API_ROOT + "/account/search?type=idol&keyword=" + keyword + "&limit=" + DEFAULT_LIMIT;
		} else {
			return URL_API_ROOT + "/account/search?type=idol&keyword=" + keyword + "&limit=" + DEFAULT_LIMIT;
		}
	}
	
	protected String findUser(String keyword) {
		checkSelectServer();
		return URL_API_ROOT + "/friends/search?keyword=" + keyword;
	}
	
	protected String Subscribed(String FrienID) {
		checkSelectServer();
		return URL_API_ROOT + "/friends/subscribe?friend_id=" + FrienID;
	}
	
	protected String UnSubscribed(String FriendID) {
		checkSelectServer();
		return URL_API_ROOT + "/friends/unsubscribe?friend_id=" + FriendID;
	}
	
	/*
	 * Fan
	 */
	protected String getSubscribedMeFriends(int offset) {
		checkSelectServer();
		if (offset >= 0) {
			return URL_API_ROOT + "/account/getFriendsSubscribeMe?limit=" + DEFAULT_LIMIT;
		} else {
			return URL_API_ROOT + "/account/getFriendsSubscribeMe?limit=" + DEFAULT_LIMIT;
		}
	}
	
	protected String searchSubcribedMeFriends(int offset, String keyword) {
		checkSelectServer();
		if (offset >= 0) {
			return URL_API_ROOT + "/account/search?type=fan&keyword=" + keyword + "&limit=" + DEFAULT_LIMIT;
		} else {
			return URL_API_ROOT + "/account/search?type=fan&keyword=" + keyword + "&limit=" + DEFAULT_LIMIT;
		}
	}
	
	public String getUrlChangePassword(String username, String oldpass, String newpass){
		checkSelectServer();
		return URL_API_ROOT + "/account/changepass?username=" + username + "&oldpassword=" + oldpass + "&newpassword=" + newpass;
	}
	
	/*
	 * Albums
	 */

	protected String getUrlListAlbumByArtist(int artistId, int offset) {
		checkSelectServer();
		return URL_API_ROOT + "/artists/" + artistId + "/albums?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	protected String getUrlBillboardAlbum() {
		checkSelectServer();
		return URL_API_ROOT + "/albums/charts";
	}

	protected String getUrlListNewAlbum(int offset) {
		checkSelectServer();
		return URL_API_ROOT + "/albums/new?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}
	
	/*
	 * Facebook
	 */
	protected String getUrlFriendOnFacebook(String access_token) {
		checkSelectServer();
		return URL_API_ROOT + "/friends/search/fb?access_token=" + access_token;
	}

	/*
	 * Gmail
	 */
	
	protected String getUrlFriendOnGmail(String access_token) {
		checkSelectServer();
		return URL_API_ROOT + "/friends/search/gmail?access_token=" + access_token;
	}
	
	/*
	 * Songs
	 */

	protected String getUrlListTopSong(int offset) {
		checkSelectServer();
		return URL_API_ROOT + "/songs/top?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	protected String getUrlListSongByAlbum(int albumId) {
		checkSelectServer();
		return URL_API_ROOT + "/albums/" + albumId + "/songs";
	}

	protected String getUrlListSongByPlaylist(int playlistId) {
		checkSelectServer();
		return URL_API_ROOT + "/playlist/" + playlistId + "/songs";
	}

	protected String getUrlBillboardSong() {
		checkSelectServer();
		return URL_API_ROOT + "/songs/charts";
	}

	protected String getUrlListSongByArtist(int artistId, int offset) {
		checkSelectServer();
		return URL_API_ROOT + "/artists/" + artistId + "/songs?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	protected String getUrlSongLyrics(int songId) {
		checkSelectServer();
		return URL_API_ROOT + "/songs/" + songId + "/lyrics";
	}

	protected String getUrlLikePlaylist(int playlistId) {
		checkSelectServer();
		return URL_API_ROOT + "/playlist/" + playlistId + "/favorite";
	}

	protected String getUrlUnLikePlaylist(int playlistId) {
		checkSelectServer();
		return URL_API_ROOT + "/playlist/" + playlistId + "/unfavorite";
	}

	protected String getUrlDownloadBySongId(int songId) {
		checkSelectServer();
		return URL_API_ROOT + "/songs/" + songId + "/download";
	}

	protected String getUrlWatchByVideoId(int videoId) {
		checkSelectServer();
		return URL_API_ROOT + "/videos/" + videoId + "/watch";
	}

	protected String getUrlListenBySongId(int songId) {
		checkSelectServer();
		return URL_API_ROOT + "/songs/" + songId + "/listen";
	}

	protected String getUrlSongInfo(int songId) {
		checkSelectServer();
		return URL_API_ROOT + "/songs/" + songId;
	}

	protected String getUrlAlbumInfo(int albumId) {
		checkSelectServer();
		return URL_API_ROOT + "/albums/" + albumId;
	}

	protected String getUrlArtistInfo(int artistId) {
		checkSelectServer();
		return URL_API_ROOT + "/artists/" + artistId;
	}

	protected String getUrlFollowArtist(int artistId) {
		checkSelectServer();
		return URL_API_ROOT + "/artists/" + artistId + "/follow";
	}

	public String getUrlUnFollowArtist(int artistId) {
		checkSelectServer();
		return URL_API_ROOT + "/artists/" + artistId + "/unfollow";
	}

	/*
	 * Search
	 */

	protected String getUrlSearchSong(String keyword, int offset) {
		checkSelectServer();
		keyword = URLEncoder.encode(keyword);
		return URL_API_ROOT + "/songs/?keyword=" + keyword + "&offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	protected String getUrlSearchAlbum(String keyword, int offset) {
		checkSelectServer();
		keyword = URLEncoder.encode(keyword);
		return URL_API_ROOT + "/albums/?keyword=" + keyword + "&offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	protected String getUrlSearchArtist(String keyword, int offset) {
		checkSelectServer();
		keyword = URLEncoder.encode(keyword);
		return URL_API_ROOT + "/artists?keyword=" + keyword + "&offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	protected String getUrlSearchPlaylist(String keyword, int offset) {
		checkSelectServer();
		keyword = URLEncoder.encode(keyword);
		return URL_API_ROOT + "/playlist?keyword=" + keyword + "&offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	protected String getUrlSearchVideo(String keyword, int offset) {
		checkSelectServer();
		keyword = URLEncoder.encode(keyword);
		return URL_API_ROOT + "/videos/?keyword=" + keyword + "&offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	/*
	 * Playlist
	 */
	protected String getUrlListTopPlaylist(int offset) {
		checkSelectServer();
		return URL_API_ROOT + "/playlist/features?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	/*
	 * Video
	 */
	protected String getUrlListTopVideo(int offset) {
		checkSelectServer();
		return URL_API_ROOT + "/videos?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	protected String getUrlListVideoByArtist(int artistId, int offset) {
		checkSelectServer();
		return URL_API_ROOT + "/artists/" + artistId + "/videos?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

	protected String getUrlBillboardVideo() {
		checkSelectServer();
		return URL_API_ROOT + "/videos/charts";
	}

	/*
	 * News
	 */
	protected String getUrlListTopNews(int offset) {
		checkSelectServer();
		return URL_API_ROOT + "/news?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
	}

}
