package com.udacity.bakeit.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.udacity.bakeit.R;
import com.udacity.bakeit.base.BaseActivity;
import com.udacity.bakeit.dto.Recipe;
import com.udacity.bakeit.dto.Step;
import com.udacity.bakeit.listeners.IRecipeStepFragmentListener;
import com.udacity.bakeit.utils.IBundleKeys;

/**
 * Created by VijayaLakshmi.IN on 8/27/2017.
 * Displays recipe details
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
        if (bundle != null) {
            mSelectedRecipe = bundle.getParcelable(IBundleKeys.SELECTED_RECIPE);
        }
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
            mRecipeStepDetailsFragment.updateStepDetails(mSelectedRecipe.getSteps().get(0), 0, false, (mSelectedRecipe.getSteps().size() - 1) > 1);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.steps_ingredients_container);
            if (savedInstanceState != null) {
                if (fragment == null) {
                    addRecipeStepIngredientListFragment();
                } else if (fragment instanceof RecipeStepDetailsFragment) {
                    mRecipeStepDetailsFragment = (RecipeStepDetailsFragment) fragment;
                }
                return;
            }
            if (findViewById(R.id.steps_ingredients_container) != null) {
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

    private void addRecipeStepDetailsFragment(int fragmentViewID, Step step,
                                              int currentPosition, boolean hasPrev, boolean hasNext) {
        mRecipeStepDetailsFragment = RecipeStepDetailsFragment.newInstance(step, currentPosition, hasPrev, hasNext);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentViewID, mRecipeStepDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStepClick(int position) {
        if (position >= 0) {
            if (getResources().getBoolean(R.bool.tablet_mode) &&
                    getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mRecipeStepDetailsFragment.updateStepDetails(mSelectedRecipe.getSteps().get(position),
                        position, position > 0, (position < (mSelectedRecipe.getSteps().size() - 1)));
            } else {
                addRecipeStepDetailsFragment(R.id.steps_ingredients_container,
                        mSelectedRecipe.getSteps().get(position),
                        position, position > 0, (position < (mSelectedRecipe.getSteps().size() - 1)));
            }
        }
    }

    @Override
    public void onNext(int currentPosition) {
        int nextPos = currentPosition + 1;
        if (nextPos < mSelectedRecipe.getSteps().size() && mRecipeStepDetailsFragment != null) {
            mRecipeStepDetailsFragment.updateStepDetails(mSelectedRecipe.getSteps().get(nextPos),
                    nextPos, nextPos > 0, (nextPos < (mSelectedRecipe.getSteps().size() - 1)));
            mRecipeStepDetailsFragment.preparePlayer();
        }
    }

    @Override
    public void onPrev(int currentPosition) {
        int prevPos = currentPosition - 1;
        if (prevPos >= 0 && mRecipeStepDetailsFragment != null) {
            mRecipeStepDetailsFragment.updateStepDetails(mSelectedRecipe.getSteps().get(prevPos),
                    prevPos, prevPos > 0, (prevPos < (mSelectedRecipe.getSteps().size() - 1)));
            mRecipeStepDetailsFragment.preparePlayer();
        }
    }

    @Override
    public void hideToolBar() {
        hideTitleBar();
    }

    @Override
    public void showToolBar() {
        showTitleBar();
    }
}
