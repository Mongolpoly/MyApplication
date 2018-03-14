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
        EditText et_user  = (EditText) findViewById(R.id.et_user);
        EditText et_pass  = (EditText) findViewById(R.id.et_pass);
        final String user = et_user.getText().toString();
        final String pass = et_pass.getText().toString();

        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                conectaBD();
                final String realuser=  "";
                final String realpass =  "";//buscar en la bbdd la pass de user
                if (user.equals(realuser)) {
                    if (pass.equals(realpass)) {
                        Intent i = new Intent(MainActivity.this, ListGames.class);
                        i.putExtra("USUARIO", user);
                        startActivity(i);
                    }else{
                        Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
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

    public void conectaBD(){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + RUTA + ":" + PUERTO + "/" + BD, USER, PASSWORD);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*public static int LeeFichero(){
        File f1 = new File(R.id.txt);
        Scanner sr = null;
        try {
            sr = new Scanner(f1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String palabra;
        int c = 0;
        while(sr.hasNext()){
            palabra = sr.next();
            System.out.println(palabra);
            c++;
        }
        System.out.println("Hay "+c+" palabras");
        sr.close();
        return c;
    }*/
}