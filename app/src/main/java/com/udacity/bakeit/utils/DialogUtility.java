package com.udacity.bakeit.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Vijayalakshmi.IN on 7/13/2017.
 * Util class to show message
 */

public class DialogUtility {


    /**
     * Shows toast message
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        if (context != null && !TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Shows a SnackBar message
     * @param view
     * @param messageId
     */
    public static void showMessage(View view, int messageId) {
        Snackbar.make(view, messageId, Snackbar.LENGTH_SHORT).show();
    }
}

