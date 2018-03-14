package com.example.java.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListGames extends Activity {

    ListView listView;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);
        Bundle bundle = new Bundle();
        user = getIntent().getStringExtra("USUARIO");
        listView = (ListView) findViewById(R.id.list);
        String[] values = new String[] {
                getString(R.string.create_game),
                "Partida 1 - 3J - MAX: 5J",
                "Partida 2 - 6J - MAX: 8J",
                "Partida 3 - 2J - MAX: 4J",
                "Partida 4 - 1J - MAX: 2J"
        };
        /*String select =  "SELECT * FROM partidas WHERE accesible IS TRUE";
        SQLiteDatabase db = con.getWritableDatabase();
        List<String> partidas = (String) db.execSQL(select);
        int total = 5; //length de lo que devuelve el select
        List<String> values = new ArrayList<String>();
        values.add(getString(R.string.create_game)); //crear partida
        for (int i=0; i<total;i++){ //las partidas disponibles
            values.add(partidas.get(i);
        }*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listView.getItemAtPosition(position);
                if (position==0){
                    Intent i = new Intent(ListGames.this, CreateGame.class); //crear la partida
                    i.putExtra("USUARIO", user);
                    startActivity(i);
                }else{
                    if(unirsepartida(position)){
                        Intent i = new Intent(ListGames.this, JoinGame.class); //unirse a una partida
                        i.putExtra("USUARIO", user);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), getString(R.string.closed) , Toast.LENGTH_LONG).show();
                    }
                }
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

    public boolean unirsepartida(int pos){
        //accedes a la partida con un insert vacío
        int maxjugadores = 5; //json con n_jugadores de la tabla partida
        int jugadores = 5; //json con n_jugadores de la tabla partida
        if (maxjugadores>=jugadores+1){
            return true;
        } else {
            return false;
        }
    }
}