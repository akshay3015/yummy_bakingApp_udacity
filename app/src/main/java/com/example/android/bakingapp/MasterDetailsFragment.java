package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.beans.Steps;
import com.example.android.bakingapp.custom.BaseBackPressedListener;
import com.example.android.bakingapp.custom.ShowOrHideBackButtonInActionBar;
import com.example.android.bakingapp.recipedetails.FragmentRecipeDetails;
import com.example.android.bakingapp.recipedetails.FragmentRecipeSteps;

/**
 * Created by akshayshahane on 20/07/17.
 */

public class MasterDetailsFragment extends Fragment implements MainActivity.changeFragment {
    private Recipe mRecipe;
    private ShowOrHideBackButtonInActionBar callBackActionbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_y_, container, false);
        callBackActionbar.showOrHide(true);
        ((MainActivity) getActivity()).setChangeFragment(this);

        Bundle args = getArguments();
        if (null != args && args.containsKey("recipe")) {
            mRecipe = args.getParcelable("recipe");

            if (savedInstanceState == null) {


                FragmentRecipeDetails fragmentRecipeDetails = new FragmentRecipeDetails();
                Bundle b = new Bundle();
                b.putParcelable("recipe", mRecipe);
                fragmentRecipeDetails.setArguments(b);
                getChildFragmentManager()
                        .beginTransaction()
                        .add(R.id.listcontainer, fragmentRecipeDetails)
                        .addToBackStack(null)
                        .commit();


            }
        }

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Make sure that container activity implement the callback interface
        try {
            callBackActionbar = (ShowOrHideBackButtonInActionBar) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isInTwoPane", true);
    }


    @Override
    public void changeFragmentInStepsFragment(Steps steps) {
        FragmentRecipeSteps fragmentRecipeSteps = new FragmentRecipeSteps();
        Bundle b = new Bundle();
        b.putSerializable("steps", steps);
        fragmentRecipeSteps.setArguments(b);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.detailscontainer, fragmentRecipeSteps)
                .addToBackStack(null)
                .commit();
    }
}
