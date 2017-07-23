package com.example.android.bakingapp.custom;

import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.MasterDetailsFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.recepielist.FragmentRecipeList;

/**
 * Created by akshayshahane on 18/07/17.
 */

public class BaseBackPressedListener implements OnBackPressedListener {
    private final AppCompatActivity activity;

    public BaseBackPressedListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void doBack() {
        if (activity.getResources().getBoolean(R.bool.is_two_pane)){
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .remove(new MasterDetailsFragment())
                    .replace(R.id.container, new FragmentRecipeList())
                    .commit();

        }else {
            activity.getSupportFragmentManager().popBackStack();
        }
    }
}