package com.udacity.bakeit.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentTransaction;

import com.udacity.bakeit.R;
import com.udacity.bakeit.base.BaseActivity;
import com.udacity.bakeit.idlengresource.RecipeListIdlingResource;


/**
 * Created by VijayaLakshmi.IN on 9/5/2017.
 * Displays recipe list
 */
public class MainActivity extends BaseActivity {


    private RecipeListFragment recipeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }
        setActivityTitle(R.string.app_name);
        addRecipeListFragment();
    }

    private void addRecipeListFragment() {
        recipeListFragment = RecipeListFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, recipeListFragment);
        transaction.commit();
    }

    /**
     * Only called from test, creates and returns a new {@link RecipeListIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        return recipeListFragment.getIdlingResource();
    }
}
