package org.com.vnp.chickenbang;

import org.com.cnc.common.adnroid.activity.CommonActivity;
import org.com.vnp.chickenbang.views.DrawView;

import android.os.Bundle;

public class ChickenBangActivity extends CommonActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }
}