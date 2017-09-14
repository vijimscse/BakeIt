package com.udacity.bakeit.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;
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
    private static final String KEY_VIDEO_CURRENT_POSITION = "key_video_current_position";

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
    private long mVideoCurrentPosition;

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
        setHasOptionsMenu(true);

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

        restoreDataFromBundle(savedInstanceState);
        updateStepDetails(mStep, mCurrentStepPosition, mHasPrev, mHasNext);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if ((!getResources().getBoolean(R.bool.tablet_mode)) &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportUIforPhoneLandscape();
        } else {
            if (!getResources().getBoolean(R.bool.tablet_mode)) {
                mRecipeStepClickListener.showToolBar();
            }
            supportUIforTablet();

            ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
            }
        }
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
    private void expandVideoView() {
        mSimpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        mSimpleExoPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
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
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void supportUIforPhoneLandscape() {
        mRecipeStepClickListener.hideToolBar();

        FrameLayout.LayoutParams noVideoContentLp = (FrameLayout.LayoutParams) mNoVideoContentView.getLayoutParams();
        noVideoContentLp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        noVideoContentLp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        mNoVideoContentView.setLayoutParams(noVideoContentLp);

        hideSystemUI();
        expandVideoView();
    }

    private void restoreDataFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(KEY_STEP);
            mCurrentStepPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION);
            mHasPrev = savedInstanceState.getBoolean(KEY_HAS_PREV);
            mHasNext = savedInstanceState.getBoolean(KEY_HAS_NEXT);
            mVideoCurrentPosition = savedInstanceState.getLong(KEY_VIDEO_CURRENT_POSITION);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_STEP, mStep);
        outState.putInt(KEY_CURRENT_POSITION, mCurrentStepPosition);
        outState.putBoolean(KEY_HAS_PREV, mHasPrev);
        outState.putBoolean(KEY_HAS_NEXT, mHasNext);
        outState.putLong(KEY_VIDEO_CURRENT_POSITION, mVideoCurrentPosition);
    }

    @Optional
    @OnClick(R.id.ingredient_prev)
    public void onPrev() {
        mVideoCurrentPosition = 0;
        mRecipeStepClickListener.onPrev(mCurrentStepPosition);
    }

    @Optional
    @OnClick(R.id.ingredient_next)
    public void onNext() {
        mVideoCurrentPosition = 0;
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
                    preparePlayer();
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
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        preparePlayer();
    }

    public void preparePlayer() {
        if ((Util.SDK_INT <= 23) && !TextUtils.isEmpty(mStep.getVideoURL())) {
            ExoPlayerHandler.getInstance().prepare(getActivity(), mStep.getVideoURL(), mSimpleExoPlayerView, mVideoCurrentPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            mVideoCurrentPosition = ExoPlayerHandler.getInstance().getCurrentPosition();
            ExoPlayerHandler.getInstance().releasePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        preparePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            ExoPlayerHandler.getInstance().releasePlayer();
        }
    }
}
