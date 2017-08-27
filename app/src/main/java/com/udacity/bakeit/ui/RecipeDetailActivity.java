package com.udacity.bakeit.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.udacity.bakeit.R;
import com.udacity.bakeit.base.BaseActivity;
import com.udacity.bakeit.dto.Recipe;
import com.udacity.bakeit.utils.IBundleKeys;

/**
 * Created by VijayaLakshmi.IN on 8/27/2017.
 */

public class RecipeDetailActivity extends BaseActivity {

    private Recipe mSelectedRecipe;
    private RecipeStepIngredientsFragment mRecipeStepIngredientsFragment;
    private RecipeStepDetailsFragment mRecipeStepDetailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_details);

        if (savedInstanceState != null) {
            return;
        }

        Bundle bundle = getIntent().getExtras();
        mSelectedRecipe = bundle.getParcelable(IBundleKeys.SELECTED_RECIPE);
        if (mSelectedRecipe == null) {
            new IllegalStateException("Please pass in the selected recipe");
        } else {
            setActivityTitle(mSelectedRecipe.getName());
        }

        addRecipeStepIngredientListFragment();

        if (getResources().getBoolean(R.bool.tablet_mode) && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            addRecipeStepDetailsFragment();
        }
    }

    private void addRecipeStepIngredientListFragment() {
        mRecipeStepIngredientsFragment = RecipeStepIngredientsFragment.newInstance(mSelectedRecipe);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.steps_ingredients_container, mRecipeStepIngredientsFragment);
        transaction.commit();
    }

    private void addRecipeStepDetailsFragment() {
        mRecipeStepDetailsFragment = RecipeStepDetailsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.recipe_step_details_container, mRecipeStepDetailsFragment);
        transaction.commit();
    }
}
