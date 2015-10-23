package vn.com.vega.music.objects;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {

	public int friend_id;
	public String friend_name;
	public String friend_info;
	public String friend_avatar;
	public int friend_follow_me;
	public ArrayList<Playlist> friend_playlist;
	
	// chacha
	public String avatar;
	
	public Friend(){}

	public Friend(Parcel source) {
		// TODO Auto-generated constructor stub
		friend_id = source.readInt();
		friend_name = source.readString();
		friend_info = source.readString();
		friend_avatar = source.readString();
		friend_follow_me = source.readInt();
		friend_playlist = source.readArrayList(Playlist.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(friend_id);
		dest.writeString(friend_name);
		dest.writeString(friend_info);
		dest.writeString(friend_avatar);
		dest.writeInt(friend_follow_me);
		dest.writeList(friend_playlist);
	}
	
public static final Parcelable.Creator<Friend> CREATOR = new Creator<Friend>() {
		
		@Override
		public Friend[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Friend[size];
		}
		
		@Override
		public Friend createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Friend(source);
		}
	};
}
