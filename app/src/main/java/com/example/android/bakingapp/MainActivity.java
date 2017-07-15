package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Beans.Recipe;
import com.example.android.bakingapp.recepie_list.FragmentRecepieList;
import com.example.android.bakingapp.recepie_list.RecepiePresenter;
import com.example.android.bakingapp.recepie_list.RecipesListContract;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.container,new FragmentRecepieList())
                .commit();
    }

}
