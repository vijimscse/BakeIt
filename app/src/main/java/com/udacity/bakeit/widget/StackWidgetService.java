package com.udacity.bakeit.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by VijayaLakshmi.IN on 9/4/2017.
 * Service for widget
 */
public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(getApplicationContext(), intent);
    }
}