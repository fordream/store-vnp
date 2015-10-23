package com.icts.utils;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.icts.object.FriendObject;
import com.icts.object.MessageObject;
import com.icts.object.User;

/**
 * Class connect to database: Chat user and message table (chat history).<p>
 * <b>Features:</b>
 *  - Add user and message
 *  - Update info of user
 *  - the number of messages and users limit: 50 
 * @author Luong
 *
 */
public class DatabaseAdapter {
	private final int MAX_USER = 50;
	private final int MAX_MESSAGE = 50;
	private static final String CREATE_DATABASE_CHAT_USER = 
			"Create table chat_user (_id integer primary key autoincrement, user_id text, user_nick text not null,"+
			"uuid text, itell text, itell_time long,address text, " +
			"latitude text, longitude text, avatar text,gender integer, isfriend integer);";
	
	private static final String CREATE_DATABASE_MESSAGE = 
			"Create table message (_id integer primary key autoincrement, "+
			"user_id text not null, message text, sender_id text not null, receiver_id text not null, " +
			"message_timestamp long, isStamp integer default 0);";
	//Stamp istamp = 1
	
	private static final String DatabaseName = "Database_itell";
	private static final String TableUserName = "chat_user";
	private static final String TableMessageName = "message";
	
	public static final String Id = "_id";
	public static final String Key_User_Id = "user_id";
	public static final String Key_User_Nick = "user_nick";
	public static final String Key_UUID = "uuid";
	public static final String Key_itell = "itell";
	public static final String Key_gender = "gender";
	public static final String Key_itell_time = "itell_time";
	public static final String Key_address = "address";
	public static final String Key_lat = "latitude";
	public static final String Key_long = "longitude";
	public static final String Key_avatar = "avatar";
	public static final String Key_IsFriend = "isfriend";
	
	
	//New 
	public static final String Key_Mesage_User_Id = "user_id";
	public static final String Key_Message= "message";
	public static final String Key_Sender_id = "sender_id";
	public static final String Key_Receiver_id = "receiver_id";
	public static final String Key_Timestamp = "message_timestamp";
	public static final String Key_isStamp = "isStamp";
	/**
	 * Get messages
	 * @return
	 */
	public ArrayList<FriendObject> getUsers(){
		
		Cursor cur = sqlDatabase.query(TableUserName, new String[]{Id, Key_User_Id,Key_User_Nick,Key_UUID,
				Key_itell, Key_itell_time,Key_address,Key_lat,Key_long,Key_avatar,Key_gender,Key_IsFriend},
				null, null, null, null, null);
		return getUserData(cur);
	}
	
	/**
	 * Get messages
	 * @return
	 */
	public Cursor getMessages(){
		return sqlDatabase.query(TableMessageName, new String[]{Id,Key_Mesage_User_Id,Key_Message,Key_Sender_id,
				Key_Receiver_id, Key_Timestamp,Key_isStamp},
				null, null, null, null, null);
	}
	
