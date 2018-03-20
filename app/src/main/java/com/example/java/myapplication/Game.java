package com.example.java.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

public class Game extends Activity {

    static int jugadores, idpartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Recoger el intent
        String player = getIntent().getStringExtra("user");
        int ficha = getIntent().getIntExtra("ficha", 1);
        String city = getIntent().getStringExtra("city");
        jugadores = getIntent().getIntExtra("jugadores", 2);
        idpartida = getIntent().getIntExtra("idpartida", -1);
        if (idpartida==-1){
            //TODO crear insert de partida
            idpartida = 0; //TODO sacar idpartida
        }
        InsertarJugador(player, ficha, idpartida);
        int jugadores_actuales = 0; //TODO sacar jugadores from partida
        while(jugadores_actuales<jugadores){
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jugadores_actuales  = 0;//TODO sacar jugadores from partida
        }
        Partida(player);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    @Override
    public void onBackPressed() {
        //Opción de salir del juego
        //TODO: CERRAR + ONSLEEP
    }

    public static int TiraDado(){
        int max = 6;
        int roll = (int) (Math.random() * max) + 1;
        return roll;
    }

    public static void IrCarcel(String player){
        int position = 11; //carcel
        CambiaTurno();
    }

    public static void InsertarJugador(String player, int ficha, int idpartida){
        //TODO insertar en partida_jugadores
    }

    public static void Partida(String user){
        //TODO quitar accesible de partida
        OrdenTurno();
        Turno(user);

    }

    public static void OrdenTurno(){
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
        for (int i = 0; i < jugadores; i++) {
            //TODO UPDATE turno SET('turno[i]') FROM partida_jugadores WHERE id=='idjugadores[i]'
        }
    }

    public static void CambiaTurno(){
        int turno = 0; //TODO select turno from partida;
        turno++;
        if (turno>jugadores){ //cuando sea el turno del último vuelve al primero
            turno = 1;
        }
        int dinero = 0; //TODO select dinero from partida_jugadores where turno=turno
        while (dinero==0){
            turno++;
            dinero = 0; //TODO select dinero from partida_jugadores where turno=turno
        }
        //TODO INSERT turno IN partida
    }

    public static void Turno(String player){
        int posicion = 0; //TODO SELECT FROM partida_jugadores
        int d1 =  TiraDado();
        int d2 =  TiraDado();
        int dobles = 0; //TODO SELECT FROM partida_jugadores
        if (d1 == d2){
            dobles++;
            if (dobles == 3){
                IrCarcel(player);
            }
        }else{
            int tirada = d1+d2;
            int dinero = 0;//TODO select dinero from partida_jugadores
            posicion += tirada;
            if (posicion>40){ //si pasas por la casilla de salida
                posicion-=40;
                dinero += 200;
            }
            if (dobles!=0){ //si lleva dobles y no ha sacado dobles
                dobles = 0;
            }
            //TODO UPDATE dobles, posicion, dinero
        }
        String color = ""; //TODO sacar el color de la casilla
        if (!color.equals("")){
            boolean disponible = true; //TODO sacar si la casilla es una propiedad disponible
            if (disponible){
                int precio = 0; //TODO sacar precio
                int dinero = 0; //TODO sacar dinero del jugador from partida_jugadores
                if(dinero>precio){
                    Comprar(player, posicion);
                }
            }else{
                Pagar(player, posicion);
            }
        }else{
            Evento(player, posicion);
        }
        CambiaTurno();
    }

    public static void Pagar(String player, int posicion){
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

    public static void Evento(String player, int posicion){
        //TODO lo que sea, sacar carta, etc
        switch (posicion){
            case 31: IrCarcel(player);
                break;
            case 3:
            case 18:
            case 34:
                Carta(player, 1);
                break;
            case 8:
            case 23:
            case 37:
                Carta(player, 0);
                break;
            case 5:
            case 39:
                Impuesto(player, posicion);
                break;
            default:
                break;
        }
    }

    public static void Carta(String player, int tipo){ //tipo 0: suerte; tipo 1: comunidad
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
            Evento(player, tipo);
        }
        //TODO UPDATE carta a no disponible
    }

    public static void Comprar(String player, int posicion) {
        int precio = 0; //TODO: valor de la casilla position
        int dinero = 0; //TODO: dinero del jugador player
        //TODO INSERT EN PROPIEDADES_JUGADORES WHERE JUGADOR = PLAYER AND PARTIDA = IDPARTIDA
    }


    public static void Impuesto(String player, int posicion) {
        int precio = 0; //TODO: valor de la casilla position
        int dinero = 0; //TODO: dinero del jugador player
        dinero -= precio;
        if (dinero<1){
            Pierde(player);
        }
        //TODO UPDATE PRECIO JUGADOR = PLAYER AND PARTIDA = IDPARTIDA
    }

    public static int ContarDisponible(String tipo) {
        int n = 0; //TODO sacar cartas disponibles de un tipo
        return n;
    }

    public static void Pierde(String player){
        //TODO update partida_jugadores turno = 0
        //TODO graficos
        jugadores--;
    }

    public static int JugadoresJugando(int idpartida){
        int n = 0; //TODO count jugadores en la partida con dinero > 0
        return n;
    }
}
