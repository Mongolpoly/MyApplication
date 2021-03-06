package com.example.java.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

public class CreateGame extends Activity {

    private List<String> list;
    private ArrayAdapter<String> adapter;
    private String user;
    private Spinner spinner;
    private EditText et_num;
    private TextView ciudad;
    private Button menos, mas, img_mas, img_menos;
    private ImageSwitcher imageSwitcher;
    private int n_img;
    private int[] galeria = { R.drawable.city_1, R.drawable.city_2, R.drawable.city_3, R.drawable.city_4};
    private int[] ciudades = { R.string.ZAR, R.string.SAO, R.string.LON, R.string.WAS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int jugadores = 4;
        user = getIntent().getStringExtra("user");
        spinner = findViewById(R.id.spinner);
        et_num = (EditText) findViewById(R.id.et_njugadores);
        ciudad = (TextView) findViewById(R.id.tv_ciudad);
        mas = (Button) findViewById(R.id.btn_plus);
        menos = (Button) findViewById(R.id.btn_minus);
        list = new ArrayList<String>();
        for (int i=1;i<=jugadores;i++){
            list.add(color(i));
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        et_num.setKeyListener(null);
        et_num.setText(""+jugadores);
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(et_num.getText().toString())-1;
                list.remove(n);
                et_num.setText(""+n);
                if (n==2){
                    menos.setEnabled(false);
                }
                if (!mas.isEnabled()){
                    mas.setEnabled(true);
                }
            }
        });
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(et_num.getText().toString())+1;
                list.add(color(n));
                et_num.setText(""+n);
                if (n==8){
                    mas.setEnabled(false);
                }
                if (!menos.isEnabled()){
                    menos.setEnabled(true);
                }
            }
        });
        Button join = (Button) findViewById(R.id.btn_join_game);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_num.getText().toString().equals("")){
                    et_num.requestFocus();
                    et_num.setError(getString(R.string.error_empty_field));
                }else{
                    int jugadores = Integer.parseInt(et_num.getText().toString());
                    String color = spinner.getSelectedItem().toString();
                    Intent i = new Intent(CreateGame.this, Game.class);
                    i.putExtra("city", getString(ciudades[n_img]));
                    i.putExtra("user", user);
                    i.putExtra("ficha", (fichaColor(color)));
                    i.putExtra("jugadores", Integer.parseInt(et_num.getText().toString()));
                    startActivity(i);
                }
            }
        });
        img_mas = (Button) findViewById(R.id.btn_next);
        img_menos = (Button) findViewById(R.id.btn_prev);
        n_img = 0;
        imageSwitcher = (ImageSwitcher) findViewById(R.id.tableros);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView switcherImageView = new ImageView(getApplicationContext());
                switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));

                switcherImageView.setImageResource(galeria[0]);
                ciudad.setText(getString(ciudades[0]));
                switcherImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //switcherImageView.setMaxHeight(100);
                return switcherImageView;
            }
        });

        img_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n_img++;
                ciudad.setText(getString(ciudades[n_img]));
                imageSwitcher.setImageResource(galeria[n_img]);
                ImageView switcherImageView = new ImageView(getApplicationContext());
                switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
                switcherImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                if (n_img==3){
                    img_mas.setEnabled(false);
                }
                img_menos.setEnabled(true);
            }
        });
        img_menos.setEnabled(false);
        img_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n_img--;
                ciudad.setText(getString(ciudades[n_img]));
                imageSwitcher.setImageResource(galeria[n_img]);
                ImageView switcherImageView = new ImageView(getApplicationContext());
                switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
                switcherImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                if (n_img==0){
                    img_menos.setEnabled(false);
                }
                img_mas.setEnabled(true);
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
}