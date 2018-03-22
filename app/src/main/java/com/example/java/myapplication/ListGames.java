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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListGames extends Activity {

    ListView listView;
    String user;
    ArrayList<ArrayList> partidas;// = new ArrayList<>();
    JSONParser jsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);
        listView = findViewById(R.id.list);
        user = getIntent().getStringExtra("user");
        ArrayList<String> values = new ArrayList<String>();
        values.add(getString(R.string.create_game));
        jsp = new JSONParser();
        try {
            partidas = jsp.listasalas();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (partidas!=null){
            //id, ciudad, max, jugadore
            int size = partidas.size();
            final int[] maxjugadores = new int[size];
            int[] jugadores = new int[size];
            int[] idpartida = new int[size];
            String [] city = new String[size];
            for (int i = 0; i < size; i++) {
                idpartida[i] = Integer.parseInt(partidas.get(i).get(0).toString());
                city[i] = partidas.get(i).get(1).toString();
                maxjugadores[i] = Integer.parseInt(partidas.get(i).get(2).toString());
                jugadores[i] = Integer.parseInt(partidas.get(i).get(3).toString());
                values.add(city[i]+" - "+getString(R.string.users_max)+": "+maxjugadores[i]+" - "+getString(R.string.users)+": "+jugadores[i]);
            }
        }
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
                        i.putExtra("idpartida", Integer.parseInt(partidas.get(position-1).get(0).toString()));
                        i.putExtra("jugadores", Integer.parseInt(partidas.get(position-1).get(2).toString()));
                        i.putExtra("city", partidas.get(position-1).get(1).toString());
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
        int maxjugadores = Integer.parseInt(partidas.get(pos-1).get(2).toString());
        int jugadores = Integer.parseInt(partidas.get(pos-1).get(3).toString());
        if (maxjugadores>=jugadores+1){
            return true;
        } else {
            return false;
        }
    }

    private void recargar() {
        try {
            partidas = jsp.listasalas();
            listView.invalidateViews();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}