package com.example.android.bakingapp.widget;


import android.content.Intent;
import android.widget.RemoteViewsService;
import android.widget.Toast;


/**
 * Created by akshayshahane on 21/07/17.
 */

public class IngredientsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return (new IngredientsListProvider(this.getApplicationContext(), intent));

    }


}
