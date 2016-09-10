package com.example.lalytto.sadora.Views;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
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
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    String categoria_id;
    String uriService;
    AppCtrl ctrl;
    LinearLayoutManager llm;
    RecyclerView recyclerView;
    List<Sitios> sitios;
    String search;
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

        // Intancia de controller
        this.ctrl = new AppCtrl(this);

        Intent searchIntent = getIntent();
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            search = searchIntent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(query);
            this.ctrl.elementsService.displayToast(query);
        }

        bottomLayout = (RelativeLayout) findViewById(R.id.loadItemsLayout_listView);
        sitios = new ArrayList<Sitios>();
        recyclerView = (RecyclerView) findViewById(R.id.rcv_category);
        loadSites(pageId);

        llm = new LinearLayoutManager(SearchResultActivity.this);
        recyclerView.setLayoutManager(llm);
        adapter = new SitesAdapter(sitios, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (sitios.get(position) instanceof Sitios) {
                    Intent intent = ctrl.activitiesCtrl.changeActivityParams(SearchResultActivity.this, SiteActivity.class);
                    intent.putExtra("sitio_id", String.valueOf(sitios.get(position).getSitio_id()));
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        implementScrollListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.session, menu);

        // Buscador
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void loadSites(int page){
        categoria_id = getIntent().getExtras().getString("categoria_id");
        uriService = HttpService.uriGET +"sitios&smartQuery&limit=5&order=sitio_nombre&page="+page+"&filter="+search;
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
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = llm.getChildCount();
                totalItemCount = llm.getItemCount();
                pastVisiblesItems = llm.findFirstVisibleItemPosition();
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
                bottomLayout.setVisibility(View.GONE);
            }
        }, 2000);
    }

}
