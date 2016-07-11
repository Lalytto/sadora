package com.example.lalytto.sadora.Views;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lalytto.sadora.Models.Article;
import com.example.lalytto.sadora.R;
import com.example.lalytto.sadora.Services.HttpClient;
import com.example.lalytto.sadora.Services.HttpService;
import com.example.lalytto.sadora.Services.OnHttpRequestComplete;

import org.json.JSONObject;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    ArrayList<Article> articleList;
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
            public void onComplete(com.example.lalytto.sadora.Services.Response status) {
                if(status.isSuccess()){
                    try {
                        JSONObject json = new JSONObject(status.getResult());
                        descApp.setText(json.getString("data"));
                    }catch (Exception e){
                        System.out.println("Fallo!");
                        e.printStackTrace();
                    }
                    Toast.makeText(AboutActivity.this, status.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        client.excecute(HttpService.uriHttp+"about");
    }

}
