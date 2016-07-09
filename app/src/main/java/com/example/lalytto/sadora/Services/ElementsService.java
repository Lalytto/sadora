package com.example.lalytto.sadora.Services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Lalytto on 9/7/2016.
 */
public class ElementsService {

    Context ctx;
    // Mensaje de Diálogo
    private AlertDialog.Builder alertDialog;
    // ProgressDialog
    public ProgressDialog pDialog;

    public ElementsService(Context ctx){
        this.ctx = ctx;
    }

    // Presentar mensaje de alerta
    public void displayToast(String str){
        Toast.makeText(this.ctx, str, Toast.LENGTH_SHORT).show();
    }

}
