package com.worldskills.colorapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.worldskills.colorapp.R;
import com.worldskills.colorapp.fragments.Pref;

public class Settings extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().replace(R.id.contenedor_prefenencias,new Pref()).commit();
    }

    public void startActivity(Intent intent){

        intent.putExtra(Home.MODO_PARTIDA,1);
        startActivity(intent);
        finish();
    }
}
