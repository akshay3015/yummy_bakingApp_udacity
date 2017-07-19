package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.recipedetails.FragmentRecipeDetails;
import com.example.android.bakingapp.recipedetails.FragmentRecipeSteps;

/**
 * Created by akshayshahane on 20/07/17.
 */

public class MasterDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.listcontainer,new FragmentRecipeSteps())
                .commit();


        getFragmentManager()
                .beginTransaction()
                .add(R.id.detailscontainer,new FragmentRecipeDetails())
                .commit();

        return view;

    }
}
