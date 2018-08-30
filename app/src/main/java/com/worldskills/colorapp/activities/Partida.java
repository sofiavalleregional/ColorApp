package com.worldskills.colorapp.activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.worldskills.colorapp.R;

import java.util.Random;

public class Partida extends AppCompatActivity {

    private static int RECURSOS[]= {R.drawable.anim_amarillo, R.drawable.anim_azul, R.drawable.anim_rojo, R.drawable.anim_verde};
    private static String COLORES[]= {"AMARILLO", "AZUL", "ROJO", "VERDE"};
    private int bien, total, intentos, pausas, modo, posicionP, numeros[];
    private boolean tipoP;
    private long timerPartida, timerPalabra, tiemporeal;

    CountDownTimer countPalabra, countPartida;
    TextView palabras, correcto, modojuego, palabraMostrar;
    Button b1, b2, b3, b4;
    Dialog gameover, detener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);


        init();

        intentos=3;
        timerPalabra=3000;
        total=0;
        bien=0;
        pausas=0;
        tipoP=true;

        Bundle bundle= getIntent().getExtras();

        if(bundle!=null)modo= bundle.getInt(Home.MODO_PARTIDA);
        else modo=1;
    }

   public void init() {
        palabras= findViewById(R.id.partida_t1);
        modojuego= findViewById(R.id.partida_t2);
        correcto= findViewById(R.id.partida_t3);
        palabraMostrar = findViewById(R.id.palabra);

        b1= findViewById(R.id.b1);
        b2= findViewById(R.id.b2);
        b3= findViewById(R.id.b3);
        b4= findViewById(R.id.b4);

        gameover= new Dialog(this);

    }



    @Override
   protected void onResume() {
        super.onResume();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);

        if(modo==1){
            if(preferences.getString(getResources().getString(R.string.key_modo_partida), "INTENTOS").equalsIgnoreCase("TIEMPO")){
               timerPartida= Long.parseLong(preferences.getString(getResources().getString(R.string.key_tiempo_partida), "30000"));
               tiempoPartida(timerPartida);
                tipoP=false;
            } else {
                intentos=Integer.parseInt(preferences.getString(getResources().getString(R.string.key_numero_intentos),"3"));
            }

            timerPalabra= Long.parseLong(preferences.getString(getResources().getString(R.string.key_duracion_palabra), "3000"));
        }

      juego();
    }


    public void juego(){
        total++;
        mostrarBotones();
        asignarColor();
        palabraMostrar.setText(COLORES[azar()]);

        tiempoPalabra(timerPalabra);

        palabras.setText(""+total);
        if(tipoP)modojuego.setText(""+intentos);
        correcto.setText(""+ bien);
    }

    public void comparar(int position){
        try {
            countPalabra.cancel();
        } catch (Exception e){}

        if(position==posicionP)bien++;
        else intentos--;


        if(tipoP) {
            if (intentos == 0) finalizarPartida();
        }

        juego();
    }


    public int azar(){
        Random random= new Random();
        int num = random.nextInt(COLORES.length);

        return num;
    }

    int colores [] = {R.color.amarillo, R.color.azul, R.color.rojo, R.color.verde};
    public void mostrarBotones(){

       numeros= new int[4];
        for (int i=0; i<COLORES.length; i++ )numeros[i]=-1;


        int conta=0;
        do {
            int numazar= azar();
            if(numeros[numazar]==-1){
                numeros[numazar]= conta;
                conta++;
            }

        }while (conta<4);

        b1.setBackgroundResource(colores[0]);
        b2.setBackgroundResource(colores[1]);
        b3.setBackgroundResource(colores[2]);
        b4.setBackgroundResource(colores[3]);

        onClickbotones();
    }


    public void onClickbotones(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comparar(numeros[0]);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               comparar(numeros[1]);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               comparar(numeros[2]);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comparar(numeros[3]);
            }
        });
    }

    @TargetApi(23)
    public void asignarColor(){
        posicionP= azar();

        switch (posicionP){
            case 0:
                palabraMostrar.setTextColor(getResources().getColor(R.color.amarillo,null));
                break;
            case 1:
                palabraMostrar.setTextColor(getResources().getColor(R.color.azul,null));
                break;
            case  2:
                palabraMostrar.setTextColor(getResources().getColor(R.color.rojo,null));
                break;
            case 3:
                palabraMostrar.setTextColor(getResources().getColor(R.color.verde,null));
                break;
        }
    }



    ////////// TIEMPOS MANEJADOS /////////////////////

    public void tiempoPalabra(Long palabra){
        countPalabra= new CountDownTimer(palabra, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                intentos--;
                if(tipoP) {
                    if(intentos==0) finalizarPartida();
                }

                juego();
            }
        }.start();
    }

    public void tiempoPartida(Long partida){
        countPartida = new CountDownTimer(partida, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                modojuego.setText(""+(millisUntilFinished/1000));
                tiemporeal= millisUntilFinished;
            }

            @Override
            public void onFinish() {
                finalizarPartida();
            }
        }.start();
    }


    public void pausarJuego(View v){
        if(pausas<2){
            detener.show();
            try {
                countPartida.cancel();
            }catch (Exception e){}

            try {
                countPalabra.cancel();
            } catch (Exception e){}

            Button reanudar = detener.findViewById(R.id.dialog_pausa_boton);

            reanudar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tipoP){
                        tiempoPartida(tiemporeal);
                    }
                 detener.dismiss();
                }
            });

            pausas++;
        } else Toast.makeText(this, "SOLO SON DOS PAUSAS", Toast.LENGTH_SHORT).show();
        juego();
    }



    private void finalizarPartida() {

    }


}
