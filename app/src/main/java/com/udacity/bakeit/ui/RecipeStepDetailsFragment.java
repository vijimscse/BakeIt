package com.udacity.bakeit.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakeit.R;
import com.udacity.bakeit.base.BaseFragment;

/**
 * Created by VijayaLakshmi.IN on 8/27/2017.
 */

public class RecipeStepDetailsFragment extends BaseFragment {

    public static RecipeStepDetailsFragment newInstance() {
        return new RecipeStepDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        return view;
    }

    public void updateStepDetails() {

    }
}
