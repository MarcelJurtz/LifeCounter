package com.marceljurtz.lifecounter.models;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class ViewHelper {

    @Nullable
    public static View findFirstViewByTag(ViewGroup root, String tag) {

        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = root.getChildAt(i);

            if (childView.getTag() != null && childView.getTag().toString().equals(tag)) {
                return childView;
            }
        }

        return null;
    }

    public static String lblDescriptionTag = "description";
    public static String lblAtkTag = "atk";
    public static String lblDefTag = "def";
}
