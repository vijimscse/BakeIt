package com.udacity.bakeit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakeit.R;
import com.udacity.bakeit.dto.Recipe;
import com.udacity.bakeit.listeners.IRecipeClickListener;

import java.util.List;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private Context mContext;
    private List<Recipe> mRecipeList;
    private IRecipeClickListener mRecipeClickListener;

    public RecipeListAdapter(Context context, IRecipeClickListener recipeClickListener, List<Recipe> recipeList) {
        mContext = context;
        mRecipeClickListener = recipeClickListener;
        mRecipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeViewHolder(itemView, mRecipeClickListener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);

        holder.mRecipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (mRecipeList != null) {
            count = mRecipeList.size();
        }

        return count;
    }
}
