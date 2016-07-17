package com.example.lalytto.sadora.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lalytto.sadora.LoginActivity;
import com.example.lalytto.sadora.Services.ElementsService;
import com.example.lalytto.sadora.Services.HttpService;
import com.example.lalytto.sadora.Views.SessionActivity;

/**
 * Created by Lalytto on 9/7/2016.
 */
public class AppCtrl {

    public static String URI = HttpService.uriHttp;

    Context ctx;
    public ActivitiesCtrl activitiesCtrl;
    public ElementsService elementsService;

    public AppCtrl(Context ctx){
        this.ctx = ctx;
        this.activitiesCtrl = new ActivitiesCtrl();
        this.elementsService = new ElementsService(ctx);
    }


}
