package com.example.lalytto.sadora.Controllers;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Lalytto on 9/7/2016.
 */
public class ActivitiesCtrl {

    public ActivitiesCtrl(){

    }

    public void changeActivity(Context out, Class in){
        out.startActivity(new Intent(out, in));
    }

}
