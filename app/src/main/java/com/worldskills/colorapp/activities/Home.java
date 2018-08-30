package com.worldskills.colorapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.worldskills.colorapp.R;
import com.worldskills.colorapp.db.DataBase;

public class Home extends AppCompatActivity {

    public static final String MODO_PARTIDA="MODOP";
    private int[] puntajesP=new int[4];
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
                abrePuntajes();
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
    /*Metodo para abrir el dialog de puntajes mostrando extraidad de la bas e de datos*/
    public void abrePuntajes(){
        Dialog dialogPuntajes=new Dialog(this);
        dialogPuntajes.setContentView(R.layout.dialog_puntajes);
        dialogPuntajes.setCanceledOnTouchOutside(false);
        dialogPuntajes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView[] puntajes=new TextView[4];
        puntajes[0]=dialogPuntajes.findViewById(R.id.pun1);
        puntajes[1]=dialogPuntajes.findViewById(R.id.pun2);
        puntajes[2]=dialogPuntajes.findViewById(R.id.pun3);
        puntajes[3]=dialogPuntajes.findViewById(R.id.pun4);


        for (int i=0;i<puntajesP.length; i++)puntajesP[0]=0;
        cargarDatosPuntajes();
        for (int i=0;i<puntajes.length;i++)puntajes[i].setText(puntajesP[i]+"");


        dialogPuntajes.show();
    }

    /*Metodo para cargar los puntajes de la base de datos*/
    public void cargarDatosPuntajes(){
        DataBase db=new DataBase(this);
        Cursor cursor=db.load();
        if (cursor==null)return;

        if (cursor.moveToFirst()){
            int i=0;
            do{
                puntajesP[i]=cursor.getInt(0);
                i++;
            }while (cursor.moveToNext());
        }
    }
}
