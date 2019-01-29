package com.hua.multilayout_core;

import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Author: hua
 * Created: 2017/9/26
 * Description:
 * 执行一次显示操作需要的参数
 */

class LoadParams {
    /** 需要显示的布局将来的父布局 */
    private ViewGroup target;
    /** 可显示的布局池 */
    private HashMap<Class<? extends LoadView>, LoadView> loadViewMap;

    ViewGroup getTarget() {
        return target;
    }

    void setTarget(ViewGroup target) {
        this.target = target;
    }

    HashMap<Class<? extends LoadView>, LoadView> getLoadViewMap() {
        return loadViewMap;
    }

    void setLoadViewMap(HashMap<Class<? extends LoadView>, LoadView> loadViewMap) {
        this.loadViewMap = loadViewMap;
    }
}
