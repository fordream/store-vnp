/***********************************************************************
 * Module:  SyncStatusListener.java
 * Author:  Administrator
 * Purpose: Defines the Interface SyncStatusListener
 ***********************************************************************/

package vn.com.vega.music.syncmanager;

public interface SyncStatusListener {

	void onSyncStart();

	void onSyncDone();

}