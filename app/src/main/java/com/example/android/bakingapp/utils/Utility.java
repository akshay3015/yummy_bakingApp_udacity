package com.example.android.bakingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.android.bakingapp.beans.Ingredients;
import com.example.android.bakingapp.beans.Recipe;

import java.util.List;

/**
 * Created by akshayshahane on 19/07/17.
 */

public class Utility {


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

    public static boolean isNetworkAvailble(Context ctx){
        ConnectivityManager cm =
                (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
