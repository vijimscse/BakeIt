package com.udacity.bakeit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.udacity.bakeit.R;
import com.udacity.bakeit.dto.Recipe;
import com.udacity.bakeit.listeners.IRecipeListItemClickListener;

import java.util.List;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListViewHolder> {
    private final Context mContext;
    private List<Recipe> mRecipeList;
    private IRecipeListItemClickListener mRecipeClickListener;

    public RecipeListAdapter(Context context, IRecipeListItemClickListener recipeClickListener, List<Recipe> recipeList) {
        mRecipeClickListener = recipeClickListener;
        mRecipeList = recipeList;
        mContext = context;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeListViewHolder(itemView, mRecipeClickListener);
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);

        holder.mRecipeName.setText(recipe.getName());
        final String image = recipe.getImage();
        if (!TextUtils.isEmpty(image)) {
            Picasso.with(mContext)
                    .load(image)
                    .placeholder(R.drawable.recipe)
                    .error(R.drawable.recipe)
                    .into(holder.mRecipeImage);
        } else {
            holder.mRecipeImage.setImageResource(R.drawable.recipe);
        }
    }

    @Override
    public int getItemCount() {
        return mRecipeList != null ? mRecipeList.size() : 0;
    }
}
