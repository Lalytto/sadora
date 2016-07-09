package com.example.lalytto.sadora.Controllers;

import android.content.Context;

import com.example.lalytto.sadora.Services.ElementsService;

/**
 * Created by Lalytto on 9/7/2016.
 */
public class AppCtrl {

    Context ctx;
    public ActivitiesCtrl activitiesCtrl;
    public ElementsService elementsService;

    public AppCtrl(Context ctx){
        this.ctx = ctx;
        this.activitiesCtrl = new ActivitiesCtrl();
        this.elementsService = new ElementsService(ctx);
    }

}
