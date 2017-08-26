package com.udacity.bakeit.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.udacity.bakeit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ViewGroup mFragmentContainer;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);

        initialiseContainer(layoutResID);

        ButterKnife.bind(this);
    }

    private void initialiseContainer(@LayoutRes int layoutResID) {
        if (layoutResID != 0) {
            mFragmentContainer = (ViewGroup) findViewById(R.id.fragment_container);
            mFragmentContainer.addView(getLayoutInflater().inflate(layoutResID, null, false));
        } else {
            throw new IllegalStateException("layoutResID can't be zero");
        }
    }

    protected void setActivityTitle(@StringRes int stringResID) {
        if (stringResID != 0) {
            mToolBar.setTitle(getString(stringResID));
        } else {
            throw new IllegalStateException("layoutResID can't be zero");
        }
    }
}
