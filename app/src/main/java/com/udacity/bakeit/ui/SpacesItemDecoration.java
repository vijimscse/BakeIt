package com.udacity.bakeit.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.bakeit.R;

/**
 * Created by VijayaLakshmi.IN on 8/26/2017.
 * Item decorator for {@link RecipeListFragment}
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private Context mContext;

    public SpacesItemDecoration(int space, Context context) {
        this.space = space;
        mContext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) < mContext.getResources().getInteger(R.integer.grid_column_count)) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
