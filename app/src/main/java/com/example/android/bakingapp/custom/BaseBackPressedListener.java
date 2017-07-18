package com.example.android.bakingapp.custom;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

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
        activity.getSupportFragmentManager().popBackStack();
    }
}