package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.android.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

//

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Toast.makeText(context, "on update widget Provider", Toast.LENGTH_SHORT).show();
        for (int id : appWidgetIds) {
            Toast.makeText(context, "on update widget Provider  id > 0", Toast.LENGTH_SHORT).show();

            updateWidgetListView(context, appWidgetManager, id);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public RemoteViews updateWidgetListView(Context context, AppWidgetManager manger,
                                            int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.recipe_ingredients_widget);


        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(R.id.listViewWidget,
                new Intent(context, IngredientsWidgetService.class));


        //setting an empty view in case of no data

        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);
        manger.updateAppWidget(appWidgetId, remoteViews);
        return remoteViews;
    }


}

