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
        user = getIntent().getStringExtra("user");
        final int[] maxjugadores = {5,8,4,2}; //TODO: JSON
        final int[] jugadores = {3,6,2,1}; //TODO: JSON
        final int[] idpartida = {1,2,3,4}; //TODO: JSON
        final String [] city = {"Zaragoza", "Sao Paulo", "London", "Washington"}; //TODO: JSON que será otro for
        listView = (ListView) findViewById(R.id.list);
        List<String> values = new ArrayList<String>();
        values.add(getString(R.string.create_game));
        int c=0;
        for (int i=0; i<maxjugadores.length; i++){
            values.add(city[i]+" - "+getString(R.string.users_max)+": "+maxjugadores[i]+" - "+getString(R.string.users)+": "+jugadores[i]);
            c++;
        }
        if (c==0){
            values.add("no hay partidas disponibles");
        }
        /*List<String> values = new ArrayList<String>();
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
                    i.putExtra("user", user);
                    startActivity(i);
                }else{
                    if(unirsepartida(position)){
                        Intent i = new Intent(ListGames.this, JoinGame.class); //unirse a una partida
                        i.putExtra("user", user);
                        i.putExtra("idpartida", idpartida[position-1]);
                        i.putExtra("jugadores", maxjugadores[position-1]);
                        i.putExtra("city", city[position-1]);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), getString(R.string.closed) , Toast.LENGTH_LONG).show();
                        recargar();
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
        int jugadores = 2; //json con n_jugadores de la tabla partida
        if (maxjugadores>=jugadores+1){
            return true;
        } else {
            return false;
        }
    }

    private void recargar() {
        //TODO: Hacer la recarga del lisview
    }
}