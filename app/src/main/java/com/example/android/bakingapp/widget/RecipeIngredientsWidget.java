package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.utils.Utility;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

//

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int id : appWidgetIds) {

            updateWidgetListView(context, appWidgetManager, id);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public RemoteViews updateWidgetListView(Context context, AppWidgetManager manger,
                                            int appWidgetId) {

        //which layout to show on widget

        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.recipe_ingredients_widget);
        remoteViews.setTextViewText(R.id.tv_recipe_name_widget, Utility.loadSharedPreferencesRecipeTitle(context));

        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(R.id.listViewWidget,
                new Intent(context, IngredientsWidgetService.class));


        //setting an empty view in case of no data

        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);
        manger.updateAppWidget(appWidgetId, remoteViews);
        return remoteViews;
    }


}

