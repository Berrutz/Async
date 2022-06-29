package com.example.async;

import android.app.Activity;
import android.util.Log;

import com.example.async.Interfaces.IDownload;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BackTask {

    private final String TAG = "BackTask";
    private Activity activity = null;
    private IDownload ID = null;

    public BackTask(Activity act,IDownload id){
        Log.i(TAG,"BackTask costruttore");
        activity=act;
        ID=id;
    }

    public void doDownload(){
        Log.i(TAG,"doDownload");

        new Thread(new Runnable() { // Crea thread parallelo
            @Override
            public void run() {
                //doInBackground
                String _url = "https://www.learningcontainer.com/wp-content/uploads/2020/04/sample-text-file.txt";
                String _res = doHeavyWork(_url);


                activity.runOnUiThread(new Runnable() {  // Permette di accedere alla UI della main activity
                    @Override
                    public void run() {
                        // OnPostExecute ( modificare UI ) Inserire in una Textview gia creata
                        // Notificare MainActivity con res via Interfacce

                        Log.i(TAG,"Text: " + _res);
                        ID.onDownloadDone(_res); // triggera on  callback tutti i metodi che la implementano dice

                    }
                });
            }
        }).start();
    }

    private String doHeavyWork(String _site) {
        Log.i(TAG, "doHeavyWork");
        String stream = "";
        try {
            URL url = new URL(_site);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                stream = sb.toString();
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }
}
