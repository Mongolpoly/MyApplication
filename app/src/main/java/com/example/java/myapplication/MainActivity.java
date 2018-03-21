package com.example.java.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    private static final String RUTA = "127.0.0.1";
    private static final String PUERTO = "3306";
    private static final String BD = "monopoly";
    private static final String USER = "player";
    private static final String PASSWORD = "player";
    private EditText et_user, et_pass;
    //private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.log_in));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView create_account  = (TextView) findViewById(R.id.createaccount);
        et_user  = (EditText) findViewById(R.id.et_user);
        et_pass  = (EditText) findViewById(R.id.et_pass);
        et_pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) { //listener para entrar con enter
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                attemptLogin();
            }
        });
        create_account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, CreateUser.class);
                startActivity(i);
            }
        });
    }

    public void attemptLogin(){
        String user = et_user.getText().toString();
        String pass = et_pass.getText().toString();
        JSONParser jsp = new JSONParser();
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
            int estado = -1;
            try {
                estado = jsp.loginusuarios(user, pass);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "estado "+estado, Toast.LENGTH_SHORT).show();
            if(estado==3){
                et_user.setError(getString(R.string.error_inexistent_user));
                et_user.requestFocus();
            }else if (estado==2){
                et_pass.setError(getString(R.string.error_incorrect_password));
                et_pass.requestFocus();
            }else if (estado==1){
                Intent i = new Intent(MainActivity.this, ListGames.class);
                i.putExtra("user", et_user.getText().toString());
                startActivity(i);
            }
        }
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