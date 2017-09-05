package com.udacity.bakeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.bakeit.R;
import com.udacity.bakeit.adapter.RecipeListAdapter;
import com.udacity.bakeit.base.BaseFragment;
import com.udacity.bakeit.dto.Recipe;
import com.udacity.bakeit.io.IOManager;
import com.udacity.bakeit.listeners.IRecipeListItemClickListener;
import com.udacity.bakeit.utils.DialogUtility;
import com.udacity.bakeit.utils.IBundleKeys;
import com.udacity.bakeit.utils.NetworkUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.bakeit.utils.IBundleKeys.SELECTED_RECIPE;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 */
public class RecipeListFragment extends BaseFragment implements IRecipeListItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindInt(R.integer.grid_coulmn_count)
    int mGridColumnCount;

    private ArrayList<Recipe> mRecipeList = new ArrayList<>();
    private RecipeListAdapter mRecyclerListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        restoreDataFromBundle(savedInstanceState);

        if (getActivity() != null) {
            initViews();

            if (mRecipeList == null || mRecipeList.isEmpty()) {
                if (NetworkUtility.isInternetConnected(getActivity())) {
                    showProgressBar();
                    IOManager.requestRecipeList(new Callback<List<Recipe>>() {
                        @Override
                        public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                            hideProgressBar();
                            mRecipeList.clear();
                            mRecipeList.addAll(response.body());
                            mRecyclerListAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<Recipe>> call, Throwable t) {
                            hideProgressBar();
                            DialogUtility.showToast(getActivity(), getString(R.string.generic_error));
                        }
                    });
                } else {
                    hideProgressBar();
                    DialogUtility.showToast(getActivity(), getString(R.string.no_internet_connection));
                }
            } else {
                hideProgressBar();
            }
        }
    }

    /**
     * Displays the progress bar
     */
    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the progress bar
     */
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Restores the stored data from previous bundle
     *
     * @param savedInstanceState
     */
    private void restoreDataFromBundle(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<Recipe> recipeList = savedInstanceState.getParcelableArrayList(IBundleKeys.RECIPE_LIST);
            mRecipeList.clear();
            if (recipeList != null && !recipeList.isEmpty()) {
                mRecipeList.addAll(recipeList);
            }
        }
    }

    /**
     * Initialises the views
     */
    private void initViews() {
        mRecyclerListAdapter = new RecipeListAdapter(getActivity(), this, mRecipeList);
        mRecyclerView.setAdapter(mRecyclerListAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mGridColumnCount));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.common_padding);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, getActivity()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(IBundleKeys.RECIPE_LIST, mRecipeList);
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SELECTED_RECIPE, mRecipeList.get(position));
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
