package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.recepielist.FragmentRecepieList;
import com.example.android.bakingapp.recipedetails.FragmentRecipeDetails;


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

    public  void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
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
