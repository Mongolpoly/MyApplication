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
    String con = "http://mongolpoly.esy.es/";


    public boolean Registrousuarios( String nombre, String password) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        params.add(new BasicNameValuePair("nombre",nombre));
        params.add(new BasicNameValuePair("password",password));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_registro_jugadores.php", params);
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
        json = c.makeHttpRequest(con+"db_registro_partidas.php", params);
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

    public int loginusuarios( String nombre, String password) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        String mensaje = "";
        List<NameValuePair> params = new LinkedList();
        params.add(new BasicNameValuePair("nombre",nombre));
        params.add(new BasicNameValuePair("password",password));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_comprobar_jugadores.php", params);
        try {
            jsonArray = json.getJSONArray("respuesta");

                JSONObject json = jsonArray.getJSONObject(0);
                success = json.getString("success");
                mensaje = json.getString("message");

            if(success.equals("1")){
                return 1;
            }else{
                if(mensaje.equals("password")){
                    return 2;
                }else{
                    return 3;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList listasalas() throws InterruptedException, ExecutionException, JSONException {
        ArrayList<ArrayList> Arraysalas = new ArrayList<>();
        List<NameValuePair> params = new LinkedList();
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_comprobar_partidas.php", params);
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

    public ArrayList comprobarMochila(String idpartida, String idjugador) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        //isset(isset($_GET['partida']))
        params.add(new BasicNameValuePair("partida",idpartida));
        params.add(new BasicNameValuePair("jugador",idjugador));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_comprobar_mochila.php", params);

        try {
            jsonArray = json.getJSONArray("mochila");
            ArrayList <ArrayList> mochila = new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                ArrayList<String> inventario = new ArrayList<>();
                String nom = json.getString("id");
                inventario.add(nom);
                String din = json.getString("dinero");
                inventario.add(din);
                String prop = json.getString("idpropiedad");
                inventario.add(prop);
                mochila.add(inventario);
            }
            return mochila;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList comprobarJugadoresTurno(String turno, String partida) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        //isset($_GET['turno']) && isset($_GET['idsala'])
        params.add(new BasicNameValuePair("turno",turno));
        params.add(new BasicNameValuePair("idsala",partida));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_comprobar_jugadores_turno.php", params);
        try {
            jsonArray = json.getJSONArray("turno");
            ArrayList <String> Tjugador = new ArrayList<>();
            JSONObject json = jsonArray.getJSONObject(0);
            String nom = json.getString("jugador");
            Tjugador.add(nom);
            return Tjugador;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList comprobarJugadoresSala(String idpartida) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        //isset(isset($_GET['partida']))
        params.add(new BasicNameValuePair("partida",idpartida));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_comprobar_jugadores_partida.php", params);
        try {
            jsonArray = json.getJSONArray("jugadores");
            ArrayList<String> jugadores= new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String jugador = json.getString("jugador");
                jugadores.add(jugador);
            }
            return jugadores;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList ComprobarFichaPartida(String idpartida) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        //isset(isset($_GET['partida']))
        params.add(new BasicNameValuePair("idsala",idpartida));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_comprobar_ficha_disponible.php", params);
        try {
            jsonArray = json.getJSONArray("fichas");
            ArrayList<String> fichasdisponibles = new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                ArrayList<String> jugadores= new ArrayList<>();
                String ficha = json.getString("ficha");
                fichasdisponibles.add(ficha);
            }
            return fichasdisponibles;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean actualizarTurno(String idpartida, String numjugadores) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        //isset(isset($_GET['partida']))
        params.add(new BasicNameValuePair("partida",idpartida));
        params.add(new BasicNameValuePair("participantes",numjugadores));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_actualizar_turno.php", params);
        try {
            String conseguido="";
            jsonArray = json.getJSONArray("respuesta");
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                conseguido = json.getString("success");
            }
            if(conseguido.equals("1")){
                return TRUE;
            }else{
                return FALSE;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return FALSE;
        }
    }

    public ArrayList comprobarDoblesJugador(String idpartida, String idjugador) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        //isset(isset($_GET['partida']))
        params.add(new BasicNameValuePair("partida",idpartida));
        params.add(new BasicNameValuePair("jugador",idjugador));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_comprobar_dobles_jugadores.php", params);
        try {
            jsonArray = json.getJSONArray("tiradas");
            ArrayList<String> dobles = new ArrayList<>();
            JSONObject json = jsonArray.getJSONObject(0);
            String doble = json.getString("dobles");
            dobles.add(doble);
            return dobles;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList ComprobarDineroJugadoresSala(String idpartida) throws InterruptedException, ExecutionException, JSONException {
        correcto=true;
        List<NameValuePair> params = new LinkedList();
        //isset(isset($_GET['partida']))
        params.add(new BasicNameValuePair("partida",idpartida));
        ConexionHTTPGet c = new ConexionHTTPGet();
        json = c.makeHttpRequest(con+"db_comprobar_dinero_jugador.php", params);
        try {
            jsonArray = json.getJSONArray("partida");
            ArrayList<ArrayList> partida = new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                ArrayList<String> jugadores= new ArrayList<>();
                String jugador = json.getString("jugador");
                jugadores.add(jugador);
                String nombre = json.getString("nombre");
                jugadores.add(nombre);
                String dinero = json.getString("dinero");
                jugadores.add(dinero);
                partida.add(jugadores);
            }
            return partida;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}