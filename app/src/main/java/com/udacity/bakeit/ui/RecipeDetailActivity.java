package com.udacity.bakeit.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.udacity.bakeit.R;
import com.udacity.bakeit.base.BaseActivity;
import com.udacity.bakeit.dto.Recipe;
import com.udacity.bakeit.dto.Step;
import com.udacity.bakeit.listeners.IRecipeStepFragmentListener;
import com.udacity.bakeit.utils.IBundleKeys;

/**
 * Created by VijayaLakshmi.IN on 8/27/2017.
 */

public class RecipeDetailActivity extends BaseActivity implements IRecipeStepFragmentListener {

    private Recipe mSelectedRecipe;
    private RecipeStepIngredientsFragment mRecipeStepIngredientsFragment;
    private RecipeStepDetailsFragment mRecipeStepDetailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_details);

        Bundle bundle = getIntent().getExtras();
        mSelectedRecipe = bundle.getParcelable(IBundleKeys.SELECTED_RECIPE);
        if (mSelectedRecipe == null) {
            new IllegalStateException("Please pass in the selected recipe");
        } else {
            setActivityTitle(mSelectedRecipe.getName());
        }

        if (getResources().getBoolean(R.bool.tablet_mode) &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Bundle stepListBundle = new Bundle();
            stepListBundle.putParcelable(IBundleKeys.SELECTED_RECIPE, mSelectedRecipe);
            mRecipeStepIngredientsFragment = (RecipeStepIngredientsFragment) getSupportFragmentManager().
                    findFragmentById(R.id.steps_ingredients_fragment);
            mRecipeStepIngredientsFragment.updateUI(stepListBundle);

            mRecipeStepDetailsFragment = (RecipeStepDetailsFragment) getSupportFragmentManager().
                    findFragmentById(R.id.recipe_step_details_fragment);
        } else {
            if (findViewById(R.id.steps_ingredients_container) != null) {
                if (savedInstanceState != null) {
                    return;
                }
                addRecipeStepIngredientListFragment();
            }
        }
    }

    private void addRecipeStepIngredientListFragment() {
        mRecipeStepIngredientsFragment = RecipeStepIngredientsFragment.newInstance(mSelectedRecipe);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.steps_ingredients_container, mRecipeStepIngredientsFragment);
        transaction.commit();
    }

    private void addRecipeStepDetailsFragment(int fragmentViewID, Step step) {
        mRecipeStepDetailsFragment = RecipeStepDetailsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentViewID, mRecipeStepDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStepClick(Step step) {
        if (step != null) {
            if (getResources().getBoolean(R.bool.tablet_mode) &&
                    getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mRecipeStepDetailsFragment.updateStepDetails();
            } else {
                addRecipeStepDetailsFragment(R.id.steps_ingredients_container, step);
            }
        }
    }
}
