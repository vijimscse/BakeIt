package com.udacity.bakeit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.bakeit.R;
import com.udacity.bakeit.listeners.IRecipeStepItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 * View holder for recipe step
 */
public class RecipeStepViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.step_name)
    TextView mStepName;

    @BindView(R.id.step_image)
    public ImageView mStepImage;

    private IRecipeStepItemClickListener mRecipeClickListener;

    public RecipeStepViewHolder(View itemView, IRecipeStepItemClickListener recipeClickListener) {
        super(itemView);

        mRecipeClickListener = recipeClickListener;

        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.step_card_view)
    void onStepClick() {
        mRecipeClickListener.onStepClick(getAdapterPosition() - 1);
    }
}
