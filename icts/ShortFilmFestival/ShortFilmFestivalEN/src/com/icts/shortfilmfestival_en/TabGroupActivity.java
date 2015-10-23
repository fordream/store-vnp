package com.icts.shortfilmfestival_en;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.vnp.shortfilmfestival.R;


public class TabGroupActivity extends ActivityGroup {

    /** Contain list activity. */
    private ArrayList<String> mIdList;

    /** Contain tag log name. */
    private static final String TAG = TabGroupActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mIdList == null) {
            mIdList = new ArrayList<String>();
        }
    }

    /**
     * This is called when a child activity of this one calls its finish method.
     * This implementation calls {@link LocalActivityManager#destroyActivity} on
     * the child activity and starts the previous activity. If the last child
     * activity just called finish(),this activity (the parent), calls finish to
     * finish the entire group.
     */

    @Override
    public void finishFromChild(Activity child) {
        LocalActivityManager manager = getLocalActivityManager();
        int index = mIdList.size() - 1;

        // When activity stack is empty, if current is first tab then close
        // application.
       
    }

    /**
     * Starts an Activity as a child Activity to this.
     * 
     * @param id
     *            Unique identifier of the activity to be started.
     * @param intent
     *            The Intent describing the activity to be started.
     * @throws android.content.ActivityNotFoundException.
     */
    public final void startChildActivity(final String id, final Intent intent) {
        Window window = getLocalActivityManager().startActivity(id,
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        if (window != null) {
            mIdList.add(id);
            setContentView(window.getDecorView());
        }
    }

    /**
     * If a Child Activity handles KeyEvent.KEYCODE_BACK. Simply override and
     * add this method.
     */
    @Override
    public void onBackPressed() {
       
        closeCurrentActivity();
    }

    /**
     * Close current activity on the first of Activity Stack.
     * */
    final void closeCurrentActivity() {
     
    }
}
