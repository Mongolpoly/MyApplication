package com.example.java.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateGame extends Activity {

    String user;
    Spinner spinner;
    EditText et_num;
    Button menos, mas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        int jugadores = 2;
        user = getIntent().getStringExtra("user");
        spinner = findViewById(R.id.spinner);
        et_num = (EditText) findViewById(R.id.et_njugadores);
        mas = (Button) findViewById(R.id.btn_plus);
        menos = (Button) findViewById(R.id.btn_minus);
        List<String> list = new ArrayList<String>();
        for (int i=1;i<=jugadores;i++){
            list.add(color(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        et_num.setKeyListener(null);
        et_num.setText(""+jugadores);
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(et_num.getText().toString());
                et_num.setText(""+n--);
                if (n==2){
                    menos.setEnabled(false);
                }
            }
        });
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(et_num.getText().toString());
                et_num.setText(""+n++);
                if (n==8){
                    menos.setEnabled(false);
                }
            }
        });
        Button btn = (Button) findViewById(R.id.btn_join_game);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_num.getText().toString().equals("")){
                    et_num.requestFocus();
                    et_num.setError(getString(R.string.error_empty_field));
                }else{
                    int jugadores = Integer.parseInt(et_num.getText().toString());
                    String color = spinner.getSelectedItem().toString();
                    if (crearPartida(jugadores, fichaColor(color))){ //si crea la partida bien
                        Intent i = new Intent(CreateGame.this, Game.class);
                        i.putExtra("user", user);
                        i.putExtra("ficha", (fichaColor(color)));
                        i.putExtra("jugadores", Integer.parseInt(et_num.getText().toString()));
                        startActivity(i);
                    }else{
                        finish();
                        Toast.makeText(CreateGame.this, "", Toast.LENGTH_SHORT).show(); //sin texto porque debería crearla siempre
                    }
                }
            }
        });
    }

    private boolean crearPartida(int jugadores, int color) { //TODO: CREAR PARTIDA
        if (jugadores>1 && jugadores <9) {
            return true;
        }else{
            return false;
        }
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
}