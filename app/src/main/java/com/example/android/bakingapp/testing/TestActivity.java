package com.example.android.bakingapp.testing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.beans.Ingredients;
import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.beans.Steps;
import com.example.android.bakingapp.custom.ShowOrHideBackButtonInActionBar;
import com.example.android.bakingapp.recepielist.FragmentRecipeList;
import com.example.android.bakingapp.recipedetails.FragmentRecipeDetails;
import com.example.android.bakingapp.recipedetails.FragmentRecipeSteps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshayshahane on 25/07/17.
 */

@VisibleForTesting
public class TestActivity extends AppCompatActivity implements FragmentRecipeList.DataPassListener, FragmentRecipeDetails.DataPassToStepsListener,ShowOrHideBackButtonInActionBar {
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.container);
        setContentView(frameLayout);


    }

    @Override
    public void passData(Recipe recipe) {
        Recipe mrecipe = new Recipe();
        mrecipe.setId(1);
        mrecipe.setName("Nutella Pie");
        List<Ingredients> ingredientsList = new ArrayList<>();

        Ingredients mIn = new Ingredients();
        mIn.setIngredient("Graham Cracker crumbs");
        mIn.setQuantity(2);
        mIn.setMeasure("cups");
        ingredientsList.add(mIn);
        mrecipe.setIngredientsList(ingredientsList);


        List<Steps> mListSteps = new ArrayList<>();
        Steps steps = new Steps();
        steps.setId(0);
        steps.setShortDescription("Recipe Introduction");
        steps.setDescription("Recipe Introduction");
        steps.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
        steps.setThumbnailURL("");
        mListSteps.add(steps);

        mrecipe.setStepsList(mListSteps);

        mrecipe.setServings("8");

        mrecipe.setImage("");

        FragmentRecipeDetails fragmentRecipeDetails = new FragmentRecipeDetails();
        Bundle b = new Bundle();
        b.putParcelable("recipe", mrecipe);
        fragmentRecipeDetails.setArguments(b);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragmentRecipeDetails)
                .addToBackStack("f2")
                .commit();


    }

    @Override
    public void passDataToSteps(Steps steps,int i ,Recipe recipe) {

        Steps steps1 = new Steps();
        steps.setId(0);
        steps.setShortDescription("Recipe Introduction");
        steps.setDescription("Recipe Introduction");
        steps.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
        steps.setThumbnailURL("");

        FragmentRecipeSteps fragmentRecipeSteps = new FragmentRecipeSteps();
        Bundle b = new Bundle();
        b.putSerializable("steps", steps1);
        fragmentRecipeSteps.setArguments(b);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragmentRecipeSteps)
                .addToBackStack("f3")
                .commit();
    }

    @Override
    public void showOrHide(Boolean b) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(b);

    }
}