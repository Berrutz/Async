package com.example.async;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.async.Interfaces.IDownload;

public class MainActivity extends AppCompatActivity implements IDownload {

    private TextView tvTesto = null;
    private Button bttGo = null;
    private BackTask backtask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTesto = findViewById(R.id.tvText);
        bttGo =  findViewById(R.id.bttGO);
        backtask = new BackTask(this,this );

        bttGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OnPreExecute
                bttGo.setText("Dowloading..."); // modifico UI e posso perchè sono qui
                backtask.doDownload(); // EFFETTUA DOWNLOAD I N BACKGROUND
            }
        });
    }

    @Override
    public void onDownloadDone(String data) {
        tvTesto.setText(data);
        bttGo.setText("GO");
    }
}