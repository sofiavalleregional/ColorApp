package com.worldskills.colorapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.worldskills.colorapp.activities.Home;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iniciarHome();
            }
        },2000);
    }

    /*Metodo que se encarga de iniciar la actividad home*/
    public void iniciarHome(){
        Intent intent=new Intent(this,Home.class);
        startActivity(intent);
        finish();
    }
}

