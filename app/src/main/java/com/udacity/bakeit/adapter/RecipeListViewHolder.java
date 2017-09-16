package com.udacity.bakeit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.bakeit.R;
import com.udacity.bakeit.listeners.IRecipeListItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 * View holder for recipe list
 */

public class RecipeListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_name)
    TextView mRecipeName;

    @BindView(R.id.recipe_image)
    ImageView mRecipeImage;

    private IRecipeListItemClickListener mRecipeClickListener;

    public RecipeListViewHolder(View itemView, IRecipeListItemClickListener recipeClickListener) {
        super(itemView);

        mRecipeClickListener = recipeClickListener;

        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.recipe_card_view)
    void onRecipeClick() {
        mRecipeClickListener.onRecipeClick(getAdapterPosition());
    }
}
