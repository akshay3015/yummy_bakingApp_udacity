package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.beans.Steps;
import com.example.android.bakingapp.custom.OnBackPressedListener;
import com.example.android.bakingapp.recepielist.FragmentRecepieList;
import com.example.android.bakingapp.recipedetails.FragmentRecipeDetails;
import com.example.android.bakingapp.recipedetails.FragmentRecipeSteps;


public class MainActivity extends AppCompatActivity implements FragmentRecepieList.DataPassListener ,FragmentRecipeDetails.DataPassToStepsListener{
    protected OnBackPressedListener onBackPressedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.container, new FragmentRecepieList())
                    .addToBackStack("f1")
                    .commit();
        }
    }
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
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
                .addToBackStack("f2")
                .commit();
    }


    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }


    @Override
    public void passDataToSteps(Steps steps) {
        FragmentRecipeSteps fragmentRecipeSteps = new FragmentRecipeSteps();
        Bundle b = new Bundle();
        b.putSerializable("recipe",steps);
        fragmentRecipeSteps.setArguments(b);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragmentRecipeSteps)
                .addToBackStack("f3")
                .commit();

    }
}
