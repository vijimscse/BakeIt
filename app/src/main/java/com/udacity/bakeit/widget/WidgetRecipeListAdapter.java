package com.udacity.bakeit.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.bakeit.dto.Recipe;

import java.util.ArrayList;

/**
 * Created by VijayaLakshmi.IN on 9/4/2017.
 */

public class WidgetRecipeListAdapter extends ArrayAdapter<Recipe> {

    private Context mContext;

    public WidgetRecipeListAdapter(@NonNull Context context, @NonNull ArrayList<Recipe> recipeList) {
        super(context, 0, recipeList);

        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WidgetRecipeViewHolder widgetRecipeViewHolder;
        if (convertView == null) {
            widgetRecipeViewHolder = new WidgetRecipeViewHolder();
            convertView = LayoutInflater.from(mContext)
                    .inflate(android.R.layout.simple_list_item_single_choice, parent, false);

            widgetRecipeViewHolder.mRecipeName = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(widgetRecipeViewHolder);
        } else {
            widgetRecipeViewHolder = (WidgetRecipeViewHolder) convertView.getTag();
        }

        widgetRecipeViewHolder.mRecipeName.setText(getItem(position).getName());

        return convertView;
    }

    static class WidgetRecipeViewHolder {
        TextView mRecipeName;
    }
}
