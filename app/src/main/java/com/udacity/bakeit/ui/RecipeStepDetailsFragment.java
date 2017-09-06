package com.udacity.bakeit.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.udacity.bakeit.R;
import com.udacity.bakeit.base.BaseFragment;
import com.udacity.bakeit.dto.Step;
import com.udacity.bakeit.listeners.IRecipeStepFragmentListener;
import com.udacity.bakeit.utils.NetworkUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by VijayaLakshmi.IN on 8/27/2017.
 */

public class RecipeStepDetailsFragment extends BaseFragment {

    private static final String KEY_STEP = "key_step";
    private static final String KEY_CURRENT_POSITION = "key_current_pos";
    private static final String KEY_HAS_NEXT = "key_has_next";
    private static final String KEY_HAS_PREV = "key_has_prev";

    @BindView(R.id.player_view)
    SimpleExoPlayerView mSimpleExoPlayerView;

    @BindView(R.id.no_video_content)
    TextView mNoVideoContentView;

    @Nullable
    @BindView(R.id.recipe_step_instructions)
    TextView mStepInstructionsView;

    @Nullable
    @BindView(R.id.ingredient_prev)
    Button mPrev;

    @Nullable
    @BindView(R.id.ingredient_next)
    Button mNext;

    private int mCurrentStepPosition;
    private boolean mHasPrev;
    private boolean mHasNext;
    private Step mStep;
    private IRecipeStepFragmentListener mRecipeStepClickListener;

    public static RecipeStepDetailsFragment newInstance(Step step, int currentPosition, boolean hasPrev, boolean hasNext) {
        final RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_STEP, step);
        bundle.putInt(KEY_CURRENT_POSITION, currentPosition);
        bundle.putBoolean(KEY_HAS_PREV, hasPrev);
        bundle.putBoolean(KEY_HAS_NEXT, hasNext);
        recipeStepDetailsFragment.setArguments(bundle);
        return recipeStepDetailsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        castToRecipeStepClickListener(context);
    }

    private void castToRecipeStepClickListener(Context context) {
        if (!(context instanceof IRecipeStepFragmentListener)) {
            throw new IllegalStateException(((FragmentActivity) context).getClass().getSimpleName() + "must implement" +
                    IRecipeStepFragmentListener.class.getSimpleName());
        }
        mRecipeStepClickListener = (IRecipeStepFragmentListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getArguments();

        if (bundle != null) {
            mStep = bundle.getParcelable(KEY_STEP);
            mCurrentStepPosition = bundle.getInt(KEY_CURRENT_POSITION);
            mHasPrev = bundle.getBoolean(KEY_HAS_PREV);
            mHasNext = bundle.getBoolean(KEY_HAS_NEXT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);

        if ((!getResources().getBoolean(R.bool.tablet_mode)) &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportUIforPhoneLandscape();
        } else {
            if (!getResources().getBoolean(R.bool.tablet_mode)) {
                mRecipeStepClickListener.showToolBar();
            }
            supportUIforTablet();
        }

        restoreDataFromBundle(savedInstanceState);
        updateStepDetails(mStep, mCurrentStepPosition, mHasPrev, mHasNext);

        return view;
    }

    private void supportUIforTablet() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mSimpleExoPlayerView.getLayoutParams();
        lp.height = (int) getResources().getDimension(R.dimen.video_view_height);
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        mSimpleExoPlayerView.setLayoutParams(lp);

        FrameLayout.LayoutParams noVideoContentLp = (FrameLayout.LayoutParams) mNoVideoContentView.getLayoutParams();
        noVideoContentLp.height = (int) getResources().getDimension(R.dimen.video_view_height);
        noVideoContentLp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        mNoVideoContentView.setLayoutParams(lp);
    }

    private void supportUIforPhoneLandscape() {
        mRecipeStepClickListener.hideToolBar();
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mSimpleExoPlayerView.getLayoutParams();
        lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        mSimpleExoPlayerView.setLayoutParams(lp);

        FrameLayout.LayoutParams noVideoContentLp = (FrameLayout.LayoutParams) mNoVideoContentView.getLayoutParams();
        noVideoContentLp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        noVideoContentLp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        mNoVideoContentView.setLayoutParams(lp);
    }

    private void restoreDataFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(KEY_STEP);
            mCurrentStepPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION);
            mHasPrev = savedInstanceState.getBoolean(KEY_HAS_PREV);
            mHasNext = savedInstanceState.getBoolean(KEY_HAS_NEXT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_STEP, mStep);
        outState.putInt(KEY_CURRENT_POSITION, mCurrentStepPosition);
        outState.putBoolean(KEY_HAS_PREV, mHasPrev);
        outState.putBoolean(KEY_HAS_NEXT, mHasNext);
    }

    @Optional
    @OnClick(R.id.ingredient_prev)
    public void onPrev() {
        mRecipeStepClickListener.onPrev(mCurrentStepPosition);
    }

    @Optional
    @OnClick(R.id.ingredient_next)
    public void onNext() {
        mRecipeStepClickListener.onNext(mCurrentStepPosition);
    }

    public void updateStepDetails(Step step, int currentPosition, boolean hasPrev, boolean hasNext) {
        if (step != null) {
            mStep = step;
            mCurrentStepPosition = currentPosition;
            String stepInstruction = step.getDescription();

            ExoPlayerHandler.getInstance().releasePlayer();

            if (!TextUtils.isEmpty(step.getVideoURL())) {
                if (!NetworkUtility.isInternetConnected(getActivity())) {
                    mSimpleExoPlayerView.setVisibility(View.GONE);
                    mNoVideoContentView.setVisibility(View.VISIBLE);
                    mNoVideoContentView.setText(getString(R.string.no_internet_connection));
                } else {
                    mSimpleExoPlayerView.setVisibility(View.VISIBLE);
                    mNoVideoContentView.setVisibility(View.GONE);

                }
            } else {
                mSimpleExoPlayerView.setVisibility(View.GONE);
                mNoVideoContentView.setVisibility(View.VISIBLE);
                mNoVideoContentView.setText(getString(R.string.there_is_no_video_demo_for_this_step));
            }
            if (mStepInstructionsView != null) {
                if (!TextUtils.isEmpty(stepInstruction)) {
                    mStepInstructionsView.setVisibility(View.VISIBLE);
                    mStepInstructionsView.setText(stepInstruction);
                } else {
                    mStepInstructionsView.setVisibility(View.GONE);
                }
            }
            if (mPrev != null && mNext != null) {
                mPrev.setVisibility(hasPrev ? View.VISIBLE : View.GONE);
                mNext.setVisibility(hasNext ? View.VISIBLE : View.GONE);
            }

            ExoPlayerHandler.getInstance().prepare(getActivity(), Uri.parse(mStep.getVideoURL()),
                    mSimpleExoPlayerView);
            ExoPlayerHandler.getInstance().putForeground();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ExoPlayerHandler.getInstance().putForeground();
    }

    @Override
    public void onPause() {
        super.onPause();

        ExoPlayerHandler.getInstance().putBackground();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ExoPlayerHandler.getInstance().putBackground();
        ExoPlayerHandler.getInstance().releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ExoPlayerHandler.getInstance().putBackground();
        ExoPlayerHandler.getInstance().releasePlayer();
    }
}
