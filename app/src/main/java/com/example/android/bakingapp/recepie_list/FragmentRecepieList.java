package com.example.android.bakingapp.recepie_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.bakingapp.Beans.Recipe;
import com.example.android.bakingapp.BuildConfig;
import com.example.android.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by akshayshahane on 16/07/17.
 */

public class FragmentRecepieList extends Fragment implements RecipesListContract.View {

    @BindView(R.id.pb)
    ProgressBar mPb;
    @BindView(R.id.rv_fragment_recipes)
    RecyclerView mRvFragmentRecipes;
    Unbinder unbinder;
    private RecipesListContract.Presenter recipeListPresenter;
    private RecepiePresenter mRecepiePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecepiePresenter = new RecepiePresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecepiePresenter.fetchRecipesFromServre();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(RecipesListContract.Presenter presenter) {
        this.recipeListPresenter = presenter;
    }

    @Override
    public void showRecipesList(List<Recipe> recipeList) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(getContext(), "Size of data" + recipeList.size(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showProgressBar() {
        mPb.setVisibility(View.VISIBLE);


    }

    @Override
    public void hidProgressBar() {
        mPb.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMsg) {
        if (BuildConfig.DEBUG){
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showRecipeDetailsUI(int recipeId) {

    }
}
