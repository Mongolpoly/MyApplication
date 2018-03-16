package com.example.java.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

public class CreateGame extends Activity {

    private String user;
    private Spinner spinner;
    private EditText et_num;
    private Button menos, mas, img_mas, img_menos;
    private ImageSwitcher imageSwitcher;
    private int n_img;
    private int[] galeria = { R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        int jugadores = 4;
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
                int n = Integer.parseInt(et_num.getText().toString())-1;
                et_num.setText(""+n);
                if (n==2){
                    menos.setEnabled(false);
                }
                mas.setEnabled(true);
            }
        });
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(et_num.getText().toString())+1;
                et_num.setText(""+n);
                if (n==8){
                    mas.setEnabled(false);
                }
                menos.setEnabled(true);
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
        img_mas = (Button) findViewById(R.id.btn_next);
        img_menos = (Button) findViewById(R.id.btn_prev);
        n_img = 1;
        imageSwitcher = (ImageSwitcher) findViewById(R.id.tableros);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView switcherImageView = new ImageView(getApplicationContext());
                switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
                switcherImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                switcherImageView.setImageResource(R.drawable.ic_launcher_background);
                //switcherImageView.setMaxHeight(100);
                return switcherImageView;
            }
        });

        img_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setImageResource(R.drawable.ic_launcher_foreground);
                n_img++;
                if (n_img==4){
                    img_mas.setEnabled(false);
                }
                img_menos.setEnabled(true);
            }
        });
        img_menos.setEnabled(false);
        img_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setImageResource(R.drawable.ic_launcher_background);
                n_img--;
                if (n_img==1){
                    img_menos.setEnabled(false);
                }
                img_mas.setEnabled(true);
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