package com.example.android.bakingapp.recepielist;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.BuildConfig;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.custom.BaseBackPressedListener;
import com.example.android.bakingapp.custom.ShowOrHideBackButtonInActionBar;
import com.example.android.bakingapp.custom.StatefulRecyclerView;
import com.example.android.bakingapp.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by akshayshahane on 16/07/17.
 */

public class FragmentRecipeList extends Fragment implements RecipesListContract.View, RecipeAdapter.ItemListener {

    @BindView(R.id.pb)
    ProgressBar mPb;
    @BindView(R.id.rv_fragment_recipes)
    StatefulRecyclerView mRvFragmentRecipes;
    Unbinder unbinder;
    private RecipesListContract.Presenter recipeListPresenter;
    private RecipePresenter mRecepePresenter;
    private RecipeAdapter mAdapter;
    private static String RECIPE_SAVED_INSTANCE_KEY = "recipesList";
    private List<Recipe> mRecipeList;
    private DataPassListener callBack;
    private ShowOrHideBackButtonInActionBar callBackActionbar;


    public interface DataPassListener {
        void passData(Recipe recipe);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        (getActivity()).setTitle(R.string.recipeList);
        callBackActionbar.showOrHide(false);
        mRecepePresenter = new RecipePresenter(this);
        mRecipeList = new ArrayList<>();
        if (null != savedInstanceState) {

            mRecipeList = savedInstanceState.getParcelableArrayList(RECIPE_SAVED_INSTANCE_KEY);


        } else {
            mRecipeList.clear();
            if (Utility.isNetworkAvailble(getContext())) {
                mRecepePresenter.fetchRecipesFromServer();
            } else {
                Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        }

        mAdapter = new RecipeAdapter(mRecipeList, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), getContext().getResources().getInteger(R.integer.columns));
        mRvFragmentRecipes.setLayoutManager(mLayoutManager);
        mRvFragmentRecipes.setItemAnimator(new DefaultItemAnimator());
        mRvFragmentRecipes.setAdapter(mAdapter);
        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener((AppCompatActivity) getContext()));


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Make sure that container activity implement the callback interface
        try {
            callBack = (DataPassListener) context;
            callBackActionbar = (ShowOrHideBackButtonInActionBar) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Comment to fix the crash on tablets when back pressed in master flow fragment
//        unbinder.unbind();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_SAVED_INSTANCE_KEY, (ArrayList<? extends Parcelable>) mRecipeList);
    }


    @Override
    public void setPresenter(RecipesListContract.Presenter presenter) {
        this.recipeListPresenter = presenter;
    }

    @Override
    public void showRecipesList(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        mAdapter = new RecipeAdapter(mRecipeList, this);
        if (null != mRvFragmentRecipes) {
            mRvFragmentRecipes.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
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
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dummy, menu);
    }


    @Override
    public void onItemClick(Recipe item) {

        callBack.passData(item);


    }
}
