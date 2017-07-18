package com.example.android.bakingapp.recepielist;

import android.support.annotation.NonNull;
import android.util.Log;



import com.example.android.bakingapp.beans.Recipe;

import com.example.android.bakingapp.BuildConfig;
import com.example.android.bakingapp.data.ApiInterface;
import com.example.android.bakingapp.data.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akshayshahane on 16/07/17.
 */

public class RecepiePresenter implements RecipesListContract.Presenter {
    private static final String TAG = "RecepiePresenter";
    private RecipesListContract.View mView;
    List<Recipe> recipeList;

    public RecepiePresenter(@NonNull RecipesListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        recipeList = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void fetchRecipesFromServre() {
        mView.showProgressBar();
        recipeList.clear();

        ApiInterface api = RetrofitClient.getClient().create(ApiInterface.class);
        Call<List<Recipe>> call = api.fetchRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {


                recipeList = response.body();
                if (recipeList.size() !=0 && null != recipeList){
                    mView.showRecipesList(recipeList);
                    mView.hidProgressBar();
                }

                if (BuildConfig.DEBUG) {

                    Log.d(TAG, "onResponse: " + recipeList.size());
                    Log.d(TAG, "onResponse: " + recipeList.get(0).getName());
                    Log.d(TAG, "onResponse: " + response.body().size());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                mView.showError(t.getMessage());
                mView.hidProgressBar();
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onItemSelected(int position) {

    }
}