	/**
	 * get message from cursor 
	 * @param cur
	 * @return
	 */
	public ArrayList<MessageObject> getMessageData(Cursor cur){
		ArrayList<MessageObject> listMessage = new ArrayList<MessageObject>();
		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			 MessageObject message= new MessageObject();
			 message.setMessage(cur.getString(cur.getColumnIndex(Key_Message)));
			 message.setSenderID(cur.getString(cur.getColumnIndex(Key_Sender_id)));
			 message.setReceiverID(cur.getString(cur.getColumnIndex(Key_Receiver_id)));
			 message.setTimestamp(cur.getLong(cur.getColumnIndex(Key_Timestamp)));
			 message.setStamp(cur.getInt(cur.getColumnIndex(Key_isStamp)));
			 listMessage.add(message);
	       	 cur.moveToNext();
	     }
	     cur.close();
	     return listMessage;
	}
	
	
	public ArrayList<MessageObject> getMessages(String id){
		ArrayList<MessageObject> listMessage = new ArrayList<MessageObject>();
		String count = "SELECT * FROM "+TableMessageName +" where "+Key_Mesage_User_Id+" = ?";
		Cursor cur = sqlDatabase.rawQuery(count,new String[]{id});
		cur.moveToFirst(); 
		while (cur.isAfterLast() == false) {
			MessageObject message= new MessageObject();
			message.setMessage(cur.getString(cur.getColumnIndex(Key_Message)));
			message.setSenderID(cur.getString(cur.getColumnIndex(Key_Sender_id)));
			message.setReceiverID(cur.getString(cur.getColumnIndex(Key_Receiver_id)));
			message.setTimestamp(cur.getLong(cur.getColumnIndex(Key_Timestamp)));
			message.setStamp(cur.getInt(cur.getColumnIndex(Key_isStamp)));
			listMessage.add(message);
	       	cur.moveToNext();
	    }
	    cur.close();
		return listMessage;
	}
	
	public ArrayList<MessageObject> getMessages(String id,String otherID){
		ArrayList<MessageObject> listMessage = new ArrayList<MessageObject>();
		String count = "SELECT * FROM "+TableMessageName +" where ("+Key_Sender_id+" = ? and "+
						Key_Receiver_id+" = ?) OR ("+Key_Receiver_id+" = ? and "+Key_Sender_id+" = ?)";
		Cursor cur = sqlDatabase.rawQuery(count,new String[]{id,otherID,id,otherID});
		cur.moveToFirst(); 
		while (cur.isAfterLast() == false) {
			MessageObject message= new MessageObject();
			message.setMessage(cur.getString(cur.getColumnIndex(Key_Message)));
			message.setSenderID(cur.getString(cur.getColumnIndex(Key_Sender_id)));
			message.setReceiverID(cur.getString(cur.getColumnIndex(Key_Receiver_id)));
			message.setTimestamp(cur.getLong(cur.getColumnIndex(Key_Timestamp)));
			message.setStamp(cur.getInt(cur.getColumnIndex(Key_isStamp)));
			listMessage.add(message);
	       	cur.moveToNext();
	    }
	    cur.close();
		return listMessage;
	}
	
	private JSONArray jsonIDs;
	
	public String getArrayID(){
		if (jsonIDs ==null){
			return null;
		}
		String s = jsonIDs.toString();
		jsonIDs = null;
		return s;
	}
	
	/**
	 * get message from cursor 
	 * @param cur
	 * @return
	 */
	public ArrayList<FriendObject> getUserData(Cursor cur){
		ArrayList<FriendObject> listUser = new ArrayList<FriendObject>();
		cur.moveToFirst();
		jsonIDs = new JSONArray();
		while (cur.isAfterLast() == false) {
			 FriendObject friend = new FriendObject();
			 int id = Integer.parseInt(cur.getString(cur.getColumnIndex(Key_User_Id)));
			 jsonIDs.put(id);
			 friend.setFriendID(id);
			 friend.uuid = cur.getString(cur.getColumnIndex(Key_UUID));
			 friend.setFriendAddress(cur.getString(cur.getColumnIndex(Key_address)));
			 friend.setFriendNick(cur.getString(cur.getColumnIndex(Key_User_Nick)));
			 friend.setFriendiTell(cur.getString(cur.getColumnIndex(Key_itell)));
			 friend.setFriendStatTime(cur.getLong(cur.getColumnIndex(Key_itell_time)));
			 friend.setFriendLat(Double.parseDouble((cur.getString(cur.getColumnIndex(Key_lat)))));
			 friend.setFriendLong(Double.parseDouble(cur.getString(cur.getColumnIndex(Key_long))));
			 friend.setFriendAva(cur.getString(cur.getColumnIndex(Key_avatar)));
			 friend.setMale(false);
			 if (cur.getInt(cur.getColumnIndex(Key_gender))==0){
				 friend.setMale(true);
			 }
			 friend.setFriend(false);
			 if (cur.getInt(cur.getColumnIndex(Key_IsFriend))==1){
				 friend.setFriend(true);
			 }
			 listUser.add(friend);
	       	 cur.moveToNext();
	     }
	     cur.close();
	     return listUser;
	}
	/** Add user into table
	 * 
	 * @param user
	 * @return
	 */
	public long createUser(User user){
		if (exist(0, user.getUserId())){
			updateUser(user);
			return 0;
		}
		String table = TableUserName;
		Long i = count(0);
		
		//delete the first item
		if (i>=MAX_USER){
			int id = getMinID(0);
			deleteRow(id, 0);
		}
		
		ContentValues newValue = new ContentValues();
		newValue.put(Key_User_Id,user.getUserId());
		newValue.put(Key_User_Nick,user.getUserNick());
		newValue.put(Key_UUID, user.uuid);
		newValue.put(Key_itell, user.getStatus());
		
		newValue.put(Key_itell_time, user.getStatusUpdateTime());
		newValue.put(Key_address, user.getAddress());
		newValue.put(Key_lat, String.valueOf(user.latitude));
		newValue.put(Key_long, String.valueOf(user.longitude));
		newValue.put(Key_avatar, user.getImageUrl());
		if (user.isMale()){
			newValue.put(Key_gender, 0);
		}
		else {
			newValue.put(Key_gender, 1);
		}
		
		if (user.isFriend()){
			newValue.put(Key_IsFriend, 1);
		}
		else {
			newValue.put(Key_IsFriend, 0);
		}
		//End new
		return sqlDatabase.insert(table, null, newValue);
	}
	
	/** Add user (friend) into table
	 * 
	 * @param user
	 * @return
	 */
	public long createUser(FriendObject user){
		
		if (exist(0, String.valueOf(user.getFriendID()))){
			updateUser(user);
			return 0;
		}
		String table = TableUserName;
		Long i = count(0);
		
		//delete the first item
		if (i>=MAX_USER){
			int id = getMinID(0);
			deleteRow(id, 0);
		}
		
		ContentValues newValue = new ContentValues();
		newValue.put(Key_User_Id,String.valueOf(user.getFriendID()));
		newValue.put(Key_User_Nick,user.getFriendNick());
		newValue.put(Key_UUID, user.uuid);
		newValue.put(Key_itell, user.getFriendiTell());
		
		newValue.put(Key_itell_time, user.getFriendStatTime());
		newValue.put(Key_address, user.getFriendAddress());
		newValue.put(Key_lat, String.valueOf(user.getFriendLat()));
		newValue.put(Key_long, String.valueOf(user.getFriendLong()));
		newValue.put(Key_avatar, user.getFriendAva());
		if (user.isMale()){
			newValue.put(Key_gender, 0);
		}
		else {
			newValue.put(Key_gender, 1);
		}
		
		if (user.isFriend()){
			newValue.put(Key_IsFriend, 1);
		}
		else {
			newValue.put(Key_IsFriend, 0);
		}
		//End new
		return sqlDatabase.insert(table, null, newValue);
	}
	
	/** Add message into message table
	 * 
	 * @param message
	 * @return
	 */
	public long createMessage(MessageObject message){
		String table = TableMessageName;
		/*Long i = count(1);
		
		//delete the first item
		if (i>=MAX_MESSAGE){
			int id = getMinID(1);
			deleteRow(id, 1);
		}
		*/
		
		deleteMess(message.getReceiverID(),message.getSenderID());
		
		ContentValues newValue = new ContentValues();
		newValue.put(Key_Mesage_User_Id,message.getReceiverID());
		if (message.isStamp()){
			newValue.put(Key_Message,"'"+ message.getMessage()+"'");
		}
		else {
			newValue.put(Key_Message,message.getMessage());
		}
		newValue.put(Key_Sender_id, message.getSenderID());
		newValue.put(Key_Receiver_id, message.getReceiverID());
		newValue.put(Key_Timestamp, message.getTimestamp());
		int isStamp = message.isStamp()?1:0;
		newValue.put(Key_isStamp, isStamp);
		//End new
		return sqlDatabase.insert(table, null, newValue);
	}
	private DatabaseHelper dbHelper;
	private final Context mContext;
	
	private SQLiteDatabase sqlDatabase;
	private static final int Version = 3;
	
	private static class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		/* Tao lai database*/
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_DATABASE_CHAT_USER);
			db.execSQL(CREATE_DATABASE_MESSAGE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS chat_user");
			db.execSQL("DROP TABLE IF EXISTS message");
			onCreate(db);
		}
		
	}
	
	public DatabaseAdapter(Context ct)
	{
		mContext = ct;
	}
	
	/* Mo ket noi voi database
	 */
	public DatabaseAdapter open()
	{
		dbHelper = new DatabaseHelper(mContext, DatabaseName, null,Version);
		sqlDatabase = dbHelper.getWritableDatabase();
		return this;
	}
	
	
	/** Delete User based on USER_ID 
	 * @param id
	 * @return true if succeed 
	 */
	public boolean deleteUser(String id){
		String table = TableUserName;
		return sqlDatabase.delete(table, Key_User_Id+ "=" +id, null)>0;
	}
	
	/** Delete User based on USER_ID 
	 * @param id
	 * @return true if succeed 
	 */
	public boolean deleteMessage(String id){
		String table = TableMessageName;
		return sqlDatabase.delete(table, Key_Mesage_User_Id+ "=" +id, null)>0;
	}
	
	/** Delete coupon based on ID 
	 * @param id
	 * @param select 0: history
	 * 1: favorite
	 * @return true if succeed 
	 */
	public boolean deleteRow(int id,int select)
	{
		String table = TableUserName;
		if (select ==1){
			table = TableMessageName;
		}
		
		return sqlDatabase.delete(table, Id+ "=" +id, null)>0;
	}
	
	
	/**Get max ID of table
	 * @param select
	 * @return
	 */
	public int getMaxID(int select){
		String table = TableUserName;
		if (select ==1){
			table = TableMessageName;
		}
	    String count = "SELECT max(_id) FROM "+table;
	    Cursor mcursor = sqlDatabase.rawQuery(count, null);
	    mcursor.moveToFirst();
	    int imax = mcursor.getInt(0);
	    mcursor.close();
	    return imax;
	}
	
	/**Get min ID of table
	 * @param select
	 * @return
	 */
	public int getMinID(int select){
		String table = TableUserName;
		if (select ==1){
			table = TableMessageName;
		}
	    String count = "SELECT min(_id) FROM "+table;
	    Cursor mcursor = sqlDatabase.rawQuery(count, null);
	    mcursor.moveToFirst();
	    int imin = mcursor.getInt(0);
	    mcursor.close();
	    return imin;
	}
	
	/**Get min ID of table
	 * @param select
	 * @return
	 */
	public int getMinIDMessage(String idReceiver,String idSender){
		String table = TableMessageName;
	    String count = "SELECT min(_id) FROM "+table+" where "+Key_Receiver_id+" = ? AND"+Key_Sender_id+" = ?";
	    Cursor mcursor = sqlDatabase.rawQuery(count, new String[]{idReceiver,idSender});
	    mcursor.moveToFirst();
	    int imin = mcursor.getInt(0);
	    mcursor.close();
	    return imin;
	}
	
	/** Update user based on ID 
	 * @param id
	 * @return true if succeed 
	 */
	public boolean updateUser(User user){
		String table = TableUserName;
		ContentValues newValue = new ContentValues();
		newValue.put(Key_User_Nick,user.getUserNick());
		newValue.put(Key_UUID, user.uuid);
		newValue.put(Key_itell, user.getStatus());
		
		newValue.put(Key_itell_time, user.getStatusUpdateTime());
		newValue.put(Key_address, user.getAddress());
		newValue.put(Key_lat, String.valueOf(user.latitude));
		newValue.put(Key_long, String.valueOf(user.longitude));
		newValue.put(Key_avatar, user.getImageUrl());
		if (user.isMale()){
			newValue.put(Key_gender, 0);
		}
		else {
			newValue.put(Key_gender, 1);
		}
		
		if (user.isFriend()){
			newValue.put(Key_IsFriend, 1);
		}
		else {
			newValue.put(Key_IsFriend, 0);
		}
		//End new
		return sqlDatabase.update(table,newValue, Key_User_Id+ "=?", new String[]{user.getUserId()})>0;
	}
	
	/** Update user based on ID 
	 * @param id
	 * @return true if succeed 
	 */
	public boolean updateUser(FriendObject user){
		String table = TableUserName;
		ContentValues newValue = new ContentValues();
		newValue.put(Key_User_Nick,user.getFriendNick());
		newValue.put(Key_UUID, user.uuid);
		newValue.put(Key_itell, user.getFriendiTell());
		
		newValue.put(Key_itell_time, user.getFriendStatTime());
		newValue.put(Key_address, user.getFriendAddress());
		newValue.put(Key_lat,String.valueOf( user.getFriendLat()));
		newValue.put(Key_long, String.valueOf(user.getFriendLong()));
		newValue.put(Key_avatar, user.getFriendAva());
		if (user.isMale()){
			newValue.put(Key_gender, 0);
		}
		else {
			newValue.put(Key_gender, 1);
		}
		if (user.isFriend()){
			newValue.put(Key_IsFriend, 1);
		}
		else {
			newValue.put(Key_IsFriend, 0);
		}
		//End new
		return sqlDatabase.update(table,newValue, Key_User_Id+ "=?", 
								  new String[]{String.valueOf(user.getFriendID())})>0;
	}
	/** Get number of rows in table
	 * @param select 0 user
	 * @return
	 */
	public long count(int select) {
		String table = TableUserName;
		if (select ==1){
			table = TableMessageName;
		}
	    return DatabaseUtils.queryNumEntries(sqlDatabase,table);
	}
	/** Get number rows in table
	 * 
	 * @param select 0 history
	 * @return
	 */
	public int countOfRows(int select){
		String table = TableUserName;
		if (select ==1){
			table = TableMessageName;
		}
	    String count = "SELECT count(*) FROM "+table;
	    Cursor mcursor = sqlDatabase.rawQuery(count, null);
	    mcursor.moveToFirst();
	    int icount = mcursor.getInt(0);
	    mcursor.close();
	    return icount;
	}
	
	/**Check rows exist into table based on c_id
	 * @param select 0 User
	 * 1 Message
	 * @param id
	 * @return
	 */
	public boolean exist(int select, String id){
		String table = TableUserName;
		if (select ==1){
			table = TableMessageName;
		}
		String count = "SELECT count(*) FROM "+table +" where "+Key_User_Id+" = ?";
		Cursor mcursor = sqlDatabase.rawQuery(count,new String[]{id});
		mcursor.moveToFirst(); 
		int icount = mcursor.getInt(0);
		mcursor.close();
		return icount>0;
	}
	
	/**Check rows exist into table based on c_id
	 * @param select 0 User
	 * 1 Message
	 * @param id
	 * @return
	 */
	public void deleteMess(String idReceiver, String idSender){
		String table = TableMessageName;
		String count = "SELECT count(*) FROM "+table +" where "+Key_Receiver_id+" = ? AND "+Key_Sender_id+" = ?";
		Cursor mcursor = sqlDatabase.rawQuery(count,new String[]{idReceiver,idSender});
		mcursor.moveToFirst(); 
		int icount = mcursor.getInt(0);
		mcursor.close();
		if (icount>MAX_MESSAGE){
			//delete message
			int min = getMinIDMessage(idReceiver,idSender);
			deleteRow(min,1);
		}
	}
	
	public void close() {
		dbHelper.close();
	}
}
			