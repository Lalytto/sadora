package com.example.lalytto.sadora.Views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lalytto.sadora.Adapters.RVAdapterCategory;
import com.example.lalytto.sadora.Controllers.AppCtrl;
import com.example.lalytto.sadora.Models.Sitios;
import com.example.lalytto.sadora.R;
import com.example.lalytto.sadora.Services.HttpClient;
import com.example.lalytto.sadora.Services.HttpService;
import com.example.lalytto.sadora.Services.OnHttpRequestComplete;
import com.example.lalytto.sadora.Services.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    String category;
    String uriService;
    private AppCtrl ctrl;
    RecyclerView recyclerView;
    RVAdapterCategory adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.refresh_categoria);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refrescando registros", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                getCategory();
            }
        });

        ctrl = new AppCtrl(this);
        getCategory();

    }

    private void getCategory(){
        category = getIntent().getExtras().getString("category");
        uriService = HttpService.uriGET+category+"&customQuery=getFromCategory";
        recyclerView = (RecyclerView) findViewById(R.id.rcv_category);

        HttpClient client = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if(status.isSuccess()){
                    Gson gson = new GsonBuilder().create();
                    System.out.println(status.getResult());
                    try {
                        JSONObject json = new JSONObject(status.getResult());
                        JSONArray jsonarray = json.getJSONArray("data");
                        ArrayList<Sitios> sitios = new ArrayList<Sitios>();
                        for(int i = 0; i < jsonarray.length(); i++) {
                            String sitio = jsonarray.getString(i);
                            Sitios a = gson.fromJson(sitio,Sitios.class);
                            sitios.add(a);
                        }
                        adapter = new RVAdapterCategory(sitios);
                        LinearLayoutManager llm = new LinearLayoutManager(CategoryActivity.this);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(adapter);
                    }catch (Exception e){
                        System.out.println("Fallo!");
                        e.printStackTrace();
                    }
                } else {
                    ctrl.elementsService.displayToast("Ha fallado la conexión con lalytto.com - "+uriService);
                }
            }
        });
        client.excecute(uriService);
    }

}