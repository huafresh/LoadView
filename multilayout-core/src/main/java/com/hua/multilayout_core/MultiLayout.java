package com.hua.multilayout_core;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/29 10:19
 */

public class MultiLayout extends FrameLayout {

    public static final int LAYOUT_TYPE_ACTUAL = -1;
    public static final int LAYOUT_TYPE_LOADING = 1001;
    public static final int LAYOUT_TYPE_ERROR = 1002;
    private SparseArray<View> cacheLayout = new SparseArray<>();
    private SparseArray<OnClickListener> listenerInfo;
    private int type = -1;

    public MultiLayout(Context context) {
        this(context, null);
    }

    public MultiLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

    }

    public static MultiLayout wrap(Activity activity) {
        return wrap(getView(activity));
    }

    public static MultiLayout wrap(Fragment fragment) {
        return wrap(getView(fragment));
    }

    public static MultiLayout wrap(View view) {
        if (view == null) {
            throw new IllegalArgumentException("view can not be null");
        }
        MultiLayout multiLayout = new MultiLayout(view.getContext());
        ViewGroup parent = (ViewGroup) view.getParent();
        multiLayout.cacheLayout.put(LAYOUT_TYPE_ACTUAL, view);
        if (parent != null) {
            parent.removeView(view);
            multiLayout.addView(view);
            parent.addView(multiLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            multiLayout.addView(view);
        }
        return multiLayout;
    }

    public static void registerLoadingLayout(ILayoutProvider provider) {
        registerLayoutProvider(LAYOUT_TYPE_LOADING, provider);
    }

    public static void registerErrorLayout(ILayoutProvider provider) {
        registerLayoutProvider(LAYOUT_TYPE_ERROR, provider);
    }

    public static void registerLayoutProvider(int type, ILayoutProvider provider) {
        if (type < 0) {
            throw new IllegalArgumentException("type must be positive");
        }
        LayoutStore.put(type, provider);
    }

    private static View getView(Object target) {
        View result = null;
        if (target instanceof Activity) {
            View decorView = ((Activity) target).getWindow().getDecorView();
            result = ((ViewGroup) decorView.findViewById(android.R.id.content)).getChildAt(0);
            if (result == null) {
                throw new IllegalArgumentException("you must call Activity.setContentView() first");
            }
        } else if (target instanceof Fragment) {
            result = ((Fragment) target).getView();
            if (result == null) {
                throw new IllegalArgumentException("MultiLayout.wrap(fragment) must call after onCreateView(), " +
                        "or you can use MultiLayout.wrap(view) instead");
            }
        } else {
            throw new IllegalArgumentException("Only support Activity or V4 Fragment");
        }
        return result;
    }

    public void showLoading() {
        showWithType(LAYOUT_TYPE_LOADING);
    }

    public void showError() {
        showWithType(LAYOUT_TYPE_ERROR);
    }

    /**
     * 展示特定布局
     *
     * @param type 注册{@link ILayoutProvider}时传递的type.
     */
    public void showWithType(final int type) {
        ensureMainThread(new Runnable() {
            @Override
            public void run() {
                if (MultiLayout.this.type == type) {
                    return;
                }
                View layout = cacheLayout.get(type);
                if (layout == null) {
                    ILayoutProvider layoutProvider = LayoutStore.get(type);
                    if (layoutProvider == null) {
                        throw new IllegalStateException("can not find ILayoutProvider for type = " + type);
                    }
                    layout = layoutProvider.contentView(getContext(), MultiLayout.this);
                    if (layout == null) {
                        int layoutId = layoutProvider.layoutId();
                        layout = LayoutInflater.from(getContext()).inflate(layoutId, MultiLayout.this, false);
                    }
                }

                if (layout != null) {
                    removeAllViews();
                    addView(layout);
                    setupListener(layout);
                    MultiLayout.this.type = type;
                }
            }
        });
    }

    private void ensureMainThread(Runnable run) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            run.run();
        } else {
            post(run);
        }
    }

    public void showContent() {
        showWithType(-1);
    }

    private void setupListener(View target) {
        if (listenerInfo != null) {
            int size = listenerInfo.size();
            for (int i = 0; i < size; i++) {
                int id = listenerInfo.keyAt(i);
                OnClickListener listener = listenerInfo.valueAt(i);
                View child = target.findViewById(id);
                if (child != null) {
                    child.setOnClickListener(listener);
                }
            }
        }
    }

    public void setOnClickListener(@IdRes int id, OnClickListener listener) {
        if (listenerInfo == null) {
            listenerInfo = new SparseArray<>();
        }
        listenerInfo.put(id, listener);
    }

}