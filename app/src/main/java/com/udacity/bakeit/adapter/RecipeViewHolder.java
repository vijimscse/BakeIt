package com.udacity.bakeit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.bakeit.R;
import com.udacity.bakeit.listeners.IRecipeClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_name)
    TextView mRecipeName;

    private IRecipeClickListener mRecipeClickListener;

    public RecipeViewHolder(View itemView, IRecipeClickListener recipeClickListener) {
        super(itemView);

        mRecipeClickListener = recipeClickListener;

        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.recipe_card_view)
    void onRecipeClick() {
        mRecipeClickListener.onRecipeClick(getAdapterPosition());
    }
}
