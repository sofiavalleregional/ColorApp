package com.worldskills.colorapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.worldskills.colorapp.R;

public class Home extends AppCompatActivity {

    public static final String MODO_PARTIDA="MODOP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
