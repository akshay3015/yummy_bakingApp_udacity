package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.android.bakingapp.beans.Ingredients;
import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.beans.Steps;
import com.example.android.bakingapp.recipedetails.FragmentRecipeDetails;
import com.example.android.bakingapp.testing.TestActivity;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshayshahane on 25/07/17.
 */

public class FragmentTestRule<F extends Fragment> extends ActivityTestRule<TestActivity> {

    private final Class<F> mFragmentClass;
    private F mFragment;

    public FragmentTestRule(final Class<F> fragmentClass) {
        super(TestActivity.class, true, false);
        mFragmentClass = fragmentClass;
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Instantiate and insert the fragment into the container layout
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();

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

                    if (getActivity().getResources().getBoolean(R.bool.is_two_pane)) {
                        MasterDetailsFragment fragmentRecipeDetails = new MasterDetailsFragment();
                        Bundle b = new Bundle();
                        b.putParcelable("recipe", mrecipe);
                        fragmentRecipeDetails.setArguments(b);
                        b.putBoolean("isTest", true);
                        transaction.replace(R.id.container, fragmentRecipeDetails);
                        transaction.commit();
                    } else {
                        FragmentRecipeDetails fragmentRecipeDetails = new FragmentRecipeDetails();
                        Bundle b = new Bundle();
                        b.putParcelable("recipe", mrecipe);
                        b.putBoolean("isTest", true);
                        fragmentRecipeDetails.setArguments(b);
                        transaction.replace(R.id.container, fragmentRecipeDetails);
                        transaction.commit();
                    }


                    } catch(Exception e){
                        Assert.fail(String.format("%s: Could not insert %s into TestActivity: %s",
                                getClass().getSimpleName(),
                                mFragmentClass.getSimpleName(),
                                e.getMessage()));
                    }


                }

        });


    }
    public F getFragment(){
        return mFragment;
    }
}

