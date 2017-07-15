package com.example.android.bakingapp.Beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshayshahane on 16/07/17.
 */

public class Recipe {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("servings")
    private String servings;

    @SerializedName("image")
    private String image;

    @SerializedName("ingredients")
    private List<Ingredients> mIngredientsList = new ArrayList<>();

    @SerializedName("steps")
    private  List<Steps> mStepsList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredients> getIngredientsList() {
        return mIngredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        mIngredientsList = ingredientsList;
    }

    public List<Steps> getStepsList() {
        return mStepsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        mStepsList = stepsList;
    }
}
