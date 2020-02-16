package com.hua.multilayout_core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/29 10:04
 */

public interface ILayoutProvider {
    @Nullable
    View contentView(Context context, @NonNull ViewGroup container);

    @LayoutRes
    int layoutId();
}
