package com.worldskills.colorapp.activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.worldskills.colorapp.R;

import java.util.Random;

public class Partida extends AppCompatActivity {

    private static int COLORES_BOTONES[]= {R.drawable.anim_amarillo, R.drawable.anim_azul, R.drawable.anim_rojo, R.drawable.anim_verde};
    private static String COLORES_PALABRAS[]= {"AMARILLO", "AZUL", "ROJO", "VERDE"};
    private int[] numeros;

    private TextView viewPalabras, viewCorrecto, viewModoJuego, viewPalabraMostrar;
    private Button[] botonesJuego;

    private Animation aparecer;

    private int correctas, palabras, intentos, pausas, modo, posicionP, colorTinta;
    private boolean tipoP;
    private long duracionPalabra, tiempoPartida, tiemporeal;

    CountDownTimer timerPalabra, timerPartida;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);



        duracionPalabra=3000;
        intentos=3;
        correctas=0;
        palabras=0;
        tipoP=true;

        Bundle datos= getIntent().getExtras();

        if(datos!=null){
            modo=datos.getInt(Home.MODO_PARTIDA);
        }
        findViews();
        loadAnimation();

    }
    /*Metodo para encontrar las vistas del layout*/
    public void findViews(){
        viewPalabras=findViewById(R.id.partida_t1);
        viewModoJuego=findViewById(R.id.partida_t2);
        viewCorrecto=findViewById(R.id.partida_t3);
        viewPalabraMostrar=findViewById(R.id.palabra);


        botonesJuego=new Button[4];
        botonesJuego[0]=findViewById(R.id.b1);
        botonesJuego[1]=findViewById(R.id.b2);
        botonesJuego[2]=findViewById(R.id.b3);
        botonesJuego[3]=findViewById(R.id.b4);

    }

    public void loadAnimation(){

    }
    /*Medoto para sacar una posicion al azar*/
    public int azar(){
        return new Random().nextInt(COLORES_BOTONES.length);
    }
    public void azarColorTinta(){
        colorTinta=azar();
        switch (colorTinta){
            case 0:
                viewPalabraMostrar.setTextColor(getResources().getColor(R.color.amarillo,null));

                break;
            case 1:
                viewPalabraMostrar.setTextColor(getResources().getColor(R.color.azul,null));
                break;
            case 2:
                viewPalabraMostrar.setTextColor(getResources().getColor(R.color.rojo,null));
                break;
            case 3:
                viewPalabraMostrar.setTextColor(getResources().getColor(R.color.verde,null));
                break;
        }
    }
    public void botonesAzar(){
        numeros=new int[4];
        for (int i=0; i<numeros.length;i++)numeros[i]=-1;

        int base=0;

        do{
            int posicion=azar();
            if (numeros[posicion]==-1){
                numeros[posicion]=base;
                base++;
            }

        }while (base<4);

        for (int i=0; i<botonesJuego.length; i++){
            botonesJuego[i].setBackgroundResource(COLORES_BOTONES[numeros[i]]);
        }

        clickBotones();


    }
    /*metodo que pone a la escucha a todos los botones de los colores*/
    public void clickBotones(){
        botonesJuego[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comparar(numeros[0]);
            }
        });

        botonesJuego[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comparar(numeros[1]);
            }
        });

        botonesJuego[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comparar(numeros[2]);
            }
        });

        botonesJuego[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comparar(numeros[3]);
            }
        });
    }


    /*metodo que recibe el boton precionado y compara si la accion es correcta*/
    public void comparar(int botonPresionado){
        if (botonPresionado==colorTinta){
            correctas++;
        }else{
            intentos--;
        }

        try{
            timerPalabra.cancel();
        }catch (Exception e){}

        if (tipoP){
            if (intentos==0)finalPartida();
            else cargaJuego();

        }
    }

    /*Metodo que se encarga de cargar el juego */
    public void cargaJuego(){
        azarColorTinta();
        viewPalabraMostrar.setText(COLORES_PALABRAS[azar()]);
        botonesAzar();
        timerPalabraD();

        palabras++;

        viewPalabras.setText(palabras+"");
        viewCorrecto.setText(correctas+"");

        if (tipoP)viewModoJuego.setText(intentos+"");
    }

    /*Metodo para terminar la partida luego de verificar*/
    public void finalPartida(){


        try{
            timerPalabra.cancel();
        }catch (Exception e){}

        try{
            timerPartida.cancel();
        }catch (Exception e){}



        Toast.makeText(this, "final partida", Toast.LENGTH_SHORT).show();


    }

    public void timerPalabraD(){
        timerPalabra=new CountDownTimer(duracionPalabra,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                comparar(10);//a
            }
        }.start();
    }
    /*metodo para iniciar la partida con el tiempo*/
    public void timerPartidaD(){
        timerPartida=new CountDownTimer(tiempoPartida,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tiemporeal=millisUntilFinished;

            }

            @Override
            public void onFinish() {
                finalPartida();
            }
        }.start();
    }
    public void onResume(){
        super.onResume();

        cargaJuego();
    }
    public void abreDialogFinal(){

    }





}
