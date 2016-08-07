package com.example.lalytto.sadora.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.lalytto.sadora.Adapters.RecyclerViewOnItemClickListener;
import com.example.lalytto.sadora.Adapters.SitesAdapter;
import com.example.lalytto.sadora.Controllers.AppCtrl;
import com.example.lalytto.sadora.Models.Sitios;
import com.example.lalytto.sadora.R;
import com.example.lalytto.sadora.Services.HttpClient;
import com.example.lalytto.sadora.Services.HttpService;
import com.example.lalytto.sadora.Services.OnHttpRequestComplete;
import com.example.lalytto.sadora.Services.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.widget.AbsListView.OnScrollListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    String categoria_id;
    String uriService;
    AppCtrl ctrl;
    LinearLayoutManager llm;
    RecyclerView recyclerView;
    List<Sitios> sitios;
    int pageId = 1;

    private boolean userScrolled = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private static RelativeLayout bottomLayout;
    private static SitesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ctrl = new AppCtrl(this);
        bottomLayout = (RelativeLayout) findViewById(R.id.loadItemsLayout_listView);
        sitios = new ArrayList<Sitios>();
        recyclerView = (RecyclerView) findViewById(R.id.rcv_category);
        loadSites(pageId);

        llm = new LinearLayoutManager(CategoryActivity.this);
        recyclerView.setLayoutManager(llm);
        adapter = new SitesAdapter(sitios, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (sitios.get(position) instanceof Sitios) {
                    Intent intent = ctrl.activitiesCtrl.changeActivityParams(CategoryActivity.this, SiteActivity.class);
                    intent.putExtra("sitio_id", String.valueOf(sitios.get(position).getSitio_id()));
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        implementScrollListener();
    }

    private void loadSites(int page){
        categoria_id = getIntent().getExtras().getString("categoria_id");
        uriService = HttpService.uriGET +"sitios&smartQuery&limit=5&order=sitio_nombre&page="+page+"&filter=&categoryId=" + categoria_id;
        HttpClient client = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                System.out.println(uriService);
                if(status.isSuccess()){
                    Gson gson = new GsonBuilder().create();
                    System.out.println(status.getResult());
                    try {
                        JSONObject json = new JSONObject(status.getResult());
                        JSONArray jsonarray = json.getJSONArray("data");
                        for(int i = 0; i < jsonarray.length(); i++) {
                            String sitio = jsonarray.getString(i);
                            sitios.add(gson.fromJson(sitio,Sitios.class));
                        }
                    }catch (Exception e){
                        System.out.println("Fallo!");
                        e.printStackTrace();
                    }
                } else {
                    ctrl.elementsService.displayToast("Ha fallado la conexión con lalytto.com - " + uriService);
                }
            }
        });
        client.excecute(uriService);
    }


    private void implementScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Here get the child count, item count and visibleitems
                // from layout manager
                visibleItemCount = llm.getChildCount();
                totalItemCount = llm.getItemCount();
                pastVisiblesItems = llm.findFirstVisibleItemPosition();
                // Now check if userScrolled is true and also check if
                // the item is end then update recycler view and set
                // userScrolled to false
                if (userScrolled && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                    userScrolled = false;
                    updateRecyclerView();
                }
            }
        });
    }

    private void updateRecyclerView() {
        bottomLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageId++;
                loadSites(pageId);
                adapter.notifyDataSetChanged();
                ctrl.elementsService.displayToast("Items Updated.");
                bottomLayout.setVisibility(View.GONE);
            }
        }, 2000);
    }


}
