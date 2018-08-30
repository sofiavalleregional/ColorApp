package com.worldskills.colorapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.worldskills.colorapp.R;

public class Home extends AppCompatActivity {

    public static final String MODO_PARTIDA="MODOP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    /*Metodo con el fin de obtener el boton precionado en el inicio y de igual manera realizar su
    * respeciva accion*/
    public void botonesInicio(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.inicio_bton_jugar:
                intent=new Intent(this, Partida.class);
                intent.putExtra(MODO_PARTIDA,0);
                startActivity(intent);
                break;
            case R.id.inicio_bton_puntaje:
                break;
            case R.id.inicio_bton_ajustes:
                intent=new Intent(this, Settings.class);
                startActivity(intent);
                break;
            case R.id.inicio_bton_salir:
                finish();
                break;

        }
    }
}
