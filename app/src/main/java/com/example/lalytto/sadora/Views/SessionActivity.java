package com.example.lalytto.sadora.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lalytto.sadora.Controllers.AppCtrl;
import com.example.lalytto.sadora.R;

public class SessionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppCtrl ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Intancia de controller
        this.ctrl = new AppCtrl(this);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
        } else {
            Intent intent;
            intent = this.ctrl.activitiesCtrl.changeActivityParams(SessionActivity.this, CategoryActivity.class);
            String category = null;
            if (id == R.id.nav_bank) {
                category = "banks";
            } else if (id == R.id.nav_culture) {
                category = "culture";
            } else if (id == R.id.nav_food) {
                category = "food";
            } else if (id == R.id.nav_health) {
                category = "health";
            } else if (id == R.id.nav_hotel) {
                category = "hotels";
            } else if (id == R.id.nav_nature) {
                category = "nature";
            } else if (id == R.id.nav_trip) {
                category = "trips";
            } else if (id == R.id.nav_tour) {
                category = "tours";
            } else if (id == R.id.nav_services) {
                category = "services";
            } else if (id == R.id.nav_shopping) {
                category = "shopping";
            }
            intent.putExtra("category", category);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
