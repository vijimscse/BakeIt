package com.udacity.bakeit.ui;

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

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 */
public class RecipeListFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindInt(R.integer.grid_coulmn_count)
    int mGridColumnCount;

    private List<Recipe> mRecipeList = new ArrayList<>();
    private RecipeListAdapter mRecyclerListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            ArrayList<Recipe> movieList = savedInstanceState.getParcelableArrayList(IBundleKeys.RECIPE_LIST);
            mRecipeList.clear();
            if (movieList != null && !movieList.isEmpty()) {
                mRecipeList.addAll(movieList);
            }
        }

        if (getActivity() != null) {
            mRecyclerListAdapter = new RecipeListAdapter(getActivity(), mRecipeList);
            mRecyclerView.setAdapter(mRecyclerListAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mGridColumnCount));
            if (NetworkUtility.isInternetConnected(getActivity())) {
                mProgressBar.setVisibility(View.VISIBLE);
                IOManager.requestRecipeList(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        mProgressBar.setVisibility(View.GONE);
                        mRecipeList.addAll(response.body());
                        mRecyclerListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        mProgressBar.setVisibility(View.GONE);
                        DialogUtility.showToast(getActivity(), getString(R.string.generic_error));
                    }
                });
            } else {
                mProgressBar.setVisibility(View.GONE);
                DialogUtility.showToast(getActivity(), getString(R.string.no_internet_connection));
            }
        }
    }
}
