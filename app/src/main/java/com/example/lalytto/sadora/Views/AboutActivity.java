package com.example.lalytto.sadora.Views;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.lalytto.sadora.Models.Sitios;
import com.example.lalytto.sadora.R;
import com.example.lalytto.sadora.Services.HttpClient;
import com.example.lalytto.sadora.Services.HttpService;
import com.example.lalytto.sadora.Services.OnHttpRequestComplete;
import com.example.lalytto.sadora.Services.Response;
import com.loopj.android.image.SmartImageView;

import org.json.JSONObject;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    NestedScrollView stackContent;
    TextView descApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stackContent = (NestedScrollView) findViewById(R.id.scrollViewContent);
        descApp = (TextView) findViewById(R.id.textDesc);

        HttpClient client = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if(status.isSuccess()){
                    try {
                        JSONObject json = new JSONObject(status.getResult());
                        JSONObject data = json.getJSONObject("data");
                        descApp.setText(data.getString("setting_descripcion"));
                        setTitle(data.getString("setting_nombre"));
                        SmartImageView img = (SmartImageView) findViewById(R.id.app_imagen);
                        img.setImageUrl("http://sadora.lalytto.com/app/src/img/system/logo.png");
                    }catch (Exception e){
                        System.out.println("Fallo!");
                        e.printStackTrace();
                    }
                }
            }
        });
        client.excecute(HttpService.uriGET+"settings");
    }

}
