package com.example.android.bakingapp.custom;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
//        if (activity.getResources().getBoolean(R.bool.is_two_pane)){
//
//            Toast.makeText(activity, "2 pane"+activity.getSupportFragmentManager().getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
//
//            if (activity.getSupportFragmentManager().getBackStackEntryCount() <= 1){
//                activity.finish();
//                Toast.makeText(activity, "do back 2 pane", Toast.LENGTH_SHORT).show();
//            }else {
//
//                Toast.makeText(activity, "do back 2 pane transaction", Toast.LENGTH_SHORT).show();
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container, new FragmentRecipeList())
//                        .commit();
//
//            }
//
//        }else {
            if (activity.getSupportFragmentManager().getBackStackEntryCount() <= 1){
                activity.finish();
            }
            activity.getSupportFragmentManager().popBackStack();
        }
//    }
}