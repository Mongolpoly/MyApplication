package com.example.java.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends Activity {

    private static final String RUTA = "127.0.0.1";
    private static final String PUERTO = "3306";
    private static final String BD = "monopoly";
    private static final String USER = "player";
    private static final String PASSWORD = "player";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.log_in));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //ver en PORTRAIT y dejar la parte de abajo para tirar dado, propiedades... USAR FRAMES
        TextView create_account  = (TextView) findViewById(R.id.createaccount);
        final EditText et_user  = (EditText) findViewById(R.id.et_user);
        final EditText et_pass  = (EditText) findViewById(R.id.et_pass);
        final Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String user = et_user.getText().toString();
                String pass = et_pass.getText().toString();
                String realuser=  "test";//buscar en la bbdd si existe el user
                String realpass =  "test";//buscar en la bbdd la pass de user
                boolean vacio = false;
                if (et_pass.getText().toString().equals("")){
                    et_pass.setError(getString(R.string.error_empty_field));
                    et_pass.requestFocus();
                    vacio = true;
                }
                if (et_user.getText().toString().equals("")){
                    et_user.setError(getString(R.string.error_empty_field));
                    et_user.requestFocus();
                    vacio = true;
                }
                if(!vacio){
                    if (user.equals(realuser)) {
                        if (pass.equals(realpass)) {
                            Intent i = new Intent(MainActivity.this, ListGames.class);
                            i.putExtra("user", user);
                            startActivity(i);
                        }else{
                            et_pass.setError(getString(R.string.error_incorrect_password));
                            et_pass.requestFocus();
                        }
                    }else{
                        et_user.setError(getString(R.string.error_inexistent_user));
                        et_user.requestFocus();
                    }
                }

            }
        });
        create_account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, CreateUser.class);
                startActivity(i);
            }
        });
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

}