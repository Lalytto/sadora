package com.example.lalytto.sadora.Services;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lalytto on 9/7/2016.
 */
public class HttpService extends AsyncTask<Void, Integer, Void> {

    String uriHttp = "http://192.168.54.3/";

    @Override
    protected Void doInBackground(Void... voids) {
        String salida = "";
        try {
            // URL DE ACCESO A WEB SERVICES
            URL url = new URL(uriHttp);
            // APERTURA DE CONEXIÓN
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // Header de permiso a navegador (webservice)
            urlConnection.setRequestProperty("User-Agent","Lalytto from Sadora");
            // code respuesta de petición
            int responseCode = urlConnection.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){

            } else {
                salida = "URL empty";
            }
            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
