package com.udacity.bakeit.widget;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

import com.udacity.bakeit.R;
import com.udacity.bakeit.dto.Ingredient;
import com.udacity.bakeit.dto.Recipe;
import com.udacity.bakeit.io.IOManager;
import com.udacity.bakeit.utils.DialogUtility;
import com.udacity.bakeit.utils.NetworkUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VijayaLakshmi.IN on 9/2/2017.
 * Provides a widget setting screen.
 */

public class WidgetSettingsActivity extends ListActivity {


    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private int mAppWidgetId;
    private List<Recipe> mRecipeList = new ArrayList<>();
    private WidgetRecipeListAdapter mRecipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!NetworkUtility.isInternetConnected(this)) {
            DialogUtility.showToast(this, getString(R.string.no_internet_connection));
            finish();
        }

        setContentView(R.layout.activity_widget_settings);
        ButterKnife.bind(this);

        setAppWidgetId();
        setRecipeListAdapter();
        fetchRecipeList();
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        if (getListView().getCheckedItemPosition() >= 0) {
            Recipe recipe = mRecipeListAdapter.getItem(getListView().getCheckedItemPosition());
            if (recipe == null) {
                return;
            }
            ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) recipe.getIngredients();
            WidgetDataProvider.getInstance().setIngredientList(ingredientList);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            Intent intent = new Intent(this, StackWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(getPackageName(), R.layout.widget_ingredients_list);
            rv.setTextViewText(R.id.ingredients_list_title, recipe.getName());
            rv.setRemoteAdapter(R.id.ingredients_list, intent);
            rv.setEmptyView(R.id.ingredients_list, R.id.empty_view);
            Intent toastIntent = new Intent(this, WidgetProvider.class);
            toastIntent.setAction(WidgetProvider.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(this, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.ingredients_list, toastPendingIntent);
            appWidgetManager.updateAppWidget(mAppWidgetId, rv);

           /* Intent resultValue = new Intent();*/
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            DialogUtility.showMessage(getListView(), R.string.select_recipe);
        }
    }

    private void setRecipeListAdapter() {
        mRecipeListAdapter = new WidgetRecipeListAdapter(this, (ArrayList<Recipe>) mRecipeList);
        setListAdapter(mRecipeListAdapter);
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void fetchRecipeList() {
        if (mRecipeList == null || mRecipeList.isEmpty()) {
            if (NetworkUtility.isInternetConnected(this)) {
                showProgressBar();
                IOManager.requestRecipeList(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        hideProgressBar();
                        mRecipeList.clear();
                        mRecipeList.addAll(response.body());
                        mRecipeListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        hideProgressBar();
                        DialogUtility.showToast(WidgetSettingsActivity.this, getString(R.string.generic_error));
                        finish();
                    }
                });
            } else {
                hideProgressBar();
                DialogUtility.showMessage(getListView(), R.string.no_internet_connection);
            }
        } else {
            hideProgressBar();
        }
    }

    private void setAppWidgetId() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        } else {
            finish();
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
}
