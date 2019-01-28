package com.hua.loadview;


import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: hua
 * Created: 2017/9/26
 * Description: 用于实现在界面中根据需要显示特定的布局
 * 使用方法：
 * 1、调用{@link #getBuilder}方法获取参数构造器并且添加自定义的{@link LoadView}
 * 2、调用{@link #register}方法注册需要进行布局替换的目标，目前仅支持了Activity
 * 3、根据拿到的{@link LoadService}实例，调用{@link LoadService#showLoadView}方法显示特定布局
 */

public class LoadManager {
    private static final String TAG = "LoadManager";
    private SparseArray<LoadBuilder> mLoadBuilders;

    private LoadManager() {
        mLoadBuilders = new SparseArray<>();
    }

    public static LoadManager getInstance() {
        return HOLDER.sInstance;
    }

    private static final class HOLDER {
        private static final LoadManager sInstance = new LoadManager();
    }

    private void addLoadBuilder(LoadBuilder loadBuilder) {
        if (loadBuilder != null) {
            mLoadBuilders.put(0, loadBuilder);
        }
    }

    private void addLoadBuilder(LoadBuilder loadBuilder, int type) {
        if (loadBuilder != null) {
            mLoadBuilders.put(type, loadBuilder);
        }
    }


    /**
     * 注册需要应用加载等布局的目标，可以是Activity、Fragment、View
     *
     * @param target   目标
     * @param listener 重新加载监听
     * @param type     标识使用哪一个{@link LoadBuilder}
     * @return 调用此对象的showLoadView方法显示特定的布局
     */
    public LoadService register(Object target, LoadView.ReLoadListener listener, int type) {
        LoadService loadService = null;
        try {
            LoadBuilder builder = mLoadBuilders.get(type);
            List<LoadView> loadViews = builder.getLoadViews();
            HashMap<Class<? extends LoadView>, LoadView> loadViewMap = new HashMap<>();
            for (LoadView loadView : loadViews) {
                loadViewMap.put(loadView.getClass(), loadView);
            }
            LoadParams loadParams = new LoadParams();
            loadParams.setLoadViewMap(loadViewMap);
            loadParams.setTarget(getTargetViewGroup(target));
            loadService = new LoadService(loadParams);
            LoadView defaultLoadView = loadViews.size() > 0 ? loadViews.get(0) : null;
            if (defaultLoadView != null) {
                loadService.showLoadView(defaultLoadView.getClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, String.format("register: register %s error",target.getClass().getSimpleName()));
        }
        return loadService;
    }

    private static ViewGroup getTargetViewGroup(Object target) {
        ViewGroup viewGroup = null;
        if (target instanceof Activity) {
            viewGroup = (ViewGroup) ((Activity) target).findViewById(android.R.id.content);
        } else {
            throw new IllegalArgumentException("the target only support activity at present");
        }
        return viewGroup;
    }


    public static LoadBuilder getBuilder() {
        return getBuilder(0);
    }

    /**
     * 获取参数构造器
     *
     * @param type 构造器标识
     * @return 构造器
     */
    public static LoadBuilder getBuilder(int type) {
        return new LoadBuilder(type);
    }

    private static class LoadBuilder {
        private int type;
        private List<LoadView> mLoadViews;

        private LoadBuilder(int type) {
            type = type;
            mLoadViews = new ArrayList<>();
        }

        public LoadBuilder addLoadView(LoadView view) {
            if (view != null) {
                mLoadViews.add(view);
            }
            return this;
        }

        public LoadBuilder setDefaultLoadView(LoadView view) {
            if (view != null) {
                if (mLoadViews.contains(view)) {
                    mLoadViews.remove(view);
                }
                mLoadViews.add(0, view);
            }
            return this;
        }

        public void commit() {
            LoadManager.getInstance().addLoadBuilder(this);
        }

        List<LoadView> getLoadViews() {
            return mLoadViews;
        }
    }


}
