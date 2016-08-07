package com.example.lalytto.sadora.Views;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
import org.json.JSONObject;

public class SiteActivity extends AppCompatActivity {

    String sitio_id;
    String uriService;
    private AppCtrl ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                getSitio();
            }
        });
        ctrl = new AppCtrl(this);
        getSitio();
    }

    private void getSitio() {
        sitio_id = getIntent().getExtras().getString("sitio_id");
        uriService = HttpService.uriGET +"sitios&id=" + sitio_id;

        HttpClient client = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if(status.isSuccess()){
                    Gson gson = new GsonBuilder().create();
                    try {
                        JSONObject json = new JSONObject(status.getResult());
                        JSONArray jsonarray = json.getJSONArray("data");
                        Sitios sitio = new Sitios();
                        sitio = gson.fromJson(jsonarray.getString(0),Sitios.class);
                        SiteActivity.this.setTitle(sitio.getSitio_nombre());

                        TextView categoria = (TextView) findViewById(R.id.categoria_nombre);
                        TextView clase = (TextView) findViewById(R.id.clase_nombre);
                        TextView visitas = (TextView) findViewById(R.id.sitio_visitas);
                        TextView contacto = (TextView) findViewById(R.id.sitio_contacto);
                        TextView descripcion = (TextView) findViewById(R.id.sitio_descripcion);
                        TextView direccion  = (TextView) findViewById(R.id.sitio_direccion);
                        SmartImageView img = (SmartImageView) findViewById(R.id.sitio_imagen);

                        categoria.setText(sitio.getCategoria_nombre());
                        clase.setText(sitio.getClase_nombre());
                        visitas.setText(sitio.getSitio_visitas());
                        contacto.setText(sitio.getSitio_direccion());
                        descripcion.setText(sitio.getSitio_direccion());
                        direccion.setText(sitio.getSitio_descripcion());
                        img.setImageUrl("http://sadora.lalytto.com/app/src/img/sitios/"+sitio.getSitio_imagen());

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
}
