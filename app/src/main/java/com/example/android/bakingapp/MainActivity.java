package com.example.android.bakingapp;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.beans.Steps;
import com.example.android.bakingapp.custom.OnBackPressedListener;
import com.example.android.bakingapp.custom.ShowOrHideBackButtonInActionBar;
import com.example.android.bakingapp.recepielist.FragmentRecipeList;
import com.example.android.bakingapp.recipedetails.FragmentRecipeDetails;
import com.example.android.bakingapp.recipedetails.FragmentRecipeSteps;
import com.example.android.bakingapp.testing.RecipesIdlingResource;


public class MainActivity extends AppCompatActivity implements FragmentRecipeList.DataPassListener, FragmentRecipeDetails.DataPassToStepsListener, ShowOrHideBackButtonInActionBar,FragmentRecipeDetails.DataPassToStepsListenerNew{
    protected OnBackPressedListener onBackPressedListener;
    private boolean isTwoPane;
    private changeFragment mChangeFragment;
    @Nullable
    private RecipesIdlingResource mRecipesIdlingResource;

    @VisibleForTesting
    public IdlingResource getIdlingResources(){

        if(null == mRecipesIdlingResource){
            mRecipesIdlingResource= new RecipesIdlingResource();
        }
        return mRecipesIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTwoPane = getResources().getBoolean(R.bool.is_two_pane);
        getIdlingResources();
        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.container, new FragmentRecipeList())
                    .addToBackStack("f1")
                    .commit();
        }

    }



    public void setChangeFragment(changeFragment changeFragmentCall) {
        this.mChangeFragment = changeFragmentCall;
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void passData(Recipe recipe) {

        if (isTwoPane) {
            MasterDetailsFragment fragmentRecipeDetails = new MasterDetailsFragment();
            Bundle b = new Bundle();
            b.putParcelable("recipe", recipe);
            fragmentRecipeDetails.setArguments(b);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragmentRecipeDetails)
                    .addToBackStack("f4")
                    .commit();
        } else {
            FragmentRecipeDetails fragmentRecipeDetails = new FragmentRecipeDetails();
            Bundle b = new Bundle();
            b.putParcelable("recipe", recipe);
            fragmentRecipeDetails.setArguments(b);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragmentRecipeDetails)
                    .addToBackStack("f2")
                    .commit();
        }
    }

    @Override
    public void showOrHide(Boolean b) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(b);
    }

    @Override
    public void passDataToStepsN(Steps steps, int position, Recipe recipe) {
        FragmentRecipeSteps fragmentRecipeSteps = new FragmentRecipeSteps();
        Bundle b = new Bundle();
        b.putSerializable("steps", steps);
        b.putInt("position",position);
        b.putParcelable("recipe", recipe);
        fragmentRecipeSteps.setArguments(b);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragmentRecipeSteps)
                .commit();
    }


    interface changeFragment {
        void changeFragmentInStepsFragment(Steps steps);
    }


    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void passDataToSteps(Steps steps,int position,Recipe recipe) {

        if (isTwoPane) {
            mChangeFragment.changeFragmentInStepsFragment(steps);

        } else {

            FragmentRecipeSteps fragmentRecipeSteps = new FragmentRecipeSteps();
            Bundle b = new Bundle();
            b.putSerializable("steps", steps);
            b.putInt("position",position);
            b.putParcelable("recipe", recipe);
            fragmentRecipeSteps.setArguments(b);


                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragmentRecipeSteps)
                        .addToBackStack("f3")
                        .commit();



        }


    }
}
