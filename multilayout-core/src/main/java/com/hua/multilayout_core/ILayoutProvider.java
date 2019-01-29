package com.hua.multilayout_core;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

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
