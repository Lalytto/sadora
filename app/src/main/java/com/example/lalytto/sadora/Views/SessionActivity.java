package com.example.lalytto.sadora.Views;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lalytto.sadora.Adapters.RVAdapterSession;
import com.example.lalytto.sadora.Controllers.AppCtrl;
import com.example.lalytto.sadora.LoginActivity;
import com.example.lalytto.sadora.Models.Categorias;
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

public class SessionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppCtrl ctrl;
    RecyclerView recyclerView;
    String uriService;
    private static final String MySession = "Lalytto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Intancia de controller
        ctrl = new AppCtrl(this);
        getSession(); // Verificar Login

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getCategories();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            getCategories();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            this.ctrl.activitiesCtrl.changeActivity(SessionActivity.this, AboutActivity.class);
        } else if (id == R.id.nav_logout) {
            logout();
        } else if (id == R.id.nav_nature) {
            Intent intent = ctrl.activitiesCtrl.changeActivityParams(SessionActivity.this, ClassTabActivity.class);
            intent.putExtra("categoria_id", 6);
            startActivity(intent);
        } else if(id == R.id.nav_scan_qr){
            this.ctrl.activitiesCtrl.changeActivity(SessionActivity.this, ScanActivity.class);
        } else {
            Intent intent = ctrl.activitiesCtrl.changeActivityParams(SessionActivity.this, CategoryActivity.class);
            String category = null;
            if (id == R.id.nav_bank) {
                category = "1";
            } else if (id == R.id.nav_culture) {
                category = "4";
            } else if (id == R.id.nav_food) {
                category = "2";
            } else if (id == R.id.nav_health) {
                category = "7";
            } else if (id == R.id.nav_hotel) {
                category = "5";
            } else if (id == R.id.nav_trip) {
                category = "10";
            } else if (id == R.id.nav_tour) {
                category = "9";
            } else if (id == R.id.nav_services) {
                category = "8";
            } else if (id == R.id.nav_shopping) {
                category = "3";
            }
            intent.putExtra("categoria_id", category);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void  getCategories(){
        recyclerView = (RecyclerView) findViewById(R.id.rcv_categories);
        uriService = HttpService.uriGET+"categorias";
        HttpClient client = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if(status.isSuccess()){
                    Gson gson = new GsonBuilder().create();
                    try {
                        JSONObject json = new JSONObject(status.getResult());
                        JSONArray jsonarray = json.getJSONArray("data");
                        ArrayList<Categorias> categorias = new ArrayList<Categorias>();
                        for(int i = 0; i < jsonarray.length(); i++) {
                            String categoria = jsonarray.getString(i);
                            Categorias a = gson.fromJson(categoria,Categorias.class);
                            categorias.add(a);
                        }

                        LinearLayoutManager llm = new LinearLayoutManager(SessionActivity.this);
                        recyclerView.setLayoutManager(llm);

                        recyclerView.setAdapter(new RVAdapterSession(categorias, new RVAdapterSession.OnItemClickListener() {
                            @Override
                            public void onItemClick(Categorias item) {
                                Intent intent = ctrl.activitiesCtrl.changeActivityParams(SessionActivity.this, CategoryActivity.class);
                                intent.putExtra("categoria_id", String.valueOf(item.getCategoria_id()));
                                startActivity(intent);
                            }
                        }));
                    }catch (Exception e){
                        System.out.println("Fallo!");
                        e.printStackTrace();
                    }
                } else {
                    ctrl.elementsService.displayToast("Ha fallado la conexión con lalytto.com - "+uriService);
                }
            }
        }); client.excecute(uriService);
    }

    private void logout(){
        SharedPreferences session = getSharedPreferences(MySession, 0);
        SharedPreferences.Editor editor = session.edit();
        editor.putBoolean("isLogged", false);
        editor.commit();
        ctrl.activitiesCtrl.changeActivity(SessionActivity.this, LoginActivity.class);
    }

    private void getSession(){
        SharedPreferences session = getSharedPreferences(MySession, 0);
        boolean isLogged = session.getBoolean("isLogged", false);
        if(!isLogged){
            ctrl.activitiesCtrl.changeActivity(SessionActivity.this, LoginActivity.class);
            finish();
            ctrl.elementsService.displayToast("Por favor inicie sesión para continuar...");
        } else {
            ctrl.elementsService.displayToast("Bienvenid@ ");
        }
    }

}
