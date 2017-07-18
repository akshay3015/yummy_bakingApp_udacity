package com.example.android.bakingapp.recipedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.beans.Ingredients;
import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by akshayshahane on 18/07/17.
 */

public class FragmentRecipeDetails extends Fragment {

    @BindView(R.id.tv_ingredients)
    TextView mTvIngredients;

    Unbinder unbinder;
    private static final String TAG = "FragmentRecipeDetails";
    private Recipe mRecipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ( getActivity()).setTitle(R.string.ingredients);

    }

    private void setIngredients() {

        List<Ingredients> ingredients = mRecipe.getIngredientsList();
        StringBuffer strIngredients = new StringBuffer();
        strIngredients.append("Ingredients \n  \n");
        int i = 0;


        for (Ingredients in : ingredients) {
            i++;
            strIngredients.append("\u2022" + in.getIngredient()).append("--" + in.getQuantity()).append(" " + in.getMeasure() + "\n \n ");

            if (i == ingredients.size()-1) {
                mTvIngredients.setText(strIngredients);
            }
            Log.d(TAG, "setIngredients: " + strIngredients);

        }


    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (null != args && args.containsKey("recipe")) {
            mRecipe = args.getParcelable("recipe");
            if (null != mRecipe) {
                setIngredients();
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
