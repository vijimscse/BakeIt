package com.udacity.bakeit.widget;

import com.udacity.bakeit.dto.Ingredient;

import java.util.ArrayList;

/**
 * Created by VijayaLakshmi.IN on 9/4/2017.
 */

public class WidgetDataProvider {

    private static WidgetDataProvider sInstance = new WidgetDataProvider();

    private ArrayList<Ingredient> mIngredientList;

    private WidgetDataProvider() {

    }

    public static WidgetDataProvider getInstance() {
        return sInstance;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return mIngredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> mIngredientList) {
        this.mIngredientList = mIngredientList;
    }
}
