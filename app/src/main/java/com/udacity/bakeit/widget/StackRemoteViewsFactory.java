package com.udacity.bakeit.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.bakeit.R;
import com.udacity.bakeit.dto.Ingredient;

import java.util.List;

/**
 * Created by VijayaLakshmi.IN on 9/4/2017.
 */
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Ingredient> mWidgetItems;
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        mWidgetItems = WidgetDataProvider.getInstance().getIngredientList();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        int count = 0;

        if (mWidgetItems != null) {
            count = mWidgetItems.size();
        }

        return count;
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_list_item);
        rv.setTextViewText(R.id.ingredients_list_item, mWidgetItems.get(position).getIngredient());

        Bundle extras = new Bundle();
        extras.putInt(WidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.ingredients_list_item, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
