package com.icts.shortfilmfestivalJa;


import com.icts.shortfilmfestivalJa.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class Main extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d("LOG_MAIN","COME__HERE");
    }
}
