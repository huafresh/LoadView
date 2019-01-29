package com.hua.multilayout_core;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: hua
 * Created: 2017/9/26
 * Description:
 * 根据参数显示布局，{@link #showLoadView(Class)}
 */

class LoadService {
    private static final String TAG = "LoadService";
    private LoadParams mLoadParams;

    LoadService(LoadParams loadParams) {
        this.mLoadParams = loadParams;
    }

    /**
     * 显示指定布局
     */
    public void showLoadView(Class<? extends LoadView> cls) {
        try {
            if (mLoadParams.getLoadViewMap().containsKey(cls)) {
                ViewGroup target = mLoadParams.getTarget();
                LoadView loadView = mLoadParams.getLoadViewMap().get(cls);
                View child = loadView.getContentView();

                ViewGroup parent = (ViewGroup) child.getParent();
                if (parent != null) {
                    parent.removeView(child);
                }

                target.removeAllViews();
                target.addView(child);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, String.format("showLoadView: show %s error", cls.getSimpleName()));
        }
    }

}
