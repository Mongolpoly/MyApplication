package com.example.java.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class JoinGame extends Activity {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        user = getIntent().getStringExtra("user");
        String partida = getIntent().getStringExtra("partida");
        int jugadores = 5; //TODO:buscar en la BBDD los jugadores y mandarlo desde la anterior activity
        final Spinner spinner = findViewById(R.id.spinner_join);
        List<String> list = new ArrayList<String>();
        for (int i=1;i<=jugadores;i++){
            list.add(color(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button btn = (Button) findViewById(R.id.btn_join_game);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String color = spinner.getSelectedItem().toString();
                if (unirsePartida(fichaColor(color))){
                    Intent i = new Intent(JoinGame.this, Game.class);
                    i.putExtra("user", user);
                    i.putExtra("ficha", (fichaColor(color)));
                    startActivity(i);
                }else{
                    finish();
                    Toast.makeText(JoinGame.this, R.string.closed, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public String color(int c){
        switch (c){
            case 1: return getString(R.string.red);
            case 2: return getString(R.string.blue);
            case 3: return getString(R.string.yellow);
            case 4: return getString(R.string.purple);
            case 5: return getString(R.string.orange);
            case 6: return getString(R.string.green);
            case 7: return getString(R.string.white);
            case 8: return getString(R.string.black);
            default: return getString(R.string.red);
        }
    }

    public int fichaColor(String color){
        if (color.equals(getString(R.string.red))){
            return 1;
        } else if(color.equals(getString(R.string.blue))){
            return 2;
        }else if(color.equals(getString(R.string.yellow))){
            return 3;
        }else if(color.equals(getString(R.string.purple))){
            return 4;
        }else if(color.equals(getString(R.string.orange))){
            return 5;
        }else if(color.equals(getString(R.string.green))){
            return 6;
        }else if(color.equals(getString(R.string.white))){
            return 7;
        }else{
            return 8;
        }
    }

    private boolean unirsePartida(int color) { //TODO: CREAR PARTIDA
        if (color>0 && color<9) {
            return true;
        }else{
            return false;
        }
    }
}
