package com.example.java.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Game extends Activity {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Recoger el intent
        user = getIntent().getStringExtra("user");
        int ficha = getIntent().getIntExtra("ficha", 1);
        int jugadores = getIntent().getIntExtra("jugadores", 2);
        Toast.makeText(this, "Soy el usuario "+user+" con el valor de ficha "+ficha+" y un máximo de "+jugadores+" jugadores", Toast.LENGTH_LONG).show();
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
    }

    public static int TiraDado(){
        int max = 6;
        int roll = (int) (Math.random() * max) + 1;
        return roll;
    }
}
