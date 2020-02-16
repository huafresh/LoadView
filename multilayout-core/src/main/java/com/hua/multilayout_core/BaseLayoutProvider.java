package com.hua.multilayout_core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/29 14:23
 */

public abstract class BaseLayoutProvider implements ILayoutProvider {
    @Nullable
    @Override
    public View contentView(Context context, @NonNull ViewGroup container) {
        return null;
    }

    @Override
    public int layoutId() {
        return 0;
    }
}
