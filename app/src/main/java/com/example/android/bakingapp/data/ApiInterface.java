package com.example.android.bakingapp.data;

import com.example.android.bakingapp.Beans.Recipe;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by akshayshahane on 16/07/17.
 */

public interface ApiInterface {
    @GET("baking.json")
    Call<List<Recipe>> fetchRecipes();
}
