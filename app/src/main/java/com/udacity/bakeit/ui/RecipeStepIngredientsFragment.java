package com.udacity.bakeit.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakeit.R;
import com.udacity.bakeit.adapter.RecipeStepListAdapter;
import com.udacity.bakeit.base.BaseFragment;
import com.udacity.bakeit.dto.Recipe;
import com.udacity.bakeit.listeners.IRecipeStepFragmentListener;
import com.udacity.bakeit.listeners.IRecipeStepItemClickListener;
import com.udacity.bakeit.utils.IBundleKeys;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VijayaLakshmi.IN on 8/27/2017.
 */

public class RecipeStepIngredientsFragment extends BaseFragment implements IRecipeStepItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Recipe mSelectedRecipe;
    private IRecipeStepFragmentListener mRecipeStepClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        castToRecipeStepClickListener(context);
    }

    private void castToRecipeStepClickListener(Context context) {
        if (! (context instanceof IRecipeStepFragmentListener)) {
            throw new IllegalStateException(((FragmentActivity)context).getClass().getSimpleName() +  "must implement" +
                    IRecipeStepFragmentListener.class.getSimpleName());
        }
        mRecipeStepClickListener = (IRecipeStepFragmentListener) context;
    }

    public static RecipeStepIngredientsFragment newInstance(Recipe selectedRecipe) {
        RecipeStepIngredientsFragment recipeStepIngredientsFragment = new RecipeStepIngredientsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(IBundleKeys.SELECTED_RECIPE, selectedRecipe);
        recipeStepIngredientsFragment.setArguments(bundle);

        return recipeStepIngredientsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateUI(getArguments());
    }

    public void updateUI(Bundle bundle) {
        if (getActivity() != null && bundle != null) {
            mSelectedRecipe = bundle.getParcelable(IBundleKeys.SELECTED_RECIPE);
            if (mSelectedRecipe != null) {
                mRecyclerView.setAdapter(new RecipeStepListAdapter(getActivity(), this,
                        mSelectedRecipe.getSteps(), mSelectedRecipe.getIngredients()));
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                        layoutManager.getOrientation());
                mRecyclerView.addItemDecoration(dividerItemDecoration);
            }
        }
    }

    @Override
    public void onStepClick(int position) {
        mRecipeStepClickListener.onStepClick(position);
    }
}
