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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ListGames extends Activity {

    ListView listView;
    String user;
    ArrayList<ArrayList> partidas = new ArrayList<>();
    JSONParser jsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);
        listView = findViewById(R.id.list);
        user = getIntent().getStringExtra("user");

        jsp = new JSONParser();
        try {
            ArrayList<String> values = new ArrayList<String>();
            values.add("");
            values.add(getString(R.string.create_game));
            values.add("");
            values.add("");
            partidas = jsp.listasalas();

            if (partidas != null) {
                partidas.add(0, values);
            } else {

                partidas = new ArrayList<>();
                partidas.add(values);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(new CustomList(this, partidas));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listView.getItemAtPosition(position);



                if (position == 0) {
                    Intent i = new Intent(ListGames.this, CreateGame.class); //crear la partida
                    i.putExtra("user", user);
                    startActivity(i);
                } else {
                    if (unirsepartida(position)) {
                        Intent i = new Intent(ListGames.this, JoinGame.class); //unirse a una partida
                        i.putExtra("user", user);
                        i.putExtra("idpartida", Integer.parseInt(listView.getAdapter().getView(position, null, listView).getTag().toString()));
                        i.putExtra("jugadores", Integer.parseInt(partidas.get(position - 1).get(2).toString()));
                        i.putExtra("city", partidas.get(position - 1).get(1).toString());
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.closed), Toast.LENGTH_LONG).show();
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

    public boolean unirsepartida(int pos) {

        int maxjugadores = Integer.parseInt(partidas.get(pos-1).get(2).toString());
        int jugadores = Integer.parseInt(partidas.get(pos-1).get(3).toString());
         if (maxjugadores>=jugadores+1) {
             return TRUE;
         }else{
             return FALSE;
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