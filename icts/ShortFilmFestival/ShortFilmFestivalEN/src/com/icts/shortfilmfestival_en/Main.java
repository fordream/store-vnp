package com.icts.shortfilmfestival_en;


import com.vnp.shortfilmfestival.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class Main extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //
        Log.d("LOG_MAIN","COME__HERE");
    }
}
