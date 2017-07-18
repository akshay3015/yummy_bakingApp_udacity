package com.example.android.bakingapp.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by akshayshahane on 16/07/17.
 */

 public class Ingredients  implements Serializable{

    @SerializedName("quantity")
    private  float quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
