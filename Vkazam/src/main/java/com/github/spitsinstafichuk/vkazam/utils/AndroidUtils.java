package com.github.spitsinstafichuk.vkazam.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class AndroidUtils {

    public static boolean isThereASettingsButton(Context context) {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR2
                || (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) && ViewConfiguration
                .get(context).hasPermanentMenuKey();
    }

    public static void setViewEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int i = 0; i < group.getChildCount(); i++) {
                setViewEnabled(group.getChildAt(i), enabled);
            }
        }
    }

    public static void setViewClickable(View view, boolean clickable) {
        view.setClickable(clickable);

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int i = 0; i < group.getChildCount(); i++) {
                setViewClickable(group.getChildAt(i), clickable);
            }
        }
    }
}
