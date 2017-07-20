package com.example.android.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.android.bakingapp.beans.Ingredients;
import com.example.android.bakingapp.beans.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshayshahane on 19/07/17.
 */

public class Utility {

    public static final String KEY_INGREDIENTS_WIDGET = "Ingredients";
    public static final String KEY_INGREDIENTS_WIDGET_JSON = "IngredientsJson";

    public static StringBuffer setIngredients(Recipe mRecipe) {


        List<Ingredients> ingredients = mRecipe.getIngredientsList();
        StringBuffer strIngredients = new StringBuffer();
        strIngredients.append("Ingredients \n  \n");
        int i = 0;


        for (Ingredients in : ingredients) {
            i++;
            strIngredients.append("\u2022" + in.getIngredient()).append("--" + in.getQuantity()).append(" " + in.getMeasure() + "\n \n ");

            if (i == ingredients.size() - 1) {
                return strIngredients;

            }


        }

        return strIngredients;
    }

    public static boolean isNetworkAvailble(Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public static void saveSharedPreferencesLogList(Context context, List<Ingredients> ingredientsList) {
        SharedPreferences mPrefs = context.getSharedPreferences(KEY_INGREDIENTS_WIDGET, context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ingredientsList);
        prefsEditor.putString(KEY_INGREDIENTS_WIDGET_JSON, json);
        prefsEditor.commit();
    }


    public static List<Ingredients> loadSharedPreferencesLogList(Context context) {
        List<Ingredients> ingredientsList;
        SharedPreferences mPrefs = context.getSharedPreferences(KEY_INGREDIENTS_WIDGET, context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(KEY_INGREDIENTS_WIDGET_JSON, "");
        if (json.isEmpty()) {
            ingredientsList = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<Ingredients>>() {
            }.getType();
            ingredientsList = gson.fromJson(json, type);
        }
        return ingredientsList;
    }
}
