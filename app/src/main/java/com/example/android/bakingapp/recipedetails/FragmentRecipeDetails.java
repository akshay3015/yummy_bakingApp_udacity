package com.example.android.bakingapp.recipedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Beans.Ingredients;
import com.example.android.bakingapp.Beans.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.custom.StatefulRecyclerView;

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
    @BindView(R.id.rv_recipe_steps)
    StatefulRecyclerView mRvRecipeSteps;
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

    }

    private void setIngredients() {

        List<Ingredients> ingredients = mRecipe.getIngredientsList();
        String strIngredients = "Ingredients";
        String fullList;

        for (Ingredients in : ingredients) {

          fullList=  strIngredients.concat("\u2022" + in.getIngredient()).concat("--" + in.getQuantity()).concat(" " + in.getMeasure() + "\n");
            mTvIngredients.setText(fullList);
            Log.d(TAG, "setIngredients: " + fullList);

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
