package com.example.android.bakingapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshayshahane on 16/07/17.
 */

public class Recipe implements Parcelable {
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

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readString();
        image = in.readString();
        mIngredientsList = (ArrayList<Ingredients>) in.readSerializable();
        mStepsList = (ArrayList<Steps>) in.readSerializable();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(servings);
        parcel.writeString(image);
        parcel.writeSerializable((Serializable) mIngredientsList);
        parcel.writeInt(id);
        parcel.writeSerializable((Serializable) mStepsList);
    }
}
