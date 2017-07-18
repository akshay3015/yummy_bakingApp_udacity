package com.example.android.bakingapp.recepielist;

import com.example.android.bakingapp.BasePresenter;
import com.example.android.bakingapp.BaseView;
import com.example.android.bakingapp.beans.Recipe;

import java.util.List;

/**
 * Created by akshayshahane on 16/07/17.
 */

public interface RecipesListContract {

    interface View extends BaseView<Presenter> {

        void showRecipesList(List<Recipe> recipeList);

        void showProgressBar();

        void hidProgressBar();

        void showError(String errorMsg);

        void showRecipeDetailsUI(int recipeId);


    }


    interface Presenter extends BasePresenter {

        void fetchRecipesFromServre();
        void onItemSelected(int position);


    }


}
