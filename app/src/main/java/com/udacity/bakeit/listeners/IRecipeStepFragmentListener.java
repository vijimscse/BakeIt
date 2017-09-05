package com.udacity.bakeit.listeners;

/**
 * Created by VijayaLakshmi.IN on 8/27/2017.
 */

public interface IRecipeStepFragmentListener {
    void onStepClick(int position);
    void onNext(int currentPosition);
    void onPrev(int currentPosition);
    void hideToolBar();
    void showToolBar();
}
