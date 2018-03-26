package com.example.java.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

public class Game extends Activity{

    private static String player, moneda, idpartida;
    private int jugadores, conversor, dinero_base=750;
    private boolean close = false;
    private ImageView btn_dado;
    private Button btn_propiedades;
    private TextView tv_dinero, tv_turno;
    private JSONParser jsp;
    private Dialog myDialog;
    private ImageView gif_dados;
    private AnimationDrawable animatirada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //inicializar el json
        jsp = new JSONParser();
        //Recoger el intent
        player = getIntent().getStringExtra("user");
        int ficha = getIntent().getIntExtra("ficha", 1);
        String city = getIntent().getStringExtra("city");
        jugadores = getIntent().getIntExtra("jugadores", 2);
        idpartida = String.valueOf(getIntent().getIntExtra("idpartida", -1));
        if (idpartida.equals("-1")){
            //isset($_GET['ciudad']) && isset($_GET['n_jugadores']) && isset($_GET['jugador']) && isset($_GET['ficha'])
            try {
                jsp.registroSala(city, String.valueOf(jugadores), player, String.valueOf(ficha));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            InsertarJugador(player, ficha, idpartida); //Introducimos el jugador en la partida
        }
        //Sacar el factor de conversion
        conversor = Conversor(city);
        dinero_base = dinero_base*conversor;
        gif_dados = (ImageView) findViewById(R.id.loadingView);
        gif_dados.setBackgroundResource(R.drawable.dados);
        gif_dados.setVisibility(View.INVISIBLE);
        AnimationDrawable animagif = (AnimationDrawable) gif_dados.getBackground();
        animagif.start();
        btn_dado = (ImageView) findViewById(R.id.btn_dados);
        btn_dado.setBackgroundResource(R.drawable.tirada);
        animatirada = (AnimationDrawable) btn_dado.getBackground();
        animatirada.start();
        btn_dado.setOnClickListener(new View.OnClickListener() { //listener del boton del dado
            @Override
            public void onClick(View view) {
                animatirada.stop(); //para que no enseñe los dados
                btn_dado.setBackgroundResource(R.color.grey);
                btn_dado.setVisibility(View.INVISIBLE);//desactiva el dado para que no vuelva a tirar
                gif_dados.setVisibility(View.VISIBLE);

                ThreadGif();
                //TODO mostrar tirada en gif_dados
            }
        });
        btn_propiedades = (Button) findViewById(R.id.btn_propiedades);
        tv_dinero = (TextView) findViewById(R.id.tv_dinero);
        tv_turno = (TextView) findViewById(R.id.tv_turno);
        tv_dinero.setTextColor(getResources().getColor(R.color.black));
        tv_turno.setTextColor(getResources().getColor(R.color.black));
        //dialogo de propiedades
        myDialog = new Dialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ThreadWaitingUsers();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onBackPressed() {
        //Opción de salir del juego
        //moveTaskToBack(true); //EL CHAPUCERO
        final AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder.setMessage("Are you sure want to do this?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private int Conversor(String city) {
        int c;
        if (city.toLowerCase().equals(getString(R.string.SAO).toLowerCase())){
            c = 4;
            moneda="R$";
        }else if (city.toLowerCase().equals(getString(R.string.WAS).toString().toLowerCase())) {
            c = 3;
            moneda="$";
        }else if (city.toLowerCase().equals(getString(R.string.ZAR).toString().toLowerCase())) {
            c = 2;
            moneda="€";
        }else {
            c = 1;
            moneda="£";
        }
        return c;
    }

    public static int TiraDado(){
        int max = 6;
        int roll = (int) (Math.random() * max) + 1;
        return roll;
    }

    public void IrCarcel(String player){
        int position = 11; //carcel
        //TODO llamar al json de carcel (contador_carcel == 3)...
        CambiaTurno();
    }

    public void InsertarJugador(String player, int ficha, String idpartida){
        try {
            jsp.entrarSala(idpartida,player,String.valueOf(ficha));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*public void Partida(String user){
        dineros = new int[jugadores];
        posiciones = new int[jugadores];
        players = new int[jugadores];
        for (int i = 0; i < jugadores; i++) {
            dineros[i] = dinero_base;
            players[i] = 0;
            posiciones[i] = 1;
        }
        //TODO quitar accesible de partida
        OrdenTurno();
    }*/

    /*public void OrdenTurno(){
        int[] idjugadores = new int[jugadores]; //TODO sacar el ID de la BBDD
        int[] orden = new int[jugadores];
        int[] turno = new int[jugadores];
        Random random = new Random();
        for(int i = 0; i < jugadores; i++){ //inicializar un índice de orden (1,2,3,4,5...)
            orden[i] = i+1;
        }
        int ran;
        int n = jugadores;
        for(int i = 0; i < jugadores; i++){
            ran = random.nextInt(n);
            turno[i] = orden[ran];
            orden[ran] = orden[n-1]; //sobreescribe el que acaba de salir
            n--;
        }
    }*/


    public void MueveFicha(String player, int posicion, int tirada){
        int dinero = 0;//TODO select dinero from partida_jugadores
        posicion += tirada;
        if (posicion>40){ //si pasas por la casilla de salida
            posicion-=40;
            dinero += 200;
            //TODO UPDATE DINERO
        }
        //TODO UPDATE POSICION
    }

    public void Turno() {
        if(JugadoresJugando(idpartida)>1){
            btn_dado.setEnabled(true);
            tv_turno.setText(getString(R.string.turn));
            int posicion = 0; //TODO SELECT FROM partida_jugadores
            int d1 = TiraDado();
            int d2 = TiraDado();
            int tirada = d1 + d2;
            int dobles = 0; //TODO SELECT FROM partida_jugadores
            try {
                dobles = jsp.comprobarDoblesJugador(idpartida, player);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int contador_carcel = 0; //TODO SELECT contador_carcel FROM partidas_jugadores
            if (d1 == d2) {
                dobles++;
                if (contador_carcel > 0) {
                    contador_carcel = 0;
                    dobles = 0;
                    MueveFicha(player, posicion, tirada);
                }
                if (dobles == 3) {
                    IrCarcel(player);
                }
            } else {
                if (dobles != 0) { //si lleva dobles y no ha sacado dobles
                    dobles = 0;
                }
                if (contador_carcel > 0) { //para cuando esta en la carcel
                    contador_carcel--;
                } else {
                    MueveFicha(player, posicion, tirada);
                    //TODO UPDATE dobles, posicion, dinero, contador_carcel
                }
            }
            String color = ""; //TODO sacar el color de la casilla
            if (!color.equals("")) {
                boolean disponible = true; //TODO sacar si la casilla es una propiedad disponible
                if (disponible) {
                    int precio = 0; //TODO sacar precio
                    int dinero = 0; //TODO sacar dinero del jugador from partida_jugadores
                    if (dinero > precio) {
                        Comprar(player, posicion);
                    }
                } else {
                    Pagar(posicion);
                }
            } else {
                Evento(posicion);
            }
            CambiaTurno();
        }else{
            Intent i = new Intent(Game.this, FinPartida.class);
            i.putExtra("win", true);
            startActivity(i);
        }
    }

    public void CambiaTurno(){
        /*int turno = 0; //TODO select turno from partida;
        turno++;
        if (turno>JugadoresJugando(idpartida)){ //cuando sea el turno del último vuelve al primero
            turno = 1;
        }
        int dinero = 0; //TODO select dinero from partida_jugadores where turno=turno
        while (dinero==0){ //cuando el jugador haya perdido que se salte su turno
            turno++;
            dinero = 0; //TODO select dinero from partida_jugadores where turno=turno
        }*/
        boolean ok = false;
        try {
            if (jsp.actualizarTurno(idpartida, String.valueOf(jugadores))){
                ok = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (ok){
            ThreadWaitingChanges();
            ThreadWaitingTurn();
            tv_turno.setText(getString(R.string.noturn));
            btn_dado.setEnabled(false);
        }
    }

    public void Pagar(int posicion){
        int pagar = 0; //TODO sacar lo que hay que pagar
        int dinero_user = 0; //TODO sacar lo que tiene el jugador
        dinero_user -= pagar; //restamos lo que se paga
        String propietario = ""; //TODO sacar propietario de propiedad
        int dinero_propietario = 0; //TODO sacar lo que tiene el propietario
        dinero_propietario += pagar; //sumamos lo que se paga
        if (dinero_user<1){
            Pierde(player);
        }
        //TODO update propietario
        //TODO update jugardor
    }

    public void Evento(int posicion){
        //TODO lo que sea, sacar carta, etc
        switch (posicion){
            case 31: IrCarcel(player);
                break;
            case 3:
            case 18:
            case 34:
                Carta(1);
                break;
            case 8:
            case 23:
            case 37:
                Carta( 0);
                break;
            case 5:
            case 39:
                Impuesto(player, posicion);
                break;
            default:
                break;
        }
    }

    public void Carta(int tipo){ //tipo 0: suerte; tipo 1: comunidad
        int suerte = 12;
        int comunidad = 12;
        Random random = new Random();
        int carta;
        boolean disponible = false;
        while (disponible){
            disponible = true;  //TODO sacar carta con id de la variable carta
        }
        if (tipo==0){
            int n_cartas = ContarDisponible("Suerte");
            carta = random.nextInt(suerte)+1; //1-12
        }else{
            int n_cartas = ContarDisponible("Comunidad");
            carta = random.nextInt(comunidad)+13; //13-24
        }
        String text =  "";  //TODO sacar carta con id de la variable carta
        int precio = 0;//TODO sacar carta con id de la variable carta
        if (precio==0){
            Evento(tipo);
        }
        //TODO UPDATE carta a no disponible
    }

    public void Comprar(String player, int posicion) {
        int precio = 0; //TODO: valor de la casilla position
        int dinero = 0; //TODO: dinero del jugador player
        //TODO INSERT EN PROPIEDADES_JUGADORES WHERE JUGADOR = PLAYER AND PARTIDA = IDPARTIDA
    }


    public void Impuesto(String player, int posicion) {
        int precio = 0; //TODO: valor de la casilla position
        int dinero = 0; //TODO: dinero del jugador player
        dinero -= precio;
        if (dinero<1){
            Pierde(player);
        }
        //TODO UPDATE PRECIO JUGADOR = PLAYER AND PARTIDA = IDPARTIDA
    }

    public int ContarDisponible(String tipo) {
        int n = 0; //TODO sacar cartas disponibles de un tipo
        return n;
    }

    public void Pierde(String player){
        int turno_perdedor = 0;//TODO select turno from partida_jugadores
        //TODO update partida_jugadores turno = 0
        //TODO update partida_jugadores de los jugadores con turno > turno_perdedor con turno--
        //TODO graficos, quitar fichita y eso
        jugadores--;
        Intent i = new Intent(Game.this, FinPartida.class);
        i.putExtra("win", false);
        startActivity(i);
    }

    public int JugadoresJugando(String idpartida){
        ArrayList<ArrayList<String>> partida = new ArrayList<>();
        try {
            partida = jsp.ComprobarDineroJugadoresSala(idpartida);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int n = 0;
        if (partida!=null){
            System.out.println("longitud "+partida.size());
            for (int i = 0; i < partida.size(); i++) {
                int dinero = Integer.parseInt(partida.get(i).get(2).toString());
                if (dinero>0){
                    n++;
                }
            }
        }
        return n;
    }

    public void LongToast(final String string){
        runOnUiThread(new Runnable() {
            public void run(){
                Toast.makeText(Game.this, string, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.activity_popup);
        txtclose =(TextView) myDialog.findViewById(R.id.xcerrar);
        btnFollow = (Button) myDialog.findViewById(R.id.buybutton);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void ThreadWaitingUsers() { //hilo esperar a que entren los jugadores
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                JSONParser jsp = new JSONParser();
                int jugadores_actuales = 0;
                try {
                    jugadores_actuales = (jsp.comprobarJugadoresSala(idpartida.toString())).size();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ; //TODO sacar jugadores from partida
                //Espera hasta que los jugadores llenen la sala
                while (jugadores_actuales < jugadores) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    jugadores_actuales++; //TODO sacar jugadores from partida
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Coloca los botones y cambia el texto por el dinero
                        btn_dado.setEnabled(true);
                        btn_dado.setVisibility(View.VISIBLE);
                        btn_propiedades.setVisibility(View.VISIBLE);
                        tv_dinero.setText(dinero_base+moneda);
                        tv_turno.setVisibility(View.VISIBLE);
                        ThreadWaitingChanges();
                        ThreadWaitingTurn();
                        btn_propiedades.setEnabled(true);
                    }
                });
            }
        };
        Thread esperajugadores = new Thread(runnable);
        esperajugadores.start();
    }

    private void ThreadWaitingChanges() { //hilo espera que cambie el turno para repintar el tablero
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                int turno = 0; //TODO sacar jugadores from partida
                int turno_actual = turno;
                //Espera hasta que cambie el turno
                while (turno == turno_actual) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    turno_actual = 0; //TODO sacar turno from partida
                }
                turno = turno_actual; //ponemos el turno con el actual
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO actualizar los gráficos
                    }
                });

            }
        };
        Thread cambioturno = new Thread(runnable);
        cambioturno.start();
    }

    private void ThreadWaitingTurn() { //hilo espera que cambie el turno para repintar el tablero
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                //Espera a que sea tu turno
                boolean turno = false;
                try {
                    turno = jsp.EsmiTurno(idpartida,player);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                while (!turno) {
                    try {
                        Thread.sleep(4000);
                        turno = jsp.EsmiTurno(idpartida,player);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_dado.setBackgroundResource(R.drawable.dados);
                        animatirada = (AnimationDrawable) btn_dado.getBackground();
                        animatirada.start();
                        Turno();
                    }
                });

            }
        };
        Thread tuturno = new Thread(runnable);
        tuturno.start();
    }

    private void ThreadGif() { //hilo mostrar gif
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Oculta el gif
                        gif_dados.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
        Thread gif = new Thread(runnable);
        gif.start();
    }
}