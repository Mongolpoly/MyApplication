package com.example.java.myapplication;

import android.app.Activity;
import android.os.Bundle;

public class JoinGame extends Activity {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        user = getIntent().getStringExtra("USUARIO");
    }
}
