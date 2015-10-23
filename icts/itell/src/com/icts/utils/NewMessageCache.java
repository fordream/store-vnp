package com.icts.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.icts.object.MessageObject;

public class NewMessageCache {
	
	private static int countInvite = 0;
	private static Map<String, Long> newInviteCache =new HashMap<String, Long>();
	/**
	 * String chua id
	 * Long timestamp
	 */
	private static Map<Long, MessageObject> timestampMessageCache =new HashMap<Long, MessageObject>();
	
	/**
	 * String chua id
	 * Long timestamp
	 */
	private static Map<String, List<MessageObject>> userMessageCache =new HashMap<String, List<MessageObject>>();
	
	/**
	 * Insert message into 2 cache
	 * @param message
	 * @param receiverID
	 */
	public static void addMessageToCache(MessageObject message,String receiverID) {
		String receiver = message.getReceiverID();
		String sender = message.getSenderID();
        if ((message == null)||
        	(receiver==null)||
        	(sender==null)) {
            return;
        }
        
        if (receiver.equalsIgnoreCase(receiverID)){
        	List<MessageObject> list = userMessageCache.get(sender);
        	if (list==null){
        		list = new ArrayList<MessageObject>();
        		list.add(message);
        		userMessageCache.put(sender, list);
        	}
        	else{
        		list.add(message);
        		userMessageCache.remove(sender);
        		userMessageCache.put(sender, list);
        	}
        	
        	if (timestampMessageCache.get(message.getTimestamp())==null){
        		timestampMessageCache.put(message.getTimestamp(), message);
        	}
        }
	}
	
	/**
	 * Insert message into 2 cache
	 * @param message
	 * @param receiverID
	 */
	public static void addMessageToCache(MessageObject message,String receiverID,
										 Map<String, List<MessageObject>> other) {
		String receiver = message.getReceiverID();
		String sender = message.getSenderID();
        if ((message == null)||
        	(receiver==null)||
        	(sender==null)) {
            return;
        }
        
        if (receiver.equalsIgnoreCase(receiverID)){
        	
        	List<MessageObject> list1 = other.get(sender);
        	if (list1==null){
        		list1 = new ArrayList<MessageObject>();
        		list1.add(message);
        		other.put(sender, list1);
        	}
        	else{
        		list1.add(message);
        		other.remove(sender);
        		other.put(sender, list1);
        	}
        }
	}
	
	/**
	 * Clear all data
	 */
	public static void clearCache(){
		if (userMessageCache!=null){
			userMessageCache.clear();
		}
		
		if (timestampMessageCache!=null){
			timestampMessageCache.clear();
		}
	}

	/**
	 * Get Arraylist using sender for chat list screen
	 * @param sender
	 * @return
	 */
	public static ArrayList<MessageObject> getMessage(String sender){
		if (sender==null||userMessageCache==null)
			return null;
		return (ArrayList<MessageObject>) userMessageCache.get(sender);
	}
	
	
	/**
	 * Remove user in cache using id
	 * @param sender
	 * @return
	 */
	public static void deleteUser(String id){
		if (userMessageCache==null||id==null)
			return;
		if (userMessageCache.containsKey(id)){
			List<MessageObject> messArr = userMessageCache.get(id);
			if (messArr!=null){
				for (MessageObject messageObject : messArr) {
					if (messageObject.getSenderID().equals(id)){
						long timestamp = messageObject.getTimestamp();
						if (timestampMessageCache!=null){
							timestampMessageCache.remove(timestamp);
						}
					}
				}
			}
			userMessageCache.remove(id);
		}
	}
	
	/**
	 * {"type":"invite","receiver":"233743","count":1,"user_id":"13",
	 * "friend_id":"233743","timestamp":1349451639}
	 * @param json
	 * @param userID
	 */
	public static void add2InviteCache(JSONObject json,String userID){
		String receiverID = json.optString("receiver");
		String sendID = json.optString("user_id");
		String type = json.optString("type");
		int count = json.optInt("count");
		long timestamp = json.optLong("timestamp");
		
		
		if (type.equalsIgnoreCase(Constant.INVITE)){
			if (sendID==null){
				countInvite = count;
				return;
			}
			if (receiverID.equalsIgnoreCase(userID)){
				if (newInviteCache.containsKey(sendID)){
					newInviteCache.remove(sendID);
					newInviteCache.put(sendID, timestamp);
				}
				else {
					newInviteCache.put(sendID, timestamp);
				}
			}
		}
	}
		
	/**
	 * get number of invite from variable countInvite
	 * @return
	 */
	public static int getInvite(){
		return countInvite;
	}
	
	public static void clearInviteCount(){
		countInvite = 0;
		if (newInviteCache!=null){
			newInviteCache.clear();
		}
	}
	
	public static int getCountInvite(){
		if (newInviteCache!=null){
			return newInviteCache.size();
		}
		return 0;
	}
	/**
	 * Remove message from 2 cache
	 * @param message
	 */
	public static void removeMessage(MessageObject message){
		if (message==null){
			return;
		}
		long timestamp = message.getTimestamp();
		if (timestampMessageCache!=null){
			timestampMessageCache.remove(timestamp);
		}
		
		if (userMessageCache==null){
			return;
		}
		String sender = message.getSenderID();
		if (sender==null){
			return;
		}
		if (userMessageCache.containsKey(sender)){
			ArrayList<MessageObject> list =(ArrayList<MessageObject>) userMessageCache.get(sender);
			list.remove(message);
			userMessageCache.remove(sender);
			userMessageCache.put(sender, list);
		}
		
	}
	/**
	 * Get number of new messages of user 
	 * @return number
	 */
	public static int getCountNewMessage(){
		if (timestampMessageCache == null){
			return 0;
		}
		return timestampMessageCache.size();
	}
	
	/**
	 * Get number of new messages of user 
	 * @return number
	 */
	public static int getCountNewMessage(String id){
		if (timestampMessageCache == null){
			return 0;
		}
		int count = timestampMessageCache.size();
		int userCount = 0;
		if (userMessageCache!=null){
			if (userMessageCache.containsKey(id)){
				 List<MessageObject> list = userMessageCache.get(id);
				 userCount = list.size();
			}
		}
		count = count - userCount;
		return count<0?0:count;
	}
	
	/**
	 * Get number of new messages of user 
	 * @return number
	 */
	public static int getCountUSer(){
		if (userMessageCache == null){
			return 0;
		}
		return userMessageCache.size();
	}
	
	public static Map<String, List<MessageObject>> getUsers(){
		return userMessageCache;
	}
}
