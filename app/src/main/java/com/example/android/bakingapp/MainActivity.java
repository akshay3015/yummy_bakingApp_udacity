package com.example.android.bakingapp;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Beans.Recipe;
import com.example.android.bakingapp.recepie_list.FragmentRecepieList;
import com.example.android.bakingapp.recipedetails.FragmentRecipeDetails;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FragmentRecepieList.DataPassListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.container, new FragmentRecepieList())
                    .commit();
        }
    }

    @Override
    public void passData(Recipe recipe) {
        FragmentRecipeDetails fragmentRecipeDetails = new FragmentRecipeDetails();
        Bundle b = new Bundle();
        b.putParcelable("recipe",recipe);
        fragmentRecipeDetails.setArguments(b);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragmentRecipeDetails)
                .commit();
    }
}
