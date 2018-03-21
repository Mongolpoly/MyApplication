package com.example.java.myapplication;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class JSONParser {

    JSONObject json;
    JSONArray jsonArray;
    Boolean correcto;
    ArrayList<String> js = new ArrayList<>();
    String success;


    public boolean Registrousuarios( String nombre, String password) throws InterruptedException, ExecutionException, JSONException {

        correcto=true;

        List<NameValuePair> params = new LinkedList();
        params.add(new BasicNameValuePair("nombre",nombre));
        params.add(new BasicNameValuePair("password",password));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest("http://mongolpoly.esy.es/db_registro_jugadores.php", params);
        try {
            jsonArray = json.getJSONArray("respuesta");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                success = json.getString("success");
            }
            if(success.equals("1")){
                return TRUE;
            }else{
                return FALSE;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return FALSE;
        }
    }

    public boolean registroSala(String ciudad, String cantidad, String idjugador, String ficha) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        //isset($_GET['ciudad']) && isset($_GET['n_jugadores']) && isset($_GET['jugador']) && isset($_GET['ficha'])
        params.add(new BasicNameValuePair("ciudad",ciudad));
        params.add(new BasicNameValuePair("n_jugadores",cantidad));
        params.add(new BasicNameValuePair("jugador",idjugador));
        params.add(new BasicNameValuePair("ficha",ficha));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest("http://mongolpoly.esy.es/db_registro_partidas.php", params);
        try {
            jsonArray = json.getJSONArray("respuesta");
            JSONObject json = jsonArray.getJSONObject(0);
            success = json.getString("success");
            if(success.equals("1")){
                return TRUE;
            }else{
                return FALSE;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return FALSE;
        }
    }

    public boolean loginusuarios( String nombre, String password) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        params.add(new BasicNameValuePair("nombre",nombre));
        params.add(new BasicNameValuePair("password",password));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest("http://mongolpoly.esy.es/db_comprobar_jugadores.php", params);
        try {
            jsonArray = json.getJSONArray("respuesta");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                success = json.getString("success");
            }
            if(success.equals("1")){
                return TRUE;
            }else{
                return FALSE;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return FALSE;
        }
    }

    public ArrayList listasalas() throws InterruptedException, ExecutionException, JSONException {
        ArrayList<ArrayList> Arraysalas = new ArrayList<>();
        List<NameValuePair> params = new LinkedList();
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest("http://mongolpoly.esy.es/db_comprobar_partidas.php", params);
        try {
            jsonArray = json.getJSONArray("salas");
            for (int i = 0; i < jsonArray.length(); i++) {
                ArrayList<String> sala = new ArrayList<>();
                JSONObject json = jsonArray.getJSONObject(i);
                String id = json.getString("id");
                sala.add(id);
                String ciudad = json.getString("ciudad");
                sala.add(ciudad);
                String size = json.getString("tamanio");
                sala.add(size);
                String ocupadas = json.getString("ocupadas");
                sala.add(ocupadas);
                Arraysalas.add(sala);
            }
            return Arraysalas;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}