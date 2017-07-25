package com.example.android.bakingapp.recipedetails;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.beans.Steps;
import com.example.android.bakingapp.custom.BaseBackPressedListener;
import com.example.android.bakingapp.custom.ShowOrHideBackButtonInActionBar;
import com.example.android.bakingapp.custom.StatefulRecyclerView;
import com.example.android.bakingapp.utils.Utility;
import com.example.android.bakingapp.widget.RecipeIngredientsWidget;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by akshayshahane on 18/07/17.
 */

public class FragmentRecipeDetails extends Fragment implements RecipeDescriptionAdapter.ItemListener {

    @BindView(R.id.tv_ingredients)
    TextView mTvIngredients;
    Unbinder unbinder;
    private static final String TAG = "FragmentRecipeDetails";
    @BindView(R.id.rv_steps)
    StatefulRecyclerView mRvSteps;
    private RecipeDescriptionAdapter mAdapter;
    private Recipe mRecipe;
    private DataPassToStepsListener callBackSteps;
    private List<Steps> mStepsList;
    private ShowOrHideBackButtonInActionBar callBackActionbar;

    @Override
    public void onItemClick(Steps item) {

        callBackSteps.passDataToSteps(item);

    }

    public interface DataPassToStepsListener {
        void passDataToSteps(Steps steps);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_y, container, false);
        unbinder = ButterKnife.bind(this, view);

        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener((AppCompatActivity) getContext()));


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_widget) {

            Utility.saveSharedPreferencesRecipeList(getContext(), mRecipe.getIngredientsList());
            Utility.saveSharedPreferencesTitle(getContext(), mRecipe.getName());
            Intent intent = new Intent(getContext(), RecipeIngredientsWidget.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());

            int appWidgetIds[] = appWidgetManager
                    .getAppWidgetIds(new ComponentName(getContext(), RecipeIngredientsWidget.class));

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            getContext().sendBroadcast(intent);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listViewWidget);

            return true;
        }

        if (id== android.R.id.home)
        {
            getActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getContext().getResources().getBoolean(R.bool.is_two_pane)){
            callBackActionbar.showOrHide(false);

        }else {
            callBackActionbar.showOrHide(true);
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Make sure that container activity implement the callback interface
        try {
            callBackActionbar = (ShowOrHideBackButtonInActionBar) context;

            callBackSteps = (DataPassToStepsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataPassListener");
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (null != args && args.containsKey("recipe")) {
            mRecipe = args.getParcelable("recipe");
            if (null != mRecipe) {
                (getActivity()).setTitle(mRecipe.getName());
                mTvIngredients.setText(Utility.setIngredients(mRecipe));
                mStepsList = new ArrayList<>();
                mStepsList = mRecipe.getStepsList();
                mAdapter = new RecipeDescriptionAdapter(mStepsList, this);
                mRvSteps.setNestedScrollingEnabled(false);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRvSteps.setLayoutManager(mLayoutManager);
                mRvSteps.setItemAnimator(new DefaultItemAnimator());
                mRvSteps.setAdapter(mAdapter);


            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
