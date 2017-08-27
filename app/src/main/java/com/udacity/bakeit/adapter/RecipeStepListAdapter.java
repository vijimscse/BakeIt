package com.udacity.bakeit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakeit.R;
import com.udacity.bakeit.dto.Ingredient;
import com.udacity.bakeit.dto.Step;
import com.udacity.bakeit.listeners.IRecipeStepItemClickListener;

import java.util.List;

/**
 * Created by VijayaLakshmi.IN on 8/27/2017.
 */

public class RecipeStepListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Ingredient> mIngredientList;
    private List<Step> mStepList;
    private IRecipeStepItemClickListener mRecipeStepClickListener;

    private static final int ITEM_INGREDIENT = 101;
    private static final int ITEM_STEP = 100;

    public RecipeStepListAdapter(Context context, IRecipeStepItemClickListener recipeClickListener,
                                 List<Step> stepList, List<Ingredient> ingredientList) {
        mContext = context;
        mRecipeStepClickListener = recipeClickListener;
        mStepList = stepList;
        mIngredientList = ingredientList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_STEP) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_item, parent, false);

            return new RecipeStepViewHolder(itemView, mRecipeStepClickListener);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_list_item, parent, false);

            return new RecipeIngredientViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            RecipeIngredientViewHolder ingredientViewHolder = (RecipeIngredientViewHolder) holder;

            StringBuilder ingredientSB = new StringBuilder();
            for (Ingredient ingredient : mIngredientList) {
                ingredientSB.append(ingredient.getIngredient())
                        .append("\t")
                        .append(ingredient.getQuantity())
                        .append(" ")
                        .append(ingredient.getMeasure())
                        .append("\n");
            }

            ingredientViewHolder.mIngredientName.setText(ingredientSB.toString());
        } else {
            position = position - 1;
            RecipeStepViewHolder stepViewHolder = (RecipeStepViewHolder) holder;
            Step step = mStepList.get(position);

            stepViewHolder.mStepName.setText(step.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (mStepList != null) {
            count = mStepList.size();
        }

        if (mIngredientList != null && !mIngredientList.isEmpty()) {
            count = count + 1;
        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = ITEM_STEP;

        if (position == 0 && mIngredientList != null && !mIngredientList.isEmpty()) {
            itemType = ITEM_INGREDIENT;
        }

        return itemType;
    }
}
