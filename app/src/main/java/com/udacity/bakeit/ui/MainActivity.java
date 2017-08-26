package com.udacity.bakeit.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.udacity.bakeit.R;
import com.udacity.bakeit.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private RecipeListFragment mRecipeListFragment;

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
        mRecipeListFragment = RecipeListFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, mRecipeListFragment);
        transaction.commit();
    }
}
