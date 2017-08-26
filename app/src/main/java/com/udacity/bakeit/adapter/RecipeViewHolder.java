package com.udacity.bakeit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.bakeit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_name)
    TextView mRecipeName;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
