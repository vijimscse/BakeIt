package com.udacity.bakeit.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.udacity.bakeit.R;

import butterknife.ButterKnife;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ViewGroup mFragmentContainer;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);

        initialiseContainer(layoutResID);
    }

    private void initialiseContainer(@LayoutRes int layoutResID) {
        if (layoutResID != 0) {
            mFragmentContainer = (ViewGroup) findViewById(R.id.fragment_container);
            mFragmentContainer.addView(getLayoutInflater().inflate(layoutResID, null, false));
            ButterKnife.bind(this);
        } else {
            throw new IllegalStateException("layoutResID can't be zero");
        }
    }

    protected void setActivityTitle(@StringRes int stringResID) {
        if (stringResID != 0) {
            //mToolBar.setTitle(getString(stringResID));
           // setSupportActionBar();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(stringResID));
            }
        } else {
            throw new IllegalStateException("layoutResID can't be zero");
        }
    }

    protected void setActivityTitle(String title) {
        //mToolBar.setTitle(title);
        getSupportActionBar().setTitle(title);
    }

    protected void hideTitleBar() {
     //   mToolBar.setVisibility(View.GONE);
    }

    protected void showTitleBar() {
     //   mToolBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
